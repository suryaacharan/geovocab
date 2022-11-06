<!DOCTYPE html>
<html>

<head>
	<title>Google Map</title>
	<meta name="viewport" content="initial-scale=1.0">
	<meta charset="utf-8">
	<style>
		#map {
			height: 500px;
			width: 700px;
		}

		#geoHashTitle {
			font-weight: bold;
			width: 25%;
			float: left;
		}

		#magicWordsTitle {
			font-weight: bold;
			width: 25%;
			float: left;
		}

		#latlngtitle {
			float: left;
			width: 25%;
		}

		#latclicked {
			float: left;
			width: 75%;
		}

		#longclicked {
			float: left;
			width: 75%;
		}

		#geoHash {
			float: left;
			width: 75%;
		}

		#magicWords {
			float: left;
			width: 75%;
		}

		#t {
			float: left;
			width: 25%;
		}
	</style>
</head>

<body>

	<div id="latlngtitle">Latitude Of Marker :</div>
	<div id="latclicked"></div>
	<div id="latlngtitle">Longitude Of Marker :</div>
	<div id="longclicked"></div>

	<div id="geoHashTitle">GeoHash of Marker :</div>
	<div id="geoHash"></div>
	<div id="magicWordsTitle">Magic Words of Marker :</div>
	<div id="magicWords"></div>

	<div id="t">
		<label for="GeoVocab"><b>GeoVocab:</b> </label>
	</div>
		<input type="text" id="GeoVocab" value="Bread-And-Chocolate" />
	
	<input type="button" id="submitBtn" onclick="getGeoSpatialData()" value="Get Location" />

	<div style="padding:20px">
		<div id="map"></div>
	</div>

	<script type="text/javascript">
		var map;
		var marker;
		var submitButton = document.getElementById("GeoVocab");
		submitButton.addEventListener("keyup", function (event) {
			if (event.keyCode === 13) {
				event.preventDefault();
				document.getElementById("submitBtn").click();
			}
		});
		function initMap() {
			var latitude = 13.031437;
			var longitude = 80.246952;

			var myLatLng = { lat: latitude, lng: longitude };

			map = new google.maps.Map(document.getElementById('map'), {
				center: myLatLng,
				zoom: 14,
				disableDoubleClickZoom: true
			});

			google.maps.event.addListener(map, 'click', function (event) {
				document.getElementById('latclicked').innerHTML = event.latLng.lat();
				document.getElementById('longclicked').innerHTML = event.latLng.lng();
			});

			google.maps.event.addListener(map, "click", (event) => {
				placeMarker(event.latLng);
				getHashCodeAndMagicWords(event);
			});

		}

		function placeMarker(location) {
			if (marker) {
				marker.setPosition(location);
			} else {
				marker = new google.maps.Marker({
					position: location,
					map: map
				});
			}
		}

		function getHashCodeAndMagicWords(event) {
			var URLvar = "http://23.102.43.92:8315/vocabmapper-api/geovocab/latitude/";
			URLvar += event.latLng.lat();
			URLvar += "/longitude/";
			URLvar += event.latLng.lng();
			URLvar += "/words";
			fetch(URLvar, {
				mode: 'cors',
				headers: {
					'Access-Control-Allow-Origin': '*'
				}
			}).then(response => {
				return response.json();
			}).then(data => {
				console.log(data);
				document.getElementById('geoHash').innerHTML = data.data.geoHash;
				document.getElementById('magicWords').innerHTML = data.data.geoVocab;
				return data;
			}).catch(err => {
				console.log(err);
			});
		}

		function getGeoSpatialData() {
			let magicWords = document.getElementById("GeoVocab").value;
			var URLvar = "http://23.102.43.92:8315/vocabmapper-api/geovocab/words/";
			URLvar += magicWords;
			URLvar += "/location";
			fetch(URLvar, {
				mode: 'cors',
				headers: {
					'Access-Control-Allow-Origin': '*'
				}
			}).then(response => {
				return response.json();
			}).then(data => {
				console.log(data);
				document.getElementById('geoHash').innerHTML = data.data.geoHash;
				document.getElementById('magicWords').innerHTML = data.data.geoVocab;
				document.getElementById('latclicked').innerHTML = data.data.latitude;
				document.getElementById('longclicked').innerHTML = data.data.longitude;
				var latitude = data.data.latitude;
				var longitude = data.data.longitude;
				var myLatLng = { lat: latitude, lng: longitude };
				placeMarker(myLatLng);
				map.setCenter(myLatLng);
				return data;
			}).catch(err => {
				console.log(err);
			});
		}

	</script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAUtMWEOA_lnhbIOlCtzba4vgfz2agx_r8&callback=initMap"
		async defer></script>
</body>

</html>