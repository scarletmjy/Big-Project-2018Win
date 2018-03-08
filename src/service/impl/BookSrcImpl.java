package service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import changedata.BookData;
import changedata.RecordData;
import changedata.UserData;
import changedata.impl.BookDataImpl;
import changedata.impl.RecordDataImpl;
import entity.Book;
import entity.Record;
import entity.User;
import service.BookSrc;

public class BookSrcImpl implements BookSrc {
	private UserData userdata=null;
	private BookData bookdata=null;
	private RecordData recorddata=null;
	public BookSrcImpl() {
		bookdata=new BookDataImpl();
		recorddata=new RecordDataImpl();
	}
	@Override
	public boolean addBook(Book book){
		return bookdata.saveBook(book);
	}

	@Override
	public boolean delBook(int bid){
		return bookdata.delBook(bid);
	}

	@Override
	public boolean updateBook(Book book)  {
		return bookdata.updateBook(book);
	}

	@Override
	public List<Book> findAllBook() {
		return bookdata.findAllBook();
	}

	@Override
	public List<Book> findBookByName(String bname){
		return bookdata.findBookByName(bname);
	}

	@Override
	public Book findBookById(int bid)  {
		return bookdata.findBookById(bid);
	}

	@Override
	public int lendBook(int bid, int uid){
		Book book=bookdata.findBookById(bid);
		User user=userdata.findUserById(uid);
		if(book==null) {
			return 0;      //û���ҵ�
		}else {
			if(book.getStatus()==0) {
				return 1;  //�Ѿ������
			}else {
				book.setStatus(0);//�޸�״̬��ʾ�ѽ��
				boolean flag1=bookdata.updateBook(book);//�������ݿ�
				String uname=user.getUname();
				Date date = new Date();
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
				String today=dateFormat.format(date);
				Record record=new Record(uid,uname,book.getId(),book.getBname(),today,null);
				boolean flag2=recorddata.saveRecord(record);//�����¼
				if(flag1&&flag2) {
					return 3;//�ɹ����
				}else {
					return 2;//���ʧ��
				}
			}
		}
	}

	@Override
	public int returnBook(int rid) {
		Record record=recorddata.findRecordById(rid);
		if(record==null) {
			return 1;//δ���ҵ���¼
		}else if(record.getReturnTime()!=null) {
			return 2;//�Ѿ��黹��
		}else {
			record.setReturnTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			boolean flag1=recorddata.updateRecord(record);//�������ݿ�
			Book book =bookdata.findBookById(record.getBid());//�ҵ���Ӧ���鼮
			book.setStatus(1);//��Ϊ�ɽ���״̬
			boolean flag2=bookdata.updateBook(book);//�������ݿ�
			if(flag1&&flag2) {
				return 3;//�ɹ��黹
			}else {
				return 4;//�黹ʧ��
			}
		}
	}

	@Override
	public List<Book> lendAva()  {
		return bookdata.findBookByStatus(1);
	}

	@Override
	public List<Book> lendUnAva()  {
		// TODO Auto-generated method stub
		return bookdata.findBookByStatus(0);
	}

}
