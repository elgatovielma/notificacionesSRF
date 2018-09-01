<?php

$hostname_localhost="localhost";
$database_localhost="tesis_sistemadeseguridad";
$username_localhost="root";
$password_localhost="";
$conexion=mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);

        $last_name="Desconocido";
	$first_name="Desconocido";
	$sql="INSERT INTO empleado(nombre,apellido,nivel_id) "
                        . "VALUES ('{$first_name}','{$last_name}','3')";
	$resultado_insert=mysqli_query($conexion,$sql);
	
        $consulta="SELECT * FROM empleado ORDER BY id DESC LIMIT 1";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$result["id"]=$registro['id'];
		}else{$Id=1;}
        
        $path = 'c:/xampp/htdocs/tesis/imgUsuarios/Desconocido'.$result["id"].'.jpg';
        $pathim = 'imgUsuarios/Desconocido'.$result["id"].'.jpg';
        print_r($_FILES);
        move_uploaded_file($_FILES["file"]["tmp_name"], $path);
        
	$im = addslashes(file_get_contents($pathim));
        header("Content-type: image/jpeg");
        
        
        $sqlImagen="INSERT INTO imagen(nombreImagen,principal,empleado_id) "
                    . "VALUES ('{$pathim}','1','{$result["id"]}')";
        mysqli_query($conexion,$sqlImagen);
        
        
        $sqlUpdate= "update empleado set acceso=1";
	mysqli_query($conexion,$sqlUpdate);
        $sqlEvento="INSERT INTO evento(restringido,empleado_id) "
                        . "VALUES ('1','{$result["id"]}')";
	mysqli_query($conexion,$sqlEvento);
        
     
	mysqli_close($conexion);
        
        
//print_r($_FILES);
//move_uploaded_file($_FILES["file"]["tmp_name"],'c:/xampp/htdocs/pruebaBD/imgUsuarios/descarga.jpg');