package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.User;

public class AdminMainView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel=null;
	private JPanel userPanel=null;
	private JPanel butPanel=null;
	
	private JDesktopPane funcDP=null;//可以放置内嵌窗体
	
	private JButton amalBt=null;      //查询、借阅
	private JButton bookRecordBt=null;//查看借阅记录
	private JButton backBt=null;      //退出
	
	private JLabel img=null;
	private JLabel userLb=null;       //显示当前用户
	
	private User user=null;
	
	public AdminMainView(User user){
		this.user=user;
		init();
		registerListener();
	}
	
	private void init() {
		mainPanel=new JPanel(new BorderLayout());
		butPanel=new JPanel(new GridLayout(7,1,0,35));
		
		//窗口
		this.getContentPane().add(mainPanel);
		this.setTitle("图书管理系统");
		this.setSize(1000, 650);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		amalBt=new JButton("查询及管理");
		bookRecordBt=new JButton("查看借阅记录");
		backBt=new JButton("退出");
		//填充控件
		butPanel.add(new JLabel());//用于填充
		butPanel.add(new JLabel());
		butPanel.add(amalBt);
		butPanel.add(bookRecordBt);
		butPanel.add(backBt);
		butPanel.add(new JLabel());
		butPanel.add(new JLabel());
		//显示当前用户信息面板
		userPanel=new JPanel();
		userLb=new JLabel("编号："+user.getId()+"  用户名："+user.getUname(),JLabel.LEFT);
		userLb.setFont(new Font("微软雅黑",Font.PLAIN,18));
		userLb.setForeground(Color.BLACK);
		userPanel.add(userLb);
		//桌面面板
		funcDP=new JDesktopPane();
		ImageIcon pic=new ImageIcon("src/repository/dragon1.jpg");
		img=new JLabel(pic);
		img.setBounds(0, 0, pic.getIconWidth(), pic.getIconHeight());
		funcDP.add(img,new Integer(Integer.MIN_VALUE));//放在最底层
		//主窗体
		mainPanel.add(butPanel, BorderLayout.EAST);
		mainPanel.add(userPanel, BorderLayout.NORTH);
		mainPanel.add(funcDP, BorderLayout.CENTER);

		this.setVisible(true);
		
	}
	
	private void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showConfirmDialog(null, "是否确定退出？","确认",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					AdminMainView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(AdminMainView.this, "退出失败！");
				}
			}
		});
		amalBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminMulView umv=new AdminMulView();
				funcDP.add(umv);//把指定视图添加到桌面容器中
				umv.toFront();  //视图显示在最前面
			}
		});
		bookRecordBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminRecordView arv=new AdminRecordView();
				funcDP.add(arv);
				arv.toFront();
			}
		});
	}
}
