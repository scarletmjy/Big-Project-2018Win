package view;
import util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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

import entity.Book;
import service.BookSrc;
import service.impl.BookSrcImpl;

public class AdminMulView extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JPanel tablePanel=null;
	private JPanel butPanel=null;
	private JPanel addPanel=null;
	private JTable table=null;
	
	private JButton findBt=null;
	private JButton addBt=null;
	private JButton updateBt=null;
	private JButton delBt=null;
	private JButton backBt=null;
	private JComboBox<String> typeCB=null;
	private JLabel typeLb=null;
	private BookSrc booksrc =null;
	
	private JLabel bnameLb=null;
	private JTextField bnameTF=null;
	private JLabel bstatusLb=null;
	private JComboBox<String> bstatusCB=null;
	private JTextField input=null;
	
	private List<Book> booklist=null;
	private bookInfoTableModel bitm=null;
	public AdminMulView() {
		booksrc=new BookSrcImpl();
		booklist=new ArrayList<Book>();
		init();
		registerListener();
	}
	
	private void init() {
		this.setTitle("��ѯ������");
		this.setSize(800,500);
		this.setIconifiable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		table=new JTable();
		JScrollPane JSP= new JScrollPane(table);    //��ʾ����
		tablePanel=new JPanel(new BorderLayout());
		tablePanel.add(JSP);
		this.add(tablePanel,BorderLayout.CENTER);
		
		refreshTable(booklist);
		
		butPanel=new JPanel(new GridLayout(8,1,10,30));
		typeLb=new JLabel("��ѯ����");
		typeCB=new JComboBox<String>(new String[] {"ȫ���鼮","��ͼ����","������"});
		input=new JTextField(10);
		findBt=new JButton("��ѯ");
		addBt=new JButton("����鼮");
		updateBt=new JButton("�����鼮");
		updateBt.setEnabled(false);
		delBt=new JButton("ɾ���鼮");
		delBt.setEnabled(false);
		backBt=new JButton("�˳�");
		butPanel.add(typeLb);
		butPanel.add(typeCB);
		butPanel.add(input);
		butPanel.add(findBt);
		butPanel.add(addBt);
		butPanel.add(updateBt);
		butPanel.add(delBt);
		butPanel.add(backBt);
		this.add(butPanel, BorderLayout.EAST);
		
		addPanel=new JPanel(new GridLayout(1,5,0,30));
		bnameLb=new JLabel("��  ��",JLabel.RIGHT);
		bnameTF=new JTextField(8);
		bstatusLb=new JLabel("״  ̬",JLabel.RIGHT);
		bstatusCB=new JComboBox<String>(new String[] {"�ѽ��","�ɽ�"});
		addPanel.add(bnameLb);
		addPanel.add(bnameTF);
		addPanel.add(bstatusLb);
		addPanel.add(bstatusCB);
		addPanel.add(new JLabel());
		this.add(addPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public class bookInfoTableModel implements TableModel{
		private List<Book> booklist=null;
		
		String[] colName= {"��id","����","״̬"};
		
		public bookInfoTableModel(List<Book> booklist) {
			this.booklist=booklist;
		}
		//��������
		@Override
		public int getRowCount() {
			return booklist.size();
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
			if(columnIndex==0||columnIndex==2) {
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
			Book book=booklist.get(rowIndex);
			if(columnIndex==0) {
				return book.getId();
			}else if(columnIndex==1) {
				return book.getBname();
			}else if(columnIndex==2) {
				return ""+(book.getStatus()==1?"�ɽ�":"���ɽ�");
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
	
	/**
	 * ˢ��JTable����ʾ����
	 * @param booklist
	 */
	private void refreshTable(List<Book> booklist) {
		bitm=new bookInfoTableModel(booklist);
		table.setModel(bitm);
	}

	public void registerListener() {
		backBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int flag=JOptionPane.showInternalConfirmDialog(AdminMulView.this, "�Ƿ�ȷ���˳���","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					AdminMulView.this.dispose();
				}else {
					JOptionPane.showInternalMessageDialog(AdminMulView.this, "�˳�ʧ�ܣ�");
				}
			}
		});
		delBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				int bid=(Integer)table.getValueAt(row, 0);
				int flag=JOptionPane.showInternalConfirmDialog(AdminMulView.this, "�Ƿ�ȷ��ɾ����","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					boolean res=booksrc.delBook(bid);
					if(res) {
						JOptionPane.showInternalMessageDialog(AdminMulView.this, "ɾ���ɹ���");
					}else {
						JOptionPane.showInternalMessageDialog(AdminMulView.this, "ɾ��ʧ�ܣ�");
					}
				}
				
			}
		});
		updateBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bname=bnameTF.getText().trim();
				int  status=bstatusCB.getSelectedIndex();
				if(bname.equals("")) {
					JOptionPane.showInternalMessageDialog(AdminMulView.this, "������������");
					return;
				}
				int flag=JOptionPane.showInternalConfirmDialog(AdminMulView.this, "�Ƿ�ȷ�����£�","ȷ��",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int row=table.getSelectedRow();
					int bid=(Integer)table.getValueAt(row, 0);
					boolean res=booksrc.updateBook(new Book(bid,bname,status));
					if(res) {
						JOptionPane.showInternalMessageDialog(AdminMulView.this, "���³ɹ���");
					}else {
						JOptionPane.showInternalMessageDialog(AdminMulView.this, "����ʧ�ܣ�");
					}
				}
			}
		});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(table.getSelectedRow()!=-1) {  //һ�ж�ûѡ�з���-1
					delBt.setEnabled(true);
					updateBt.setEnabled(true);
				}
				//����ı���������˵�
				int row=table.getSelectedRow(); //�õ�ѡ���е����
				String bname=table.getValueAt(row, 1).toString();
				String status=table.getValueAt(row, 2).toString();
				bnameTF.setText(bname);
				bstatusCB.setSelectedItem(status);
			}
		});
		addBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String bname=bnameTF.getText().trim();
				int status=bstatusCB.getSelectedIndex();//0��� 1�ɽ�
				if(bname.equals("")) {
					JOptionPane.showInternalConfirmDialog(AdminMulView.this, "������������");
					return;
				}
				int flag=JOptionPane.showConfirmDialog(AdminMulView.this, "�Ƿ�ȷ����ӣ�","ȷ��",JOptionPane.YES_NO_OPTION);
				if (flag==JOptionPane.YES_OPTION) {
					boolean res=booksrc.addBook(new Book(bname, status));
					if(res==true) {
						JOptionPane.showInternalConfirmDialog(AdminMulView.this, "��ӳɹ���");
					}else {
						JOptionPane.showInternalConfirmDialog(AdminMulView.this, "���ʧ�ܣ�");
					}
				}
			}
		});
		findBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index=typeCB.getSelectedIndex();
				String content=input.getText().trim();
				if(index!=0&&content.equals("")) {
					JOptionPane.showInternalConfirmDialog(AdminMulView.this, "�������ѯ���ݣ�");
					return;
				}
				//��ղ�ѯ���ݼ���
				if(booklist!=null) {
					booklist.clear();
				}
				if(index==0) {
					booklist=booksrc.findAllBook();
				}else if(index==1) {
					if(Util.isNumber(content)==false) {
						JOptionPane.showInternalConfirmDialog(AdminMulView.this, "��ű���ȫΪ���֣�");
						return;
					}
					Book book=booksrc.findBookById(Integer.parseInt(content));
					if(book!=null) {
						booklist.add(book);
					}
				}else {
					booklist=booksrc.findBookByName(content);
				}
				refreshTable(booklist);
				delBt.setEnabled(false);
				updateBt.setEnabled(false);
				if(booklist.size()==0) {
					JOptionPane.showInternalConfirmDialog(AdminMulView.this, "û���ҵ���ѯ���ݣ�");
				}
			}
		});
	}

}
