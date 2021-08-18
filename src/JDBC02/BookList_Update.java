package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BookList_Update {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null; 
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "tiger");
			
			Scanner sc = new Scanner(System.in);
			System.out.print("수정할 도서의 도서번호를 선택하세요: ");
			String num = sc.nextLine();
			System.out.print("수정할 항목을 선택하세요. 1. 제목 2. 출판년도 3. 출고가격 ");
			String input = sc.nextLine();
			String sql = ""; // null과 ""의 차이점 의문
			
			switch(input) {
			case "1":
				System.out.print("수정할 제목을 입력하세요");
				String subject = sc.nextLine();
				sql = "Update booklist set subject = ? where booknum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1,  subject);
				pstmt.setString(2,  num );
				break;
			case "2":
				System.out.print("수정할 출판년도을 입력하세요");
				String makeyear = sc.nextLine();
				sql = "Update booklist set makeyear = ? where booknum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,  Integer.parseInt(makeyear));
				pstmt.setString(2,  num );
				break;
			case "3":
				System.out.print("수정할 출고가격을 입력하세요");
				String outprice = sc.nextLine();
				sql = "Update booklist set outprice = ? where booknum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1,  Integer.parseInt(outprice));
				pstmt.setString(2,  num );  
				break;
			}
			int result = pstmt.executeUpdate(); //PreparedStatement의 메서드
			if(result == 1) System.out.println("수정성공 ~");  //오라클 성공은 1로받음
			else System.out.println("수정실패 ~");
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
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
