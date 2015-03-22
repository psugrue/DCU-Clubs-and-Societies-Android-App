<?php
require("config.inc.php");

if (!empty($_POST)) {

    $query = "SELECT * FROM Events WHERE creation_time = :time";
    $query_params = array(
        ':time' => $_POST['time']
    );
    
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one to product JSON data:
        $response["success"] = 0;
        $response["message"] = "Database Error1. Please Try Again!";
        die(json_encode($response));
        
    }
    
    
  
    $row = $stmt->fetch();

    $data2 = $row['attending'];

    $data = $data2 + 1;

        $query = "UPDATE Events SET attending = :num WHERE creation_time=:time";
    
        $query_params = array(
        ':num' => $data,
        ':time' => $_POST['time']
        );
    
        //time to run our query, and create the user
        try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
         }
           catch (PDOException $ex) {

        $response["success"] = 0;
        $response["message"] = "Database Error2. Please Try Again!";
        die(json_encode($response));
    }


        $response["success"] = 1;
        $response["message"] = "Number updated";
        die(json_encode($response));
    }
    
else {
?>
		<h1>Update Event</h1> 
		<form action="updateEvent.php" method="post"> 
		    Time:<br /> 
		    <input type="text" name="time" text="" /> 
		    <br /><br /> 
		    
		    <input type="submit" value="Submit" /> 
		</form>
	<?php
}

?> 