package mingming.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mingming.research.Server;
import mingming.research.SocketServer;

public class MainClass extends JFrame implements ActionListener{

	
	JPanel pane ;
	JButton startBt ;
	JButton stopBt;
	
	JButton cameraViewBt;
	JButton solidColorBt;
	
	JButton activeClientBt;
	
	
	//SocketServer mSocketServer;
	Server mServer;
	Thread serverThread;
	
	MainClass()
	{
		super("Phone Stack Server");
		setBounds(100,100,400,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pane = new JPanel();
		pane.setLayout(null);
		Container con = this.getContentPane();
		con.add(pane);
		
		startBt = new JButton("Connect");
		startBt.setLocation(50,0);
		startBt.setSize(100,30);
		pane.add(startBt);
	    startBt.addActionListener(this);
		startBt.setEnabled(true);
		startBt.setBackground(Color.green);
		
		
		stopBt = new JButton("Disconnect");
		stopBt.setLocation(250,0);
		stopBt.setSize(100,30);
		pane.add(stopBt);
		stopBt.addActionListener(this);
		stopBt.setEnabled(false);
		
		
		cameraViewBt = new JButton("Camera View");
		cameraViewBt.setLocation(125,60);
		cameraViewBt.setSize(150,30);
		pane.add(cameraViewBt);
		cameraViewBt.addActionListener(this);
		cameraViewBt.setEnabled(true);
		
		
		solidColorBt = new JButton("Solid Color");
		solidColorBt.setLocation(125,120);
		solidColorBt.setSize(150,30);
		pane.add(solidColorBt);
		solidColorBt.addActionListener(this);
		solidColorBt.setEnabled(true);
		
	
		activeClientBt = new JButton("Active Clients");
		activeClientBt.setLocation(125,180);
		activeClientBt.setSize(150,30);
		pane.add(activeClientBt);
		activeClientBt.addActionListener(this);
		activeClientBt.setEnabled(true);
		

		
		setVisible(true);
	}
	
	 private void startServer()
	 {
		 /*
		mSocketServer = new SocketServer();
		Thread serverThread = new Thread(mSocketServer);
		serverThread.start();
		*/
		 
		 mServer =  new Server();
		 serverThread = new Thread(mServer);
		 serverThread.start();
	 }
	 
	 private void stopServer()
	 {
		 serverThread.interrupt();
		 serverThread.stop();
		 mServer.stopServer();
	 }
	 
	 private int getNumberOfClients()
	 {
		 return mServer.getNumberOfClients();
	 }
	 
	 private void sendMessage(int clientId, String msg)
	 {
		 mServer.sendMessage(clientId, msg);
	 }

	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		if(source == startBt)
		{
			startBt.setEnabled(false);
			stopBt.setEnabled(true);
			startBt.setBackground(Color.gray);
			stopBt.setBackground(Color.green);
			
			startServer();
		}
		else if(source == stopBt)
		{
			startBt.setEnabled(true);
			stopBt.setEnabled(false);
			startBt.setBackground(Color.green);
			stopBt.setBackground(Color.gray);		
			stopServer();
		}
		else if(source == cameraViewBt)
		{
			int numAllClients = getNumberOfClients();
			for(int i = 1; i <= numAllClients; i++)
			{
				mServer.sendMessage(i, "1");
			}
		}
		else if(source == solidColorBt)
		{
			int numAllClients = getNumberOfClients();
			for(int i = 1; i <= numAllClients; i++)
			{
				mServer.sendMessage(i, "0");
			}
		}
		else if(source == activeClientBt)
		{
			int activeNumberClients = mServer.getNumberOfActiveClients();
			System.out.println("# of active clients: " + activeNumberClients);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MainClass();
	}

}
