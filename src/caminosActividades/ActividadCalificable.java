package caminosActividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DatosEstudiante.DatosEstudianteActividad;

public abstract class ActividadCalificable extends Actividad{
	protected double calificacionMin;
	protected List<Actividad> actividadesSigFracaso;
	
	public ActividadCalificable(String nombre, String descripcion, List<String> objetivos, double dificultad,
			int duracion, int[] fechaLim, boolean obligatoria, double calificacionMin, String creadorLogin) {
		
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, creadorLogin);
		this.calificacionMin = calificacionMin;
		this.actividadesSigFracaso=new ArrayList<Actividad>();
	}
	
	

	public ActividadCalificable(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria,  double rating, int ratingsTotales, List<String> resenias,
			String creadorLogin, String type, HashMap<String, DatosEstudianteActividad> datosEstudiantes,
			double calificacionMin, List<Actividad> actividadesSigFracaso) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, rating, ratingsTotales, resenias, creadorLogin, type, datosEstudiantes);
		this.calificacionMin = calificacionMin;
		this.actividadesSigFracaso = actividadesSigFracaso;
	}

	public ActividadCalificable(String creadorLogin, ActividadCalificable ActividadOG)
	{
		super(creadorLogin, ActividadOG);
		this.calificacionMin=ActividadOG.getCalificacionMin();
		this.actividadesSigFracaso=new ArrayList<Actividad>();

	}
	
	
	public double getCalificacionMin() {
		return calificacionMin;
	}

	public void setCalificacionMin(double calificacionMin) {
		this.calificacionMin = calificacionMin;
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
