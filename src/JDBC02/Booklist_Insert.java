package JDBC02;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

// 시퀀스 : 매번 늘어나는 숫자 발생  시퀀스.nextVal -시퀀스자체에서는 중복안되게 매번 늘어나는 숫자를 발생함 
// 테이블 : 시퀀스가 발생시킨 숫자 사용 - 테이블은 그걸 가져다 써서 중복이 안됨 
// 결과 : 중목안되는 늘어나는 숫자로 테이블 기본키 제약 충족 
// 결론 : 도서번호가 나열이 안됨
public class Booklist_Insert {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null; 
		PreparedStatement pstmt = null; 

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "tiger");
			Scanner sc = new Scanner(System.in);
			System.out.print("제목를 입력하세요");
					String subject = sc.nextLine();
			System.out.print("출판년도를 입력하세요");
					String makeyear = sc.nextLine();
			System.out.print("입고가격을 입력하세요");
					String inprice = sc.nextLine();
			System.out.print("출고가격을입력하세요");
					String outprice = sc.nextLine();
			System.out.print("등급을 입력하세요");
					String grade = sc.nextLine();		
			
			// 아래구문 주의
			String sql = "insert into booklist values(booklist_seq.nextVal,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, subject);
			pstmt.setInt(2, Integer.parseInt(makeyear));
			pstmt.setInt(3, Integer.parseInt(inprice));
			pstmt.setInt(4, Integer.parseInt(outprice));
			pstmt.setString(5, grade);
			
			int result  = pstmt.executeUpdate();
			if(result == 1)System.out.println("저장성공 ~");
			else System.out.println("저장실패 ㅠ");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}catch(Exception e) {
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
