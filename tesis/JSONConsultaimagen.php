<?PHP
$hostname_localhost ="localhost";
$database_localhost ="tesis_sistemadeseguridad";
$username_localhost ="root";
$password_localhost ="";
$json=array();
	
				
	$conexion = mysqli_connect($hostname_localhost,$username_localhost,$password_localhost,$database_localhost);
	$consulta="SELECT img.id, e.fecha_hora, emp.nombre, emp.apellido, img.nombreimagen " 
    ."FROM tesis_sistemadeseguridad.evento e "
    ."JOIN tesis_sistemadeseguridad.empleado emp ON (e.empleado_id = emp.id) "
    ."JOIN tesis_sistemadeseguridad.imagen img ON (img.empleado_id = emp.id) "
    ."WHERE img.principal = 1  AND e.restringido = 1 ORDER BY img.id DESC";
	$resultado=mysqli_query($conexion,$consulta);

	while($registro=mysqli_fetch_array($resultado)){
                $result["id"]=$registro['id'];
		$result["fecha_hora"]=$registro['fecha_hora'];
		$result["nombreimagen"]=$registro['nombreimagen'];
		$json['empleado'][]=$result;
		//echo $registro['id'].' - '.$registro['nombre'].'<br/>';
	}
		
	mysqli_close($conexion);
	echo json_encode($json);

