<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
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
			
			<h2>Login</h2>
			
			<form method="post" action="/login">
				Username:<input type="text" name="user"/><br/><br/>
				Password:<input type="password" name="pass"/><br/><br/>
				<input type="submit" value="login"/>
			</form>
		</div>
	</body>
</html>