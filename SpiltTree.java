package sample.System.Map;

/**
 * @author huyue
 * @date 2018/11/15-9:16
 */
public class SpiltTree {
    public int x;
    public int y;
    public int width;
    public int height;

    public SpiltTree P;
    public SpiltTree A=null;
    public SpiltTree B=null;

    public SpiltTree(int x, int y, int width, int height,SpiltTree P) {
        this.P = P;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean spilt(int w,int h,double rate){
        if (w<width || h<height){
            if (width>height){
                //竖着切一刀
                A=new SpiltTree(x,y,(int)(rate*width),height,this);
                B=new SpiltTree(x+(int)(rate*width),y,width-(int)(rate*width),height,this);
            }else {
                //横着切一刀
                A=new SpiltTree(x,y,width,(int)(rate*height),this);
                B=new SpiltTree(x,y+(int)(rate*height),width,height-(int)(height*rate),this);
            }
            return true;
        }
        return false;
    }

    public boolean isRoom(){
        if (A==null && B==null)
            return true;
        else
            return false;
    }
    public int[] center(){
        int[] fin={x+width/2,y+height/2};
        return fin;
    }
}
