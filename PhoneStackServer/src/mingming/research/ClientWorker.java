package mingming.research;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable {

	private Socket client;
	private int id;
	
	public int showPic = 0;
	
	ClientWorker(Socket client, int client_id){
		this.client = client;
		this.id = client_id;
	}
	

	public void run() {
		// TODO Auto-generated method stub

		String line;
		BufferedReader  in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("in or out failed...");
			e.printStackTrace();
		}

		while(true)
		{
			try {
				if(in.ready())
				{
					line = in.readLine();
					System.out.println("thread " + id + " received: " + line);
					
					out.println("welcome! thread " + id);
					out.flush();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
	}

}
