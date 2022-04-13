package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public GameServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(!User.isConnected(request.getSession())) {
			response.sendRedirect("/");
			return;
		}
		
		try {
		
			User user = User.getConnected(request.getSession());
			request.setAttribute("user", user);
		
		} catch (Exception e) {
			e.printStackTrace();
			
			request.setAttribute("error", e.getMessage());
			
		}
		
		request.getRequestDispatcher("/WEB-INF/game.jsp").include(request, response);
		
	}

}
