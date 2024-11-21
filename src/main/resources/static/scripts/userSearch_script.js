function displayPlaylists(playlistArray){

    document.querySelector("#usernameDisplay").innerHTML = localStorage.getItem("id") + "'s playlists:";

    var playlistContainer = document.getElementById("playlistContainer");

    playlistArray.playlists.forEach(function(playlist){
    var card = document.createElement("div");
    card.className = "col";
    card.innerHTML = `
    <div class="card text-center" style="width: 15rem; margin: auto; margin-top: 20px;">
        <img src="${playlist.image}" class="card-img-top" style="height: 14rem; object-fit: cover; o alt="...">
        <div class="card-body">
            <h5 class="card-title";">${playlist.name}</h5>
            <p class="card-text"></p>
            <a href="http://localhost:8080/playlist.html" class="btn btn-primary" style="margin-left: 0;">Show playlist</a>
        </div>
    </div>
`;
    var button = card.querySelector('.btn-primary');
    button.addEventListener("click", function() {
    localStorage.removeItem("playlistId");
    localStorage.removeItem("playlistName");
    
    localStorage.setItem("playlistId", playlist.id);
    localStorage.setItem("playlistName", playlist.name);
});
    playlistContainer.appendChild(card);
   });


}

displayPlaylists(JSON.parse(localStorage.getItem("playlists")));
