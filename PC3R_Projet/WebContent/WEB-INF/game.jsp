<%@ page import="data.*" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<link href="/css/game.css" rel="stylesheet">
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
		<script src="/js/production.js"></script>
		<script src="/js/game.js"></script>
		<script src="/js/common.js"></script>
		<meta charset="utf-8">
	</head>
	<body>
		<div class="container">
			
			<%
			if(request.getAttribute("error") != null) {
			%>

			<div class="alert alert-danger" role="alert">
				${requestScope.error}
			</div>
			
			<%
			}
			%>
			
			<%
			User user = (User) request.getAttribute("user");	
			%>
			
			<h2>Game</h2>
			
			<h4>
				<%= user.name %>
			</h4>
			
			<form method="post" action="/">
  				<button type="submit" name="action" value="logout" class="btn btn-primary">Log out</button>
			</form>
			
			<%
			for(Resource res : Resource.values()) {
				ResourceProduction rp = user.production.get(res);
			%>
			
			<div>
				<%= rp.count %> <%= res %>
				
			</div>
			
			<%	
			}
			%>
			
			
		</div>
	</body>
</html>
