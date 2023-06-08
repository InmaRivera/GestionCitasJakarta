package gestioncitas;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

public class Gestiones
{
	// conectamos a la base de datos llamando al pool
	Pool pool = new Pool();
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	ArrayList<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
	ArrayList<Servicios> listaservicios = new ArrayList<Servicios>();
	ArrayList<LocalTime> horasDisponibles = new ArrayList<LocalTime>();
	ArrayList<Citas> listadoCitas = new ArrayList<Citas>();
	//variable para diferenciar usuarios
	HttpSession session = null;

	public Gestiones()
	{
		super();
	}

	//Sacar nombre usuario
	public ArrayList<Usuarios> getUsuarios()
	{
		try
		{
			pool.Conectar();
			String usuarios = "SELECT * FROM usuarios;";
			ResultSet rs = pool.statement.executeQuery(usuarios);
			Usuarios usuario;
			while(rs.next())
			{
				usuario = new Usuarios(rs.getInt("idUsuario"),rs.getString("nombreUsuario"), rs.getString("apellidosUsuario"), rs.getString("telefonoUsuario"),rs.getString("emailUsuario"));
				listaUsuarios.add(usuario);
			} 

		}
		catch (ServletException | SQLException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();
		return listaUsuarios;

	}

	//Alta cliente
	public void insertarUsuario(int idUsuario, String nombreUsuario, String apellidosUsuario, String telefonoUsuario, String emailUsuario, String claveUsuario, int idUsuarioFK) throws SQLException
	{
		try 
		{
			pool.Conectar();
			//Primero insertamos el usuario
			String insertClient = "INSERT INTO gestioncitas.usuarios (idUsuario, nombreUsuario, apellidosUsuario, telefonoUsuario, emailUsuario, claveUsuario) VALUES (null, '"+nombreUsuario+"', '"+apellidosUsuario+"', "+telefonoUsuario+", '"+emailUsuario+"', SHA2('"+claveUsuario+"', 256)) ";
			pool.statement.execute(insertClient);

			//Buscamos el último id insertado y lo añadimos a la tabla cliente
			String insertClientFK = "INSERT INTO gestioncitas.clientes (idCliente, idUsuarioFK) VALUES (null, LAST_INSERT_ID())";
			pool.statement.execute(insertClientFK);

		}
		catch (ServletException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();
	}

	//Mostrar Servicios
	public ArrayList<Servicios> getServicios () throws SQLException
	{
		try
		{
			pool.Conectar();
			String servicios = "SELECT * FROM servicios;";
			ResultSet rs = pool.statement.executeQuery(servicios);
			Servicios servicio;
			while(rs.next())
			{
				servicio = new Servicios(rs.getInt("idServicio"),rs.getString("tipoServicio"), rs.getTime("duracionServicio"), rs.getString("descripcionServicio"),rs.getFloat("precioServicio"));
				listaservicios.add(servicio);
			} 

		}
		catch (ServletException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();
		return listaservicios;
	}

	//Mostrar calendario
	public void calendario ()
	{
		try
		{
			pool.Conectar();
			// Obtener el calendario actual
			Calendar calendario = Calendar.getInstance();

		}
		catch (ServletException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();

	}
	//mostrar horas disponibles
	@SuppressWarnings("unlikely-arg-type")
	public ArrayList<LocalTime> horarioDisponible(int dia) throws SQLException {
		ArrayList<LocalTime> horasDisponibles = new ArrayList<>();
		try {
			ArrayList<LocalTime> horasOcupadas = horarioOcupado(dia);
			pool.Conectar();

			// Agregar horas desde las 10:00 a las 14:00
			LocalTime hora = LocalTime.of(10, 0);
			while (hora.isBefore(LocalTime.of(14, 0))) {
				//Si horas estan ocupadas
				if (horasOcupadas.contains(horasDisponibles)) 
				{	            	
					//horas ocupdadas
					horasOcupadas.remove(horasDisponibles);
				}
				else
				{	            	
					//sino agregamos horas disponibles al add
					horasDisponibles.add(hora);
					hora = hora.plusHours(1);
				}
				
			}

			// Agregar horas desde las 16:00 a las 20:00
			hora = LocalTime.of(16, 0);
			while (hora.isBefore(LocalTime.of(20, 0))) {
				if (horasOcupadas.contains(hora)) 
				{
					//horas ocupadas
					horasDisponibles.contains(horasOcupadas);
					horasDisponibles.remove(hora);
		
				}
				else
				{
					horasDisponibles.add(hora);
					hora = hora.plusHours(1);
				}

			}
		} catch (ServletException e) {
			e.printStackTrace();
		}

		pool.cerrarConexion();
		return horasDisponibles;
	}

	//Metodo horas ocupadas
	public ArrayList<LocalTime> horarioOcupado(int dia) throws SQLException {
		ArrayList<LocalTime> horasOcupadas = new ArrayList<LocalTime>();

		try {
			pool.Conectar();
			//Recogemos las horas seleccionadas en la base
			String query = "SELECT horaCita FROM citas WHERE fechaCita = ?";
			PreparedStatement statement = pool.conn.prepareStatement(query);
			statement.setDate(1, java.sql.Date.valueOf(LocalDate.of(2023, 6, dia))); // parseamos la fecha en date

			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				LocalTime horaCita = rs.getTime("horaCita").toLocalTime();
				horasOcupadas.add(horaCita);
			}
		} catch (ServletException | SQLException e) {
			e.printStackTrace();
		}

		pool.cerrarConexion();
		return horasOcupadas;
	}



	//Agregar cita nueva 
	public void agregarCita(LocalTime horaCita, LocalDate fechaCita, int idClienteFK, int idServicioFK, int idTrabajadorFK ) throws SQLException
	{
		try
		{
			pool.Conectar();
			//INSERT INTO `gestioncitas`.`citas` (`horaCita`, `fechaCita`, `idClienteFK`, `idTrabajadorFK`, `idServicioFK`) VALUES ('10', '2023/05/01', '1', '1', '1');
			String cita = "INSERT INTO gestioncitas.citas (horaCita, fechaCita, idClienteFK, idTrabajadorFK, idServicioFK) VALUES ('"+ horaCita +"', '"+fechaCita+"', '"+idClienteFK+"', '"+idTrabajadorFK+"', '"+idServicioFK+"');";
			pool.statement.execute(cita);
		}
		catch (ServletException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();

	}

	//Mostrar informacion de citas al trabajador 
	public ArrayList<Citas> getCitas() throws ServletException, SQLException {
		ArrayList<Citas> listadoCitas = new ArrayList<Citas>();

		try
		{
			pool.Conectar();

			String infoCitas = "select *  from citas join clientes on citas.idClienteFK = clientes.idCliente join usuarios on usuarios.idUsuario = clientes.idUsuarioFK where fechaCita >= CURRENT_DATE() AND idCliente;";
			ResultSet rs = pool.statement.executeQuery(infoCitas);
			Citas infoCita;

			try
			{
				while(rs.next())
				{
					// obtener el nombre y apellidos del usuario
					String nombreUsuario = rs.getString("nombreUsuario");
					String apellidosUsuario = rs.getString("apellidosUsuario");
					String telefonoUsuario = rs.getString("telefonoUsuario");
					Usuarios usuario1 = new Usuarios(nombreUsuario, apellidosUsuario,telefonoUsuario);
					infoCita = new Citas(rs.getInt("idCita"),rs.getTime("horaCita"), rs.getDate("fechaCita"), rs.getInt("idClienteFK"),rs.getInt("idTrabajadorFK"), rs.getInt("idServicioFK"),nombreUsuario,apellidosUsuario, telefonoUsuario);
					listadoCitas.add(infoCita);
				}
			} catch (SQLException e)
			{		
				e.printStackTrace();
			} 
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}

		pool.cerrarConexion();
		return listadoCitas;
	}

	//Mostrar informacion de citas al cliente
	public ArrayList<Citas> getInfoCitas(int idCliente) throws ServletException, SQLException {
		ArrayList<Citas> listaCitas = new ArrayList<Citas>();
		//Usamos idCliente de session

		try
		{
			pool.Conectar();
			String citas = "select * from citas join clientes on citas.idClienteFK = clientes.idCliente join usuarios on usuarios.idUsuario = clientes.idUsuarioFK where fechaCita >= CURRENT_DATE() AND idCliente = "+idCliente+" ORDER BY fechaCita ASC;";			
			ResultSet rs = pool.statement.executeQuery(citas);
			Citas cita;
			try
			{
				while(rs.next())
				{
					// obtener el nombre y apellidos del usuario
					String nombreUsuario = rs.getString("nombreUsuario");
					String apellidosUsuario = rs.getString("apellidosUsuario");
					String telefonoUsuario = rs.getString("telefonoUsuario");
					Usuarios usuario = new Usuarios(nombreUsuario, apellidosUsuario,telefonoUsuario);
					cita = new Citas(rs.getInt("idCita"),rs.getTime("horaCita"), rs.getDate("fechaCita"), rs.getInt("idClienteFK"),rs.getInt("idTrabajadorFK"), rs.getInt("idServicioFK"),nombreUsuario,apellidosUsuario, telefonoUsuario);
					listaCitas.add(cita);
				}

			} catch (SQLException e)
			{		
				e.printStackTrace();
			} 
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}

		pool.cerrarConexion();

		return listaCitas;	              

	}
	//Eliminar cita
	public void eliminarCita(int idCita) throws SQLException
	{
		try
		{
			pool.Conectar();
			String borrarCita = "DELETE FROM citas WHERE idCita = "+idCita+";";
			pool.statement.execute(borrarCita);
		}
		catch (ServletException e)
		{

			e.printStackTrace();
		}

		pool.cerrarConexion();
	}



	//Devolver la información de servicios
	public int tamano()
	{
		return listaservicios.size();
	}
	public int getIdServicio (int idServicio)
	{
		return listaservicios.get(idServicio).getIdServicio();
	}
	public String getTipoServicio(int idServicio)
	{
		return listaservicios.get(idServicio).getTipoServicio();
	}
	public Time getDuracionServicio (int idServicio)
	{
		return listaservicios.get(idServicio).getDuracionServicio();
	}
	public String getDescripcionServicio(int idServicio)
	{
		return listaservicios.get(idServicio).getDescripciónServicio();
	}
	public Float getPrecioServicio (int idServicio)
	{
		return listaservicios.get(idServicio).getPrecioServicio();
	}
	public Time getHoraCita (int idCita)
	{
		return listadoCitas.get(idCita).getHoraCita();
	}
	public Date getFechaCita (int idCita)
	{
		return listadoCitas.get(idCita).getFechaCita();
	}
	public int getIdClienteFK (int idCita)
	{
		return listadoCitas.get(idCita).getIdClienteFK();
	}

	//metodo horas
	public class Hora {
		private int hora;
		private int minutos;

		public Hora(int hora, int minutos) {
			this.hora = hora;
			this.minutos = minutos;
		}

		public int getHora() {
			return hora;
		}

		public int getMinutos() {
			return minutos;
		}
	}

}
