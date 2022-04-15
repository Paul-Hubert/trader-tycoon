const refresh_rate = 1000; // milliseconds

function error(e) {
    console.log(e);
}

function addProduction(resource) {
    $.post("/production", {action:"addProduction", resource}, reload, "json").fail(error);
}

function reload(e) {
    
    


}

function refresh() {
    $.getJSON("/update", {}, reload, "json").fail(error);
}

window.addEventListener("load", (e) => {
    $.ajaxSetup({ cache: false });
    setInterval(refresh, refresh_rate);
});