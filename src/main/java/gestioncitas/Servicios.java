package gestioncitas;

import java.sql.Time;

public class Servicios
{
	private int idServicio;
	private String tipoServicio;
	private Time duracionServicio;
	private String descripcionServicio;
	private float precioServicio;
	
	public Servicios()
	{
		idServicio = 0;
		tipoServicio = "";
		descripcionServicio = "";
		precioServicio = 0f;
			
	}

	public Servicios(int idServicio, String tipoServicio, Time duracionServicio, String descripciónServicio, float precioServicio)
	{
		setIdServicio(idServicio);
		this.tipoServicio = tipoServicio;
		this.duracionServicio = duracionServicio;
		this.descripcionServicio = descripciónServicio;
		this.precioServicio = precioServicio;
		
	}

	public int getIdServicio()
	{
		return idServicio;
	}

	public void setIdServicio(int idServicio)
	{
		this.idServicio = idServicio;
	}

	public String getTipoServicio()
	{
		return tipoServicio;
	}

	public void setTipoServicio(String tipoServicio)
	{
		this.tipoServicio = tipoServicio;
	}

	public Time getDuracionServicio()
	{
		return duracionServicio;
	}

	public void setDuracionServicio(Time duracionServicio)
	{
		this.duracionServicio = duracionServicio;
	}

	public String getDescripciónServicio()
	{
		return descripcionServicio;
	}

	public void setDescripciónServicio(String descripcionServicio)
	{
		this.descripcionServicio = descripcionServicio;
	}

	public float getPrecioServicio()
	{
		return precioServicio;
	}

	public void setPrecioServicio(float precioServicio)
	{
		this.precioServicio = precioServicio;
	}
	

}
