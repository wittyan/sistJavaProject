package Semicolon.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class Dinfo{
	JDialog frame;
	Image Infomation;
	JLabel jLabel =new JLabel();
	Dinfo(DMain g){
		Dimension d = new Dimension(670,395);
		
		JLayeredPane pane = new JLayeredPane();
		
		jLabel.setIcon(new ImageIcon("img/Info.png"));
		frame =new JDialog(g.getFrame());
		frame.setTitle("Information");
		frame.add(jLabel);
		jLabel.setPreferredSize(d);
		frame.setBounds(350, 350,670,395);
		frame.setBackground(Color.white);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
//		frame.setLocationRelativeTo(null);
	}
}