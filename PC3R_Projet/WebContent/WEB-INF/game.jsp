<!DOCTYPE html>
<html>
	<head>
		<title>Login</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
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
			
			<h2>Game</h2>
			
			<form method="post" action="/">
  				<button type="submit" name="action" value="logout" class="btn btn-primary">Log out</button>
			</form>
			
			
		</div>
	</body>
</html>
