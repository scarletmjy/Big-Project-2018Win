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
		this.setTitle("用户记录查询");
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
		typeLb=new JLabel("查询类型");
		typeCB=new JComboBox<String>(new String[] {"全部记录","已归还的","未归还的"});
		findBt=new JButton("查询");
		returnBt=new JButton("归还");
		returnBt.setEnabled(false);
		backBt=new JButton("退出");
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
		
		private String[] colName= {"序号","书id","书名","用户名","借阅时间","归还时间"};
		
		public recordInfoTableModel(List<Record> recordlist) {
			this.recordlist=recordlist;
		}
		//数据行数
		@Override
		public int getRowCount() {
			return recordlist.size();
		}
		//数据列数
		@Override
		public int getColumnCount() {
			return colName.length;
		}
		//列名
		@Override
		public String getColumnName(int columnIndex) {
			return colName[columnIndex];
		}
		//列的数据类型
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			if(columnIndex==0||columnIndex==1) {
				return Integer.class;
			}else {
				return String.class;
			}
		}
		//单元格是否可编辑
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}
		//获取指定单元格的数据
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
				return "错误！";
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
					JOptionPane.showInternalConfirmDialog(UserRecordView.this, "没有找到查询内容！");
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
					JOptionPane.showInternalMessageDialog(UserRecordView.this, "此书不用归还！");
				}else {
					int flag=JOptionPane.showInternalConfirmDialog(UserRecordView.this, "是否确定归还？","确认",JOptionPane.YES_NO_OPTION);
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
							JOptionPane.showInternalMessageDialog(UserRecordView.this, "归还成功！");
						}else {
							JOptionPane.showInternalMessageDialog(UserRecordView.this, "归还失败！");
						}
					}
				}
				
			}
		});
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showInternalConfirmDialog(UserRecordView.this, "是否确定退出？","确认",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserRecordView.this.dispose();
				}else {
					JOptionPane.showInternalMessageDialog(UserRecordView.this, "退出失败！");
				}
			}
		});
	}
}