<?php
	$response = array();

	if(isset($_POST["username"])) {

		$con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

		$username = $_POST["username"];

		$statement = mysqli_prepare($con, "SELECT * FROM republica WHERE username = ?");
		mysqli_stmt_bind_param($statement, "s", $username);
		mysqli_stmt_execute($statement);

	    mysqli_stmt_bind_result($statement, $id_rep, $username, $nome, $descricao, $rua, $numero, $complemento, $bairro, $cidade, $estado, $latitude, $longitude, $telefone, $link, $tipo, $perfil, $qtd_moradores, $aceita_animais);

       	$i = 0;
        while (mysqli_stmt_fetch($statement)) {
            $response[$i]["id"] = $id_rep;
            $response[$i]["username"] = $username;
            $response[$i]["nome"] = $nome;
            $response[$i]["descricao"] = $descricao;
            $response[$i]["rua"] = $rua;
            $response[$i]["numero"] = $numero;
            $response[$i]["complemento"] = $complemento;
            $response[$i]["bairro"] = $bairro;
            $response[$i]["cidade"] = $cidade;
            $response[$i]["estado"] = $estado;
            $response[$i]["latitude"] = $latitude;
            $response[$i]["longitude"] = $longitude;
            $response[$i]["telefone"] = $telefone;
            $response[$i]["link"] = $link;
            $response[$i]["tipo"] = $tipo;
            $response[$i]["perfil"] = $perfil;
            $response[$i]["qtd_moradores"] = $qtd_moradores;
            $response[$i]["aceita_animais"] = $aceita_animais;
        	$i++;
        }

        $response['success'] = true;

	} else {
	    $response['success'] = false;
	}

	echo json_encode($response);
?>