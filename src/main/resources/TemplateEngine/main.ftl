<html>
    <head>
        <link rel="stylesheet" href="css/interfaceDesign.css">
        <meta charset="UTF-8">
        <title>Home</title>
    </head>
    <body>
    <center>
    <img src="assets/concertimage.png" style="width:75px;height:100px;">
    </center>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <#--<a href="logInPage.html">LOGIN</a>-->
            <a href="/">Home</a>
            <a href="addEvent">New Event</a>

        </div>
        <span style="font-size:20px;cursor:pointer" onclick="openNav()">&#9776; Menu</span>
        <script>
            function openNav() {
                document.getElementById("mySidenav").style.width = "250px";
            }

            /* Set the width of the side navigation to 0 */
            function closeNav() {
                document.getElementById("mySidenav").style.width = "0";
            }
        </script>
        <form action="" method="POST" role="form">
            <center>
              <input type="text" id="SEARCH" name="SEARCH" placeholder="Search..." class="search">
              <button type="submit" class="btn btn-default">Submit</button>
            </center>
        <script>

        $(document).on('submit', 'form', function(e) {
             var st = $( "#SEARCH" );
             var txt = st.value();
             $.ajax({
                url: $(this).attr('action'),
                type: $(this).attr('method'),
                data: txt,
            });
            e.preventDefault();
        });
        </script>
        <center>
        <br>
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <center>
            <div class="container">
                <a href="search">List View</a>  <a href="map">Map View</a>
                <#include "${templateName}">
            </div>
        </center>
            <br>
            <br>
        </center>
        <center>
            <br>
            <br>
            <br>
            <br>
        </center>
        <script src="js/jquery.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
