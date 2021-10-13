<?php 
    $connect = mysqli_connect("localhost", "userID", "ftpPw", "ftpID");
    mysqli_query($connect,'SET NAMES utf8');
 
    $id = $_POST["id"];
    $name = $_POST["name"];
    $pwd = $_POST["pwd"];
 
    $statement = mysqli_prepare($connect, "INSERT INTO USER VALUES (?,?,?)");
    mysqli_stmt_bind_param($statement, "sss", $id, $pwd, $name);
    mysqli_stmt_execute($statement);
 
 
    $result = array();
    $result["success"] = true;
 
   
    echo json_encode($result);
?>