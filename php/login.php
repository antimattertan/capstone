<?php
    $connect = mysqli_connect("localhost", "userID", "ftpPw", "ftpID");
    mysqli_query($connect,'SET NAMES utf8');
 
    $id = $_POST["id"];
    $pwd = $_POST["pwd"];
    
    $statement = mysqli_prepare($connect, "SELECT * FROM User WHERE id = ? AND pwd = ?");
    mysqli_stmt_bind_param($statement, "ss", $id, $pwd);
    mysqli_stmt_execute($statement);
 
 
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $name, $pwd);
 
    $result = array();
    $result["success"] = true;
 
    while(mysqli_stmt_fetch($statement)) {
        $result["success"] = true;
        $result["id"] = $id;
        $result["name"] = $name;
        $result["pwd"] = $pwd;
    }
 
    echo json_encode($result);
?>
