package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.exception.BoardException;
import board.model.service.BoardService;
import board.model.vo.Board;
import board.model.vo.PageInfo;
import board.model.vo.SearchCondition;
import common.Pagination;

/**
 * Servlet implementation class BoardSearchServlet
 */
@WebServlet("/search.bo")
public class BoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// url을 통해 넘겨준 값들을 받는 것
		String condition = request.getParameter("searchCondition");
		String value = request.getParameter("searchValue");
		/*
		 	검색 후 버튼 눌렀을 때 NullPointerException 뜨는 이유
		 	처음 검색을 눌렀을 때 
		 	http://localhost:9180/1_MyBatis/search.bo?searchCondition=writer&searchValue=%EC%9A%B4%EC%98%81%EC%9E%90
		 	이런 식으로 url이 떠서 위에서 getParameter로 값을 받아올 수 있음(searchCondition, searchValue)
		 	하지만 다른 버튼을 눌렀을 경우(ex. 다음, 이전, 숫자버튼)
		 	url이 http://localhost:9180/1_MyBatis/search.bo?currentPage=2
		 	이런 식으로 바뀜
		 	==> url에서 값을 받아올 수 없음. 그래서 NullPointerException이 뜨는 것임
		 */
		
		BoardService service = new BoardService();
		
		SearchCondition sc = new SearchCondition();	
		if(condition.equals("writer")) sc.setWriter(value);
		else if(condition.equals("title")) sc.setTitle(value);
		else if(condition.equals("content")) sc.setContent(value);
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		try {
			int listCount = service.getSearchResultListCount(sc);
			
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
			
			ArrayList<Board> list = service.selectSearchResultList(sc, pi);
			
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			request.setAttribute("searchCondition", condition);
			request.setAttribute("searchValue", value);
			
			request.getRequestDispatcher("WEB-INF/views/board/boardList.jsp").forward(request, response);
		} catch (BoardException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
