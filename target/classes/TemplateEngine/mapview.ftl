<link rel="stylesheet" type="text/css" href="css/map.css">
<div class="starter-template">
    <div id="map"></div>
    <div id="holder"></div>
    <div id="floating-panel"><!--Controls-->
        <!--<input id="etitle"  onclick="clean(etitle);" type="text" value="Event Title">-->
        <!--<input id="date"  onclick="clean(date);" type="text" value="Date">-->
        <!--<input id="place"  onclick="clean(place);" type="text" value="Place">-->
        <!--<input id="more" onclick="clean(more);" type="text" value="More">-->
        <input type="submit" onclick="show();" value="Add Event">
        <#--<input type="submit" onclick="getEvent();" value="Add Event From Database">-->
        <!--<form id="display"></form>-->
    </div>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.0.0-beta1/jquery.js"></script> <!--Load jQuery-->
<#--<script async defer src="https://maps.googleapis.com/maps/api/js?&callback=initMap"></script><!--load Google Map API&ndash;&gt;-->

<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC9Y37ttLBmvgaxOo5--xnnEcNECP0bUxk&callback=initMap">
</script>

<script src="js/map.js"></script>

<#--AIzaSyC9Y37ttLBmvgaxOo5--xnnEcNECP0bUxk-->
