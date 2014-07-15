package mingming.research;

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
	Server mServer;
	
	public ClientThread(Server _server, Socket _socket, int _id)
	{
		mServer = _server;
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
					System.out.println("a new client " + id + " connects... ");
					while(!mSocket.isClosed())
					{
						if(input != null && input.ready())
						{
							String inputData = input.readLine();
							System.out.println("data from client: " + id + " is: " + inputData);
							
							if(inputData.trim().equals("close"))
							{
								System.out.println("receiving close note from client...");
								//client disconnected
								mSocket.close();
							}
							
						}
					}
					
					
					System.out.println("Socket is closed");
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
					
					mServer.notifyClose(mSocket);
					

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			/*
			else
			{
				
				System.out.println("Socket is closed");
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
				
				mServer.notifyClose(mSocket);
			}
			*/
		}

		
	}
	
	public boolean sendMessage(String msg)
	{
		if(!mSocket.isClosed() && output != null)
		{
			output.println(msg);
			output.flush();
			return true;
		}
		
		return false;
	}

}
