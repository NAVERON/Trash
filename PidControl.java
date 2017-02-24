package show;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PidControl extends JPanel{
	
	public static float kp=1.2F, ki=20F, kd=10F;
	public static float desireValue=80,//目标值，目标航向
			curValue=0;  //当前值,本船航向
	public static float theta = 0;  //舵角>>>>>>>>>>>>>>>>>>>>>>>>
	
	public float cur_error=0,  //当前的误差
			last_error=0,  //上次误差
			pre_error=0,  //上上次误差
			sum_error=0;  //误差之和，求取积分和
	public float last_u=0,  //这个是什么？？--->可以记录上次计算后的输出值
			cur_u=0;  //当前偏差,对舵角的偏移值
	
	public ArrayList<Float> uArray= new ArrayList<>();  //计算输出值存储-->现在用来存储当前值与目标值的误差
	public ArrayList<Float> value = new ArrayList<>();  //被控制量的变化情况
	public ArrayList<Float> thetas = new ArrayList<>();  //舵角变化情况
	float K=0.0785F, T = 3.12F;  //船舶操纵性能，根据操纵性能求出舵角theta后->角度变化
	public PidControl() {
		super();
	}
	
	public static void main(String[] args) {  //假定舵可以在采样一个周期达到任何舵角
		// TODO 界面
		JFrame frame=new JFrame();
		
		PidControl draw = new PidControl();
		draw.setBackground(Color.WHITE);
		frame.setContentPane(draw);
		
		//frame.pack();
		frame.setBounds(50, 50, 1000, 600);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		
		//开始计算数据
		//draw.test();
		//draw.p();  //比例
		//draw.pi();
		//draw.pd();
		draw.pid();
		//draw.pidAdd();
		//draw.repaint();
		draw.repaint();
	}
	
	public void test(){  //测试方法
		uArray.clear();
		value.clear();
		thetas.clear();
		
		desireValue=80;
		curValue=0;
		//System.out.println(K*35*(-2.12+T*Math.exp((-1/T))));
		while(Math.abs(desireValue-curValue)>1){
			cur_error = desireValue-curValue;  //当前距离目标值的差
			cur_u = kp*(cur_error-last_error);  //pid计算后输出增量
			//System.out.println("当前舵角变化值->"+cur_u);
			//根据当前状态输出舵角
			theta+=cur_u;  //当前舵角
			//System.out.println("当前舵角->"+theta);
			if (theta>35 || theta<-35) {  //当增大到最大舵角后不再增大-->这里有一个问题，并不是舵角越大转角效果越好？怎么办？映射函数？
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(1-T+T*Math.pow(Math.E, -1/T)));
			//System.out.println("角度增加/变化->"+div);
			curValue+=div;  //根据计算出的角度变化求出当前角度值，即下一次的采集角度值
			//System.out.println("当前角度值->"+curValue);
			uArray.add(cur_u);
			value.add(curValue);
			thetas.add(theta);
			
			last_error=cur_error;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	public void p(){  //kp越大越快
		uArray.clear();
		value.clear();
		thetas.clear();
		
		desireValue=80;
		curValue=0;
		while(Math.abs(desireValue-curValue)>1){
			cur_error = desireValue-curValue;  //当前距离目标值的差
			cur_u = kp*(cur_error-last_error);
			//curValue +=cur_u;
			theta+=cur_u;
			if (theta>35 || theta<-35) {  //当增大到最大舵角后不再增大-->这里有一个问题，并不是舵角越大转角效果越好？怎么办？映射函数？
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(1-T+T*Math.pow(Math.E, -1/T)));
			curValue += div;
			System.out.println("+-------------------------------"
					+"\n|当前值："+curValue
					+"\n|本次舵角-->"+theta
					+"\n|本次误差: "+cur_error
					+"\n+------------------------------------"
					);
			uArray.add(cur_error);
			value.add(curValue);
			thetas.add(theta);
			
			last_error=cur_error;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	public void pi(){
		uArray.clear();
		value.clear();
		desireValue=80;
		curValue=0;
		//比例和积分
		while(Math.abs(curValue-desireValue)>1){
			cur_error = desireValue-curValue;
			cur_u = kp*(cur_error-last_error)+(kp/ki)*cur_error;
			pre_error=last_error;
			last_error=cur_error;
			//curValue +=cur_u;
			theta+=cur_u;
			if (theta>35 || theta<-35) {  //当增大到最大舵角后不再增大-->这里有一个问题，并不是舵角越大转角效果越好？怎么办？映射函数？
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(1-T+T*Math.pow(Math.E, -1/T)));
			curValue += div;
			
			System.out.println("+-------------------------------"
					+"\n|当前值："+curValue
					+"\n|本次增量-->"+cur_u
					+"\n|距离目标的误差-->"+cur_error
					+"\n+------------------------------------"
					);
			uArray.add(cur_error);  //当前的误差
			value.add(curValue);
			thetas.add(theta);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
		
	}
	public void pd(){
		uArray.clear();
		value.clear();
		desireValue=80;
		curValue=0;
		//微分+比例
		while (Math.abs(curValue-desireValue)>1) {
			cur_error = desireValue-curValue;
			cur_u = kp*(cur_error-last_error)+(kp*kd)*(cur_error-2*last_error+pre_error);
			pre_error=last_error;
			last_error=cur_error;
			//curValue +=cur_u;
			theta+=cur_u;
			if (theta>35 || theta<-35) {  //当增大到最大舵角后不再增大-->这里有一个问题，并不是舵角越大转角效果越好？怎么办？映射函数？
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(1-T+T*Math.pow(Math.E, -1/T)));
			curValue += div;
			
			System.out.println("+-------------------------------"
					+"\n|当前值："+curValue
					+"\n|本次增量-->"+cur_u
					+"\n|距离目标的误差-->"+cur_error
					+"\n+------------------------------------"
					);
			uArray.add(cur_error);  //当前的误差
			value.add(curValue);
			thetas.add(theta);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	public void pid(){  //稳定快速准确
		uArray.clear();
		value.clear();
		desireValue=80;
		curValue=0;
		
		float a = kp*(1+1/ki+kd);
		float b = kp*(1+2*kd);
		float c = kp*kd;
		boolean begin=true;
		while(Math.abs(curValue-desireValue)>1 || begin){
			cur_error = desireValue-curValue;  //当前误差
			cur_u = kp*(cur_error-last_error)+(kp/ki)*cur_error+kp*kd*(cur_error-2*last_error+pre_error);  //计算得出增量
			//cur_u = a*cur_error-b*last_error+c*pre_error;
			pre_error=last_error;
			last_error=cur_error;
			//curValue +=cur_u;
			theta+=cur_u;  //下面这步相当于matlab里的设定阈值
			if (theta>35 || theta<-35) {  //当增大到最大舵角后不再增大-->这里有一个问题，并不是舵角越大转角效果越好？怎么办？映射函数？
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(2-T+T*Math.pow(Math.E, -2/T)));
			/*float div=0;
			if (theta>0 && theta<10) {  //简单计算在舵角一定的情况下，时间t后航向变化值
				div=5;
			}
			else if(theta>10 && theta<20){
				div=10;
			}
			else if(theta>20 && theta<35){
				div=4;
			}*/
			curValue += div;
			float r = (float) (K*theta*(1-Math.exp(-2/T)));
			System.out.println(r);
			if (r<0.01 && Math.abs(curValue-desireValue)<1) {
				begin=false;
			}
			System.out.println("+-------------------------------"
					+"\n|当前值："+curValue
					+"\n|本次增量-->"+cur_u
					+"\n|距离目标误差==>"+cur_error
					+"\n+------------------------------------"
					);
			uArray.add(cur_error);  //当前的误差
			value.add(curValue);
			thetas.add(theta);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	public void pidAdd(){  //位置式
		uArray.clear();
		value.clear();
		desireValue=80;
		curValue=0;
		while(Math.abs(curValue-desireValue)>1){
			cur_error = desireValue-curValue;  //当前误差
			cur_u = kp*cur_error+(kp/ki)*sum_error+kp*kd*(cur_error-last_error);
			last_error=cur_error;
			//curValue = cur_u;
			theta+=cur_u;
			if (theta>35 || theta<-35) {
				theta -=cur_u;
			}
			//根据舵角计算下一次采集的航向值
			float div = (float) (K*theta*(1-T+T*Math.pow(Math.E, -1/T)));
			curValue += div;
			
			System.out.println("+-------------------------------"
					+"\n|当前值："+curValue
					+"\n|当前误差==>"+cur_error
					+"\n+------------------------------------"
					);
			uArray.add(cur_error);  //当前的误差
			value.add(curValue);
			thetas.add(theta);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	/*public void curchange(){  //计算舵角大小
		if (theta>-5 && theta<5) {
			curValue += 1;
		}
		else if (theta>=5 && theta<10) {
			curValue += 5;
		}
		else if (theta>=10 && theta<20) {
			curValue += 10;
		}
		else if (theta>=20 && theta<35) {
			curValue += 5;
		}/////////////////////////////////////////////////////////////////////
		else if (theta<=-5 && theta>-10) {
			curValue += -5;
		}
		else if (theta<=-10 && theta>-20) {
			curValue  += -10;
		}
		else if (theta<=-20 && theta>-35) {
			curValue += -5;
		}
		else {
			return;
		}
	}*/
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);  //去掉这句不会全部重新绘制，只会绘制新值，并覆盖原值
		//**************************************************
		//坐标系，左下角  0 500处-------->画出坐标系
		//x轴
		g.setColor(Color.BLACK);
		g.drawLine(0, 500, 700, 500);
		
		for(int i=0;i<700;i+=40){
			g.drawOval(i, 500, 5, 5);
			g.drawString(String.valueOf(i), i, 520);
		}
		//y轴
		for(int i=0;i<501;i+=20){
			g.drawOval(0, 500-i, 5, 5);
			g.drawString(String.valueOf(i/5), 15, 510-i);
		}
		g.setFont(new Font("Consolas", Font.PLAIN, 20));
		g.drawString("kp = "+kp
				+ "->ki = "+ki
				+ "->kd = "+kd,
				600, 20);  //当前pid的值
		g.drawString("cur error", 600, 40);
		g.drawString("Current Value", 600, 60);
		g.drawString("Rudder Angle", 600, 80);
		//显示数据--------------------------------------------------------------
		g.drawLine(0, (int) (500-desireValue*5), 700, (int) (500-desireValue*5));  //目标线
		g.setColor(Color.BLUE);
		g.drawLine(750, 40, 850, 40);  //表示那种颜色对应哪种变量
		int j=0;
		for(int i=0;i<uArray.size();i++){  //增量值
			float geti = uArray.get(i);  //自动拆箱
			g.drawOval(j, 500-(int) geti*5, 5, 5);
			j+=5;
		}
		g.setColor(Color.GREEN);
		g.drawLine(750, 60, 850, 60);  //颜色对应的变量
		j=0;
		for(int i=0;i<value.size();i++){  //画出当前值
			float geti = value.get(i);
			g.drawOval(j, 500-(int) geti*5, 5, 5);
			j+=5;
		}
		g.setColor(Color.ORANGE);
		g.drawLine(750, 80, 850, 80);
		j=0;
		for(int i=0;i<thetas.size();i++){
			float geti = thetas.get(i);
			g.fillOval(j, 500-(int)geti*5, 5, 5);
			j+=5;
		}
	}
	
}
