<div class="starter-template">
    <center>
        <br>
        <br>
        <br>
        <input type="text" placeholder="Title">
        </p>
        <p>
            <input type="Text" placeholder="Venue Name">
        </p>
        <p>
            <#if address??>
                <input type="Text" placeholder="Address" value="${address}">
            <#else>
                <input type="Text" placeholder="Address">
            </#if>
        </p>
        <p>
            <select name="month">
                <option selected value=''>--Select Month--</option>
                <option value='1'>Janaury</option>
                <option value='2'>February</option>
                <option value='3'>March</option>
                <option value='4'>April</option>
                <option value='5'>May</option>
                <option value='6'>June</option>
                <option value='7'>July</option>
                <option value='8'>August</option>
                <option value='9'>September</option>
                <option value='10'>October</option>
                <option value='11'>November</option>
                <option value='12'>December</option>
            </select> <select>
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

            </select>
        </p>
        <p>
            <select>
                <option>--Select Time--</option>
                <option value="1">12:00AM</option>
                <option value="2">12:30AM</option>
                <option value="3">1:00AM</option>
                <option value="4">1:30AM</option>
                <option value="5">2:00AM</option>
                <option value="6">2:30AM</option>
                <option value="7">3:00AM</option>
                <option value="8">3:30AM</option>
                <option value="9">4:00AM</option>
                <option value="10">4:30AM</option>
                <option value="11">5:00AM</option>
                <option value="12">5:30AM</option>
                <option value="13">6:00AM</option>
                <option value="14">6:30AM</option>
                <option value="15">7:00AM</option>
                <option value="16">7:30AM</option>
                <option value="17">8:00AM</option>
                <option value="18">8:30AM</option>
                <option value="19">9:00AM</option>
                <option value="20">9:30AM</option>
                <option value="21">10:00AM</option>
                <option value="22">10:30AM</option>
                <option value="23">11:00AM</option>
                <option value="24">11:30AM</option>
                <option value="25">12:00PM</option>
                <option value="26">12:30PM</option>
                <option value="27">1:00PM</option>
                <option value="28">1:30PM</option>
                <option value="29">2:00PM</option>
                <option value="30">2:30PM</option>
                <option value="31">3:00PM</option>
                <option value="32">3:30PM</option>
                <option value="33">4:00PM</option>
                <option value="34">4:30PM</option>
                <option value="35">5:00PM</option>
                <option value="36">5:30PM</option>
                <option value="37">6:00PM</option>
                <option value="38">6:30PM</option>
                <option value="39">7:00PM</option>
                <option value="40">7:30PM</option>
                <option value="41">8:00PM</option>
                <option value="42">8:30PM</option>
                <option value="43">9:00PM</option>
                <option value="44">9:30PM</option>
                <option value="45">10:00PM</option>
                <option value="46">10:30PM</option>
                <option value="47">11:00PM</option>
                <option value="48">11:30PM</option> </select> <select id="year"></select>
        </p>
        <p>
        <p>
            Description(optional):
        </p>
        <textarea rows="4" cols="50">
                </textarea>

        </p>
        <button type="button" onclick="tcreateEvent()">Submit</button>
        <#--<button type="" class="btn btn-default" onclick="createEvent()">Do</button>-->
    </center>
    <script>
        function tcreateEvent(){
            console.log("clicked");

        }
        var start = 1900;
        var end = new Date().getFullYear();
        var options = "";
        options+= "<option>" +"--Select Year--"+"</option>"
        for(var year = start ; year <=end; year++){

            options += "<option>"+ year +"</option>";
        }
        document.getElementById("year").innerHTML = options;
    </script>
</div>



