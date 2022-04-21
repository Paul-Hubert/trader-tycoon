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
		<link href="/css/common.css" rel="stylesheet">
		<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
			crossorigin="anonymous"></script>
		<script src="/js/common.js"></script>
		<script src="/js/game.js"></script>
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
				<span class="left navbar-text"><span id="money" class="currency"><%=Money.format(user.money) %></span></span>
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
							ResourceProduction rp = user.getProduction().get(res);
						%>
						
						<li id="<%=res%>" class="<%=res%> list-group-item list-group-item-dark bg-dark">
							<div class="card text-white dark mb-3">
								<div class="card-body">
							  		<h5 class="resource card-title"><%=res %></h5>
							    	<h6 class="card-subtitle1 mb-2 text-muted">Stock : <span class="count"><%=rp.count %></span></h6>
							    	<h6 class="card-subtitle2 mb-2 text-muted">Production : <span class="production"><%=rp.production %></span></h6>
									<div class="form-group">
										<label class="form-check-label">Research investment : </label>
					    				<input type="text" data-type="currency" step=0.01 class="research-cost area mb-2" name="invest" value="<%=rp.research_cost %>">
					    			</div>
							    	<h6 class="card-subtitle1 mb-2 text-muted">Production efficiency : <span class="research"><%=rp.research %></span></h6>
									<button onclick="addProduction(<%=res.getID()%>)" type="button" class="btn btn-secondary">Add production for <span class="production-cost currency"><%=Money.format(rp.getProductionCost()) %></span></button>
							  	</div>
							</div>
						</li>
						
						<%
						}
						%>
			
					</ul>
				</div>
				
				<div class="col">
	
					<form id="search" class="auto-submit" method="post" action="/action">
					
						<div class="form-check form-check-inline mb-2">
							<input class="form-check-input" type="radio" name="achat_vente" value="achat" required>
					  		<label class="form-check-label">Buy</label>
						</div>
						
						<div class="form-check form-check-inline mb-2">
					  		<input class="form-check-input" type="radio" name="achat_vente" value="vente" required>
					  		<label class="form-check-label">Sell</label>
						</div>
						
						<div class="form-group">
							<select class="custom-select mb-2" name="resource" required>
						      	<option value="">Select Resource</option>
						      	
						      	<%
								for (Resource res : Resource.values()) {
								%>
								
								<option value="<%=res.getID() %>"><%=res %></option>
								
								<%
								}
								%>
								
						    </select>
				     	</div>
				     	
				     	<div class="form-group form-check-inline">
					    	<label class="form-check-label">Target Price</label>
					    	<input type="text" data-type="currency" step=0.01 class="area mb-2" placeholder="$" name="price" required>
					  	</div>
					  	
					  	<div class="form-group">
					    	<label class="form-check-label">Target Quantity</label>
					    	<input type="number" step=1 class="area mb-2" placeholder="" name="quantity" required>
					    </div>
					  	
					  	<div class="form-group row">
					  		<button type="button" onclick="search()" name="action" value="search" class="btn btn-secondary m-2 col-md-2">Search</button>
					  		<button type="button" onclick="publish()" name="action" value="publish" class="btn btn-secondary m-2 col-md-2">Publish</button>
					  	</div>
					</form>
					
					<li id="template" class="list-group-item list-group-item-dark bg-dark" hidden>
						<div class="card text-white dark mb-3">
							<div class="card-body">
						    	<h6 class="card-subtitle1 mb-2 text-muted">Price : <span class="price currency"></span></h6>
						    	<h6 class="card-subtitle1 mb-2 text-muted">Quantity : <span class="quantity currency"></span></h6>
								<button type="button" class="delete btn btn-secondary">Delete</button>
						  	</div>
						</div>
					</li>
					
					<ul id="offer-list" class="scroll group-list bg-dark">
					
						
			
					</ul>
					
				</div>
				
			</div>
			
	
		</div>
	</body>
</html>
