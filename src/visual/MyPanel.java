package visual;

import core.Algorithm;
import core.PlayMusic;
import core.Variable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Random;

public class MyPanel extends JPanel {
	
	//	棋子数组
	//	为了保证外围的相同的棋子能够连线 需添加一圈棋盘空间来做判断
    GameStatus[][] arr = new GameStatus[Variable.rows + 2][Variable.cols + 2];
    
    //	线条粗细
    Stroke stroke = new BasicStroke(3.0f);

    // 两次点击的位置
    Point firstPoint;
    Point secondPoint;

    public MyPanel() {
    	
        init();

        //	添加鼠标事件监听
        this.addMouseListener(new MouseAdapter(){
        	
        	//	鼠标按键事件监听
        	public void mousePressed(MouseEvent e) {
        	
        		//	检测鼠标左键是否按下
        		if (e.getModifiers() != InputEvent.BUTTON1_MASK) {
        			return;
        		}

        		//	获取当前鼠标选中位置
        		int x = e.getX();
        		int y = e.getY();
            
        		//	相应行列数-1
        		int X = (x - Variable.marginWidth) / Variable.chessWidth;	//	列
        		int Y = (y - Variable.marginHeight) / Variable.chessHeight;	//	行
            
        		//	相应的棋子边缘左上位置
        		int rowX = X * Variable.chessWidth + Variable.marginWidth;
        		int rowY = Y * Variable.chessHeight + Variable.marginHeight;
            
        		//	绘制线条和边框
        		Graphics g = getGraphics();
        		Graphics2D g2d = (Graphics2D) g;
     
        		// 	设置边框的线条
        		g2d.setStroke(stroke);
        		
        		//	判断当前鼠标选中的位置是否在棋盘上 不包括外圈
        		if ((x >= Variable.marginWidth + Variable.chessWidth && x <= Variable.marginWidth + Variable.cols * Variable.chessWidth + Variable.chessWidth)
        				&& (y >= Variable.marginHeight + Variable.chessHeight && y <= Variable.marginHeight + Variable.rows * Variable.chessHeight + Variable.chessHeight)
                    		&& arr[Y][X].getStatus() > 0) {
                
        			// 	当前点击是否为第一次点击 或者 不是重复点击
        			if (firstPoint == null || (firstPoint.x == Y && firstPoint.y == X)) {
                	
        				//	保存该棋子
        				//	行数 X 为 y  列数 Y 为 x 方便数组下标
	                    firstPoint = new Point(Y, X);
	                    
	                    //	设置线条颜色
	                    g2d.setColor(Color.LIGHT_GRAY);
                    
	                    //	绘制边框线条
	                    g2d.drawRect(rowX + 2, rowY + 2, Variable.chessWidth - 4, Variable.chessHeight - 4);
	                    return;
        			}
                
        			//	第二次点击
        			//	保存该棋子
        			secondPoint = new Point(Y, X);
                
        			// 	判断两个棋子的状态是否相等
        			if (arr[firstPoint.x][firstPoint.y].getStatus() != arr[secondPoint.x][secondPoint.y].getStatus()) {
                    
        				//	将第二次点击作为第一次点击
        				firstPoint = secondPoint;

        				//	重绘
        				repaint();
        				return;
        			}
                
        			// 	判断是否可消去
        			List<Point> list = Algorithm.checkLinked(arr, firstPoint, secondPoint);
                
        			//	判断列表是否为空 为空则不可消去
        			if (list == null) {
                	
        				//	将第二次点击作为第一次点击
        				firstPoint = secondPoint;

        				//	重绘
        				repaint();
        				return;
        			}
                
        			//  设置线条颜色
        			g2d.setColor(Color.LIGHT_GRAY);
                    
        			//	绘制边框线条
        			g2d.drawRect(rowX + 2, rowY + 2, Variable.chessWidth - 4, Variable.chessHeight - 4);

        			// 	将两个棋子的状态更新
        			arr[firstPoint.x][firstPoint.y].setStatus(Variable.update);
        			Variable.update = Variable.update - 1;
        			arr[secondPoint.x][secondPoint.y].setStatus(Variable.update);
        			Variable.update = Variable.update - 1;
                
        			//	将两次点击设为null
        			firstPoint = null;
        			secondPoint = null;
        			
        			//	棋子总数减二
        			Variable.count = Variable.count - 2;

        			//	播放音效
        			new Thread(new Runnable() {
        				public void run() {
        					if(Variable.sel) {
        						PlayMusic.playEatBean1();
        						Variable.sel = false;
        					}
        					else {
        						PlayMusic.playEatBean2();
        						Variable.sel = true;
        					}
        				}
        			}).start();
        			
        			// 	绘制连接线
        			drawLinkedLine(list, g2d);

        			//	重绘
        			repaint();
        		}
        	}
        });
    }

