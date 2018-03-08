package changedata.impl;

import java.util.ArrayList;
import java.util.List;

import changedata.UserData;
import entity.User;

public class UserDataImpl extends DataBaseMul implements UserData {

	@Override
	public boolean saveUser(User user){
		String sql="insert into users(uname,upwd,type)values(?,?,?)";
		List<Object> params=new ArrayList<Object>();//±£´æÕ¼Î»·û
		params.add(user.getUname());
		params.add(user.getUpwd());
		params.add(user.getType());
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean delUser(int uid) {
		String sql="delete from users where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(uid);
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean updateUser(User user)  {
		String sql="update users set uname=?,upass=?,type=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUname());
		params.add(user.getUpwd());
		params.add(user.getType());
		params.add(user.getId());
		return this.operUpdate(sql, params);
	}

	@Override
	public User findUser(User user){
		List<User> uList=null;
		String sql="select id,uname,upwd,type from users where uname=? and upwd=? and type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(user.getUname());
		params.add(user.getUpwd());
		params.add(user.getType());
		uList=this.operFind(sql, params, User.class);
		if(uList.size()>0) {
			return uList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public User findUserByNT(String uname, int type) {
		List<User> uList=null;
		String sql="select id,uname,upwd,type from users where uname=? and type=?";
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		params.add(type);
		uList=this.operFind(sql, params, User.class);
		if(uList.size()>0) {
			return uList.get(0);
		}else {
			return null;
		}
	}

	@Override
	public User findUserById(int uid) {
		List<User> uList=null;
		String sql="select id,uname,upwd,type from users where uid=?";
		List<Object> params=new ArrayList<Object>();
		params.add(uid);
		uList=this.operFind(sql, params, User.class);
		if(uList.size()>0) {
			return uList.get(0);
		}else {
			return null;
		}
	}

}
