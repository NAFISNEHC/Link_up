package visual;

public class GameStatus {

	//	棋子状态量
    private int status;

    //	初始化棋子状态量
    public GameStatus(int status) {
        this.status = status;
    }

    //	获得当前棋子状态量
    public int getStatus() {
        return status;
    }

    //	设置棋子状态量
    public void setStatus(int status) {
        this.status = status;
    }

}