package Semicolon.project;

import java.awt.Color;
import java.awt.Graphics;

public class BodyPart{
	private int xColor, yColor, width, height;
	
	
	public BodyPart(int xColor, int yColor, int titleSize) {
		this.xColor = xColor;
		this.yColor = yColor;
		width = titleSize;
		height = titleSize;
	}

	public void draw(Graphics g) {
		g.setColor(Color.yellow);
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
