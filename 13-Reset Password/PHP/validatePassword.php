<?php

//load and connect to MySQL database stuff
require("config.inc.php");


function hashEncrypt($code) {
                
                $options = [
                        'cost' =>11,
                ];
                
                $hash = password_hash($code, PASSWORD_BCRYPT, $options);
                return $hash;
    }


if (!empty($_POST)) {


    $code_ok = false;
    $data = $_POST['code'];



    //gets user's info based off of a username.
    $query = "SELECT reset_code FROM users WHERE email = :email";
    $query_params = array(
        ':email' => $_POST['email']
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


    if ($row) {
            
		$sqlcode = $row['reset_code'];
        $password=$_POST['code'];
        if($sqlcode = $password){
            $code_ok=true;
        }

   
    }

    if($code_ok){
        $response["success"] = 1;
        $response["message"] = "Code Okay";
        die(json_encode($response));
    }
    else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
} else {
?>
		<h1>Enter Code</h1> 
		<form action="validatePassword.php" method="post"> 
		    Email:<br /> 
            <input type="text" name="email" text="" /> 
            <br /><br />


            Code:<br /> 
		    <input type="text" name="code" text="" /> 
		    <br /><br />
 
		    
		    <input type="submit" value="Submit" /> 
		</form>
	<?php
}

?> 
