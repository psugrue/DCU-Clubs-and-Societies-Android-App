<?php

require("config.inc.php");


if (!empty($_POST)) {
    if(empty($_POST['comment'])){

    	$response['success']=0;
    	$response['message']="Please enter a comment";

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
    	$response["message"] = "Database Error1. Please Try Again.";
    	die(json_encode($response));
    }

    $row = $stmt->fetch();

    $data = $row["thread_id"];

   

    echo $data;

    

	$timezone = date_default_timezone_get();
	date_default_timezone_set('Europe/Dublin');
	$date = date('m/d/Y h:i:s a', time());
		
    $query = "INSERT INTO Comments (society,thread_id,comment,created_by,creation_time) VALUES (:soc,:threadID, :comment, :user, :time)";

    $query_params = array(
			':soc' => $_POST['society'],
    		':threadID' => $data,
    		':comment' => $_POST['comment'],
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

    $response["success"] = 1;
    $response["message"] = "Comment Created Successfully";
    echo json_encode($response);


	
		
} else {
?>
	<h1>Create Comment</h1> 
	<form action="createComment.php" method="post"> 

        Society:<br /> 
        <input type="text" name="society" value="" /> 
        <br /><br />

        Title:<br /> 
        <input type="text" name="title" value="" /> 
        <br /><br />

		Comment:<br /> 
	    <input type="text" name="comment" value="" /> 
	    <br /><br /> 

        Username:<br /> 
        <input type="text" name="username" value="" /> 
        <br /><br />

	    
	    <input type="submit" value="Submit comment." /> 
	</form>
	<?php
}

?>