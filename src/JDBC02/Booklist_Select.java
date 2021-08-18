package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Booklist_Select {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null; 
		PreparedStatement pstmt = null; 
		ResultSet rs = null; 
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "tiger");
			String sql = "select * from booklist";
			pstmt = con.prepareStatement(sql);  
			rs = pstmt.executeQuery(); 
			
			System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급");
			System.out.println("------------------------------------------------------------------");
			while(rs.next()) { // sql패키지 내 Resultset클래스 next()메서드 boolean 값을 파악
				String booknum = rs.getString("booknum");
				String subject = rs.getString("subject");
				int makeyear = rs.getInt("makeyear");
				int inprice = rs.getInt("inprice");
				int outprice = rs.getInt("outprice");
				String grade = rs.getString("grade");
				System.out.printf("%s\t%s\t%d\t%d\t%s\t%s\n", 
								booknum, subject, makeyear, inprice, outprice, grade);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstmt !=null) pstmt.close(); 
			if(con !=null) con.close(); 
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
}
