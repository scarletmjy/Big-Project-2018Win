package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import entity.Record;
import service.RecordSrc;
import service.impl.RecordSrcImpl;

public class AdminRecordView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel tablePanel=null;
	private JPanel butPanel=null;
	private JTable table=null;
	
	private JButton findBt=null;
	private JTextField inputTF=null;
	private JButton backBt=null;
	private JComboBox<String> typeCB=null;
	private JLabel typeLb=null;
	
	private RecordSrc recordsrc=null;
	private List<Record> recordlist=null;
	private recordInfoTableModel ritm=null;

	Date date = new Date();
	SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
	String today=dateFormat.format(date);
	
	
	public AdminRecordView() {
		recordsrc=new RecordSrcImpl();
		recordlist=new ArrayList<Record>();
		init();
		registerListener();
	}
	
	private void init() {
		this.setTitle("借阅记录查询");
		this.setSize(800,500);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		table=new JTable();
		JScrollPane JSP= new JScrollPane(table);    //显示列名
		tablePanel=new JPanel(new BorderLayout());
		tablePanel.add(JSP);
		this.add(tablePanel,BorderLayout.CENTER);
		
		butPanel=new JPanel(new GridLayout(8,1,10,30));
		typeLb=new JLabel("查询类型");
		typeCB=new JComboBox<String>(new String[] {"全部记录","根据用户名字","根据图书名字"});
		inputTF=new JTextField(10);
		findBt=new JButton("查询");
		backBt=new JButton("退出");
		butPanel.add(typeLb);
		butPanel.add(typeCB);
		butPanel.add(inputTF);
		butPanel.add(findBt);
		butPanel.add(new JLabel());
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
				String str=inputTF.getText().trim();
				if(recordlist!=null) {
					recordlist.clear();
				}
				int index=typeCB.getSelectedIndex();
				if(index==0) {
					recordlist=recordsrc.findAllRecord();
				}else if(index==1) {
					recordlist=recordsrc.findUserRecord(str);
				}else {
					recordlist=recordsrc.findBookRecord(str);
				}
				refreshTable(recordlist);
				if(recordlist.size()==0) {
					JOptionPane.showInternalConfirmDialog(AdminRecordView.this, "没有找到查询内容！");
				}
			}
		});
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showInternalConfirmDialog(AdminRecordView.this, "是否确定退出？","确认",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					AdminRecordView.this.dispose();
				}else {
					JOptionPane.showInternalMessageDialog(AdminRecordView.this, "退出失败！");
				}
			}
		});
	}
	

}
