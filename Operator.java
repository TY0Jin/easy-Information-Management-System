import java.sql.SQLException;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
public class Operator extends User implements ActionListener {
	private String name ;
	private String password;
	private String role;
	private int ID;
	Socket socket;
	String path1 = null;
	String path2 = "F:\\Java_file\\";
	private JTabbedPane tp = new JTabbedPane();
	JFrame f = new JFrame();
	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel(new GridLayout(4,1));
	JPanel p3 = new JPanel(new GridLayout(4,1));
	Container c = f.getContentPane();
	JMenuBar b = new JMenuBar();
	JMenu m1 = new JMenu("�ļ�����");
	JMenu m3 = new JMenu("����ϵͳ");
	JMenuItem m11 = new JMenuItem("�ļ��б�");
	JMenuItem m12 = new JMenuItem("�ļ��ϴ�");
	JMenuItem m13 = new JMenuItem("�ļ�����");
	JMenuItem m31 = new JMenuItem("��ǰ�汾");
	JButton b1 = new JButton("�ļ��б�");
	JButton b3 = new JButton("�޸��˻�");
	JButton b4 = new JButton("�˳���¼");
	Label l = new Label ("��ӭʹ��",JLabel.CENTER);
	String [][][][][] str1 = {};
	String [] str2 = {"I D","�ļ���","������","�ļ�����","����ʱ��"};
	private DefaultTableModel model1 = new DefaultTableModel(str1,str2);
	private JTable table1 = new JTable(model1);
	String address;
	String ID_df = null;
	JTextArea t21 = new JTextArea("�û��б�",50,20);
	JScrollPane sp1 = new JScrollPane(table1);
	public Operator (int ID,String name , String password , String role) {
		this.ID = ID;
		this.name = name;
		this.password = password;
		this.role = role;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getName() {
		return name;
		
	}
	public String getPassword() {
		return password;
	}
	public String getRole() {
		return role;
	}
	public int getID() {
		return ID;
	}
	public void showMenu() {
		f.setContentPane(p1);
		p1.setLayout(null);
		m1.add(m11);m1.add(m12);m1.add(m13);
		m3.add(m31);
		//m1.addSeparator();m2.addSeparator();m3.addSeparator();
		table1.setPreferredScrollableViewportSize(new Dimension(500,500));
		b.add(m1);b.add(m3);
		l.setFont(new Font("����",Font.BOLD, 80));
		l.setBounds(580, 10, 330, 100);
		p1.add(l);
		sp1.setBounds(350,140,800,500);
		sp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		p1.add(sp1);
		p1.add(b1);p1.add(b3);p1.add(b4);
		b1.setBounds(80, 150,100,40);b3.setBounds(80,250,100,40);b4.setBounds(80, 540, 100, 40);
		b1.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		m11.addActionListener(this);m12.addActionListener(this);
		m13.addActionListener(this);
		m31.addActionListener(this);
		f.setJMenuBar(b);
		f.setBounds(400,150,1200, 800);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==m11 || e.getSource() == b1) {
			showFileList();
		}else if(e.getSource() == m12) {
			int result = 0;
			File file = null;
			Component chatFrame = null;
			JFileChooser fileChooser = new JFileChooser();
			FileSystemView fsv = FileSystemView.getFileSystemView();
			fileChooser.setCurrentDirectory(fsv.getHomeDirectory());
			fileChooser.setDialogTitle("��ѡ��Ҫ�ϴ��ļ���·��");
			fileChooser.setApproveButtonText("ȷ��");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			result = fileChooser.showOpenDialog(chatFrame);
			if (JFileChooser.APPROVE_OPTION == result) {
				path1=fileChooser.getSelectedFile().getAbsolutePath();
				uploadFile();
			}
		}else if(e.getSource() == m13) {
			ID_df = JOptionPane.showInputDialog("�����ļ�ID");
			if(ID_df != null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.showSaveDialog(null);
			address = chooser.getSelectedFile().getAbsolutePath();
			address = address.concat("\\");
			downLoadFile();
			}
		}else if(e.getSource() == b3) {
			changeSelfInfo();
		}else if(e.getSource() == m31) {
			JOptionPane.showMessageDialog(null,"�汾�ţ�1.0.0.0","��ǰ�汾",JOptionPane.PLAIN_MESSAGE);
		}else if(e.getSource() == b4) {
			f.dispose();
			try {
				userSystem u = new userSystem();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	public void showFileList() {
		try {
			Enumeration<Doc> doc = null;
			model1.setRowCount(0);
			socket =new Socket("localhost",2017);
			socket.setSoTimeout(60000);
			PrintWriter printWriter =new 
					PrintWriter(socket.getOutputStream(),true);
			printWriter.println("7/");
			printWriter.flush();
			BufferedReader bufferedReader =new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String result = bufferedReader.readLine();
			model1.setRowCount(0);
			int index ;
			System.out.println(result);
			while(true) {
				Vector<String> v = new Vector<String>();
				index = result.indexOf('?');
				String id = result.substring(0, index);
				v.addElement(id);
				result = result.substring(index+1);
				index = result.indexOf('?');
				String filename = result.substring(0,index);
				v.addElement(filename);
				result = result.substring(index+1);
				index = result.indexOf('?');
				String creator = result.substring(0,index);
				v.addElement(creator);
				result = result.substring(index+1);
				index = result.indexOf('?');
				String time = result.substring(0,index);
				v.addElement(time);
				String description = result.substring(index+1);
				v.addElement(description);
				model1.addRow(v);
				result = bufferedReader.readLine();
				if(result.equals("null"))
					break;
			}
			printWriter.close();
	        bufferedReader.close();
	        socket.close();
		}catch(IllegalStateException e) {
			JOptionPane.showMessageDialog(null,"�޷����ӵ����ݿ⣬������","��ʾ",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public boolean downLoadFile() {
		try {
			socket =new Socket("localhost",2017);
			socket.setSoTimeout(60000);
			PrintWriter printWriter =new 
					PrintWriter(socket.getOutputStream(),true);
			printWriter.println("2/"+ID_df);
			printWriter.flush();
			DataInputStream dis =new DataInputStream(socket.getInputStream());
			String fileName = dis.readUTF();
            long fileLength = dis.readLong();
            FileOutputStream fos =new FileOutputStream(new File(address + fileName));
                   
            byte[] sendBytes =new byte[1024];
            int transLen =0;
            //System.out.println("----��ʼ�����ļ�<" + fileName +">,�ļ���СΪ<" + fileLength +">----");
            while(true){
         	   int read =0;
         	   read = dis.read(sendBytes);
         	   if(read == -1) {
         		  JOptionPane.showMessageDialog(null,"���سɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE );
         		   break;
         	   }
         	  // System.out.println(1);
         	   transLen += read;
         	   //System.out.println("�����ļ�����" +100 * transLen/fileLength +"%...");
         	   fos.write(sendBytes,0, read);
         	   fos.flush();
            }
            fos.close();
			
		}catch(IllegalStateException e) {
			JOptionPane.showMessageDialog(null,"�޷����ӵ����ݿ⣬������","��ʾ",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
}
	public void changeSelfInfo() {
		try {
			
			int result = JOptionPane.showConfirmDialog(null, "�Ƿ��޸��û���");
			if(result == 1) {
				name = JOptionPane.showInputDialog("�������޸ĺ���û�����");
			}
			result = JOptionPane.showConfirmDialog(null, "�Ƿ��޸�����");
			if(result == 1) {
				password = JOptionPane.showInputDialog("�������޸ĺ�����룺");
			}
			socket = new Socket("localhost",2017);
			socket.setSoTimeout(60000);
			PrintWriter printWriter = new
					PrintWriter(socket.getOutputStream(),true);
			printWriter.println("6/"+ID+"/"+name+"/"+password+"/"+role);
			printWriter.flush();
			BufferedReader bufferedReader =new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			String result6 = bufferedReader.readLine();
			if(result6.equals("ok"))
				JOptionPane.showMessageDialog(null,"�û��޸ĳɹ���","��ʾ",JOptionPane.PLAIN_MESSAGE);
			else
				JOptionPane.showMessageDialog(null,"�û��޸�ʧ�ܣ�������","��ʾ",JOptionPane.PLAIN_MESSAGE);
		}
		catch (IllegalStateException e) {
			JOptionPane.showMessageDialog(null,"�޷����ӵ����ݿ⣬������","��ʾ",JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean uploadFile() {
		try {
			socket =new Socket("localhost",2017);
			socket.setSoTimeout(60000);
			String description = JOptionPane.showInputDialog("�������ϴ��ļ�������");
			String filename = path1.substring(path1.lastIndexOf("\\")+1);
			String creator = name;
			File file =new File(path1);
			FileInputStream fis =new FileInputStream(file);
			DataOutputStream dos =new DataOutputStream(socket.getOutputStream());
            //�ļ����ͳ���
			PrintWriter printWriter =new 
					PrintWriter(socket.getOutputStream(),true);
			printWriter.println("1/");
			printWriter.flush();
            dos.writeUTF(file.getName());
            dos.flush();
            dos.writeLong(file.length());
            dos.flush();
            dos.writeUTF(creator+"/"+description);
            dos.flush();
            //�����ļ�
            byte[] sendBytes =new byte[1024];
            int length =0;
            while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
                dos.write(sendBytes,0, length);
                dos.flush();
            }
            JOptionPane.showMessageDialog(null,"�ϴ��ɹ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
            fis.close();
            dos.close();
			return true;
        }catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"�ϴ�ʧ�ܣ�������������","��ʾ",JOptionPane.PLAIN_MESSAGE);
			return false;
        }
			//new Client(path1);
			
	}
}
