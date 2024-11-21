async function checkUser(){

    const userId = document.getElementById("username").value;
    const url = 'http://localhost:8080/API/user?id=' + userId;

    const options = {
        method: "GET",
        headers: {
          "Accept": "application/json"
        }
    };

    const response = await fetch(url, options);
    const playlists = await response.json();


    if(playlists.playlists!=null){
        if (playlists.playlists.length > 0) {
            localStorage.removeItem("playlists");
            localStorage.setItem("playlists", JSON.stringify(playlists));
            changeWindow();
        }
        else {
            localStorage.removeItem("playlists");
            
            if(window.location.href=="http://localhost:8080/"){
                var element = document.getElementById('no_user_index');
                element.classList.remove('no_user_index');
                element.classList.add('nouser_vis');
            }
            else if(window.location.href=="http://localhost:8080/userSearch.html") {
                var element = document.getElementById('no_user_search');
                element.classList.remove('no_user_search');
                element.classList.add('nouser_vis');
            }
            else if(window.location.href=="http://localhost:8080/playlist.html"){
                var element = document.getElementById('no_user_playlist');
                element.classList.remove('no_user_playlist');
                element.classList.add('nouser_vis');
            }
            
        }
    }
    else {
            localStorage.removeItem("playlists");

            if(window.location.href=="http://localhost:8080/"){
                var element = document.getElementById('no_user_index');
                element.classList.remove('no_user_index');
                element.classList.add('nouser_vis');
            }
            else if(window.location.href=="http://localhost:8080/userSearch.html") {
                var element = document.getElementById('no_user_search');
                element.classList.remove('no_user_search');
                element.classList.add('nouser_vis');
            }
            else if(window.location.href=="http://localhost:8080/playlist.html"){
                var element = document.getElementById('no_user_playlist');
                element.classList.remove('no_user_playlist');
                element.classList.add('nouser_vis');
            }
    }  
    
}       


function changeWindow(){
    localStorage.removeItem("id");
    const userId = document.getElementById("username").value;
    localStorage.setItem("id", userId);
    window.location.href = "userSearch.html"; 
}

