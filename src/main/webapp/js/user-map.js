let editMarker;
let map;

function createMap(){

  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 38.5949, lng: -94.8923},
    zoom: 4
  });

  // Add marker to map where user clicked
  map.addListener('click', (event) => {
    createMarkerForEdit(event.latLng.lat(), event.latLng.lng());
  });

  fetchMarkers();
}

// Uses Fetch API to get markers from server and adds each to the map
function fetchMarkers(){
  fetch('/user-markers').then((response) => {
    return response.json();
  }).then((markers) => {
    markers.forEach((marker) => {
     createMarkerForDisplay(marker.lat, marker.lng, marker.content)
    });
  });
}

// Adds a non-editable marker to the map
function createMarkerForDisplay(lat, lng, content){

  const marker = new google.maps.Marker({
    position: {lat: lat, lng: lng},
    map: map
  });

  var infoWindow = new google.maps.InfoWindow({
    content: content
  });
  marker.addListener('click', () => {
    infoWindow.open(map, marker);
  });
}

// Send new markers to the server
function postMarker(lat, lng, content){
  const params = new URLSearchParams();
  params.append('lat', lat);
  params.append('lng', lng);
  params.append('content', content);

  fetch('/user-markers', {
    method: 'POST',
    body: params
  });
}

// Create map marker that takes in info from the user
function createMarkerForEdit(lat, lng){

  // Calling setMap(null) on the previous marker before creating a new one
  // so that only one Info Window can be open at a time
  if(editMarker){
   editMarker.setMap(null);
  }

  editMarker = new google.maps.Marker({
    position: {lat: lat, lng: lng},
    map: map
  });

  // Populate the user input content in an Info Window that we attach to our marker
  const infoWindow = new google.maps.InfoWindow({
    content: buildInfoWindowInput(lat, lng)
  });

  // Remove the marker when the user closes the Info Window
  google.maps.event.addListener(infoWindow, 'closeclick', () => {
    editMarker.setMap(null);
  });

  infoWindow.open(map, editMarker);
}

// Helper function that builds a div that contains a textarea and a button
function buildInfoWindowInput(lat, lng){
  const textBox = document.createElement('textarea');
  const button = document.createElement('button');
  button.appendChild(document.createTextNode('Submit'));

  // When user clicks submit, send data to server and display marker on map
  button.onclick = () => {
    postMarker(lat, lng, textBox.value);
    createMarkerForDisplay(lat, lng, textBox.value);
    // Remove the editable marker after adding display marker
    editMarker.setMap(null);
  };

  const containerDiv = document.createElement('div');
  containerDiv.appendChild(textBox);
  containerDiv.appendChild(document.createElement('br'));
  containerDiv.appendChild(button);

  return containerDiv;
}