package com.winreal.ex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class TestOjdbc {
	
	@Test
	public void calcTest() {
		Calc calc = new Calc(); //주입받을 준비
		int res = calc.add(1, 2);
		
		// 예상결과값, 실제결과값
		assertEquals(4, res);
	}
	
	
	@Test
	public void ojdbcRest() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 커넥션 생성
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "library", "1234");

			ResultSet rs = conn.createStatement()
								.executeQuery("select to_char(sysdate, 'yyyy-mm-dd') today from dual");

			rs.next();
			System.out.println(rs.getString(1));
			System.out.println(conn);
			
			assertNotNull(conn);
			
		} catch (SQLException e) {
			System.err.println("라이브러리를 확인해주세요!");
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

// 이렇게 해도 히카리풀 테스트 할 수 있지만 우린 	bean객체가 잘 만들어졌나 테스트해야한다
//	public void hikariTest() {
//		HikariConfig config = new HikariConfig();
//		config.setJdbcUrl("jdbc:mysql://localhost:3306/simpsons");
//		config.setUsername("spring");
//		config.setPassword("spring");
//
//		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
//	}
	
	
}
