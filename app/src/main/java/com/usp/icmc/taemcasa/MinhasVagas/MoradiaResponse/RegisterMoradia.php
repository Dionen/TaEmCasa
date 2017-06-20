<?php
    $con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

    $username = $_POST["username"];
    $nome= $_POST["nome"];
    $descricao = $_POST["descricao"];
    $rua = $_POST["rua"];
    $numero = $_POST["numero"];
    $complemento = $_POST["complemento"];
    $bairro = $_POST["bairro"];
    $cidade = $_POST["cidade"];
    $estado = $_POST["estado"];
    $telefone = $_POST["telefone"];
    $tipo = $_POST["tipo"];
    $perfil = $_POST["perfil"];
    $qtd_moradores = $_POST["qtd_moradores"];
    $aceita_animais = $_POST["aceita_animais"];

    $statement = mysqli_prepare($con, "INSERT INTO republica (username, nome, descricao, rua, numero, complemento, bairro, cidade, estado, telefone, tipo, perfil, qtd_moradores, aceita_animais) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssssssssiiss", $username, $nome, $descricao, $rua, $numero, $complemento, $bairro, $cidade, $estado, $telefone, $tipo, $perfil, $qtd_moradores, $aceita_animais);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>