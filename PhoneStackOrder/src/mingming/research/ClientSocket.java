package mingming.research;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket implements Runnable{

	Socket socket;
	PrintWriter out;
	BufferedReader in;
	public volatile String text="";

	public static volatile boolean running = false;
	public ClientSocket(){
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {

			while(running)
			{
				socket = new Socket(Utils.server_ip, Utils.port);
				out = new PrintWriter(socket.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if(in.ready())
				{
					String content = null;
					content = in.readLine();
					
					if(content != null)
					{
						text = content;
						//System.out.println("receiving message from server: " + content);
					}
				}
				
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
