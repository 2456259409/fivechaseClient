package client;

import frames.loginFrame;  
import persistance.Jdbcs;

public class Client {
	
	private static Jdbcs jdbc=new Jdbcs();
	
    public static void main(String[] args) throws Exception{
        loginFrame login=new loginFrame(jdbc);
        login.setVisible(true);
    }
}
