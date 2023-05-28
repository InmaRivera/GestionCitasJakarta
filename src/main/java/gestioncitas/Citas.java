package gestioncitas;

import java.sql.Date;
import java.sql.Time;

public class Citas
{
	private int idCita;
	private Time horaCita;
	private Date fechaCita;
	private int idServicioFK;
	private int idClienteFK;
	private int idTrabajadorFK;
	private String nombreUsuario;
	private String apellidosUsuario ;
	private String telefonoUsuario;
	private int diaSemana;

	
	public Citas()
	{
		idCita = 0;
		idServicioFK = 0;
		idClienteFK  = 0;
		idTrabajadorFK  = 0;
	}

	public Citas(int idCita, Time horaCita, Date fechaCita, int idClienteFK, int idTrabajadorFK, int idServicioFK, String nombreUsuario, String apellidosUsuario, String telefonoUsuario)
	{
		this.idCita = idCita;
//		this.nombreUsuario = nombreUsuario;
//		this.apellidosUsuario = apellidosUsuario;
		this.horaCita = horaCita;
		this.fechaCita = fechaCita;
		this.idClienteFK = idClienteFK;
		this.idTrabajadorFK = idTrabajadorFK;
		this.idServicioFK = idServicioFK;
		this.nombreUsuario = nombreUsuario;
		this.apellidosUsuario = apellidosUsuario;
		this.telefonoUsuario = telefonoUsuario;
	}

	public Citas(Time hora, java.util.Date fecha, int idCliente, int idTrabajadorFK, int idServicio)
	{

		this.fechaCita = (Date) fecha;
		this.horaCita = hora;
		this.idClienteFK = idCliente;
		this.idTrabajadorFK = idTrabajadorFK;
		this.idServicioFK = idServicio;
	}
	public int getDiaSemana()
	{
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana)
	{
		this.diaSemana = diaSemana;
	}
	public String getTelefonoUsuario()
	{
		return telefonoUsuario;
	}

	public void setTelefonoUsuario(String telefonoUsuario)
	{
		this.telefonoUsuario = telefonoUsuario;
	}

	public int getIdCita()
	{
		return idCita;
	}

	public void setIdCita(int idCita)
	{
		this.idCita = idCita;
	}

	public Time getHoraCita()
	{
		return horaCita;
	}

	public void setHoraCita(Time horaCita)
	{
		this.horaCita = horaCita;
	}

	public Date getFechaCita()
	{
		return fechaCita;
	}

	public void setFechaCita(Date fechaCita)
	{
		this.fechaCita = fechaCita;
	}

	public int getIdServicioFK()
	{
		return idServicioFK;
	}

	public void setIdServicioFK(int idServicioFK)
	{
		this.idServicioFK = idServicioFK;
	}

	public int getIdClienteFK()
	{
		return idClienteFK;
	}

	public void setIdClienteFK(int idClienteFK)
	{
		this.idClienteFK = idClienteFK;
	}

	public int getIdTrabajadorFK()
	{
		return idTrabajadorFK;
	}

	public void setIdTrabajadorFK(int idTrabajadorFK)
	{
		this.idTrabajadorFK = idTrabajadorFK;
	}

	public String getNombreUsuario()
	{
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario)
	{
		this.nombreUsuario = nombreUsuario;
	}

	public String getApellidosUsuario()
	{
		return apellidosUsuario;
	}

	public void setApellidosUsuario(String apellidosUsuario)
	{
		this.apellidosUsuario = apellidosUsuario;
	}

	public void setUsuario(Usuarios usuario2)
	{
		this.setUsuario(usuario2);
		
	}

	public void setFechaCita(Date fechaCita2, Date fechaCita3)
	{
		this.setFechaCita(fechaCita2, fechaCita3);
		
	}

	
}
