var url = window.location.href;
var id = retrieveGameplayId(url);

fetchGameplayData();

function fetchGameplayData() {
    $.getJSON("/api/gp/" + id.gp)
        .done(function (data) {
            console.log(data);
            gameOverview.playerData = data;
            gameOverview.fillInterface();
            gameOverview.placeShips();
        })
        .catch(function (error) {
            console.log("Error: " + error);
        });
}

var gameOverview = new Vue({
    el: "#gameInterface",
    created: function () {
        //Temp variable, will be removed when things go automatic
        this.hasStarted = false;

        createEmptyGrid("player");
        createEmptyGrid("opponent");
    },
    data: {
        playerData: [],
        player: "",
        opponent: "",
        //Temp variable, will be removed when things go automatic
        hasStarted: false,
        //Temp variable, will be removed when things go automatic
        currentTurn: 1
    },
    methods: {
        fillInterface: function () {
            this.player = this.playerData.gamePlayers[0].player;
            this.opponent = this.playerData.gamePlayers[1].player;
        },
        placeShips: function () {
            var ships = this.playerData.ships;
            for (var i = 0; i < ships.length; i++) {
                for (var j = 0; j < ships[i].locations.length; j++) {
                    var cell = document.getElementById("player" + ships[i].locations[j]);

                    cell.setAttribute("class", "grid_cell cell_with_ship");
                }
            }
        },
        placeSalvos: function () {
            var salvos = this.playerData.salvoes;

            for (var i = 0; i < salvos.length; i++) {
                if (salvos[i].turn === this.currentTurn) {
                    for (var j = 0; j < salvos[i].locations.length; j++) {
                        var cellPlayer = document.getElementById("player" + salvos[i].locations[j]);
                        var cellOpponent = document.getElementById("opponent" + salvos[i].locations[j]);

                        if (cellPlayer.className === "grid_cell cell_with_ship") {
                            cellPlayer.setAttribute("class", "grid_cell cell_with_whip cell_hit");
                            cellOpponent.setAttribute("class", "grid_cell cell_hit");
                        } else {
                            cellPlayer.setAttribute("class", "grid_cell cell_miss");
                            cellOpponent.setAttribute("class", "grid_cell cell_miss");
                        }
                    }
                }
            }
        },
        //Temp function, will be removed when things go automatic
        start: function () {
            this.placeSalvos();
            this.hasStarted = true;
        },
        //Temp function, will be removed when things go automatic
        increaseTurn: function () {
            if (this.currentTurn < 3) {
                this.currentTurn++;
                this.placeSalvos();
            }
        }
    }
});

function createEmptyGrid(competitor) {
    var table = document.getElementById(competitor);
    var arrayOfChars = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
    var arrayOfNumbers = ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];

    for (var i = 0; i < 11; i++) {
        var row = document.createElement("tr");

        for (var j = 0; j < 11; j++) {
            var cell = document.createElement("td");
            cell.setAttribute("class", "grid_cell");

            if (i === 0) {
                cell.innerHTML = arrayOfChars[j];
            }

            if (j === 0) {
                cell.innerHTML = arrayOfNumbers[i];
            }

            if (i > 0 && j > 0) cell.setAttribute("id", competitor + arrayOfChars[j] + arrayOfNumbers[i]);
            row.appendChild(cell);
        }

        table.appendChild(row);
    }
}

function retrieveGameplayId(search) {
    var obj = {};
    var reg = /(?:[?&]([^?&#=]+)(?:=([^&#]*))?)(?:#.*)?/g;

    search.replace(reg, function (match, param, val) {
        obj[decodeURIComponent(param)] = val === undefined ? "" : decodeURIComponent(val);
    });

    return obj;
}