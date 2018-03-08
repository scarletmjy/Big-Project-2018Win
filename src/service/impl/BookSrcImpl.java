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
			return 0;      //没有找到
		}else {
			if(book.getStatus()==0) {
				return 1;  //已经被借出
			}else {
				book.setStatus(0);//修改状态表示已借出
				boolean flag1=bookdata.updateBook(book);//更新数据库
				String uname=user.getUname();
				Date date = new Date();
				SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
				String today=dateFormat.format(date);
				Record record=new Record(uid,uname,book.getId(),book.getBname(),today,null);
				boolean flag2=recorddata.saveRecord(record);//插入记录
				if(flag1&&flag2) {
					return 3;//成功借出
				}else {
					return 2;//借出失败
				}
			}
		}
	}

	@Override
	public int returnBook(int rid) {
		Record record=recorddata.findRecordById(rid);
		if(record==null) {
			return 1;//未查找到记录
		}else if(record.getReturnTime()!=null) {
			return 2;//已经归还了
		}else {
			record.setReturnTime(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			boolean flag1=recorddata.updateRecord(record);//更新数据库
			Book book =bookdata.findBookById(record.getBid());//找到对应的书籍
			book.setStatus(1);//变为可借阅状态
			boolean flag2=bookdata.updateBook(book);//更新数据库
			if(flag1&&flag2) {
				return 3;//成功归还
			}else {
				return 4;//归还失败
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
