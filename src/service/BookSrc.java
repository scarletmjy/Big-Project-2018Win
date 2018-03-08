package service;

import java.util.List;

import entity.Book;

public interface BookSrc {
	public boolean addBook(Book book);             //����鼮
	public boolean delBook(int bid);               //ɾ���鼮
	public boolean updateBook(Book book);          //�޸��鼮
	public List<Book> findAllBook();               //��ѯ�����鼮
	public List<Book> findBookByName(String bname);//����������ѯ
	public Book findBookById(int bid);             //����id��ѯ
	public int lendBook(int bid,int uid);          //����
	public int returnBook(int rid);                //�黹
	public List<Book> lendAva();                   //�ɽ��鼮
	public List<Book> lendUnAva();                 //���ɽ����鼮
	
}
