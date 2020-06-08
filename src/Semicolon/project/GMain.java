package Semicolon.project;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class GMain {
JDialog frame;	

	public GMain(JFrame jFrame, Mal mal) {
		frame = new JDialog(jFrame,true);
		
		GGamePanel gamePanel = new GGamePanel(this,mal);
		
		frame.add(gamePanel);
		
		frame.setTitle("SNAKE");
		
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	

}
