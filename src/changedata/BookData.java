package changedata;

import java.util.List;

import entity.Book;

public interface BookData {
	public boolean saveBook(Book book) ;            //添加书籍
	public boolean delBook(int id) ;                //删除书籍
	public boolean updateBook(Book book) ;          //更改信息
	public List<Book> findAllBook() ;               //查找所有书籍
	public List<Book> findBookByName(String bname) ;//查找指定名称的书籍
	public Book findBookById(int bid) ;             //根据编号查询
	public List<Book> findBookByStatus(int status) ;//根据状态查询

}
