package changedata;

import java.util.List;

import entity.Record;

public interface RecordData {
	public Record findRecordById(int rid) ;               //ָ��id�鼮�Ľ軹��¼
	public boolean saveRecord(Record record) ;            //��Ӽ�¼
	public boolean updateRecord(Record record) ;          //���¼�¼
	public List<Record> findAllRecord() ;                 //��ѯ����ͼ��軹��¼
	public List<Record> findRecordByUname(String uname) ; //��ѯ�û������н軹��¼
	public List<Record> findRecordByBname(String bname) ; //��ѯ������н軹��¼
	public List<Record> findUserRecordByReturnTime(boolean flag, String uname) ;//������״̬��ѯ
}
