package mingming.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread implements Runnable {

	Socket mSocket;
	int id;
	PrintWriter output = null;
	BufferedReader input = null;
	
	public ClientThread(Socket _socket, int _id)
	{
		mSocket = _socket;
		id = _id;
	}
	
	public void run() {
		// TODO Auto-generated method stub
 
		if(mSocket != null)
		{
			if(!mSocket.isClosed())
			{
				try {
				    output = new PrintWriter(mSocket.getOutputStream(),true);
					input = new BufferedReader(new InputStreamReader(mSocket.getInputStream()));
					
					while(!mSocket.isClosed())
					{
						String inputData = input.readLine();
						System.out.println("data from client: " + id + " is: " + inputData);
					}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				if(input != null)
				{
					try {
						input.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					input = null;
					
				}
				
				if(output != null)
				{
					output.close();
					output = null;
				}
			}
		}

		
	}

}
