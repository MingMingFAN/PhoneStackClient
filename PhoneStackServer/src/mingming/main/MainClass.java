package mingming.main;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import mingming.research.SocketServer;

public class MainClass extends JFrame implements ActionListener{

	
	JPanel pane ;
	JButton startBt ;
	
	JButton forwardBt;
	JButton backwardBt;
	JButton turnLeftBt;
	JButton turnRightBt;
	JButton stopmotionBt;
	
	
	JButton stopBt;
	
	SocketServer mSocketServer;
	
	
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
		
		
		forwardBt = new JButton("Camera View");
		forwardBt.setLocation(125,60);
		forwardBt.setSize(150,30);
		pane.add(forwardBt);
		forwardBt.addActionListener(this);
		forwardBt.setEnabled(true);
		
		
		backwardBt = new JButton("Solid Color");
		backwardBt.setLocation(125,180);
		backwardBt.setSize(150,30);
		pane.add(backwardBt);
		backwardBt.addActionListener(this);
		backwardBt.setEnabled(true);
		
	
		
		stopBt = new JButton("Disconnect");
		stopBt.setLocation(250,0);
		stopBt.setSize(100,30);
		pane.add(stopBt);
		stopBt.addActionListener(this);
		stopBt.setEnabled(false);
		

		
		setVisible(true);
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MainClass();
	   

	}

	 private void startServer()
	 {
		mSocketServer = new SocketServer();
		Thread serverThread = new Thread(mSocketServer);
		serverThread.start();
	 }
	 
	 private void stopServer()
	 {
		 mSocketServer.stopServer();
	 }


	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		Object source = event.getSource();
		if(source == startBt)
		{
			startServer();
			startBt.setEnabled(false);
			stopBt.setEnabled(true);
			startBt.setBackground(Color.gray);
			stopBt.setBackground(Color.green);
		}
		else if(source == stopBt)
		{
			stopServer();
			startBt.setEnabled(true);
			stopBt.setEnabled(false);
			startBt.setBackground(Color.green);
			stopBt.setBackground(Color.gray);
		}
		else if(source == forwardBt)
		{
			if(mSocketServer != null)
			{
				//System.out.println("message is: L");
				mSocketServer.updateMessage("1");
			}
		}
		else if(source == backwardBt)
		{
			if(mSocketServer != null)
			{
				//System.out.println("message is: B");
				mSocketServer.updateMessage("0");
			}
		}

	}

}
