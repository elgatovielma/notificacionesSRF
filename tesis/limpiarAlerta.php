<?php

$hostname_localhost="localhost";
$database_localhost="tesis_sistemadeseguridad";
$username_localhost="root";
$password_localhost="";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

        if(isset($_GET["id"])){
            
        $id = $_GET["id"];
        $consulta="update empleado set acceso=0 where empleado.id ='{$id}'";
		$resultado=mysqli_query($conexion,$consulta);
        }else{
            echo "NO FUNCIONA";
        }
        mysqli_close($conexion);
        