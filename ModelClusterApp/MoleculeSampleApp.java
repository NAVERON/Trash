/*
 * Copyright (c) 2013, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package moleculesampleapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author cmcastil
 */
public class MoleculeSampleApp extends Application {
    
    final Group root = new Group();
    final Xform axisGroup = new Xform();
    final Xform moleculeGroup = new Xform();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -450;
    private static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    private static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double AXIS_LENGTH = 250.0;
    private static final double HYDROGEN_ANGLE = 104.5;
    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    
    private void buildCamera() {
        System.out.println("buildCamera()");
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(false);
        world.getChildren().addAll(axisGroup);
    }

    private void handleMouse(Scene scene, final Node root) {
        scene.setOnMousePressed((MouseEvent me) -> {  //使用了lamda表达式
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX); 
                mouseDeltaY = (mousePosY - mouseOldY); 
                
                double modifier = 1.0;
                
                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                } 
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }     
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);  
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);  
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);  
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);  
                }
            }
        });
    }
    
    private void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {  //可以在这里添加按键变化颜色的动作
                    case Z:
                        cameraXform2.t.setX(0.0);
                        cameraXform2.t.setY(0.0);
                        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
                        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
                        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
                        break;
                    case X:
                        axisGroup.setVisible(!axisGroup.isVisible());
                        break;
                    case V:
                        moleculeGroup.setVisible(!moleculeGroup.isVisible());
                        break;
                }
            }
        });
    }
    
    private void buildMolecule() {
        //======================================================================
        // THIS IS THE IMPORTANT MATERIAL FOR THE TUTORIAL
        //======================================================================
        
        //两个问题，颜色的生成
        //如何表示聚类，颜色，需要加点别的东西
        
        final PhongMaterial redMaterial = new PhongMaterial();  //红色材质
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        
        final PhongMaterial whiteMaterial = new PhongMaterial();  //白色材质
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);
        
        final PhongMaterial blueMaterial = new PhongMaterial(Color.BLUE);   //蓝色材质
        blueMaterial.setSpecularColor(Color.BLUE);
        
        final PhongMaterial greyMaterial = new PhongMaterial();  //灰色材质，作为背景
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);
        
        Xform moleculeXform = new Xform();
        Xform oxygenXform = new Xform();
        Xform hydrogen1SideXform = new Xform();
        Xform hydrogen1Xform = new Xform();
        Xform hydrogen2SideXform = new Xform();
        Xform hydrogen2Xform = new Xform();
        //************************************************************画球开始
//        Sphere oxygenSphere = new Sphere(1.0);  //起初坐标都在原点
//        oxygenSphere.setMaterial(redMaterial);
//        
//        Sphere hydrogen1Sphere = new Sphere(1.0);
//        hydrogen1Sphere.setMaterial(whiteMaterial);
//        hydrogen1Sphere.setTranslateX(0.0);   //坐标变换在这里
//        
//        Sphere hydrogen2Sphere = new Sphere(1.0);
//        hydrogen2Sphere.setMaterial(greyMaterial);
//        hydrogen2Sphere.setTranslateZ(0.0);  //偏移坐标

        for (int i = 0; i<100; i++) {
            Point3D next = allPoints.get(i);
            Sphere t = new Sphere(3.0);
            if (null==typeDefine[i]) {
                t.setMaterial(whiteMaterial);
            }
            else switch (typeDefine[i]) {
                case 0:
                    t.setMaterial(redMaterial);
                    break;
                case 1:
                    t.setMaterial(blueMaterial);
                    break;
                default:
                    t.setMaterial(whiteMaterial);
                    break;
            }
            t.setTranslateX(next.getX());
            t.setTranslateY(next.getY());
            t.setTranslateZ(next.getZ());
            moleculeXform.getChildren().add(t);
        }
        
//        for (int i = 0; i<100; i++) {
//            Point3D next = allPoints.get(i);
//            Sphere t = new Sphere(3.0);
//            t.setMaterial(whiteMaterial);
//            t.setTranslateX(next.getX());
//            t.setTranslateY(next.getY());
//            t.setTranslateZ(next.getZ());
//            moleculeXform.getChildren().add(t);
//        }
        //*************************************************************画球结束  --> 圆柱开始
//        Cylinder bond1Cylinder = new Cylinder(0.5, 100);
//        bond1Cylinder.setMaterial(greyMaterial);
//        bond1Cylinder.setTranslateX(50.0);
//        bond1Cylinder.setRotationAxis(Rotate.Z_AXIS);
//        bond1Cylinder.setRotate(90.0);
//
//        Cylinder bond2Cylinder = new Cylinder(0.5, 100);
//        bond2Cylinder.setMaterial(greyMaterial);
//        bond2Cylinder.setTranslateX(50.0);
//        bond2Cylinder.setRotationAxis(Rotate.Z_AXIS);
//        bond2Cylinder.setRotate(90.0);
        //**************************************************************圆柱结束
        moleculeXform.getChildren().add(oxygenXform);
        moleculeXform.getChildren().add(hydrogen1SideXform);
        moleculeXform.getChildren().add(hydrogen2SideXform);
        //oxygenXform.getChildren().add(oxygenSphere);
        hydrogen1SideXform.getChildren().add(hydrogen1Xform);
        hydrogen2SideXform.getChildren().add(hydrogen2Xform);
        //hydrogen1Xform.getChildren().add(hydrogen1Sphere);
        //hydrogen2Xform.getChildren().add(hydrogen2Sphere);
