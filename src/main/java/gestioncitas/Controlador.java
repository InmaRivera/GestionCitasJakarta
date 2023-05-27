package gestioncitas;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.ParseException;

import gestioncitas.Gestiones.Hora;



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


	@SuppressWarnings({ "unused", "deprecation" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		request.setCharacterEncoding("UTF-8");
		String nextPage ="";
		String mensaje = "";

		// Recupera la sesión actual o crea una nueva si no existe
		HttpSession session = request.getSession(true);
		String usuario = (String)session.getAttribute("usuario");
		int idCliente = (int)session.getAttribute("idCliente");
		ArrayList<Citas> listaCitas = new ArrayList<Citas>();
		@SuppressWarnings("unchecked")
		ArrayList<Citas> elCalendario =  (ArrayList<Citas>)
		session.getAttribute("elCalendario");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");//formatear fecha
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
				request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Debe introducir sus datos, todos los campos son obligatorios </h3>");
				nextPage = "/registro.jsp";
			}
			else if (telefono.length()==0 && email.length()==0)
			{
				//Respuesta //Avisamos que debe rellenar los campos
				request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Debe introducir sus datos, todos los campos son obligatorios </h3>");
				nextPage = "/registro.jsp";
			}
			else if(clave.length()==0 && clave2.length()==0)
			{
				request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Debe introducir su clave</h3>");
				nextPage = "/registro.jsp";
			}
			//Si todo está completo hacemos la inserción
			else if (nombre != null && apellidos != null && telefono != null && email != null && clave != null && clave2 != null)
			{
				if(!clave.equals(clave2))
				{
					// las claves no coinciden
					request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Las claves deben coincidir</h3>");
					nextPage = "/registro.jsp";
				}
				else 
				{
					// las claves coinciden, insertamos el usuario en la base de datos
					try
					{
						//llamamos al método insertar usuario 
						gestioncitas.insertarUsuario(idUsuario, nombre, apellidos, telefono, email, clave, idUsuarioFK);
						request.setAttribute("response","<h3 class='text-center text-success mb-4'> El usuario "+ nombre +" ha sido creado correctamente</h3>");
						nextPage = "/registro.jsp";
					} 
					catch (SQLException e)
					{
						request.setAttribute("response","<h3 class='text-danger'>Este usuario ya está registrado</h3>" );
						nextPage = "/registro.jsp";
					}

				}

			}
		}
		//Recoger datos del calendiario (horas dias y servicio)
		else if (todo.equals("areacliente")) 
		{
			System.out.println("area cliente");
			//		    int dia = 0;
			// Añadimos las horas
			Gestiones gestion = new Gestiones();
			gestion.horario();
			gestion.calendario();
			ArrayList<Hora> horasDia = new ArrayList<Hora>();
			int idServicio = Integer.parseInt(request.getParameter("idServicio"));//Sacamos idServicio
			Calendar cal = Calendar.getInstance();
			int mesActual = cal.get(Calendar.MONTH) + 1;
			mesActual = Calendar.DAY_OF_MONTH;
			int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
			int diaActual = Integer.parseInt(request.getParameter("dias"));
			String mesAnioActual = Year.now() +"-" + mesActual + "-" + diaActual; // Agregar el año actual
			Date fecha=null;
			String horaParam = request.getParameter("horas");//Sacamos hora
			Time hora = Time.valueOf(horaParam + ":00");//transformamos hora
			// Verificar si el formato de la cadena es válido
			if (horaParam != null) {
				if (horaParam.matches("\\d{2}:\\d{2}:\\d{2}")) {
					// Extraer las partes de la hora
					String[] partesHora = horaParam.split(":");
					int horas = Integer.parseInt(partesHora[0]);
					int minutos = Integer.parseInt(partesHora[1]);
					int segundos = Integer.parseInt(partesHora[2]);

					// Crear el objeto Time con los valores extraídos
					hora = new Time(horas, minutos, segundos);

					try {
						Date parsed =  formatoFecha.parse(mesAnioActual);
						fecha = new Date(parsed.getTime());
						System.out.println("dia "+ mesAnioActual  + " hora " + hora + " servicio " + idServicio );
						Citas nuevaCita = new Citas(idServicio, fecha, hora);

						if (elCalendario == null) {
							elCalendario = new ArrayList<>();
						}

						// Comprueba si la hora está ya en el calendario
						// Si lo está, actualizamos la hora
						// Si no está, la añadimos
						boolean encontrado = false;

						for (Citas cita : elCalendario) {
							if (cita.getHoraCita().equals(nuevaCita.getHoraCita())) {
								System.out.println("Fecha antes de añadir => " + cita.getHoraCita() + ", " + nuevaCita.getHoraCita());
								nuevaCita.setFechaCita(nuevaCita.getFechaCita(), cita.getFechaCita());
								encontrado = true;
								break;
							}
						}

						if (!encontrado) {
							// Añade la nueva hora al calendario
							elCalendario.add(nuevaCita);
							System.out.println("fecha despues de añadir => " + nuevaCita.getFechaCita());
						}


					} catch(Exception e) {

						request.setAttribute("response","<h3 class='text-danger'>No se ha podido realizar la cita</h3>" );

						System.out.println("Error occurred"+ e.getMessage());
					}
				}
			}

			session.setAttribute("elCalendario", elCalendario);

			// Volvemos a la página principal para añadir más citas
			request.setAttribute("response","<h3 class='text-success'>Cita realizada</h3>" );

			nextPage = "/gestioncitasclientes.jsp";

		}

		//Agregamos una cita nueva al confirmar
		else if (todo.equals("confirmar")) {
			System.out.println("Controlador Entra en confirmar cita");
			int dia = 0;
			int idTrabajadorFK = 1;
			//		    String fecha = request.getParameter("dias");
			Citas citas = new Citas();
			Gestiones confirmar = new Gestiones();
			idCliente  = (int)session.getAttribute("idCliente");
			int idClienteFK = idCliente;
			if (elCalendario != null) {
				for (Citas item : elCalendario) {
					try {
						confirmar.agregarCita(item.getHoraCita(), item.getFechaCita(), item.getIdClienteFK(), item.getIdServicioFK(), idTrabajadorFK);
						System.out.println("idcliente " + item.getIdClienteFK() + " idservicio " + item.getIdServicioFK() + " idTrabajadorFK " + idTrabajadorFK);
						request.setAttribute("alertify", "confirmar");
						nextPage = "/gestioncitasclientes.jsp";
						System.out.println("se acepta cita");
					} catch (SQLException e2) {
						e2.printStackTrace();
					} 
					nextPage = "/gestioncitasclientes.jsp";
					System.out.println("se acepta cita");
				}
			} else {
				// Manejar el caso cuando elCalendario es nulo
				if(elCalendario == null)
				{
					//		    		request.setAttribute("alertify", "cancelar");
					//		    		 nextPage = "/gestioncitasclientes.jsp";
					System.out.println("se cancela cita");
				}
			}
		}

		//para salir de la app
		//		else if (todo.equals("logout"))
		//		{
		//			nextPage = "/logout.jsp";
		//		}

		ServletContext servletContext = request.getServletContext();
		RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}


