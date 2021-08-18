package JDBC04;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class In_OutDao {
	static String driver = "oracle.jdbc.driver.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static Connection con = null;
	static PreparedStatement pstmt = null;
	static ResultSet rs = null;
	
	
	
	
	public static Connection getConnection() {
		Connection cnn = null;
		try {
		Class.forName(driver);
		cnn = DriverManager.getConnection(url, "scott", "tiger");
		} catch (ClassNotFoundException e) { e.printStackTrace();
		} catch (SQLException e) { e.printStackTrace();
		} catch (Exception e) { e.printStackTrace(); }
		return cnn;
		}
	public static void close() {
	try { 
	if(con != null) con.close(); 
	if(pstmt != null) pstmt.close();
	if(rs != null) rs.close();
	}catch (SQLException e) { 
	e.printStackTrace(); 
	}
	}
	public int delete(java.sql.Date sDate, int indexk) {
	int result = 0;
	String sql = "delete from in_out "
					+ " where to_char(Out_date, 'yy/mm/dd') = to_char(? , 'yy/mm/dd') "
					+ " and indexk=?";

	con = getConnection();

	try {
	pstmt = con.prepareStatement(sql);
	pstmt.setDate(1, sDate);
	pstmt.setInt(2, indexk);
	result = pstmt.executeUpdate();
	}catch (SQLException e) { e.printStackTrace(); } 
	close(); 
	return result;

	}
	public int update(In_OutDto idto) {
	int result = 0;
	In_OutDto dtoOrigin = null;
	String sql = "select * from In_Out "
		+ " where to_char(Out_date, 'yy/mm/dd') = to_char(? , 'yy/mm/dd') "
		+ " and indexk=?";

	con = getConnection();
	try {
	pstmt = con.prepareStatement(sql);
	pstmt.setDate(1, idto.getOut_date());
	pstmt.setInt(2, idto.getIndexk());
	rs = pstmt.executeQuery();

	if(rs.next()) {
	dtoOrigin = new In_OutDto();
	dtoOrigin.setOut_date( rs.getDate("out_date"));
	dtoOrigin.setIndexk( rs.getInt("indexk"));
	dtoOrigin.setBooknum( rs.getString("booknum"));
	dtoOrigin.setPersonnum( rs.getString("personnum"));
	dtoOrigin.setDiscount( rs.getInt("discount"));
	}

	System.out.println( dtoOrigin.getBooknum() + " " + dtoOrigin.getPersonnum());
	pstmt.close();
	rs.close();
	//원본데이터에 전달된 idto 의 멤버변수중 값을 갖고 있는 항목만 복사(날짜 순분 포함)
	dtoOrigin.setOut_date( idto.getOut_date() );
	dtoOrigin.setIndexk( idto.getIndexk() );

	if(!idto.getBooknum().equals(""))dtoOrigin.setBooknum(idto.getBooknum() );
	if(!idto.getPersonnum().equals(""))dtoOrigin.setPersonnum(idto.getPersonnum());
	if(idto.getDiscount()!=0)dtoOrigin.setDiscount( idto.getDiscount() );

	sql = "update in_out set booknum=?, personnum=?, discount=? "
			+ " where to_char(Out_date, 'yy/mm/dd') = to_char(? , 'yy/mm/dd') "
			+ " and indexk=?";

	pstmt = con.prepareStatement(sql);
	pstmt.setString(1, dtoOrigin.getBooknum());
	pstmt.setString(2, dtoOrigin.getPersonnum());
	pstmt.setInt(3, dtoOrigin.getDiscount());
	pstmt.setDate(4, dtoOrigin.getOut_date());
	pstmt.setInt(5, dtoOrigin.getIndexk());
	result = pstmt.executeUpdate();
	} catch (SQLException e) {

	e.printStackTrace();

	} 

	close(); 

	return result;
	}
	public int insert(In_OutDto idto) {

		int result = 0;
		String sql = "select booknum from booklist";
		con = getConnection();
		try {
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		int cnt = 0;
		while(rs.next()) 
		if( rs.getString("booknum").equals(idto.getBooknum()) ) 
		cnt++;
		
	if( cnt==0) {
	System.out.println("도서목록에 없는 도서코드입니다.");
	return 0;

	}
	pstmt.close();
	rs.close();

	sql = "select personnum from person";
	pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
	cnt = 0;

	while(rs.next()) 
	if( rs.getString("personnum").equals(idto.getPersonnum()) ) 
	cnt++;
	if( cnt==0) {
	System.out.println("회원 목록에 없는 회원입니다입니다.");
	return 0;
	}
	pstmt.close();
	rs.close();
	
	sql = "select max(indexk) as max_index from in_out "
	+ " where to_char(out_date, 'yy/mm/dd') = to_char(sysdate, 'yy/mm/dd')";

	pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
	
	int indexk = 0;
	if( rs.next() ) indexk = rs.getInt("max_index") + 1;
	else indexk = 1;
	pstmt.close();
	System.out.println(indexk);
	
	sql = "insert into in_out value (sysdate, ?,?,?,?)";
	pstmt = con.prepareStatement(sql);
	pstmt.setInt( 1, indexk );
	pstmt.setString( 2, idto.getBooknum() );
	pstmt.setString( 3, idto.getPersonnum() );
	pstmt.setInt( 4, idto.getDiscount() );
	result = pstmt.executeUpdate();
	} catch (SQLException e) { e.printStackTrace(); }
	close();
	return result;

	}

	public ArrayList<In_OutDto> select(){
	ArrayList<In_OutDto> list = new ArrayList<In_OutDto>();
	con = getConnection();
	String sql = "select * from in_out";
	try {
	pstmt = con.prepareStatement(sql);
	rs = pstmt.executeQuery();
	while( rs.next() ) {
	In_OutDto idto = new In_OutDto();
	idto.setOut_date( rs.getDate("out_date") );
	idto.setIndexk( rs.getInt("indexk") );
	idto.setBooknum( rs.getString("booknum") );
	idto.setPersonnum( rs.getString("personnum") );
	idto.setDiscount( rs.getInt("discount") );
	list.add(idto);
	}
	} catch (SQLException e) { e.printStackTrace(); }
	close();
	return list;

	}

	}
