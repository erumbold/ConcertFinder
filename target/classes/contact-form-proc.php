
//<?php

/*if(empty($_POST["submit"]))
{
    echo "Form is not submitted!";
    exit;
}
if(empty($_POST["fullname"]) || empty($_POST["email"]))
{
    echo "Please fill the form";
    exit;
}
$name=$_POST["fullname"];
$email=$_POST["email"];
mail ("umunakwec@yahoo.com", "New form submission", "New form submission: Name: $name, Email; $email");
*/

//header('Location: thank-you.html');
/*$name = $email = $event = $date = $time = $location= $price = $description= "";
if($_SERVER["Request_method"]== "POST"){
     $name = test_input($_POST["name"]);
     $email = test_input($_POST["email"]);
     $website = test_input($_POST["event"]);
     $comment = test_input($_POST["date"]);
     $gender = test_input($_POST["time"]);
     $gender = test_input($_POST["location"]);
     $gender = test_input($_POST["price"]);
     $gender = test_input($_POST["description]);

}
*/
<!DOCTYPE html>
<html>
<head>
<style>
table {
    width: 100%;
    border-collapse: collapse;
}

table, td, th {
    border: 1px solid black;
    padding: 5px;
}

th {text-align: left;}
</style>
</head>
<body>

<?php
$q = intval($_GET['q']);

$db = mysqli_connect('localhost','username','password','database_name')
 or die('Error connecting to MySQL server.');
?>
}

mysqli_select_db($con,"ajax_demo");
$sql="SELECT * FROM user WHERE id = '".$q."'";
$result = mysqli_query($con,$sql);

echo "<table>
<tr>
<th>Name</th>
<th>Email</th>
<th>Event</th>
<th>Date</th>
<th>Time</th>
<th>Location</th>
<th>Price</th>
<th>Description</th>
</tr>";
while($row = mysqli_fetch_array($result)) {
    echo "<tr>";
    echo "<td>" . $row['Name'] . "</td>";
    echo "<td>" . $row['Email'] . "</td>";
    echo "<td>" . $row['Event'] . "</td>";
    echo "<td>" . $row['Date'] . "</td>";
    echo "<td>" . $row['Time'] . "</td>";
    echo "<td>" . $row['Location'] . "</td>";
    echo "<td>" . $row['Price'] . "</td>";
    echo "<td>" . $row['Description'] . "</td>";
    echo "</tr>";
}
echo "</table>";
mysqli_close($con);
?>
</body>
</html>











//?>
