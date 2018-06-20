package calfromimg;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
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
    private Parent CreateContent() {
        Pane root = new Pane();
        root.setPrefSize(800, 600);

        Canvas canvas = new Canvas(800, 600);
        g = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed((event) -> {
            System.out.println("hello");
            if(event.getButton()==MouseButton.PRIMARY){
                startx = event.getX();
                starty = event.getY();
                System.out.println(startx + "," + starty);
            }else if(event.getButton()==MouseButton.SECONDARY){
                endx = event.getX();
                endy = event.getY();
                System.out.println(endx + ", " + endy);
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();
        
        root.getChildren().add(canvas);
        return root;
    }
    private double startx, starty, endx, endy;
    public void onUpdate() {
        g.setStroke(Color.BLUE);
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
    
    /**
     * @param bottom  桅杆底部坐标点
     * @param top  桅杆顶部坐标点
     * @param center 相机拍照中心点
     * @param h 无人机拍摄的高度 
     * 
     * 坐标系以左下角围殴原点
     */
    public double horziential(Point2D bottom, Point2D top, Point2D center, double h){
        double delta_y = top.getY() - center.getY();
        return delta_y + h;
    }

}
