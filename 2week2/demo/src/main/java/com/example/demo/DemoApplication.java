package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.MessageDigest;
import java.sql.*;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication {

	public static String code(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] byteDigest = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < byteDigest.length; offset++) {
				i = byteDigest[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean createUser(String userName, String passwordMD5){
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost:3306/demo";
		final String USER = "root";
		final String PASS = "Yhf@1018";

		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.prepareStatement("insert into users(users_name, users_password) values(?,?)");
			stmt.setString(1, userName);
			stmt.setString(2, passwordMD5);
			stmt.executeUpdate();

			stmt.close();
			conn.close();
		}catch(SQLException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		}catch(Exception e){
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 关闭资源
			try{
				if(stmt!=null) stmt.close();
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");

		return true;
	}

	public static boolean login(String userName, String passwordMD5){
		//System.out.println(code("12345678"));
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost:3306/demo";
		final String USER = "root";
		final String PASS = "Yhf@1018";

		Connection conn = null;
		Statement stmt = null;
		try{
			// 注册 JDBC 驱动
			Class.forName(JDBC_DRIVER);

			// 打开链接
			System.out.println("连接数据库...");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);

			// 执行查询
			System.out.println(" 实例化Statement对象...");
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT users_name, users_password FROM users";
			ResultSet rs = stmt.executeQuery(sql);

			// 展开结果集数据库
			while(rs.next()){
				// 通过字段检索
				String users_name = rs.getString("users_name");
				String users_password = rs.getString("users_password");

				// 输出数据
				System.out.print(", users_name: " + users_name);
				System.out.print(", users_password: " + users_password);
				System.out.print("\n");
			}
			// 完成后关闭
			rs.close();
			stmt.close();
			conn.close();
		}catch(SQLException se){
			// 处理 JDBC 错误
			se.printStackTrace();
		}catch(Exception e){
			// 处理 Class.forName 错误
			e.printStackTrace();
		}finally{
			// 关闭资源
			try{
				if(stmt!=null) stmt.close();
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(conn!=null) conn.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		System.out.println("Goodbye!");
		return true;
	}

	public static void main(String[] args) {

		SpringApplication.run(DemoApplication.class, args);

/*		Scanner myObj = new Scanner(System.in);
		System.out.println("Enter userName");
		String userName = myObj.nextLine();

		System.out.println("Enter passWord");
		String passWord = myObj.nextLine();
		String passwordMD5 = code(passWord);*/

		System.out.println(code("12345678"));
		//login(userName, passwordMD5);
		String a = "aaa";
		String b = "a";
		String c = "aaa";
		b = b + "aa";
		System.out.println(a == b);
		System.out.println(a.equals(b));
		System.out.println(a == c);
	}

}
