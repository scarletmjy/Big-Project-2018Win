package entity;

public class Book {
	private int id;
	private String bname;
	private int status;
	
	public Book() {

	}
	public Book(String bname, int status) {
		this.bname = bname;
		this.status = status;
	}
	public Book(int id, String bname, int status) {
		this.id = id;
		this.bname = bname;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
