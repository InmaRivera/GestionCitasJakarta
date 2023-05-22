package gestioncitas;
/**
 * 
 * @author Inma
 * clase usuarios
 *
 */
public class Usuarios
{
	private int idUsuario;
	private String usuario;
	private String apellidos;
	private String telefono;
	private String email;

	//constructor vacio
	public Usuarios()
	{
		idUsuario = 0;
		usuario = "";
		apellidos ="";
		telefono ="";
		email = "";
	}
	
	public Usuarios(int idUsuario, String usuario, String apellidos, String telefono, String email)
	{
		this.idUsuario = idUsuario;
		this.usuario = usuario;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
	}

	public Usuarios(String nombreUsuario, String apellidosUsuario, String telefonoUsuario)
	{
		this.usuario = nombreUsuario;
		this.apellidos = apellidosUsuario;
		this.telefono = telefonoUsuario;
	}

	//Getters and setters
	public int getIdUsuario()
	{
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario)
	{
		this.idUsuario = idUsuario;
	}

	public String getUsuario()
	{
		return usuario;
	}

	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getApellidos()
	{
		return apellidos;
	}

	public void setApellidos(String apellidos)
	{
		this.apellidos = apellidos;
	}

	public String getTelefono()
	{
		return telefono;
	}

	public void setTelefono(String telefono)
	{
		this.telefono = telefono;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

}


