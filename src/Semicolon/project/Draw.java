package Semicolon.project;

import java.awt.Graphics;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Draw implements Serializable{
	
	private String path = "";
	private JPanel observerPanel;
	private Graphics g;
	
//	Draw 생성자
	public Draw() {
		
	}
	public Draw(Graphics g,JPanel observer,String imagePath) {
		this.path = imagePath;
		this.g = g;
		observerPanel = observer;
	}
	
//	그림을 그리는 함수
	public void drawImage(int x, int y,int width,int height) {
		
		ImageIcon imageIcon = new ImageIcon(path);			
		
		g.drawImage(imageIcon.getImage(),x,y,width,height,observerPanel);
	}
	
	public void drawString(String str,int x,int y) {
		g.drawString(str, x, y);
	}
//	getter/setter
	public void setObserver(JPanel observer) {
		observerPanel = observer;
	}
	public void setImagePath(String path) {
		this.path = path;
	}
	public void setG(Graphics g) {
		this.g = g;
	}
	
}
