package usuarios;

import java.util.ArrayList;
import java.util.List;

import caminosActividades.CaminoAprendizaje;

public class Estudiante extends Usuario {
	
	private List<String> historialCaminos;
	private List<String> intereses;
	private boolean actividadActiva=false;
	
	public Estudiante(String login, String password, String type) {
		super(login, password, type);
		this.historialCaminos=new ArrayList<String>();
		this.intereses=new ArrayList<String>();
	}
	
	
	public Estudiante(String login, String password, String type, List<String> historialCaminos, List<String> intereses,
			boolean actividadActiva) {
		super(login, password, type);
		this.historialCaminos = historialCaminos;
		this.intereses = intereses;
		this.actividadActiva = actividadActiva;
	}


	public void addCamino(String camino)
	{
		this.historialCaminos.add(camino);
	}
	
	public void addInteres(String interes)
	{
		this.intereses.add(interes);
	}
	
	public void delInteres(String interes)
	{
		this.intereses.remove(interes);
	}
	
	public void setActividadActiva(boolean bool)
	{
		this.actividadActiva=bool;
	}

	public List<String> getHistorialCaminos() {
		return historialCaminos;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public boolean isActividadActiva() {
		return actividadActiva;
	}
	
	
	

}
