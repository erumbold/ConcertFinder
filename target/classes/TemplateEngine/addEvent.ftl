<link type="text/css" rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500">
<link rel="stylesheet" type="text/css" href="css/googleplace.css">
<div class="starter-template">
    <h2> Add Event </h2>
</div>
<br>
<form id= "theForm">
    <div class="form-group">
        <input type="text" class="form-control" id="TITLE" name="TITLE" placeholder="Title">
    </div>
    <br>
    <div class="form-group">
        <input type="text" class="form-control" id="VENUE_NAME" name="VENUE_NAME" placeholder="Venue Name">
    </div>
    <br>
    <#---------------------------------------------------------->
    <div id="locationField">
        <#if address??>
            <input id="autocomplete" placeholder="Enter/Search your address" onFocus="geolocate()" type="text" value="${address}"/>
            <script>window.onload = function() {
                document.getElementById("autocomplete").focus();
            };</script>
        <#else>
            <input id="autocomplete" placeholder="Enter/Search your address" onFocus="geolocate()" type="text"/>
        </#if>
    </div>
    <table id="address">
        <tr>
            <td class="label">Street address</td>
            <td class="slimField"><input class="field" id="street_number" disabled="true"/></td>
            <td class="wideField" colspan="2"><input class="field" id="route" disabled="true"/></td>
        </tr>
        <tr>
            <td class="label">City</td>
            <!-- Note: Selection of address components in this example is typical.
                 You may need to adjust it for the locations relevant to your app. See
                 https://developers.google.com/maps/documentation/javascript/examples/places-autocomplete-addressform
            -->
            <td class="wideField" colspan="3"><input class="field" id="locality" disabled="true"/></td>
        </tr>
        <tr>
            <td class="label">State</td>
            <td class="slimField"><input class="field" id="administrative_area_level_1" disabled="true"/></td>
            <td class="label">Zip code</td>
            <td class="wideField"><input class="field" id="postal_code" disabled="true"/></td>
        </tr>
        <tr>
            <td class="label">Country</td>
            <td class="wideField" colspan="3"><input class="field" id="country" disabled="true"/></td>
        </tr>
    </table>
    <br>
    <#---------------------------------------------------------->
    <div class="form-group">
        <select name="MONTH" id="MONTH">
            <option selected value=''>--Select Month--</option>
            <option value="1">Janaury</option>
            <option value="2">February</option>
            <option value="3">March</option>
            <option value="4">April</option>
            <option value="5">May</option>
            <option value="6">June</option>
            <option value="7">July</option>
            <option value="8">August</option>
            <option value="9">September</option>
            <option value="10">October</option>
            <option value="11">November</option>
            <option value="12">December</option>
        </select>
    </div>
    <br>
    <div class="form-group">
        <select name="DAY" id="DAY">
            <option>--Select Day--</option>
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
            <option value="11">11</option>
            <option value="12">12</option>
            <option value="13">13</option>
            <option value="14">14</option>
            <option value="15">15</option>
            <option value="16">16</option>
            <option value="17">17</option>
            <option value="18">18</option>
            <option value="19">19</option>
            <option value="20">20</option>
            <option value="21">21</option>
            <option value="22">22</option>
            <option value="23">23</option>
            <option value="24">24</option>
            <option value="25">25</option>
            <option value="26">26</option>
            <option value="27">27</option>
            <option value="28">28</option>
            <option value="29">29</option>
            <option value="30">30</option>
            <option value="31">31</option>
        </select>
    </div>
    <br>
    <div class="form-group">
        <select name="TIME" id="TIME">
            <option>--Select Time--</option>
            <option value="12:00  AM">12:00 AM</option>
            <option value="12:30  AM">12:30 AM</option>
            <option value="01:00  AM">01:00 AM</option>
            <option value="01:30  AM">01:30 AM</option>
            <option value="02:00  AM">02:00 AM</option>
            <option value="02:30  AM">02:30 AM</option>
            <option value="03:00  AM">03:00 AM</option>
            <option value="03:30  AM">03:30 AM</option>
            <option value="04:00  AM">04:00 AM</option>
            <option value="04:30  AM">04:30 AM</option>
            <option value="05:00  AM">05:00 AM</option>
            <option value="05:30  AM">05:30 AM</option>
            <option value="06:00  AM">06:00 AM</option>
            <option value="06:30  AM">06:30 AM</option>
            <option value="07:00  AM">07:00 AM</option>
            <option value="07:30  AM">07:30 AM</option>
            <option value="08:00  AM">08:00 AM</option>
            <option value="08:30  AM">08:30 AM</option>
            <option value="09:00  AM">09:00 AM</option>
            <option value="09:30  AM">09:30 AM</option>
            <option value="10:00  AM">10:00 AM</option>
            <option value="10:30  AM">10:30 AM</option>
            <option value="11:00  AM">11:00 AM</option>
            <option value="11:30  AM">11:30 AM</option>
            <option value="12:00  PM">12:00 PM</option>
            <option value="12:30  PM">12:30 PM</option>
            <option value="01:00  PM">01:00 PM</option>
            <option value="01:30  PM">01:30 PM</option>
            <option value="02:00  PM">02:00 PM</option>
            <option value="02:30  PM">02:30 PM</option>
            <option value="03:00  PM">03:00 PM</option>
            <option value="03:30  PM">03:30 PM</option>
            <option value="04:00  PM">04:00 PM</option>
            <option value="04:30  PM">04:30 PM</option>
            <option value="05:00  PM">05:00 PM</option>
            <option value="05:30  PM">05:30 PM</option>
            <option value="06:00  PM">06:00 PM</option>
            <option value="06:30  PM">06:30 PM</option>
            <option value="07:00  PM">07:00 PM</option>
            <option value="07:30  PM">07:30 PM</option>
            <option value="08:00  PM">08:00 PM</option>
            <option value="08:30  PM">08:30 PM</option>
            <option value="09:00  PM">09:00 PM</option>
            <option value="09:30  PM">09:30 PM</option>
            <option value="10:00  PM">10:00 PM</option>
            <option value="10:30  PM">10:30 PM</option>
            <option value="11:00  PM">11:00 PM</option>
            <option value="11:30  PM">11:30 PM</option>
        </select>
    </div>
    <br>
    <div class="form-group">
        <select id="YEAR" name="YEAR"></select>
    </div>
    <br>
    <p>Description(optional):</p>
    <div class="form-group">
        <textarea rows="4" cols="50" class="form-control" id="DESCRIPTION" name="DESCRIPTION"></textarea>
    </div>
    <br>
    <button type="button" onclick="sendData()">Submit</button>
