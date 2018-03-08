package changedata;

import java.util.List;

import entity.Book;

public interface BookData {
	public boolean saveBook(Book book) ;            //����鼮
	public boolean delBook(int id) ;                //ɾ���鼮
	public boolean updateBook(Book book) ;          //������Ϣ
	public List<Book> findAllBook() ;               //���������鼮
	public List<Book> findBookByName(String bname) ;//����ָ�����Ƶ��鼮
	public Book findBookById(int bid) ;             //���ݱ�Ų�ѯ
	public List<Book> findBookByStatus(int status) ;//����״̬��ѯ

}
