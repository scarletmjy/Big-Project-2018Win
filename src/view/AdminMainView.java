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
	
	private JDesktopPane funcDP=null;//���Է�����Ƕ����
	
	private JButton amalBt=null;      //��ѯ������
	private JButton bookRecordBt=null;//�鿴���ļ�¼
	private JButton backBt=null;      //�˳�
	
	private JLabel img=null;
	private JLabel userLb=null;       //��ʾ��ǰ�û�
	
	private User user=null;
	
	public AdminMainView(User user){
		this.user=user;
		init();
		registerListener();
	}
	
	private void init() {
		mainPanel=new JPanel(new BorderLayout());
		butPanel=new JPanel(new GridLayout(7,1,0,35));
		
		//����
		this.getContentPane().add(mainPanel);
		this.setTitle("ͼ�����ϵͳ");
		this.setSize(1000, 650);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		amalBt=new JButton("��ѯ������");
		bookRecordBt=new JButton("�鿴���ļ�¼");
		backBt=new JButton("�˳�");
		//���ؼ�
		butPanel.add(new JLabel());//�������
		butPanel.add(new JLabel());
		butPanel.add(amalBt);
		butPanel.add(bookRecordBt);
		butPanel.add(backBt);
		butPanel.add(new JLabel());
		butPanel.add(new JLabel());
		//��ʾ��ǰ�û���Ϣ���
		userPanel=new JPanel();
		userLb=new JLabel("��ţ�"+user.getId()+"  �û�����"+user.getUname(),JLabel.LEFT);
		userLb.setFont(new Font("΢���ź�",Font.PLAIN,18));
		userLb.setForeground(Color.BLACK);
		userPanel.add(userLb);
		//�������
		funcDP=new JDesktopPane();
		ImageIcon pic=new ImageIcon("src/repository/dragon1.jpg");
		img=new JLabel(pic);
		img.setBounds(0, 0, pic.getIconWidth(), pic.getIconHeight());
		funcDP.add(img,new Integer(Integer.MIN_VALUE));//������ײ�
		//������
		mainPanel.add(butPanel, BorderLayout.EAST);
		mainPanel.add(userPanel, BorderLayout.NORTH);
		mainPanel.add(funcDP, BorderLayout.CENTER);

		this.setVisible(true);
		
	}
	
	private void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showConfirmDialog(null, "�Ƿ�ȷ���˳���","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					AdminMainView.this.dispose();
				}else {
					JOptionPane.showMessageDialog(AdminMainView.this, "�˳�ʧ�ܣ�");
				}
			}
		});
		amalBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AdminMulView umv=new AdminMulView();
				funcDP.add(umv);//��ָ����ͼ��ӵ�����������
				umv.toFront();  //��ͼ��ʾ����ǰ��
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
