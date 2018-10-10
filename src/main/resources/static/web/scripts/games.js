

$.getJSON("/api/game_id")
    .done(function (data) {
        gameInfo.games = data;
        console.log(data);
    })
    .catch(function(error) {
        console.log("Error: " + error);
    });



function myFunction(data) {
    console.log(data);
}

var gameInfo = new Vue({
    el: "#gameInfo",
    data: {
        games: []
    }
});