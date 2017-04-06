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


    var demoLo = {lat: 42.439401, lng: -76.499547};


    var markerE = new google.maps.Marker({
        position: demoLo,
        label: "S",
        title: 'Hello World!',
        map: map

    });

    var contentString = '<div id="content">' +
        '<div id="siteNotice">' +
        '</div>' +
        '<h1 id="firstHeading" class="firstHeading">Steve Hackett - Genesis Revisited with Classic Hackett 2017 Tour in Ithaca</h1>' +
        '<div id="bodyContent" style="float:left; display: block">' +
        '<p>Steve Hackett</p>' +
        '<p><b>February 18, 2017</b><br>' +
        '<b>Saturday   8:00 PM</b></p> ' +
        '<p>State Theatre<br>' +
        '105 State Street<br>' +
        'Ithaca, New York 14850</p>' +
        '<p>Former Genesis guitarist and prog legend Steve Hackett is returning with an exciting new show in 2017.</p>' +
        '</div>' +
        '<div style="=float:right; display: block"><img src="http://www.progarchives.com/progressive_rock_discography_band/782.jpg"></div>' +
        '</div>';

    var infowindow = new google.maps.InfoWindow({
        content: contentString
    });

    markerE.addListener('click', function () {
        infowindow.open(map, markerE);
    });

    //---------------------------------------

    getEvent();

}
function show() {
    var demoLoF = {lat: 42.451451, lng:-76.505192};

    var markerF = new google.maps.Marker({
        position: demoLoF,
        label: "E",
        title: 'Hello World!',
        map: map

    });

    var contentStringII = '<div class="content"><h1>That 1 Guy @ The Haunt in Ithaca, NY</h1></div>' +
        '<div class="vk_sh">Friday, February 10, 2017, 7:00 PM</div>'+
        '<div class="vk_c_cxp"><div><a href="/search?sa=X&amp;biw=1440&amp;bih=776&amp;q=The+Haunt&amp; ludocid=172722043785288477&amp;sqi=2&amp;ved=0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB" data-ved="0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB">The Haunt</a></div><div class="vk_gy">702 Willow Avenue, Ithaca, NY</div></div>';

    var infowindowF = new google.maps.InfoWindow({
        content: contentStringII
    });

    markerF.addListener('click', function () {
        infowindowF.open(map, markerF);
    });
    markerF.setMap(map);
}

function getEvent(){
    $.getJSON("getevents", function(result){
        $.each(result, function(key, val){
            //$("div").append(field + " ");
            console.log(key);
            console.log(val);
            addMarker(val)
        });
    });
}
function addMarker(val){

    console.log("Yes!" + val);
    var latt;
    var lngg;
    //if(his.includes(val[12])+parseFloat(val[13])){
    //    latt = parseFloat(val[12])+0.01;
    //    lngg = parseFloat(val[13])+0.01;
    //}else{
        latt = parseFloat(val[12]);
        lngg = parseFloat(val[13]);
    //    his.push(parseFloat(val[12])+parseFloat(val[13]));
    //}
    var demoLoF = {lat:latt, lng:lngg};

    var markerF = new google.maps.Marker({
        position: demoLoF,
        label: "!",
        title: "test",
        map: map

    });

    //var contentStringII = '<div class="content"><h1>That 1 Guy @ The Haunt in Ithaca, NY</h1></div>' +
    //    '<div class="vk_sh">Friday, February 10, 2017, 7:00 PM</div>'+
    //    '<div class="vk_c_cxp"><div><a href="/search?sa=X&amp;biw=1440&amp;bih=776&amp;q=The+Haunt&amp; ludocid=172722043785288477&amp;sqi=2&amp;ved=0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB" data-ved="0ahUKEwiO6Kj5koPSAhWC6iYKHYPQAsEQrj0IXjAB">The Haunt</a></div><div class="vk_gy">702 Willow Avenue, Ithaca, NY</div></div>';

    var infowindowF = new google.maps.InfoWindow({
        content: val[0]
    });

    markerF.addListener('click', function () {
        infowindowF.open(map, markerF);
    });
    markerF.setMap(map);
}

function clean(id){ //clean the form
    var temp = $("#"+id.id).val();
    temp.val("");
}

