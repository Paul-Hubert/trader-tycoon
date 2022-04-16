const refresh_rate = 1000; // milliseconds

function error(e) {
    console.log(e);
}

function addProduction(resource) {
    $.post("/action", {action:"addProduction", resource}, reload, "json").fail(error);
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

function refresh() {
    $.getJSON("/update", {}, reload, "json").fail(error);
}

window.addEventListener("load", (e) => {
	updateCurrencySpy();
    $.ajaxSetup({ cache: false });
    setInterval(refresh, refresh_rate);
});