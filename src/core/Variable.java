package core;

import javax.swing.*;
import java.awt.*;

public class Variable {
	
	//	棋子总数
	public static int count = 80;
	
	//	停止循环
	public static boolean flag = false;
	public static boolean flag2 = true;
	
	//	音效播放选择
	public static boolean sel = false;
	
	//	消除后的棋子状态量
	public static int update = 0;
	
    //	游戏总行数与总列数
    public static int rows = 8;
    public static int cols = 10;
    
    //	棋子图标的宽与高
    public static int chessWidth = 55;
    public static int chessHeight = 55;
    
    //	棋盘到边界的距离 棋盘包括外圈
    public static int marginWidth = 220-chessWidth;
    public static int marginHeight = 100-chessHeight;
    
    //	游戏开始按钮图
    public static Image imageStart = new ImageIcon("./src/images/start.png").getImage().getScaledInstance(450, 250, Image.SCALE_DEFAULT);

    //	游戏结束的动画
    public static Image imageJs = new ImageIcon("./src/images/js.png").getImage().getScaledInstance(396, 553, Image.SCALE_DEFAULT);
    
    //	游戏的界面图片
    public static Image imageBegin = new ImageIcon("./src/images/jm.jpg").getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
    
    //	游戏的背景图片
    public static Image imageBackground = new ImageIcon("./src/images/timg.jpg").getImage().getScaledInstance(1000, 650, Image.SCALE_DEFAULT);
    
    //	棋子图片数组
    public static Image[] chessImage = new Image[20];
    
    //	获取棋子图片到 chessImage 数组
    static {
        for (int i = 1; i <= chessImage.length; i++) {
            chessImage[i-1] = new ImageIcon("./src/images/" + i + ".png").getImage().getScaledInstance(chessWidth, chessWidth, Image.SCALE_DEFAULT);
        }
    }
}