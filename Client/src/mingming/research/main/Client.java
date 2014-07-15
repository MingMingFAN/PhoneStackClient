package mingming.research.main;

import mingming.research.ClientSocket;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ClientSocket clt = new ClientSocket();
		clt.connect2server();
	}

}