</form>
<script>
    var start = 2016;
    var end = new Date().getFullYear();
        end++;
    var options = "";
    options+= "<option>" +"--Select Year--"+"</option>";
    for(var year = start ; year <=end; year++){

        options += "<option>"+ year +"</option>";
    }
    document.getElementById("YEAR").innerHTML = options;
    $(document).ready(function() {
        $(window).keydown(function(event){
            if(event.keyCode == 13) {
                event.preventDefault();
                return false;
            }
        });
    });
</script>
<script>
function sendData(){
    var time = $("#TIME").val();
    var state = document.getElementById("locality");
    var form = document.getElementById("theForm");
    console.log(form);
    if (!endAddress.length == 0) {
       if(time === "--Select Time--") {
           alert("Please Select Time!");
       }else if(state.isDisabled){
           alert("Please confirm Address!");
       }else{
           console.log("Called2");
           var this_ = $(this);
           var json = {};
           var hours = Number(time.match(/^(\d+)/)[1]);
           var minutes = Number(time.match(/:(\d+)/)[1]);
           var AMPM = time.match(/\s(.*)$/)[1];
           if(AMPM == "PM" && hours<12) hours = hours+12;
           if(AMPM == "AM" && hours==12) hours = hours-12;
           var sHours = hours.toString();
           var sMinutes = minutes.toString();
           if(hours<10) sHours = "0" + sHours;
           if(minutes<10) sMinutes = "0" + sMinutes;

           json["TITLE"] = $("#TITLE").val();
           json["MONTH"] = $("#MONTH").val();
           json["DAY"] = $("#DAY").val();
           json["YEAR"] =$("#YEAR").val();
           json["HOUR"] =sHours;
           json["MINUTE"] =sMinutes;
           json["DESCRIPTION"] =$("#DESCRIPTION").val();
           json["VENUE_NAME"] =$("#VENUE_NAME").val();
           json["ADDRESS"] = endAddress[0]+" "+endAddress[1];
           json["CITY"] =endAddress[2];
           json["STATE"] =endAddress[5];
           json["ZIPCODE"] =endAddress[7];
           json["LATITUDE"] =endAddress[9].geometry.location.lat();
           json["LONGITUDE"] =endAddress[9].geometry.location.lng();

           json = JSON.stringify(json);
           console.log(json);
        $.ajax({
            url: 'addEvent',
            data:json,
            type: 'post',
            success:function(data) {
                alert("Event Added.");
                setTimeout(function(){location.href = 'addEvent'},500);
            },
            error:function(data){
                alert("There is a problem...");
            }
        });
       }
    }else{
        alert("Please fill all Info!");
    }
}
</script>
<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCxp3Tnc7BZ23kxwpOJ3_HxFb-FyAu87kA&libraries=places&callback=initAutocomplete" async defer></script>
<script src="js/googlePlace.js"></script>
