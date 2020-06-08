package Semicolon.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
//	GamePan의 크기
	private static final int WIDTH = 1000, HEIGHT = 800;

//	게임패널 네트워크 통신
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
//	주사위
	int diceNumber1 = -1;
	int diceNumber2 = -1;
	
//	게임차례 -> 주사위 굴리기 활성화
	boolean gameEnd = false;
	boolean secondTurn = false;
	
//	말 
	Mal mal[];
//	자신의 말번호
	int myMalNumber;
	
//	유저정보
	JButton throwButton = new JButton("던지기");
	JButton startButton = new JButton("게임시작");
	
//	JButton game1 = new JButton("가위바위보게임 실행");
//	JButton game2 = new JButton("지렁이게임 실행");
//	JButton game3 = new JButton("숫자야구게임 실행");
//	JButton game4 = new JButton("스도쿠게임 실행");

//	JLabel 
//	주사위 라벨
	JLabel diceNumberLabel = new JLabel("");
//  현재 턴인 플레이어
	JLabel turnPlayerLabel = new JLabel("");
//	게임진행상황
	JLabel playStatusLabel1 = new JLabel("");
	JLabel playStatusLabel2 = new JLabel("");
//	자신의 팀
	JLabel teamLabel= new JLabel("");
	

	GameManager gameManager;
	
	Main main;
	
	public int randomInt() {
		int random = (int)(Math.random()*6)+1; // 1~6
		return random;			
	}
	
	public void diceThrow(int inputNumber1,int inputNumber2) {
		
		diceNumber1 = 0;
		diceNumber2 = 0;
		repaint();
		try {
		Thread.sleep(1000);
		}catch(Exception e) {
			
		}
		
		int resultNumber = inputNumber1+inputNumber2;
		diceNumberLabel.setText("나온 주사위 값 : "+inputNumber1+" + "+inputNumber2 + " = " +  resultNumber);
		diceNumber1 = inputNumber1;
		diceNumber2 = inputNumber2;
		repaint();
	}
	
	
	public void initMal() {
		mal = new Mal[2];
		for (int i = 0; i < mal.length; i++) {
			mal[i] = new Mal();
			mal[i].position=40;
			mal[i].money=500;
			
		}		
		mal[0].team = "blue";
		mal[1].team = "red";
	}
	
	BufferedImage img = null;
	public void initbackground() {
		
			try {
				img = ImageIO.read(new File("D:\\background.jpg"));
			}catch(IOException e) {
				JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
				System.exit(0);
			}
			
	}
		
	public GamePanel(Main main) {
		this.main = main;
		socket = main.getNetwork().socket;
		oos = main.getNetwork().getOos();
		ois = main.getNetwork().getOis();
		
		initMal();
		setPanelSize();
		initbackground();
		initPlayerLabel();
		this.setBackground(Color.BLACK);

		gameManager= new GameManager(main);
	 
	}
	
	JLabel player1Label;
	JLabel player2Label;
	
	public void initPlayerLabel() {
//		player의 정보를 담는 JLabel
		
		player1Label = new JLabel();
		player1Label.setBounds(450, 600, 500, 500);
		player1Label.setForeground(Color.WHITE);
		player1Label.setFont(new Font("휴먼매직체",Font.BOLD,20));

		this.add(player1Label);
		
		player2Label = new JLabel();
		player2Label.setBounds(850, 600, 500, 500);
		player2Label.setForeground(Color.WHITE);
		player2Label.setFont(new Font("휴먼매직체",Font.BOLD,20));

		this.add(player2Label);
		
		updatePlayerLabel();
	}
	
	public synchronized void updatePlayerLabel() {
//		player1, player2의 보유현금을 String으로 조합하여 출력한다
		
		String str = "";		
		str = "<HTML>[Blue]<BR>";
		if(mal[0]!=null) {
		str+="보유현금 : " + mal[0].getMoney() +"<HTML>";
		}else{
			str += "패배한 유저입니다.<HTML>";
		}
		player1Label.setText(str);
		
		str = "<HTML>[Red]<BR>";
		if(mal[1]!=null) {
		str+="보유현금 : " + mal[1].getMoney() +"<HTML>";
		}else {
			str += "패배한 유저입니다.<HTML>";
		}
		player2Label.setText(str);
		
		if(mal[myMalNumber].getMoney()<=0 && !gameEnd) {
			gameEnd = true;
			try {
				main.getNetwork().getOos().writeObject("[Out]"+myMalNumber);
			}catch(Exception e) {
			
			}
		}
	}
	
	
	public void setPanelSize() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)d.getWidth();
		int height = (int)d.getHeight()/15*14; //작업표시줄 제외
		
		width = width/10 * 7;
		
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		throwButton.setBounds(1120, 180, 100, 40);
		throwButton.setEnabled(false);
		throwButton.addActionListener(this);
		this.add(throwButton);
		
		
		startButton.setBounds(1120,900,100,40);
		startButton.addActionListener(this);
		startButton.setEnabled(true);
		this.add(startButton);
		
		diceNumberLabel.setBounds(1080,250,300,40);
		diceNumberLabel.setForeground(Color.WHITE);
		diceNumberLabel.setFont(new Font("휴먼매직체",Font.BOLD,20));
		this.add(diceNumberLabel);
		
		turnPlayerLabel.setBounds(1120,300,300,40);
		turnPlayerLabel.setForeground(Color.WHITE);
		turnPlayerLabel.setFont(new Font("휴먼매직체",Font.BOLD,20));
		this.add(turnPlayerLabel);
	
		teamLabel.setBounds(1120,400,300,40);
		teamLabel.setForeground(Color.white);
		teamLabel.setFont(new Font("휴먼매직체",Font.BOLD,20));
		this.add(teamLabel);
		
		playStatusLabel1.setBounds(300,0,500,500);
		playStatusLabel1.setForeground(Color.WHITE);
		playStatusLabel1.setFont(new Font("휴먼매직체",Font.BOLD,20));
		this.add(playStatusLabel1);
		
		playStatusLabel2.setBounds(320,40,500,500);
		playStatusLabel2.setForeground(Color.WHITE);
		playStatusLabel2.setFont(new Font("휴먼매직체",Font.BOLD,20));
		this.add(playStatusLabel2);
		
		
