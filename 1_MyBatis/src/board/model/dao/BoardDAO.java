package board.model.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import board.model.exception.BoardException;
import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.SearchCondition;
import member.model.exception.MemberException;

public class BoardDAO {
	public int getListCount(SqlSession session) throws BoardException {
		int listCount = session.selectOne("boardMapper.selectListCount");
		// 넘겨줄 값 없음 -> parameterType은 필요하지 않음
		
		if(listCount <= 0) {
			session.close();
			throw new BoardException("게시물 조회에 실패하였습니다.");
		}
		
		return listCount;
	}

	public ArrayList<Board> selectBoardList(SqlSession session, PageInfo pi) throws BoardException {
		// currentPage i페이지에서 i
		// boardLimit = 5
		
		// 1페이지 1 2 3 4 5 => 앞 0개 빼고 5개 선택
		// 2페이지 6 7 8 9 10 => 앞 5개 빼고 5개 선택
		// 3페이지 11 12 => 앞 10개 빼고 2개 선택
		
		// 앞 n개 빼고 ~ => n = (currentPage-1)*boardLimit
		
		// 몇 개의 게시글을 건너 뛸 것인지에 대한 값을 담고 있는 변수
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		// offset만큼의 게시글을 건너뛰고 boardLimit개수만큼의 게시글을 가져오겠다라는 정보를 RowBounds객체에 담음
		
		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectBoardList", null, rowBounds);
		// String arg0, Object arg1, RowBounds arg2
		// 어떤 쿼리에 연결할 것인지, 이러한 값을 보내겠다, 페이징 처리를 하기 위해 이 rowBounds를 사용하겠다
		// 두 번째 null인 이유 : 그냥 전체를 다 가지고 오는 쿼리만 쓰면 됨
		
		if(list == null) {
			session.close();
			throw new BoardException("게시글을 불러오는데 실패했습니다.");
		}
		
		return list;
	}

	public void updateCount(SqlSession session, int bId) throws BoardException {
		int result = session.update("boardMapper.updateCount", bId);
		
		if(result <= 0) {
			session.rollback();
			session.close();
			throw new BoardException("게시물 조회 증가에 실패하였습니다.");
		}
	}
	
	public Board selectBoardDetail(SqlSession session, int bId) throws BoardException {
		Board board = session.selectOne("boardMapper.selectBoardDetail", bId);
		
		if(board == null) {
			session.close();
			throw new BoardException("게시물 조회에 실패하였습니다.");
		}
		
		return board;
	}

	public int getSearchResultListCount(SqlSession session, SearchCondition sc) throws BoardException {
		int listCount = session.selectOne("boardMapper.selectSearchResultListCount", sc); // 게시글 개수만 가져오면 됨
		// sc를 보내주지 않으면 페이징 오류가 생김
		
		if(listCount <= 0) {
			session.close();
			throw new BoardException("검색 결과의 카운트 조회에 실패하였습니다.");
		}
		
		return listCount;
	}

	public ArrayList<Board> selectSearchResultList(SqlSession session, SearchCondition sc, PageInfo pi) throws BoardException {
		int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
		
		RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
		
		ArrayList<Board> list = (ArrayList)session.selectList("boardMapper.selectSearchResultList", sc, rowBounds);
		
		if(list == null) {
			session.close();
			throw new BoardException("검색 결과 리스트를 조회하는데 실패하였습니다.");
		}
		
		return list;
	}


}
