package service;

import java.util.List;

import entity.Record;

public interface RecordSrc {
	public boolean saveRecord(Record record) ;
	public boolean updateRecord(Record record);      //更新记录
	
	public List<Record> findUserRecord(String uname);//管理员查看指定用户的租赁记录
	public List<Record> findBookRecord(String bname);//管理员查看指定书籍的租赁记录
	
	public List<Record> findReturnedRecord(String uname);//用户查看指定用户的已归还记录
	public List<Record> findUnreturnedRecord(String uname);//用户查看指定用户的未归还记录

	public List<Record> findAllRecord();                   //查看所有记录
}
