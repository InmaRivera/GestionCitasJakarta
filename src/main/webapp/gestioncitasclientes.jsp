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

h1, h3, h4, p {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #000;
	text-shadow: #fff 3px 6px 4px;
	text-align: center;
}

.calendar {
	border: 1px solid #ddd;
	padding: 0px;
	padding-left: 76px;
	padding-right: 60px;
}

.calendar th {
	background-color: #f2f2f2;
	text-align: center;
	font-weight: bold;
	padding: 10px;
	font-size: 18px;
}

.calendar td {
	padding: 10px;
	text-align: center;
	font-size: 16px;
	cursor: pointer;
	transition: background-color 0.2s;
}

.calendar td:hover {
	background-color: #f2f2f2;
}

.calendar .available {
	background-color: #d4edda;
	color: #155724;
}

.calendar .unavailable {
	background-color: #f8d7da;
	color: #721c24;
}

.calendar .selected {
	background-color: #007bff;
	color: #fff;
}

section {
	background-color: #DBF7FD;
}

.d-flex {
	display: -ms-flexbox !important;
	display: flex !important;
	flex-direction: column;
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
			<a class="navbar-brand" href="gestioncitasclientes.jsp"><h5>Área
					Cliente</h5></a>
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
	<form name="confirmar" action="controlador" method="POST">
		<input type="hidden" name="todo" value="areacliente">
		<section>
			<div class="justify-content-center text-center pt-4">
				<h3>Reserva una cita</h3>

				<!-- 	<form name="AgregarForm" action="controlador" method="POST"> -->
				<div class="text-center form-floating">
					<label class="color:#000;" for="floatingSelect">Seleccione
						un tratamiento/servicio:</label><br> <select class="form-select"
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
			<br> <br>
		</section>
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
						<p>Seleccione un día disponible:</p>
						<h4>
							Hoy:
							<%=fechaActual%></h4>
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
						<%--  <%} %> --%>
					</div>
					<br>
					<table class="table">
						<thead>
							<tr>
								<th>Lun</th>
								<th>Mar</th>
								<th>Mie</th>
								<th>Jue</th>
								<th>Vie</th>
								<th>Sab</th>
								<th>Dom</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<%
								int dia = 0;
								Gestiones horasDia = new Gestiones();
								/* horasDia.calendario(); */
								/* 	horasDia.horario();*/
								horasDia.horarioDisponible(dia);
								/* ArrayList<Citas> horasDisponibles = (ArrayList<Citas>) session.getAttribute("elCalendario"); */

								for (int i = 1; i <= numDias; i++) {
									// Obtener el día de la semana para el día actual
									cal.set(Calendar.DAY_OF_MONTH, i);
									mesActual = Calendar.DAY_OF_MONTH;
									int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
									mesActual = Calendar.DAY_OF_WEEK;
									//obtener horas
								%>
								<!-- Indicamos en el class si que sabado y domingo no esta disponible -->

								<td
									class="<%=diaSemana == Calendar.SATURDAY || diaSemana == Calendar.SUNDAY ? "unavailable" : "available"%>"
									data-dia="<%=i%>"><%=i%></td>

								<%-- 
								<td
									class="<%=diaSemana == Calendar.SATURDAY || diaSemana == Calendar.SUNDAY ? "unavailable" : "available"%>"
									name="fecha" id="dia"><%=i%></td> --%>
								<%
								if (i % 7 == 0) {
								%>
							</tr>
							<tr>
								<%
								}
								}
								%>

							</tr>

						</tbody>
					</table>

					<label>Seleccionar hora</label>
					<!-- 	<input type="hidden" name="todo" value="areacliente"> -->
					<select name="horas" id="horas-disponibles">

						<%
						/* horasDia.horarioDisponible(dia);
						for (int i = 0; i < gestiones.tamano(); i++) {
						    Citas cita = horasDisponibles.get(i);
						    gestiones.getHoraCita(i); */
						ArrayList<LocalTime> horaDisponible = horasDia.horarioDisponible(dia);
						for (int i = 0; i < horaDisponible.size(); i++) {
							LocalTime hora = horaDisponible.get(i);
							/* 	String horas = horasDia.getHoraCita(i).toString();
								String[] partesHoras = horas.split("-"); */
						%>
						<option
							data-horas="<%=hora.getHour() + ":0" + hora.getMinute() + ":0" + hora.getSecond()%>">
							<%
							if (hora.getMinute() < 10) {
								out.println(hora.getHour() + ":0" + hora.getMinute());
							%>
						</option>
						<%
						} else {
						out.println(hora.getHour() + ":" + hora.getMinute());
						}
						}
						%>
					</select>
					<table class="table">
						<thead>
							<tr>
								<th>Horas y día seleccionado:</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td id="dias-disponibles"></td>
							</tr>
							<%
							if (horasDisponibles != null && horasDisponibles.size() > 0) {
							%>
							<tr>
								<td id="horas-disponibles"></td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
	<div class="text-center">
		<!-- 		<input  type="hidden" name="todo" onclick="confirmarCita()" value="agregarCita">
 -->
 		<input class="btn btn-success boton" type="submit" name="confirmar" onclick="confirmarCita()" value="Confirmar">
		<button name="todo" value="confirmar" class="btn btn-success boton"
			onclick="confirmarCita()">Confirmar</button>

		<br> <br>
	</div>

	<!-- Cambio de colores  -->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

	<script>
		// Seleccionar un día disponible
		$('.calendar td.available').on('click', function() {
			$('.calendar td.selected').removeClass('selected');
			$(this).addClass('selected');
			 // Obtener el día seleccionado
			  let diaSeleccionado = $(this).data('dia');
			  $('#dias-disponibles').html(diaSeleccionado);
			  // Obtener las horas disponibles para el día seleccionado
			// Aquí se hace la llamada para obtener los horarios disponibles para ese día
			let horas = $(this).data('horas');
			$('#horas-disponibles').html(horas);
		});
	
		//mostramos mensaje de alerta al confirmar
<%--  function confirmarCita() {
  var alertify = "<%=request.getAttribute("alertify") %>";
  if (alertify != null && alertify === "confirmar") {
    alertify.confirm("¿Está seguro de confirmar su cita?", function() {
      alertify.success("¡Su cita ha sido confirmada!");
      window.location.href = "gestioncitasclientes.jsp";
    }, function() {
      alertify.error("Su cita ha sido cancelada.");
      window.location.href = "gestioncitasclientes.jsp";
    });
  } 
} --%>
//mostramos mensaje de alerta al confirmar
function confirmarCita() {
	 <%--  var alertify = "<%=request.getAttribute("alertify")%>"; --%>

	alertify.confirm("¿Está seguro de confirmar su cita?", function() {
		// Aquí puedes agregar el código para confirmar la cita
		alertify.success("¡Su cita ha sido confirmada!");
	     window.location.href = "gestioncitasclientes.jsp";
	}, function() {
		// Aquí puedes agregar el código para cancelar la cita
		alertify.error("Su cita ha sido cancelada.");
	     window.location.href = "gestioncitasclientes.jsp";
	});

}
</script>
</body>

</html>