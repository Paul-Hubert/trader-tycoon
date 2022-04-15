<%@ page import="data.*" %>
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
	"money": <%= user.money %>,
	"resources": [
		<%
		boolean first = true;
		for(Resource res : Resource.values()) {
			ResourceProduction rp = user.production.get(res);
		%><%= first ? "" : "," %>
			{
				"id": <%=res.getID() %>,
				"name": "<%=res %>",
				"count": <%=rp.count %>,
				"production": <%=rp.production %>
			}
		<%
		first = false;
		}
		%>
	]

}