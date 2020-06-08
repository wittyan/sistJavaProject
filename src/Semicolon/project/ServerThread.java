
package Semicolon.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ServerThread extends Thread{

	
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
//	쓰레드 한개 = 유저 한명
	String name;
	
	boolean isStop = false;
	
	ServerThread(Socket socket) {
		try {
		
		this.socket = socket;
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());

		
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void broadCastString(String str) {
		try {
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			
			for (ServerThread s : serverThreadList) {
					s.getOos().writeObject(str);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public void broadCastUserList() {
		try {
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			
			ArrayList<String> userList = new ArrayList<String>();
			for (ServerThread s : serverThreadList) {
//				ServerThread에 있는 name 필드를 이용해 userList생성
			
				if(s.name!=null) {
					System.out.println(s.name);
					userList.add(s.name);
				}
			}
			for (ServerThread s : serverThreadList) {
					s.getOos().writeObject(userList);
			}
		} catch (IOException e) {
			System.out.println("exception");
		}
		
	}
	public void broadCastMalInfo(MalInfo malInfo) {
		try {
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			
			for (ServerThread s : serverThreadList) {
					s.getOos().writeObject(malInfo);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	public void broadCastCityInfo(CityInfo cityInfo) {
		try {
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			
			for (ServerThread s : serverThreadList) {
				s.getOos().writeObject(cityInfo);
			}
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	
	public void processMessage(String message) {
		if(message.startsWith("[name]")) {

			String name = message.split("]")[1];
			this.name = name;
			
			broadCastString("[Server] "+name+"님 입장(ip : "+socket.getInetAddress().getHostAddress()+")");
//			채팅접속자
			broadCastUserList();
			
		}else if(message.equals("[exit]")){
//			쓰레드 종료
			
//			ServerThreadList에서 제거
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			
			Iterator<ServerThread> it = serverThreadList.iterator();
			
			String str = "[Server] "+name+"님이 종료하셨습니다.";
			while(it.hasNext()) {
				ServerThread s = it.next();
				if(s.name.equals(this.name)) {
					this.name = null;
					it.remove();
				}
			}

			broadCastUserList();
			broadCastString(str);
			broadCastString(message);
			isStop = true;
		}else if(message.equals("[START]")){
//			게임 시작
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			if(serverThreadList.size()==2) {
				try {
				serverThreadList.get(0).getOos().writeObject("[team]blue");
				serverThreadList.get(1).getOos().writeObject("[team]red");
				String turnUserName = serverThreadList.get(Server.turnNumber).name;
//				특정 유저에게 주사위를 던질수 있게 한다
				serverThreadList.get(Server.turnNumber).getOos().writeObject("[turn]");
//				모든사람들에게 어느 차례인지 알려준다
				broadCastString("[차례]"+turnUserName);
				
				Server.turnNumber++;
				}catch(Exception e) {
					
				}
			}else {
				broadCastString("[Server : 2명이 접속해야 게임이 시작됩니다.]");
			}
			
		}else if(message.startsWith("[Throw]")) {
			
			broadCastString(message);
			
		}else if(message.startsWith("[next]")) {
			ArrayList<ServerThread> serverThreadList = Server.getListThread();
			try {
//				현재 누구 차례인지 구한다
				String turnUserName = serverThreadList.get(Server.turnNumber).name;
//				모든사람들에게 어느 차례인지 알려준다
				broadCastString("[차례]"+turnUserName);
//				특정 유저에게 주사위를 던질수 있게 한다
				serverThreadList.get(Server.turnNumber).getOos().writeObject("[turn]");
				
			}catch(Exception e) {
				
			}
			Server.turnNumber++;
		
			if(Server.turnNumber==2)
				Server.turnNumber=0;
			
		}else if(message.startsWith("[Out]")) {
			broadCastString(message);
		}
		else{
			broadCastString(message);
		}
	}
	
	public void processMalInfo(MalInfo malInfo) {
			broadCastMalInfo(malInfo);
	}
	public void processCityInfo(CityInfo cityInfo) {
			broadCastCityInfo(cityInfo);
	}
	@Override
	public synchronized void run() {
		
		while(!isStop) {
			try {
				Object o = ois.readObject();
				if(o instanceof String) {
					processMessage((String)o);
				}
				else if(o instanceof MalInfo) {
					processMalInfo((MalInfo)o);
				}
				else if(o instanceof CityInfo) {
					processCityInfo((CityInfo)o);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	public ObjectOutputStream getOos() {
		return oos;
	}
	
	
	
}
