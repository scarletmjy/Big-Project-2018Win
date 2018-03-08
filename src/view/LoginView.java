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

	private JPanel mainPanel=null; //�����
	
	private JLabel unameLb=null;//�û���ǩ
	private JLabel upwdLb=null; //�����ǩ
	private JLabel typeLb=null; //���ͱ�ǩ
	
	private JTextField unameTF=null;   //�û��ı���
	private JPasswordField upwdPF=null;//�����ı���
	
	private JButton loginBt=null;   //��½��ť
	private JButton registerBt=null;//ע�ᰴť
	
	private JComboBox<String> typeCB=null;//�û����������б�
	
	private UserSrc usersrc=null;
	
	
	public LoginView() {
		usersrc=new UserSrcImpl();
		this.init();
		registerListener();
	}
	/**
	 * ��ʼ���ؼ�
	 */
	private void init() {
		this.setSize(500, 400);                             //���ô����С
		this.setLocationRelativeTo(null);                   //������ʾ
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ���
		this.setTitle("��½");                               //���ñ���
		this.setResizable(false);                           //�����϶���С
		//��ʼ�����
		mainPanel=new JPanel(new GridLayout(4,2,0,10)); 
		//��ʼ���ؼ�
		unameTF=new JTextField(15);                         //�ı��򳤶�
		upwdPF=new JPasswordField(15);
		typeCB=new JComboBox<String>(new String[] {"��ͨ�û�","����Ա"});
		typeCB.setFont(new Font("����",Font.BOLD,15));
		loginBt=new JButton("��½");
		loginBt.setFont(new Font("����",Font.PLAIN,15));
		registerBt=new JButton("ע��");
		registerBt.setFont(new Font("����",Font.PLAIN,15));
		unameLb=new JLabel("��  ��",JLabel.CENTER);
		unameLb.setFont(new Font("����",Font.BOLD,15));
		upwdLb=new JLabel("��  ��",JLabel.CENTER);
		upwdLb.setFont(new Font("����",Font.BOLD,15));
		typeLb=new JLabel("��  ��",JLabel.CENTER);
		typeLb.setFont(new Font("����",Font.BOLD,15));
		//���ؼ��������
		mainPanel.add(unameLb);
		mainPanel.add(unameTF);
		mainPanel.add(upwdLb);
		mainPanel.add(upwdPF);
		mainPanel.add(typeLb);
		mainPanel.add(typeCB);
		mainPanel.add(loginBt);
		mainPanel.add(registerBt);
		//�������봰��
		this.getContentPane().add(mainPanel);
		this.pack();//��������
		
		this.setVisible(true);                              //��ʾ����
	}
	
	private void registerListener(){
		loginBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//��ȡ�û���������
				String uname=unameTF.getText().trim();     //ȥ����β�ո�
				String upwd=new String(upwdPF.getPassword());
				//��ȡ�����˵�ѡ��
				int type=typeCB.getSelectedIndex();
				
				if(uname.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "�������û�����");//������Ϣ��
					return;
				}else if(upwd.equals("")) {
					JOptionPane.showMessageDialog(LoginView.this, "���������룡");
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
					JOptionPane.showMessageDialog(LoginView.this, "�û������������");
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
