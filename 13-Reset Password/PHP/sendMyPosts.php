<?php
require("config.inc.php");


if (!empty($_POST)) {
$query = "SELECT * FROM Comments WHERE created_by = :user";

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
	$response["message"] = "Threads Available";
	$response["posts"] = array();

	foreach($rows as $row){
		$post = array();
		$post["comment"] = $row["comment"];
		$post["society"] = $row["society"];
		$post["time"] = $row["creation_time"];
		$data = $row["thread_id"];

		$query2 = "SELECT title FROM Threads WHERE thread_id = :data ";

		$query_params2 = array(
				':data' => $data
			);

		try{
			$stmt2 = $db -> prepare($query2);
			$result2 = $stmt2->execute($query_params2);
		}
		catch (PDOException $ex){
			$response["success"] = 0;
			$response["message"] = "Database Error2";
			die(json_encode($response));
		}

		$row2 = $stmt2->fetch();
		$title = $row2["title"];

		$post["title"] = $title;

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
	<h1>Username</h1> 
	<form action="sendMyPosts.php" method="post"> 
		 
	    Username:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br /> 
	    
	    <input type="submit" value="Enter Username" /> 
	</form>
	<?php
}

?>