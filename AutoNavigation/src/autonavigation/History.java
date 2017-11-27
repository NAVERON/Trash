/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autonavigation;

import javafx.geometry.Point2D;

/**
 *
 * @author ERON
 */
public class History {  //轨迹点
    
    private double x;
    private double y;
    private double rotation;
    private Point2D velocity;
    private double rudder;
    
    public History(){
    }
    
    public History(double x, double y, double rotation, Point2D velocity, double rudder){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.velocity = velocity;
        this.rudder = rudder;
    }
    
    @Override
    public String toString(){
        return x + "," + y + "," + rotation + "," + velocity.getX() + "," + velocity.getY() + "," + rudder + ","
                + Math.sqrt( velocity.getX()*velocity.getX() + velocity.getY()*velocity.getY() );
    }
}
