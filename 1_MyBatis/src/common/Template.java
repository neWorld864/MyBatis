package common;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession; // lib에 mybatis파일을 넣었기 때문에 임포트가 가능한 것
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Template {
	public static SqlSession getSqlSession() {
		SqlSession session = null;
		
		// SqlSession <----------- SqlSessionFactory <----------------- SqlSessionFactoryBuilder
		//            openSession()                   build(inputStream)
		//												ㄴ> mybatis-config.xml : myBatis에 대한 설정정보
		
		
		
		try {
//			SqlSessionFactoryBuilder ssfb = new SqlSessionFactoryBuilder();
// 			Builder의 매개변수로 InputStream를 사용 가능함
//			InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml");
//			SqlSessionFactory ssf = ssfb.build(stream);
//			session = ssf.openSession(false); // = autoCommit(false) : 자동으로 커밋을 하지 않도록 하는 설정
			
			InputStream stream = Resources.getResourceAsStream("/mybatis-config.xml");
			session = new SqlSessionFactoryBuilder().build(stream).openSession(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return session;
	}
	
}
