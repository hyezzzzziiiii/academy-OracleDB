package JDBC04;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

// 데이터 추가 2 2 6 7 500
public class in_OutDriver {

		public static void main(String[] args) {
			Scanner sc = new Scanner(System.in);
			System.out.println("\n*** 메뉴 선택 ***");
			System.out.printf("1. 프로그램 종료 2. 데이터추가 3. 데이터열람 4. 데이터수정 5. 데이터삭제");

			System.out.print(">>메뉴 선택 : "); 

			String choice = sc.nextLine();

			while( !choice.equals("1") ) {

			switch(choice) {
			case "3": selectData(); break;
			case "2": insertData(); break;
			case "4": updateData(); break;
			case "5": deleteData(); break;

			}

			System.out.println("\n*** 메뉴 선택 ***");
			System.out.printf("1. 프로그램 종료 2. 데이터추가 3. 데이터열람 4. 데이터수정 5. 데이터삭제");
			System.out.print(">>메뉴 선택 : "); 
			choice = sc.nextLine();

			}

			}
			
			private static void selectData() {

			In_OutDao idao = new In_OutDao();

			ArrayList<In_OutDto> list = null;

			list = idao.select();

			System.out.println("대여일자\t\t순번\t도서\t회원\t할인금액");

			System.out.println("-----------------------------------------------------------");

			for(In_OutDto dto : list) { // 리스트에서 꺼낸 하나의 객체는 BookDto형이며, 반복은 갯수만큼

			System.out.printf("%s\t%d\t%s\t%s\t%d\t\n", dto.getOut_date(), 

			dto.getIndexk(), dto.getBooknum(), dto.getPersonnum(), dto.getDiscount() );

			} 

			}
			private static void insertData() {

			// 날짜는 오늘날짜 , 순번 : 오늘날짜에서 가장큰 순번 + 1 -> 임의의 숫자를 넣되
			// 날짜 + 순번이 중복되지 않게 입력
			In_OutDto idto = new In_OutDto();
			Scanner sc = new Scanner(System.in);
			//System.out.print("순번을 입력하세요: ");
			//idto.setIndexk( Integer.parseInt( sc.nextLine() ) );
			System.out.print("도서번호를 입력하세요: ");

			idto.setBooknum( sc.nextLine() );

			System.out.print("회원번호를 입력하세요: ");

			idto.setPersonnum( sc.nextLine() );

			System.out.print("할인금액을 입력하세요: ");

			idto.setDiscount( Integer.parseInt( sc.nextLine() ) );
			
			In_OutDao idao = new In_OutDao();

			int result = idao.insert(idto);

			if(result == 1) System.out.println("데이터 삽입 성공");

			else System.out.println("데이터 삽입 실패");

			}
			
			private static void updateData() {

			In_OutDto idto = new In_OutDto();

			Scanner sc = new Scanner(System.in);

			In_OutDao idao = new In_OutDao();

			// 수정할 사항이 없는 필드(null - 입력하지 않음)는 수정 안함

			// 수정 값을 입력한 필드만 수정

			String in;

			System.out.print("수정하고자하는 매출의 날짜를 입력하세요('yy/mm/dd')-필수");
			// 문자로 입력 받은 날짜 -> java.util.Date 로 형변환 -> java.sql.Date 로 형변환 ->
			// idto.setOut_date() 로 저장
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
			java.util.Date uDate = null;
			while(true) {
			try {
			uDate = sdf.parse( sc.nextLine() );
			break;
			} catch (ParseException e) {
			System.out.println("잘못입력하셨습니다. 다시입력하세요(yy/MM/dd)"); 

			}
			}

			java.sql.Date sDate = new java.sql.Date(uDate.getTime());

			idto.setOut_date(sDate);
			
			
			System.out.print("수정하고자하는 매출의 순번을 입력하세요-필수");

			if((in=sc.nextLine()).equals("")) idto.setIndexk( 0 );

			else idto.setIndexk( Integer.parseInt( in ) );
			System.out.print("수정할 도서의 도서번호를 선택하세요 (수정하지 않으려면 Enter): ");

			idto.setBooknum( sc.nextLine() );
			
			System.out.print("수정할 도서의 회원번호를 선택하세요 (수정하지 않으려면 Enter): ");

			idto.setPersonnum( sc.nextLine() );
			
			System.out.print("수정하고자하는 할인금액을 입력하세요(수정하지 않으려면 Enter)");

			if((in=sc.nextLine()).equals("")) idto.setDiscount( 0 );

			else idto.setDiscount( Integer.parseInt( in ) );
			
			int result = idao.update(idto);

			if(result==1)System.out.println("레코드 수정 성공");

			else System.out.println("레코드 수정 실패");}

			private static void deleteData() {

			Scanner sc = new Scanner(System.in);

			In_OutDao idao = new In_OutDao();

			String in;

			System.out.print("수정하고자하는 매출의 날짜를 입력하세요('yy/mm/dd')-필수");

			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");

			java.util.Date uDate = null;

			while(true) {
			try {
			uDate = sdf.parse( sc.nextLine() );
			break;
			} catch (ParseException e) {
			System.out.println("잘못입력하셨습니다. 다시입력하세요(yy/MM/dd)"); 
			}
			}

			java.sql.Date sDate = new java.sql.Date(uDate.getTime());
			System.out.print("수정하고자하는 매출의 순번을 입력하세요-필수");

			int indexk = 0;
			if(!(in=sc.nextLine()).equals("")) indexk = Integer.parseInt(in); 
			int result = idao.delete(sDate, indexk);
			if( result == 1) System.out.println("삭제 성공");
			else System.out.println("삭제 실패");

			}

			
		}
	