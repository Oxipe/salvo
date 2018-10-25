
/*
==================================================
    Fetching stuff
==================================================
*/
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
        gameInfo.scores = data;
    })
    .catch(function (error) {
        console.log("Error: " + error);
    });

function logIn() {
    var userName = document.getElementById("inputUserName").value;
    var userPassWord = document.getElementById("inputUserPassWord").value;



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
                headerInfo.isLoggedIn = true;
                headerInfo.login = false;
                headerInfo.player = userName;
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
            if (r.status === 200)
                headerInfo.isLoggedIn = false;
        })

        .catch(e => console.log(e));
}

function signUp() {
    var userName = document.getElementById("inputUserName").value;
    var userMail = document.getElementById("inputUserMail").value;
    var userPassWord = document.getElementById("inputUserPassWord").value;

    fetch("/api/login", {
        credentials: 'include',
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/x-www-form-urlencoded'
        },
        body: 'userName=' + userName + '&userMail=' + userMail + '&password=' + userPassWord,

    })

        .then(r => console.log(r))

        .catch(e => console.log(e));

    document.getElementById("inputUserName").value = "";
    document.getElementById("inputUserMail").value = "";
    document.getElementById("inputUserPassWord").value = "";
}

/*
==================================================
    Vue
==================================================
*/

var headerInfo = new Vue({
    el: "#header",
    data: {
        isLoggedIn: false,
        login: false,
        signup: false,
        player: ""
    },
    method: {

    }
});

var gameInfo = new Vue({
    el: "#gameInfo",
    data: {
        games: [],
        scores: [],
        gameInfo: true
    }
});