<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true" import="java.util.*, gestioncitas.*"%>
<!DOCTYPE html>
<html lang="es">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
	crossorigin="anonymous"></script>

<title>Log In</title>

</head>
<style>
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

	<section class="vh-100  justify-content-center align-items-center"
		style="background-color: #eee;">
		<div class="container py-5 h-100">
			<div
				class="row d-flex justify-content-center align-items-center h-100">
				<div class="col-xl-10">
					<div class="card rounded-3 text-black">
						<div class="row g-0">
							<!-- <div class="col-lg-6"> -->
							<div class="card-body shadow p-md-5 mx-md-4">

								<div class="text-center">
									<img
										src="https://i.pinimg.com/originals/58/c2/53/58c253f9dbde6c2ed7f74eedc4ddc7a2.jpg"
										style="width: 185px;" alt="logo">
									
									<h4 class="mt-1 mb-5 pb-1">Acceder a tu perfil</h4>

								</div>
								<%
									String mensaje = (String) request.getAttribute("response");
									if(mensaje != null){
									out.println(mensaje);
									request.removeAttribute("response");
									}
									%>
								<form method="post" action="login">
									<p>Por favor, conéctese con su cuenta</p>

									<div class="form-outline mb-4">

										<input type="exampleInputEmail1" class="form-control"
											name="usuario" id="usuario" placeholder="Nombre usuario" />
										<!-- <label class="form-label" for="form2Example11">Username</label> -->
									</div>

									<div class="form-outline mb-4">
										<input type="password" name="password" id="password"
											class="form-control" placeholder="Contraseña" />
										<!-- <label class="form-label" for="form2Example22">Password</label> -->
									</div>

									<div class="d-flex justify-content-center mb-lg-4">
										<button class="btn btn-primary btn-lg gradient-custom-2"
											type="submit">Entrar</button>
										<!-- <a class="text-muted" href="#!">Forgot password?</a> -->
									</div>
									<br>
									<div
										class="d-flex align-items-center justify-content-center pb-4">
										<p class="mb-0 me-2">¿No tienes cuenta?</p>
										<form name="login" action="controlador" method="POST">
											<a type="button" class="btn btn-outline-danger"
												href="registro.jsp">Crea una nueva</a>

										</form>

									</div>

								</form>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
</body>

</html>