//		game1.setBounds(1120,850,170,40);
//		game1.addActionListener(this);
//		this.add(game1);
//		game2.setBounds(1120,800,170,40);
//		game2.addActionListener(this);
//		this.add(game2);
//		game3.setBounds(1120,750,170,40);
//		game3.addActionListener(this);
//		this.add(game3);
//		game4.setBounds(1120,700,170,40);
//		game4.addActionListener(this);
//		this.add(game4);
	}
	
	
	public void drawGamePan(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawLine(WIDTH/ 13 * 2, 0, WIDTH / 13 * 2, HEIGHT);
		g.drawLine(WIDTH / 13 * 11, 0, WIDTH / 13 * 11, HEIGHT);
		g.drawLine(0, HEIGHT / 13 * 2, WIDTH, HEIGHT / 13 * 2);
		g.drawLine(0, HEIGHT / 13 * 11, WIDTH, HEIGHT / 13 * 11);

		g.drawLine(0, HEIGHT / 13 * 11+130, WIDTH, HEIGHT / 13 * 11+130); //	맨 밑줄
		
		for (int i = 3; i < 11; i++) {
			g.drawLine(WIDTH / 13 * i, 0, WIDTH / 13 * i, HEIGHT / 13 * 2);
			g.drawLine(WIDTH / 13 * i, HEIGHT / 13 * 11, WIDTH / 13 * i, HEIGHT);
			g.drawLine(0, HEIGHT / 13 * i, WIDTH / 13 * 2, HEIGHT / 13 * i);
			g.drawLine(WIDTH / 13 * 11, HEIGHT / 13 * i, WIDTH, HEIGHT / 13 * i);
			
			g.drawLine(WIDTH, 0, WIDTH, HEIGHT); // 맨 오른쪽 줄
		}		
		
	}
	
	
	public void drawMal(Graphics g){
		for (int i = 0; i < mal.length; i++) {
			if(mal[i]!=null) {
			mal[i].draw(g);
			}
		}
	}
	
	public void drawCityText(Graphics g) {
//		40개의 칸마다 도시의 이름을 그린다
		City city[] = gameManager.getCity();
		for (int i = 0; i < city.length; i++) {
			int x = 0,y=0;
			switch (i+1) {
			case 1:
				x = 880;
				y = 635;
				break;
			case 2:
				x = 880;
				y = 575;
				break;
			case 3:
				x = 880;
				y = 515;
				break;
			case 4:
				x = 880;
				y = 450;
				break;
			case 5:
				x = 880;
				y = 390;
				break;
			case 6:
				x = 850;
				y = 340;
				break;
			case 7:
				x = 880;
				y = 270;
				break;
			case 8:
				x = 880;
				y = 210;
				break;
			case 9:
				x = 880;
				y = 150;
				break;
			case 10:
				x = 873;
				y = 80;
				break;
			case 11:
				x = 763;
				y = 90;
				break;
			case 12:
				x = 691;
				y = 90;
				break;
			case 13:
				x = 611;
				y = 90;
				break;
			case 14:
				x = 535;
				y = 90;
				break;
			case 15:
				x = 461;
				y = 90;
				break;
			case 16:
				x = 381;
				y = 90;
				break;
			case 17:
				x = 307;
				y = 90;
				break;
			case 18:
				x = 230;
				y = 90;
				break;
			case 19:
				x = 159;
				y = 90;
				break;
			case 20:
				x = 20;
				y = 80;
				break;
			case 21:
				x = 50;
				y = 155;
				break;
			case 22:
				x = 50;
				y = 210;
				break;
			case 23:
				x = 50;
				y = 275;
				break;
			case 24:
				x = 50;
				y = 340;
				break;
			case 25:
				x = 50;
				y = 405;
				break;
			case 26:
				x = 50;
				y = 460;
				break;
			case 27:
				x = 50;
				y = 520;
				break;
			case 28:
				x = 50;
				y = 590;
				break;
			case 29:
				x = 50;
				y = 645;
				break;
			case 30:
				x = 30;
				y = 730;
				break;
			case 31:
				x = 165;
				y = 720;
				break;
			case 32:
				x = 240;
				y = 720;
				break;
			case 33:
				x = 315;
				y = 720;
				break;
			case 34:
				x = 382;
				y = 720;
				break;
			case 35:
				x = 465;
				y = 720;
				break;
			case 36:
				x = 540;
				y = 720;
				break;
			case 37:
				x = 610;
				y = 720;
				break;
			case 38:
				x = 690;
				y = 720;
				break;
			case 39:
				x = 765;
				y = 720;
				break;
			case 40:
				x = 855;
				y = 690;
			default:
				break;
			}
			g.setFont(new Font("함초롱바탕", Font.PLAIN, 18));
			
			if(i==5 || i==9 || i ==19 || i==29) {
//			게임의 이름은 yellow색으로 지정
				g.setColor(Color.yellow);
				g.setFont(new Font("휴먼매직체", Font.PLAIN, 21));
				
			}else {
//			도시의 이름은 white로 지정
				g.setColor(Color.white);
			}
			if(i==36) {
				g.drawString("아이", x, y);
				g.drawString("슬란드", x, y+20);
			}else {
			g.drawString(city[i].getCityName(),x,y);
			}
		}
	}
	public void drawDice(Graphics g) {
//		주사위 출력
		Draw draw = new Draw(g,this,"D:\\Dice.gif");
		
		if(diceNumber1==-1 || diceNumber2 == -1)
			return;
		
		if(diceNumber1==0 || diceNumber2==0) { // 0이면 굴리는중..
			draw.drawImage(1070, 0, 200, 220);
			return;
		}else {
			switch (diceNumber1) {
			case 1:
				draw.setImagePath("D:\\1.png");
				break;
			case 2:
				draw.setImagePath("D:\\2.png");
				break;
			case 3:
				draw.setImagePath("D:\\3.png");
				break;
			case 4:
				draw.setImagePath("D:\\4.png");
				break;
			case 5:
				draw.setImagePath("D:\\5.png");
				break;
			case 6:
				draw.setImagePath("D:\\6.png");
				break;
			default:
				break;
			}
			draw.drawImage(1070, 50, 100, 100);
			switch (diceNumber2) {
			case 1:
				draw.setImagePath("D:\\1.png");
				break;
			case 2:
				draw.setImagePath("D:\\2.png");
				break;
			case 3:
				draw.setImagePath("D:\\3.png");
				break;
			case 4:
				draw.setImagePath("D:\\4.png");
				break;
			case 5:
				draw.setImagePath("D:\\5.png");
				break;
			case 6:
				draw.setImagePath("D:\\6.png");
				break;
			default:
				break;
			}
			draw.drawImage(1170, 50, 100, 100);
		}
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		배경화면 draw
		g.drawImage(img, 0,0,WIDTH,HEIGHT,null);
		
		drawGamePan(g);
	    drawCity(g);
	    drawMal(g);
	    drawDice(g);
	    drawCityText(g);
	}
	
	public void drawCity(Graphics g) {
//		40개의 칸마다 지어진 건물들을 그린다
		City[] city = gameManager.getCity();
		String path = null;
		String fullpath = null;
		Draw draw = new Draw(g, this, "D:\\blueBuilding.png");
		
		for (int i = 0; i < city.length-1; i++) {
			Mal mal = city[i].getMal();
			if(mal==null)
				continue;
			String team = mal.getTeam();
			
			if(team.equals("blue")) {
				path = "D:\\blue";
			}else if(team.equals("red")) {
				path = "D:\\red";				
			}
			boolean land = city[i].land;
			boolean villa = city[i].villa;
			boolean building = city[i].building;
			boolean hotel = city[i].hotel;
			
			
			int x=0,y=0;
			switch (i+1) {
			case 1:
				x = 950;
				y = 625;
				break;
			case 2:
				x = 950;
				y = 565;
				break;
			case 3:
				x = 950;
				y = 505;
				break;
			case 4:
				x = 950;
				y = 440;
				break;
			case 5:
				x = 950;
				y = 380;
				break;
			case 6:
				x = 950;
				y = 320;
				break;
			case 7:
				x = 950;
				y = 260;
				break;
			case 8:
				x = 950;
				y = 200;
				break;
			case 9:
				x = 950;
				y = 140;
				break;
			case 10:
				x = 900;
				y = 20;
				break;
			case 11:
				x = 765;
				y = 5;
				break;
			case 12:
				x = 685;
				y = 5;
				break;
			case 13:
				x = 605;
				y = 5;
				break;
			case 14:
				x = 525;
				y = 5;
				break;
			case 15:
				x = 455;
				y = 5;
				break;
			case 16:
				x = 375;
				y = 5;
				break;
			case 17:
				x = 305;
				y = 5;
				break;
			case 18:
				x = 228;
				y = 5;
				break;
			case 19:
				x = 153;
				y = 5;
				break;
			case 20:
				x = 20;
				y = 5;
				break;
			case 21:
				x = 90;
				y = 135;
				break;
			case 22:
				x = 90;
				y = 200;
				break;
			case 23:
				x = 90;
				y = 265;
				break;
			case 24:
				x = 90;
				y = 330;
				break;
			case 25:
				x = 90;
				y = 395;
				break;
			case 26:
				x = 90;
				y = 450;
				break;
			case 27:
				x = 90;
				y = 510;
				break;
			case 28:
				x = 90;
				y = 580;
				break;
			case 29:
				x = 90;
				y = 635;
				break;
			case 30:
				x = 15;
				y = 770;
				break;
			case 31:
				x = 160;
				y = 770;
				break;
			case 32:
				x = 235;
				y = 770;
				break;
			case 33:
				x = 310;
				y = 770;
				break;
			case 34:
				x = 385;
				y = 770;
				break;
			case 35:
				x = 460;
				y = 770;
				break;
			case 36:
				x = 535;
				y = 770;
				break;
			case 37:
				x = 610;
				y = 770;
				break;
			case 38:
				x = 685;
				y = 770;
				break;
			case 39:
				x = 760;
				y = 770;
				break;
			default:
				break;
			}
			if(land) {
				fullpath= path+"Land.png";				
				draw.setImagePath(fullpath);
				draw.drawImage(x, y, 30, 30);
			}
			if(villa) {
				fullpath= path+"Villa.png";
				draw.setImagePath(fullpath);
				draw.drawImage(x+30, y, 30, 30);
			}
			if(building) {
				fullpath= path+"Building.png";
				draw.setImagePath(fullpath);
				if(i>=10&&i<=18) {
					draw.drawImage(x, y+30, 30, 30);
				}else if(i>=30 &&i<=38) {
					draw.drawImage(x, y-30, 30, 30);					
				}else {
				draw.drawImage(x+60, y, 30, 30);
				}
			}
			if(hotel) {
				fullpath= path+"Hotel.png";
				draw.setImagePath(fullpath);
				if(i>=10&&i<=18) {
					draw.drawImage(x+30, y+30, 30, 30);
				}else if(i>=30 &&i<=38) {
					draw.drawImage(x+30, y-30, 30, 30);					
				}
				else {
				draw.drawImage(x+90, y, 30, 30);
				}
			}
		}
	}
	
	public void play(int position) {
		try {
		mal[myMalNumber].position = position;
		
		MalInfo sendClass = new MalInfo();
		int pos[] = new int[mal.length];
		int money[] = new int[mal.length];
		
		for (int i = 0; i < mal.length; i++) {
			
			pos[i] = mal[i].position;
			sendClass.setPosition(pos);
		}
//		 위치를 SendClass에 담아 먼저 보낸다
		oos.writeObject(sendClass);
		
//		현재 위치의 이벤트(게임 or 건물구매) 수행 (JDialog로 인해 코드가 정지한다)
		gameManager.showCity(mal[myMalNumber], mal[myMalNumber].position);
	
		oos.writeObject("[next]");
		}catch (Exception e) {
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
	if (e.getSource()==throwButton) {
//				두개의 주사위 번호
				int number1 = randomInt();
				int number2 = randomInt();
		
//				모든 클라이언트에게 공유하도록 서버로 주사위 정보 전송
				String message = "[Throw]"+number1+"@"+number2;
				throwButton.setEnabled(false);
				oos.writeObject(message);
				
//				주사위 굴린 후 말 위치 계산
				int position = mal[myMalNumber].position+number1+number2;
				
//				두번째 바퀴를 돌때 출발지(40번)에서 20만원 획득
				if(position>20&&position<39) {
					secondTurn = true;
				}
				if((position==40||position>40)&&secondTurn) {
					 int money = mal[myMalNumber].getMoney();
					 mal[myMalNumber].setMoney(money+20);
					 
					main.getNetwork().getOos().writeObject("[Status1]"+mal[myMalNumber].getTeam()+"팀이 출발지에서  20만원을 얻어갑니다.");
				}
				if(position>40) {
					position-=40;
				}
				 
				repaint();
				
				play(position);
				
		}else if (e.getSource()==startButton) {
				oos.writeObject("[START]");
//		game1~4 테스트용도 JButton
		}
//		else if(e.getSource()==game1) {// 가위바위보
//			play(6);
//		}else if(e.getSource()==game2) {// 지렁이
//			play(10);
//		}else if(e.getSource()==game3) {// 숫자야구
//			play(20);
//		}else if(e.getSource()==game4) {// 스도쿠
//			play(30);
//		}
		}catch(Exception es) {
			
		}
	}

	public boolean isGameTurn() {
		return gameEnd;
	}

	public void setGameTurn(boolean gameTurn) {
		this.gameEnd = gameTurn;
	}

	public JButton getThrowButton() {
		return throwButton;
	}


	public JButton getStartButton() {
		return startButton;
	}

	public GameManager getGameManager() {
		return gameManager;
	}
	public Mal[] getMal() {
		return mal;
	}
	public JLabel getDiceNumberLabel() {
		return diceNumberLabel;
	}
	public void setDiceNumberLabel(JLabel diceNumberLabel) {
		this.diceNumberLabel = diceNumberLabel;
	}
	public JLabel getTurnPlayerName() {
		return turnPlayerLabel;
	}
	public void setTurnPlayerName(JLabel turnPlayerName) {
		this.turnPlayerLabel = turnPlayerName;
	}
	public JLabel getPlayer1Label() {
		return player1Label;
	}
	public JLabel getPlayer2Label() {
		return player2Label;
	}
}
