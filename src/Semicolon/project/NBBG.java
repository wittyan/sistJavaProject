package Semicolon.project;

import java.util.Arrays;

public class NBBG {
	
	int A[]=new int[3];//랜덤의 3자리 숫자
	int B[]=new int[3];//입력할 3자리 숫자
	
	public void Compare(int a[],int b[]) {
		int Strike=0,Ball=0;
		
		for(int i=0;i<B.length;i++) {
			for(int j=0;j<A.length;j++) {
				if(B[i]==A[j]) {//일치하는 숫자비교
					if(i==j)//자릿수가 같으면 Strike 아니면 Ball 1증가
						Strike++;
					else
						Ball++;
					continue;
				}
			}
		}
		
		if(Strike==0 && Ball==0) {
			System.out.println("아웃!");
		}else {
			if(Strike!=0) 
				System.out.println(Strike+"스트라이크!");
			if(Ball!=0)
				System.out.println(Ball+"볼!");
			if(Strike==3) 
				System.out.println("정답!");
		}
	}
	
	public void GameSet() {
		for(int i=0;i<A.length;i++) {
			A[i]=(int)(Math.random()*(9+1));//0~9까지의 랜덤숫자 생성
			if((i==0&&A[i]==0)) {//첫번째 자리가 0일때 다시 정함
				i--;
			}
			for(int j=0;j<i;j++) {//중복된 숫자가 있을 경우 다시 정함
				if(A[j]==A[i]) {
					i--;
				}
			}
		}

		for(int i=0;i<B.length;i++) {
			B[i]=(int)(Math.random()*(9+1));//0~9까지의 랜덤숫자 생성
			if((i==0&&B[i]==0)) {//첫번째 자리가 0일때 다시 정함
				i--;
			}
			for(int j=0;j<i;j++) {//중복된 숫자가 있을 경우 다시 정함
				if(B[j]==B[i]) {
					i--;
				}
			}
		}
		
		System.out.println(Arrays.toString(A));
		System.out.println(Arrays.toString(B));
		
		Compare(A,B);
	}
	
	public void GameMain() {
		GameSet();
	}
	
	public NBBG() {
		GameMain();
	}
	
	public static void main(String[] args) {
		new NBBG();
	}
}