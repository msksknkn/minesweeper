package field;

import java.util.*;

public class Field {
	private Mass[][] field;

	public Mass[][] getField() {
		return field;
	}

	public void setField(Mass[][] field) {
		this.field = field;
	}
	//フィールドを完全に何もない、空いていないマスだけにする
	public void syokikaField(int width,int height) {
		//System.out.println("デバッグ");
		for(int i = 0; i < width + 2; i++) {
			for(int j = 0; j < height + 2; j++) {
				this.field[i][j] = new Mass();
				this.field[i][j].setIsMine(false);
				if(i == 0 || j == 0 || i == width + 1 || j == height + 1) {
					//周囲を囲むようにある見えないマスの処理。あらかじめ開いており、勝手に開かない
					this.field[i][j].setOpen(true);
					this.field[i][j].setMineNum(-1);
				}
				else {
					this.field[i][j].setOpen(false);
					this.field[i][j].setMineNum(0);
				}
			}
		}
	}
	//地雷を埋めるメソッド。表示されないマスをのぞいた場所に爆弾を置く
	//numMineは地雷の総数
	public void setMine(int numMine,int width, int height) {
		ArrayList<Integer> ransuX = new ArrayList<Integer>(); 
		for(int i = 0;i < width; i++) {//すべてのXをリストに入れる
			ransuX.add(i);
		}
		ArrayList<Integer> ransuY = new ArrayList<Integer>(); 
		for(int i = 0;i < height; i++) {//すべてのYをリストに入れる
			ransuY.add(i);
		}
		int count = 0;
		while(count < numMine){
			Collections.shuffle(ransuX);
			Collections.shuffle(ransuY);
			System.out.println("乱数x" + ransuX + "乱数Y" +ransuY );
			if(this.field[ransuX.get(0) + 1][ransuY.get(0) + 1].getIsMine() != true) {
				this.field[ransuX.get(0) + 1][ransuY.get(0) + 1].setIsMine(true);
				count++;
			}
		}
	}
	//周囲の地雷の数を調べるメソッド。端だけは周囲の数が減るため要注意
	public void checkMineNum(int width,int height) {
		//表示されないマスを周りに囲むよう作ったため、周囲が減る端っこも他のマスのように計算可能
		for(int i = 1; i < width + 1; i++) {
			for(int j = 1; j < height + 1; j++) {
					if(this.field[i + 1][j].getIsMine() == true) 	this.field[i][j].plusMineNum();//右隣
					if(this.field[i - 1][j].getIsMine() == true) 	this.field[i][j].plusMineNum();//左隣
					if(this.field[i][j + 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//上
					if(this.field[i][j - 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//下
					if(this.field[i + 1][j + 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//右上
					if(this.field[i - 1][j + 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//左上
					if(this.field[i + 1][j - 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//右下
					if(this.field[i - 1][j - 1].getIsMine() == true) 	this.field[i][j].plusMineNum();//左下
			}		
		}
	}	
	//周りに何もないことがはっきりしている地雷0のまわりをすべて開ける
	public void openAroundZero(int massX,int massY) {
		//うんちみたいなコード。まわり８箇所に同じ処理してるだけです。
		this.field[massX + 1][massY].setOpen(true);//右
		
		this.field[massX - 1][massY].setOpen(true);//左
		
		this.field[massX][massY + 1].setOpen(true);//下
		
		this.field[massX][massY - 1].setOpen(true);//上
	
		this.field[massX + 1][massY + 1].setOpen(true);//右下	
		
		this.field[massX - 1][massY + 1].setOpen(true);//左下
		
		this.field[massX + 1][massY - 1].setOpen(true);//右上
		
		this.field[massX - 1][massY -1].setOpen(true);//左下
		/*if(this.field[massX + 1][massY].getMineNum() == 0) { 
			this.openAroundZero(massX + 1,massY);
		}
		if(this.field[massX - 1][massY].getMineNum() == 0) {
			this.openAroundZero(massX - 1,massY);
		}
		if(this.field[massX][massY + 1].getMineNum() == 0) {
			this.openAroundZero(massX ,massY + 1);
		}
		if(this.field[massX][massY - 1].getMineNum() == 0) {
			this.openAroundZero(massX ,massY - 1);
		}
		if(this.field[massX + 1][massY + 1].getMineNum() == 0) {
			this.openAroundZero(massX + 1,massY + 1);
		}
		if(this.field[massX - 1][massY + 1].getMineNum() == 0) {
			this.openAroundZero(massX - 1,massY + 1);
		}
		if(this.field[massX + 1][massY -1].getMineNum() == 0) {
			this.openAroundZero(massX + 1,massY - 1);
		}
		if(this.field[massX - 1][massY - 1].getMineNum() == 0) {
			this.openAroundZero(massX - 1,massY - 1);*/
	}
	public void allOpen(int width,int height) {
		for(int i = 1; i < width + 1; i++) {
			for(int j = 1; j < height + 1;j++) {
				this.field[i][j].setOpen(true);
			}
		}
	}
	//最初のクリックで爆発しないようにする処理
	public void firstOpen(int massX,int massY,int numMine,int width,int height) {
		while(this.field[massX][massY].getMineNum() != 0) {
			syokikaField(width,height);
			setMine(numMine, width, height);
			checkMineNum(width,height);
		}
	}
}
