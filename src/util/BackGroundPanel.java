package util;

import java.awt.*;   
import javax.swing.JPanel;  
public class BackGroundPanel extends JPanel {    
	private static final long serialVersionUID = -6352788025440244338L;        
	private Image image = null; 
	private int width;
	private int height;
	public BackGroundPanel(Image image,int width,int height) {  
		this.image = image;  
		this.width=width;
		this.height=height;
	}  
 // 固定背景图片，允许这个JPanel可以在图片上添加其他组件  
	protected void paintComponent(Graphics g) {  
		g.drawImage(image, 0, 0, width, height, this);  
    }  
 }
