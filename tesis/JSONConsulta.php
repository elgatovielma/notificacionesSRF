<?PHP
$hostname_localhost ="localhost";
$database_localhost ="tesis_sistemadeseguridad";
$username_localhost ="root";
$password_localhost ="";
$json=array();
	if(isset($_GET["usuario"]) && isset($_GET["password"])){
            
		//$documento=$_GET["documento"];
                $pass =$_GET['password'];
		$usuario=$_GET['usuario'];
				
		$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
		//$consulta="select * from employees where id= '{$documento}'";
                $consulta="select * from empleado where password= '{$pass}' &&  usuario='{$usuario}' && (nivel_id = 1 || nivel_id = 2)";
		$resultado=mysqli_query($conexion,$consulta);
			
		if($registro=mysqli_fetch_array($resultado)){
			$json['empleado'][]=$registro;
			//echo $registro['id'].' - '.$registro['last_name'].' - '.$registro['first_name'].'<br/>';
		}else{
			$resultar["id"]=0;
			$resultar["password"]='unknown';
			$resultar["usuario"]='unknown';
			$json['empleado'][]=$resultar;
		}
		
		mysqli_close($conexion);
		echo json_encode($json);
	}
	else{
		$resultar["success"]=0;
		$resultar["message"]='Ws no Retorna';
		$json['empleado'][]=$resultar;
		echo json_encode($json);
	}

