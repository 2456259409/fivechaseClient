package persistance;

import model.User;
import util.PropertiesUtil;

import java.sql.*;
/**
 *数据库操作
 *
 */
import java.util.Properties;

import javax.swing.JOptionPane;

public class Jdbcs {
    Connection con = null;
    Statement statement = null;
    PreparedStatement prestatement=null;
    ResultSet res = null;
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://"+PropertiesUtil.getProperties("mysql.ip")+":3306/fivechase?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&serverTimezone=UTC";
    String name = PropertiesUtil.getProperties("mysql.username");
    String passwd = PropertiesUtil.getProperties("mysql.password");

    public Jdbcs() {
        try {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url, name, passwd);
            statement = con.createStatement();

        } catch (ClassNotFoundException e) {
            System.out.println("对不起，找不到这个Driver");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   

    //用户注册功能的实现，添加数据
    public void insert(String username, String password,String nickname) {
        if (username == null || username.trim().length() <= 0) {
            JOptionPane.showMessageDialog(null, "注册账号为空，请重新输入！");
            return;
        }
        String sql = "insert into user(username,password,nickname) values(?,?,?)";
        try {
            prestatement=con.prepareStatement(sql);
            prestatement.setString(1, username);
            prestatement.setString(2, password);
            prestatement.setString(3, nickname);
            boolean judge=prestatement.execute();
            if (judge) {
                JOptionPane.showMessageDialog(null, "注册成功！");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "对不起该用户名已经有了！");
            e.printStackTrace();
        }
    }

    public User getBynameAndPass(String username,String password) throws Exception{
        String sql="select * from user where username=? and password=?";
        prestatement=con.prepareStatement(sql);
        prestatement.setString(1,username);
        prestatement.setString(2,password);
        res=prestatement.executeQuery();
        User user=null;
        if(res==null){
            System.out.println("error!!!!!!!!!!!!=============");
        }else {
            user=new User();
        }
        while(res.next()){
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setId(res.getLong("id"));
            user.setNickname(res.getString("nickname"));
        }
        if(user.getId()==null) {
        	user=null;
        }
        
        return user;
    }
    
    public User getByUsername(String username) throws Exception{
        String sql="select * from user where username=?";
        prestatement=con.prepareStatement(sql);
        prestatement.setString(1,username);
        res=prestatement.executeQuery();
        User user=null;
        if(res==null){
            System.out.println("error!!!!!!!!!!!!=============");
        }else {
            user=new User();
        }
        while(res.next()){
            user.setUsername(res.getString("username"));
            user.setPassword(res.getString("password"));
            user.setId(res.getLong("id"));
            user.setNickname(res.getString("nickname"));
        }
        if(user.getId()==null) {
        	user=null;
        }
        
        return user;
    }


}

