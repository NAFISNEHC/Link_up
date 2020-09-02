package core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class PlayMusic {
	
	// AudioClip接口
	private static AudioClip bgmAudio;
	private static AudioClip overAudio;
	private static AudioClip lifeAudio;
	
	//	播放游戏背景音乐
	public static void playBGM(){
		
		//	获取背景音乐文件的路径
		URL url = PlayMusic.class.getResource("../music/bgm.wav");
		
		//	创建声音剪辑对象
		bgmAudio = Applet.newAudioClip(url);
		
		//	播放声音即可
		bgmAudio.play();
	}
	
	//	播放界面背景音乐
	public static void playJM(){
		
		//	获取背景音乐文件的路径
		URL url = PlayMusic.class.getResource("../music/jm.wav");
		
		//	创建声音剪辑对象
		bgmAudio = Applet.newAudioClip(url);
		
		//	播放声音即可
		bgmAudio.play();
	}
	
	public static void stopBGM(){
		
		//	停止背景音乐
		bgmAudio.stop();
	}
	
	public static void stopJM(){
		
		//	停止背景音乐
		bgmAudio.stop();
	}
	
	//	播放结束音效
	public static void playGameOver(){
		
		//	获取音乐文件的路径
		URL url = PlayMusic.class.getResource("../music/js.wav");
		
		//	创建声音剪辑对象
		overAudio = Applet.newAudioClip(url);
		
		//	播放声音即可
		overAudio.play();
	}
	
	//	 播放消除棋子时的音效1
	public static void playEatBean1(){
		
		//	获取消除音效文件的路径
		URL url = PlayMusic.class.getResource("../music/1.wav");
		
		//	创建声音剪辑对象
		lifeAudio = Applet.newAudioClip(url);
			
		//	播放声音即可
		lifeAudio.play();
	}
	
	//	 播放消除棋子时的音效2
	public static void playEatBean2(){
		
		//	获取消除音效文件的路径
		URL url = PlayMusic.class.getResource("../music/2.wav");
		
		//	创建声音剪辑对象
		lifeAudio = Applet.newAudioClip(url);

		//	播放声音即可
		lifeAudio.play();
	}
}
