package calfromimg;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalFromImg extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(CreateContent());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    private GraphicsContext g;
    private double t = 0.0;
    private Parent CreateContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        Canvas canvas = new Canvas(800, 600);
        g = canvas.getGraphicsContext2D();
        canvas.setOnMouseClicked((event) -> {
            
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                t += 1;
                draw();
            }
        };
        timer.start();
        
        root.getChildren().add(canvas);
        return root;
    }
    private int startx, starty, endx, endy;
    public void draw() {
        g.strokeLine(startx, starty, endx, endy);
    }
    /**
     * 计算描述：
     * 无人机拍摄过程中  飞行高度不变，恒定h
     * 无人机与船舶的距离可以测得   恒定d
     * 
     * @return 
     */
    public double calcute(){
        double resault = 0;
        int h = 0;
        
        
        return resault;
    }

}
