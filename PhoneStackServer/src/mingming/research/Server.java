package mingming.research;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Server implements Runnable{

	
	ServerSocket sSocket;
	int numberOfClients = 0;
	boolean running = true;
	HashMap<Integer, ClientThread> Clients = new HashMap<Integer, ClientThread>();
	HashMap<Socket, Integer>  ClientSocketTable = new HashMap<Socket, Integer>();
	
	
	public Server(){
		try {
			sSocket = new ServerSocket(Utils.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	public void start()
	{
		running = true;
		
		while(running && sSocket != null && !sSocket.isClosed()){
			Socket acceptedSocket;
			try {
				acceptedSocket = sSocket.accept();
				numberOfClients ++;
				ClientThread mClient = new ClientThread(acceptedSocket, numberOfClients);
				if(!Clients.containsKey(numberOfClients))
				{
					Clients.put(numberOfClients, mClient);
				}
				
				new Thread(mClient).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	*/
	
	public void stopServer()
	{
		try {
			//clean up tables
			if(ClientSocketTable.containsKey(sSocket))
			{
				int clientId = ClientSocketTable.get(sSocket);
								
				if(Clients.containsKey(clientId))
				{
					ClientSocketTable.remove(sSocket);
					Clients.remove(clientId);
				}
			}
			
			sSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		running = false;
	}
	
	
	public int getNumberOfClients()
	{
		return numberOfClients;
	}
	
	public void sendMessage(int clientId, String msg)
	{
		if(Clients.containsKey(clientId))
		{
			boolean successful = Clients.get(clientId).sendMessage(msg);
			
		}
	}
	
	public int getNumberOfActiveClients()
	{
		if(Clients != null)
			return Clients.size();
		return 0;
	}

	public void run() {
		// TODO Auto-generated method stub
		running = true;
		
		while(running && sSocket != null && !sSocket.isClosed()){
			Socket acceptedSocket;
			try {
				acceptedSocket = sSocket.accept();
				numberOfClients ++;
				ClientThread mClient = new ClientThread(this,acceptedSocket, numberOfClients);
				
				if(!Clients.containsKey(numberOfClients))
				{
					Clients.put(numberOfClients, mClient);
					ClientSocketTable.put(acceptedSocket, numberOfClients);
				}
				
				new Thread(mClient).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	public void notifyClose(Socket _socket)
	{
		/*
		//check if clients still alive
		Iterator<HashMap.Entry<Socket,Integer>> it = ClientSocketTable.entrySet().iterator();
	    while(it.hasNext())
	    {
	    	HashMap.Entry<Socket,Integer> entry =  it.next();
	    	Socket tmpSocket = entry.getKey();
	    	
	    	if(tmpSocket.isClosed())
	    	{
				int clientId = ClientSocketTable.get(tmpSocket);
				
				if(Clients.containsKey(clientId))
				{
					Clients.remove(clientId);
				}
				
				ClientSocketTable.remove(tmpSocket);
	    	}
	    }
	    */
		
		System.out.println("notify close...");
		if(ClientSocketTable.containsKey(_socket))
		{
			System.out.println("remove socket...");
			int clientId = ClientSocketTable.get(_socket);		
			Clients.remove(clientId);
			ClientSocketTable.remove(_socket);
			
		}
		
		
		
	}
	
}
