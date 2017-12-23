import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

public class Receive extends Thread{
	static String path = "F:/Java_file/";
	Socket socket;
	Receive(Socket socket){
		this.socket = socket;
	}
	public void run (){
		try {
            /** 创建ServerSocket*/
            // 创建一个ServerSocket在端口2013监听客户请求
            //ServerSocket serverSocket =new ServerSocket(2017);
            while (true) {
                // 侦听并接受到此Socket的连接,请求到来则产生一个Socket
//对象，并继续执行
                //socket = serverSocket.accept();
                System.out.println(0);
                BufferedReader bufferedReader =new BufferedReader(
                		new InputStreamReader(socket.getInputStream()));
                // 获取从客户端读入的字符串
                String result = bufferedReader.readLine();
                int index = result.indexOf('/');
                int i = Integer.parseInt(result.substring(0, index));
                System.out.println(result);
                switch(i) {
                case 0:
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String name = result.substring(0,index);
                	String password = result.substring(index+1);
                	User u = DataProcessing.searchUser(name, password);
                	PrintWriter printWriter =new 
    						PrintWriter(socket.getOutputStream());
                	if(!(u == null)) {
                		//System.out.println(u.getID()+"/"+u.getName()+"/"+u.getPassword()+"/"+u.getRole());
		                printWriter.println(u.getID()+"/"+u.getName()+"/"+u.getPassword()+"/"+u.getRole());
		                printWriter.flush();
                	}else{
                        printWriter.print("null");
                        printWriter.flush();
                	}
                	break;
                case 1:
                	try {
                           DataInputStream dis =new DataInputStream(socket.getInputStream());
                                 //文件名和长度
                           String fileName = dis.readUTF();
                           long fileLength = dis.readLong();
                           result = dis.readUTF();
                           System.out.println(result);
                           FileOutputStream fos =new FileOutputStream(new File(path + fileName));
                                  
                           byte[] sendBytes =new byte[1024];
                           int transLen =0;
                           //System.out.println("----开始接收文件<" + fileName +">,文件大小为<" + fileLength +">----");
                           while(true){
                        	   int read =0;
                        	   read = dis.read(sendBytes);
                        	   if(read == -1)
                        		   break;
                        	  // System.out.println(1);
                        	   transLen += read;
                        	   //System.out.println("接收文件进度" +100 * transLen/fileLength +"%...");
                        	   fos.write(sendBytes,0, read);
                        	   fos.flush();
                           }
                           fos.close();
                           //System.out.println(result);
                           index = result.indexOf("/");
                           String creator = result.substring(0, index);
                           String description = result.substring(index+1);
                           Timestamp time = new Timestamp(new Date().getTime());
                           DataProcessing.insertDoc(creator, time, description, fileName);
                	}catch (Exception e) {
                		e.printStackTrace();
                	}
                	break;
                case 2:
                	Doc doc =DataProcessing.searchDoc(result.substring(index+1));
                	DataOutputStream dos =new DataOutputStream(socket.getOutputStream());
                	File file =new File(path + doc.getFilsename());
                	FileInputStream fis =new FileInputStream(file);
                	dos.writeUTF(file.getName());
                    dos.flush();
                    dos.writeLong(file.length());
                    dos.flush();
                    byte[] sendBytes =new byte[1024];
                    int length =0;
                    while((length = fis.read(sendBytes,0, sendBytes.length)) >0){
                        dos.write(sendBytes,0, length);
                        dos.flush();
                    }
                    fis.close();
                    dos.close();
                    break;
                case 3:
                	Enumeration<User> b =DataProcessing.getAllUser();
                	PrintWriter printWriter3 =new 
    						PrintWriter(socket.getOutputStream());
                	while(b.hasMoreElements()) {
        				User a = b.nextElement();
        				printWriter3.println(a.getID()+"/"+a.getName()+"/"+a.getPassword()+"/"+a.getRole());
        				printWriter3.flush();
                	}
                	printWriter3.println("null");
    				printWriter3.flush();
    				break;
                case 4:
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String uname = result.substring(0,index);
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String upassword = result.substring(0,index);
                	String urole = result.substring(index+1);
                	if(DataProcessing.insertUser(uname, upassword, urole)) {
                		PrintWriter printWriter4 =new 
							PrintWriter(socket.getOutputStream());
                		printWriter4.println("ok");
                		printWriter4.flush();
                	}
                	break;
                case 5:
                	String ID = result.substring(index+1);
                	if(DataProcessing.deleteUser(ID)) {
                		PrintWriter printWriter4 =new 
							PrintWriter(socket.getOutputStream());
                		printWriter4.println("ok");
                		printWriter4.flush();
                	}
                	break;
                case 6:
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String id = result.substring(0,index);
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String name6 = result.substring(0,index);
                	result = result.substring(index+1);
                	index = result.indexOf('/');
                	String password6 = result.substring(0,index);
                	String role6 = result.substring(index+1);
                	if(DataProcessing.updateUser(id, name6, password6,role6)) {
                		PrintWriter printWriter4 =new 
							PrintWriter(socket.getOutputStream());
                		printWriter4.println("ok");
                		printWriter4.flush();
                	}
                	break;
                case 7:
                	Enumeration<Doc> a =DataProcessing.getAllDocs();
                	PrintWriter printWriter7 =new 
    						PrintWriter(socket.getOutputStream());
                	DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                	while(a.hasMoreElements()) {
        				Doc c = a.nextElement();
        				printWriter7.println(c.getiD()+"?"+c.getFilsename()+"?"+c.getCreator()+"?"
        						+sdf.format(c.getTimestamp())+"?"+c.getDescription());
        				printWriter7.flush();
                	}
                	printWriter7.println("null");
    				printWriter7.flush();
    				break;
                default:
                		break;
                }
                	bufferedReader.close();
                	socket.close();
                	}
            }catch (Exception e) {
            System.out.println("Exception:" + e);
        }finally{
//          serverSocket.close();
        }
	}

}
