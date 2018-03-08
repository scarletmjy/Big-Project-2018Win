package service;

import java.util.List;

import entity.Record;

public interface RecordSrc {
	public boolean saveRecord(Record record) ;
	public boolean updateRecord(Record record);      //���¼�¼
	
	public List<Record> findUserRecord(String uname);//����Ա�鿴ָ���û������޼�¼
	public List<Record> findBookRecord(String bname);//����Ա�鿴ָ���鼮�����޼�¼
	
	public List<Record> findReturnedRecord(String uname);//�û��鿴ָ���û����ѹ黹��¼
	public List<Record> findUnreturnedRecord(String uname);//�û��鿴ָ���û���δ�黹��¼

	public List<Record> findAllRecord();                   //�鿴���м�¼
}
