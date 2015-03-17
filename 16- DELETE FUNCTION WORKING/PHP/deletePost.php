<?php

require("config.inc.php");


if (!empty($_POST)) {
    

    $query = "DELETE from Comments WHERE created_by = :user AND creation_time = :time AND comment = :com";

    $query_params = array(
			':user'=>$_POST['username'],
			':time' =>$_POST['time'],
            ':com' => $_POST['comment']
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
    $response["message"] = "Post Deleted Successfully";
    echo json_encode($response);


	
		
} else {
?>
	<h1>Delete Post</h1> 
	<form action="deletePost.php" method="post"> 


        Username:<br /> 
        <input type="text" name="username" value="" /> 
        <br /><br />

        Comment:<br /> 
        <input type="text" name="comment" value="" /> 
        <br /><br /> 


        Time:<br /> 
        <input type="text" name="time" value="" /> 
        <br /><br />

	    
	    <input type="submit" value="Delete Post." /> 
	</form>
	<?php
}

?>