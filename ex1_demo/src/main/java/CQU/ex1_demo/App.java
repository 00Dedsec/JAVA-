package CQU.ex1_demo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//（1）实现文件夹创建、删除；
//（2）实现当前文件夹下的内容罗列；
//（3）实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）；
//（4）实现指定文件的加密和解密；
//（5）使用Eclipse、NetBeans或Intellj作为开发环境，使用Maven管理项目，使用Git进行源代码控制；
//（6）在实验报告中，对软件需求进行规范、详细的描述。
import java.util.Scanner;

public class App 
{
	//private static String datapath;
	private void makeDir() {
		System.out.println("请输入文件夹路径:");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		File dir = new File(path);
        if (!dir.exists()) {// 判断目录是否存在     
            dir.mkdir();
            System.out.println("文件夹创建成功");
        }
        else
        	System.out.println("文件夹已存在！");
        
        //sin.close();
	}
	
	private void deleteDir() {
		System.out.println("请输入文件夹路径:");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		
		File file = new File(path);
		
		if(file.isDirectory() == false || file.exists() ==false)
			System.out.println("不是文件夹或者文件夹不存在！");
		else {
			System.out.println("正在删除...");
			if(deleteDir_help(file))
				System.out.println("删除成功...");
		}
		
		//sin.close();
	}
	
	private boolean deleteDir_help(File dirFile) {
		File[] files = dirFile.listFiles();
		for(int i = 0; i < files.length; i++) {
			if(files[i].isFile() == true)
				files[i].delete();
			else
				deleteDir_help(files[i]);
		}
		if(dirFile.delete())
			return true;
		return false;
	}
	
	private void listDir() {
		System.out.println("请输入文件夹路径：");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		File dirFile = new File(path);
		if(dirFile.isDirectory() == false || dirFile.exists() == false)
			System.out.println("不是文件夹或者文件夹不存在！");
		else {
			System.out.println("在目录文件" + dirFile.getAbsoluteFile() + "下：");
			listDir_help(dirFile);
			System.out.println(" ");
		}
		//sin.close();
	}
	
	private void listDir_help(File dirFile) {
		File[] files= dirFile.listFiles();
		for(int i = 0 ; i < files.length; i++) {
			if(files[i].isDirectory()) {
				System.out.print("目录文件：" + files[i].getName() + "  ");
			}
			else
				System.out.print("普通文件: " + files[i].getName() + "  ");
		}
	}

	private void copy_dir(String path,String desPath) throws IOException {
		File sFile = new File(path);
		//desPath = desPath+ File.separator + sFile.getName();
		File dFile = new File(desPath);
		String[] file = sFile.list();		//获取该文件夹下的所有文件以及目录的名字
		if(!dFile.exists()) {
			dFile.mkdir();
		}
		for(String temp:file)
			if(new File(path + File.separator + temp).isDirectory()) {//文件夹
				copy_dir(path + File.separator + temp, desPath  + File.separator + temp);
			}
			else {//普通文件
				copy_file(path + File.separator + temp, desPath + File.separator + temp);
			}
	}
	
	private void copy_file(String path,String desPath) throws IOException {
		File file = new File(path);
		FileReader f = new FileReader(file);
		FileWriter fout = new FileWriter(desPath);
		
		
		//int n = (int)(file.length()/10); 文件内容必须大于10，否则n = 0，引起死循环
		int n = (int)(file.length());
		char[] b = new char[n];
		int count = 0;
		while((count = f.read(b,0,n)) != -1) 
			fout.write(b,0,count);
		
		f.close();
		fout.close();
	}
	
	
	private void copy() throws IOException {
		System.out.println("请输入目标文件夹或者文件路径：");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		File file = new File(path);
		
		System.out.println("请目标路径：");
		String desPath = sin.nextLine();
		if(file.exists() == false)
			System.out.println("不存在！");
		else if(file.isDirectory()) {
			System.out.println("文件夹正在拷贝...");
			File dirFile = new File(desPath + File.separator + file.getName());
			if(dirFile.exists())
				System.out.println("文件夹已存在！");
			else {
				copy_dir(path,desPath + File.separator + file.getName());
				System.out.println("文件夹拷贝完成...");
			}
		}
		else {
			System.out.println("文件正在拷贝...");
			copy_file(path,desPath);
			System.out.println("文件拷贝完成...");
		}
		//sin.close();
		
	}
	
	private void EncryptFile() throws IOException {
		System.out.println("请输入需要加密的文件路径：");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		File file = new File(path);
		
		System.out.println("请目标路径：");
		String desPath = sin.nextLine();
		
		if(file.isFile() == false || file.exists() == false)
			System.out.println("无法加密！");
		else
		{
			byte pwd = 123;
			FileInputStream fin = new FileInputStream(file);
			FileOutputStream fout = new FileOutputStream(desPath);
			System.out.println("开始加密...");
			int n = fin.available();
			byte buf[] = new byte[n];
			int count = 0;
			while((count = fin.read(buf,0,n)) != -1) {
				for(int i = 0; i < count; i++)
					buf[i] = (byte)(buf[i] ^ pwd);
				fout.write(buf, 0, n);
			}
			System.out.println("加密成功");
		}
	}
	
	private void Unencrypt() throws IOException {
		System.out.println("请输入需要解密密的文件路径：");
		Scanner sin = new Scanner(System.in);
		String path = sin.nextLine();
		File file = new File(path);
		
		System.out.println("请目标路径：");
		String desPath = sin.nextLine();
		
		if(file.isFile() == false || file.exists() == false)
			System.out.println("无法解密！");
		else
		{
			byte pwd = 123;
			FileInputStream fin = new FileInputStream(file);
			FileOutputStream fout = new FileOutputStream(desPath);
			System.out.println("开始解密...");
			int n = fin.available();
			byte buf[] = new byte[n];
			int count = 0;
			while((count = fin.read(buf,0,n)) != -1) {
				for(int i = 0; i < count; i++)
					buf[i] = (byte)(buf[i] ^ pwd);
				fout.write(buf, 0, n);
			}
			System.out.println("解密成功");
		}
	}
	
	public int function(String a) throws IOException {
		int end = 0;
		if(a.equals("1"))
			makeDir();
		else if(a.equals("2"))
			deleteDir();
		else if(a.equals("3"))
			listDir();
		else if(a.equals("4"))
			copy();
		else if(a.equals("5"))
			EncryptFile();
		else if(a.equals("6"))
			Unencrypt();
		else if(a.equals("7")) {
			System.out.println("已退出");
			end = 1;}
		else
			System.out.println("输入错误！请重新输入：");		
		return end;
	}
	
    public static void main( String[] args ) throws IOException
    {
    	//datapath = "D:/";
        //System.out.println("当前目录为：" + datapath);
    	Scanner sin = new Scanner(System.in);
    	while(true) {
    	System.out.println("请选择功能：");
    	System.out.println( "1. 实现文件夹创建" );
    	System.out.println( "2. 实现文件夹删除" );
    	System.out.println( "3. 实现当前文件夹下的内容罗列；" );
    	System.out.println( "4. 实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）；" );
    	System.out.println( "5. 实现指定文件的加密；" );
    	System.out.println( "6. 实现指定文件的解密；" );
    	System.out.println( "7. 退出选择" );
    	App app = new App();
    	String a = sin.nextLine();
    	if(app.function(a) == 1)
    		break;
    	}
    	sin.close();
    }
}
