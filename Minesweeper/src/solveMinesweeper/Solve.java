package solveMinesweeper;

import field.Field;

public class Solve {
	private int[][] prob;
	//コンストラクタ。フィールドのサイズに合わせて確率の配列を作る
	public Solve(int width,int height) {
		prob = new int[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				prob[i][j] = 0;
			}
		}
	}
	public int[][] getProb(){
		return this.prob;
	}
	public void setProb(int[][] prob) {
		this.prob = prob;
	}
	//マスごとの確率を調べる
	public void checkProb(Field field,int width,int height){
		int count = 0;
		for(int i = 1; i < width + 1; i++) {
			for(int j = 1; j < height + 1; j++) {
				if(field.getField()[i][j].getOpen() == true) {
					if(field.getField()[i + 1][j].getOpen() == false)count++;//右
					
					if(field.getField()[i - 1][j].getOpen()  == false)count++;//左
				
					if(field.getField()[i][j + 1].getOpen()  == false)count++;//下
					
					if(field.getField()[i][j - 1].getOpen()  == false)count++;//上
				
					if(field.getField()[i + 1][j + 1].getOpen()  == false)count++;//右下	
					
					if(field.getField()[i - 1][j + 1].getOpen()  == false)count++;//左下
					
					if(field.getField()[i + 1][j - 1].getOpen()  == false)count++;//右上
					
					if(field.getField()[i - 1][j -1].getOpen()  == false)count++;//左下
			
					//もし開いていない箇所と爆弾の数が同じなら旗を立てる
					if(count == field.getField()[i][j].getMineNum()) {
						if(field.getField()[i + 1][j].getOpen() == false)
							field.getField()[i + 1][j].setFlag(true);//右
						if(field.getField()[i - 1][j].getOpen()  == false)
							field.getField()[i - 1][j].setFlag(true);//左
						if(field.getField()[i][j + 1].getOpen()  == false)
							field.getField()[i][j + 1].setFlag(true);//下
						if(field.getField()[i][j - 1].getOpen()  == false)
							field.getField()[i][j - 1].setFlag(true);//上
						if(field.getField()[i + 1][j + 1].getOpen()  == false)
							field.getField()[i + 1][j + 1].setFlag(true);//右下	
						if(field.getField()[i - 1][j + 1].getOpen()  == false)
							field.getField()[i - 1][j + 1].setFlag(true);//左下
						if(field.getField()[i + 1][j - 1].getOpen()  == false)
							field.getField()[i + 1][j - 1].setFlag(true);//右上
						if(field.getField()[i - 1][j -1].getOpen()  == false)
							field.getField()[i - 1][j - 1].setFlag(true);
					}
					count = 0;
				//もし爆弾の数と旗の数が同じなら周りを開ける
				}if(field.getField()[i][j].getOpen() == true) {
						if(field.getField()[i + 1][j].isFlag() == true)count++;//右
						if(field.getField()[i - 1][j].isFlag()  == true)count++;//左
						if(field.getField()[i][j + 1].isFlag()  == true)count++;//下
						if(field.getField()[i][j - 1].isFlag()  == true)count++;//上
						if(field.getField()[i + 1][j + 1].isFlag()  == true)count++;//右下	
						if(field.getField()[i - 1][j + 1].isFlag()  == true)count++;//左下
						if(field.getField()[i + 1][j - 1].isFlag()  == true)count++;//右上
						if(field.getField()[i - 1][j -1].isFlag()  == true)count++;//左下
						
						if(field.getField()[i][j].getMineNum() == count) {
							if(field.getField()[i + 1][j].isFlag() == false)
								field.getField()[i + 1][j].setOpen(true);//右
							if(field.getField()[i - 1][j].isFlag()  == false)
								field.getField()[i - 1][j].setOpen(true);//左
							if(field.getField()[i][j + 1].isFlag()  == false)
								field.getField()[i][j + 1].setOpen(true);//下
							if(field.getField()[i][j - 1].isFlag()  == false)
								field.getField()[i][j - 1].setOpen(true);//上
							if(field.getField()[i + 1][j + 1].isFlag()  == false)
								field.getField()[i + 1][j + 1].setOpen(true);//右下	
							if(field.getField()[i - 1][j + 1].isFlag()  == false)
								field.getField()[i - 1][j + 1].setOpen(true);//左下
							if(field.getField()[i + 1][j - 1].isFlag()  == false)
								field.getField()[i + 1][j - 1].setOpen(true);//右上
							if(field.getField()[i - 1][j -1].isFlag()  == false)
								field.getField()[i - 1][j - 1].setOpen(true);
							count = 0;
					}
				}
			}
		}
	}
}
