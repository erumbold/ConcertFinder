<div class="starter-template">
    <h2> Add Event </h2>
</div>


<center>
    <br>
    <br>
    <br>
        <form action="" method="POST" role="form">
            <div class="form-group">
                <input type="text" class="form-control" id="TITLE" name="TITLE" placeholder="Title">
            </div>
    </p>
    <p>
            <div class="form-group">
                <input type="text" class="form-control" id="VENUE_NAME" name="VENUE_NAME" placeholder="Venue Name">
            </div>
    </p>
    <p>
            <div class="form-group">
                <input type="text" class="form-control" id="ADDRESS" name="ADDRESS" placeholder="Address">
            </div>
    </p>
    <p>
            <div class="form-group">
         <select name="MONTH" id="MONTH">
        <option selected value=''>--Select Month--</option>
        <option value="January">Janaury</option>
        <option value="February">February</option>
        <option value="March">March</option>
        <option value="April">April</option>
        <option value="May">May</option>
        <option value="June">June</option>
        <option value="July">July</option>
        <option value="August">August</option>
        <option value="September">September</option>
        <option value="October">October</option>
        <option value="November">November</option>
        <option value="December">December</option>
    </select></div>
    <p></p>
    <div class="form-group"><select name="DAY" id="DAY">
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
    </select></div>

    <p>
            <div class="form-group"><select name="TIME" id="TIME">
            <option>--Select Time--</option>
            <option value="1200AM">12:00AM</option>
            <option value="1230AM">12:30AM</option>
            <option value="100AM">1:00AM</option>
            <option value="130AM">1:30AM</option>
            <option value="200AM">2:00AM</option>
            <option value="230AM">2:30AM</option>
            <option value="300AM">3:00AM</option>
            <option value="330AM">3:30AM</option>
            <option value="400AM">4:00AM</option>
            <option value="430AM">4:30AM</option>
            <option value="500AM">5:00AM</option>
            <option value="530AM">5:30AM</option>
            <option value="600AM">6:00AM</option>
            <option value="630AM">6:30AM</option>
            <option value="700AM">7:00AM</option>
            <option value="730AM">7:30AM</option>
            <option value="800AM">8:00AM</option>
            <option value="830AM">8:30AM</option>
            <option value="900AM">9:00AM</option>
            <option value="930AM">9:30AM</option>
            <option value="1000AM">10:00AM</option>
            <option value="1030AM">10:30AM</option>
            <option value="1100AM">11:00AM</option>
            <option value="1130AM">11:30AM</option>
            <option value="1200PM">12:00PM</option>
            <option value="1230PM">12:30PM</option>
            <option value="100PM">1:00PM</option>
            <option value="130PM">1:30PM</option>
            <option value="200PM">2:00PM</option>
            <option value="230PM">2:30PM</option>
            <option value="300PM">3:00PM</option>
            <option value="330PM">3:30PM</option>
            <option value="400PM">4:00PM</option>
            <option value="430PM">4:30PM</option>
            <option value="500PM">5:00PM</option>
            <option value="530PM">5:30PM</option>
            <option value="600PM">6:00PM</option>
            <option value="630PM">6:30PM</option>
            <option value="700PM">7:00PM</option>
            <option value="730PM">7:30PM</option>
            <option value="800PM">8:00PM</option>
            <option value="830PM">8:30PM</option>
            <option value="900PM">9:00PM</option>
            <option value="930PM">9:30PM</option>
            <option value="1000PM">10:00PM</option>
            <option value="1030PM">10:30PM</option>
            <option value="1100PM">11:00PM</option>
            <option value="1130PM">11:30PM</option> </select></div>

            <p></p>
            <div class="form-group"><select id="YEAR" name="YEAR"></select></div>
    </p>
    <p>
    <p>
        Description(optional):
    </p>
            <div class="form-group">
    <textarea rows="4" cols="50" class="form-control" id="DESCRIPTION" name="DESCRIPTION">
            </textarea></div>

    </p>
    <button type="submit" class="btn btn-default">Submit</button>
            </form>
</center>

<script>
    var start = 1900;
    var end = new Date().getFullYear();
    var options = "";
    options+= "<option>" +"--Select Year--"+"</option>";
    for(var year = start ; year <=end; year++){

        options += "<option>"+ year +"</option>";
    }
    document.getElementById("YEAR").innerHTML = options;
</script>

<script>
$(function() {
    $('form').submit(function(e) {
        e.preventDefault();
        var this_ = $(this);
        var array = this_.serializeArray();
        var json = {};

        $.each(array, function() {
            json[this.name] = this.value || 'k';
        });

        json = JSON.stringify(json);

        // Ajax Call
        $.ajax({
            type: "POST",
            url: "addEvent",
            data: json,
            dataType: "json",
            success : function() {
                $("#status").text("User SuccesFully Added");
                this_.find('input,select').val('');
            },
            error : function(e) {
                console.log(e.responseText);
                $("#status").text(e.responseText);
            }
        });
        $("html, body").animate({ scrollTop: 0 }, "slow");
        return false;
    });
});

</script>
