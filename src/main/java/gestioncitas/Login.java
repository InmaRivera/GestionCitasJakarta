package gestioncitas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Login
 */
@WebServlet(name = "Login",
urlPatterns = {"/login"})
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	public void init(ServletConfig conf) throws ServletException
	{
		super.init(conf);
		
	}

	//Las respuestas serán enviadas por método post
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	//Creamos las credenciales en post, para más seguridad
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");

		//Variable para mandar a la siguiente página
		Connection conn = null;
		Statement stmt = null;
		String nextPage = "";
		//la iniciamos las variables en 0 para detectar el id del usuario o del cliente
		int tipoUsuario = 0;
		int idCliente = 0;
		//variable para diferenciar usuarios
		HttpSession session = request.getSession(false);
		try
		{	
			// Obtener una conexión del pool
			Pool pool = new Pool();
			pool.Conectar();
			conn = pool.conn;
			stmt = conn.createStatement();
			// Recuperar los parámetros usuario y password de la petición request
			String usuario = request.getParameter("usuario");
			String password = request.getParameter("password");
			// Validar los parámetros de la petición request
			if(usuario.length()==0)
			{
				//si no introduce el nombre
				session.setAttribute("usuario", usuario);
				//Respuesta
				request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Debe introducir su usuario</h3>");
				nextPage = "/login.jsp";
			}
			else if(password.length()==0)
			{
				//si no introducen contraseña
				session.setAttribute("usuario", usuario);
				request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Debe introducir su contraseña</h3>");
				nextPage = "/login.jsp";
			}
			else
			{
				// Verificar que existe el usuario y su correspondiente clave
				StringBuilder sqlStr = new StringBuilder();
				sqlStr.append("SELECT * FROM usuarios WHERE ");
				sqlStr.append("STRCMP(usuarios.nombreUsuario, '").append(usuario).append("') = 0 ");
				sqlStr.append(" AND STRCMP(usuarios.claveUsuario, SHA2('").append(password).append("',256)) = 0;");
				ResultSet rset = stmt.executeQuery(sqlStr.toString());

				if(!rset.next())
				{
					//En caso de que no introduzca bien contraseña o usuario
					session.setAttribute("usuario", usuario);
					request.setAttribute("response","<h3 class='text-center mb-4 text-danger'>Error! Usuario o contraseña incorrectos</h3>");
					nextPage = "/login.jsp";
				}
				else
				{

					//Recuperamos el tipo de usuario de la base de datos
					tipoUsuario = rset.getInt("idUsuario");
					//					
					// Sacar el tipo del usuario logado
					String sql = ("SELECT idUsuario = '"+ tipoUsuario +"' FROM usuarios WHERE " 
							+ "nombreUsuario = '"
							+ usuario 
							+ "' AND claveUsuario = SHA2('" + password +  "', 256);");
					System.out.println(sql);
					//buscamos idCliente
					tipoUsuario = rset.getInt("idUsuario");
					String clienteQuery = "SELECT idCliente FROM clientes WHERE idUsuarioFK = " + tipoUsuario;
					ResultSet clienteResult = stmt.executeQuery(clienteQuery);
					if (clienteResult.next()) 
					{
						idCliente = clienteResult.getInt("idCliente");
						System.out.println("idcliente login "+idCliente);
						nextPage = "/gestioncitasclientes.jsp";
						//comprobamos mayor que 1 es cliente
						if(tipoUsuario>1)
						{	
							// Creamos una variable de session, con el registro de usuario
							// Verificar en el administrador de aplicaciones de tomcat.
							if(session != null)
							{
								//si la session no es nula, la invalidamos
								session.invalidate();
							}
							session = request.getSession(true);
							synchronized(session)
							{
								//y sincronizamos la nueva sesion según el tipo de usuario
								session.setAttribute("usuario", usuario);
								session.setAttribute("idCliente", idCliente);
								session.setAttribute("idUsuario", rset.getInt("idUsuario"));	

							}
							nextPage = "/gestioncitasclientes.jsp";

						}
						//comprobamos = a 1 es trabajador
						else if(tipoUsuario==1)
						{
							// Creamos una variable de session, con el registro de usuario (Bean)
							// Verificar en el administrador de aplicaciones de tomcat.
							if(session != null)
							{
								session.invalidate();
							}

							session = request.getSession(true);
							synchronized(session)
							{
								session.setAttribute("usuario", usuario);
								session.setAttribute("idUsuario", rset.getInt("idUsuario"));
								nextPage = "/citas.jsp";
							}
							nextPage = "/citas.jsp";
						}
					} else {
						//mandamos a zona trbajador
						nextPage = "/citas.jsp";
					}
					
				}
			}	
		
		}
		catch(SQLException ex){}

		//Cerramos conexión 
		Pool pool = new Pool();
		pool.cerrarConexion();


		//Recibimos las respuestas indicadas a través de next page

		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher =
				servletContext.getRequestDispatcher(nextPage);
		requestDispatcher.forward(request, response);
	}
}