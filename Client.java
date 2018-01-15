package socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	int id;
	int choice;
	Socket s;
	String message;
	BufferedReader in, br;
	PrintStream out;
	Scanner input;
	int senderid;
	public Client() {
		try {
			input = new Scanner(System.in);
			s = new Socket("localhost", 2000);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintStream(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(System.in));
			id = Integer.parseInt(in.readLine());
			while(true) {
				System.out.println("To broadcast messages press 1 and to receive incoming messages press 0");
				choice  = input.nextInt();
				if(choice == 1) {
					System.out.print("Client [ ID: "+ id + " ]: ");
					message = br.readLine();
					out.println(message);
				}
				else {
					senderid = Integer.parseInt(in.readLine());
					message = in.readLine();
					System.out.println("Client: [ ID: "+ senderid + " ]: " + message );
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Client();
	}
}
