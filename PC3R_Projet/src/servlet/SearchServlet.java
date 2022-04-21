package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!User.isConnected(request.getSession())) {
			response.sendRedirect("/");
			return;
		}
		
		response.setContentType("application/json");
		response.setHeader("Content-Disposition", "inline");
		
		try {
			
			User user = User.getConnected(request.getSession());
			request.setAttribute("user", user);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("error", e.getMessage());
			
		}
		
		request.getRequestDispatcher("/WEB-INF/search.jsp").include(request, response);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
