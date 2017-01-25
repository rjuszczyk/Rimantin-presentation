<?php
require "config.php";

function escape($val) {
    $val = str_replace("'",'_', $val);
	$val = str_replace('*','_', $val);
	$val = str_replace(';','_', $val);
	return $val;
	
}
	
	// Create connection
	$conn = new mysqli($servername, $username, $password, $dbname );
    $conn->set_charset("utf8");

	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 

	$agent = $_POST['agent'];
    $city = $_POST['city'];
    $pharmacy = $_POST['pharmacy'];
    $participantNumber = $_POST['participantNumber'];
    $timeSpendInApp = $_POST['timeSpendInApp'];
    $createDate = $_POST['createDate'];

    $agent = escape($agent);
    $city = escape($city);
    $pharmacy = escape($pharmacy);
    $participantNumber = escape($participantNumber);
    $timeSpendInApp = escape($timeSpendInApp);
    $createDate = escape($createDate);


	$sql = "
		INSERT INTO $tableName (
			agent,
            city,
            pharmacy,
            participantNumber,
            timeSpendInApp,
            createDate
		) VALUES (
			'$agent',
			'$city',
			'$pharmacy',
			'$participantNumber',
			'$timeSpendInApp',
			'$createDate'
		)";

	if ($conn->query($sql) === TRUE) {
		echo "OK";
	} else {
		echo "Error: " . $sql . "<br>" . $conn->error;
	}
	$conn->close();
?>