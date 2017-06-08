<?php
    $con = mysqli_connect("localhost", "id1693452_poovagas", "12345678", "id1693452_poovagas");

    $min_preco = $_POST["min_preco"];
    $max_preco = $_POST["max_preco"];
    $tipo = $_POST["tipo"];
    $individual = $_POST["individual"];
    $aceita_animais = $_POST["aceita_animais"];

    $tipo_sql = 0;
    $sql = "SELECT republica.nome, republica.rua, republica.numero, republica.complemento, republica.cidade, republica.estado, vaga.preco FROM vaga, republica WHERE vaga.id_rep = republica.id_rep AND vaga.individual = ? AND republica.aceita_animais = ?";
    if($tipo != 2){
        $sql .= " AND vaga.tipo = ?";
        $tipo_sql += 1;
    }
    if($min_preco != NULL){
        $sql .= " AND vaga.preco >= ?";
        $tipo_sql += 2;
    }
    if($max_preco != NULL){
        $sql .= " AND vaga.preco <= ?";
        $tipo_sql += 4;
    }
    $sql .= " ORDER BY vaga.preco, republica.nome ASC";


    $statement = mysqli_prepare($con, $sql);
    if($tipo_sql == 0){
        mysqli_stmt_bind_param($statement, "ii", $individual, $aceita_animais);
    }
    if($tipo_sql == 1){
        mysqli_stmt_bind_param($statement, "iii", $individual, $aceita_animais, $tipo);
    }
    if($tipo_sql == 2){
        mysqli_stmt_bind_param($statement, "iid", $individual, $aceita_animais, $min_preco);
    }
    if($tipo_sql == 3){
        mysqli_stmt_bind_param($statement, "iiid", $individual, $aceita_animais, $tipo, $min_preco);
    }
    if($tipo_sql == 4){
        mysqli_stmt_bind_param($statement, "iid", $individual, $aceita_animais, $max_preco);
    }
    if($tipo_sql == 5){
        mysqli_stmt_bind_param($statement, "iiid", $individual, $aceita_animais, $tipo, $max_preco);
    }
    if($tipo_sql == 6){
        mysqli_stmt_bind_param($statement, "iidd", $individual, $aceita_animais, $min_preco, $max_preco);
    }
    if($tipo_sql == 7){
        mysqli_stmt_bind_param($statement, "iiidd", $individual, $aceita_animais, $tipo, $min_preco, $max_preco);
    }

    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $nome, $rua, $numero, $complemento, $cidade, $estado, $preco);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)){
        $response[]["success"] = true;
        $response[]["republica.nome"] = $nome;
        $response[]["republica.rua"] = $rua;
        $response[]["republica.numero"] = $numero;
        $response[]["republica.complemento"] = $complemento;
        $response[]["republica.cidade"] = $cidade;
        $response[]["republica.estado"] = $estado;
        $response[]["vaga.preco"] = $preco;
    }

    echo json_encode($response);
?>