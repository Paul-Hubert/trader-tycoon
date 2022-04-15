<%@ page import="data.*"%>

<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link href="/css/game.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<script src="/js/common.js"></script>
<script src="/js/game.js"></script>
<script src="/js/production.js"></script>
<meta charset="utf-8">
</head>
<body class="bg-dark text-white">

	<div class="container">

		<%
		if (request.getAttribute("error") != null) {
		%>

		<div class="alert alert-danger" role="alert">
			${requestScope.error}</div>

		<%
		}
		%>

		<%
		User user = (User) request.getAttribute("user");
		%>

		<nav class="navbar fixed-top navbar-expand-lg navbar-dark bg-secondary">
			<span class="left navbar-brand">Game</span>
			<span id="username" class="navbar-text"><%= user.name %></span>
			<span class="left navbar-text"><span id="money"><%=Money.format(user.money) %></span></span>
			<form class="right" method="post" action="/">
				<button type="submit" name="action" value="logout"
					class="right btn btn-secondary">Log out</button>
			</form>
		</nav>

		<div class="row m-top">
			<div class="col">
				<ul class="scroll group-list bg-dark">
		
					<%
					for (Resource res : Resource.values()) {
						ResourceProduction rp = user.production.get(res);
					%>
					
					<li id="<%=res%>" class="<%=res%> list-group-item list-group-item-dark bg-dark">
						<div class="card text-white dark mb-3" style="width: 18rem;">
							<div class="card-body">
						  		<h5 class="resource card-title"><%=res %></h5>
						    	<h6 class="card-subtitle1 mb-2 text-muted">Stock : <span class="count"><%=rp.count %></span></h6>
						    	<h6 class="card-subtitle2 mb-2 text-muted">Production : <span class="production"><%=rp.production %></span></h6>
								<div class="form-group">
									<label class="form-check-label">Research investment : </label>
				    				<input type="number" class="research-cost area mb-2" value="<%=rp.research_cost %>">
				    				<label class="form-check-label">$</label>
				    			</div>
						    	<h6 class="card-subtitle1 mb-2 text-muted">Production efficiency : <span class="research"><%=rp.research %></span></h6>
								<button onclick="addProduction(<%=res.getID()%>)" type="button" class="btn btn-secondary">Add production for <span class="production-cost"><%=Money.format(rp.getProductionCost()) %></span></button>
						  	</div>
						</div>
					</li>
					
					<%
					}
					%>
		
				</ul>
			</div>
			
			<div class="col">
				<form>
				
					<div class="form-check form-check-inline mb-2">
						<input class="form-check-input" type="radio" name="inlineRadioOptions" id="buy" value="acheter" required>
				  			<label class="form-check-label">Acheter</label>
					</div>
					
					<div class="form-check form-check-inline mb-2">
				  		<input class="form-check-input" type="radio" name="inlineRadioOptions" id="sell" value="vendre" required>
				  		<label class="form-check-label">Vendre</label>
					</div>
					
					<div class="form-group">
						<select class="custom-select mb-2" required>
					      	<option value="">Select Ressource</option>
					      	
					      	<%
							for (Resource res : Resource.values()) {
								ResourceProduction rp = user.production.get(res);
							%>
							
							<option value="<%=res %>"><%=res %></option>
							
							<%
							}
							%>
							
					    </select>
			     	</div>	
			     	
			     	<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="auto-price" id="auto-price">
					  <label class="form-check-label">Auto-price</label>
					</div>
			     	
			     	<div class="form-group form-check-inline">
				    	<input type="number" class="area mb-2" id="price" placeholder="Price" required>
				    	<label class="form-check-label">$</label>
				  	</div>
				  	
				  	<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="auto-quantity" id="auto-quantity">
					  <label class="form-check-label">Auto-quantity</label>
					</div>
				  	
				  	<div class="form-group">
				    	<input type="number" class="area mb-2" id="quantity" placeholder="Quantity" required>
				    </div>
				  	
				  	<div class="form-group">
				  		<button type="submit" class="btn btn-secondary mb-2">Valider</button>
				  	</div>
				</form>
			</div>
		</div>
		

	</div>
</body>
</html>
