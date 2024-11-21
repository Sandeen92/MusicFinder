
async function embedFirstVideo(artistName, title) {
  try {
    const searchQuery = artistName + " " + title;
    const videoUrl = 'http://localhost:8080/API/video?query=' + encodeURIComponent(searchQuery);
    const videoID = await fetch(videoUrl).then(response => response.text());

    const videoPlayer = document.getElementById("music_video");
    videoPlayer.src = "https://www.youtube.com/embed/" + videoID + "?autoplay=0"; // Embed it paused

  } catch (error) {
    console.error("Error fetching video information:", error);
  }
}

async function changeVideo(artistName, title) {
  try {
    const searchQuery = artistName + " " + title;
    const videoUrl = 'http://localhost:8080/API/video?query=' + encodeURIComponent(searchQuery);
    const videoID = await fetch(videoUrl).then(response => response.text());

    const videoPlayer = document.getElementById("music_video");
    videoPlayer.src = "https://www.youtube.com/embed/" + videoID + "?autoplay=1";

  } catch (error) {
    console.error("Error fetching video information:", error);
  }
}

async function populatePlaylist(playlistId, shownPlaylistname){

    const url = 'http://localhost:8080/API/playlist?id=' + playlistId;

    const options = {
        method: "GET",
        headers: {
          "Accept": "application/json"
        }
    };
    
    const response = await fetch(url, options);
    const playlist = await response.json();


    var playlistsName = document.getElementById("shownPlaylistName");
    playlistsName.innerHTML = "Playlist: " + shownPlaylistname;


    var itemContainer = document.getElementById("trackList");

    playlist.tracks.forEach(function(itemname){
        var link = document.createElement("a");
        link.href = "#";
        link.title = itemname.artistName + " - " + itemname.title;
        link.className = "list-group-item list-group-item-action m-0 pb-1 text-truncate";
        link.id = "song_list"
        link.textContent = itemname.artistName + " - " + itemname.title;
        link.onclick = function() {
            changeVideo(itemname.artistName, itemname.title);
            populateTicketInfo(itemname.artistName);
        };
        if (itemname.artistName != "" && itemname.title != ""){
        itemContainer.appendChild(link);
      }
    });

    if (playlist.tracks.length > 0) {
      // Get details of the first track
      const firstTrack = playlist.tracks[0];
      
      // Embed the first video in the playlist
      embedFirstVideo(firstTrack.artistName, firstTrack.title);
  }


}
populatePlaylist(localStorage.getItem("playlistId"), localStorage.getItem("playlistName"));

async function populateTicketInfo(artistName) {
  const newName = artistName;
  

  try {
    const concertUrl = 'http://localhost:8080/API/tickets?keyWord=' + newName;
    const response = await fetch(concertUrl);
    if (!response.ok) {
     
      var imageLink = document.getElementById("ticketmaster_image");
      imageLink.src = "images/microphone-2574511_1280.jpg";

      var artis = document.getElementById('artistName');
      artis.textContent = "Bummer, no tickets found!";

      var dateConcert = document.getElementById('date');
      dateConcert.textContent = "Please select another artist.";

      var timeConcert = document.getElementById('time');
      timeConcert.textContent = "";

      var venues = document.getElementById('venue');
        venues.textContent = "";

      var ticketLink = document.getElementById("ticketmaster_link");
      ticketLink.style.display = "none";
      throw new Error(`Error fetching concert information. Status: ${response.status}`);
    }
    const concertInfo = await response.json();

    // Access properties of the concertInfo
    if (Object.keys(concertInfo).length > 0) {
      const ticketUrl = concertInfo.ticketUrl;
      if (ticketUrl != null) {
        const localDate = concertInfo.localDate;
        const localTime = concertInfo.localTime;
        const venueName = concertInfo.venueName;
        const imageUrl = concertInfo.imageUrl;

        var imageLink = document.getElementById("ticketmaster_image");
        imageLink.src = imageUrl;

        var artis = document.getElementById('artistName');
        artis.textContent = newName;

        var dateConcert = document.getElementById('date');
        dateConcert.textContent = localDate;

        var timeConcert = document.getElementById('time');
        timeConcert.textContent = localTime;

        var venues = document.getElementById('venue');
        venues.textContent = venueName;

        var ticketLink = document.getElementById("ticketmaster_link");
        ticketLink.href = ticketUrl;
        ticketLink.textContent = "Book a ticket!";
        ticketLink.style.display = "inline-block";
      }
      else {
        var imageLink = document.getElementById("ticketmaster_image");
        imageLink.src = "images/microphone-2574511_1280.jpg";
  
        var artis = document.getElementById('artistName');
        artis.textContent = "Bummer, no tickets found!";
  
        var dateConcert = document.getElementById('date');
        dateConcert.textContent = "Please select another artist.";
  
        var timeConcert = document.getElementById('time');
        timeConcert.textContent = "";

        var venues = document.getElementById('venue');
        venues.textContent = "";
  
        var ticketLink = document.getElementById("ticketmaster_link");
        
        ticketLink.style.display = "none";
      }
      
      
    }

  } catch (error) {
    console.error("Error:", error);
  }
}

const arrowButtonLeft = document.getElementById('arrowbutton_left');
const arrowButtonRight = document.getElementById('arrowbutton_right');
// Get the element by its ID
const arrowButton = document.getElementById('arrowbutton');


// Add event listener for mouseover (hover) event
arrowButtonLeft.addEventListener('mouseover', () => {
    arrowButtonLeft.classList.remove('translate-middle');
    arrowButtonLeft.classList.add('translate-middle-y');
});

// Add event listener for mouseout (hover out) event
arrowButtonLeft.addEventListener('mouseout', () => {
    arrowButtonLeft.classList.remove('translate-middle-y');
    arrowButtonLeft.classList.add('translate-middle');
});

//* Add event listener for mouseover (hover) event
arrowButtonRight.addEventListener('mouseover', () => {
    arrowButtonRight.classList.remove('position-absolute', 'top-50', 'start-100', 'translate-middle');
    arrowButtonRight.classList.add('position-absolute', 'top-50', 'end-0', 'translate-middle-y');
});

// Add event listener for mouseout (hover out) event
arrowButtonRight.addEventListener('mouseout', () => {
    arrowButtonRight.classList.remove('position-absolute', 'top-50', 'end-0', 'translate-middle-y');
    arrowButtonRight.classList.add('position-absolute', 'top-50', 'start-100', 'translate-middle');
});
