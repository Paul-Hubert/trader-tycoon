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
			
			<h2 class="">Trade</h2>
			
			<div class="row">
			
				<div class="col-md-6">
					<form  method="post" action="/">
						<div class="form-group">
						    <label for="login-user">Username</label>
						    <input type="text" class="form-control" id="login-user" name="user" placeholder="Enter username">
						</div>
						<div class="form-group">
						    <label for="login-pass">Password</label>
						    <input type="password" class="form-control" id="login-pass" name="pass" placeholder="Enter password">
						</div>
		  				<button type="submit" name="action" value="login" class="btn btn-primary">Login</button>
					</form>
				</div>
				
				
				<div class="col-md-6">
					<form method="post" action="/">
						<div class="form-group">
						    <label for="signup-user">Username</label>
						    <input type="text" class="form-control" id="signup-user" name="user" placeholder="Enter username">
						</div>
						<div class="form-group">
						    <label for="signup-pass">Password</label>
						    <input type="password" class="form-control" id="signup-pass" name="pass" placeholder="Enter password">
						</div>
		  				<button type="submit" name="action" value="signup" class="btn btn-primary">Sign up</button>
					</form>
				</div>
			
			</div>
			
		</div>
	</body>
</html>
