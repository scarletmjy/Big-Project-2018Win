package service.impl;

import java.util.List;

import changedata.RecordData;
import changedata.impl.RecordDataImpl;
import entity.Record;
import service.RecordSrc;

public class RecordSrcImpl implements RecordSrc {

	RecordData recorddata=null;
	public RecordSrcImpl(){
		recorddata=new RecordDataImpl();
		
	}
	@Override
	public List<Record> findUserRecord(String uname) {
		return recorddata.findRecordByUname(uname);
	}

	@Override
	public List<Record> findBookRecord(String bname){
		return recorddata.findRecordByBname(bname);
	}

	@Override
	public List<Record> findReturnedRecord(String uname) {
		return recorddata.findUserRecordByReturnTime(true, uname);
	}

	@Override
	public List<Record> findUnreturnedRecord(String uname)  {
		return recorddata.findUserRecordByReturnTime(false, uname);
	}

	@Override
	public List<Record> findAllRecord()  {
		return recorddata.findAllRecord();
	}
	@Override
	public boolean updateRecord(Record record) {
		return recorddata.updateRecord(record);
	}
	@Override
	public boolean saveRecord(Record record) {
		return recorddata.saveRecord(record);
	}


}
