<?php
    $con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

    $nome = $_POST["nome"];
    $sobrenome = $_POST["sobrenome"];
    $email = $_POST["email"];
    $salt = $_POST["salt"];
    $hash = $_POST["hash"];

    $statement2 = mysqli_prepare($con, "SELECT user_id FROM users WHERE email = ?");
    mysqli_stmt_bind_param($statement2, "s", $email);
    mysqli_stmt_execute($statement2);

    mysqli_stmt_store_result($statement2);

    $response = array();
    $response["success"] = false;

    if(mysqli_stmt_fetch($statement2)){
    } else {
        $statement = mysqli_prepare($con, "INSERT INTO users (nome, sobrenome, email, salt, hash) VALUES (?, ?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement, "sssss", $nome, $sobrenome, $email, $salt, $hash);
        mysqli_stmt_execute($statement);

        $response["success"] = true;
    }
    
    echo json_encode($response);
?>
