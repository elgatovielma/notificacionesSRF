<?PHP
$hostname_localhost="localhost";
$database_localhost="demo";
$username_localhost="student";
$password_localhost="student";
$json=array();
	if(isset($_GET["id"]) && isset($_GET["last_name"]) 
                && isset($_GET["first_name"]) && isset($_GET["email"]) 
                && isset($_GET["department"]) && isset($_GET["salary"])){
            
		$documento=$_GET['id'];
                $apellido =$_GET['last_name'];
		$nombre=$_GET['first_name'];
                $email=$_GET['email'];
		$profesion=$_GET['department'];
                $salario=$_GET['salary'];
		
		$conexion=mysqli_connect($hostname_localhost,$username_localhost,
                        $password_localhost,$database_localhost);
		
		$insert="INSERT INTO employees(id, last_name, first_name, email, "
                        . "department, salary) VALUES ('{$documento}','{$apellido}',"
                        . "'{$nombre}','{$email}','{$profesion}','{$salario}')";
                        
		$resultado_insert=mysqli_query($conexion,$insert);
		
		if($resultado_insert){
			$consulta="SELECT * FROM employees WHERE id = '{$documento}'";
			$resultado=mysqli_query($conexion,$consulta);
			
			if($registro=mysqli_fetch_array($resultado)){
				$json['employees'][]=$registro;
			}
			mysqli_close($conexion);
			echo json_encode($json);
		}
		else{
			$resulta["documento"]=0;
			$resulta["nombre"]='No Registras';
			$resulta["profesion"]='No Registra';
			$json['usuario'][]=$resulta;
			echo json_encode($json);
		}
		
	}
	else{
			$resulta["documento"]=0;
			$resulta["nombre"]='WS No retorna';
			$resulta["profesion"]='WS No retornas';
			$json['usuario'][]=$resulta;
			echo json_encode($json);
		}



