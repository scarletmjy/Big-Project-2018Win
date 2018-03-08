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
	 * 与数据库建立连接
	 * @return 数据库连接对象
	 */
	public Connection getConn(){
		Connection conn=null;
		//加载驱动
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//建立连接
		try {
			conn=DriverManager.getConnection("jdbc:sqlite:C:/Users/HP/Desktop/project/data.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	/**
	 * 释放资源
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
	 * 修改操作
	 * @param sql
	 * @param params
	 * @return 是否完成操作
	 * @throws Exception
	 */
	public boolean operUpdate(String sql,List<Object> params){
		int res=0;      //影响的行数                                             占位符组
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		conn=getConn();
		try {
			pstmt=conn.prepareStatement(sql);            //装载sql语句
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(params!=null) {
			for(int i=0;i<params.size();i++) {
				try {
					pstmt.setObject(i+1, params.get(i)); //占位符替换（从1开始计数）
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		//执行更新
		try {
			res=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closeAll(rs,pstmt,conn);                         //关闭资源
		return res>0? true:false;
	}

	/**
	 * 使用泛型方法、反射机制封装
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
		List<T> data=new ArrayList<T>();//泛型数组
		try {
			//装载数据库语句
			pstmt=conn.prepareStatement(sql);
			//占位符替换
			if(params!=null) {
			for(int i=0;i<params.size();i++) {
				pstmt.setObject(i+1, params.get(i));
				}
			}
			rs=pstmt.executeQuery();
			ResultSetMetaData rsmd=rs.getMetaData();          //可用于获取关于 ResultSet对象中列的类型和属性信息的对象
			while(rs.next()) {
				T m=cls.newInstance();                        //调用无参构造方法
				//将查询结果转变为对应的对象
				for(int i=0;i<rsmd.getColumnCount();i++) {
					String colName=rsmd.getColumnName(i+1);   //获得列名（从1开始计数）
					Object colValue=rs.getObject(colName);    //获得列所对应的值
					Field field=cls.getDeclaredField(colName);//获取私有属性字节码对象
					field.setAccessible(true);                //给私有属性设置可访问权
					field.set(m, colValue);                   //给对象的属性赋值
				}
				data.add(m);
		}}catch(Exception e) {
			e.printStackTrace();
		}
		closeAll(rs,pstmt,conn);                              //关闭资源
		return data;
	}
}
