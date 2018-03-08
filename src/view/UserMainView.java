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


public class UserMainView extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel mainPanel=null;
	private JPanel userPanel=null;
	private JPanel butPanel=null;
	
	private JDesktopPane funcDP=null;//���Է�����Ƕ����
	
	JButton umalBt=null;      //��ѯ������
	JButton bookRecordBt=null;//�鿴���ļ�¼
	JButton backBt=null;      //�˳�
	
	JLabel img=null;
	JLabel userLb=null;
	
	private User user=null;
	
	public UserMainView(User user){
		this.user=user;
		init();
		registerListener();
	}
	
	private void init() {
		mainPanel=new JPanel(new BorderLayout());//border���ֹ�����
		butPanel=new JPanel(new GridLayout(7,1,0,35));
		
		umalBt=new JButton("��ѯ�ͽ���");
		bookRecordBt=new JButton("�鿴���ļ�¼");
		backBt=new JButton("�˳�");
		//���ؼ�
		butPanel.add(new JLabel());//�������
		butPanel.add(new JLabel());
		butPanel.add(umalBt);
		butPanel.add(bookRecordBt);
		butPanel.add(backBt);
		butPanel.add(new JLabel());
		butPanel.add(new JLabel());
		//��ʾ�û���Ϣ
		userPanel=new JPanel();
		userLb=new JLabel("��ţ�"+user.getId()+"  �û�����"+user.getUname(),JLabel.LEFT);
		userLb.setFont(new Font("΢���ź�",Font.PLAIN,18));
		userLb.setForeground(Color.BLACK);
		userPanel.add(userLb);
		this.add(userPanel, BorderLayout.NORTH);
		//�������
		funcDP=new JDesktopPane();
		ImageIcon pic=new ImageIcon("src/repository/dragon1.jpg");
		img=new JLabel(pic);
		img.setBounds(0, 0, pic.getIconWidth(), pic.getIconHeight());
		funcDP.add(img,new Integer(Integer.MIN_VALUE));//������ײ�
		//�����
		mainPanel.add(butPanel, BorderLayout.EAST);
		mainPanel.add(userPanel, BorderLayout.NORTH);
		mainPanel.add(funcDP, BorderLayout.CENTER);
		//����
		this.getContentPane().add(mainPanel);
		this.setTitle("�û�����ϵͳ");
		this.setSize(1000, 650);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
		
	}
	
	private void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ���˳���","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserMainView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(UserMainView.this, "�˳�ʧ�ܣ�");
				}
			}
		});
		umalBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserMulView umv=new UserMulView(user);
				funcDP.add(umv);//��ָ����ͼ��ӵ�����������
				umv.toFront();//��ͼ��ʾ����ǰ��
			}
		});
		bookRecordBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserRecordView urv=new UserRecordView(user);
				funcDP.add(urv);
				urv.toFront();
			}
		});
	}
	
}
