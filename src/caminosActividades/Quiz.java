package caminosActividades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import DatosEstudiante.DatosEstudianteActividad;

public class Quiz extends ActividadCalificable{
	private List<PreguntaQuiz> preguntas;
	
	public Quiz(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria, double calificacionMin, List<PreguntaQuiz> preguntas, 
			String creadorLogin) 
	{
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, calificacionMin, creadorLogin);
		this.preguntas = preguntas;
		this.type=QUIZ;

	}
	
	public Quiz(String creadorLogin, Quiz ActividadOG)
	{
		super(creadorLogin, ActividadOG);
		this.type=QUIZ;

		this.preguntas=new ArrayList<PreguntaQuiz>();
		
		Iterator<PreguntaQuiz> it1 = ActividadOG.getPreguntas().iterator(); 
		PreguntaQuiz pregunta;
    	
    	while (it1.hasNext())
    	{
    		pregunta=new PreguntaQuiz(it1.next());
    		this.preguntas.add(pregunta);
    	}
	}
	
	public Quiz(String nombre, String descripcion, List<String> objetivos, double dificultad, int duracion,
			int[] fechaLim, boolean obligatoria,  double rating, int ratingsTotales, List<String> resenias,
			String creadorLogin, String type, HashMap<String, DatosEstudianteActividad> datosEstudiantes,
			double calificacionMin, List<Actividad> actividadesSigFracaso, List<PreguntaQuiz> preguntas) {
		super(nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria,  rating, ratingsTotales, resenias, creadorLogin, type, datosEstudiantes,
				calificacionMin, actividadesSigFracaso);
		this.preguntas = preguntas;
	}

	public void addPregunta(PreguntaQuiz pregunta)
	{
		this.preguntas.add(pregunta);
	}
	
	public void delPregunta(PreguntaQuiz pregunta)
	{
		this.preguntas.remove(pregunta);
	}
	
	public void delPregunta(int pos)
	{
		this.preguntas.remove(pos);
	}

	public List<PreguntaQuiz> getPreguntas() {
		return preguntas;
	}

}
