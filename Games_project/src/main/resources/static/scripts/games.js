
function showGamesByGame(gameName) {
    var username = document.getElementById('username').value;
    var url = '/games/byGame?username=' + username + '&game=' + gameName;
    window.location.href = url;
}

let dropdown = document.querySelector('.dropdown');
dropdown.onclick = function() {
    dropdown.classList.toggle('active');
}