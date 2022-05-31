package sample.System.Map;

import sample.Main;

import java.util.Arrays;
import java.util.Random;

/**
 * @author huyue
 * @date 2018/11/15-9:09
 */
public class Rogue {
    private int width;
    private int height;
    private int[][] map;
    private Random random;

    private SpiltTree initTree;

    public static final int wall=0;
    public static final int floor=1;
    public static final int corridor=2;
    public static final int door=3;
    public static final int doorOpen=4;
    public static final int wallUpfloor=5;


    public static final int[][] toolR1={
            {-1,-1},{-1,+0},{-1,+1},
            {+0,-1},        {+0,+1},
            {+1,-1},{+1,+0},{+1,+1},
    };


    public Rogue(int width,int height){
        this.width=width;
        this.height=height;
        map=new int[height][width];
        for (int i=0;i<map.length;i++){
            Arrays.fill(map[i],wall);
        }
        initTree=new SpiltTree(0,0,width,height,null);
        random= Main.random;
        init(7,7);
    }
    public void init(int w,int h){
        //初始化 w:预设房间最大宽度 h:预设房间最大高度 限制条件
        //w,h实际上是分割空间的宽高
        w+=3;
        h+=4;
        //空间分割
        spilt(w,h,initTree);

        //房间填充
        drawRooms(initTree);
        //通道填充
        digCorridor(initTree);
    }
    public int[][] getMap() {
        //防止
        int[][] fin=new int[height*2-3][width*2-3];
        for (int i=1;i<fin.length;i+=2){
            for (int j=1;j<fin[0].length;j+=2){
                fin[i][j]=map[(i-1)/2+1][(j-1)/2+1];
            }
        }
        insert(fin);
        initWall(fin);
        return fin;
    }

    private void initWall(int[][] map){
        for (int y=0;y<map.length-1;y++){
            for (int x=0;x<map[0].length;x++){
                if ((map[y+1][x]==floor || map[y+1][x]==corridor || map[y+1][x]==door || map[y+1][x]==doorOpen)
                        && map[y][x]==wall)
                    map[y][x]=wallUpfloor;
            }
        }
    }
    private void spilt(int w,int h,SpiltTree s){
        int r=random.nextInt(20);
        double rate=0.4+r/100.0;
        if (s.spilt(w,h,rate)){
             spilt(w,h,s.A);
             spilt(w,h,s.B);
        }
    }
    private void drawRooms(SpiltTree temp){
        if (temp.isRoom()){
            if (temp.width>4 || temp.height>4){
                for (int i=temp.y+1;i<temp.y+temp.height-1;i++){
                    for (int j=temp.x+1;j<temp.x+temp.width-1;j++){
                        map[i][j]=floor;
                    }
                }
            }
        }else {
            drawRooms(temp.A);
            drawRooms(temp.B);
        }
    }
    private void digCorridor(SpiltTree temp){
        if (!temp.isRoom()){
            int[] t1=temp.A.center();
            int[] t2=temp.B.center();

            if (t1[0]==t2[0]){
                for (int i=t1[1];i<t2[1];i++){
                        if (map[i][t1[0]]==0){
                        map[i][t1[0]] = corridor;
                    }
                }
            }else {
                for (int i=t1[0];i<t2[0];i++){
                        if (map[t1[1]][i]==0){
                        map[t1[1]][i] = corridor;
                    }
                }
            }

            digCorridor(temp.A);
            digCorridor(temp.B);
        }
    }
    private void insert(int[][] fin){
        for (int i=1;i<fin.length;i+=2){
            for (int j=2;j<fin[0].length-1;j+=2){
                if (fin[i][j-1]==fin[i][j+1]){
                    fin[i][j]=fin[i][j-1];
                }
            }
        }
        for (int i=2;i<fin.length-1;i+=2){
            for (int j=1;j<fin[0].length-1;j++){
                if (fin[i-1][j]==fin[i+1][j]){
                    fin[i][j]=fin[i-1][j];
                }
            }
        }
        for (int i=1;i<fin.length-1;i++){
            for (int j=1;j<fin[0].length-1;j++){
                int[] roll1=r1(fin,j,i);
                if(fin[i-1][j]==corridor || fin[i+1][j]==corridor ||
                        fin[i][j-1]==corridor || fin[i][j+1]==corridor){
                    if ((roll1[0]==3 || roll1[0]==2) && roll1[1]==1)
                        fin[i][j]=door;
//                        fin[i][j]=floor;
                    if ((roll1[0]==3 || roll1[0]==2) && roll1[1]==2)
                        fin[i][j]=door;
//                        fin[i][j]=floor;
                    if ( roll1[0]==2 && roll1[1]==3)
                        fin[i][j]=door;
//                        fin[i][j]=floor;
                }

            }
        }
    }
    private int[] r1(int[][] map,int x,int y){
        int[] fin=new int[2];
        for (int i=0;i<toolR1.length;i++){
            if (map[y+toolR1[i][0]][x+toolR1[i][1]]==floor)
                fin[0]++;
            if (map[y+toolR1[i][0]][x+toolR1[i][1]]==corridor)
                fin[1]++;
        }
        return fin;
    }
}
