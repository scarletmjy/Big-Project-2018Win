package changedata.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseMul {
	/**
	 * �����ݿ⽨������
	 * @return ���ݿ����Ӷ���
	 */
	public Connection getConn(){
		Connection conn=null;
		//��������
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//��������
		try {
			conn=DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Desktop/project/data.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * �ͷ���Դ
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public void closeAll(ResultSet rs,PreparedStatement pstmt,Connection conn) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(pstmt!=null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * �޸Ĳ���
	 * @param sql
	 * @param params
	 * @return �Ƿ���ɲ���
	 * @throws Exception
	 */
	public boolean operUpdate(String sql,List<Object> params){
		int res=0;      //Ӱ�������                                             ռλ����
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		conn=getConn();
		try {
			pstmt=conn.prepareStatement(sql);            //װ��sql���
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(params!=null) {
			for(int i=0;i<params.size();i++) {
				try {
					pstmt.setObject(i+1, params.get(i)); //ռλ���滻����1��ʼ������
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//ִ�и���
		try {
			res=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeAll(rs,pstmt,conn);                         //�ر���Դ
		return res>0? true:false;
	}

	/**
	 * ʹ�÷��ͷ�����������Ʒ�װ
	 * @param sql
	 * @param params
	 * @param cls
	 * @return
	 * @throws Exception 
	 */
	public <T> List<T> operFind(String sql,List<Object>params,Class<T> cls){
		Connection conn=null; 
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		conn=getConn();
		List<T> data=new ArrayList<T>();//��������
		try {
			//װ�����ݿ����
			pstmt=conn.prepareStatement(sql);
			//ռλ���滻
			if(params!=null) {
			for(int i=0;i<params.size();i++) {
				pstmt.setObject(i+1, params.get(i));
				}
			}
			rs=pstmt.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();          //�����ڻ�ȡ���� ResultSet�������е����ͺ�������Ϣ�Ķ���
			while(rs.next()) {
				T m=cls.newInstance();                        //�����޲ι��췽��
				//����ѯ���ת��Ϊ��Ӧ�Ķ���
				for(int i=0;i<rsmd.getColumnCount();i++) {
					String colName=rsmd.getColumnName(i+1);   //�����������1��ʼ������
					Object colValue=rs.getObject(colName);    //���������Ӧ��ֵ
					Field field=cls.getDeclaredField(colName);//��ȡ˽�������ֽ������
					field.setAccessible(true);                //��˽���������ÿɷ���Ȩ
					field.set(m, colValue);                   //����������Ը�ֵ
				}
				data.add(m);
		}}catch(Exception e) {
			e.printStackTrace();
		}
		closeAll(rs,pstmt,conn);                              //�ر���Դ
		return data;
	}
}
