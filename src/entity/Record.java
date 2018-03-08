package entity;

public class Record {
	private int id;
	private int uid;
	private String uname;
	private int bid;
	private String bname;
	private String lendTime;
	private String returnTime;
	
	public Record() {

	}
	public Record(int uid, String uname,int bid, String bname,String lendTime, String returnTime) {
		this.uid = uid;
		this.uname=uname;
		this.bid = bid;
		this.bname=bname;
		this.lendTime = lendTime;
		this.returnTime = returnTime;
	}
	public Record(int id, int uid, String uname,int bid, String bname,String lendTime, String returnTime) {
		this.id = id;
		this.uid = uid;
		this.uname=uname;
		this.bid = bid;
		this.bname=bname;
		this.lendTime = lendTime;
		this.returnTime = returnTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getLendTime() {
		return lendTime;
	}
	public void setLendTime(String lendTime) {
		this.lendTime = lendTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}
}
	