//        hydrogen1SideXform.getChildren().add(bond1Cylinder);
//        hydrogen2SideXform.getChildren().add(bond2Cylinder);
        
        hydrogen1Xform.setTx(100.0);
        hydrogen2Xform.setTx(100.0);
        hydrogen2SideXform.setRotateY(HYDROGEN_ANGLE);
        
        moleculeGroup.getChildren().add(moleculeXform);

        world.getChildren().addAll(moleculeGroup);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////?????
    //自我变量定义区
    List<Point3D> allPoints = new ArrayList<Point3D>();  //保存随机生成的3D点坐标
    Random xs = new Random(80);  // setSeed()    -----> 生成随机坐标点
    Random ys = new Random(50);
    Random zs = new Random(200);
    //List<LinkedList<Double>> distances = new LinkedList<LinkedList<Double>>();  //总链表，在链表上嵌套分类后的链表
    double[][] distances = new double[100][100];  //距离存储   --- ????为什么
    
    //Boolean[] coreIndex = new Boolean[100];  //核心点索引存储
    int coreCounts = 0, borderCounts = 0;  //类的数目, 核心点数量，边界点数量
    Integer[] typeDefine = new Integer[100];  //标志属于哪一类
    //核心点 0  --  边界点  1 --  其他点   -1
    
    ////////////////////////////////////////////////////////////////////////////////////////////?????
    private void cluster(){  //聚类计算部分
        for (int i = 0; i < 100; i++) {  //初始化标志量，全部初始化为杂点
            typeDefine[i] = -1;
        }
        //计算，变量全局
        for (int i = 0; i < 100; i++) {  //生成   100   随机点
            allPoints.add(new Point3D(xs.nextInt(100), ys.nextInt(100), zs.nextInt(100)));
            System.out.println("第"+i + "--->" + allPoints.get(i));
        }
        //计算每个点相对于其他点的距离
        for (int i = 0; i < allPoints.size(); i++) {
            Point3D next = allPoints.get(i);
            for (int j = 0; j < allPoints.size(); j++) {
                if (i==j) {
                    distances[i][j] = 0;
                    continue;
                }
                Point3D get = allPoints.get(j);
                distances[i][j] = next.distance(get.getX(), get.getY(), get.getZ());  //点距离
            }
        }
        //复制数组，方便检查核心点
        double[][] cloneArray = new double[100][100];  //这个是将要排序的数组
        for (int i = 0; i < 100; i++) {
            System.arraycopy(distances[i], 0, cloneArray[i], 0, 100);
        }
        //升序排列
        for (int i = 0; i < allPoints.size(); i++) {
            Arrays.sort(cloneArray[i]);
        }
        //打印点的距离
//        for (int i = 0; i < 100; i++) {  //这里可以看出是升序的
//            for (int j = 0; j < 100; j++) {
//                System.out.println("第"+i+"个点：距离值"+cloneArray[i][j]);
//            }
//        }
        
        int MinPts = 3;  //半径内对象数量
        int average, sum = 0;  //计算Eps临时变量
        for (int i = 0; i < 100; i++) {
            sum += cloneArray[i][3];
        }
        average = sum/100;  //规定半径
        System.out.println("计算后的半径："+average);
        //求取核心点
        for (int i = 0; i < 100; i++) {    //最小数目   定做  3
            for (int j = 0; j < 100; j++) {
                if (cloneArray[i][j]<=average && j>=4) {  //如果符合条件继续前移
                    typeDefine[i] = 0;  //标记为核心点
                    break;
                }
                else if(cloneArray[i][j]>average && j<4){
                    break;
                }
                //记录那个索引是核心点
            }
        }
        
        //求取边界点---------------------------------这里计算错误，排序之后会错乱,排序方便了筛选距离，但是带来这个问题。。。how to do？
        for (int i = 0; i < 100; i++) {
            if (typeDefine[i]!=0) {  //如果不是核心点，则计算是不是边界点，不是边界点就是杂点
                for (int j = 1; j < 100; j++) {  //排序之后0索引总是自己的距离，0---
                    //if (i!=j) {  //如果不是自己
                    if (distances[i][j]<average && typeDefine[j]==0) {  //拿出的这个点在范围内，并且是核心点，则i索引的点为边界点
                        typeDefine[i] = 1;
                        break;
                    }
                }
            }
        }
        //输出计算后的信息
        for (int i = 0; i < 100; i++) {
            if (typeDefine[i] == 0) {  //核心点
                coreCounts++;
            }
            else if (typeDefine[i] == 1) {  //边界点
                borderCounts++;
            }
        }
        
        System.out.println("核心点数量是："+coreCounts 
                + "\n边界点数量是："+borderCounts);
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
       // setUserAgentStylesheet(STYLESHEET_MODENA);
        System.out.println("start()");
        
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        
        // buildScene();
        buildCamera();
        buildAxes();
        //需要在这里插入，先计算，存储后显示
        cluster();
        buildMolecule();
        
        Scene scene = new Scene(root, 1024, 768, true);
        scene.setFill(Color.GREY);
        handleKeyboard(scene, world);
        handleMouse(scene, world);
        
        primaryStage.setTitle("Molecule Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        scene.setCamera(camera);
    }
    
    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
