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

public class UserMulView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel tablePanel=null;//保存jtable的面板
	private JPanel butPanel=null;
	private JTable table=null;
	
	private JButton findBt=null;
	private JButton rentBt=null;
	private JButton backBt=null;
	private JComboBox<String> typeCB=null;
	private JLabel typeLb=null;

	
	private List<Book> booklist=null;
	private bookInfoTableModel bitm=null;
	private BookSrc booksrc=null;
	private User user=null;
	private RecordSrc recordsrc=null;
	//获取日期
	Date date = new Date();
	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	String today=dateFormat.format(date);
	
	public UserMulView(User user) {
		this.user=user;
		booklist=new ArrayList<Book>();
		booksrc=new BookSrcImpl() ;
		recordsrc=new RecordSrcImpl();
		init();
		registerListener();
	}
	
	private void init() {
		this.setTitle("查询及借阅");
		this.setSize(800,500);
		this.setIconifiable(true);//窗体可最小化
		this.setClosable(true);//可被关闭
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		table=new JTable();
		JScrollPane JSP= new JScrollPane(table);    //显示列名
		refreshTable(booklist);
		tablePanel=new JPanel(new BorderLayout());  //创建面板
		tablePanel.add(JSP);
		this.add(tablePanel,BorderLayout.CENTER);
		
		
		butPanel=new JPanel(new GridLayout(7,1,10,30));
		typeLb=new JLabel("查询类型");
		typeCB=new JComboBox<String>(new String[] {"全部书籍","可借阅的","已借出的"});
		findBt=new JButton("查询");
		rentBt=new JButton("借阅");
		rentBt.setEnabled(false);//默认不可用
		backBt=new JButton("退出");
		butPanel.add(typeLb);
		butPanel.add(typeCB);
		butPanel.add(findBt);
		butPanel.add(rentBt);
		butPanel.add(new JLabel());
		butPanel.add(new JLabel());
		butPanel.add(backBt);
		this.add(butPanel, BorderLayout.EAST);
		
		this.setVisible(true);
	}
	
	public class bookInfoTableModel implements TableModel{
		private List<Book> booklist=null;
		
		private String[] colName= {"书id","书名","状态"};
		
		public bookInfoTableModel(List<Book> booklist) {
			this.booklist=booklist;
		}
		//数据行数
		@Override
		public int getRowCount() {
			return booklist.size();
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
			if(columnIndex==0||columnIndex==2) {
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
			Book book=booklist.get(rowIndex);
			if(columnIndex==0) {
				return book.getId();
			}else if(columnIndex==1) {
				return book.getBname();
			}else if(columnIndex==2) {
				return ""+(book.getStatus()==1?"可借":"不可借");
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
	
	private void refreshTable(List<Book> booklist) {
		bitm=new bookInfoTableModel(booklist);
		table.setModel(bitm);
	}
	
	void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showInternalConfirmDialog(UserMulView.this, "是否确定退出？","确认",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					UserMulView.this.dispose();
				}else {
					JOptionPane.showInternalMessageDialog(UserMulView.this, "退出失败！");
				}
			}
		});
		findBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(booklist!=null) {
					booklist.clear();
				}
				int index=typeCB.getSelectedIndex();
				if(index==0) {
					booklist=booksrc.findAllBook();
				}else if(index==1) {
					booklist=booksrc.lendAva();
				}else {
					booklist=booksrc.lendUnAva();
				}
				refreshTable(booklist);
				if(booklist.size()==0) {
					JOptionPane.showInternalConfirmDialog(UserMulView.this, "没有找到查询内容！");
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRow()!=-1) {  //一行都没选中返回-1
					rentBt.setEnabled(true);
				}
			}
		});
		rentBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow(); //得到选中行的序号
				String bstatus=table.getValueAt(row, 2).toString();
				if(bstatus.equals("不可借")) {
					JOptionPane.showInternalMessageDialog(UserMulView.this, "此书已经被借走啦！");
				}else {
					int flag=JOptionPane.showInternalConfirmDialog(UserMulView.this, "是否确定借阅？","确认",JOptionPane.YES_NO_OPTION);
					if(flag==JOptionPane.YES_OPTION) {
						int bid=(Integer)table.getValueAt(row, 0);
						String bname=table.getValueAt(row, 1).toString();
						boolean res1=booksrc.updateBook(new Book(bid,bname, 0));
						boolean res2=recordsrc.saveRecord(new Record(user.getId(), user.getUname(),bid,bname, today, null));
						if(res1==true&&res2==true) {
							JOptionPane.showInternalMessageDialog(UserMulView.this, "借阅成功！");
						}else {
							JOptionPane.showInternalMessageDialog(UserMulView.this, "借阅失败！");
						}
					}
				}
				
			}
		});
	}
		
}
