package Semicolon.project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class Network implements Serializable{
	
//	client에서 서버와 연결할때 이 클래스를 사용
	
	Socket socket;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	ClientThread clientThread;
	
	String ip = null;
	String name = null;
	final int PORT = 5000;
	
//	패널들을 불러올수 있는 main객체
	Main main;
	
	Network(String ip, String name,Main main) throws IOException{

			this.ip = ip;
			this.name = name;
			
			socket = new Socket(ip,PORT);
			
			ois = new ObjectInputStream(socket.getInputStream());
			oos = new ObjectOutputStream(socket.getOutputStream());
			clientThread = new ClientThread(this,main);
			clientThread.start();
//			첫 연결시 서버로 이름을 보냄 
			
			sendMessage("[name]"+name);
	}
	
	public void sendMessage(String message) {
		try {
			oos.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void networkClose() {
		try {
		oos.close();
		ois.close();
		socket.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}

//	getter/setter
	public Socket getSocket() {
		return socket;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}
	public ObjectInputStream getOis() {
		return ois;
	}

	public ClientThread getChatThread() {
		return clientThread;
	}

	public void setMain(Main main) {
		this.main = main;
	}
	
	
}
