/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autonavigation;

import java.util.LinkedList;
import java.util.List;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Ship extends Navigator{
    
    private List<History> historys = new LinkedList<>();
    private double rudder = 0;
    
    public Point2D destination;  //目的地
    public double direction;  //目标方向
    public double finalVelocity;  //目标速度
    private double K = 0.0785, T = 3.12;
    
    public Ship(int id){
        super(new Circle(10, Color.RED));
        super.setId(id);
        super.setVelocity(Point2D.ZERO);
        super.getView().setRotate(0);
    }
    public Ship(int id, Point2D velocity){
        super(new Circle(10, Color.RED));
        super.setId(id);
        super.setVelocity(velocity);
    }
    public int isWhat(){  //对象类型，1代表普通船舶
        return 1;
    }
    public void diffRudder(double diff){  //传入的是舵角变化值
        this.rudder += diff;
    }
    public double getRudder(){
        return this.rudder;
    }
    public List<History> getHistorys(){
        return historys;
    }
    public boolean addHistory(History history){
        return this.historys.add(history);
    }
    public void clearHistory(){
        historys.clear();
    }
    public void setDestination(Point2D destination){
        this.destination = destination;
    }
    public Point2D getDestination(){
        return this.destination;
    }
    
    @Override
    public void upDate(){
        
        if(isDead()){
            this.setVelocity(Point2D.ZERO);  //设置为静止
            return;
        }
        if (super.getView().getTranslateX() > 800) {
            super.getView().setTranslateX(0);
        } else if (super.getView().getTranslateX() < 0) {
            super.getView().setTranslateX(800);
        } else if (super.getView().getTranslateY() > 600) {
            super.getView().setTranslateY(0);
        } else if (super.getView().getTranslateY() < 0) {
            super.getView().setTranslateY(600);
        }
        if(getView().getRotate() >= 360){
            getView().setRotate(getView().getRotate() - 360);
        }else if(getView().getRotate() < 0){
            getView().setRotate(getView().getRotate() + 360);
        }
        //下面把速度变化了
        //System.out.println(super.getVelocity().getX() + "before===" + super.getVelocity().getY() + "rotate :" + super.getView().getRotate());
        double dr = K*rudder*( 1-T+T*Math.pow(Math.E, -1/T) );
        super.getView().setRotate(super.getView().getRotate() + dr);
        setVelocity(new Point2D( Math.cos(Math.toRadians(super.getView().getRotate()))*getSpeed(),
                Math.sin(Math.toRadians(super.getView().getRotate()))*getSpeed()) );
        
        //System.out.println(super.getVelocity().getX() + "after===" + super.getVelocity().getY() + "rotate : " + super.getView().getRotate());
        super.getView().setTranslateX(super.getView().getTranslateX() + super.getVelocity().getX());
        super.getView().setTranslateY(super.getView().getTranslateY() + super.getVelocity().getY());
        
        historys.add(new History(super.getPosition().getX(), super.getPosition().getY(), super.getView().getRotate(), super.getVelocity(), getRudder()));
        //System.out.println(super.getView().getRotate());
    }
    
    @Override
    public void turnLeft(){  //转向影响的是速度分量的变化
        rudder -= 5;
    }
    @Override
    public void turnRight(){
        rudder += 5;
    }
    
    public void coursePID(double direction){
        //
    }
    public void autoSpeed(double finalVelocity){
        //
    }
    
    public void thinkAndDoing() {  //覆盖服了的方法
        //
    }
    
}





