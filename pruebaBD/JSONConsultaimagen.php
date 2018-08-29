<?PHP
$hostname_localhost ="localhost";
$database_localhost ="demo";
$username_localhost ="student";
$password_localhost ="student";
$json=array();
	
				
	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="SELECT * FROM employees ORDER BY id DESC";
	$resultado=mysqli_query($conexion,$consulta);

	while($registro=mysqli_fetch_array($resultado)){
		$result["id"]=$registro['id'];
		$result["first_name"]=$registro['first_name'];
		$result["last_name"]=$registro['last_name'];
		$result["imagen"]=$registro['imagen'];
		$json['employees'][]=$result;
		//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
	}
		
	mysqli_close($conexion);
	echo json_encode($json);

