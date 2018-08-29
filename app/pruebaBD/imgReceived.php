<?php

$hostname_localhost="localhost";
$database_localhost="demo";
$username_localhost="student";
$password_localhost="student";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	
        $consulta="SELECT * FROM employees ORDER BY ID DESC LIMIT 1";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$result["id"]=$registro['id'];
			$nuevoId = $result["id"] +1;
		}else{$nuevoId=1;}
        
	
        $path = 'c:/xampp/htdocs/pruebaBD/imgUsuarios/Desconocido'.$nuevoId.'.jpg';
        $pathim = 'imgUsuarios/Desconocido'.$nuevoId.'.jpg';
        print_r($_FILES);
        move_uploaded_file($_FILES["file"]["tmp_name"], $path);
        
	$im = addslashes(file_get_contents($pathim));
        header("Content-type: image/jpeg");

	$last_name="Desconocido";
	$first_name="Desconocido";
	$sql="INSERT INTO employees(last_name,first_name,imagen) "
                        . "VALUES ('{$last_name}','{$first_name}','{$pathim}')";
	$resultado_insert=mysqli_query($conexion,$sql);
        
        $sqlUpdate= "update employees set salary=1";
	$resultado_insert=mysqli_query($conexion,$sqlUpdate);
        
        
     
	mysqli_close($conexion);
        
        
//print_r($_FILES);
//move_uploaded_file($_FILES["file"]["tmp_name"],'c:/xampp/htdocs/pruebaBD/imgUsuarios/descarga.jpg');