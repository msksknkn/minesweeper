package window;

import javax.swing.*;
import javax.swing.event.*;

import field.Field;
import field.Mass;
import solveMinesweeper.Solve;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements MouseListener{
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	int height = 0, width = 0;//フィールドの大きさ　レベルに応じて変わる
	public static final int GRID_SIZE = 20;//マス目の大きさ
	private int numMine = 0;   //残りの爆弾の数
	JLabel mine = new JLabel("残り地雷" + numMine);//残りの爆弾の数を表示する
	//マインスイーパのフィールド
	Field field = new Field();
	//フィールドに対してこの逆数個だけ地雷がある.
	//例えば6なら1/6＊（width * height）
	private int decideMineNum = 6;
	private boolean gameEndFlag = false;
	private boolean firstClick = true;
	private boolean easyMode = false;
	private Solve solver;
	
	public GamePanel() {
		setBackground(new Color(100,100,100));
		setBounds(0,0,100,100);
		setLayout(null);
		setPreferredSize(new Dimension(600,320));
		this.mine.setBackground(Color.WHITE);
		this.mine.setForeground(Color.GREEN);
		add(this.mine);
		addMouseListener(this);
		setVisible(false);
	}
	public void startGame(int level,boolean yutoriMode) {
		//レベルに応じてウィンドウサイズを調整してゲームを始める
		//横:width,縦:height
		if(level == 1) {
			this.width = 9;this.height = 9;
			setBounds(0,0,width * GRID_SIZE ,height * GRID_SIZE);
			
		}
		else if(level == 2) {
			this.width = 16;this.height = 16;
			setBounds(0,0,width * GRID_SIZE ,height * GRID_SIZE);
		}
		else if(level == 3) {
			this.width = 30; this.height = 16;
			setBounds(0,0,width * GRID_SIZE ,height * GRID_SIZE);
		}
		setPreferredSize(new Dimension(GRID_SIZE * this.width +1,GRID_SIZE * (this.height +100)));//パネルのサイズを画面サイズに合わせる
		numMine = this.width * this.height/this.decideMineNum;//爆弾の数はマス目の1/decideMineNum個にする.
		Mass[][] mass = new Mass[width + 2][height + 2];//マス目の外に描画されないマスを用意する。周囲のマスの地雷を調べやすくする
		this.field.setField(mass);
		this.field.syokikaField(this.width, this.height);
		this.field.setMine(this.numMine, this.width, this.height);
		field.checkMineNum(this.width, this.height);
		//ラベルの表示場所設定
		this.mine.setBounds(width * GRID_SIZE/4,height * GRID_SIZE -20 ,150,100);
		this.mine.setPreferredSize(new Dimension(width * GRID_SIZE ,50));
		//solverの設定
		this.solver = new Solve(width,height);
		if(yutoriMode == true) this.easyMode = true;
		setVisible(true);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//終了した場合それ以上描画しない
		//描画はfield[1][1]~field[width][height]の範囲で行う。外側に見えないマスがある
				int flagNum = 0;
				for(int i = 1; i < this.width + 1; i++) {
					for(int j = 1; j < this.height + 1; j++) {
						
						if(this.field.getField()[i][j].getOpen() == false && 
								this.field.getField()[i][j].isFlag() == true) {
							g.setColor(Color.GREEN);
							g.fillRect((i - 1) * GRID_SIZE ,(j - 1) * GRID_SIZE,GRID_SIZE ,GRID_SIZE);
							g.setColor(Color.WHITE);
							g.drawRect((i - 1) * GRID_SIZE ,(j - 1) * GRID_SIZE,GRID_SIZE ,GRID_SIZE);
							//旗を建てられたマスを描く
							flagNum++;
					}
						else if(this.field.getField()[i][j].getOpen() == false) {
							g.setColor(Color.WHITE);
							g.drawRect((i - 1) * GRID_SIZE ,(j - 1) * GRID_SIZE,GRID_SIZE ,GRID_SIZE);
							//開けられていないマスを描く
					}
						else if(this.field.getField()[i][j].getOpen() == true) {//空いているマスの描画
							if(this.field.getField()[i][j].getIsMine() == true) {//地雷判定、地雷なら赤で書く
								g.setColor(Color.RED);
								g.fillRect((i - 1) * GRID_SIZE ,(j - 1) * GRID_SIZE,GRID_SIZE ,GRID_SIZE);//地雷のマスを描く
							}else {
								g.setColor(Color.WHITE);
								g.fillRect((i - 1) * GRID_SIZE ,(j - 1) * GRID_SIZE,GRID_SIZE ,GRID_SIZE);//地雷でないマスを描く
							}if(this.field.getField()[i][j].getMineNum() != 0 ) {
								g.setColor(Color.BLACK);
								g.drawString(Integer.toString(this.field.getField()[i][j].getMineNum()) ,(i - 1) *GRID_SIZE ,j * GRID_SIZE);
							}
						}
					}
				}
				
		if(gameEndFlag == true) return;
		this.mine.setText("残り地雷" + (this.numMine - flagNum)+ "　　緑:旗");
		//イージーモードなら実行。ある程度勝手に開いてくれる
		if(easyMode == true) {
			this.solver.checkProb(this.field, this.width, this.height);
		}
		for(int i = 1; i < this.width + 1 ; i++) {
			for(int j = 1; j < this.height + 1; j++) {
				if(this.field.getField()[i][j].getOpen() == true && 
						this.field.getField()[i][j].getMineNum() == 0	) {
							field.openAroundZero(i,j);//0の周りを開ける
				}
			}
		}
		if(firstClick == false){
			checkGameEnd();
		}
		
		
		repaint();
	}
	public void checkGameEnd() {
		//失敗したときのゲーム終了処理
				
				//爆弾以外をすべて開けたときのゲーム終了処
				int count = 0;
				for(int i = 1; i < this.width + 1 ; i++) {
					for(int j = 1; j < this.height + 1; j++) {
						if(this.field.getField()[i][j].getOpen() == true && 
								this.field.getField()[i][j].getIsMine() == false) {
									count++;
						}
					}
				}
				if(count == width * height - this.numMine) {
					this.gameEndFlag = true;
					this.mine.setText("ゲームクリア");
				}
				//爆弾を開けたときのゲーム終了処理
				label:
					for(int i = 1; i < this.width + 1 ; i++) {
						for(int j = 1; j < this.height + 1; j++) {
							if(this.field.getField()[i][j].getOpen() == true && 
									this.field.getField()[i][j].getIsMine() == true) {
										this.field.allOpen(this.width,this.height);
										this.gameEndFlag = true;
										this.mine.setText("ゲームオーバー");
										break label;
							}
						}
					}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタ
		//クリックされた
		
		Point p = e.getPoint();
		int massX = (p.x/GRID_SIZE) + 1 ;
		int massY = (p.y/GRID_SIZE) + 1;
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			System.out.println("x" + p.x + "y" + p.y + "massx" + massX + "massY" + massY);
			if(firstClick == true) {
				//最初のクリックで地雷を踏まないようにする
				this.field.firstOpen(massX,massY,numMine,this.width,this.height);
				firstClick = false;
			}
			if(this.field.getField()[massX][massY].isFlag() != true) {
				if(this.field.getField()[massX][massY].getOpen() == false) {//空いていないマスを指定したとき開ける処理
					this.field.getField()[massX][massY].setOpen(true);
					}
			}
			break;
		case MouseEvent.BUTTON3:
			//フラッグを立てる処理、消す処理
			if(this.field.getField()[massX][massY].isFlag() == false) {
				this.field.getField()[massX][massY].setFlag(true);
				
			}
			else if(this.field.getField()[massX][massY].isFlag() == true) {
				this.field.getField()[massX][massY].setFlag(false);
			break;
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		//上にマウスが乗ったとき
		
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		//マウスが離れた
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		//マウスが押された
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		//マウスが離された
	}
}
	
