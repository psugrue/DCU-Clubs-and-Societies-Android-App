<?php

require("config.inc.php");


if (!empty($_POST)) {
    if(empty($_POST['receiver']) || empty($_POST['message'])){

    	$response['success']=0;
    	$response['message']="Please enter both a username and a message";

    	die(json_encode($response));
    }


    $timezone = date_default_timezone_get();
    date_default_timezone_set('Europe/Dublin');
    $date = date('m/d/Y h:i:s a', time());


    $query = "INSERT INTO Messages(sender,receiver,message,time) VALUES (:send,:user,:mess,:time)";

    $query_params = array(
			':send'=>$_POST['username'],
			':user' =>$_POST['receiver'],
            ':mess' => $_POST['message'],
            ':time' => $date
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



    $response["success"] = 1;
    $response["message"] = "Message Sent Successfully";
    echo json_encode($response);		
		
} else {
?>
	<h1>Send Message</h1> 
	<form action="sendMessage.php" method="post"> 

		Username:<br /> 
	    <input type="text" name="receiver" value="" /> 
	    <br /><br /> 
 
	    Message:<br /> 
	    <input type="text" name="message" value="" /> 
	    <br /><br /> 

	    Sender:<br /> 
	    <input type="text" name="username" value="" /> 
	    <br /><br />  
	    <input type="submit" value="Send Message" /> 
	</form>
	<?php
}

?>