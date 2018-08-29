<?php
$hostname_localhost="localhost";
$database_localhost="demo";
$username_localhost="student";
$password_localhost="student";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	//$documento = $_POST["documento"];
	//$nombre = $_POST["nombre"];
	//$profesion = $_POST["profesion"];
	//$imagen = $_POST["imagen"];
	//$path = "imgUsuarios/nombre.jpg";
	//$url = "http://192.168.1.4/pruebaBD/$path";
	//$url = "c:/xampp/htdocs/pruebaBD/imgUsuarios/descarga-min.jpg";

        print_r($_FILES);
        move_uploaded_file($_FILES["file"]["tmp_name"],'c:/xampp/htdocs/pruebaBD/imgUsuarios/descarga-min.jpg');
    
	//file_put_contents("imgUsuarios/descarga-min.jpg",($_FILES["file"]["tmp_name"]));
        
	$im = addslashes(file_get_contents("imgUsuarios/descarga-min.jpg"));
        header("Content-type: image/jpeg");
 
	
	$a=9;
	$b="hola";
	$c="hola";
	$d="hola";
	$sql="INSERT INTO usuario(documento,nombre,profesion,imagen,ruta_imagen) "
                        . "VALUES ('{$a}','{$b}','{$c}','{$im}','{$d}')";
	$resultado_insert=mysqli_query($conexion,$sql);

      
	
	mysqli_close($conexion);