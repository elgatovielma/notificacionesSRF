<?PHP
$hostname_localhost ="localhost";
$database_localhost ="demo";
$username_localhost ="student";
$password_localhost ="student";
$json=array();
	if(isset($_GET["last_name"]) && isset($_GET["first_name"])){
            
		//$documento=$_GET["documento"];
                $apellido =$_GET['last_name'];
		$nombre=$_GET['first_name'];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		//$consulta="select * from employees where id= '{$documento}'";
                $consulta="select * from employees where last_name= '{$apellido}' &&  first_name='{$nombre}'";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$json['employees'][]=$registro;
			//echo $registro['id'].' - '.$registro['last_name'].' - '.$registro['first_name'].'<br/>';
		}else{
			$resultar["id"]=0;
			$resultar["last_name"]='unknown';
			$resultar["first_name"]='unknown';
			$json['employees'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['employees'][]=$resultar;
		echo json_encode($json);
	}

