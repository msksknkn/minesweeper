package field;

public class Mass {
	private boolean open;//すでに開けられているか、閉じられているかなど表示に関わる。余裕があればフラッグ,？なども実装する
	private boolean isMine;//地雷かどうかを示す。地雷かつopenで調べたときに空いているマスがあればゲームオーバー
	private int mineNum;//周囲8マスの地雷の個数を示す、0の場合何も表示せず、地雷でない場合数字を表示させるための変数
	private boolean flag;
	public boolean getOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean getIsMine() {
		return isMine;
	}
	public void setIsMine(boolean isMine) {
		this.isMine = isMine;
	}
	public int getMineNum() {
		return mineNum;
	}
	public void setMineNum(int mineNum) {
		this.mineNum = mineNum;
	}
	public void plusMineNum() {
		this.mineNum++;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
