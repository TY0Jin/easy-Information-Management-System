import java.sql.SQLException;
import java.sql.Timestamp;

public class Doc {
	private String iD ;
	private String creator;
	private Timestamp timestamp;
	private String description;
	private String filsename;
	public Doc(String iD, String creator, Timestamp timestamp, String description, String filename) {
		this.setiD(iD);
		this.setCreator(creator);
		this.setTimestamp(timestamp);
		this.setDescription(description);
		this.setFilsename(filename);
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public String getiD() {
		return iD;
	}
	public void setiD(String iD) {
		this.iD = iD;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getFilsename() {
		return filsename;
	}
	public void setFilsename(String filsename) {
		this.filsename = filsename;
	}
	
}
