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
import common.Pagination;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/selectList.bo")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		BoardService service = new BoardService();
		
		int currentPage = 1;
		if(request.getParameter("currentPage") != null) {
			// 사용자가 페이지에 위치하고 있을 때 그 페이지를 int로 변환해줌
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		
		try {
			int listCount = service.getListCount(); // 전체 게시글 개수
			PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
			
			ArrayList<Board> list = service.selectBoardList(pi);
			
			request.setAttribute("list", list);
			request.setAttribute("pi", pi);
			
			request.getRequestDispatcher("WEB-INF/views/board/boardList.jsp").forward(request, response);
			
		} catch (BoardException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("WEB-INF/views/common/errorPage.jsp").forward(request, response);
		} // 전체 게시글 개수
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
