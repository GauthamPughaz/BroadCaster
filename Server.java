package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.lang.Thread;

public class Server {
	PrintStream print;
	ServerSocket ss;
	HashMap<Integer, Socket> map;
	public Server() throws InterruptedException {
		map = new HashMap<Integer, Socket>();
		try {
			ss = new ServerSocket(2000);
			System.out.println("Waiting for client...");
			while(true) {
				Socket s = ss.accept();
				print = new PrintStream(s.getOutputStream());
				
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							BufferedReader in;
							PrintStream out;
							String message;
							int senderid;
							while(true){
								senderid = (int) Thread.currentThread().getId();
								in = new BufferedReader(new InputStreamReader(map.get(senderid).getInputStream()));
								message = in.readLine();
								System.out.println("\nClient [ ID: " + senderid + " ]: " + message);							
								for(int key: map.keySet()) {
									if(key != senderid) {
										out = new PrintStream(map.get(key).getOutputStream());
										out.println(senderid);
										out.println(message);
									}
								}
							}
						}
						catch (IOException e) {
							e.printStackTrace();
						}						
					}
				});
				print.println(t.getId());
				map.put((int)t.getId(), s);
				t.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Server();
	}

}

