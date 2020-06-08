package Semicolon.project;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ConnectDialog extends KeyAdapter implements ActionListener {

	JFrame jFrame = new JFrame();

//	ServerIp, Name JLabel
	JLabel jLabelServerIp = new JLabel("ServerIp : ");
	JLabel jLabelName = new JLabel("Name    :");

//	ServerIp와 Name 입력칸
	JTextField jtIp = new JTextField();
	JTextField jtName = new JTextField();

//	접속버튼
	JButton okButton = new JButton("접속");

//	배경화면 로딩
	BufferedImage img = null;

	public ConnectDialog() {
		try {
			img = ImageIO.read(new File("D:\\loginImage.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "이미지 불러오기 실패");
			System.exit(0);
		}

		myPanel panel = new myPanel();

		jFrame.setTitle("접속");

		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(480, 640);
		layeredPane.setLayout(null);

		panel.setSize(480, 640);
		layeredPane.add(panel);

		jLabelServerIp.setBounds(30, 70, 130, 50);
		jLabelServerIp.setFont(new Font("휴먼매직체", Font.BOLD, 20));
		jLabelName.setBounds(30, 100, 100, 50);
		jLabelName.setFont(new Font("휴먼매직체", Font.BOLD, 20));
		jFrame.add(jLabelServerIp);
		jFrame.add(jLabelName);
		jtIp.setBounds(150, 80, 200, 20);
		jtIp.addKeyListener(this);
		jFrame.add(jtIp);
		jtName.setBounds(150, 110, 200, 20);
		jtName.addKeyListener(this);
		jFrame.add(jtName);
		okButton.setBounds(280, 140, 60, 30);
		jFrame.add(okButton);
		okButton.addActionListener(this);
		jFrame.add(layeredPane);
		jFrame.setBounds(720, 220, 480, 300);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		jFrame.addKeyListener(this);
	}

//	배경화면을 그리는 패널 (JLayeredPane에 add)
	class myPanel extends JPanel {
		@Override
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, 480, 300, null);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			String ip = jtIp.getText().trim();
			String name = jtName.getText().trim();

			Main main;

			if (!ip.equals("") && !name.equals("")) {
//				입력된 ip와 name을 바탕으로 Main생성
				main = new Main(ip, name);

				jFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(jFrame, "입력이 되지 않았습니다.");
			}
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == e.VK_ENTER) {
			String ip = jtIp.getText().trim();
			String name = jtName.getText().trim();

			Main main;

			if (!ip.equals("") && !name.equals("")) {
				main = new Main(ip, name);
				jFrame.dispose();
			} else {
				JOptionPane.showMessageDialog(jFrame, "입력이 되지 않았습니다.");
			}
		}
	}
	
}
