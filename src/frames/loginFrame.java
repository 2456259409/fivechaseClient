package frames;

import model.User;
import persistance.Jdbcs;
import util.BackGroundPanel;
import util.MyExecutor;
import util.PropertiesUtil;

import javax.swing.*;

import client.ChatWithServer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.chrono.IsoChronology;
import java.util.concurrent.CompletableFuture;


/**
 * @author : renjian
 */
public class loginFrame extends JFrame {

	private String serverIp=PropertiesUtil.getProperties("server.ip");
	
	private Integer serverPort=Integer.valueOf(PropertiesUtil.getProperties("server.port"));

	private Jdbcs jdbc=null;
    private JButton loginButton = new JButton("登录");
    private JButton registerButton = new JButton("注册");
    private JTextField userText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    
    private static RegisterFrame regframe = null;
    
    private loginFrame login=this;
    
    
    /**
     * close事件监听
     */
    
    public void closeListener() {
    	this.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    			System.out.println("关闭操作");
    			System.exit(0);
    		}
		});
    }
    /**
     * 登录监听
     */
    public void loginListener() {
    	this.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username=userText.getText();
                String password=passwordText.getText();
                if(username==null||username.equals("")||password==null||password.equals("")) {
                	JOptionPane.showMessageDialog(null,"用户名或密码不能为空");
                }else {
                	User user=null;
                    try {
                        user=jdbc.getBynameAndPass(username,password);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    if(user==null){
                    	JOptionPane.showMessageDialog(null,"登录失败");
                    }else { 
                    	ChatWithServer chat=new ChatWithServer(serverIp,(int)serverPort);
                    	try {
							HellFrame hellFrame=new HellFrame(chat.BuildChat(),user);
							chat.SendMsg(user.toString());
							hellFrame.setVisible(true);
							dispose();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
                    }
                }
            }
        });
    }
    
    public void registerListen() {
    	registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
				regframe=new RegisterFrame(jdbc,login);
				regframe.setVisible(true);
				
			}
		});
    }

    public loginFrame(Jdbcs jdbc){
    	
    	ImageIcon icon=new ImageIcon("src/images/first.jpg");
    	this.setIconImage(icon.getImage());
    	this.jdbc=jdbc;
        this.setTitle("登录");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(350,300);
        Image image=new ImageIcon("src/images/bg4.jpg").getImage();
        JPanel panel = new BackGroundPanel(image,350,300);
        // 添加面板
        this.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        this.setLocationRelativeTo(null);
        loginListener();
        registerListen();
        closeListener();
        
    }

    private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("用户名:");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,50,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */

        userText.setBounds(100,50,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(10,80,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */

        passwordText.setBounds(100,80,165,25);
        panel.add(passwordText);

        // 创建登录按钮

        loginButton.setBounds(60, 150, 80, 25);
        panel.add(loginButton);

        //创建注册按钮

        registerButton.setBounds(200, 150, 80, 25);
        panel.add(registerButton);
    }
    
    public JButton getLoginButton() {
		return loginButton;
	}


	public void setLoginButton(JButton loginButton) {
		this.loginButton = loginButton;
	}


	public JButton getRegisterButton() {
		return registerButton;
	}


	public void setRegisterButton(JButton registerButton) {
		this.registerButton = registerButton;
	}


	public JTextField getUserText() {
		return userText;
	}


	public void setUserText(JTextField userText) {
		this.userText = userText;
	}


	public JPasswordField getPasswordText() {
		return passwordText;
	}


	public void setPasswordText(JPasswordField passwordText) {
		this.passwordText = passwordText;
	}

	

}