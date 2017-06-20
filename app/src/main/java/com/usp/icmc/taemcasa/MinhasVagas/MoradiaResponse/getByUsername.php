<?php
	$response = array();

	if(isset($_POST["username"])) {

		$con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

		$username = $_POST["username"];

		$statement = mysqli_prepare($con, "SELECT * FROM republica WHERE username = ?");
		mysqli_stmt_bind_param($statement, "s", $username);
		mysqli_stmt_execute($statement);

		mysqli_stmt_bind_result($statement, $id_rep, $username, $nome, $descricao, $rua, $numero, $complemento, $bairro, $cidade, $estado, $latitude, $longitude, $telefone, $link, $tipo, $perfil, $qtd_moradores, $aceita_animais);

		$response["data"] = array();
		$i = 0;
		while (mysqli_stmt_fetch($statement)) {
			$response["data"][$i]["id"] = $id_rep;
			$response["data"][$i]["username"] = $username;
			$response["data"][$i]["nome"] = $nome;
			$response["data"][$i]["descricao"] = $descricao;
			$response["data"][$i]["rua"] = $rua;
			$response["data"][$i]["numero"] = $numero;
			$response["data"][$i]["complemento"] = $complemento;
			$response["data"][$i]["bairro"] = $bairro;
			$response["data"][$i]["cidade"] = $cidade;
			$response["data"][$i]["estado"] = $estado;
			$response["data"][$i]["latitude"] = $latitude;
			$response["data"][$i]["longitude"] = $longitude;
			$response["data"][$i]["telefone"] = $telefone;
			$response["data"][$i]["link"] = $link;
			$response["data"][$i]["tipo"] = $tipo;
			$response["data"][$i]["perfil"] = $perfil;
			$response["data"][$i]["qtd_moradores"] = $qtd_moradores;
			$response["data"][$i]["aceita_animais"] = $aceita_animais;
			$i++;
		}

		$response['size'] = (string) $i;
		$response['success'] = true;

	} else {
		$response['success'] = false;
	}

	echo json_encode($response);
?>