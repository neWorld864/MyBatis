package member.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.GregorianCalendar;

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
 * Servlet implementation class MemberUpdateForm
 */
@WebServlet("/mupdate.me")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
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
		String userName = request.getParameter("userName");
		String nickName = request.getParameter("nickName");
		String email = request.getParameter("email");
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int date = Integer.parseInt(request.getParameter("date"));
		Date birthDay = new Date(new GregorianCalendar(year, month-1, date).getTimeInMillis());
		String gender = request.getParameter("gender");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		
		Member m = new Member(userId, member.getUserPwd(), userName, nickName, email, birthDay, gender, phone, address, null, null, null);
		
		MemberService service = new MemberService();
		try {
			service.updateMember(m);
			// 세션에 있는 값으로 내 정보 보기를 해줬기 때문에 세션 값을 업데이트 해주어야 함
			// 그러기 위해서 로그인 했던 메소드를 사용하여 디비에 있는 값을 새로 가져와 세팅해줄 것
			Member newMember = service.selectMember(m);
			session.setAttribute("loginUser", newMember);
			response.sendRedirect(request.getContextPath() + "/info.me");
			
		} catch (MemberException e) {
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("WEB-INF/views/member/memberUpdateForm.jsp").forward(request, response);
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
