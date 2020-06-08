package Semicolon.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ClientThread extends Thread{

	ObjectInputStream ois;
	static boolean isStop = false;
	Main main;
	Network network;
	
	ClientThread(Network network,Main main) {
		this.network = network;
		this.main = main;
		
		ois = network.getOis();
	}
	public void processMessage(String message) {
		
		RightChatPanel chatPanel =main.getRightChatPanel();

		if(message.equals("[exit]")) {
			if(!main.getGamePanel().getStartButton().isEnabled()) {

			JOptionPane.showMessageDialog(main.getFrame(), "player ÇÑ¸íÀÌ ³ª°¬À¸¹Ç·Î °ÔÀÓÀ» Á¾·áÇÕ´Ï´Ù.");
			main.getFrame().dispose();
			}
		}
		else if(message.startsWith("[team]")){
//			ÆÀ ¼³Á¤
			GamePanel gamePanel = main.getGamePanel();
			
			String team = message.split("]")[1];
			if(team.equals("blue")) {
//				gamePanel.mal[0].setTeam(team);
				gamePanel.myMalNumber = 0;
			}else if(team.equals("red")) {
//				gamePanel.mal[1].setTeam(team);
				gamePanel.myMalNumber = 1;
			}
			
			gamePanel.getStartButton().setEnabled(false);
			RightChatPanel rightChatPanel = main.getRightChatPanel();
			rightChatPanel.textAreaAppend("´ç½ÅÀÇ ÆÀÀº ["+team+"]ÆÀ ÀÔ´Ï´Ù\n");
			
			main.getGamePanel().teamLabel.setText("³ªÀÇ ÆÀ : "+team+"ÆÀ");
			
			
		}else if(message.startsWith("[turn]")){
			System.out.println("[turn]");
			GamePanel gamePanel = main.getGamePanel();
			gamePanel.getThrowButton().setEnabled(true);
		
			
		}else if(message.startsWith("[Throw]")) {
//			ÁÖ»çÀ§¸¦ ´øÁü
			String str[] = message.split("]");
			String strNumber[] = str[1].split("@");
			
			int number1 = Integer.parseInt(strNumber[0]);
			int number2 = Integer.parseInt(strNumber[1]);
			
			GamePanel gamePanel = main.getGamePanel();
			gamePanel.diceThrow(number1,number2);
			
		}else if(message.startsWith("[Â÷·Ê]")) {
//			¾î´À player Â÷·ÊÀÎÁö ¶óº§À» ¼öÁ¤
			
			String str[] = message.split("]");
			String name = str[1];
			
			GamePanel gamePanel = main.getGamePanel();
			gamePanel.turnPlayerLabel.setText( name + " Â÷·ÊÀÔ´Ï´Ù.");
			
		}else if(message.startsWith("[Status1]")) {
			String str[] = message.split("]");
			
			GamePanel gamePanel = main.getGamePanel();
			gamePanel.playStatusLabel1.setText(str[1]);
		}
		else if(message.startsWith("[Status2]")) {
			String str[] = message.split("]");
			
			GamePanel gamePanel = main.getGamePanel();
			gamePanel.playStatusLabel2.setText(str[1]);
			
		}else if(message.startsWith("[Out]")) {
			String str = message.split("]")[1];
			
			int outNumber = Integer.parseInt(str);
			int winNumber = 0 ;
			if(outNumber==0)
				winNumber=1;
			else
				winNumber=0;
			String team = main.getGamePanel().getMal()[winNumber].getTeam();
			
			JOptionPane.showMessageDialog(main.getFrame(), team+"ÆÀ ½Â¸®!");
			
			main.getGamePanel().repaint();
			main.getGamePanel().updatePlayerLabel();
		}
		else if(message.contains("#")){
			String id = message.split("#")[0];
			String text = message.split("#")[1];
				
			chatPanel.textAreaAppend(id + " : "+ text +"\n");
		}else {
			chatPanel.textAreaAppend(message+"\n");
		}
	}
	
	public void processArrayList(ArrayList<String> list) {
		
		RightChatPanel rightChatPanel = main.getRightChatPanel();
		rightChatPanel.setJList(list);
		
		
	}
	public void processMalInfo(MalInfo malInfo) {
		
		GamePanel gamePanel = main.getGamePanel();
		
		Mal mal[] =gamePanel.getMal();
		int [] pos = malInfo.getPosition();
		int [] money = malInfo.getMoney();
		
		for (int i = 0; i < mal.length; i++) {
			if(mal[i]!=null) {
				if(pos!=null) {
				gamePanel.getMal()[i].setPosition(pos[i]);
				}
				if(money!=null) {
				gamePanel.getMal()[i].setMoney(money[i]);	
				}
			}
		}
		gamePanel.repaint();
		gamePanel.updatePlayerLabel();
	}
	public void processCityInfo(CityInfo cityInfo) {
		
		GamePanel gamePanel = main.getGamePanel();
		
		City city[] = gamePanel.getGameManager().getCity();
		
		for (int i = 0; i < city.length; i++) {
			if(city[i].getCityName().equals(cityInfo.cityName)) {
				city[i].setMal(cityInfo.mal);
				city[i].isBuilding = cityInfo.isBuilding;
				city[i].isHotel = cityInfo.isHotel;
				city[i].isVilla= cityInfo.isVilla;
				city[i].land = cityInfo.land;
				city[i].hotel = cityInfo.hotel;
				city[i].building = cityInfo.building;
				city[i].villa = cityInfo.villa;
			}
		}
		gamePanel.repaint();
		gamePanel.updatePlayerLabel();
	}
	@Override
	public synchronized void run() {
		
		while(!isStop) {
			try {
				Object o = ois.readObject();

				if(o instanceof String) {
					processMessage((String)o);
				}else if(o instanceof ArrayList) {
					processArrayList((ArrayList<String>)o);
				}else if(o instanceof MalInfo) {
					processMalInfo((MalInfo)o);
				}else if(o instanceof CityInfo) {
					processCityInfo((CityInfo)o);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
//				e.printStackTrace();
			}
		}
		
	}
	


	public void setMain(Main main) {
		this.main = main;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public void setStop(boolean isStop) {
		ClientThread.isStop = isStop;
	}
	
}
