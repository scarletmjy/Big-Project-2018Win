package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import entity.Book;
import entity.Record;
import entity.User;
import service.BookSrc;
import service.RecordSrc;
import service.impl.BookSrcImpl;
import service.impl.RecordSrcImpl;

public class UserRecordView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel tablePanel=null;
	private JPanel butPanel=null;
	private JTable table=null;
	
	private JButton findBt=null;
	private JButton returnBt=null;
	private JButton backBt=null;
	private JComboBox<String> typeCB=null;
	private JLabel typeLb=null;
	
	private RecordSrc recordsrc=null;
	private List<Record> recordlist=null;
	private recordInfoTableModel ritm=null;
	private BookSrc booksrc=null;
	private User user=null;

	Date date = new Date();
	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	String today=dateFormat.format(date);
	
	public UserRecordView(User user) {
		recordsrc=new RecordSrcImpl();
		recordlist=new ArrayList<Record>();
		booksrc=new BookSrcImpl() ;
		this.user=user;
		init();
		registerListener();
	}
	
	private void init() {
		this.setTitle("�û���¼��ѯ");
		this.setSize(800,500);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		table=new JTable();
		JScrollPane JSP= new JScrollPane(table);
		tablePanel=new JPanel(new BorderLayout());
		tablePanel.add(JSP);
		this.add(tablePanel,BorderLayout.CENTER);
		
		butPanel=new JPanel(new GridLayout(7,1,10,30));
		typeLb=new JLabel("��ѯ����");
		typeCB=new JComboBox<String>(new String[] {"ȫ����¼","�ѹ黹��","δ�黹��"});
		findBt=new JButton("��ѯ");
		returnBt=new JButton("�黹");
		returnBt.setEnabled(false);
		backBt=new JButton("�˳�");
		butPanel.add(typeLb);
		butPanel.add(typeCB);
		butPanel.add(findBt);
		butPanel.add(returnBt);
		butPanel.add(new JLabel());
		butPanel.add(new JLabel());
		butPanel.add(backBt);
		this.add(butPanel, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	public class recordInfoTableModel implements TableModel{
		private List<Record> recordlist=null;
		
		private String[] colName= {"���","��id","����","�û���","����ʱ��","�黹ʱ��"};
		
		public recordInfoTableModel(List<Record> recordlist) {
			this.recordlist=recordlist;
		}
		//��������
		@Override
		public int getRowCount() {
			return recordlist.size();
		}
		//��������
		@Override
		public int getColumnCount() {
			return colName.length;
		}
		//����
		@Override
		public String getColumnName(int columnIndex) {
			return colName[columnIndex];
		}
		//�е���������
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if(columnIndex==0||columnIndex==1) {
				return Integer.class;
			}else {
				return String.class;
			}
		}
		//��Ԫ���Ƿ�ɱ༭
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		//��ȡָ����Ԫ�������
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			Record record=recordlist.get(rowIndex);
			if(columnIndex==0) {
				return record.getId();
			}else if(columnIndex==1) {
				return record.getBid();
			}else if(columnIndex==2) {
				return record.getBname();
			}else if(columnIndex==3) {
				return record.getUname();
			}else if(columnIndex==4) {
				return record.getLendTime();
			}else if(columnIndex==5) {
				return record.getReturnTime();
			}else {
				return "����";
			}
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private void refreshTable(List<Record> recordlist) {
		ritm=new recordInfoTableModel(recordlist);
		table.setModel(ritm);
	}
	
	public void registerListener() {
		findBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(recordlist!=null) {
					recordlist.clear();
				}
				String uname=user.getUname();
				int index=typeCB.getSelectedIndex();
				if(index==0) {
					recordlist=recordsrc.findUserRecord(uname);
				}else if(index==1) {
					recordlist=recordsrc.findReturnedRecord(uname);
				}else {
					recordlist=recordsrc.findUnreturnedRecord(uname);
				}
				refreshTable(recordlist);
				if(recordlist.size()==0) {
					JOptionPane.showInternalConfirmDialog(UserRecordView.this, "û���ҵ���ѯ���ݣ�");
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRow()!=-1) {  
					returnBt.setEnabled(true);
				}
			}
		});
		returnBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow(); 
				String bstatus=table.getValueAt(row, 5).toString();
				if(bstatus!=null) {
					JOptionPane.showInternalMessageDialog(UserRecordView.this, "���鲻�ù黹��");
				}else {
					int flag=JOptionPane.showInternalConfirmDialog(UserRecordView.this, "�Ƿ�ȷ���黹��","ȷ��",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						int rid=(int) table.getValueAt(row, 0);
						int bid=(int)table.getValueAt(row, 1);
						int uid=user.getId();
						String uname=user.getUname();
						String bname=table.getValueAt(row, 2).toString();
						String lendDay=table.getValueAt(row, 4).toString();
						boolean res1=booksrc.updateBook(new Book(bid,bname, 1));
						boolean res2=recordsrc.updateRecord(new Record(rid,uid,uname, bid, bname,lendDay, today));
						if(res1==true&&res2==true) {
							JOptionPane.showInternalMessageDialog(UserRecordView.this, "�黹�ɹ���");
						}else {
							JOptionPane.showInternalMessageDialog(UserRecordView.this, "�黹ʧ�ܣ�");
						}
					}
				}
				
			}
		});
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showInternalConfirmDialog(UserRecordView.this, "�Ƿ�ȷ���˳���","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserRecordView.this.dispose();
				}else {
					JOptionPane.showInternalMessageDialog(UserRecordView.this, "�˳�ʧ�ܣ�");
				}
			}
		});
	}
}