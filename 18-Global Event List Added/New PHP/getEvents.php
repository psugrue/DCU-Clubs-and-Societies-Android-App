<?php
require("config.inc.php");

//get all societies 
$query = "SELECT * FROM Events";

$query_params= array(	
);

try{
	$stmt = $db -> prepare($query);
	$result = $stmt->execute($query_params);
}
catch (PDOException $ex){
	$response["success"] = 0;
	$response["message"] = "Database Error";
	die(json_encode($response));
}

$rows = $stmt->fetchAll();


if($rows){
	$response["success"]= 1;
	$response["message"] = "Events Available";
	$response["posts"] = array();

	//store data in array
	foreach($rows as $row){
		$post = array();
		
		$post["society"] = $row["society"];
		$post["title"] = $row["title"];
		$post["message"] = $row["message"];
		$post["time"] = $row["creation_time"];
		$post["number"] = $row["attending"];


		array_push($response["posts"], $post);
	}

	//send data back to the app
	echo json_encode($response);
}
else{
	$response["success"] = 0;
	$response["message"] = "No Events Available";
	die(json_encode($response));
}

?>