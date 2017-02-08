package elos;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ShapeFactory shapeFactory=new ShapeFactory();
		Ground ground=new Ground();
		GamePanel gamePanel=new GamePanel();
		
		Controller controller=new Controller(
				shapeFactory,ground,gamePanel);
		
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(gamePanel.getSize().width +10,
				gamePanel.getSize().height +35);
		frame.add(gamePanel);
		gamePanel.addKeyListener(controller);
		frame.addKeyListener(controller);
		frame.setVisible(true);
		controller.newGame();
	}

}
