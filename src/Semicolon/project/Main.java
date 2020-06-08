package Semicolon.project;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main implements Serializable{
	
//	JFrame
	JFrame frame;
	
//	JPanel
	JPanel mainPanel;
	RightChatPanel rightChatPanel= new RightChatPanel(this);
	GamePanel gamePanel;
	
	Network network;
	
	public void setPanel(){
		
		
		mainPanel = new JPanel(new BorderLayout());
		gamePanel = new GamePanel(this);

		
		mainPanel.add("Center",gamePanel);
		mainPanel.add("East",rightChatPanel);
		
		
	}
	
	public void setFrame() {
		frame = new JFrame();
		frame.add(mainPanel);
		frame.setTitle("SEMICOLON MARBLE");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
//					서버측에 종료메시지를 보내 쓰레드를 종료시킨다
				network.getOos().writeObject("[exit]");
				
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}

		});
		frame.pack();
		frame.setVisible(true);
		
	}
	
	public Main(String ip, String name) {
			Network network = null;
//		서버와 통신하는 네트워크 생성
			try {
				network = new Network(ip, name, this);
			}catch(IOException e2) {
				JOptionPane.showMessageDialog(null, "접속 실패");
				System.exit(0);
			}
		
//			main에서 network를 가지고 있게 한다 
			this.network = network;

			setPanel();
			setFrame();
		
	}
//	getter/setter
	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public RightChatPanel getRightChatPanel() {
		return rightChatPanel;
	}
	
	
	public void setNetwork(Network network) {
		this.network = network;
		
	}
	
	public Network getNetwork() {
		return network;
	}

	
	public JFrame getFrame() {
		return frame;
	}

	//	main
	public static void main(String[] args) {
//		IP와 NAME을 입력받을 창을 띄운다
		new ConnectDialog();
		
//		new Main("127.0.0.1", "test1");
//		new Main("127.0.0.1", "test2");
		
	}
}
