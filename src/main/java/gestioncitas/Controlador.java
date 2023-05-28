package gestioncitas;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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


	@SuppressWarnings({ "unused" })
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
	 
		else if (todo.equals("areacliente")) 
		{
			System.out.println("area cliente");
			Gestiones gestion = new Gestiones();
			gestion.horario();
			gestion.calendario();
			ArrayList<Hora> horasDia = new ArrayList<Hora>();
			int idTrabajadorFK = 1;
			int idServicio = Integer.parseInt(request.getParameter("idServicio"));//Sacamos idServicio
			Calendar cal = Calendar.getInstance();
			int mesActual = cal.get(Calendar.MONTH) + 1;		
			int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
			String diaParam = request.getParameter("data-dia");
			int diaActual = 0;
			diaActual  = Calendar.DAY_OF_MONTH;
			//			String mesAnioActual = Year.now() +"-" + mesActual + "-" + diaActual; // Agregar el año actual
			//			Date fecha = Date.parse(mesAnioActual);
			String horaParam = request.getParameter("horas");//Sacamos hora
			if ( horaParam != null && !horaParam.isEmpty()) {
			Time hora = Time.valueOf(horaParam + ":00");//transformamos hora

			// Extraer las partes de la hora
			String[] partesHora = horaParam.split(":");
			int horas, minutos, segundos;
			// Verificar si el formato de la cadena es válido
			

				if (partesHora.length == 3) {

					horas = Integer.parseInt(partesHora[0]);
					minutos = Integer.parseInt(partesHora[1]);
					segundos = Integer.parseInt(partesHora[2]);

					// Crear el objeto Time con los valores extraídos
					//					hora = new Time(horas, minutos, segundos);

					try {
						diaActual = Integer.parseInt(diaParam);
						String mesAnioActual = cal.get(Calendar.YEAR) + "-" + mesActual + "-" + diaActual;
						Date fecha =  formatoFecha.parse(mesAnioActual);
						fecha = new Date(fecha.getTime());
						System.out.println("dia "+ mesAnioActual  + " hora " + hora + " servicio " + idServicio );
						Citas nuevaCita = new Citas(hora,fecha, idCliente, idTrabajadorFK, idServicio);

						if (elCalendario == null) {
							elCalendario = new ArrayList<>();
							elCalendario.add(nuevaCita);
							// Enlazar el carrito con la sesión
							session.setAttribute("elCalendario", elCalendario);
						}
						else
						{
							// Comprueba si la hora está ya en el calendario
							// Si lo está, actualizamos la hora
							// Si no está, la añadimos
							boolean encontrado = false;
							Iterator<Citas> iter = elCalendario.iterator();
							while(!encontrado&&iter.hasNext())
							{
								Citas cita =
										(Citas)iter.next();
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
						}
//						request.setAttribute("alertify","confirmar" );
						nextPage = "/gestioncitasclientes.jsp";
						
					} catch(Exception e) {

						request.setAttribute("alertify","cancelar" );
						nextPage = "/gestioncitasclientes.jsp";
						System.out.println("Error occurred"+ e.getMessage());
					}
				}
			}
			request.setAttribute("alertify","confirmar" );
			nextPage = "/gestioncitasclientes.jsp";
		}
		//Agregamos una cita nueva al confirmar
		else if (todo.equals("confirmar")) {
		    System.out.println("Controlador Entra en confirmar cita");
		    int dia = 0;
		    int idTrabajadorFK = 1;
		    int idServicioFK = 0;
		    Citas citas = new Citas();
		    Gestiones confirmar = new Gestiones();
		    confirmar.pool.Conectar();
		    idCliente = (int) session.getAttribute("idCliente");
		    int idServicio = Integer.parseInt(request.getParameter("idServicio"));
		    idServicio = idServicioFK;
		    int idClienteFK = idCliente;
		    if (elCalendario != null) {
		        for (Citas item : elCalendario) {
		            try {
		                confirmar.agregarCita(item.getHoraCita(), item.getFechaCita(), item.getIdClienteFK(), item.getIdServicioFK(), idTrabajadorFK);
		                System.out.println("idcliente " + item.getIdClienteFK() + " idservicio " + item.getIdServicioFK() + " idTrabajadorFK " + idTrabajadorFK);
		                request.setAttribute("alertify", "confirmar");
		                System.out.println("se acepta cita");
		            } catch (SQLException e2) {
		            	 request.setAttribute("alertify", "cancelar");
			                System.out.println("se cancela cita");
		                e2.printStackTrace();
		            }
		        }
		    }
		    request.setAttribute("alertify","confirmar" );
		    nextPage = "/gestioncitasclientes.jsp";
		}
		ServletContext servletContext = request.getServletContext();
		RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}


