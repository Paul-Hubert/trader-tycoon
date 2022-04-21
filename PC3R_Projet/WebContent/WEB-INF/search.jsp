<%@ page import="data.*" %>
<%@ page import="java.util.ArrayList" %>
{
	<%
	if(request.getAttribute("error") != null) {
	%>
	error: "${requestScope.error}",
	<% } %>
	<%
	User user = (User) request.getAttribute("user");
	%>
	"user": "<%= user.name %>",
	
	"offers": [
		<%
		boolean first = true;
		for(Offer offer : (ArrayList<Offer>) request.getAttribute("offers")) {
		%><%= first ? "" : "," %>
			{
				"id": <%=offer.resource.getID() %>,
				"name": "<%=offer.resource %>",
				"buy": <%=offer.buy %>,
				"price": <%=offer.price %>,
				"quantity": <%=offer.quantity %>
			}
		<%
		first = false;
		}
		%>
	]

}