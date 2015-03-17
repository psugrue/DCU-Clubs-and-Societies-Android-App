<?php
require("config.inc.php");


//if (!empty($_POST)) {
$query = "SELECT * FROM Societies";

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

//echo $str;
$rows = $stmt->fetchAll();


if($rows){
	$response["success"]= 1;
	$response["message"] = "Threads Available";
	$response["posts"] = array();

	foreach($rows as $row){
		$post = array();
		
		$post["society"] = $row["Society"];
		$post["info"] = $row["ContactInfo"];

		array_push($response["posts"], $post);
	}

	echo json_encode($response);
}
else{
	$response["success"] = 0;
	$response["message"] = "No Threads Available";
	die(json_encode($response));
}
/*} else {
?>
	<h1>Society</h1> 
	<form action="sendSocList.php" method="post"> 
		 
	    Society:<br /> 
	    <input type="text" name="society" value="" /> 
	    <br /><br /> 
	    
	    <input type="submit" value="Enter Society" /> 
	</form>
	<?php
}
*/
?>