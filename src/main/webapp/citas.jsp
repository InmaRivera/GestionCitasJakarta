<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, gestioncitas.*"%>
<!-- Importamos Time -->
<%@ page import="java.sql.Time"%>
<!-- Importamos formateo fecha -->
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDate"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link href="https://getbootstrap.com/docs/5.2/assets/css/docs.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
<title>Citas</title>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<%
String usuario = (String) session.getAttribute("usuario");
int tipoUsuario = 1;
%>
</head>
<style>
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

body {
	background-image:
		url("https://marketplace.canva.com/EAD2962NKnQ/2/0/1600w/canva-rainbow-gradient-pink-and-purple-virtual-background-_Tcjok-d9b4.jpg");
	background-repeat: 100% no-repeat;
	background-size: 100% auto;
	height: 100%;
	padding-top: 10%;
	padding-left: 76px;
	padding-right: 60px;
}

h5 {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #fff;
	text-shadow: #5ef0f2 3px 6px 4px;
	text-align: center;
}

h1, p {
	font-family: 'Pacifico', cursive;
	font-family: 'Shantell Sans', cursive;
	color: #000;
	text-shadow: #fff 3px 6px 4px;
	text-align: center;
}
</style>

<body>
	<nav class="navbar navbar-expand-lg trans bg-dark fixed-top">
		<div class="container-fluid ">
			<a class="navbar-brand " href="citas.jsp"><h5>Área
					Trabajador</h5></a>
			<div class="d-flex justify-content-end" id="navbarNav">
				<ul class="navbar-nav">

					<li class="nav-item "><a class="nav-link letra"
						href="logout.jsp">
							<button name="todo" value="logout" class="btn btn-danger ">
								Cerrar Sesión <i class="bi bi-power"></i>
							</button>
					</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<br>
	<br>
	<h1>Agenda de citas</h1>
	<p>
		<%
		String mensaje = (String) request.getAttribute("response");
		if (mensaje != null)
		{
			out.println(mensaje);
			request.removeAttribute("response");
		}
		%>
	</p>
	<!-- Example Code -->
	<section>
		<div class="list-group text-center">
			<table class="table table-dark table-hover">
				<tr>
					<th>Clienta</th>
					<th>Apellidos</th>
					<th>Teléfono</th>
					<th>Tipo Servicio</th>
					<th>Tiempo</th>
					<th>Fecha</th>
					<th>Hora</th>
					<!-- <th>Opciones</th> -->
				</tr>
				<%
				Gestiones gestiones = new Gestiones();//objeto clase gestiones
				//Arraylists
				ArrayList<Usuarios> listaUsuarios = gestiones.getUsuarios();
				ArrayList<Servicios> listaServicios = gestiones.getServicios();
				ArrayList<Citas> listadoCitas = gestiones.getCitas();
				
				for (int i = 0; i < listadoCitas.size(); i++)
					{
						Usuarios usuarios = listaUsuarios.get(i);
						Servicios servicio = listaServicios.get(i);
						Citas citas = listadoCitas.get(i);
				%>
				<tr>
					<td><%=citas.getNombreUsuario()%></td>
					<td><%=citas.getApellidosUsuario()%></td>
					<td><%=citas.getTelefonoUsuario()%></td>
					<td><%=servicio.getTipoServicio()%></td>
					<td><%=servicio.getDuracionServicio()%></td>
					<!-- Formateamos fecha con split  -->
					<%
					String fecha = citas.getFechaCita().toString();
					String[] partesFecha = fecha.split("-");
					%>
					<td><%= partesFecha[2] %>/<%= partesFecha[1] %>/<%= partesFecha[0] %></td>
					<%
					/* Formateamos hora */
					String hora = citas.getHoraCita().toString();
					String[] partesHora = hora.split(":");
				%>
					<td><%=partesHora[0] + ":" + partesHora[1]%></td>
					<!-- 	<td>
						<form name="infoCitas" action="controlador" method="POST">
							<input type="button" class="btn btn-info"
								value="Confirmar Asistencia"><a href="infoCitas.jsp"></a>
						</form>
					</td> -->
				</tr>
				<%
				}
				%>

			</table>
		</div>
	</section>
	<!-- End Example Code -->
</body>

</html>