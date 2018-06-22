package calfromimg;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CalFromImg extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.setImplicitExit(false);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    public Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(200, 100);
        return root;
    }

    /**
     * 计算描述： 无人机拍摄过程中 飞行高度不变，恒定h 无人机与船舶的距离可以测得 恒定d
     *
     * 这里计算有一定倾角的情况
     *
     * @return
     */
    public double calcute() {
        double result = 0;
        int h = 0;

        return result;
    }

    /**
     * 问题描述：
     * 摄像头有拍摄中心，并且作为无人机的中心，当无人机拍摄时，可以获得自身的高度h
     * 在图片上检测桅杆的最高点，最终可获得拍摄中心和桅杆顶点的垂直距离
     * 
     * @param bottom 桅杆底部坐标点
     * @param top 桅杆顶部坐标点
     * @param center 相机拍照中心点
     * @param h 无人机拍摄的高度
     *
     * 坐标系以左下角为原点
     */
    public double horziential(Point2D top, Point2D center, double h) {
        double delta_y = top.getY() - center.getY();
        return delta_y + h;
    }

}
