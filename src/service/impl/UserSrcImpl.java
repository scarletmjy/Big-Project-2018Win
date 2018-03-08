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
			return 1;//�û����Ѿ�����
		}else {
			boolean res=userdata.saveUser(user);
			if(res) {
				return 2;//ע��ɹ�
			}else {
				return 3;//ע��ʧ��
			}
		}
	}
	@Override
	public User findUserByNT(String uname, int type) {
		return userdata.findUserByNT(uname, type);
	}
}
