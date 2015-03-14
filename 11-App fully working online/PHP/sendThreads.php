<?php
require("config.inc.php");


if (!empty($_POST)) {
$query = "SELECT * FROM Threads WHERE society = :soc";

$query_params= array(
	':soc'=> $_POST['society'] 
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
		$post["post_id"] = $row["thread_id"];
		$post["society"] = $row["society"];
		$post["title"] = $row["title"];
		$post["message"] = $row["message"];
		$post["user"] = $row["created_by"];

		array_push($response["posts"], $post);
	}

	echo json_encode($response);
}
else{
	$response["success"] = 0;
	$response["message"] = "No Threads Available";
	die(json_encode($response));
}
} else {
?>
	<h1>Society</h1> 
	<form action="sendThreads.php" method="post"> 
		 
	    Society:<br /> 
	    <input type="text" name="society" value="" /> 
	    <br /><br /> 
	    
	    <input type="submit" value="Enter Society" /> 
	</form>
	<?php
}

?>