package Semicolon.project;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DMain{
	
//	JFrame
	JDialog frame;
	
//	JPanel
	JPanel mainPanel;
	JPanel upPanel;
	DGamePanel dgamePanel;
	
	public void setPanel(Mal mal){
		mainPanel = new JPanel(new BorderLayout());
		
		upPanel = new JPanel(new BorderLayout());
		dgamePanel = new DGamePanel(this,mal);
		
		mainPanel.add("Center",dgamePanel);
		
	}
	
	public void setFrame(JFrame frames) {
		frame = new JDialog(frames);
		frame.setModal(true);
		frame.add(mainPanel);
		frame.setTitle("NumberBaseBallGame");

		
		frame.pack();
		frame.setLocation(150,150);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				frame.dispose();
			}
			
		});
	}
	
	public DMain(JFrame frame,Mal inputMal) {
		
		setPanel(inputMal);
		setFrame(frame);	
	}
//	getter/setter
	public DGamePanel getGamePanel() {
		return dgamePanel;
	}
	public JDialog getFrame() {
		return frame;
	}
	
}