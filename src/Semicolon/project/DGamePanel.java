package Semicolon.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class DGamePanel extends JPanel implements ActionListener,KeyListener{

	private DMain g;
//	public DMain g;
	
	JButton Stbutton = new JButton("시작");
	JButton Enbutton = new JButton("입력");
	JButton Rebutton = new JButton("리셋");
	
	ImageIcon Up = new ImageIcon("img/Up.jpg");
	ImageIcon Dw = new ImageIcon("img/Dw.jpg");

	Image num1;
	Image num2;
	Image num3;
	
	ImageIcon spare0=new ImageIcon("img/0.jpg");
	ImageIcon spare1=new ImageIcon("img/1.jpg");
	ImageIcon spare2=new ImageIcon("img/2.jpg");
	ImageIcon spare3=new ImageIcon("img/3.jpg");
	ImageIcon spare4=new ImageIcon("img/4.jpg");
	ImageIcon spare5=new ImageIcon("img/5.jpg");
	ImageIcon spare6=new ImageIcon("img/6.jpg");
	ImageIcon spare7=new ImageIcon("img/7.jpg");
	ImageIcon spare8=new ImageIcon("img/8.jpg");
	ImageIcon spare9=new ImageIcon("img/9.jpg");
	
	ImageIcon spare0x=new ImageIcon("img/0x.jpg");
	ImageIcon spare1x=new ImageIcon("img/1x.jpg");
	ImageIcon spare2x=new ImageIcon("img/2x.jpg");
	ImageIcon spare3x=new ImageIcon("img/3x.jpg");
	ImageIcon spare4x=new ImageIcon("img/4x.jpg");
	ImageIcon spare5x=new ImageIcon("img/5x.jpg");
	ImageIcon spare6x=new ImageIcon("img/6x.jpg");
	ImageIcon spare7x=new ImageIcon("img/7x.jpg");
	ImageIcon spare8x=new ImageIcon("img/8x.jpg");
	ImageIcon spare9x=new ImageIcon("img/9x.jpg");
	ImageIcon info=new ImageIcon("img/!.jpg");
	
	int cnt1=0;
	int cnt2=0;
	int cnt3=0;
	int f=0;
	
	int money=500;
	
	JButton Num1Up = new JButton(Up);
	JButton Num1Dw = new JButton(Dw);
	JButton Num2Up = new JButton(Up);
	JButton Num2Dw = new JButton(Dw);
	JButton Num3Up = new JButton(Up);
	JButton Num3Dw = new JButton(Dw);
	
	JButton Spare0=new JButton(spare0);
	JButton Spare1=new JButton(spare1);
	JButton Spare2=new JButton(spare2);
	JButton Spare3=new JButton(spare3);
	JButton Spare4=new JButton(spare4);
	JButton Spare5=new JButton(spare5);
	JButton Spare6=new JButton(spare6);
	JButton Spare7=new JButton(spare7);
	JButton Spare8=new JButton(spare8);
	JButton Spare9=new JButton(spare9);
	JButton Info=new JButton(info);
	
	JTextArea jTextArea;
	
	JScrollPane jsp;
	JTextField jt = new JTextField();
	
	int width,height;
	int x=280,y=200;
	
	int A[]=new int[3];//랜덤의 3자리 숫자
	int B[]=new int[3];//입력할 3자리 숫자
	
	Mal inputMal;
	JDialog frame;
	
	public void Compare(int A[],int B[]) {
		
		int Strike=0,Ball=0;
		
		for(int i=0;i<B.length;i++) {
			for(int j=0;j<A.length;j++) {
				if(B[i]==A[j]) {//일치하는 숫자비교
					if(i==j) {//자릿수가 같으면 Strike 아니면 Ball 1증가
						Strike++;
					}
					else {
						Ball++;
					}
					continue;
				}
			}
		}
		System.out.println(Strike+" "+Ball);
		if(Strike==0 && Ball==0) {
			textAreaAppend("아웃!"+"\n");
		}else {
			if(Strike!=0) 
				textAreaAppend(Strike+"스트라이크!"+"\n");
			if(Ball!=0)
				textAreaAppend(Ball+"볼!"+"\n");
			if(Strike==3) {
				textAreaAppend("축하합니다!");
				inputMal.setMoney(inputMal.getMoney()+money);
				JOptionPane.showMessageDialog(null, money+"만큼 획득 하셨습니다!\n총 "+inputMal.getMoney()+"원이 됩니다.");
				g.frame.dispose();
			}
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
		System.out.println(Arrays.toString(A));
	}
	
	public void GameMain() {
		GameSet();
	}
	
	public DGamePanel(DMain g,Mal mal) {
		this.inputMal = mal;
		this.g = g;
		setPanelSize();
		this.setLayout(new BorderLayout());
		setComponent();	
		this.setBackground(Color.white);
	}
	
	public void textAreaAppend(String text) {
		jTextArea.append(text);
		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
	}
	
	public void setComponent() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		jTextArea = new JTextArea();
		width = (int)d.getWidth();
		height = (int)d.getHeight()/15*14;
		
		jTextArea.setBackground(new Color(150, 150, 180));
		jTextArea.setFont(new Font("휴먼매직체",Font.PLAIN,20));
		jTextArea.setEditable(false);
		
		jsp = new JScrollPane(jTextArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED , JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setPreferredSize(new Dimension(width/8, height-10));
		
		this.add("East",jsp);
	}
	
	public void setPanelSize() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)d.getWidth();
		height = (int)d.getHeight()/15*14; //작업표시줄 제외
		
		width = width/10*7;
		height = height/10*8;				
		this.setPreferredSize(new Dimension(width, height));
		this.setLayout(null);
		Enbutton.setEnabled(false);
		Rebutton.setEnabled(false);

		Stbutton.setBounds(x,y+390,100,40);
		this.add(Stbutton);
		Stbutton.addActionListener(this);
		
		Enbutton.setBounds(x+200,y+390,100,40);
		this.add(Enbutton);
		Enbutton.addActionListener(this);
		
		Rebutton.setBounds(x+400,y+390,100,40);
		this.add(Rebutton);
		Rebutton.addActionListener(this);
		
		Num1Up.setBounds(x+3,y+40,99,88);
		this.add(Num1Up);
		Num1Up.addActionListener(this);
		
		Num1Dw.setBounds(x+3,y+264,99,88);
		this.add(Num1Dw);
		Num1Dw.addActionListener(this);
		
		Num2Up.setBounds(x+203,y+40,99,88);
		this.add(Num2Up);
		Num2Up.addActionListener(this);
		
		Num2Dw.setBounds(x+203,y+264,99,88);
		this.add(Num2Dw);
		Num2Dw.addActionListener(this);
		
		Num3Up.setBounds(x+403,y+40,99,88);
		this.add(Num3Up);
		Num3Up.addActionListener(this);
		
		Num3Dw.setBounds(x+403,y+264,99,88);
		this.add(Num3Dw);
		Num3Dw.addActionListener(this);
		
		Num1Up.setEnabled(false);
		Num2Up.setEnabled(false);
		Num3Up.setEnabled(false);
		Num1Dw.setEnabled(false);
		Num2Dw.setEnabled(false);
		Num3Dw.setEnabled(false);

		Spare0.setBounds(x+700,y-78,60,60);
		this.add(Spare0);
		Spare0.addActionListener(this);
		
		Spare1.setBounds(x+700,y-16,60,60);
		this.add(Spare1);
		Spare1.addActionListener(this);
		
		Spare2.setBounds(x+700,y+46,60,60);
		this.add(Spare2);
		Spare2.addActionListener(this);
		
		Spare3.setBounds(x+700,y+108,60,60);
		this.add(Spare3);
		Spare3.addActionListener(this);
		
		Spare4.setBounds(x+700,y+170,60,60);
		this.add(Spare4);
		Spare4.addActionListener(this);
		
		Spare5.setBounds(x+700,y+232,60,60);
		this.add(Spare5);
		Spare5.addActionListener(this);
		
		Spare6.setBounds(x+700,y+294,60,60);
		this.add(Spare6);
		Spare6.addActionListener(this);
		
		Spare7.setBounds(x+700,y+356,60,60);
		this.add(Spare7);
		Spare7.addActionListener(this);
		
		Spare8.setBounds(x+700,y+418,60,60);
		this.add(Spare8);
		Spare8.addActionListener(this);
		
		Spare9.setBounds(x+700,y+480,60,60);
		this.add(Spare9);
		Spare9.addActionListener(this);
		
		Info.setBounds(x+700,y-170,60,60);
		this.add(Info);
		Info.addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
	    num1 = new ImageIcon("img/"+cnt1+".jpg").getImage();
	    g.drawImage(num1,x,y+140,this);
	    
	    num2 = new ImageIcon("img/"+cnt2+".jpg").getImage();
	    g.drawImage(num2,x+200,y+140,this);
	    
	    num3 = new ImageIcon("img/"+cnt3+".jpg").getImage();
	    g.drawImage(num3,x+400,y+140,this);
	    
	    g.setFont(new Font("휴먼매직체", Font.BOLD, 50));
	    g.setColor(new Color(145,224,224));
	    g.drawString("※ "+money+" ※",(width/10*7)/3,(height/10*8)/6);
	}
	
	public void check() {
		if(cnt1==0||(cnt1==cnt2||cnt1==cnt3||cnt2==cnt3)){
			Enbutton.setEnabled(false);
		}else {
			Enbutton.setEnabled(true);
		}
	}
	
	public void reset() {
		money=500;
		Stbutton.setEnabled(true);
		Enbutton.setEnabled(false);
		Rebutton.setEnabled(false);
		cnt1=0;cnt2=0;cnt3=0;
		Spare0.setIcon(spare0);
		Spare1.setIcon(spare1);
		Spare2.setIcon(spare2);
		Spare3.setIcon(spare3);
		Spare4.setIcon(spare4);
		Spare5.setIcon(spare5);
		Spare6.setIcon(spare6);
		Spare7.setIcon(spare7);
		Spare8.setIcon(spare8);
		Spare9.setIcon(spare9);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT) {
//			if(f==2)f=0;
//			else f++;
		}
		
		if(key == KeyEvent.VK_LEFT) {
//			if(f==0)f=2;
//			else f--;
		}
		
		if(key == KeyEvent.VK_UP) {
//			if(f==0) {
//				if(cnt1==9)cnt1=0;
//				else cnt1++;
//			}else if(f==1){
//				if(cnt2==9)cnt2=0;
//				else cnt2++;
//			}else {
//				if(cnt3==9)cnt3=0;
//				else cnt3++;
//			}
		}
		
		if(key == KeyEvent.VK_DOWN) {
//			if(f==0) {
//				if(cnt1==0)cnt1=9;
//				else cnt1--;
//			}else if(f==1){
//				if(cnt2==0)cnt2=9;
//				else cnt2--;
//			}else {
//				if(cnt3==0)cnt3=9;
//				else cnt3--;
//			}
			Num1Up.getFocusCycleRootAncestor();
		}
		
		if(key == KeyEvent.VK_SPACE) {
//			B[0]=cnt1;
//			B[1]=cnt2;
//			B[2]=cnt3;
//			textAreaAppend(cnt1+" "+cnt2+" "+cnt3+"\n");
//			Compare(A,B);
//			money-=10;
//			repaint();
		}		
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==Stbutton) {
			Num1Up.setEnabled(true);
			Num2Up.setEnabled(true);
			Num3Up.setEnabled(true);
			Num1Dw.setEnabled(true);
			Num2Dw.setEnabled(true);
			Num3Dw.setEnabled(true);
			Stbutton.setEnabled(false);
			Rebutton.setEnabled(true);
			GameMain();
			check();
			repaint();
		}
		
		if(e.getSource()==Enbutton) {
			B[0]=cnt1;
			B[1]=cnt2;
			B[2]=cnt3;
			textAreaAppend(cnt1+" "+cnt2+" "+cnt3+"\n");
			Compare(A,B);
			money-=10;
			repaint();
		}
		
		if(e.getSource()==Rebutton) {
			reset();
		}
		
		if(e.getSource()==Num1Up) {
			if(cnt1==9)cnt1=0;
			else cnt1++;
			num1 = new ImageIcon("img/"+cnt1+".jpg").getImage();
			check();
			repaint();
		}
		
		if(e.getSource()==Num1Dw) {
			if(cnt1==0)cnt1=9;
			else cnt1--;
			num1 = new ImageIcon("img/"+cnt1+".jpg").getImage();
			check();
			repaint();
		}
		
		if(e.getSource()==Num2Up) {
			if(cnt2==9)cnt2=0;
			else cnt2++;
			num2 = new ImageIcon("img/"+cnt2+".jpg").getImage();
			check();
			repaint();
		}
		
		if(e.getSource()==Num2Dw) {
			if(cnt2==0)cnt2=9;
			else cnt2--;
			num2 = new ImageIcon("img/"+cnt2+".jpg").getImage();
			check();
			repaint();
		}
		
		if(e.getSource()==Num3Up) {
			if(cnt3==9)cnt3=0;
			else cnt3++;
			num1 = new ImageIcon("img/"+cnt3+".jpg").getImage();
			check();			
			repaint();
		}
		
		if(e.getSource()==Num3Dw) {
			if(cnt3==0)cnt3=9;
			else cnt3--;
			num3 = new ImageIcon("img/"+cnt3+".jpg").getImage();
			check();
			repaint();
		}
		
		if(e.getSource()==Spare0) {
			if(Spare0.getIcon()==spare0) {
				Spare0.setIcon(spare0x);
			}else {
				Spare0.setIcon(spare0);
			}
			repaint();
		}
		
		if(e.getSource()==Spare1) {
			if(Spare1.getIcon()==spare1) {
				Spare1.setIcon(spare1x);
			}else {
				Spare1.setIcon(spare1);
			}
			repaint();
		}
		
		if(e.getSource()==Spare2) {
			if(Spare2.getIcon()==spare2) {
				Spare2.setIcon(spare2x);
			}else {
				Spare2.setIcon(spare2);
			}
			repaint();
		}
		
		if(e.getSource()==Spare3) {
			if(Spare3.getIcon()==spare3) {
				Spare3.setIcon(spare3x);
			}else {
				Spare3.setIcon(spare3);
			}
			repaint();
		}
		
		if(e.getSource()==Spare4) {
			if(Spare4.getIcon()==spare4) {
				Spare4.setIcon(spare4x);
			}else {
				Spare4.setIcon(spare4);
			}
			repaint();
		}
		
		if(e.getSource()==Spare5) {
			if(Spare5.getIcon()==spare5) {
				Spare5.setIcon(spare5x);
			}else {
				Spare5.setIcon(spare5);
			}
			repaint();
		}
		
		if(e.getSource()==Spare6) {
			if(Spare6.getIcon()==spare6) {
				Spare6.setIcon(spare6x);
			}else {
				Spare6.setIcon(spare6);
			}
			repaint();
		}
		
		if(e.getSource()==Spare7) {
			if(Spare7.getIcon()==spare7) {
				Spare7.setIcon(spare7x);
			}else {
				Spare7.setIcon(spare7);
			}
			repaint();
		}
		
		if(e.getSource()==Spare8) {
			if(Spare8.getIcon()==spare8) {
				Spare8.setIcon(spare8x);
			}else {
				Spare8.setIcon(spare8);
			}
			repaint();
		}
		
		if(e.getSource()==Spare9) {
			if(Spare9.getIcon()==spare9) {
				Spare9.setIcon(spare9x);
			}else {
				Spare9.setIcon(spare9);
			}
			repaint();
		}
		
		if(e.getSource()==Info) {
			new Dinfo(g);
		}
	}

	

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}