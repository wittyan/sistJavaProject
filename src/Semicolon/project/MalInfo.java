package Semicolon.project;

import java.io.Serializable;

public class MalInfo implements Serializable{
	
//	Player의 위치와 돈정보
	int position[];
	int money[];
	
	public MalInfo() {
		super();
	}

	public MalInfo(int[] position, int[] money) {
		super();
		this.position = position;
		this.money = money;
	}
	
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		this.position = position;
	}
	public int[] getMoney() {
		return money;
	}
	public void setMoney(int[] money) {
		this.money = money;
	}
	

	
}
