package service;

import entity.User;

public interface UserSrc {
	public User login(User user);//�û���½
	public int registerUser(User user);//�û�ע��
	public User findUserByNT(String uname,int type);//�����û������û�����Ѱ���û�
}
