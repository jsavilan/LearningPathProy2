package caminosActividades;

import java.util.HashMap;
import java.util.List;

import DatosEstudiante.DatosEstudianteActividad;

public class ActividadRecurso extends Actividad {

	private String recurso;
	private String instrucciones;
	
	public ActividadRecurso(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, String recurso, String instrucciones, String creadorLogin) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, creadorLogin);
		this.recurso = recurso;
		this.instrucciones = instrucciones;
		this.type=ACTIVIDADRECURSO;
	}
	
	public ActividadRecurso(String creadorLogin, ActividadRecurso ActividadOG)
	{
		super(creadorLogin, ActividadOG);
		
		this.recurso = ActividadOG.getRecurso();
		this.instrucciones = ActividadOG.getInstrucciones();
		this.type=ACTIVIDADRECURSO;

	}
	
	

	public ActividadRecurso(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, double rating, int ratingsTotales, List<String> resenias,
			String creadorLogin, String type, HashMap<String, DatosEstudianteActividad> datosEstudiantes,
			String recurso, String instrucciones) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, rating, ratingsTotales, resenias, creadorLogin, type, datosEstudiantes);
		this.recurso = recurso;
		this.instrucciones = instrucciones;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}
	
	
}
