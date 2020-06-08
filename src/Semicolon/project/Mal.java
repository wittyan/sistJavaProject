package Semicolon.project;

import java.awt.Graphics;
import java.io.Serializable;

public class Mal implements Serializable {
	
	String team;
	int position;
	int money;
	
	transient Draw draw;
	
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public Mal() {
		position = 0;
		money=0;
		draw = new Draw();
	}
	public Mal(String team,int position){
		this.team = team;
		this.position = position;
		draw = new Draw();
	}
	
	
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public synchronized void draw(Graphics g) {
		int i = 0;
		draw.setG(g);
		if(team.equals("blue")) {
			i=0;
				draw.setImagePath("D:\\blueMal.png");
		}else if(team.equals("red")) {
			i=1;
				draw.setImagePath("D:\\redMal.png");
		}
		if(position!=0) {	
			int x = 0;
			int y = 0;
			int malwidth = 35;
			int malheight =35;
			if(i==0) {
				switch (position) {
					case 1:
						x = 850;
						y = 625;
						break;
					case 2:
						x = 850;
						y = 565;
						break;
					case 3:
						x = 850;
						y = 505;
						break;
					case 4:
						x = 850;
						y = 440;
						break;
					case 5:
						x = 850;
						y = 380;
						break;
					case 6:
						x = 850;
						y = 320;
						break;
					case 7:
						x = 850;
						y = 260;
						break;
					case 8:
						x = 850;
						y = 200;
						break;
					case 9:
						x = 850;
						y = 140;
						break;
					case 10:
						x = 880;
						y = 80;
						break;
					case 11:
						x = 775;
						y = 90;
						break;
					case 12:
						x = 695;
						y = 90;
						break;
					case 13:
						x = 615;
						y = 90;
						break;
					case 14:
						x = 535;
						y = 90;
						break;
					case 15:
						x = 465;
						y = 90;
						break;
					case 16:
						x = 385;
						y = 90;
						break;
					case 17:
						x = 315;
						y = 90;
						break;
					case 18:
						x = 238;
						y = 90;
						break;
					case 19:
						x = 163;
						y = 90;
						break;
					case 20:
						x = 110;
						y = 80;
						break;
					case 21:
						x = 50;
						y = 135;
						break;
					case 22:
						x = 50;
						y = 200;
						break;
					case 23:
						x = 50;
						y = 265;
						break;
					case 24:
						x = 50;
						y = 330;
						break;
					case 25:
						x = 50;
						y = 395;
						break;
					case 26:
						x = 50;
						y = 450;
						break;
					case 27:
						x = 50;
						y = 510;
						break;
					case 28:
						x = 50;
						y = 580;
						break;
					case 29:
						x = 50;
						y = 635;
						break;
					case 30:
						x = 100;
						y = 700;
						break;
					case 31:
						x = 200;
						y = 720;
						break;
					case 32:
						x = 275;
						y = 720;
						break;
					case 33:
						x = 350;
						y = 720;
						break;
					case 34:
						x = 425;
						y = 720;
						break;
					case 35:
						x = 500;
						y = 720;
						break;
					case 36:
						x = 575;
						y = 720;
						break;
					case 37:
						x = 650;
						y = 720;
						break;
					case 38:
						x = 725;
						y = 720;
						break;
					case 39:
						x = 800;
						y = 720;
						break;
					case 40:
						x = 875;
						y = 690;
					default:
						break;
					}
				draw.drawImage(x, y, malwidth, malheight);
		
			}
			else if(i==1) {
				switch (position) {
					case 1:
						x = 880;
						y = 625;
						break;
					case 2:
						x = 880;
						y = 565;
						break;
					case 3:
						x = 880;
						y = 505;
						break;
					case 4:
						x = 880;
						y = 440;
						break;
					case 5:
						x = 880;
						y = 380;
						break;
					case 6:
						x = 880;
						y = 320;
						break;
					case 7:
						x = 880;
						y = 260;
						break;
					case 8:
						x = 880;
						y = 200;
						break;
					case 9:
						x = 880;
						y = 140;
						break;
					case 10:
						x = 880;
						y = 50;
						break;
					case 11:
						x = 775;
						y = 60;
						break;
					case 12:
						x = 695;
						y = 60;
						break;
					case 13:
						x = 615;
						y = 60;
						break;
					case 14:
						x = 535;
						y = 60;
						break;
					case 15:
						x = 465;
						y = 60;
						break;
					case 16:
						x = 385;
						y = 60;
						break;
					case 17:
						x = 315;
						y = 60;
						break;
					case 18:
						x = 238;
						y = 60;
						break;
					case 19:
						x = 163;
						y = 60;
						break;
					case 20:
						x = 80;
						y = 80;
						break;
					case 21:
						x = 20;
						y = 135;
						break;
					case 22:
						x = 20;
						y = 200;
						break;
					case 23:
						x = 20;
						y = 265;
						break;
					case 24:
						x = 20;
						y = 330;
						break;
					case 25:
						x = 20;
						y = 395;
						break;
					case 26:
						x = 20;
						y = 450;
						break;
					case 27:
						x = 20;
						y = 510;
						break;
					case 28:
						x = 20;
						y = 580;
						break;
					case 29:
						x = 20;
						y = 635;
						break;
					case 30:
						x = 60;
						y = 700;
						break;
					case 31:
						x = 200;
						y = 690;
						break;
					case 32:
						x = 275;
						y = 690;
						break;
					case 33:
						x = 350;
						y = 690;
						break;
					case 34:
						x = 425;
						y = 690;
						break;
					case 35:
						x = 500;
						y = 690;
						break;
					case 36:
						x = 575;
						y = 690;
						break;
					case 37:
						x = 650;
						y = 690;
						break;
					case 38:
						x = 725;
						y = 690;
						break;
					case 39:
						x = 800;
						y = 690;
						break;
					case 40:
						x = 875;
						y = 720;
					default:
						break;
					}
				draw.drawImage(x, y, malwidth, malheight);
			}
		 
	}
}
}

	

