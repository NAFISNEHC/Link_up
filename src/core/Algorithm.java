package core;

import visual.GameStatus;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Algorithm {
    
	//	动态数组列表  随时添加拐点 删除拐点
    private static List<Point> list = new ArrayList<Point>();
    
    //	判断连通方法
    public static List<Point> checkLinked(GameStatus[][] arr, Point a, Point b) {
        
    	//	清空列表
    	list.clear();
    	
    	//	判断是否为直线连通
        if (noCorner(arr, a, b) != null) {
            return list;
        }
        
        //	判断是否为一拐角连通
        if (oneCorner(arr, a, b) != null) {
            return list;
        }
        
        //	判断是否为两拐角连通
        if (twoCorner(arr, a, b) != null) {
            return list;
        }
        
        //	若都不是则返回null
        return null;
    }

    // 	直线连通算法
    public static List<Point> noCorner(GameStatus[][] arr, Point a, Point b) {
        
    	//	判断是否两点是否可以直线连通
    	if (canArrived(arr, a, b)) {
    		
    		//	将点添加到列表中
            list.add(a);
            list.add(b);
            
            return list;
        }
    	
        return null;
    }

    // 	一拐角连通算法
    public static List<Point> oneCorner(GameStatus[][] arr, Point a, Point b) {
        
    	//	新增拐点（情况一）
    	Point c = new Point(a.x, b.y);
    	
    	//	判断拐点位置的棋子是否已经消除 且 起点到拐点 和 拐点到终点 能够直线连通
        if (arr[c.x][c.y].getStatus() <= 0 && canArrived(arr, a, c) && canArrived(arr, c, b)) {

        	//  将点添加到列表中
        	list.add(a);
            list.add(c);
            list.add(b);
            return list;
        }

        //	新增拐点（情况二）
        Point d = new Point(b.x, a.y);

        //  判断拐点位置的棋子是否已经消除 且 起点到拐点 和 拐点到终点 能够直线连通
        if (arr[d.x][d.y].getStatus() <= 0 && canArrived(arr, a, d) && canArrived(arr, d, b)) {
            
        	//	将点添加到列表中
        	list.add(a);
            list.add(d);
            list.add(b);
            
            return list;
        }
        
        return null;
    }

    // 	二拐角连通算法
    public static List<Point> twoCorner(GameStatus[][] arr, Point a, Point b) {
    	
    	//	遍历该行
        for (int i = 0; i < arr[0].length; i++) {
            
        	//	新增拐点（情况一）
        	Point c = new Point(a.x, i);

        	//  判断拐点位置的棋子是否已经消除 且 起点到拐点 能够直线连通 且拐点到终点可以一拐角连通
            if (arr[c.x][c.y].getStatus() <= 0 && canArrived(arr, a, c) && oneCorner(arr, c, b) != null) {
                
            	//	将起点添加到列表头
            	list.add(0, a);
            	
                return list;
            }
        }
        for (int i = 0; i < arr.length; i++) {
            
        	//	新增拐点（情况二）
        	Point c = new Point(i, a.y);
            
            //  判断拐点位置的棋子是否已经消除 且 起点到拐点 能够直线连通 且拐点到终点可以一拐角连通
            if (arr[c.x][c.y].getStatus() <= 0 && canArrived(arr, a, c) && oneCorner(arr, c, b) != null) {

            	//	将起点添加到列表头
                list.add(0, a);
                
                return list;
            }
        }
        
        return null;
    }

    //	判断直线是否可以连通
    public static boolean canArrived(GameStatus[][] arr, Point a, Point b) {
        
    	//	同一行
        if (a.x == b.x) {

        	//	遍历该行两点之间的棋子是否都已清除
            for (int i = Math.min(a.y, b.y) + 1; i < Math.max(a.y, b.y); i++) {
                
            	//	若有未清除的棋子则不连通
            	if (arr[a.x][i].getStatus() > 0) {
                    return false;
                }
            }
           
            return true;
        }

        // 	同一列
        if (a.y == b.y) {
        	
        	//	遍历该列两点之间的棋子是否都已清除
            for (int i = Math.min(a.x, b.x) + 1; i < Math.max(a.x, b.x); i++) {
            	
            	//	若有未清除的棋子则不连通
                if (arr[i][a.y].getStatus() > 0) {
                    return false;
                }
            }
  
            return true;
        }
        
        //	不是同一行或同一列
        return false;
    }
}