package JDBC01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBC_Select {

	public static void main(String[] args) {
		String driver = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "scott";
		String pw = "tiger";
		Connection con = null; 
		PreparedStatement pstmt = null; //Sql 연결 및 실행 클래스
		ResultSet rs = null; //Sql 명령 수행 결과를 얻는 클래스
		
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pw);
			// 데이터 베이스 연결 후 에는 SQL명령을 사용하기위해 String변수에 SQL명령을 준비합니다.
			// 데이터베이스에 제공되어질 명령이므로 String 형으로 준비합니다.
			String sql = "select * from customer";
			
			// preparedStatement : SQL 문을 Connection 객체에 적용하여 결과를 얻어낼 클래스 도구
			pstmt = con.prepareStatement(sql);  //도구 연결(준비)
			// SQL 명령에 의해 얻어진 결과를 rs에 저장합니다.
			rs = pstmt.executeQuery(); // SQL 실행 결과 리턴: 형식 ResultSet
			System.out.println("번호\t이름\t이메일\t\t\t전화번호");
			System.out.println("------------------------------------------------------------");
			// rs.next() : 다음 레코드로 이동, 다음 레코드가 있으면 true 없으면 false리턴
			while(rs.next()) {
				int num = rs.getInt("num");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String tel = rs.getString("tel");
				System.out.printf("%d\t%s\t%s\t%s\n", num, name, email, tel);
						
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {
			if(con !=null) con.close(); //종료할때 실패하지 말라고 밑에 예외처리 들어감
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
