<?php

$hostname_localhost="localhost";
$database_localhost="tesis_sistemadeseguridad";
$username_localhost="root";
$password_localhost="";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,
        $password_localhost,$database_localhost);

if(isset($_GET["id"])){
    
    $id = $_GET["id"];
    
    $sqlEvento="INSERT INTO evento(restringido,empleado_id) "
                        . "VALUES ('0','{$id}')";
	mysqli_query($conexion,$sqlEvento);
	mysqli_close($conexion);
}else{
    echo "No existe el id";
}