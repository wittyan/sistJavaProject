package Semicolon.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GGamePanel extends JPanel implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 500, HEIGHT = 500;

	private static final int SPEED = 600000;
	
	private GMain g;
	private boolean running;
	private Thread thread;
	private boolean right = true, left = false, up = false, down = false;

	private BodyPart b;
	private ArrayList<BodyPart> snake;
	
	private Apple apple;
	private ArrayList<Apple>apples;
	
	private Random r;

	private int xColor = 10, yColor = 10, size = 5;
	private int ticks = 0;
	
	private int game_Score;
	
	private Mal mal;

	public GGamePanel(GMain g, Mal mal) {
		this.g = g;
		this.mal = mal;
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		addKeyListener(this);
		
		snake = new ArrayList<BodyPart>();
		apples = new ArrayList<Apple>();
				
		r = new Random();

		start();
		
	}
	public void start() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void stop() {
		running = false;
		mal.setMoney(mal.getMoney()+score());
		g.frame.dispose();
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	

	public void tick() {
		if (snake.size() == 0) {
			b = new BodyPart(xColor, yColor, 10);
			snake.add(b);
		}
		ticks++;
		
		// 뱀의 이동 속도 
		if (ticks > SPEED) {
			if (right)
				xColor++;
			if (left)
				xColor--;
			if (up)
				yColor--;
			if (down)
				yColor++;

			ticks = 0;

			b = new BodyPart(xColor, yColor, 10);
			snake.add(b);
			if (snake.size() > size) {
				snake.remove(0);
			}

		}
		
		// 사과의 위치 랜덤으로 표시
		if(apples.size() == 0) {
			int xColor = r.nextInt(49);
			int yColor = r.nextInt(49);
			
			apple = new Apple(xColor, yColor, 10);
			apples.add(apple);
		}
		
		// 사과를 먹을 때 마다 몸의 길이가 1씩 증가 
		for (int i = 0; i < apples.size(); i++) {
			if(xColor == apples.get(i).getxColor() && yColor == apples.get(i).getyColor()) {
				size++;
				apples.remove(0);
				i++;
				check();
			}
		}
		
		
		// snake 가 자기 몸에 닿으면 종료 
		for (int i = 0; i < snake.size(); i++) {
			if(xColor == snake.get(i).getxColor() && yColor == snake.get(i).getyColor()) {
				if(i != snake.size() - 1) {
				JOptionPane.showMessageDialog(null, "Game Over");		
				stop();
				}
			}
		}
		
		// 윈도우 벽면에 닿으면 종료
		if(xColor < 0 || xColor > 49 || yColor < 0 || yColor > 49) {
			JOptionPane.showMessageDialog(null, "Game Over");
			stop();
		}
	}	
	public int score() {
		JOptionPane.showMessageDialog(null, game_Score+"원 획득하셨습니다.");
		return game_Score;
	}
	public void check() {
		game_Score += 100/5;		
	}
	
	public int getGame_Score() {
		return game_Score;
	}
	
	public void paint(Graphics g) {

		g.clearRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		for (int i = 0; i < WIDTH / 10; i++) {
			g.drawLine(i * 10, 0, i * 10, HEIGHT);
		}
		for (int i = 0; i < HEIGHT / 10; i++) {
			g.drawLine(0, i * 10, WIDTH, i * 10);
		}
		for (int i = 0; i < snake.size(); i++) {
			snake.get(i).draw(g);
		}
		for (int i = 0; i < apples.size(); i++) {
			apples.get(i).draw(g);
		}
		g.drawString("SCORE : " + game_Score, 400, 20);
		
	}
	@Override
	public void run() {
		while (running) {
			tick();
			repaint();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT && !left) {
			right= true;
			up = false;
			down = false;
		}
		if(key == KeyEvent.VK_LEFT && !right) {
			left= true;
			up = false;
			down = false;
		}
		if(key == KeyEvent.VK_UP && !down) {
			up= true;
			left = false;
			right = false;
		}
		if(key == KeyEvent.VK_DOWN && !up) {
			down= true;
			left = false;
			right = false;
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
