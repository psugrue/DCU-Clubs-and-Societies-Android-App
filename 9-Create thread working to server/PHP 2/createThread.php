<?php

require("config.inc.php");


if (!empty($_POST)) {
    if(empty($_POST['title']) || empty($_POST['message'])){

    	$response['success']=0;
    	$response['message']="Please enter both a title and a message";

    	die(json_encode($response));
    }

    $query = "SELECT 1 from Poker WHERE title = :title";

    $query_params = array(':title'=>$_POST['title']);

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

    if($row){
    	$response["success"] = 0;
    	$response["message"] = "Please choose another title. That title is currently in use";
    	die(json_encode($response));
    }

    $query = "INSERT INTO Poker (title,message) VALUES (:title, :message)";

    $query_params = array(
    	':title' => $_POST['title'],
    	':message' => $_POST['message']
    	);

    try{
    	$stmt = $db->prepare($query);
    	$result = $stmt->execute($query_params);
    }
    catch(PDOException $ex){
    	$response["success"] = 0;
    	$response["message"] = "Database Error2. Please Try Again";
    	die(json_encode($response));
    }

    $response["success"] = 1;
    $response["message"] = "Thread Created Successfully";
    echo json_encode($response);


		
		
} else {
?>
	<h1>Create Thread</h1> 
	<form action="createThread.php" method="post"> 
		 
	    Title:<br /> 
	    <input type="text" name="title" value="" /> 
	    <br /><br /> 
	    Message:<br /> 
	    <input type="text" name="message" value="" /> 
	    <br /><br /> 
	    <input type="submit" value="Create New Thread" /> 
	</form>
	<?php
}

?>