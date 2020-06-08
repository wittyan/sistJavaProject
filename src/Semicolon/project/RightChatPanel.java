package Semicolon.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class RightChatPanel extends JPanel implements ActionListener{
	
	JTextArea jTextArea;
	JList<String> jList;
	JTextField jt = new JTextField();
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel areaListPanel = new JPanel(new BorderLayout());
	JPanel fieldPanel = new JPanel(new BorderLayout());
	
	JScrollPane jsp;
	JButton jButton = new JButton("Send");
	
	Network network;
	Main main;
	
	int width;
	int height;
	public void setPanelSize() {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)d.getWidth();
		height = (int)d.getHeight()/15*14; // 작업표시줄 제외
		
		width = width/10 * 3;
	
		this.setPreferredSize(new Dimension(width, height));	
	}
	
	public void setComponent() {
		
		
		jTextArea = new JTextArea();
		int upHeight = height/10 * 9;
//		jTextArea.setPreferredSize(new Dimension(width/3*2,upHeight-20));
		jTextArea.setBackground(new Color(50, 50, 80));
		jTextArea.setForeground(Color.WHITE);
		jTextArea.setFont(new Font("휴먼매직체",Font.PLAIN,20));
		
		jTextArea.setEditable(false);
		
		jsp = new JScrollPane(jTextArea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS , JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		jList = new JList<String>();
		jList.setPreferredSize(new Dimension(width/3*1,upHeight));
		jList.setBackground(Color.DARK_GRAY);
		jList.setForeground(Color.white);				
		jList.setFont(new Font("휴먼매직체",Font.BOLD,30));
		
		areaListPanel.add("Center",jsp);
		areaListPanel.add("East",jList);
		
		fieldPanel.add("Center",jt);
		fieldPanel.add("East",jButton);
		
		this.add("Center",areaListPanel);
		this.add("South",fieldPanel);
		
//		ActionListener 추가
		jt.addActionListener(this);
	}
	
	public RightChatPanel(Main main) {
		this.setLayout(new BorderLayout());
		this.main = main;
		setPanelSize();
		setComponent();	
	}
	
	
	
	
//	채팅 엔터 눌렀을때 처리
@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==jt) {
			try {
			Network network = main.getNetwork();
			ObjectOutputStream oos =network.getOos();
			
			String message; 
			message = network.name+"#"+jt.getText()+" ";
			oos.writeObject(message);
			
			jt.setText("");
			
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	
	}

	
	public void textAreaAppend(String text) {
		jTextArea.append(text);

		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());

	}
	
	public void setJList(ArrayList<String> list) {
		
		String[] userList = new String[list.size()+1];
		
		userList[0] = "접속자 목록";
		for (int i = 0; i < list.size(); i++) {
			userList[i+1] = list.get(i);
		}
//		String []  str = {
//				"1","2","3"
//		};
//		jList.setListData(str);   String[] 로 받는다
		jList.setListData(userList);
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
	
	
}
