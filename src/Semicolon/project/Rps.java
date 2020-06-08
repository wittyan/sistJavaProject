package Semicolon.project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//부루마블 팝업게임용
public class Rps extends JDialog implements ActionListener{
	private JPanel upLabel,gamePanel,bottomPanel;
	private JLabel me, you,meScoreLabel,result;
	private JButton rockBtn,paperBtn,scissorsBtn;
	
	JFrame frame;
	private Mal inputMal;
	private JLabel meT, youT;
	private int width=1000, height=600;
	private int meGetScore=0;
	
	
	ImageIcon icon[]= {
			new ImageIcon("D:\\scissors.png"),
			new ImageIcon("D:\\rock.png"),
			new ImageIcon("D:\\paper.png")
	};
	
	String resultArr[]= {"WIN","LOSE","DRAW"};
	
	
	//이기면 돈++ 비기면 0 지면 바로 끝
	public void getScore() {
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o=e.getSource();
		
			if(o==scissorsBtn || o==rockBtn || o==paperBtn) {
				youT.setIcon(icon[(int)(Math.random()*3)]);
			}
			boolean win = false;
			boolean draw= false;
			boolean lose= false;
			if(o==scissorsBtn) {
				meT.setIcon(icon[0]);
				
				if(youT.getIcon()==icon[0]) {
					draw = true;
				}
				
				if(youT.getIcon()==icon[1]) {
					lose = true;
				}
				
				if(youT.getIcon()==icon[2]) {
					win = true;
					
				}
				
			}
		
			
			
			if(o==rockBtn) {
				meT.setIcon(icon[1]);
				
				if(youT.getIcon()==icon[0]) {
					win = true;
					
				}
				if(youT.getIcon()==icon[1]) {
					draw = true;
					
				}
				if(youT.getIcon()==icon[2]) {
					lose = true;
					
				}
			}
			
			
			if(o==paperBtn) {
				meT.setIcon(icon[2]);
				
				if(youT.getIcon()==icon[0]) {
					lose = true;
					
				}
				if(youT.getIcon()==icon[1]) {
					win = true;
					
				}
				if(youT.getIcon()==icon[2]) {
					draw = true;
					
				}
			}
		

			if(lose){
				result.setText(resultArr[1]);
				JOptionPane.showMessageDialog(frame, "게임이 끝났습니다, 점수:"+meGetScore);
				
				inputMal.setMoney(inputMal.getMoney()+meGetScore);
				this.dispose();
			}
			if(win) {
				result.setText(resultArr[0]);
				meGetScore += 100;
				meScoreLabel.setText(String.valueOf(meGetScore));
			}if(draw) {
				result.setText(resultArr[2]);
			}
		
	}
	
	
	public void frame() {
	
		upLabel=new JPanel(new GridLayout(1,2));
		upLabel.setBounds(40,50,width,100);
		upLabel.setBackground(Color.BLACK);
		
		
		you=new JLabel("상대방");
		you.setFont(new Font("돋움",Font.BOLD,50));
		
		you.setHorizontalAlignment(JLabel.CENTER);
		you.setForeground(Color.WHITE);
		
		me=new JLabel("나");
		me.setFont(new Font("돋움",Font.BOLD,50));
		
		me.setHorizontalAlignment(JLabel.CENTER);
		me.setForeground(Color.WHITE);

		upLabel.add(you);
		upLabel.add(me);
		
		//게임패널
		gamePanel=new JPanel(new GridLayout(1,2));
		gamePanel.setBounds(40,150, width, 300);
		gamePanel.setBackground(Color.WHITE);
		
		youT=new JLabel(icon[(int)(Math.random()*3)]);
		
		
		meT=new JLabel(icon[(int)(Math.random()*3)]);
		
		
		//게임패널 add
		
	
		
		gamePanel.add(youT);
		gamePanel.add(meT);
	
		
		//bottomPanel
		bottomPanel=new JPanel(new GridLayout(1,5));
		bottomPanel.setBounds(40, 450, width, 100);
		bottomPanel.setBackground(new Color(150,200,255));
		
		
		
		
		meScoreLabel=new JLabel(Integer.toString(meGetScore));
		meScoreLabel.setFont(new Font("돋움",Font.BOLD,20));
		meScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		meScoreLabel.setForeground(Color.WHITE);
		
		
		
		result=new JLabel();
		result.setFont(new Font("돋움",Font.BOLD,40));
		result.setHorizontalAlignment(JLabel.CENTER);
		result.setBackground(Color.YELLOW);
		result.setForeground(Color.RED);
		
		scissorsBtn=new JButton("가위");
		scissorsBtn.setFont(new Font("돋움", Font.BOLD, 40));
		scissorsBtn.setBackground(Color.RED);
		scissorsBtn.setForeground(Color.WHITE);
		scissorsBtn.addActionListener(this);
		
		rockBtn=new JButton("바위");
		rockBtn.setFont(new Font("돋움", Font.BOLD, 40));
		rockBtn.setBackground(Color.RED);
		rockBtn.setForeground(Color.WHITE);
		rockBtn.addActionListener(this);
		
		paperBtn=new JButton("보");
		paperBtn.setFont(new Font("돋움", Font.BOLD, 40));
		paperBtn.setBackground(Color.RED);
		paperBtn.setForeground(Color.WHITE);
		paperBtn.addActionListener(this);
		
		
		
		
		//bottomPanel add
		
		bottomPanel.add(meScoreLabel);
		bottomPanel.add(scissorsBtn);
		bottomPanel.add(rockBtn);
		bottomPanel.add(paperBtn);
		bottomPanel.add(result);
		
		
		this.add(gamePanel);
		this.add(upLabel);
		this.add(bottomPanel);
		
		
	}
	
	
	public Rps(JFrame frame,Mal mal) {
		super(frame);
		this.frame = frame;
		
		super.setModal(true);
		inputMal = mal;
		
		frame();
	
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
//				System.exit(0);
				frame.dispose();
			}
			
		});
		
		this.setBounds(500, 100, width+100, height+100);
		this.setTitle("가위바위보게임");
		this.setLayout(null);
		this.setVisible(true);
	
		
	}
	
	
	
//	public static void main(String[] args) {
//		new Rps();
//	}

}
