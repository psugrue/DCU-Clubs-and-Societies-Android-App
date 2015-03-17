<?php

//load and connect to MySQL database stuff
require("config.inc.php");

function randomCode(){
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charLength = 62;
    $random = '';

    for($i =0;$i < 10; $i++){
        $random .= $characters[rand(0,$charLength - 1)];
    }

    return $random;

}


if (!empty($_POST)) {
    //gets user's info based off of a username.
    $query = "SELECT * FROM users WHERE email = :email";
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
            
		$user = $row['username'];
        $email = $row['email'];
        $str = randomCode();

        $headers = "MIME-Version: 1.0" . "\r\n";
        $headers .= "Content-type:text/html;charset=UTF-8" . "\r\n";

        // More headers
        $headers .= 'From: <paul.sugrue3@mail.dcu.ie>' . "\r\n";
        
        $to = $email;
        $subject = 'Reset Password';
        $message = $str;
        


        $query = "UPDATE users SET reset_code = :code WHERE username=:user";
    
        $query_params = array(
        ':user' => $user,
        ':code' => $message
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


        mail($to,$subject,$message,$headers);


				
        
    
    
    // If the user logged in successfully, then we send them to the private members-only page 
    // Otherwise, we display a login failed message and show the login form again 
   
        $response["success"] = 1;
        $response["message"] = "Email Sent";
        die(json_encode($response));
    }
    else {
        $response["success"] = 0;
        $response["message"] = "Invalid Credentials!";
        die(json_encode($response));
    }
} else {
?>
		<h1>Forgot Password</h1> 
		<form action="forgotPassword.php" method="post"> 
		    Email:<br /> 
		    <input type="text" name="email" text="email" /> 
		    <br /><br /> 
		    
		    <input type="submit" value="Submit" /> 
		</form>
	<?php
}

?> 
