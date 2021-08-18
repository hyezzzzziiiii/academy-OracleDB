 package JDBC01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// 자바에서 지원하는 데이터베이스 연결 클래스

// 자바에 데이터베이스를 연결하기 위해서는 자신의 컴퓨터의 mysql이나 mariadb, 오라클DB 셋 중에 하나라도 설치가 되어 있어야 사용이 가능합니다. (블로그 참조함 10-14)
// 자바에서 데이터베이스를 사용하려면 JDBC드라이버가 있어야 합니다. 
// 이 JDBC드라이버는 자바 프로그램과 데이터베이스에 연결하기 위한 라이브러리입니다. 
// DBMS에 따라 DB를 다루는 방식이 다르면 사용자들이 알아야하는것이 방대해지기 때문에 JDBC가 인터페이스들만 제공하고 나머지는 각 DBMS에 맞게 구현이 되어 있습니다. 
public class JDBC_Connect {

	public static void main(String[] args) {
		Connection con = null; // 연결된 Connection 객체의 주소를 저장할때 레퍼런스 변수
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String id = "scott";
		String pw = "tiger";
		String driver = "oracle.jdbc.OracleDriver";
		
		try {
			Class.forName(driver); // Class.forName은 메서드로 메서드를 호출하여 오라클DB가 제공하는 드라이버 클래스를 로드시키는 코드
			con = DriverManager.getConnection(url, id, pw);
			// 입력되는 첫번째 값 : URL
			// 두번째 값 : 사용자 계정 ID
			// 세번쨰 값 : 사용자 계정 Password
			// getConnection() 메서드의 리턴값 : 연결되 Database Connection 객체 
			System.out.println("데이터 베이스 연결 성공 ~!!");
		} catch (SQLException e) {
			System.out.println("데이터베이스 연결 실패 ㅠㅠ: DB 연결 정보 오류");
			
		} catch (ClassNotFoundException e) {
			System.out.println("데이터베이스 연결 실패 ㅠㅠ: 드라이버 클래스 파일오류");
		}catch(Exception e) {
			System.out.println("기타 사유로 인한 연결 실패 ㅠㅠ");
		}
		try {
			if(con!=null) con.close();
			System.out.println("데이터베이스 종료 ~!!");
		}catch(SQLException e) {}
	}

}
