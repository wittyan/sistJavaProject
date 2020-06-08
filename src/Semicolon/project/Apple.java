package Semicolon.project;

import java.awt.Color;
import java.awt.Graphics;

public class Apple{
	private int xColor, yColor, width, height;
	private int money;
	
	public Apple(int xColor, int yColor, int titleSize) {
		this.xColor = xColor;
		this.yColor = yColor;
		width = titleSize;
		height = titleSize;
	}
		
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(xColor * width, yColor * height, width, height);
	}
	public int getxColor() {
		return xColor;
	}
	public void setxColor(int xColor) {
		this.xColor = xColor;
	}
	public int getyColor() {
		return yColor;
	}
	public void setyColor(int yColor) {
		this.yColor = yColor;
	}

	
}
