if (getCookie("userName")) logIn(getCookie("userName"), getCookie("userPassWord"));

/*
==================================================
    Fetching stuff
==================================================
*/
$.getJSON("/api/games")
    .done(function (data) {
        gameInfo.showAllGames(data);
    })
    .catch(function(error) {
        console.log("Error: " + error);
    });

$.getJSON("/api/scores")
    .done(function (data) {
        gameInfo.scores = data;
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

function logIn(userName, userPassWord) {
    if (!getCookie("userName") && gameInfo.login === true) {
        userName = getValue("inputUserName");
        userPassWord = getValue("inputUserPassWord");
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

    resetValue("inputUserName");
    resetValue("inputUserPassWord");
}

function logOut() {
    fetch("/api/logout", {

        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        }
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
    let userName = getValue("inputUserName");
    let userMail = getValue("inputUserMail");
    let userPassWord = getValue("inputUserPassWord");

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
        });

    resetValue("inputUserName");
    resetValue("inputUserMail");
    resetValue("inputUserPassWord");
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
        .then(data => data.json())
        .then(data => {
            location.href = "/web/game.html?gp=" + data.gamePlayerId;
        })
        .catch(e => {
            alert("Error: " + e);
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
        showAllGames: function (data) {
            
            data.reverse();
            console.log(data[0])
            let i = 0;
            while (this.games.length < 10) {
                if (data[i]) {
                    this.games.push(data[i]);
                } else {
                    break;
                }

                i++;
            }
        },
        showGamesToJoin: function () {
            this.gamesToJoin = [];
            for (let i = 0; i < this.games.length; i++) {
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
        joinGame: function (index) {
            fetch("/api/joinGame", {
                credentials: 'include',
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'gameId=' + this.gamesToJoin[index].id
            })
                .then(function (data) {
                    data.json()
                        .then(function (data) {
                            location.href = "/web/game.html?gp=" + data.gamePlayerId;
                        });
                })
                .catch(e => {
                    alert("Error: " + e);
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

function getValue(target) {
    return document.getElementById(target).value;
}

function resetValue(target) {
    document.getElementById(target).value = "";
}
