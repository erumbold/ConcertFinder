/**
 * Created by yanmingwang on 4/25/17.
 */
function getEvent(){
    $.getJSON("getmapjson", function(result){
        $.each(result, function(key, val){
            console.log(key);
            console.log(val);
            //addMarker(val)
        });
    });
}