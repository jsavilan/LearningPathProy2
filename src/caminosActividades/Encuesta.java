package caminosActividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import DatosEstudiante.DatosEstudianteActividad;


public class Encuesta extends Actividad{
	
	private List<String> preguntasAbiertas;
	
	public Encuesta(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, List<String> preguntasAbiertas, String creadorLogin) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, creadorLogin);
		this.preguntasAbiertas = preguntasAbiertas;
		this.type=ENCUESTA;

	}

	public Encuesta(String creadorLogin, Encuesta ActividadOG)
	{
		super(creadorLogin, ActividadOG);
		this.type=ENCUESTA;
		this.preguntasAbiertas=new ArrayList<String>();

		Iterator<String> it1 = ActividadOG.getPreguntasAbiertas().iterator(); 
    	
    	while (it1.hasNext())
    	{
    		this.preguntasAbiertas.add(it1.next());
    	}
	}
	
	

	public Encuesta(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, double rating, int ratingsTotales, List<String> resenias,
			String creadorLogin, String type, HashMap<String, DatosEstudianteActividad> datosEstudiantes,
			List<String> preguntasAbiertas) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, rating, ratingsTotales, resenias, creadorLogin, type, datosEstudiantes);
		this.preguntasAbiertas = preguntasAbiertas;
	}

	public void addPregunta(String pregunta)
	{
		this.preguntasAbiertas.add(pregunta);
	}
	
	public List<String> getPreguntasAbiertas() {
		return preguntasAbiertas;
	}

	

}
