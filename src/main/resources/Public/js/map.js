var map;
var init = true;
var his = [];

function initMap() { //this function will get call back from Google map API
    mapDiv = document.getElementById('map');
    map = new google.maps.Map(mapDiv, {
        center: {lat: 42.4393687, lng: -76.501165}, // init api
        zoom: 15, draggable: true, maxZoom: 17, minZoom: 14, streetViewControl: false,
        mapTypeId: google.maps.MapTypeId.roadmap
    }); //set map option

    //
    //var demoLo = {lat: 42.439401, lng: -76.499547};
    //
    //
    //var markerE = new google.maps.Marker({
    //    position: demoLo,
    //    map: map
    //
    //});
    //
    //var contentString = '<div id="content">' +
    //    '<div id="siteNotice">' +
    //    '</div>' +
    //    '<h1 id="firstHeading" class="firstHeading">Steve Hackett - Genesis Revisited with Classic Hackett 2017 Tour in Ithaca</h1>' +
    //    '<div id="bodyContent" style="float:left; display: block">' +
    //    '<p>Steve Hackett</p>' +
    //    '<p><b>February 18, 2017</b><br>' +
    //    '<b>Saturday   8:00 PM</b></p> ' +
    //    '<p><a href=/search>'+
    //    'State Theater of Ithaca</a><br>' +
    //    '105 State Street<br>' +
    //    'Ithaca, New York 14850</p>' +
    //    '<p>Former Genesis guitarist and prog legend Steve Hackett is returning with an exciting new show in 2017.</p>' +
    //    '</div>' +
    //    '<div style="=float:right; display: block"><img src="http://www.progarchives.com/progressive_rock_discography_band/782.jpg"></div>' +
    //    '</div>';
    //
    //var infowindow = new google.maps.InfoWindow({
    //    content: contentString
    //});
    //
    //markerE.addListener('click', function () {
    //    infowindow.open(map, markerE);
    //});

    //---------------------------------------

    getEvent();

}
function show() {
    var demoLoF = {lat: 42.451451, lng:-76.505192};

    var markerF = new google.maps.Marker({
        position: demoLoF,
        map: map

    });

    //var contentStringII = '<div class="content"><h1>That 1 Guy @ The Haunt in Ithaca, NY</h1></div>' +
    //    '<div class="vk_sh">Friday, February 10, 2017, 7:00 PM</div>'+
    //    '<div class="vk_c_cxp"><div><a href="/search?sa=X&amp;biw=1440&amp;bih=776&amp;q=The+Haunt&amp; ludocid=172722043785288477&amp;sqi=2&amp;ved=0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB" data-ved="0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB">The Haunt</a></div><div class="vk_gy">702 Willow Avenue, Ithaca, NY</div></div>';

    var infowindowF = new google.maps.InfoWindow({
        content: contentStringII
    });

    markerF.addListener('click', function () {
        infowindowF.open(map, markerF);
    });
    markerF.setMap(map);
}

function getEvent(){
    $.getJSON("getmapjson", function(result){
        console.log(result);
//            $.each(result, function(key, val){
//                console.log(key);
//                console.log(val);
//                //addMarker(val)
//            });
    });
    //$.getJSON("getmapjson", function(result){
    //    $.each(result, function(key, val){
    //        console.log(key);
    //        console.log(val);
    //        //addMarker(val)
    //    });
    //});
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

