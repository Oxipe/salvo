

$.getJSON("/api/games")
    .done(function (data) {
        console.log(data);
        gameInfo.games = data;
    })
    .catch(function(error) {
        console.log("Error: " + error);
    });

$.getJSON("/api/scores")
    .done(function (data) {
        console.log(data);
        scoreInfo.scores = data;
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

var gameInfo = new Vue({
    el: "#gameInfo",
    data: {
        games: []
    }
});

var scoreInfo = new Vue({
    el: "#scoreTable",
    data: {
        scores: []
    }
});