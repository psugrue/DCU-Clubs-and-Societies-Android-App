<?php

require("config.inc.php");


if (!empty($_POST)) {
    if(empty($_POST['title']) || empty($_POST['message'])){

    	$response['success']=0;
    	$response['message']="Please enter both a title and a message";

    	die(json_encode($response));
    }

    $query = "SELECT 1 from Threads WHERE title = :title AND society = :society";

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
		$date = date('m/d/Y h:i:s a', time());


		
    $query = "INSERT INTO Threads (society,title,message,created_by,creation_time) VALUES (:soc,:title, :message, :user, :time)";

    $query_params = array(
			':soc' => $_POST['society'],
    		':title' => $_POST['title'],
    		':message' => $_POST['message'],
			':user' => $_POST['username'],
			':time' => $date
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
    	$response["message"] = "Database Error3. Please Try Again.";
    	die(json_encode($response));
    }

    $row = $stmt->fetch();

    $data = $row["thread_id"];




    $query = "INSERT INTO Comments (society,thread_id,comment,created_by,creation_time) VALUES (:soc,:tID, :message, :user, :time)";

    $query_params = array(
			':soc' => $_POST['society'],
    		':tID' => $data,
    		':message' => $_POST['message'],
			':user' => $_POST['username'],
			':time' => $date
    	);

    try{
    	$stmt = $db->prepare($query);
    	$result = $stmt->execute($query_params);
    }
    catch(PDOException $ex){
    	$response["success"] = 0;
    	$response["message"] = "Database Error4. Please Try Again";
    	die(json_encode($response));
    }







    $response["success"] = 1;
    $response["message"] = "Thread Created Successfully";
    echo json_encode($response);


		
		
} else {
?>
	<h1>Create Thread</h1> 
	<form action="createThread.php" method="post"> 

		Society:<br /> 
	    <input type="text" name="society" value="" /> 
	    <br /><br /> 

	    Title:<br /> 
	    <input type="text" name="title" value="" /> 
	    <br /><br /> 
	    Message:<br /> 
	    <input type="text" name="message" value="" /> 
	    <br /><br /> 
	    User:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br />  
	    <input type="submit" value="Create New Thread" /> 
	</form>
	<?php
}

?>