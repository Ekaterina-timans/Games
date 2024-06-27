
function saveGameResult(result) {
    // Отправляем запрос на сервер для сохранения результата игры
    fetch('/tic-tac-toe', {
        method: 'POST',
        body: `gameResult=${result}`,
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    }).then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.text();
    }).then(data => {
        console.log('Game result saved successfully:', data);
    }).catch(error => {
        console.error('Error saving game result:', error);
    });
}

const
    selectBox = document.querySelector(".select-box"),
    selectXBtn = selectBox.querySelector(".options .playerX"),
    selectOBtn = selectBox.querySelector(".options .playerO"),
    playBoard = document.querySelector(".play-board"),
    allBox = document.querySelectorAll("section span"),
    players = document.querySelector(".players"),
    resultBox = document.querySelector(".result-box"),
    wonText = resultBox.querySelector(".won-text"),
    replayBtn = resultBox.querySelector("button");

window.onload = () => {
    for (let i = 0; i < allBox.length; i++) {
        allBox[i].setAttribute("onclick", "clickedBox(this)");
    }
}

selectXBtn.onclick = () => {
    selectBox.classList.add("hide");
    playBoard.classList.add("show");
}

selectOBtn.onclick = () => {
    selectBox.classList.add("hide");
    playBoard.classList.add("show");
    players.setAttribute("class", "players active player");
}

let playerXIcon = "fas fa-times";
let playerOIcon = "far fa-circle";
let playerSign = "X";
let runBot = true;
let userTurn = "X";

// user click function
function clickedBox(element) {

    if (players.classList.contains("player")) {
        userTurn = "O";
        playerSign = "O";
        element.innerHTML = `<i class="${playerOIcon}"></i>`;
        players.classList.remove("active");
        element.setAttribute("id", playerSign);
    }
    else {
        userTurn = "X";
        element.innerHTML = `<i class="${playerXIcon}"></i>`;
        element.setAttribute("id", playerSign);
        players.classList.add("active");
    }
    selectWinner();
    element.style.pointerEvents = "none";
    playBoard.style.pointerEvents = "none";

    let randomDelayTime = ((Math.random() * 1000) + 200).toFixed();
    console.log(randomDelayTime);
    setTimeout(() => {
        bot(runBot);
    }, randomDelayTime);
}

// bot click function
function bot() {
    let array = [];
    if (runBot) {
        playerSign = "O";
        for (let i = 0; i < allBox.length; i++) {
            if (allBox[i].childElementCount == 0) {
                array.push(i);
            }
        }
        let randomBox = array[Math.floor(Math.random() * array.length)];
        if (array.length > 0) {
            if (players.classList.contains("player")) {
                playerSign = "X";
                allBox[randomBox].innerHTML = `<i class="${playerXIcon}"></i>`;
                allBox[randomBox].setAttribute("id", playerSign);
                players.classList.add("active");
            } else {
                allBox[randomBox].innerHTML = `<i class="${playerOIcon}"></i>`;
                players.classList.remove("active");
                allBox[randomBox].setAttribute("id", playerSign);
            }
            selectWinner();
        }
        allBox[randomBox].style.pointerEvents = "none";
        playBoard.style.pointerEvents = "auto";
        playerSign = "X";
    }
}

function getClass(classname) {
    return document.querySelector(".box" + classname).id;
}

function checkClass(val1, val2, val3, sign) {
    if (getClass(val1) == sign && getClass(val2) == sign && getClass(val3) == sign) {
        return true;
    }
}

function selectWinner() {
    if (checkClass(1, 2, 3, playerSign) || checkClass(4, 5, 6, playerSign) || checkClass(7, 8, 9, playerSign) || checkClass(1, 4, 7, playerSign) || checkClass(2, 5, 8, playerSign) || checkClass(3, 6, 9, playerSign) || checkClass(1, 5, 9, playerSign) || checkClass(3, 5, 7, playerSign)) {
        runBot = false;
        bot(runBot);

        setTimeout(() => {
            resultBox.classList.add("show");
            playBoard.classList.remove("show");
        }, 700);


        wonText.innerHTML = `Игрок <p>${playerSign}</p> выиграл игру!`;
        if (userTurn == playerSign) {
            saveGameResult('win');
        } else {
            saveGameResult('lose');
        }
    }
    else {
        if (getClass(1) != "" && getClass(2) != "" && getClass(3) != "" && getClass(4) != "" && getClass(5) != "" && getClass(6) != "" && getClass(7) != "" && getClass(8) != "" && getClass(9) != "") {
            runBot = false;
            bot(runBot);

            setTimeout(() => {
                resultBox.classList.add("show");
                playBoard.classList.remove("show");
            }, 700);

            wonText.textContent = `Match has been drawn!`;
            saveGameResult('tie');
        }
    }
}

replayBtn.onclick = () => {
    window.location.reload();
}