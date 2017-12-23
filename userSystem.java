import java.sql.SQLException;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.*;

public class userSystem extends Frame implements ActionListener{
	private String name;
	private String password;
	private int ID;
	private String role;
	private Socket socket;
	JFrame f = new JFrame();
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	JPanel p3 = new JPanel();
	JLabel wel = new JLabel("欢迎使用!",JLabel.CENTER);
	JTextField inName = new JTextField(20);
	JPasswordField inPassword =  new JPasswordField(20);
	JButton b1 = new JButton("登陆");
	JButton b2 = new JButton("退出");
	
	public userSystem() throws UnknownHostException, IOException {
		Container con =f.getContentPane();
		con.setLayout(new GridLayout(5,1));
		con.add(wel);
		con.add(p1);
		con.add(p2);
		con.add(p3);
		f.setTitle("用户登录");
		f.setBounds(750,300,400,400);
		JLabel a = new JLabel("用户名",JLabel.RIGHT);
		p1.add(a);
		p1.add(inName);
		JLabel b = new JLabel("密 码 ",JLabel.RIGHT);
		p2.add(b);
		inPassword.setEchoChar('*');
		p2.add(inPassword);
		p3.add(b1);
		p3.add(b2);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		b1.addActionListener(this);
		b2.addActionListener(this);
	}
	
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == b1) { 
			try {
				socket =new Socket("localhost",2017);
		        socket.setSoTimeout(60000);
				name = inName.getText();
				password = inPassword.getText();
				PrintWriter printWriter =new 
							PrintWriter(socket.getOutputStream(),true);
				printWriter.println("0/"+name+"/"+password);
				printWriter.flush();
				BufferedReader bufferedReader =new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				String result = bufferedReader.readLine();
				if(result.equals("null")) {
					JOptionPane.showMessageDialog(null,"登陆失败，请检查用户名和密码是否正确","提示",JOptionPane.PLAIN_MESSAGE);
				}else {
					System.out.println(result);
					int index = result.indexOf('/');
					ID = Integer.parseInt(result.substring(0,index));
					result = result.substring(index+1);
					index = result.indexOf('/');
					name = result.substring(0, index);
					result = result.substring(index+1);
					index = result.indexOf('/');
					password = result.substring(0,index);
					role = result.substring(index+1);
				User a = null;
				if(role.equals("administrator")) {
				    	a = new Administrator(ID,name,password,role);
				    }else if (role.equals("operator")) {
				    	a = new Operator(ID,name,password,role);
				    }else if (role.equals("browser")) {
				    	a = new Browser(ID,name,password,role);
				    }
				f.dispose();
				a.showMenu();
				printWriter.close();
		        bufferedReader.close();
		        socket.close();
				}
			}catch(IllegalStateException a) {
				System.out.println("No connect to Database");
			}catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			f.dispose();
		}
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		userSystem u = new userSystem();
	}
}
