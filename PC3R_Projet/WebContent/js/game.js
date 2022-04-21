const refresh_rate = 1000; // milliseconds

function error(e) {
    console.log(e);
}

function refresh() {
    $.getJSON("/update", {}, reload, "json").fail(error);
}

function addProduction(resource) {
    $.post("/action", {"action":"addProduction", resource}, reload, "json").fail(error);
}

function reload(data) {
    
    $("#money").html(money(data.money));

    for(let i in data.resources) {
    	let res = data.resources[i];
        $("#" + res.name + " .count").html(res.count);
        $("#" + res.name + " .production").html(res.production);
        $("#" + res.name + " .production-cost").html(money(res.production_cost));
        $("#" + res.name + " .research").html(res.research);
        $("#" + res.name + " .research-cost").attr('value', res.research_cost);
    }

	updateCurrencySpy();

}



function search() {
	let resource = $("#search [name='resource']").val();
	let buy = $("#search [name='achat_vente']").val();
	let price = $("#search [name='price']").val();
	let quantity = $("#search [name='quantity']").val();
	
	console.log(resource, buy, price, quantity); 
	
	$.post("/action", {"action":"search", resource, buy, price, quantity}, searchResults, "json").fail(error);
}

function publish() {
	let resource = $("#search [name='resource']").val();
	let buy = $("#search [name='achat_vente']").val();
	let price = $("#search [name='price']").val();
	let quantity = $("#search [name='quantity']").val();
	$.post("/action", {"action":"publish", resource, buy, price, quantity}, searchResults, "json").fail(error);
}


function deleteOffer(e) {
	console.log("delete", e);
}

function searchResults(data) {
	
	console.log(data);
	
	$("#offer-list").empty();
	
	let template = $("#template");
	
	for(let offer in data.offers) {
		let offer_card = template.clone();
		offer_card.appendTo("#offer-list");
		
		offer_card.attr("id", "modify");
		
		
		$("#modify .price").html(money(offer.price));
		
		console.log($("#modify .price").html());
		$("#modify .quantity").html(offer.quantity);
		
		$("#modify .delete").on("click", deleteOffer);
		
		//offer_card.removeAttr("id");
		offer_card.removeAttr("hidden");
		
	}
	
	updateCurrencySpy();
	
}



window.addEventListener("load", (e) => {
	updateCurrencySpy();
    $.ajaxSetup({ cache: false });
    setInterval(refresh, refresh_rate);
});

/*
$('.auto-submit').submit(function(e){
    e.preventDefault();
    
    $.ajax({
        type: 'POST',
        cache: false,
        url: $(this).attr('action'),
        data: 'action-type='+$(this).attr('action-type')+'&'+$(this).serialize(), 
        success: searchResults
    }).fail(error);
    
    const form = e.target;
	let data = new FormData(form);
	data.set("action-type", "publish");
    fetch(form.action, {
        method: form.method,
        body: data
    }).then(searchResults).catch(error);
});
*/
    
