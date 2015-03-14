<?php
require("config.inc.php");


if (!empty($_POST)) {

	$query = "SELECT thread_id from Threads WHERE title = :title AND society = :society";

    $query_params = array(
			':title'=>$_POST['title'],
			':society' =>$_POST['society']
		);

    try{
    	$stmt = $db->prepare($query);
    	$result = $stmt->execute($query_params);
    }
    catch (PDOException $ex){
    	$response["success"] = 0;
    	$response["message"] = "Database Error1. Please Try Again.";
    	die(json_encode($response));
    }

    $row = $stmt->fetch();

    $data = $row["thread_id"];





	$query = "SELECT * FROM Comments WHERE society = :soc AND thread_id = :data" ;

	$query_params= array(
		':soc'=> $_POST['society'], 
		':data' => $data
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
		$response["message"] = "Comments Available";
		$response["posts"] = array();

		foreach($rows as $row){
			$post = array();
			$post["post_id"] = $row["comment_id"];
			$post["society"] = $row["society"];
			$post["message"] = $row["comment"];
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
		<form action="sendComments.php" method="post"> 
			 
		    Title:<br /> 
		    <input type="text" name="title" value="" /> 
		    <br /><br />

		    Society:<br /> 
		    <input type="text" name="society" value="" /> 
		    <br /><br /> 
		    
		    <input type="submit" value="Enter Society" /> 
		</form>
		<?php
}

?>