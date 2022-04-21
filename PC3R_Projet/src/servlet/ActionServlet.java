package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Money;
import data.Offer;
import data.Resource;
import data.ResourceProduction;
import data.User;
import exception.NotEnoughMoneyException;

@WebServlet("/action")
public class ActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ActionServlet() {
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
				
				ResourceProduction rp = user.getProduction().get(r);
				
				try {
					user.pay(rp.getProductionCost());
					rp.addProduction();
				} catch(NotEnoughMoneyException e) {}
				
			} else if(action.equals("publish")) {
				
				boolean buy = Boolean.parseBoolean(request.getParameter("achat_vente"));
				long price = Money.parse(request.getParameter("price"));
				long quantity = Long.parseLong(request.getParameter("quantity"));
				
				Offer offer = new Offer(user.id, r, buy, price, quantity);
				
				user.getOffers().insert(offer);
				
				var offers = user.getOffers().search(offer);
				
				request.setAttribute("offers", offers);
				
				request.getRequestDispatcher("/search").forward(request, response);
				return;
				
			}  else if(action.equals("search")) {
				
				boolean buy = Boolean.parseBoolean(request.getParameter("achat_vente"));
				long price = Money.parse(request.getParameter("price"));
				long quantity = Long.parseLong(request.getParameter("quantity"));
				
				Offer offer = new Offer(user.id, r, buy, price, quantity);
				
				var offers = user.getOffers().search(offer);
				
				request.setAttribute("offers", offers);
				
				request.getRequestDispatcher("/search").forward(request, response);
				return;
				
			}
			
			request.getRequestDispatcher("/update").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
