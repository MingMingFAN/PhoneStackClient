package mingming.research;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	Socket socket;
	PrintWriter out;
	BufferedReader in;
	
	public ClientSocket(){}
	
	public void connect2server()
	{
		try {
			socket = new Socket(Utils.server_ip, Utils.port);
			out = new PrintWriter(socket.getOutputStream(),true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(!socket.isClosed())
			{
				System.out.println("Connected to server...");
				if(in != null &&  in.ready())
				{
					String content = null;
					content = in.readLine();
					
					if(content != null)
					{
						System.out.println("receiving message from server: " + content);
					}
				}
				
				if(out != null)
				{
					out.println("data from client...");
					out.flush();
				}
				
			}
			
			try {
				if(in != null)
					in.close();
				if(out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
