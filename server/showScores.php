<?php
require "config.php";
if(  strcmp($_POST["pass"] , "marzec2016!") != 0) {
?>

<html>
<head>

<meta charset="UTF-8">
</head>
<body>
Podano nieprawidlowe haslo,
spróbuj jeszcze raz... 
<form action="showScores.php" method="post">
Haslo: <input type="password" name="pass"><br>

<input type="submit">
</form>

</body>
</html>

<?php
die();
}
$rowsFrom = 0;
if(isset($_POST["clean"])) {
    file_put_contents("removed.txt", $_POST["clean"]);
}
if(file_exists("removed.txt")) {
    $contentOfFile = file_get_contents("removed.txt");
    $rowsFrom = intval($contentOfFile);
}

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
$conn->set_charset("utf8");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
 } 

$sql = "SELECT * FROM $tableName";
$result = $conn->query($sql);

echo "<!DOCTYPE html>
<html>
<head>

<meta charset=\"UTF-8\">
<style>

h1 {
font-family: Arial, Helvetica, sans-serif;
color: #006699;
}

.button {
  background: #3498db;
  background-image: -webkit-linear-gradient(top, #3498db, #006699);
  background-image: -moz-linear-gradient(top, #3498db, #006699);
  background-image: -ms-linear-gradient(top, #3498db, #006699);
  background-image: -o-linear-gradient(top, #3498db, #006699);
  background-image: linear-gradient(to bottom, #3498db, #006699);
  -webkit-border-radius: 7;
  -moz-border-radius: 7;
  border-radius: 7px;
  font-family: Arial;
  color: #FFFFFF;
  font-size: 20px;
  padding: 10px 20px 10px 20px;
  text-decoration: none;
margin-top:20px;
}

.button:hover {
  background: #3cb0fd;
  background-image: -webkit-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -moz-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -ms-linear-gradient(top, #3cb0fd, #3498db);
  background-image: -o-linear-gradient(top, #3cb0fd, #3498db);
  background-image: linear-gradient(to bottom, #3cb0fd, #3498db);
  text-decoration: none;
}

.datagrid table { border-collapse: collapse; text-align: left; width: 100%; } .datagrid {font: normal 12px/150% Arial, Helvetica, sans-serif; background: #fff; overflow: hidden; border: 1px solid #006699; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; }.datagrid table td, .datagrid table th { padding: 3px 10px; }.datagrid table thead th {background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; color:#FFFFFF; font-size: 15px; font-weight: bold; border-left: 1px solid #0070A8; } .datagrid table thead th:first-child { border: none; }.datagrid table tbody td { color: #00496B; border-left: 1px solid #E1EEF4;font-size: 12px;font-weight: normal; }.datagrid table tbody .alt td { background: #E1EEF4; color: #00496B; }.datagrid table tbody td:first-child { border-left: none; }.datagrid table tbody tr:last-child td { border-bottom: none; }.datagrid table tfoot td div { border-top: 1px solid #006699;background: #E1EEF4;} .datagrid table tfoot td { padding: 0; font-size: 12px } .datagrid table tfoot td div{ padding: 2px; }.datagrid table tfoot td ul { margin: 0; padding:0; list-style: none; text-align: right; }.datagrid table tfoot  li { display: inline; }.datagrid table tfoot li a { text-decoration: none; display: inline-block;  padding: 2px 8px; margin: 1px;color: #FFFFFF;border: 1px solid #006699;-webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #006699), color-stop(1, #00557F) );background:-moz-linear-gradient( center top, #006699 5%, #00557F 100% );filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#006699', endColorstr='#00557F');background-color:#006699; }.datagrid table tfoot ul.active, .datagrid table tfoot ul a:hover { text-decoration: none;border-color: #006699; color: #FFFFFF; background: none; background-color:#00557F;}div.dhtmlx_window_active, div.dhx_modal_cover_dv { position: fixed !important; }
</style>
</head>
<body>
<h1>Wyniki livexprezentacja</h1>"; 
if ($result->num_rows > 0) {
    echo '<div class="datagrid"><table>';
	
	echo '<thead><tr>';
	
		echo "<th>lp</th>";
		echo "<th>przedstawiciel</th>";
		echo "<th>miejscowość</th>";
		echo "<th>apteka</th>";
		echo "<th>liczba uczestników</th>";
		echo "<th>czas oglądania</th>";
		echo "<th>data wysłania</th>";
		echo "<th>data utworzenia</th>";		
		
	echo '</tr></thead>';

$lp = 0;
echo '<tbody>';

$lastRow = 0;
    while($row = $result->fetch_assoc()) {

        $lpToShow = $lp-$rowsFrom;

        if($lpToShow>=0) {

            if($lp % 2 == 1) {
                echo '<tr class="alt">';
            } else {
                echo '<tr>';
            }


            echo "<td>".$lpToShow."</td>";


            echo "<td>".$row["agent"]."</td>";
            echo "<td>".$row["city"]."</td>";
            echo "<td>".$row["pharmacy"]."</td>";
            echo "<td>".$row["participantNumber"]."</td>";
            echo "<td>".$row["timeSpendInApp"]."</td>";
            echo "<td>".$row["serverDate"]."</td>";
            echo "<td>".$row["createDate"]."</td>";
            echo '</tr>';
		}
		$lp++;
		$lastRow = $lp;
    }
	echo '</tbody>';
	
	echo '</table></div>';

	?>
	
	<form action="showScoresCsv.php" method="post">
            <input type="hidden" name="pass" value="<?php echo $_POST["pass"]; ?>" />
           
            <input type="submit"  class="button" value="Pobierz CSV" class="button" />
        </form>

	<form action="showScores.php" method="post">
            <input type="hidden" name="pass" value="<?php echo $_POST["pass"]; ?>" />
            <input type="hidden" name="clean" value="<?php echo $lastRow ?>" />
            <input type="submit"  onclick="writeMsg();" class="button" value="Wyczyść" class="button" />

    </form>
	<?php
	 
} else {
    echo "0 results";
}



echo "</body></html>";
 $conn->close();
 ?> 