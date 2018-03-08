package changedata;

import entity.User;

public interface UserData {
	public boolean saveUser(User user) ;  //添加用户
	public boolean delUser(int uid) ;      //删除用户
	public boolean updateUser(User user) ;//更改信息  
	public User findUser(User user) ;     //查找用户
	public User findUserById(int uid);     //根据id查找用户
	public User findUserByNT(String uname,int type);//根据用户名和用户类型寻找用户
}
