//create an empty grid with numbers across the top and letters on the left side, per the email.
//grid size 11x11, playing field will be 10x10

var url = window.location.href;
var id = retrieveGameplayId(url);

fetchGameplayData();

function fetchGameplayData() {
    $.getJSON("/api/gp/" + id.gp)
        .done(function (data) {
            console.log(data);
            playerGrid.playerData = data;
            playerGrid.fillInterface();
            playerGrid.placeShips();
        })
        .catch(function (error) {
            console.log("Error: " + error);
        });
}

var playerGrid = new Vue({
    el: "#gameInterface",
    created: function () {
        createGrid("player");
    },
    data: {
        playerData: [],
        player: "",
        opponent: ""
    },
    methods: {
        placeShips: function () {
            var ships = this.playerData.ships;
            for (var i = 0; i < ships.length; i++) {
                console.log(ships)
                console.log(ships.length);
                for (var j = 0; j < ships[i].locations.length; j++) {
                    var cell = document.getElementById(ships[i].locations[j]);

                    cell.setAttribute("class", "grid_cell cell_with_ship");

                }
            }
        },
        fillInterface: function () {
            this.player = this.playerData.gamePlayers[0].player.user_name;
            this.opponent = this.playerData.gamePlayers[1].player.user_name;
        }
    }
});

function createGrid(competitor) {
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

            if (i > 0 && j > 0) cell.setAttribute("id", arrayOfChars[i] + arrayOfNumbers[j]);
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