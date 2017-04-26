<html>
    <head>
        <link rel="stylesheet" href="css/interfaceDesign.css">
        <meta charset="UTF-8">
        <title>Home</title>
    </head>
    <body>

        <div id="mySidenav" class="sidenav">
            <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
            <a href="logInPage.html">LOGIN</a>
            <a href="addEvent">NEW EVENT </a>
        </div>
        <span style="font-size:20px;cursor:pointer" onclick="openNav()">&#9776; EVENT</span>
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
                <#include "${templateName}">
            </div>
        </center>
            <br>
            <br>
            <a href="search">List View</a>  <a href="map">Map View</a>
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
