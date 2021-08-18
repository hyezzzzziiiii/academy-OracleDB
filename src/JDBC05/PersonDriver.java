package JDBC05;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class PersonDriver {
	
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
while(!choice.equals("1")) {
		switch(choice) {
		case "2":
			insertData();
		break;
		case "3":
			selectData();
		break;
		case"4":
			updateData();
		break;
		case "5" :
			deleteData();
		break;
		}
		System.out.println("\n*** 메뉴 선택 ***");
		System.out.printf("1. 프로그램 종료");
		System.out.printf(" 2. 데이터추가");
		System.out.printf(" 3. 데이터열람");
		System.out.printf(" 4. 데이터수정");
		System.out.println(" 5. 데이터삭제");
		System.out.print(">>메뉴 선택 : ");
		
		choice = sc.nextLine();
	}
		
	}
	public static void selectData() {
		//personnum/ personname/ phone/ birth/enterdate bpoint age gender
		PersonDao pdao = new PersonDao();
		ArrayList<PersonDto> list = null;
		list = pdao.select();
	
		System.out.println("번호\t성명\t전화번호\t생년월일\t가입날짜\t점수\t나이\t성별");
		System.out.println("-----------------------------------------------------------");
		for(PersonDto dto : list) { 
		System.out.printf("%s\t%s\t%s\t%s\t%s\t%d\t%d\t%s\t\n", dto.getPersonnum(), 
				dto.getPersonname(), dto.getPhone(), dto.getBirth(), dto.getEnterdate(),
				dto.getBpoint(), dto.getAge(), dto.getGender());
		} 
	}
	public static void insertData() {
		PersonDao pdao = new PersonDao();
		PersonDto pdto = new PersonDto();
		Scanner sc = new Scanner(System.in);
		//personnum/ personname/ phone/ birth/enterdate bpoint age gender

		System.out.print("성명 입력하세요: ");
		pdto.setPersonname( sc.nextLine() );
		System.out.print("생년월일을 입력하세요: ");
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		java.util.Date uDate = null;
		while(true) {
			try {
			uDate = sdf.parse(sc.nextLine());
			break;
			}catch(ParseException e) {
				System.out.println("잘못입력하셨습니다. 다시 입력바랍니다.");
			}
		}
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		pdto.setBirth(sDate);
		System.out.print("전화번호를 입력하세요: ");
		pdto.setPhone(sc.nextLine());
		System.out.print("나이를 입력하세요: ");
		pdto.setAge( Integer.parseInt( sc.nextLine() ));
		System.out.print("성별을 입력하세요: ");
		pdto.setGender( sc.nextLine() );

		int result = pdao.insert(pdto);
		if(result==1)System.out.println("레코드 추가 성공");
		else System.out.println("레코드 추가 실패");
		}
	
	
	public static void updateData() {
		PersonDto pdto = new PersonDto();
		Scanner sc = new Scanner(System.in);
		PersonDao pdao = new PersonDao();
		String in = null;
		

		System.out.print("수정할 회원의 회원번호를 선택하세요 (필수): ");
		pdto.setPersonnum(sc.nextLine());
		System.out.print("수정할 성명을 입력하세요(수정하지 않으려면 Enter): ");
		pdto.setPersonname( sc.nextLine() );
		System.out.print("수정할 생년월일을 입력하세요(수정하지 않으려면 Enter): ");
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
		java.util.Date uDate = null;
		while(true) {
		try {
		uDate = sdf.parse(in );
		break;
		} catch (ParseException e) {
		System.out.println("잘못입력하셨습니다. 다시입력하세요(yy/MM/dd)"); 

		}
		}

		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		pdto.setBirth(sDate);
		System.out.print("수정할 점수를 입력하세요(수정하지 않으려면 Enter): ");
		if( (in=sc.nextLine()).equals("")) pdto.setBpoint( 0 );
		else pdto.setBpoint( Integer.parseInt( in ) );
		
		System.out.print("수정할 나이를입력하세요(수정하지 않으려면 Enter): ");
		if( (in=sc.nextLine()).equals("")) pdto.setAge( 0 );
		else pdto.setAge( Integer.parseInt( in ) );
	
		System.out.print("수정할 성별을 입력하세요(수정하지 않으려면 Enter): ");
		pdto.setGender( sc.nextLine() );
		
		
		int result = pdao.update(pdto);
		if(result==1)System.out.println("레코드 수정 성공");
		else System.out.println("레코드 수정 실패");
		}
	
	public static void deleteData() {
		Scanner sc = new Scanner(System.in);
		PersonDao pdao = new PersonDao();
		System.out.print("삭제할 번호를 입력하세요: ");
		String personnum=sc.nextLine();
		int result = pdao.delete(personnum);
		if(result==1)System.out.println("레코드 삭제 성공");
		else System.out.println("레코드 삭제 실패");
		}
	}
	
