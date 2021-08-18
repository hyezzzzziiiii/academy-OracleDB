package JDBC03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

// 4번 출판년도 2000로 수정(update)
// 멘탈의 연금술 2020 6000 16000 전체 추가 (insert)
public class BookDriver {
	String driver = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	Connection con = null; 
    PreparedStatement pstmt = null; 
	ResultSet rs = null; 

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);  // 사용자에게 메뉴를 보여줌

		System.out.println("\n*** 메뉴 선택 ***");
		System.out.printf("1. 프로그램 종료");
		System.out.printf(" 2. 데이터추가");
		System.out.printf(" 3. 데이터열람");
		System.out.printf(" 4. 데이터수정");
		System.out.println(" 5. 데이터삭제");
		System.out.print(">>메뉴 선택 : "); 

		String choice = sc.nextLine();

		switch(choice) {
		case "3":
		selectData();
		break;
		case "2":
		insertData();
		break;
		case "4":
		updateData();
		break;
		case "5":
		deleteData();
		break;
		}

		}
		public static void deleteData() {
			Scanner sc = new Scanner(System.in);​
			BookDao bdao = new BookDao();
			System.out.print("삭제할 도서번호를 입력하세요: ");
			String booknum=sc.nextLine();
			int result = bdao.delete(booknum);
			if(result==1)System.out.println("레코드 삭제 성공");
			else System.out.println("레코드 삭제 실패");
		}
		
		public static void updateData() {
		BookDto bdto = new BookDto();
		Scanner sc = new Scanner(System.in);
		BookDao bdao = new BookDao();
		// 수정할 사항이 없는 필드(null - 입력하지 않음)는 수정 안함
		// 수정 값을 입력한 필드만 수정
		String in;
		
		System.out.print("수정할 도서의 도서번호를 선택하세요 (필수): ");
		bdto.setBooknum( sc.nextLine() );​
		System.out.print("제목을 입력하세요(수정하지 않으려면 Enter): ");
		bdto.setSubject( sc.nextLine() );
		System.out.print("출판년도 입력하세요(수정하지 않으려면 Enter): "); 
		// in 에 입력 받은 내용이 "" 라면(아무것도 입력하지 않고 엔터쳤다면) 0 으로 , 아니면 입력값으로
		if((in=sc.nextLine()).equals("")) bdto.setMakeyear( 0 );
		else bdto.setMakeyear( Integer.parseInt( in ) );
		System.out.print("입고가격을 입력하세요(수정하지 않으려면 Enter): ");
		if( (in=sc.nextLine()).equals("")) bdto.setInprice( 0 );
		else bdto.setInprice( Integer.parseInt( in ) );
		System.out.print("출고가격을 입력하세요(수정하지 않으려면 Enter): ");
		if( (in=sc.nextLine()).equals("")) bdto.setOutprice( 0 );
		else bdto.setOutprice( Integer.parseInt( in ) );
		System.out.print("등급을입력하세요(수정하지 않으려면 Enter): ");
		bdto.setGrade( sc.nextLine() ); // 입력값없으면 null, 있으면 그값으로
		int result = bdao.update(bdto);
		if(result==1)System.out.println("레코드 수정 성공");
		else System.out.println("레코드 수정 실패");
		}

		public static void insertData() {
		BookDto bdto = new BookDto();
		Scanner sc = new Scanner(System.in);
		// String subject = sc.nextLine();
		// bdto.setBooknum( subject );
		// -> bdto.setSubject( sc.nextLine() );
		System.out.print("제목을 입력하세요: ");
		bdto.setSubject( sc.nextLine() );
		System.out.print("출판년도 입력하세요: ");
		bdto.setMakeyear( Integer.parseInt( sc.nextLine() ) );
		System.out.print("입고가격을 입력하세요: ");
		bdto.setInprice( Integer.parseInt( sc.nextLine() ) );
		System.out.print("출고가격을 입력하세요: ");
		bdto.setOutprice( Integer.parseInt( sc.nextLine() ) );
		System.out.print("등급을입력하세요: ");
		bdto.setGrade( sc.nextLine() );
		BookDao bdao = new BookDao();
		int result = bdao.insert(bdto);
		if(result==1)System.out.println("레코드 추가 성공");
		else System.out.println("레코드 추가 실패");
		}

		public static void selectData() {
		// 데이터의 열람
		// 1. 데이터 열람은 데이터베이스에서 레코드 목록을 조회하고 그 결과 전달받아 
		// 화면에 표시하는 역할입니다
		// 2. 데이터 베이스 레코드 목록을 조회하는 역할은 다른 클래스객체의 메서드를 실행시켜서 
		// 전달 받습니다
		// 3. 클래스의 이름은 BookDao 이고 그안의 멤버 메서드 중 select() 메서드를 이용합니다
		BookDao bdao = new BookDao();
		// 4. 리턴값은 한개의 레코드 데이터를 담을수 있는 BookDto 클래스의 객체에 담기고,
		// 그 객체들은 ArrayList 에 데이터베이스 테이블에 들어 있는 레코드 수만큼 담겨서
		// 리턴됩니다. 리턴값의 자료형 ArrayList<BookDto>
		ArrayList<BookDto> list = null;
		list = bdao.select();
		// list 안에는 BookDto 형태의 객체들이 데이터베이스 테이블의 레코드 갯수 만큼 들어있습니다.
		System.out.println("도서번호\t제목\t출판년도\t입고가격\t출고가격\t등급");
		System.out.println("-----------------------------------------------------------");
		for(BookDto dto : list) { // 리스트에서 꺼낸 하나의 객체는 BookDto형이며, 반복은 갯수만큼
		System.out.printf("%s\t%s\t%d\t%d\t%d\t%s\n", dto.getBooknum(), 
		dto.getSubject(), dto.getMakeyear(), dto.getInprice(), dto.getOutprice(),
		dto.getGrade());
		} 
	}
}