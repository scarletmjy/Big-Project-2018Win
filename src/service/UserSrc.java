package service;

import entity.User;

public interface UserSrc {
	public User login(User user);//用户登陆
	public int registerUser(User user);//用户注册
	public User findUserByNT(String uname,int type);//根据用户名和用户类型寻找用户
}
