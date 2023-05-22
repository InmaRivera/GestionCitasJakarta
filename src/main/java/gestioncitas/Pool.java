package gestioncitas;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.sql.DataSource;
/**
 * 
 * @author Inma
 * Clase de conexion pool para el proyecto gestion citas
 *
 */
public class Pool
{
	Connection conn = null;
	Statement statement = null;
	private DataSource pool;
	public void Conectar() throws ServletException
	{
		try
		{
			// Crea un contexto para poder luego buscar el recurso DataSource
			InitialContext ctx = new InitialContext();
			// Busca el recurso DataSource en el contexto
			pool = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql_gestioncitas");
			if(pool == null)
			{
				throw new ServletException("DataSource desconocida 'mysql_gestioncitas'");
			} else {
				try {
					conn = pool.getConnection();
					statement = conn.createStatement();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(e.toString());
				}
			}

		}
		catch(NamingException ex){}
	}
	public DataSource getPool() {
		return pool;
	}
	public void cerrarConexion() {
		try
		{
			// Cerramos el resto de recursos
			if(statement != null)
			{
				statement.close();
			}
			if(conn != null)
			{
				conn.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
