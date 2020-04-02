package client;

import java.io.BufferedReader; 
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatWithServer {
	private DataOutputStream dos=null;
	private DataInputStream dis=null;
	private int port=8888;
	private String ip="";
	public Socket socket=null;
	public ChatWithServer(Socket socket) {
		this.socket=socket;
	}
	
	public ChatWithServer() {
		
	}
	
	public ChatWithServer(String ip,int port) {
		this.port=port;
		this.ip=ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Socket BuildChat() throws Exception {
		Socket s=new Socket(ip,port);
		socket=s;
		return s;
	}
	
	public void SendMsg(String msg) {
		try {
			if(dos==null)
				dos=new DataOutputStream(socket.getOutputStream());
			dos.writeUTF(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String GetMsg() {
		String result="";
		try {
			if(dis==null) {
				dis=new DataInputStream(socket.getInputStream());
			}
			result=dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/*public static void main(String[] args) {
		//ChatWithServer chat=new ChatWithServer("127.0.0.1",8888);
		try {
			ChatWithServer chat=new ChatWithServer("127.0.0.1",8888);
			socket=chat.BuildChat();
			chat.SendMsg("[任建,123,123]\n任建");
			System.out.println(chat.GetMsg());
	        while(true) {}
			//chat.SendMsg("大家好,我是任建");
			//System.out.println(chat.GetMsg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
}
