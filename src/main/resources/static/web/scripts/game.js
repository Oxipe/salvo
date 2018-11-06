var url = window.location.href;
var id = retrieveGameplayId(url);
var arrayOfChars = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"];
var arrayOfNumbers = ["", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"];

/*
 ========================================================
    Fetching stuff
 ========================================================
 */

$.getJSON("/api/gp/" + id.gp)
    .done(function (data) {
        console.log(data);
        gameOverview.playerData = data;
        gameOverview.fillInterface();
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

$.getJSON('/api/ships')
    .done(function (data) {
        gameOverview.allShips = data;
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

function submitPlacement() {
    if (gameOverview.placedShips !== 5) {
        alert("Place all ships before submitting.");
    } else {
        fetch("/api/games/players/" + id.gp + "/ships", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 
                    "details=" + gameOverview.allShips[0].type + "," + gameOverview.allShips[0].location +
                    "&details=" + gameOverview.allShips[1].type + "," + gameOverview.allShips[1].location +
                    "&details=" + gameOverview.allShips[2].type + "," + gameOverview.allShips[2].location +
                    "&details=" + gameOverview.allShips[3].type + "," + gameOverview.allShips[3].location +
                    "&details=" + gameOverview.allShips[4].type + "," + gameOverview.allShips[4].location
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                gameOverview.gameStatus = "Battle";
            })
            .catch(e => {
                alert("Error: " + e);
            });
    }
}

function submitSalvos() {
    if (gameOverview.arrayOfSalvoLocations.length !== 5) {
        alert("Place all salvos before submitting.");
    } else {
        fetch("/api/games/players/" + id.gp + "/salvos", {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body:
                "details=" + gameOverview.arrayOfSalvoLocations[0] +
                "&details=" + gameOverview.arrayOfSalvoLocations[1] +
                "&details=" + gameOverview.arrayOfSalvoLocations[2] +
                "&details=" + gameOverview.arrayOfSalvoLocations[3] +
                "&details=" + gameOverview.arrayOfSalvoLocations[4]
        })
            .then(response => response.json())
            .then(data => {
                console.log(data)
                gameOverview.arrayOfSalvoLocations = [];
                gameOverview.placedSalvos = 0;
            })
            .catch(e => {
                alert("Error: " + e);
            });
    }
}

/*
 ========================================================
    Vue
 ========================================================
 */

var gameOverview = new Vue({
    el: "#gameInterface",
    created: function () {
        createEmptyGrid("player");
        createEmptyGrid("opponent");
    },
    data: {
        playerData: [],
        allShips: [],
        arrayOfShipLocations: [],
        arrayOfSalvoLocations: [],
        player: "",
        creator: "",
        opponent: "",
        gameStatus: "Place Ships", 
        selectedShip: {},
        horizontal: true,
        placedShips: 0,
        placedSalvos: 0
    },
    methods: {
        fillInterface: function () {
            this.player = getCookie("userName");
            this.creator = this.playerData.creator;
            this.opponent = this.playerData.opponent;
        },
        placeShip: function (event) {
            if (this.gameStatus === "Battle") {
                alert("You dont want to fire at you own ships.");
            } else if (Object.keys(this.selectedShip).length === 0) {
                alert("No ship selected");

            } else if (this.placedShips < 5 && !this.selectedShip.isPlaced) {

                var cellId = event.target.id.replace("player", "");
                this.calculatePositions(cellId);
            } else {
                alert("You already place the " + this.selectedShip.type);
            }
        },
        calculatePositions: function (cellId) {
            cellId.slice();
            let shipLength = this.selectedShip.length;
            let currentShipLocation = [];
            let char = cellId[0];
            let number;
            let cell;
            let div;

            cellId.length > 2 ? number = cellId[1] + cellId[2] : number = cellId[1];

            console.log(this.allShips)


            if (this.horizontal) {
                let index;

                for (var i = 0; i < arrayOfChars.length; i++) {
                    if (arrayOfChars[i] === char) index = i;
                }

                try {
                    if (index + shipLength <= 11) {
                        let charToCheck = arrayOfChars[index];
                        let indexToCheck = index;
                        for (var j = 0; j < shipLength; j++) {
                            for (var k = 0; k < this.arrayOfShipLocations.length; k++) {
                                if (this.arrayOfShipLocations[k] === charToCheck + number) {
                                    throw "Selected ship overlaps an other ship, please try again.";
                                }
                            }
                            charToCheck = arrayOfChars[indexToCheck++];
                        }



                        for (var l = 0; l < shipLength; l++) {
                            this.arrayOfShipLocations.push(arrayOfChars[index] + number);
                            currentShipLocation.push(arrayOfChars[index] + number);

                            cell = getElement("player" + arrayOfChars[index] + number);
                            div = createElement("div");
                            div.setAttribute("class", "cell_with_ship");
                            cell.appendChild(div);
                            char = arrayOfChars[index];
                            index++;
                        }
                        this.finalizePlacement(currentShipLocation);
                        currentShipLocation = [];
                    } else {
                        throw "The " + this.selectedShip.type + " is illigally placed outside the grid, please try again.";
                    }
                } catch (error) {
                    alert(error);
                }
            } else {
                try {
                    if (parseInt(number) + shipLength <= 11) {
                        let numberToCheck = number;
                        for (var i = 0; i < shipLength; i++) {
                            for (var j = 0; j < this.arrayOfShipLocations.length; j++) {
                                if (this.arrayOfShipLocations[j] === char + numberToCheck) {
                                    throw "Selected ship overlaps an other ship, please try again.";
                                }
                            }
                            numberToCheck++
                        }



                        for (var k = 0; k < shipLength; k++) {


                            this.arrayOfShipLocations.push(char + number);
                            currentShipLocation.push(char + number);

                            cell = getElement("player" + char + number);
                            div = createElement("div");
                            div.setAttribute("class", "cell_with_ship");
                            cell.appendChild(div);
                            number++;
                        }
                        this.finalizePlacement(currentShipLocation);
                        currentShipLocation = [];
                        
                    } else {
                        throw "The " + this.selectedShip.type + " is illigally placed outside the grid, please try again.";
                    }

                } catch (error) {
                    alert(error);
                }
            }
        },
        finalizePlacement: function (locations) {
            for (var i = 0; i < this.allShips.length; i++) {
                if (this.allShips[i] === this.selectedShip) {
                    this.allShips[i].location = locations;
                }
            }            
            this.selectedShip.isPlaced = true;
            this.selectedShip = {};
            this.placedShips++;
        },
        placeSalvos: function (event) {
            console.log(event)
            let cell;
            let cellId;
            if (event.target.nodeName === "TD") {
                cell = getElement(event.target.id)
                cellId = event.target.id.replace("opponent", "");
            } else {
                cell = getElement(event.target.parentNode.id);
                cellId = event.target.parentNode.id.replace("opponent", "");
            }

            if (cell.hasChildNodes()) {
                
                if (this.arrayOfSalvoLocations.indexOf(cellId) !== -1) {
                    cell.innerHTML = "";
                    this.placedSalvos--;
                    var pos = this.arrayOfSalvoLocations.indexOf(cellId);

                    this.arrayOfSalvoLocations.splice(this.arrayOfSalvoLocations.indexOf(cellId), 1);
                } else {
                    alert("Can not remove previous shot salvo.");
                }

            } else {
                if (this.placedSalvos !== 5) {
                    var div = createElement("div");
                    div.setAttribute("class", "cell_salvo");
                    cell.appendChild(div);
                    this.placedSalvos++;
                    this.arrayOfSalvoLocations.push(event.target.id.replace("opponent", ""));
                } else {
                    alert("You already place to maximum amount of salvos.");
                }
            }
            console.log(this.arrayOfSalvoLocations)


        },
        selectShip: function (ship) {
            for (var i = 0; i < this.allShips.length; i++) {
                if (this.allShips[i].type === ship) this.selectedShip = this.allShips[i];
            }
        },
        changePointer: function (ship) {
            ship = document.getElementById(ship);
            ship.style.cursor = "pointer";
            ship.style.color = "red";
        },
        restoreColor: function (ship) {
            ship = getElement(ship);
            ship.style.color = "black";
        }
    }
});

/*
 ========================================================
    Functions
 ========================================================
 */

function createEmptyGrid(competitor) {
    var table = getElement(competitor);
    
    for (var i = 0; i < 11; i++) {
        var row = createElement("tr");

        for (var j = 0; j < 11; j++) {
            var cell = createElement("td");
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

document.addEventListener('keypress', function (e) {
    if (e.key === "r" && gameOverview.selectedShip !== "") {
        var ship = getElement("currentShip");

        if (gameOverview.horizontal) {
            ship.style.transform = "rotate(90deg)";
            gameOverview.horizontal = false;
        } else {
            ship.style.transform = "rotate(0deg)";
            gameOverview.horizontal = true;
        }
    }
});

function getElement(target) {
    return document.getElementById(target);
}

function createElement(target) {
    return document.createElement(target);
}

function clearGrid() {
    for (var i = 0; i < gameOverview.arrayOfShipLocations.length; i++) {
        getElement("player" + gameOverview.arrayOfShipLocations[i]).innerHTML = "";
    }

    for (var j = 0; j < gameOverview.allShips.length; j++) {
        gameOverview.allShips[j].isPlaced = false;
    }

    gameOverview.arrayOfShipLocations = [];
    gameOverview.selectedShip = {};
    gameOverview.placedShips = 0;
}