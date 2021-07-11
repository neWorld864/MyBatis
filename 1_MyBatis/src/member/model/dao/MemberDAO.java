package member.model.dao;

import org.apache.ibatis.session.SqlSession;

import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberDAO {

	public Member selectMember(SqlSession session, Member m) throws MemberException {
		// query로 쓸 문장을 메소드로 호출
		// INSERT INTO .... --> insert()
		// UPDATE ... SET ...-> update()
		// DELETE FROM ...   -> delete()
		// SELECT ... FROM ..-> selectXXX()
		//		select를 통해서 반환되는 ResultSet을 보고서 종류 결정
		//			1) selectOne()  : ResultSet의 결과가 0개 또는 1개일 때
		//			2) selectList() : ResultSet의 결과가 0개 이상일 때(여러 개)
		
		// 모든 메소드는 매개 변수의 개수에 따라 오버로딩이 되어있음
		// XXXX(String arg0)
		// XXXX(String arg0, Object arg1)
		//		첫 번째 매개변수 String arg0 : 어느 쿼리에 연결할 것이나 (ex. prop.getProperty("selectMember");)
		//		두 번째 매개변수 Object arg1 : 쿼리에 어떤 값을 전달할 것이냐 (SELECT * FROM MEMBER WHERE USER_ID=? AND USER_PWD=? AND M_STATUS='Y')
		
		Member loginUser = session.selectOne("memberMapper.loginMember", m);
		// member-mapper.xml에서 <mapper namespace="memberMapper"></mapper>로 설정
		
		if(loginUser == null) {
			session.close();
			throw new MemberException("로그인에 실패하였습니다.");
		}
		
		return loginUser;
	}

	public void insertMember(SqlSession session, Member m) throws MemberException {
		int result = session.insert("memberMapper.insertMember", m);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원가입에 실패하였습니다.");
		}
		
		// return을 안 해도 되는 이유
		// throw new MemberException("회원가입에 실패하였습니다.");라는 에러를 강제발생 시킴. -> 강제발생한 에러를 try-catch 하기 전까지는 비정상종료됨
		// 그래서 비정상종료가 된다면 service에서 commit, close를 할 수 없게되니 DAO에서 rollback도 하고 close를 미리 해두자.
		// 만약 if문을 거치지 않는다 -> result > 0이라는 뜻이 됨 ==> service에서 commit과 close가 가능하게 됨 
		// ==> 그래서 굳이 result를 넘기지 않아줘도 됨
	}

	public void updateMember(SqlSession session, Member m) throws MemberException {
		int result = session.update("memberMapper.updateMember", m);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원 수정에 실패하였습니다.");
		}
		
	}

	public void updatePwd(SqlSession session, Member m) throws MemberException {
		int result = session.update("memberMapper.updatePwd", m);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new MemberException("회원 수정에 실패하였습니다.");
		}
	}

}
