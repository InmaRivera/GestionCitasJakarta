package gestioncitas;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


	@SuppressWarnings("unused")
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
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");//formatear fecha
		String todo = request.getParameter("todo");

		if(todo==null)
		{
			// Primer acceso, redirigir a pagina principal 
			nextPage = "/login.jsp";
		}

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

			//Si todo está completo hacemos la insercción
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

					}
					nextPage = "/registro.jsp";
				}
				nextPage = "/registro.jsp";
			}
		}
		//Mostrar horas del calendario
					else if (todo.equals("calendario"))
					{
						int dia = 0;
						//Añadimos las horas
						Gestiones horas = new Gestiones();
						horas.horarioDisponible(dia);
						horas.horario();
						ArrayList<Hora> horasDia = new ArrayList<Hora>();
						int idServicio = Integer.parseInt(request.getParameter("idServicio"));
						Date fecha;
		
						try
						{
							fecha = formatoFecha.parse(request.getParameter("fecha")); 
							Citas nuevaCita = new Citas (idServicio,fecha);
							if(elCalendario==null)
							{
								// El calendario está vacío
								elCalendario = new ArrayList<>();
								elCalendario.add(nuevaCita);
								// Enlazar el calendario con la sesión
								session.setAttribute("elCalendario", elCalendario);
							}
							else
							{
								// Comprueba si la hora está ya en el calendario
								// Si lo está, actualizamos la hora
								// Si no está, la añadimos
								boolean encontrado = true;
								Iterator<Citas> iter = elCalendario.iterator();
								while(!encontrado&&iter.hasNext())
								{
									Citas nuevoDia = (Citas)iter.next();
									if(nuevoDia.getHoraCita() == nuevaCita.getHoraCita())
									{
										System.out.println("isFecha antes de añadir => "+nuevoDia.getHoraCita() +", " +nuevaCita.getHoraCita());
		
										nuevaCita.setFechaCita(nuevaCita.getFechaCita(),nuevoDia.getFechaCita());
										encontrado = true;
									}
								}
								if(!encontrado)
								{
									// Lo añade nueva hora al calendario
									elCalendario.add(nuevaCita);
		
									System.out.println("fecha despues de añadir=> " +
											nuevaCita.getFechaCita());
		
									session.setAttribute("elCalendario", elCalendario);
								}
								// Volvemos página principal para añadir más citas
								nextPage = "/gestioncitasclientes.jsp";
							}
		
						} catch (ParseException e)
						{
							System.out.println("error => " + e);
							e.printStackTrace();
						}
		
						nextPage = "/gestioncitasclientes.jsp";
					}

		//Agregamos una cita nueva al confirmar
		else if (todo.equals("confirmar"))
		{
			System.out.println("Controlador Entra en confirmar cita");
			int dia = 0;
			idCliente = (int) session.getAttribute("idCliente"); 
			int idTrabajadorFK = 1;
			String idServicioStr = request.getParameter("idServicio");
			String horaCitaStr = request.getParameter("horas-disponibles");
			String fecha = request.getParameter("dia");
			int idServicio = Integer.parseInt(idServicioStr);
			Gestiones confirmar = new Gestiones();
			//Recogemos horario disponible
			Gestiones horas = new Gestiones();
			horas.horarioDisponible(dia);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//Agregamos cita nueva
			try
			{
				//					Time horaCita = (Time) sdf.parse(horaCitaStr);
				//					Date fechaCita = sdf.parse(fecha);
				java.util.Date fechaCita = sdf.parse(fecha); // Concatena fecha y hora
				Time horaCita = new java.sql.Time(fechaCita.getTime()); // Utiliza java.sql.Times

				confirmar.agregarCita(horaCita, fechaCita, idCliente, idServicio, idTrabajadorFK);
				System.out.println("idcliente " + idCliente + "idservicio " +idServicio + "idTrabajadorFK " + idTrabajadorFK);
			} catch (SQLException e2)
			{
				System.out.println( "error " + e2);
				e2.printStackTrace();
			}

			catch (ParseException e)
			{			
				System.out.println("error " + e);
				e.printStackTrace();
			}
			request.setAttribute("alertify", "confirmar");
			nextPage = "/gestioncitasclientes.jsp";
		}
		//para salir de la app
		else if (todo.equals("logout"))
		{
			nextPage = "/logout.jsp";
		}

		ServletContext servletContext = request.getServletContext();
		RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}


