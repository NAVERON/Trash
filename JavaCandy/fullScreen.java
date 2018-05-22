
//这个文件里是关于java代码全屏的解决方案，可以参考

//import java.awt.BorderLayout;
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
// 
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
// 
// 
//public class T extends JFrame implements ActionListener {//全屏 示例  111
//    //开始时左上角的位置,开始时的高度，宽度
//    private Point start = new Point(300,150);
//    private int width = 600;
//    private int height = 400;
//     
//    private JButton max;
//    private JButton normal;
//     
//    public T() {
//        JPanel panel = new JPanel();
//        max = new JButton("全屏");
//        max.addActionListener(this);
//        panel.add(max);
//         
//        normal = new JButton("退出全屏");
//        normal.addActionListener(this);
//        panel.add(normal);
//        this.add(panel,BorderLayout.NORTH);
//        this.setLocation(start);
//        this.setSize(width, height);
//        this.setUndecorated(true);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//     
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == max) {
//            this.setLocation(0, 0);
//            this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//            this.repaint();
//        } else if(e.getSource() == normal) {
//            this.setLocation(start);
//            this.setSize(width, height);
//            this.repaint();
//        }
//    }
//     
//    public static void main(String[] args) {
//        new T();
//    }
//}

/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////
//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.event.KeyEvent;
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
// 
//import javax.swing.JFrame;
// 
// 
//public class T extends JFrame implements MouseListener {//example 222
//    private boolean flag = false;
//    //开始时左上角的位置,开始时的高度，宽度
//    private Point start = new Point(300,150);
//    private int width = 600;
//    private int height = 400;
//     
//     
//    public T() {
//        this.addMouseListener(this);       
//        this.setLocation(start);
//        this.setSize(width, height);
//        this.setUndecorated(true);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//     
//    public static void main(String[] args) {
//        new T();
//    }
// 
//    public void keyPressed(KeyEvent e) {
//        System.out.println(e.getKeyChar());
//    }
// 
//    public void keyReleased(KeyEvent e) {
//        System.out.println(e.getKeyChar());
//    }
// 
//    public void keyTyped(KeyEvent e) {}
// 
//    public void mouseClicked(MouseEvent e) {
//        //双击全屏或退出全屏
//        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
//            if(flag) {
//                normal();
//            } else {
//                max();
//            }
//        } 
//    }
// 
//    public void mouseEntered(MouseEvent e) {}
// 
//    public void mouseExited(MouseEvent e) {}
// 
//    public void mousePressed(MouseEvent e) {}
// 
//    public void mouseReleased(MouseEvent e) {}
//     
//    /**
//     * 最大化
//     */
//    private void max() {
//        flag = true;
//        this.setLocation(0, 0);
//        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//        this.repaint();
//    }
//     
//    /**
//     * 退出全屏
//     */
//    private void normal() {
//        flag = false;
//        this.setLocation(start);
//        this.setSize(width, height);
//        this.repaint();
//    }
//}

////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////

//import java.awt.Point;
//import java.awt.Toolkit;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
// 
//import javax.swing.JFrame;
// 
// 
//public class T extends JFrame implements KeyListener {//example 333
//    private boolean flag = false;
//    //开始时左上角的位置,开始时的高度，宽度
//    private Point start = new Point(300,150);
//    private int width = 600;
//    private int height = 400;
//     
//     
//    public T() {
//        this.addKeyListener(this);
//         
//        this.setLocation(start);
//        this.setSize(width, height);
//        this.setUndecorated(true);
//        this.setVisible(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//     
//    public static void main(String[] args) {
//        new T();
//    }
// 
//    public void keyPressed(KeyEvent e) {
//        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//            if(flag) {
//                normal();
//            } else {
//                max();
//            }
//        }
//         
//        //最大化时按esc键退出
//        if(e.getKeyCode() == KeyEvent.VK_ESCAPE && flag) {
//            normal();
//        }
//    }
// 
//    public void keyReleased(KeyEvent e) {}
// 
//    public void keyTyped(KeyEvent e) {}
// 
//    /**
//     * 最大化
//     */
//    private void max() {
//        flag = true;
//        this.setLocation(0, 0);
//        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
//        this.repaint();
//    }
//     
//    /**
//     * 退出全屏
//     */
//    private void normal() {
//        flag = false;
//        this.setLocation(start);
//        this.setSize(width, height);
//        this.repaint();
//    }
//}

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;


public class T extends JFrame implements MouseListener {
    private boolean flag = false;
    //开始时左上角的位置,开始时的高度，宽度
    private Point start = new Point(300,150);
    private int width = 600;
    private int height = 400;
     
     
    public T() {
        this.addMouseListener(this);        
        this.setLocation(start);
        this.setTitle("全屏程序");
        this.setSize(width, height);
        //this.setUndecorated(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
     
    public static void main(String[] args) {
        new T();
    }
 
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }
 
    public void keyReleased(KeyEvent e) {
        System.out.println(e.getKeyChar());
    }
 
    public void keyTyped(KeyEvent e) {}
 
    public void mouseClicked(MouseEvent e) {
        //双击全屏或退出全屏
        if(e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
            if(flag) {
                normal();
            } else {
                max();
            }
        } 
    }
 
    public void mouseEntered(MouseEvent e) {}
 
    public void mouseExited(MouseEvent e) {}
 
    public void mousePressed(MouseEvent e) {}
 
    public void mouseReleased(MouseEvent e) {}
     
    /**
     * 最大化
     */
    private void max() {
        flag = true;
        this.setLocation(0, 0);
        this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        this.titled(true);
        this.repaint();
    }
     
    /**
     * 退出全屏
     */
    private void normal() {
        flag = false;
        this.setLocation(start);
        this.setSize(width, height);
        this.titled(false);
        this.repaint();
    }
     
    /**
     * 退去全屏时显示标题，全屏时不显示标题
     * @param title
     */
    private void titled(boolean title) {
        this.dispose();
        this.setUndecorated(title);
        this.setVisible(true);
    }
}

