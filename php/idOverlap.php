<?php
    $connect = mysqli_connect("localhost", "userID", "ftpPw", "ftpID");

    $id = $_POST["id"];
    $pwd = $_POST["pwd"];

    $statement = mysqli_prepare($connect, "SELECT id FROM USER WHERE id = ?");

    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID);

    $result = array();
    $result["success"] = true;

    while(mysqli_stmt_fetch($statement)){
      $result["success"] = false;
      $result["id"] = $id;
    }

    echo json_encode($result);
?>