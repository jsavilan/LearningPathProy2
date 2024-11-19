package caminosActividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DatosEstudiante.DatosEstudianteActividad;

public class Tarea extends Actividad{

	private String instrucciones;
	private List<Actividad> actividadesSigFracaso;
	
	public Tarea(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, String instrucciones, String creadorLogin) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, creadorLogin);
		this.instrucciones = instrucciones;
		this.type=TAREA;

		this.actividadesSigFracaso=new ArrayList<Actividad>();
	}
	
	/**
	 * No copia actividades siguientes exitososas ni las de fracaso
	 * @param creadorLogin
	 * @param ActividadOG
	 */
	public Tarea(String creadorLogin, Tarea ActividadOG)
	{
		super(creadorLogin, ActividadOG);
		this.type=TAREA;

		this.actividadesSigFracaso=new ArrayList<Actividad>();
		
	}
	
	
	
	public Tarea(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, double rating, int ratingsTotales, List<String> resenias,
			String creadorLogin, String type, HashMap<String, DatosEstudianteActividad> datosEstudiantes,
			String instrucciones, List<Actividad> actividadesSigFracaso) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria,  rating, ratingsTotales, resenias, creadorLogin, type, datosEstudiantes);
		this.instrucciones = instrucciones;
		this.actividadesSigFracaso = actividadesSigFracaso;
	}

	public String getInstrucciones() {
		return instrucciones;
	}


	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}


	public List<Actividad> getActividadesSigFracaso() {
		return actividadesSigFracaso;
	}
	
	public void addActividadSigFracaso(Actividad actividad)
	{
		this.actividadesSigFracaso.add(actividad);
	}
	
	public void delActividadSigFracaso(Actividad actividad) 
	{
		this.actividadesSigFracaso.remove(actividad);
	}
	
	public void delActividadSigFracaso(int pos) 
	{
		this.actividadesSigFracaso.remove(pos);
	}
	
}
