package service;

import java.util.List;

import entity.Book;

public interface BookSrc {
	public boolean addBook(Book book);             //添加书籍
	public boolean delBook(int bid);               //删除书籍
	public boolean updateBook(Book book);          //修改书籍
	public List<Book> findAllBook();               //查询所有书籍
	public List<Book> findBookByName(String bname);//根据书名查询
	public Book findBookById(int bid);             //根据id查询
	public int lendBook(int bid,int uid);          //借阅
	public int returnBook(int rid);                //归还
	public List<Book> lendAva();                   //可借书籍
	public List<Book> lendUnAva();                 //不可借阅书籍
	
}
