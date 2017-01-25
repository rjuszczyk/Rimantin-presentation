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
spróbuj jeszcze raz... 2 <?php echo $_POST["pass"]  ?>
<form action="showScoresCsv.php" method="post">
Haslo: <input type="password" name="pass"><br>

<input type="submit">
</form>

</body>
</html>

<?php
die();
}

header('Content-Type: text/csv; charset=utf-8');
header('Content-Disposition: attachment; filename=data.csv');

$rowsFrom = 0;
if(file_exists("removed.txt")) {
    $contentOfFile = file_get_contents("removed.txt");
    $rowsFrom = intval($contentOfFile);
}

// create a file pointer connected to the output stream
$output = fopen('php://output', 'w');

// output the column headings
fputcsv($output, array(
		'lp',
		'przedstawiciel',
		'miejscowość',
		'apteka',
		'liczba uczestników',
		'czas oglądania',
		'data wysłania',
		'data utworzenia'
 ));


// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
$conn->set_charset("utf8");
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
 } 

$sql = "SELECT * FROM $tableName";
$result = $conn->query($sql);


if ($result->num_rows > 0) {
$lp = 0;	
    while($row = $result->fetch_assoc()) {

		$lpToShow = $lp-$rowsFrom;

		$row = array(
			$lp,
			$row["agent"],
            $row["city"],
            $row["pharmacy"],
            $row["participantNumber"],
            $row["timeSpendInApp"],
            $row["serverDate"],
            $row["createDate"],
			
			);	
			$lp++;

        if($lpToShow>=0) {
    		fputcsv($output, $row);
		}
		
    //    echo "id: " . $row["id"]. " - Name: " . $row["first_name"]. " " . $row["last_name"]. "<br>";
    }
	
} 


$conn->close();

?>