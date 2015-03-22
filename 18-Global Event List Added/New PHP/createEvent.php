<?php

require("config.inc.php");


if (!empty($_POST)) {
    if(empty($_POST['title']) || empty($_POST['message']) || empty($_POST['number'])){

    	$response['success']=0;
    	$response['message']="Please enter both a title, a message and the number attending";

    	die(json_encode($response));
    }

    $query = "SELECT 1 from Events WHERE title = :title AND society = :society";

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

    if($row){
    	$response["success"] = 0;
    	$response["message"] = "Please choose another title. That title is currently in use";
    	die(json_encode($response));
    }

		$timezone = date_default_timezone_get();
		date_default_timezone_set('Europe/Dublin');
		$date = date('d/m/Y h:i:s a', time());


		
    $query = "INSERT INTO Events (society,title,message,creation_time,attending) VALUES (:soc,:title, :message,:time,:num)";

    $query_params = array(
			':soc' => $_POST['society'],
    		':title' => $_POST['title'],
    		':message' => $_POST['message'],
			':time' => $date,
            ':num' => $_POST['number']
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
    $response["message"] = "Event Created Successfully";
    echo json_encode($response);


		
		
} else {
?>
	<h1>Create Event</h1> 
	<form action="createEvent.php" method="post"> 

		Society:<br /> 
	    <input type="text" name="society" value="" /> 
	    <br /><br /> 

	    Title:<br /> 
	    <input type="text" name="title" value="" /> 
	    <br /><br /> 

	    Message:<br /> 
	    <input type="text" name="message" value="" /> 
	    <br /><br /> 

	    Numbers Attending:<br /> 
	    <input type="int" name="number" value="" /> 
	    <br /><br />  
	    <input type="submit" value="Create New Event" /> 
	</form>
	<?php
}

?>