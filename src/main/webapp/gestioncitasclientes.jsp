<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, gestioncitas.*"%>
<%@ page import="java.time.LocalTime"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Date"%>

<!DOCTYPE html>
<html lang="es">

<head>
<title>Área Cliente</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Estilos bootstrap -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Agregamos iconos de bootstrap -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
<!-- Implementacion alertify -->
<script
	src="https://cdn.jsdelivr.net/npm/alertifyjs/build/alertify.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/alertifyjs/build/css/alertify.min.css" />

<style>
@import
	url('https://fonts.googleapis.com/css2?family=Pacifico&family=Shantell+Sans:ital,wght@0,300;1,300&display=swap')
	;

body {
	background-image:
		url("https://marketplace.canva.com/EAD2962NKnQ/2/0/1600w/canva-rainbow-gradient-pink-and-purple-virtual-background-_Tcjok-d9b4.jpg");
	background-repeat: 100% no-repeat;
	background-size: 100% auto;
	height: 100%;
}

.fondo:hover {
	background-color: #4bf5d9;
	color: #000;
}

.trans {
	opacity: 0.8;
	color: #fff;
}

.trans:hover {
	opacity: 0.9;
	font-size: larger;
	font-weight: 200;
}

h5 {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #fff;
	text-shadow: #5ef0f2 3px 6px 4px;
	text-align: center;
	font-size: 90%;
	font-weight: 80;
}

span {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #fff;
	text-shadow: #5ef0f2 3px 6px 4px;
	text-align: center;
	font-size: 90%;
	font-weight: 80;
}

h1, h3, h4, p {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #000;
	text-shadow: #fff 3px 6px 4px;
	text-align: center;
}

.calendar {
	border: 1px solid #ddd;
	background-color: #DBF7FD;
	padding: 0px;
	padding-left: 76px;
	padding-right: 60px;
}

.d-flex {
	display: -ms-flexbox !important;
	display: flex !important;
	flex-direction: column;
}

.hour {
	border: 1px solid #ddd;
	padding: 0px;
	padding-left: 76px;
	padding-right: 60px;
}

.boton {
	align-items: right;
}
</style>
<%
String usuario = (String) session.getAttribute("usuario");
int idCliente = (int) session.getAttribute("idCliente");
if (usuario == null) {
	response.sendRedirect("./login.jsp");
}
%>

</head>

