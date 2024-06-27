
function saveGameResult(result, time, moves) {
    // Отправляем запрос на сервер для сохранения результата игры
    fetch('/memory-game', {
        method: 'POST',
        body: `gameResult=${result}&gameTime=${time}&gameMoves=${moves}`,
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
    result = document.getElementById("result"),
    controls = document.querySelector(".controls-container"),
    startButton = document.getElementById("start");

const emojis = ["😍","😍","❤","❤","😎","😎","😂","😂","😁","😁","😜","😜","🤢","🤢","😆","😆"];

let shuf_emojis = emojis.sort(() => (Math.random() > .5) ? 2 : -1); // использует метод sort с функцией сравнения, которая возвращает 2 или -1 в зависимости от случайного значения, чтобы переупорядочить элементы в массиве

let openedPairs = 0;
let movesCount = 0;
let totalPairs = emojis.length / 2;
let startTime = new Date().getTime();
let timeLimit = 60;
let elapsedTime;

updateInfo();

// Timer function
let timer = setInterval(function() {
    let currentTime = new Date().getTime();
    elapsedTime = (currentTime - startTime) / 1000;
    let remainingTime = timeLimit - elapsedTime;

    if (remainingTime <= 0) {
        clearInterval(timer);
        result.innerHTML = `<h2>Вы проиграли!</h2>
                            <h4>Кол-во открытых карточек: ${movesCount}</h4>`;
        saveGameResult("lose", 60, movesCount);
        stopGame();
    }

    document.getElementById("time").textContent = remainingTime.toFixed(0);
}, 1000);

for (let i = 0; i < emojis.length; i++) {
    let box = document.createElement('div');
    box.className = 'item';
    box.innerHTML = shuf_emojis[i];
    box.onclick = function() {
        if (!this.classList.contains('boxOpen') && !this.classList.contains('boxMatch')) {
            this.classList.add('boxOpen');

            setTimeout(function() {
                let openBoxes = document.querySelectorAll('.boxOpen');
                if (openBoxes.length === 2) {
                    movesCount++;
                    updateInfo();

                    if (openBoxes[0].innerHTML === openBoxes[1].innerHTML) {
                        openBoxes.forEach(box => {
                            box.classList.add('boxMatch');
                            box.classList.remove('boxOpen');
                        });
                        openedPairs++;

                        if (openedPairs === totalPairs) {
                            clearInterval(timer);
                            result.innerHTML = `<h2>Вы выиграли!</h2>
                            <h4>Кол-во открытых карточек: ${movesCount}</h4>`;
                            saveGameResult("win", elapsedTime.toFixed(0),  movesCount);
                            stopGame();
                        }
                    } else {
                        openBoxes.forEach(box => {
                            box.classList.remove('boxOpen');
                        });
                    }
                }
            }, 500);
        }
    };
    document.querySelector('.game').appendChild(box);
}

function updateInfo() {
    document.getElementById("moves-count").textContent = movesCount;
}

function stopGame() {
    controls.classList.remove("hide");
    startButton.classList.remove("hide");
    clearInterval(timer);
}

startButton.onclick = () => {
    window.location.reload();
}