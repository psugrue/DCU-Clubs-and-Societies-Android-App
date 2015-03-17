<?php
require("config.inc.php");


if (!empty($_POST)) {
$query = "SELECT * FROM Messages WHERE sender = :user";

$query_params= array(
	':user'=> $_POST['username'] 
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
	$response["message"] = "Messages Available";
	$response["posts"] = array();

	foreach($rows as $row){
		$post = array();

		$post["message"] = $row["message"];
		$post["receiver"] = $row["receiver"];
		$post["time"] = $row["time"];

		array_push($response["posts"], $post);
	}

	echo json_encode($response);
}
else{
	$response["success"] = 0;
	$response["message"] = "No Messages Available";
	die(json_encode($response));
}
} else {
?>
	<h1>Messages</h1> 
	<form action="getSentMessages.php" method="post"> 
		 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    
	    <input type="submit" value="Enter Username" /> 
	</form>
	<?php
}

?>