<body>
	<nav class="navbar navbar-expand-lg bg-dark fixed-top">
		<div class="container-fluid ">
			<a class="navbar-brand h5" href="gestioncitasclientes.jsp"><span>Área
					Cliente</span></a>
			<div class=" d-flex justify-content-end" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item "><a class="nav-link letra"
						href="logout.jsp">
							<button name="todo" value="logout" class="btn btn-danger boton">
								Cerrar Sesión <i class="bi bi-power"></i>
							</button>
					</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<br />
	<%="<h3> Bienvenid@ " + usuario + "</h3>"%>
	<%
	//Mensaje por defecto
	String mensaje = "Aún no tiene citas.";
	if (mensaje != null) {
		/* out.println(mensaje); */
		request.removeAttribute("response");
	}
	%>
	<div class="justify-content-center text-center">
		<%
		String message = (String) request.getAttribute("cita");
		if (message != null) {
			out.println(message);
			request.removeAttribute("cita");
		}
		%>
		<!-- <input type="hidden" name="todo" value="areacliente"> -->
		<div class="alert alert-info" role="alert">
			<table class="table table-hover">
				<tr>
					<th class='text-center'>Próxima cita</th>
				</tr>
				<%
				Gestiones gestiones = new Gestiones();//objeto clase gestiones
				ArrayList<Servicios> listaServicios = gestiones.getServicios();
				ArrayList<Citas> listadoCitas = gestiones.getInfoCitas(idCliente);
				boolean tieneCita = false;

				for (int j = 0; j < listadoCitas.size(); j++) {
					Citas cita = listadoCitas.get(j);
					Servicios servicio = listaServicios.get(j);
					//buscamos el idCliente para saber si tiene cita
					if (cita.getIdClienteFK() == idCliente) {

						/* Formateamos fecha con split */
						String fecha = cita.getFechaCita().toString();
						String[] partesFecha = fecha.split("-");
						/* Formateamos hora */
						String hora = cita.getHoraCita().toString();
						String[] partesHora = hora.split(":");

						mensaje = "Su próxima cita es <b>" + partesFecha[2] + "-" + partesFecha[1] + "-" + partesFecha[0]
						+ "</b> a las <b>" + partesHora[0] + ":" + partesHora[1] + " h</b> para " + servicio.getTipoServicio();
						tieneCita = true;
						break; // Si encontramos la cita del cliente, podemos salir del bucle
					}
				}
				//Si no tiene informamos también
				if (!tieneCita) {
					mensaje = "Aún no tiene citas.";
				}

				request.setAttribute("response", mensaje);
				%>
				<tr>
					<!-- Mostramos mensaje -->
					<td><%=mensaje%></td>
				</tr>

			</table>
		</div>
	</div>
	<hr>
	<form name="formulario" action="controlador" method="POST">
		<input type="hidden" name="todo" value="formulario">
		<div class="justify-content-center text-center pt-4">
			<h3>Reserva una cita</h3>
			<div class="text-center form-floating">
				<label class="color:#000;" for="idServicio">Seleccione un
					tratamiento/servicio:</label><br> <select class="form-select"
					name="idServicio" id="idServicio"
					aria-label="Floating label select example">
					<!-- código java  -->
					<%
					ArrayList<Citas> horasDisponibles = (ArrayList<Citas>) session.getAttribute("elCalendario");
					Gestiones servicio = new Gestiones();//Creamos objeto de la clase modelo gestiones donde creamos el método
					ArrayList<Servicios> listaservicios = new ArrayList<Servicios>();
					//servicio.servicios(); //Llamamos al método para sacar la información de servicios de la base de datos
					Servicios infoServicio = new Servicios();//Creamos objeto de la clase servicios
					servicio.getServicios();
					//Creamos bucle para mostrar todos los servicios disponibles
					for (int i = 0; i < servicio.tamano(); i++) {
					%>

					<option class="text-center" value="<%=servicio.getIdServicio(i)%>">
						<!-- Mostramos solo los de interés, precio y nombre del servicio -->
						<%=servicio.getTipoServicio(i) + " " + servicio.getPrecioServicio(i) + "0 € "%>
						<%
						}
						%>
					</option>
				</select>
			</div>
		</div>
		<br>
		<!-- 	</section> -->
		<!-- Mostramos calendario y fecha -->
		<!-- <input type="hidden" name="todo" value="dias"> -->
		<div class="my-5 justify-content-center text-center">
			<div class="row">
				<!-- <div class="col-md-6 mx-auto"> -->
				<div class="calendar col-md-6 mx-auto">

					<div class="calendar-header">
						<%
						/* Controlador formato = new Controlador(); */
						Date fechaActual = new Date();
						%>
						<h4>Calendario 2023</h4>
						<!-- <p>Días disponible:</p> -->
						<b><h4>
								Hoy:<br>
								<%=fechaActual%></h4></b>
						<%
						// Obtener el número de días del mes actual
						Gestiones mes = new Gestiones();
						mes.calendario();
						Calendar cal = Calendar.getInstance();
						int numDias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
						int mesActual = cal.get(Calendar.MONTH) + 1;
						%>


						<div class="btn-group">
							<button type="button" class="btn btn-default">
								<i class="glyphicon glyphicon-chevron-left"></i>
							</button>
							<button type="button" class="btn btn-default">
								<i class="glyphicon glyphicon-calendar"></i>
							</button>
							<button type="button" class="btn btn-default">
								<i class="glyphicon glyphicon-chevron-right"></i>
							</button>
						</div>
					</div>
					<div>

						<%
						message = (String) request.getAttribute("error");
						if (message != null) {

							out.println(message);

						}
						%>
						<br> <label for="date">Seleccione el día:</label> <br> <input
							type="date" class="calendario " name="data-dia" value="data-dia"
							required />
					</div>
					<br> <label> Seleccionar una hora:</label> <br> <select
						class="hour" name="data-horas" id="data-horas">
						<%
						int dia = 0;
						Gestiones horasDia = new Gestiones();
						horasDia.horario();
						horasDia.horarioDisponible(dia);
						ArrayList<LocalTime> horaDisponible = horasDia.horarioDisponible(dia);
						for (int i = 0; i < horaDisponible.size(); i++) {
							LocalTime hora = horaDisponible.get(i);
							String horaString = String.format("%02d:%02d", hora.getHour(), hora.getMinute()); // Formatear manualmente la hora como "hh:mm"
						%>
						<option value="<%=horaString%>">
							<%=horaString%>
						</option>
						<%
						}
						%>
					</select>
				</div>
			</div>
			<br>
			<br>
		</div>
		<div class="text-center">

			<button name="formulario" onclick="confirmarCita()" value="confirmar"
				class="btn btn-success boton">Confirmar</button>

			<br> <br>
		</div>
	</form>
	<!-- </form> -->
	<!-- Cambio de colores  -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

	<script>
		//mostramos mensaje de alerta al confirmar
		function confirmarCita() {
			let fecha = document.getElementById("data-dia");
			alertify.confirm("Debe seleccionar un día", function() {
				// Mensaje de confirmación
				/* alertify.success("¡Su cita ha sido confirmada!"); */
				/* window.location.href = "gestioncitasclientes.jsp"; */

				if (fecha !== "data-dia") {
					// Si la fecha no es válida, muestra el mensaje de error y cancela la cita
					alertify.error("Debe seleccionar un día");
				} else if (fecha !== "data-dia")
					window.load = function() {
						{

							alertify.confirm("¡Su cita ha sido confirmada!");

						}
						;
					}
			});
		}
	</script>
</body>

</html>