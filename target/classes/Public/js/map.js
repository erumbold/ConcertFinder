var map;
var init = true;
var his = [];

function initMap() { //this function will get call back from Google map API
    mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 42.4393687, lng: -76.501165}, // init api
        zoom: 15, draggable: true, maxZoom: 19, minZoom: 14, streetViewControl: false,
        mapTypeId: google.maps.MapTypeId.roadmap
    }); //set map option
    var geocoder = new google.maps.Geocoder;
    map.addListener('click', function(event) {
        var clickLat = event.latLng.lat();
        var clickLon = event.latLng.lng();
        addEventKicker(geocoder, clickLat,clickLon);
        //// 3 seconds after the center of the map has changed, pan back to the
        //// marker.
        //window.setTimeout(function() {
        //    map.panTo(marker.getPosition());
        //}, 3000);
    });
    getEvent();
}

function geocodeLatLng(geocoder,clickLat, clickLon) {
    var latlng = {lat: clickLat, lng: clickLon};
    geocoder.geocode({'location': latlng}, function(results, status) {
        if (status === 'OK') {
            if (results) {
                console.log(results);
                pushToCreateEvent(results,clickLat, clickLon);

            } else {
                window.alert('No results found');
            }
        } else {
            window.alert('Geocoder failed due to: ' + status);
        }
    });
}

function addEventKicker(geocoder,clickLat,clickLon){
    geocodeLatLng(geocoder,clickLat,clickLon);
}

function pushToCreateEvent(address,clickLat,clickLon){
    console.log("Click");
    console.log(clickLat);
    console.log(clickLon);
    console.log(address[0].formatted_address);
    var URL = "addEventPre/"+clickLat+"/"+clickLon+"/"+address[0].formatted_address;
    console.log(URL);
    $.post(URL).done(function() {location.href = 'addEvent';})
}

function getEvent(){
    $.getJSON("getmapjson", function(result){
        $.each(result, function(key, val){
            addMarker(val);
        });
    });
}

function addMarker(val){
    console.log("Yes!" + val);
    var latt;
    var lngg;
    latt = parseFloat(val[13]);
    lngg = parseFloat(val[12]);
    var demoLoF = {lat:latt, lng:lngg};

    var markerF = new google.maps.Marker({
        position: demoLoF,
        map: map

    });

    var minuteDigs = val[5];
    if (minuteDigs < 10){
        minuteDigs = "0"+val[5];
    }

    var contentStringTest = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        '<h1 id="firstHeading" class="firstHeading">'+val[0]+'</h1>' +
        '<div id="bodyContent" style="float:left; display: block">' +
        '<p><b>'+val[1]+'/'+val[2]+'/'+val[3]+' @ '+val[4]+':'+minuteDigs+'</b><br>' +
        '<p><a href=/search>'+
        val[7]+'</a><br>' +
        val[8]+'<br>' +
        val[9]+', '+val[10]+' '+val[11]+'</p>' +
        '<p>'+val[6]+'</p>' +
        '</div>';

    var infowindowF = new google.maps.InfoWindow({
        content: contentStringTest
    });

    markerF.addListener('click', function () {
        infowindowF.open(map, markerF);
    });

}

function clean(id){ //clean the form
    var temp = $("#"+id.id).val();
    temp.val("");
}

