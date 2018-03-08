package changedata;

import java.util.List;

import entity.Record;

public interface RecordData {
	public Record findRecordById(int rid) ;               //指定id书籍的借还记录
	public boolean saveRecord(Record record) ;            //添加纪录
	public boolean updateRecord(Record record) ;          //更新记录
	public List<Record> findAllRecord() ;                 //查询所有图书借还记录
	public List<Record> findRecordByUname(String uname) ; //查询用户的所有借还记录
	public List<Record> findRecordByBname(String bname) ; //查询书的所有借还记录
	public List<Record> findUserRecordByReturnTime(boolean flag, String uname) ;//根据书状态查询
}
