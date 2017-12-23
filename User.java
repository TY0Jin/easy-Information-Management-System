import java.awt.Component;
import java.io.IOException;
import java.net.UnknownHostException;

public abstract class User {
	private String name ;
	private String password;
	private String role;
	int ID;
	abstract void setName(String name);
	abstract void setPassword(String name);
	abstract void setRole(String role);
	abstract void setID(int ID);
	abstract String getName();
	abstract String getPassword();
	abstract String getRole();
	abstract int getID();
	abstract void showMenu() throws UnknownHostException, IOException;
	abstract void showFileList();
	abstract boolean downLoadFile();
	abstract void changeSelfInfo();
	
}
