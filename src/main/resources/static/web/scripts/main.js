if (getCookie("userName")) {
    let userName = getCookie("userName");
    let userPassWord = getCookie("userPassWord");

    logIn(userName, userPassWord);
}


/*
==================================================
    Fetching stuff
==================================================
*/
$.getJSON("/api/games")
    .done(function (data) {
        console.log(data);
        data.reverse;
        gameInfo.games = data;
    })
    .catch(function(error) {
        console.log("Error: " + error);
    });

$.getJSON("/api/scores")
    .done(function (data) {
        console.log(data);
        gameInfo.scores = data;
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

function logIn(userName, userPassWord) {
    if (!getCookie("userName") && gameInfo.login === true) {
        userName = document.getElementById("inputUserName").value;
        userPassWord = document.getElementById("inputUserPassWord").value;
    }

    fetch("/api/login", {
        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'userName=' + userName + '&userPassWord=' + userPassWord
    })
        .then(r => {
            if (r.status === 200) {
                setCookie("userName", userName, 1);
                setCookie("userPassWord", userPassWord, 1);
                gameInfo.isLoggedIn = true;
                gameInfo.login = false;
                gameInfo.player = userName;
                setTimeout(function () {
                    gameInfo.showGamesToReturn();
                }, 500);
                
            }

            if (r.status === 401) {
                alert("Username or mail address is incorrect.");
            }
        })
        .catch(e => console.log(e));

    document.getElementById("inputUserName").value = "";
    document.getElementById("inputUserPassWord").value = "";

}

function logOut() {
    fetch("/api/logout", {

        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    })
        .then(r => {
            if (r.status === 200) {
                document.cookie = "userName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                document.cookie = "userPassWord=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
                gameInfo.isLoggedIn = false;
                gameInfo.toJoin = false;
            }
        })
        .catch(e => console.log(e));
}

function signUp() {
    var userName = document.getElementById("inputUserName").value;
    var userMail = document.getElementById("inputUserMail").value;
    var userPassWord = document.getElementById("inputUserPassWord").value;

    fetch("/api/players", {
        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'userName=' + userName + '&userMail=' + userMail + '&userPassWord=' + userPassWord

    })
        .then(response => response.json())
        .then(data => {
            if (data.status.statusCodeValue === 201) {
                gameInfo.signup = false;
                logIn(userName, userPassWord);
            } else if (data.status.statusCodeValue === 409) {
                alert("Error: " + data.status.body);
            }
        })
        .catch(e => {
            alert("Error: " + e);
            console.log(e);
        });

    document.getElementById("inputUserName").value = "";
    document.getElementById("inputUserMail").value = "";
    document.getElementById("inputUserPassWord").value = "";
}

function createGame() {

    fetch("/api/currentGame", {
        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
        .then(function (data) {
            data.json()
                .then(function (data) {
                    location.href = "/web/game.html?gp=" + data.gameId;
                });
        })
        .catch(e => {
            alert("Error: " + e);
            console.log(e);
        });
}


/*
==================================================
    Vue
==================================================
*/

var gameInfo = new Vue({
    el: "#header",
    data: {
        isLoggedIn: false,
        login: false,
        signup: false,
        player: "",
        games: [],
        gamesToJoin: [],
        gamesToReturn: [],
        gameId: 0,
        scores: [],
        gameInfo: true,
        toJoin: false
    },
    methods: {
        showGamesToJoin: function () {
            this.gamesToJoin = [];
            for (var i = 0; i < this.games.length; i++) {
                if (this.games[i].gamePlayers.length !== 2) {
                    this.gamesToJoin.push(this.games[i]);
                }
            }
        },
        showGamesToReturn: function () {
            for (let i = 0; i < this.games.length; i++) {
                if (this.games[i].has_finished === false) {
                    for (let j = 0; j < this.games[i].gamePlayers.length; j++) {
                        if (this.games[i].gamePlayers[j].player.user_name === this.player) {
                            let obj = {};
                            obj.game = this.games[i];
                            obj.gamePlayerId = this.games[i].gamePlayers[j].id;
                            this.gamesToReturn.push(obj);
                        }
                    }
                }
            }
        },
        joinGame: function (index, target) {
            console.log(index)
            let gameId;

            target === "join" ? gameId = this.gamesToJoin[index].id : gameId = this.gamesToReturn[index].game.id;

            fetch("/api/joinGame", {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'gameId=' + gameId
            })
                .then(function (data) {
                    console.log(data)
                    data.json()
                        .then(function (data) {
                            console.log(data)
                            location.href = "/web/game.html?gp=" + data.gamePlayerId;
                        })
                })
                .catch(e => {
                    alert("Error: " + e);
                    console.log(e);
                });
        },
        returnToGame: function (index) {
            location.href = "/web/game.html?gp=" + this.gamesToReturn[index].gamePlayerId;
        }
    }
});

/*
==================================================
    Functions
==================================================
*/

function setCookie(name, value, days) {
    var date = new Date();
    date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
    var expires = "expires=" + date.toUTCString();
    document.cookie = name + "=" + value + ";" + expires + ";path=/";
}

function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) === 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
