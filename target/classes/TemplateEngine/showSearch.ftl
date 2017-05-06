<style>
	table th a {
		text-transform: capitalize;
	}
</style>

<div class="starter-template">
	<h2> Search Results </h2>
	<div class="userTable"> </div>
	<div class="paginationContainer "></div>
</div>
<script src="js/awesomeTable.js" type="text/javascript"></script>
<script>
	$( document ).ready(function() {
		$.getJSON('/getsearch',function(json){
			if ( json.length == 0 ) {
				console.log("NO DATA!");
				$(".userTable").text("No Events Found");
			}
			else {
				newData = [];
                for (i = 0; i < json.length; i++) {
					json[i].splice(12,2);
                    json[i].splice(10,1);
					if(json[i][10]){
                    	temp=json[i][10].toString();
					}else{
						temp="";
					}
					newData.push({"Name":json[i][0],"Month":json[i][1],"Day":json[i][2],"Year":json[i][3],"Hour":json[i][4],"Minute":json[i][5],"description":json[i][6],"Venue":json[i][7],"Street":json[i][8],"City":json[i][9],"Zip":temp});
                }
				var tbl = new awesomeTableJs({
					data:newData,
					tableWrapper:".userTable",
					paginationWrapper:".paginationContainer",
					buildPageSize: false,
					buildSearch: false,
				});
				tbl.createTable();
			}
		});

	});

</script>