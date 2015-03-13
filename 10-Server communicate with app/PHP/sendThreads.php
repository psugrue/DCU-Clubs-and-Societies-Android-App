<?php

require("config.inc.php");

$query = "SELECT * FROM Poker";
$query_params= array();

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
	$response["success"]=1;
	$response["message"] = "Threads Available";
	$response["posts"] = array();

	foreach($rows as $row){
		$post = array();
		$post["post_id"] = $row["id"];
		$post["title"] = $row["title"];
		$post["message"] = $row["message"];

		array_push($response["posts"], $post);
	}

	echo json_encode($response);
}
else{
	$response["success"] = 0;
	$response["message"] = "No Threads Available";
	die(json_encode($response));
}



?>