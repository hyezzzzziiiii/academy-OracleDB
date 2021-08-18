package JDBC01;  //한번 화인 필요! 결과정도?

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


public class JDBC_insert {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		Connection con = null; 
		PreparedStatement pstmt = null;

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, "scott", "tiger");
			Scanner sc = new Scanner(System.in);
			System.out.print("저장할 번호를 입력하세요"); 
					String num = sc.nextLine();
			System.out.print("이름를 입력하세요");
					String name = sc.nextLine();
			System.out.print("이메일를 입력하세요");
					String email = sc.nextLine();
			System.out.print("전화번호를 입력하세요");
					String tel = sc.nextLine();
			
			// String sql = "insert into customer values("+ num + "," 
			//					 + name + "," + email + "," + tel + "')"; // 옛날 방식
			
					
			String sql = "insert into customer values(?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(num));
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, tel);
			
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
