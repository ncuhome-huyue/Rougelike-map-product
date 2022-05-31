import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author huyue
 * @date 2018/11/15-10:51
 */
public class Test extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    public void start(Stage stage){
        Canvas canvas=new Canvas(610,510);

        Rogue rouge=new Rogue(32,27);
        rouge.init(7,7);
        int[][] map=rouge.getMap();

        Color[] set={Color.BLACK,Color.YELLOW,Color.GREEN,Color.GRAY};

        for (int i=0;i<map.length;i++) {
            for (int j = 0; j < map[0].length; j++) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.setFill(set[map[i][j]]);
                gc.fillRect(j * 10, i * 10, 10, 10);
            }
        }

        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Test");
        stage.sizeToScene();
        stage.show();
    }
}
