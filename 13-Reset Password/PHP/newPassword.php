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


    $p1 = $_POST['password1'];
    $p2 = $_POST['password2'];

    if($p1!=$p2){
        $response["success"] = 0;
        $response["message"] = "Passwords didnt match.";
        die(json_encode($response));
    }


    $query = "UPDATE  users SET encrypt_password = :pass WHERE email = :email ";
        
    $hash = hashEncrypt($p1);
   
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
        ':email' => $_POST['email'],
        ':pass' => $hash,

                
    );
    
    //time to run our query, and create the user
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error2. Please Try Again!";
        die(json_encode($response));
    }



    $query = "UPDATE  users SET reset_code = :code WHERE email = :email ";
    
   $empty = "";
    
    //Again, we need to update our tokens with the actual data:
    $query_params = array(
        ':email' => $_POST['email'],
        ':code' => $empty
    );
    
    //time to run our query, and create the user
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute($query_params);
    }
    catch (PDOException $ex) {
        // For testing, you could use a die and message. 
        //die("Failed to run query: " . $ex->getMessage());
        
        //or just use this use this one:
        $response["success"] = 0;
        $response["message"] = "Database Error3. Please Try Again!";
        die(json_encode($response));
    }
    
    //If we have made it this far without dying, we have successfully added
    //a new user to our database.  We could do a few things here, such as 
    //redirect to the login page.  Instead we are going to echo out some
    //json data that will be read by the Android application, which will login
    //the user (or redirect to a different activity, I'm not sure yet..)
    $response["success"] = 1;
    $response["message"] = "Password Successfully Changed!";
    echo json_encode($response);



} else {
?>
		<h1>Enter Password</h1> 
		<form action="newPassword.php" method="post"> 

            Email:<br /> 
            <input type="text" name="email" text="" /> 
            <br /><br />

		    Password:<br /> 
            <input type="text" name="password1" text="" /> 
            <br /><br />


            Confirm Password:<br /> 
		    <input type="text" name="password2" text="" /> 
		    <br /><br />
 
		    
		    <input type="submit" value="Submit" /> 
		</form>
	<?php
}

?> 
