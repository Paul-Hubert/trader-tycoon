const refresh_rate = 1000; // milliseconds

function error(e) {
    console.log(e);
}

function addProduction(resource) {
    $.post("/production", {action:"addProduction", resource}, reload, "json").fail(error);
}

function reload(data) {
    console.log(data);

    $("#money").html(data.money);

    for(let i in data.resources) {
    	let res = data.resources[i];
        $("#" + res.name + " .count").html(res.count);
        $("#" + res.name + " .production").html(res.production);
    }

}

function refresh() {
    $.getJSON("/update", {}, reload, "json").fail(error);
}

window.addEventListener("load", (e) => {
    $.ajaxSetup({ cache: false });
    setInterval(refresh, refresh_rate);
});