package member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.exception.MemberException;
import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberPwdUpdateServlet
 */
@WebServlet("/mPwdUpdate.me")
public class MemberPwdUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberPwdUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		Member member = (Member)session.getAttribute("loginUser");
		String userId = member.getUserId();
		String userPwd = request.getParameter("userPwd");
		String newPwd = request.getParameter("newPwd");
		
		Member m = new Member(userId, newPwd);
		
		MemberService service = new MemberService();
		
		try {
			service.updatePwd(m);
			
			Member newMember = service.selectMember(m);
			session.setAttribute("loginUser", newMember);
			request.getSession().invalidate();
			response.sendRedirect(request.getContextPath());
		} catch (MemberException e) {
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
