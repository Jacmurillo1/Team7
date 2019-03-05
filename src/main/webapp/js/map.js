/** Creates a map object and adds markers. */
function createMap(){

  const map = new google.maps.Map(document.getElementById('map'), {
    // Set the center to be Memphis, Tennessee (kind of equidistant from all of our schools)
    center: {lat: 35.149774, lng: -90.048283},
    zoom: 5
  });

  addLandmark(map, 38.899765, -77.048481, 'The George Washington University', 'Melody attends school here!');
  addLandmark(map, 25.651652, -100.289347, 'Monterrey Institute of Technology', 'Luis attends school here!');
  addLandmark(map, 29.940746, -90.119920, 'Tulane University', 'Mary attends school here!');
  addLandmark(map, 33.207544, -97.152416, 'University of North Texas', 'Jacob attends school here!');
  addLandmark(map, 40.442823, -79.942998, 'Carnegie Mellon University', 'Our PA Justin attended school here!');

}

/** Adds a marker that shows an InfoWindow when clicked. */
function addLandmark(map, lat, lng, title, description){

  const marker = new google.maps.Marker({
    position: {lat: lat, lng: lng},
    map: map,
    title: title
  });

  var infoWindow = new google.maps.InfoWindow({
    content: description
  });
  marker.addListener('click', function() {
    infoWindow.open(map, marker);
  });
}