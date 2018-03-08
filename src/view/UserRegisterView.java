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

import changedata.UserData;
import changedata.impl.UserDataImpl;
import entity.User;

public class UserRegisterView extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel=null;
	private JPanel firstPanel=null;
	private JPanel secondPanel=null;
	private JPanel thirdPanel=null;
	private JPanel fourthPanel=null;
	private JPanel fifthPanel=null;
	
	private JLabel unameLb=null;
	private JLabel upwdLb=null;
	private JLabel upwdConLb=null;
	private JLabel typeLb=null;
	
	private JTextField unameTF=null;
	private JPasswordField upwdPF=null;
	private JPasswordField upwdConPF=null;
	private JComboBox<String> typeCB=null;
	
	private JButton conBt=null; //ȷ��
	private JButton backBt=null;//����
	
	public UserRegisterView() {
		init();
		registerListener();
	}
	
	private void init() {
		unameLb=new JLabel("�û��� ");
		unameLb.setFont(new Font("����",Font.BOLD,15));//��������
		upwdLb=new JLabel("��  ��");
		upwdLb.setFont(new Font("����",Font.BOLD,15));
		upwdConLb=new JLabel("ȷ������");
		upwdConLb.setFont(new Font("����",Font.BOLD,15));
		typeLb=new JLabel("�û�����");
		typeLb.setFont(new Font("����",Font.BOLD,15));
		unameTF=new JTextField(15);
		upwdPF=new JPasswordField (15);
		upwdConPF=new JPasswordField (15);
		typeCB=new JComboBox<String>(new String[] {"��ͨ�û�","����Ա"});
		typeCB.setFont(new Font("����",Font.BOLD,15));
		conBt=new JButton("ȷ��") ;
		conBt.setFont(new Font("����",Font.PLAIN,15));
		backBt=new JButton("����") ;
		backBt.setFont(new Font("����",Font.PLAIN,15));
		
		mainPanel=new JPanel(new GridLayout(5,1));
		firstPanel=new JPanel();
		secondPanel=new JPanel();
		thirdPanel=new JPanel();
		fourthPanel=new JPanel();
		fifthPanel=new JPanel();
		
		firstPanel.add(typeLb);
		firstPanel.add(typeCB);
		secondPanel.add(unameLb);
		secondPanel.add(unameTF);
		thirdPanel.add(upwdLb);
		thirdPanel.add(upwdPF);
		fourthPanel.add(upwdConLb);
		fourthPanel.add(upwdConPF);
		fifthPanel.add(conBt);
		fifthPanel.add(backBt);
		
		mainPanel.add(firstPanel);
		mainPanel.add(secondPanel);
		mainPanel.add(thirdPanel);
		mainPanel.add(fourthPanel);
		mainPanel.add(fifthPanel);
		
		this.getContentPane().add(mainPanel);
		this.setSize(500, 500);
		this.setTitle("ע��");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		
		this.setVisible(true);
	}
	
	public void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showConfirmDialog(UserRegisterView.this, "�Ƿ�ȷ���˳���","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					new LoginView();
					UserRegisterView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(null, "�˳�ʧ�ܣ�");
				}
			}
		});
		conBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String uname=unameTF.getText().trim();
				String upwd=new String(upwdPF.getPassword());
				String upwdCon=new String(upwdConPF.getPassword());
				int type=typeCB.getSelectedIndex();

				if(uname.equals("")) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "�������û�����");
					return;
				}else if(upwd.equals("")) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "���������룡");
					return;
				}else if(upwdCon.equals("")) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "��ȷ�����룡");
					return;
				}else if(!upwd.equals(upwdCon)) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "�����������벻һ�£�");
					return;
				}
				UserData userdata=new UserDataImpl();
				User user = userdata.findUserByNT(uname, type);
				if(user!=null) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "���û����Ѿ���������");
					return;
				}
				boolean res=userdata.saveUser(new User(uname, upwdCon, type));
				if(res==true) {
					JOptionPane.showMessageDialog(UserRegisterView.this, "���û������ɹ���");
					UserRegisterView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(UserRegisterView.this, "����ʧ�ܣ�");
				}
			}
		});
	}
}
