<?php
    $fname = $_POST["firstname"];
    $lname = $_POST["lastname"];
    $user_email = $_POST['emailaddress'];
    $message = $_POST['content'];

    $email_from = "kotch97@hotmail.com";
    $email_subject = "New Contact Form";
    $email_body = "User Name: $fname " "$lname\n".
                    "User Email: $user_email.\n".
                    "User Message: $message.\n";
    $to = "kostasergakis@gmail.com";

    $headers = "From: $email_from \r\n";
    $headers .= "Reply-To: $user_email \r\n"

    mail($to, $email_subject, $email_body, $headers);
    header("Location: contactUs.html");

?>