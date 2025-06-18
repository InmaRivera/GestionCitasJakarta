package gestioncitas;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class Controlador
 */
@WebServlet("/controlador")
public class Controlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ServletContext servletContext = null;
	RequestDispatcher requestDispatcher = null;
	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
		/**
		 * Llamamos a la clase pool para hacer la conexión a la base de datos
		 */
		Pool pool = new Pool();
		pool.Conectar();
	}
	public Controlador() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * indicamos que la respuesta sea por post 
		 * para seguridad de no aparecer en la barra de navegación las credenciales.
		 */
		doPost(request, response);
	}


	@SuppressWarnings({ "unused" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		request.setCharacterEncoding("UTF-8");
		String nextPage ="";
		String mensaje = "";

		// Recupera la sesión actual o crea una nueva si no existe
		HttpSession session = request.getSession(true);
		String usuario = (String)session.getAttribute("usuario");
		if (usuario == null) {
		    response.sendRedirect("login.jsp");
		    return;
		}

		//int idCliente = ((Integer)session.getAttribute("idCliente")).intValue();
		Integer idClienteObj = (Integer) session.getAttribute("idCliente");
		if (idClienteObj == null) {
		    // Si no está logueado o no hay sesión válida, redirige a login
		    response.sendRedirect("login.jsp");
		    return;
		}
		int idCliente = idClienteObj.intValue();

		ArrayList<Citas> listaCitas = new ArrayList<Citas>();
		@SuppressWarnings("unchecked")
		ArrayList<Citas> elCalendario =  (ArrayList<Citas>)
		session.getAttribute("elCalendario");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//formatear fecha
		String todo = request.getParameter("todo");

		if(todo==null)
		{
			// Primer acceso, redirigir a pagina principal 
			nextPage = "/login.jsp";
		}

		// nuevo registro
		else if(todo.equals("registro"))
		{
			Gestiones gestioncitas = new Gestiones();
			int idUsuario = 0;
			int idUsuarioFK = 0;
			String nombre = request.getParameter("nombre");
			String apellidos = request.getParameter("apellidos");
			String telefono = request.getParameter("telefono");
			String email = request.getParameter("email");
			String clave = request.getParameter("clave");
			String clave2 = request.getParameter("clave2");
			if (nombre.length()==0 && apellidos.length()==0 && telefono.length()==0 && email.length()==0) 
			{
				//Respuesta //Avisamos que debe rellenar los campos
				request.setAttribute("response","<div class='alert alert-danger text-center' role='alert'><h3 class='text-center mb-4 text-danger'>Error! Debe introducir sus datos, todos los campos son obligatorios</h3></div>");
				nextPage = "/registro.jsp";
			}
			else if (telefono.length()==0 && email.length()==0)
			{
				//Respuesta //Avisamos que debe rellenar los campos
				request.setAttribute("response","<div class='alert alert-danger text-center' role='alert'><h3 class='text-center mb-4 text-danger'>Error! Debe introducir sus datos, todos los campos son obligatorios</h3></div>");
				nextPage = "/registro.jsp";
			}
			else if(clave.length()==0 && clave2.length()==0)
			{
				request.setAttribute("response","<div class='alert alert-danger text-center' role='alert'><h3 class='text-center mb-4 text-danger'>Error! Debe introducir su clave</h3></div>");
				nextPage = "/registro.jsp";
			}
			//Si todo está completo hacemos la inserción
			else if (nombre != null && apellidos != null && telefono != null && email != null && clave != null && clave2 != null)
			{
				if(!clave.equals(clave2))
				{
					// las claves no coinciden
					request.setAttribute("response","<div class='alert alert-danger text-center' role='alert'><h3 class='text-center mb-4 text-danger'>Error! Las claves deben coincidir</h3></div>");
					nextPage = "/registro.jsp";
				}
				else 
				{
					// las claves coinciden, insertamos el usuario en la base de datos
					try
					{
						//llamamos al método insertar usuario 
						gestioncitas.insertarUsuario(idUsuario, nombre, apellidos, telefono, email, clave, idUsuarioFK);
						request.setAttribute("response","<div class='alert alert-success text-center' role='alert'> <h3 class='text-center text-success mb-4'> El usuario "+ nombre +" ha sido creado correctamente</h3></div>");
						nextPage = "/registro.jsp";
					} 
					catch (SQLException e)
					{
						request.setAttribute("response","<div class='alert alert-danger text-center' role='alert'><h3 class='text-danger'>Este usuario ya está registrado</h3></div>");
						nextPage = "/registro.jsp";
					}

				}

			}
		}
		// Agregar caso "agregarCita"
		else if (todo.equals("formulario"))
		{
			Gestiones nuevaCita = new Gestiones();//objeto gestiones para llamar a agregar cita
			// Obtener los valores seleccionados del formulario
			idCliente = (int)session.getAttribute("idCliente");			
			int idTrabajadorFK = 1;
			int idServicio = Integer.parseInt(request.getParameter("idServicio"));//obtenemos id servicio seleccionado
			String dia = request.getParameter("data-dia");//recogemos día seleccionado
			LocalDate fechaCita = LocalDate.parse(dia);// Parseamos la fecha actual
			String horaString = request.getParameter("data-horas");
	
			LocalTime horaCita;
			horaCita = LocalTime.parse(horaString);
			Time horaCitaTime = Time.valueOf(horaCita);
			try 
			{
				// Llamar al método para agregar la cita a la base de datos
				nuevaCita.agregarCita(horaCita, fechaCita, idCliente, idServicio, idTrabajadorFK);
				request.setAttribute("cita", "<div class='alert alert-success text-center' role='alert'><h3 class='text-success'>Cita guardada correctamente.</h3></div>");
				nextPage = "/gestioncitasclientes.jsp";
			} 

			catch (DateTimeParseException | SQLException e) 
			{
				throw new IllegalArgumentException("Error debe indicar la fecha" + e.getMessage());
			}
		
		}
		else if (todo.equals("remove"))
		{
			Gestiones gestion = new Gestiones();
			Servicios cita = new Servicios();
			Citas citas = new Citas();
			
			int idCita = Integer.parseInt(request.getParameter("idCita")); // Obtener el ID de la cita desde el formulario
			idCliente = (int) session.getAttribute("idCliente"); // Obtener el ID del cliente desde la sesión
			
			try
			{
				gestion.eliminarCita(idCita);
				request.setAttribute("cita", "<div class='alert alert-success text-center' role='alert'><h3 class='text-success'>Su cita ha sido eliminada correctamente.</h3></div>");
				nextPage = "/gestioncitasclientes.jsp";
			} catch (SQLException e)
			{				
				e.printStackTrace();
			}
					
		}

		else if (todo.equals("volver"))
		{
			nextPage = "/login.jsp";
		}

		ServletContext servletContext = request.getServletContext();
		RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}


