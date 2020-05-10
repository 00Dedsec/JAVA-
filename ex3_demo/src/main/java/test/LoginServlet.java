package test;

import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		HttpSession session = request.getSession();
		String verCode = String.valueOf(session.getAttribute("verCode"));
		
		UserBean usr = new UserBean();
		usr.setValidName("user");
		usr.setValidPassword("password");
		usr.setValidVerCode(verCode);
		
		
		response.setHeader("content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if(usr.isValidUsr(name, password,code)) {
			//out.println("登录成功！"); 
			response.sendRedirect("login.jsp");
			session.setAttribute("isLogin", true);
		}
		else {
			out.println("登录失败！请检查用户名、密码以及验证码！<a href = 'index.jsp'> 重试 </a>");
			session.setAttribute("isLogin", false);
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
