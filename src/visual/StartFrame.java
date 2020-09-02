package visual;

import core.PlayMusic;
import core.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartFrame extends JFrame {
	
	//	新建面板
    JPanel StartPanel = new JPanel();
    Graphics g;

    //	设置框架
    public StartFrame() {
    	
    	//	添加面板
        this.add(StartPanel);
        
        //	设置窗口标题
        this.setTitle("连连看");
        
        //	设置窗口大小
        this.setSize(1000, 650);

        //	设置窗口图标
        ImageIcon imageIcon = new ImageIcon("./src/images/bt.png");
        Image image = imageIcon.getImage();
        this.setIconImage(image);
        
        //	加载图片
      	ImageIcon icon1 = new ImageIcon(Variable.imageBegin);
      	ImageIcon icon2 = new ImageIcon(Variable.imageStart);
      	
      	//	将图片放入标签中
      	JLabel label1 = new JLabel(icon1);
      	JLabel label2 = new JLabel(icon2);
      		
      	//	设置标签的大小
      	label1.setBounds(0, 0, 1000, 650);
      	label2.setBounds(275, 400, 450, 250);
      		
      	//	获取窗口的第二层 将标签放入
      	this.getLayeredPane().add(label1, new Integer(Integer.MIN_VALUE));
      	this.getLayeredPane().add(label2, new Integer(Integer.MAX_VALUE));
      			
      	//	获取窗口的顶层容器 并设置为透明
      	JPanel j = (JPanel)this.getContentPane();
      	j.setOpaque(false);
       
      	//	新增面板
      	JPanel panel = new JPanel();
       
      	//	面板必须设置为透明的 否则看不到图片
      	panel.setOpaque(false);
       
      	//	添加面板到窗口
      	this.add(panel);
        
     	//  设置窗口居中
        this.setLocationRelativeTo(null);

        //	设置窗口关闭
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //	设置窗口不可缩放
        this.setResizable(false);

        //	设置窗口可见
        this.setVisible(true);

        //	先调用一次
      	PlayMusic.playJM();
      		
      	//设置一个定时器 循环播放界面音乐
      	new Timer(32000, new ActionListener() {
      		public void actionPerformed(ActionEvent e) {
      			if(!Variable.flag) {
      				PlayMusic.playJM();
      			}
      		}
      	}).start();
      	
      	//	添加鼠标事件监听
        this.addMouseListener(new MouseAdapter(){
        	
        	//	鼠标按键事件监听
        	public void mousePressed(MouseEvent e) {
        	
        		//	检测鼠标左键是否按下
        		if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
        			return;
        		}
        		
        		//  获取当前鼠标选中位置
        		int x = e.getX();
        		int y = e.getY();
        		
        		//  判断当前鼠标选中的位置是否在开始图片上
        		if ((x >= 413 && x <= 588) && (y >=  510 && y <= 575)) {
        			
        			//	跳转
            		Variable.flag = true;
                    to();
        		}
        	}
        });
    }

    public void to() {

    	//	关闭界面窗口
    	Window window = (Window) this;
        window.dispose();

        //	停止播放界面音乐
        PlayMusic.stopJM();

        //	打开主窗口
        new MainFrame();
    }

    public static void main(String[] args) throws Exception {

        new StartFrame();

    }
}