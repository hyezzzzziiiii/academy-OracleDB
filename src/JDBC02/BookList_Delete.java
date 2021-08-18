package JDBC02;  

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BookList_Delete {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null; 
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "tiger");
			// sql패키지 내 DriverManage클래스의 getConnection메서드로 url, id, pw를 얻어오고
			// Connection 객체인 con에 저장
			Scanner sc = new Scanner(System.in);
			System.out.println("삭제할 도서번호를 선택하세요 : ");
			String num = sc.nextLine();
			
			String sql = "delete from booklist where booknum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, num);
			
			int result  = pstmt.executeUpdate();  
			//executeUpdate로 pstmt에 setString으로 얻는 값이 있는지 없는 지 T/F판단하고 result에 저장.
			if(result == 1)System.out.println("삭제성공 ~"); //오라클 성공은 1로받음
			else System.out.println("삭제실패 ㅠ");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();	
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
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
