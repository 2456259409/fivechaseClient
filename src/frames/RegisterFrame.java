package frames;

import model.User;
import persistance.Jdbcs;
import util.BackGroundPanel;
import util.MyExecutor;
import javax.swing.*;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.CompletableFuture;


/**
 * @author : renjian
 */
public class RegisterFrame extends JFrame {


	private loginFrame login=null;
    
    private JButton submitButton = new JButton("提交");
    private JTextField userText = new JTextField(20);
    private JPasswordField passwordText = new JPasswordField(20);
    private JTextField nickNameText = new JTextField(20);
    private Jdbcs jdbc=null;

    public RegisterFrame(Jdbcs jdbc,loginFrame login){
    	
    	ImageIcon icon=new ImageIcon("src/images/first.jpg");
    	this.setIconImage(icon.getImage());
    	this.jdbc=jdbc;
    	this.login=login;
        this.setTitle("注册");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(350,350);
        Image image=new ImageIcon("src/images/bg2.jpg").getImage();
        JPanel panel = new BackGroundPanel(image,350,350);
        // 添加面板
        this.add(panel);
        /*
         * 调用用户定义的方法并添加组件到面板
         */
        placeComponents(panel);

        this.setLocationRelativeTo(null);
        registerListener();
       
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
        
        /**
         * 昵称
         */
        JLabel nicknameLabel=new JLabel("昵称:");
        nicknameLabel.setBounds(10, 110, 80, 25);
        panel.add(nicknameLabel);
        
        /**
         * 昵称输入域
         */
        nickNameText.setBounds(100, 110, 165, 25);
        panel.add(nickNameText);


        //创建注册按钮

        submitButton.setBounds(140, 160, 80, 25);
        panel.add(submitButton);
    }
   


	public JButton getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(JButton submitButton) {
		this.submitButton = submitButton;
	}

	public JTextField getNickNameText() {
		return nickNameText;
	}

	public void setNickNameText(JTextField nickNameText) {
		this.nickNameText = nickNameText;
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
	/**
	 * 监视注册
	 */
	
	public void registerListener() {
		this.getSubmitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String username=userText.getText();
				String password=passwordText.getText();
				String nickname=nickNameText.getText();
				if(username==null||username.equals("")||password==null||password.equals("")||nickname==null||nickname.equals("")) {
					JOptionPane.showMessageDialog(null,"请填写完整!");
				}else {
					try {
						if(jdbc.getByUsername(username)==null) {
							jdbc.insert(username, password, nickname);
							login.setVisible(true);
							dispose();
						}else {
							JOptionPane.showMessageDialog(null,"该用户已经存在!");
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
	}
	
}