package frames;

import java.awt.Font; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import client.ChatWithServer;
import model.User;
import util.BackGroundPanel;
import util.CloseUtils;
import util.MyExecutor;

public class HellFrame extends JFrame {
	
	private ChatWithServer chat=null;
	private Socket socket=null;
	
	private JTextArea chatConten=null;
	
	private JTextField inputContent=null;
	
	private JButton sendMsg=null;
	
	private DataInputStream dis=null;
	
	private DataOutputStream dos=null;
	
	private User user=null;
	
	private boolean isRunning=true;
	
	private ThreadPoolExecutor executor = MyExecutor.getExecutor();
	//Socket socket
	public HellFrame(Socket socket,User user) {
		ImageIcon icon=new ImageIcon("src/images/first.jpg");
    	this.setIconImage(icon.getImage());
		this.user=user;
		this.socket=socket;
		this.setTitle("大厅");
	    this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    this.setSize(800,700);
	    this.setLocationRelativeTo(null);
	    Image image=new ImageIcon("src/images/bg.jpg").getImage();
        JPanel panel = new BackGroundPanel(image,800,700);
	    this.add(panel);
	    placeComponents(panel);
	    sendButtonListener();
	    CompletableFuture.runAsync(()->{
	    	while(isRunning) {
                try {
                	if(socket.isClosed()) {
                		System.out.println("结束了");
                		isRunning=false;
                		break;
                	}else {
                		String str="";
                		dis=new DataInputStream(socket.getInputStream());
            			try {
            				str=dis.readUTF();
            			}catch (Exception e) {
							// TODO: handle exception
						}
    					chatConten.append(str);
                	}
				} catch (IOException e) {
					e.printStackTrace();
				}
               
            }
	    },executor);
	    
	    chatConten.append("恭喜你成功登录!\n\n");
	    closeListener();
	}
	
	private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);
        JLabel chatPart=new JLabel("大厅聊天区域");
        chatPart.setFont(new Font("宋体", Font.PLAIN, 16));
        chatPart.setBounds(600, 30, 100, 25);
        panel.add(chatPart);
        
        
        chatConten=new JTextArea();
        chatConten.setEditable(false);
        chatConten.setFont(new Font("宋体", Font.PLAIN, 16));
        chatConten.setBounds(450, 70, 330, 500);
        
        JScrollPane scroll = new JScrollPane(chatConten); 
      //把定义的JTextArea放到JScrollPane里面去 

      //分别设置水平和垂直滚动条自动出现 
        scroll.setHorizontalScrollBarPolicy( 
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(450, 70, 330, 500);
        
        scroll.show();
        panel.add(scroll);
        
        inputContent=new JTextField();
        inputContent.setBounds(450, 600, 230, 30);
        panel.add(inputContent);
        
        sendMsg=new JButton("发送");
        sendMsg.setBounds(690, 600, 80, 30);
        panel.add(sendMsg);
        
    }
	
	public void paint(Graphics g) {
	    super.paint(g); 
	    Graphics2D g2 = (Graphics2D) g;
	    Line2D lin = new Line2D.Float(450, 40, 450, 700);
	    g2.draw(lin);
	 }
	
	public void sendButtonListener() {
		sendMsg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(chat==null) {
						chat=new ChatWithServer(socket);
					}
					String msg=inputContent.getText();
					if(msg!=null&&!msg.equals("")) {
						chat.SendMsg(msg);
						inputContent.setText("");
						chatConten.append("你说:"+msg+"\n\n");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
	}
	
	 class ClientConnect implements Runnable{
	        private Socket socket=null;
	        private DataInputStream dis=null;
	       
	        public ClientConnect() {}

	        public ClientConnect(Socket socket) {
	            this.socket=socket;
	            CompletableFuture.runAsync(()->{
	                run();
	            },executor);
	        }

	        @Override
	        public void run() {
	            try {
	                while(true) {
	                    dis=new DataInputStream(socket.getInputStream());
	                    String str=dis.readUTF();
	                }
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }

	        }
	        
	    }
	 
	 
	 	/**
	     * close事件监听
	     */
	    
	    public void closeListener() {
	    	this.addWindowListener(new WindowAdapter() {
	    		public void windowClosing(WindowEvent e) {
	    			System.out.println("关闭操作");
	    			if(chat==null) {
	    				chat=new ChatWithServer(socket);
	    			}
	    			chat.SendMsg("Close");
					CloseUtils.closeAll(socket,dis,dos);
	    			System.exit(0);
	    		}
			});
	    }


}
