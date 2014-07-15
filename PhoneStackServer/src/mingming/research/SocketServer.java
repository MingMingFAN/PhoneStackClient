package mingming.research;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer implements Runnable{

	ServerSocket server;
	
	public static int client_id = 1;
	
	public static boolean running = true;
	
	public volatile String message = "";
	
	public String previousMsg = "";
	
	
	
	public SocketServer(){}
	
	public void listenSocket(){
		
		    Socket acceptedSocket = null;
		    SocketServer.running = true;
	        try {
				server = new ServerSocket(Utils.port);
				acceptedSocket = server.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        if(acceptedSocket != null)
	        {
				
				BufferedReader  in = null;
				PrintWriter out = null;
				
				try {
					
					in = new BufferedReader(new InputStreamReader(acceptedSocket.getInputStream()));
					out = new PrintWriter(acceptedSocket.getOutputStream(), true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("in or out failed...");
					e.printStackTrace();
				}
				
				while(running && acceptedSocket != null && !acceptedSocket.isClosed())
				{
					//try {
						/*
						if(in != null && in.ready())
						{
							final String line = in.readLine();
							System.out.println("received: " + line);
						}
						*/
						
						synchronized(this){
						
						    if(previousMsg != message)
						    {
								out.println(message);
								out.flush();
								System.out.println("sending message: " + message);
								
						    }
						    
						    previousMsg = message;
						
							/*
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							*/
						}
						
					//} 
					//catch (IOException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
				}
				
				try {
					if(in != null)
					{
						in.close();
						in = null;
					}
					if(out != null)
					{
						out.close();
						out = null;
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					server.close();
					server = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	        }
	}


	public void run() {
		// TODO Auto-generated method stub
		listenSocket();
	}
	
	public void updateMessage(String _msg)
	{
		synchronized(this){		
			 message = _msg;
			}
	}
	
	public void stopServer()
	{
		synchronized(this)
		{
			running = false;
		}
	}
}
