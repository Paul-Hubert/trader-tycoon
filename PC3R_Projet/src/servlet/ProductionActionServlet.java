package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Resource;
import data.ResourceProduction;
import data.User;

@WebServlet("/production")
public class ProductionActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ProductionActionServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!User.isConnected(request.getSession())) {
				response.sendRedirect("/");
				return;
			}
			
			User user = User.getConnected(request.getSession());
			var action = request.getParameter("action");
			var resource = request.getParameter("resource");
			Resource r = Resource.get(Integer.parseInt(resource));

			if (action.equals("addProduction")) {
				ResourceProduction rp = user.production.get(r);
				rp.addProduction(user);
			}
			request.getRequestDispatcher("/update").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
