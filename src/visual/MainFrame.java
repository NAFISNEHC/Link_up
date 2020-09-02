package visual;

import core.PlayMusic;
import core.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
	
	//	新建面板
    MyPanel mapPanel = new MyPanel();

    //	设置框架
    public MainFrame() {
    	
    	//	添加面板
        this.add(mapPanel);
        
        //	设置窗口标题
        this.setTitle("连连看");
        
        //	设置窗口大小
        this.setSize(1000, 650);

        //	设置窗口居中
        this.setLocationRelativeTo(null);

        //	设置窗口关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //	设置窗口不可缩放
        this.setResizable(false);

        //	设置窗口可见
        this.setVisible(true);

        //	设置窗口图标
        ImageIcon imageIcon = new ImageIcon("./src/images/bt.png");
        Image image = imageIcon.getImage();
        this.setIconImage(image);
        
        //	先调用一次
      	PlayMusic.playBGM();
      		
      	//	设置一个定时器 循环播放背景音乐
      	new Timer(36000, new ActionListener() {
      		public void actionPerformed(ActionEvent e) {
      			if (Variable.flag2) {
      				PlayMusic.playBGM();
      			}
      		}
      	}).start();
    }
}