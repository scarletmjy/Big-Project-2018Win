package changedata.impl;

import java.util.ArrayList;
import java.util.List;

import changedata.RecordData;
import entity.Record;

public class RecordDataImpl extends DataBaseMul implements RecordData {

	@Override
	public Record findRecordById(int rid)  {
		List<Record> rList=null;
		String sql="select id,uid,uname,bid,bname,lendTime,returnTime from records where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(rid);
		rList=this.operFind(sql, params, Record.class);
		if(rList.size()>0) {
			return rList.get(0);
		}
		return null;
	}

	@Override
	public boolean saveRecord(Record record) {
		String sql="insert into records(uid,uname,bid,bname,lendTime,returnTime)values(?,?,?,?,?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(record.getUid());
		params.add(record.getUname());
		params.add(record.getBid());
		params.add(record.getBname());
		params.add(record.getLendTime());
		params.add(record.getReturnTime());
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean updateRecord(Record record)  {
		String sql="update records set uid=?,uname=?,bid=?,bname=?,lendTime=?,returnTime=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(record.getUid());
		params.add(record.getUname());
		params.add(record.getBid());
		params.add(record.getBname());
		params.add(record.getLendTime());
		params.add(record.getReturnTime());
		params.add(record.getId());
		return this.operUpdate(sql, params);
	}

	@Override
	public List<Record> findAllRecord()  {
		List<Record> rList=null;
		String sql="select id,uid,uname,bid,bname,lendTime,returnTime from records";
		rList=this.operFind(sql, null, Record.class);
		return rList;
	}

	@Override
	public List<Record> findRecordByUname(String uname)  {
		List<Record> rList=null;
		String sql="select id,uid,uname,bid,bname,lendTime,returnTime from records where uname=?";
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		rList=this.operFind(sql, params, Record.class);
		return rList;
	}

	@Override
	public List<Record> findRecordByBname(String bname)  {
		List<Record> rList=null;
		String sql="select id,uid,uname,bid,bname,lendTime,returnTime from records where bname=?";
		List<Object> params=new ArrayList<Object>();
		params.add(bname);
		rList=this.operFind(sql, params, Record.class);
		return rList;
	}

	/*
	 * 查看用户的借还记录
	 * flag为0：未归还的记录
	 * flag为1：已归还的记录
	 * */
	@Override
	public List<Record> findUserRecordByReturnTime(boolean flag,String uname)  {
		List<Record> rList=null;
		String sql=null;
		if(flag) {
		sql="select id,uid,uname,bid,bname,lendTime,returnTime from records where returnTime is not null and uname=?";
		}else {
			sql="select id,uid,uname,bid,bname,lendTime,returnTime from records where returnTime is null and uname=?";
		}
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		rList=this.operFind(sql, params, Record.class);
		return rList;
	}


}
