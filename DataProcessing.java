import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.*;



public  class DataProcessing {
	private static Connection con;
	private Statement st;
	private ResultSet rs;
	private static String strCon = "jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8";
	private static String strUser = "root";
	private static String strPwd = "152340";
	static PreparedStatement stmt = null;
//	private int numberofRows;
	private static boolean connectToDB=false;
	
	//static Hashtable<String, User> users;
	//static Hashtable<String, Doc> docs;

	static {
		
		/*users = new Hashtable<String, User>();
		users.put("jack", new Operator("jack","123","operator"));
		users.put("rose", new Browser("rose","123","browser"));
		users.put("kate", new Administrator("kate","123","administrator"));*/
		try {
			Init();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Timestamp timestamp = new Timestamp(System.currentTimeMillis()); 
		//docs = new Hashtable<String,Doc>();
		//docs.put("0001",new Doc("0001","jack",timestamp,"Age Source Java","Age.java"));
		
		
	}
	
	public static  void Init() throws SQLException, ClassNotFoundException{
		// connect to database
		//con = DriverManager.getConnection( "url", "username", "password" );

		// create Statement to query database
		//st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY );

		// update database connection status
		
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection(strCon,strUser,strPwd);
	}
	
	public static Doc searchDoc(String ID) throws SQLException,IllegalStateException {
		PreparedStatement stmt = null;
		Doc a = null ;
		String strSql = "SELECT * FROM file_info WHERE id=?";
		stmt = con.prepareStatement(strSql);
		stmt.setString(1, ID);
		ResultSet rs = stmt.executeQuery();
		 if (rs.next())       //循环将结果集游标往下移动，到达末尾返回false
			a = new Doc(rs.getString("id"),rs.getString("creator"),rs.getTimestamp("time"),
		   		rs.getString("description"),rs.getString("filename"));   
		return a;
		
	}
	
	public static Enumeration<Doc> getAllDocs() throws SQLException,IllegalStateException{		
		Vector<Doc> docs =  new Vector<Doc>();
		Statement sta = con.createStatement();
		ResultSet rs = sta.executeQuery("SELECT * FROM file_info");
		Doc a = null ;
		while (rs.next()) {        //循环将结果集游标往下移动，到达末尾返回false
		  a = new Doc(rs.getString("id"),rs.getString("creator"),rs.getTimestamp("time"),
		  		rs.getString("description"),rs.getString("filename"));
		  docs.add(a);
		}
		Enumeration<Doc> en = docs.elements();
		return en;
	}
	
	public static boolean insertDoc(String creator, Timestamp timestamp, String description, String filename) throws SQLException,IllegalStateException{		
		PreparedStatement stmt = null;
		String strSql = "INSERT INTO file_info (filename,creator,time,description) VALUES (?,?,?,?)";
		stmt = con.prepareStatement(strSql);
		stmt.setString(1,filename);
		stmt.setString(2,creator);
		stmt.setTimestamp(3, timestamp);
		stmt.setString(4, description);
		int count = stmt.executeUpdate();
		if (count == 1)
			return true;
	return connectToDB;
	}
	
	public static User searchUser(String name, String password) throws SQLException,IllegalStateException, UnknownHostException, IOException {
		User a = null ;
		PreparedStatement stmt = null;
			String strSql = "SELECT * FROM user_info WHERE name=? AND password=?";
			stmt = con.prepareStatement(strSql);
			stmt.setString(1, name);stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			 if (rs.next())       //循环将结果集游标往下移动，到达末尾返回false
				    if(rs.getString("role").equals("administrator")) {
				    	a = new Administrator(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    }else if (rs.getString("role").equals("operator")) {
				    	a = new Operator(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    }else if (rs.getString("role").equals("browser")) {
				    	a = new Browser(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    }
		 return a;
		
	}
	
	public static Enumeration<User> getAllUser() throws SQLException,IllegalStateException, UnknownHostException, IOException{
//		if ( !connectToDB ) 
//	        throw new IllegalStateException( "Not Connected to Database" );
//		
//		if (Math.random()>0.7)
//			throw new SQLException( "Error in excecuting Query" );
		@SuppressWarnings("unchecked")
		Vector<User> users =  new Vector<User>();
		PreparedStatement stmt = null;
			Statement sta = con.createStatement();
		  ResultSet rs = sta.executeQuery("SELECT * FROM user_info");
		  User a = null ;
		  while (rs.next()) {          //循环将结果集游标往下移动，到达末尾返回false
				    if(rs.getString("role").equals("administrator")) {
				    	a = new Administrator(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    	users.add(a);
				    }else if (rs.getString("role").equals("operator")) {
				    	a = new Operator(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    	users.add(a);
				    }else if (rs.getString("role").equals("browser")) {
				    	a = new Browser(rs.getInt("id"),rs.getString("name"),rs.getString("password"),rs.getString("role"));
				    	users.add(a);
				    }
		  }
		Enumeration<User> en = users.elements();
		return en;
	}
	
	
	
	@SuppressWarnings("resource")
	public static boolean updateUser(String id, String name, String password, String role) throws SQLException,IllegalStateException{
		User user;
		PreparedStatement stmt = null;
			String strSql1 = "UPDATE user_info SET name = ? WHERE id = ?";
			stmt = con.prepareStatement(strSql1);
			stmt.setString(1, name);stmt.setString(2,id);
			stmt.executeUpdate();
			String strSql2 = "UPDATE user_info SET password = ? WHERE id = ? ";
			stmt = con.prepareStatement(strSql2);
			stmt.setString(1, password);stmt.setString(2, id);
			stmt.executeUpdate();
			String strSql3 = "UPDATE user_info SET role = ? WHERE id = ? ";
			stmt.setString(1, role);stmt.setString(2, id);
			if(stmt.executeUpdate()==1)
				return true;
			else
				return false;
	}
	
	public static boolean insertUser(String name, String password, String role) throws SQLException,IllegalStateException{
		PreparedStatement stmt = null;
			String strSql = "INSERT INTO user_info (name,password,role) VALUES (?,?,?)";
			stmt = con.prepareStatement(strSql);
			stmt.setString(1,name);stmt.setString(2,password);stmt.setString(3,role);
			int count = stmt.executeUpdate();
			if (count == 1)
				return true;
		return connectToDB;
	}
	
	public static boolean deleteUser(String id) throws SQLException,IllegalStateException{
		PreparedStatement stmt = null;
			//con = DriverManager.getConnection(strCon,strUser,strPwd);
			String strSql = "DELETE FROM user_info WHERE id = ?";
			stmt = con.prepareStatement(strSql);
			int ID = Integer.parseInt(id);
			stmt.setInt(1, ID);
			int count = stmt.executeUpdate();
			if (count == 0){
				return false;
			}else
				return true;
	}	
            
	public void disconnectFromDB() {
		if ( connectToDB ){
			// close Statement and Connection            
			try{
				rs.close();                        
			    st.close();                        
			    con.close();                       
			}catch ( SQLException sqlException ){                                            
			    sqlException.printStackTrace();           
			}finally{                                            
				connectToDB = false;              
			}                             
		} 
   }           

	
	public static void main(String[] args) {		

	}
	
}
