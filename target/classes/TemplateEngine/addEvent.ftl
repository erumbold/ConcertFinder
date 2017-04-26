<div class="starter-template">
    <h2> Add Event </h2>
</div>

<form action="" method="POST" role="form">
    <div class="form-group">
      <label for="TITLE">Event Name</label>
      <input type="text" class="form-control" id="TITLE" name="TITLE" placeholder="Enter Event Name">
    </div>
    <div class="form-group">
      <label for="MONTH">Month</label>
      <input type="number" class="form-control" id="MONTH" name="MONTH">
    </div>
    <div class="form-group">
      <label for="DAY">Day</label>
      <input type="number" class="form-control" id="DAY" name="DAY">
    </div>
    <div class="form-group">
      <label for="YEAR">Year</label>
      <input type="number" class="form-control" id="YEAR" name="YEAR">
    </div>
    <div class="form-group">
      <label for="HOUR">Hour</label>
      <input type="number" class="form-control" id="HOUR" name="HOUR">
    </div>
    <div class="form-group">
      <label for="MINUTE">Minute</label>
      <input type="number" class="form-control" id="MINUTE" name="MINUTE">
    </div>
    <div class="form-group">
      <label for="DESCRIPTION">Description</label>
      <input type="text" class="form-control" id="DESCRIPTION" name="DESCRIPTION" placeholder="Enter Event Description">
    </div>
    <div class="form-group">
      <label for="VENUE_NAME">Venue Name</label>
      <input type="text" class="form-control" id="VENUE_NAME" name="VENUE_NAME" placeholder="Enter Venue Name">
    </div>

    <#if address??>
        <div class="form-group">
            <label for="ADDRESS">Address</label>
            <input type="text" class="form-control" id="ADDRESS" name="ADDRESS" placeholder="Enter Event Address" value="${address?split(",")[0]}">
        </div>
        <div class="form-group">
            <label for="CITY">City</label>
            <input type="text" class="form-control" id="CITY" name="CITY" placeholder="Enter Event City" value="${address?split(",")[1]}">
        </div>
        <div class="form-group">
            <label for="STATE">State</label>
            <#assign t = address?split(",")[2]>
            <input type="text" class="form-control" id="STATE" name="STATE" placeholder="Enter Event STATE" value="${t?substring(0,3)}">
        </div>
        <div class="form-group">
            <label for="ZIPCODE">Zip Code</label>
            <#assign te = t?substring(4)>
            <input type="number" class="form-control" id="ZIPCODE" name="ZIPCODE" value=${te}>
        </div>
        <div class="form-group">
            <label for="LONGITUDE">Longitude</label>
            <input type="number" class="form-control" id="LONGITUDE" name="LONGITUDE" value="${lon}">
        </div>
        <div class="form-group">
            <label for="LATITUDE">Latitude</label>
            <input type="number" class="form-control" id="LATITUDE" name="LATITUDE" value="${lat}">
        </div>
    <#else>
        <div class="form-group">
          <label for="ADDRESS">Address</label>
          <input type="text" class="form-control" id="ADDRESS" name="ADDRESS" placeholder="Enter Event Address">
        </div>
        <div class="form-group">
          <label for="CITY">City</label>
          <input type="text" class="form-control" id="CITY" name="CITY" placeholder="Enter Event City">
        </div>
        <div class="form-group">
          <label for="STATE">State</label>
          <input type="text" class="form-control" id="STATE" name="STATE" placeholder="Enter Event STATE">
        </div>
        <div class="form-group">
          <label for="ZIPCODE">Zip Code</label>
          <input type="number" class="form-control" id="ZIPCODE" name="ZIPCODE">
        </div>
        <div class="form-group">
            <label for="LONGITUDE">Longitude</label>
            <input type="number" class="form-control" id="LONGITUDE" name="LONGITUDE">
        </div>
        <div class="form-group">
            <label for="LATITUDE">Latitude</label>
            <input type="number" class="form-control" id="LATITUDE" name="LATITUDE">
        </div>
    </#if>
    <button type="submit" class="btn btn-default">Submit</button>
</form>

<script>
$(function() {
    $('form').submit(function(e) {
        e.preventDefault();
        var this_ = $(this);
        var array = this_.serializeArray();
        var json = {};

        $.each(array, function() {
            json[this.name] = this.value || '';
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
