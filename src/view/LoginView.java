package view;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entity.User;
import service.UserSrc;
import service.impl.UserSrcImpl;

public class LoginView extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel mainPanel=null; //主面板
	
	private JLabel unameLb=null;//用户标签
	private JLabel upwdLb=null; //密码标签
	private JLabel typeLb=null; //类型标签
	
	private JTextField unameTF=null;   //用户文本框
	private JPasswordField upwdPF=null;//密码文本框
	
	private JButton loginBt=null;   //登陆按钮
	private JButton registerBt=null;//注册按钮
	
	private JComboBox<String> typeCB=null;//用户类型下拉列表
	
	private UserSrc usersrc=null;
	
	
	public LoginView() {
		usersrc=new UserSrcImpl();
		this.init();
		registerListener();
	}
	/**
	 * 初始化控件
	 */
	private void init() {
		this.setSize(500, 400);                             //设置窗体大小
		this.setLocationRelativeTo(null);                   //居中显示
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体
		this.setTitle("登陆");                               //设置标题
		this.setResizable(false);                           //不可拖动大小
		//初始化面板
		mainPanel=new JPanel(new GridLayout(4,2,0,10)); 
		//初始化控件
		unameTF=new JTextField(15);                         //文本框长度
		upwdPF=new JPasswordField(15);
		typeCB=new JComboBox<String>(new String[] {"普通用户","管理员"});
		typeCB.setFont(new Font("宋体",Font.BOLD,15));
		loginBt=new JButton("登陆");
		loginBt.setFont(new Font("黑体",Font.PLAIN,15));
		registerBt=new JButton("注册");
		registerBt.setFont(new Font("黑体",Font.PLAIN,15));
		unameLb=new JLabel("用  户",JLabel.CENTER);
		unameLb.setFont(new Font("黑体",Font.BOLD,15));
		upwdLb=new JLabel("密  码",JLabel.CENTER);
		upwdLb.setFont(new Font("黑体",Font.BOLD,15));
		typeLb=new JLabel("类  型",JLabel.CENTER);
		typeLb.setFont(new Font("黑体",Font.BOLD,15));
		//将控件放入面板
		mainPanel.add(unameLb);
		mainPanel.add(unameTF);
		mainPanel.add(upwdLb);
		mainPanel.add(upwdPF);
		mainPanel.add(typeLb);
		mainPanel.add(typeCB);
		mainPanel.add(loginBt);
		mainPanel.add(registerBt);
		//主面板放入窗体
		this.getContentPane().add(mainPanel);
		this.pack();//收缩窗体
		
		this.setVisible(true);                              //显示窗体
	}
	
	private void registerListener(){
		loginBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//获取用户名和密码
				String uname=unameTF.getText().trim();     //去掉首尾空格
				String upwd=new String(upwdPF.getPassword());
				//获取下拉菜单选项
				int type=typeCB.getSelectedIndex();
				
				if(uname.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "请输入用户名！");//弹出消息框
					return;
				}else if(upwd.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "请输入密码！");
					return;
				}
				User user=new User(uname,upwd,type);
				user=usersrc.login(user);
				if(user!=null) {
					if(user.getType()==0) {
						new UserMainView(user);
					}else {
						new AdminMainView(user);
					}
					LoginView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(LoginView.this, "用户名或密码错误！");
				}
			}
		});
		registerBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new UserRegisterView();
			}
		});
	}
}
