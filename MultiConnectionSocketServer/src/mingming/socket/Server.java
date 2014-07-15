package mingming.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	
	ServerSocket sSocket;
	int numberOfClients = 0;
	
	public Server(){
		try {
			sSocket = new ServerSocket(Utils.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		while(true){
			Socket acceptedSocket;
			try {
				acceptedSocket = sSocket.accept();
				numberOfClients ++;
				ClientThread mClient = new ClientThread(acceptedSocket, numberOfClients);
				
				new Thread(mClient).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
           new Server().start();
	}

}
