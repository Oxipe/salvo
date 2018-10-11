

$.getJSON("/api/games")
    .done(function (data) {
        gameInfo.games = data;
        console.log(data);
    })
    .catch(function(error) {
        console.log("Error: " + error);
    });

var gameInfo = new Vue({
    el: "#gameInfo",
    data: {
        games: []
    }
});