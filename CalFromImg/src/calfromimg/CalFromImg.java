package calfromimg;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class CalFromImg extends Application {

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();

        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest((event) -> {          //这里获取图片路径不正确，明天改正
            if (!SystemTray.isSupported()) {
                System.out.println("SystemTray is not supported");
                return;
            }
            final PopupMenu popup = new PopupMenu();
            
            URL url = CalFromImg.class.getResource("..\\images\\bulb.gif");
            Image image = new ImageIcon(url).getImage();
            //Image image = Toolkit.getDefaultToolkit().getImage(url);

            final TrayIcon trayIcon = new TrayIcon(image);

            final SystemTray tray = SystemTray.getSystemTray();

            // Create a pop-up menu components
            MenuItem aboutItem = new MenuItem("About");
            CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
            CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
            Menu displayMenu = new Menu("Display");
            MenuItem errorItem = new MenuItem("Error");
            MenuItem warningItem = new MenuItem("Warning");
            MenuItem infoItem = new MenuItem("Info");
            MenuItem noneItem = new MenuItem("None");
            MenuItem exitItem = new MenuItem("Exit");

            //Add components to pop-up menu
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(cb1);
            popup.add(cb2);
            popup.addSeparator();
            popup.add(displayMenu);
            displayMenu.add(errorItem);
            displayMenu.add(warningItem);
            displayMenu.add(infoItem);
            displayMenu.add(noneItem);
            popup.add(exitItem);

            trayIcon.setPopupMenu(popup);
            
            aboutItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(() -> primaryStage.show());
            }
        });

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.out.println("TrayIcon could not be added.");
            }
        });
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
     * @param bottom 桅杆底部坐标点
     * @param top 桅杆顶部坐标点
     * @param center 相机拍照中心点
     * @param h 无人机拍摄的高度
     *
     * 坐标系以左下角围殴原点
     */
    public double horziential(Point2D top, Point2D center, double h) {
        double delta_y = top.getY() - center.getY();
        return delta_y + h;
    }

}
