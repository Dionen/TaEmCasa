<?php
    $con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

    $email = $_POST["email"];

    $statement = mysqli_prepare($con, "SELECT * FROM users WHERE email = ?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $user_id, $nome, $sobrenome, $email, $salt, $hash);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["user_id"] = $user_id;
        $response["nome"] = $nome;
        $response["sobrenome"] = $sobrenome;
        $response["salt"] = $salt;
        $response["hash"] = $hash;
    }

    echo json_encode($response);
?>