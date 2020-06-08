
package Semicolon.project;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
	
	ServerSocket serverSocket;
	static final int PORT = 5000;
	
	static ArrayList<ServerThread> listThread = new ArrayList<ServerThread>(); 
	
	static int turnNumber = 0;
	
	
	Server() {
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("server ready");
			while(true) {
				Socket socket = serverSocket.accept();
				
				System.out.println("client Á¢¼Ó : "+ socket.getInetAddress().getHostAddress());
				
				ServerThread serverThread = new ServerThread(socket);
				
				listThread.add(serverThread);
				serverThread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ServerThread> getListThread() {
		return listThread;
	}
	
	
	public static void main(String[] args) {
			new Server();
	}
	
}
