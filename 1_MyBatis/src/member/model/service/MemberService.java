package member.model.service;

import static common.Template.getSqlSession;

import org.apache.ibatis.session.SqlSession;

import member.model.dao.MemberDAO;
import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberService {
// Service가 하는 일(역할)
// 커넥션 연결, DAO 연결, 트랜젝션 해주기
	public Member selectMember(Member m) throws MemberException {
		SqlSession session = getSqlSession();
		
		Member loginUser = new MemberDAO().selectMember(session, m);
		
//		session.close(); MemberDAO에서 해줌
		
		return loginUser;
	}

	public void insertMember(Member m) throws MemberException {
		SqlSession session = getSqlSession();
		
		new MemberDAO().insertMember(session, m);
		
		// 만약 DAO에서 비정상종료가 되면 이 아래 두개는 안하고 그냥 넘어가게 됨, 
		// service에서 commit과 close가 된다면 그것은 DAO에서 result > 0이라는 뜻이 됨.
		session.commit();
		session.close();
		
	}

	public void updateMember(Member m) throws MemberException {
		SqlSession session = getSqlSession();
		
		new MemberDAO().updateMember(session, m);
		
		session.commit();
		session.close();
	}

	public void updatePwd(Member m) throws MemberException {
		SqlSession session = getSqlSession();
		
		new MemberDAO().updatePwd(session, m);
		
		session.commit();
		session.close();
	}




}
