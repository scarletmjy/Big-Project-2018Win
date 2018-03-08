package changedata.impl;

import java.util.ArrayList;
import java.util.List;

import changedata.BookData;
import entity.Book;

public class BookDataImpl extends DataBaseMul implements BookData {

	@Override
	public boolean saveBook(Book book)  {
		String sql="insert into books(bname,status)values(?,?)";
		List<Object> params=new ArrayList<Object>();
		params.add(book.getBname());
		params.add(book.getStatus());
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean delBook(int id) {
		String sql="delete from books where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(id);
		return this.operUpdate(sql, params);
	}

	@Override
	public boolean updateBook(Book book)  {
		String sql="update books set bname=?,status=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(book.getBname());
		params.add(book.getStatus());
		params.add(book.getId());
		return this.operUpdate(sql, params);
	}

	@Override
	public List<Book> findAllBook()  {
		String sql ="select id,bname,status from books";
		List<Book> bList=null;
		bList=this.operFind(sql, null, Book.class);
		return bList;
	}

	@Override
	public List<Book> findBookByName(String bname)  {
		String sql ="select id,bname,status from books where bname=?";
		List<Object> params=new ArrayList<Object>();
		List<Book> bList=null;
		params.add(bname);
		bList=this.operFind(sql, params, Book.class);
		return bList;
	}

	@Override
	public Book findBookById(int bid)  {
		String sql ="select id,bname,status from books where id=?";
		List<Object> params=new ArrayList<Object>();
		List<Book> bList=null;
		params.add(bid);
		bList=this.operFind(sql, params, Book.class);
		if(bList.size()>0) {
			return bList.get(0);
		}
		return null;
	}

	@Override
	public List<Book> findBookByStatus(int status)  {
		String sql ="select id,bname,status from books where status=?";
		List<Object> params=new ArrayList<Object>();
		List<Book> bList=null;
		params.add(status);
		bList=this.operFind(sql, params, Book.class);
		return bList;
	}

}
