package calfromimg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class CalFromImg extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 300, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(300, 100);
        root.getChildren().add(new Circle(50, 50, 50, Color.BLUE));
        
        return root;
    }

    /**
     * 计算描述： 无人机拍摄过程中 飞行高度不变，恒定h 无人机与船舶的距离可以测得 恒定d
     *
     * 这里计算有一定倾角的情况
     *
     * @param bottom
     * @param top
     * @param center
     * @param h
     * @param d
     * @return result
     */
    public double calcute(Point2D bottom, Point2D top, Point2D center, double h, double d) {
        double result = 0;

        return result;
    }
    
    class Pic{
        public Pic(){
            //第一张照片上的
        }
    }

    /**
     * 问题描述：
     * 摄像头有拍摄中心，并且作为无人机的中心，当无人机拍摄时，可以获得自身的        飞行高度h
     * 在图片上检测桅杆的   最高点坐标top，最终可获得    拍摄中心center    和桅杆顶点top   的垂直距离
     * 
     * @param top 桅杆顶部坐标点
     * @param center 相机拍照中心点
     * @param h 无人机拍摄的高度
     *
     * 坐标系以左下角为原点
     * @param b  图片上，两次拍摄距离
     * @param real_b  实际中无人机飞行距离
     */
    public double horziential(Point2D top, Point2D center, double h, double b, double real_b) {
        double ratio = b/real_b;  //虚幻比例
        double delta_y = (top.getY() - center.getY())/ratio;
        return delta_y + h;
    }

}