    //	初始化棋盘
    public void init() {

    	//	产生随机数种子
        Random random = new Random();
        
        //	在棋盘随机位置上放置棋子
        for (int i = 1; i <= 20; i++) {
        	
        	//	计数器
            int count = 0;
            
            //	在四个不同的位置放置相同的棋子
            while (count < 4) {
                int x = random.nextInt(8)+1;
                int y = random.nextInt(10)+1;
                
                //	判断该位置是否放置过棋子
                if (arr[x][y] == null) {
                	
                	//	设置这四个位置棋子的状态为i 0为消除
                    arr[x][y] = new GameStatus(i);
                    count++;
                }
            }
        }
        
        //	棋盘外圈初始化状态为0 	
        for (int i = 0; i < arr[0].length; i++) {
            arr[0][i] = new GameStatus(0);
            arr[arr.length - 1][i] = new GameStatus(0);
        } 
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = new GameStatus(0);
            arr[i][arr[0].length - 1] = new GameStatus(0);
        }
    }

    //	绘制棋盘
    public void paint(Graphics g) {
    	
    	//	添加背景图片到当前面板
        super.paint(g);
        g.drawImage(Variable.imageBackground, 0, 0, this);
        
        //	弹出结束动画
        if (Variable.count == 0) {
        	
        	//	添加结束动画到当前面板
            g.drawImage(Variable.imageJs, 300, 80, this);
            
            //  播放音效
		    PlayMusic.playGameOver();
            Variable.flag2 = false;
        }
      		
        //	绘制棋子边框线条
        for (int i = 1; i < arr.length; i++) {
            for (int j = 1; j < arr[i].length; j++) {
            	
            	//	判断该棋子是否已消去 消去则去掉线条
                if (arr[i][j].getStatus() > 0) {
                	
                	//	获取棋子位置
                    int x = j * Variable.chessWidth + Variable.marginWidth;
                    int y = i * Variable.chessHeight + Variable.marginHeight;
                    
                    //	添加棋子图片到当前面板
                    g.drawImage(Variable.chessImage[arr[i][j].getStatus() - 1], x, y, this);
                    
                    //	设置边框线条颜色
                    g.setColor(Color.BLACK);
                    
                    //	绘制边框线条
                    g.drawRect(x, y, Variable.chessWidth, Variable.chessHeight);
                }
            }
        }
        
        // 判断是否第一次点击
        if (firstPoint != null && arr[firstPoint.x][firstPoint.y].getStatus() > 0 ) {
        	
        	//	绘制第一次选中边框
            Graphics2D g2d = (Graphics2D) g;
            
            //	绘制边框的线条
            g2d.setStroke(stroke);
            
            //	设置线条颜色
            g2d.setColor(Color.LIGHT_GRAY);
            
            //	获取第一次点击的棋子位置
            int rowX = firstPoint.y * Variable.chessWidth + Variable.marginWidth;
            int rowY = firstPoint.x * Variable.chessHeight + Variable.marginHeight;

            //	绘制线条
            g2d.drawRect(rowX + 2, rowY + 2, Variable.chessWidth - 4, Variable.chessHeight - 4);
        }
    }

    //	绘制连线
    private void drawLinkedLine(List<Point> list, Graphics2D g2d) {
    	
    	//	如果列表里有两个点存在
        if (list.size() >= 2) {
        	
        	//	获取两个点
            Point a = list.get(0);
            Point b = list.get(1);
            
            //	线头位置
            int ax = a.y * Variable.chessWidth + Variable.marginWidth + Variable.chessWidth / 2;
            int ay = a.x * Variable.chessHeight + Variable.marginHeight + Variable.chessHeight / 2;
            
            //	线尾位置
            int bx = b.y * Variable.chessWidth + Variable.marginWidth + Variable.chessWidth / 2;
            int by = b.x * Variable.chessHeight + Variable.marginHeight + Variable.chessHeight / 2;
            
            //	绘制线条
            g2d.drawLine(ax, ay, bx, by);
            
            //  如果列表里有三个点存在
            if (list.size() >= 3) {
            	Point c = list.get(2);
            	
            	//	线尾位置
                int cx = c.y * Variable.chessWidth + Variable.marginWidth + Variable.chessWidth / 2;
                int cy = c.x * Variable.chessHeight + Variable.marginHeight + Variable.chessHeight / 2;
                
            	//	绘制线条
                g2d.drawLine(bx, by, cx, cy);
                
                //  如果列表里有四个点存在
                if (list.size() == 4) {
                	Point d = list.get(3);
                	
                	//	线尾位置
                    int dx = d.y * Variable.chessWidth + Variable.marginWidth + Variable.chessWidth / 2;
                    int dy = d.x * Variable.chessHeight + Variable.marginHeight + Variable.chessHeight / 2;
                    
                    //  绘制线条
                    g2d.drawLine(cx, cy, dx, dy);
                }
            }
        }

        //	控制绘制线条线程时间
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}