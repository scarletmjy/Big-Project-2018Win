package service.impl;

import changedata.UserData;
import changedata.impl.UserDataImpl;
import entity.User;
import service.UserSrc;

public class UserSrcImpl implements UserSrc {
	private UserData userdata=null;
	public UserSrcImpl() {
		userdata=new UserDataImpl();
	}
	@Override
	public User login(User user){
		return userdata.findUser(user);
	}

	@Override
	public int registerUser(User user){
		if(userdata.findUser(user)!=null) {
			return 1;//用户名已经存在
		}else {
			boolean res=userdata.saveUser(user);
			if(res) {
				return 2;//注册成功
			}else {
				return 3;//注册失败
			}
		}
	}
	@Override
	public User findUserByNT(String uname, int type) {
		return userdata.findUserByNT(uname, type);
	}
}
