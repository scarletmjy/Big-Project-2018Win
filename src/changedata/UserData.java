package changedata;

import entity.User;

public interface UserData {
	public boolean saveUser(User user) ;  //����û�
	public boolean delUser(int uid) ;      //ɾ���û�
	public boolean updateUser(User user) ;//������Ϣ  
	public User findUser(User user) ;     //�����û�
	public User findUserById(int uid);     //����id�����û�
	public User findUserByNT(String uname,int type);//�����û������û�����Ѱ���û�
}
