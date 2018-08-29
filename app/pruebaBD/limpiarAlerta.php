<?php

$hostname_localhost="localhost";
$database_localhost="demo";
$username_localhost="student";
$password_localhost="student";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

        if(isset($_GET["id"])){
            
        $id = $_GET["id"];
        $consulta="update employees set salary=0 where employees.id ='{$id}'";
		$resultado=mysqli_query($conexion,$consulta);
        }else{
            echo "NO FUNCIONA";
        }
        mysqli_close($conexion);
        