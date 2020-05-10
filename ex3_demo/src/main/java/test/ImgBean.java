package test;

import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImgBean {
	private int length;
	private int width;
	private int height;
	private StringBuffer str;
	public ImgBean() {
		this.length = 4;
		this.width = 100;
		this.height = 30;
		this.str = new StringBuffer();
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public String getStr() {
		return this.str.toString();
	}
	
	//生成随机数
	public void generateRandomStr() {
		int i = 0;
		int t = 0;
		Random r = new Random();
		for(i = 0; i < length; i++) {
			t = r.nextInt(9);
			this.str.append(t);
		}
	}
	
	//生成验证码图片
	public BufferedImage generateImg() {
		Random r = new Random();
		
		BufferedImage img  = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.width, this.height);
		g.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,(int)(this.height*0.8)));
		
		//写验证码
		int x = 0;
		int y = (int)(this.height*0.8);
		for(int i = 0; i < this.length; i++) {
			g.setColor(new Color(20 + r.nextInt(200), 20 + r.nextInt(200), 20 + r.nextInt(200)));
			g.drawString(String.valueOf(this.str.charAt(i)), x, y);
			//System.out.println(this.length);
			x+=(this.width / this.length) * (Math.random() * 0.3 + 0.8);
		}
		
		//设置干扰
		int n = 0;//绘制5条干扰线
		while(n < 4) {
			int y1 = this.height/(r.nextInt(10)+1);
			int y2 = this.height/(r.nextInt(10)+1);
			g.setColor(new Color(20 + r.nextInt(200), 20 + r.nextInt(200), 20 + r.nextInt(200)));
			g.drawLine(0, y1, this.width, y2);
			n++;
		}
		
		//设置20个噪点
		for(int i = 0; i < 20; i++) {
			int xx = r.nextInt(this.width);
			int yy = r.nextInt(this.height);
			img.setRGB(xx, yy, new Color(20 + r.nextInt(200), 20 + r.nextInt(200), 20 + r.nextInt(200)).getRGB());
		}
		
		g.dispose();
		
		return img;
	}
	
	public static void main(String[] args) throws IOException {
		ImgBean img = new ImgBean();
		//img.setLength(4);
		img.generateRandomStr();
		System.out.println(img.str);
		File file = new File("d:/test01.jpg");
		ImageIO.write(img.generateImg(),"jpg",file);
		System.out.println("成功保存到："+file.getAbsolutePath());
	}
	
}
