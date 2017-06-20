<?php

    $con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

    $id_rep = $_POST["id_rep"];

        $statement = mysqli_prepare($con, "SELECT * FROM republica WHERE id_rep = ?");
        mysqli_stmt_bind_param($statement, "s", $id_rep);
        mysqli_stmt_execute($statement);

        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $username, $nome, $descricao, $rua, $numero, $complemento, $bairro, $cidade, $estado, $telefone, $tipo, $perfil, $qtd_moradores, $aceita_animais, $qtd_vagas, &preco);

        $response = array();
        $response["success"] = false;

        while(mysqli_stmt_fetch($statement)){
            $response["success"] = true;
            $response["username"] = $username;
            $response["nome"] = $nome;
            $response["descricao"] = $descricao;
            $response["rua"] = $rua;
            $response["numero"] = $numero;
            $response["complemento"] = $complemento;
            $response["bairro"] = $bairro;
            $response["cidade"] = $cidade;
            $response["estado"] = $estado;
            $response["telefone"] = $telefone;
            $response["tipo"] = $tipo;
            $response["perfil"] = $perfil;
            $response["qtd_moradores"] = $qtd_moradores;
            $response["aceita_animais"] = $aceita_animais;
            $response["qtd_vagas"] = $qtd_vagas;
            $response["preco"] = $preco;
        }

        echo json_encode($response);
?>