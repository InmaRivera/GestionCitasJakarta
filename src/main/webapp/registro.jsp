<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, gestioncitas.*"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Alta cliente</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css">
</head>
<style>
a {
	text-decoration: none; /* Elimina la subrayado del enlace */
}

@import
	url("https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.4/font/bootstrap-icons.css")
	;

.gradient-custom-2 {
	/* fallback for old browsers */
	background: #fccb90;
	/* Chrome 10-25, Safari 5.1-6 */
	background: -webkit-linear-gradient(to right, #ee7724, #d8363a, #dd3675, #b44593);
	/* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
	background: linear-gradient(to right, #ee7724, #d8363a, #dd3675, #b44593);
}

@media ( min-width : 768px) {
	.gradient-form {
		height: 100vh !important;
	}
}

@media ( min-width : 769px) {
	.gradient-custom-2 {
		border-top-right-radius: .3rem;
		border-bottom-right-radius: .3rem;
	}
}
</style>

<body>
	<section class="vh-100 gradient-form" style="background-color: #eee;">
		<div class="container h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-lg-12 col-xl-11">
					<div class="card text-black" style="border-radius: 25px;">
						<div class="card-body p-md-5">
							<div class="row justify-content-center">
								<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

									<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Registro</p>
									<%
									String mensaje = (String) request.getAttribute("response");
									if (mensaje != null)
									{
										out.println(mensaje);
										request.removeAttribute("response");
									}
									%>
									<!--  <form class="mx-1 mx-md-4"> -->
									<form class="mx-1 mx-md-4" name="registro" action="controlador"
										method="POST">
										<%
										Gestiones gestioncitas = new Gestiones();
										%>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-person-fill fa-lg me-3 fa-fw"></i>

											<div class="form-outline flex-fill mb-0">
												<input type="text" id="nombre" name="nombre"
													class="form-control" placeholder="Nombre" />
												<!-- <label class="form-label" for="form3Example1c">Your Name</label> -->
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-person-plus-fill fa-lg me-3 fa-fw"></i>

											<div class="form-outline flex-fill mb-0">
												<input type="text" id="apellidos" name="apellidos"
													class="form-control" placeholder="Apellidos" />
												<!-- <label class="form-label" for="form3Example1c">Your Name</label> -->
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-telephone-fill fa-lg me-3 fa-fw"></i>
											<!-- <i class="fas fa-envelope fa-lg me-3 fa-fw"></i> -->
											<div class="form-outline flex-fill mb-0">
												<input type="number" id="telefono" name="telefono"
													class="form-control" placeholder="Teléfono" />
												<!-- <label class="form-label" for="form3Example3c">Your Email</label> -->
											</div>
										</div>
										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-envelope-fill fa-lg me-3 fa-fw"></i>
											<!-- <i class="fas fa-envelope fa-lg me-3 fa-fw"></i> -->
											<div class="form-outline flex-fill mb-0">
												<input type="email" id="email" name="email"
													class="form-control" placeholder="Email" />
												<!-- <label class="form-label" for="form3Example3c">Your Email</label> -->
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-lock-fill fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="password" id="clave" name="clave"
													class="form-control" placeholder="Contraseña" />
												<!-- <label class="form-label" for="form3Example4c">Password</label> -->
											</div>
										</div>

										<div class="d-flex flex-row align-items-center mb-4">
											<i class="bi bi-key-fill fa-lg me-3 fa-fw"></i>
											<div class="form-outline flex-fill mb-0">
												<input type="password" id="clave2" name="clave2"
													class="form-control" placeholder="Repite la contraseña" />
											</div>
										</div>


										<div class="form-check d-flex justify-content-center mb-5">
											<input class="form-check-input me-2" type="checkbox"
												value="terminos" name="terminos" id="terminos" /> <label
												class="form-check-label" for="form2Example3"> Acepto
												todos los términos <a
												href="https://boe.es/buscar/act.php?id=BOE-A-2018-16673">Términos
													del servicio</a>
											</label>
										</div>
										<div>

											<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
												<!-- <form name="registro" action="controlador" method="POST">  -->

												<button type="submit" name="todo" value="registro"
													class="btn btn-primary btn-lg gradient-custom-2 d-flex ">Registrarte</button>
												<input type="submit" class="btn btn-danger btn-lg margin"
													name="todo" value="volver">
											</div>
										</div>

									</form>

								</div>

								<div
									class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

									<img
										src="https://i.pinimg.com/originals/58/c2/53/58c253f9dbde6c2ed7f74eedc4ddc7a2.jpg"
										class="img-fluid" alt="Sample image">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>

	</section>

</body>

</html>