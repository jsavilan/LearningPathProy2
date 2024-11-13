package Controllers;

import java.util.List;

import caminosActividades.Actividad;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import serviceProviders.CreadorCaminosActividades;
import serviceProviders.VisualizadorEnviosCalificacionesEstados;
import serviceProviders.ActualizadorCalificacionesExitoso;
import usuarios.Profesor;

public class ControladorProfesor {

	public void crearCamino(String titulo, String descripcion, List<String> objetivos, double dificultad, 
			Profesor profesor, LearningPathSystem LPS)
	{
		try 
		{
			CreadorCaminosActividades.crearCaminoCero(titulo, descripcion, objetivos, dificultad, profesor, LPS);
		} catch (Exception e) {
			System.out.println("No se pudo crear el camino");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void crearTareaCero(CaminoAprendizaje camino, String nombre, String descripcion,
			List<String> objetivos, double dificultad, int duracion, int[] fechaLim, boolean obligatoria, 
			String instrucciones, Profesor profesor)
	{
		CreadorCaminosActividades.crearTareaCero(camino, nombre, descripcion, objetivos, dificultad, duracion,
				fechaLim, obligatoria, instrucciones, profesor);
	}
	
	public static void crearQuizCero(CaminoAprendizaje camino, String nombre, String descripcion,
			List<String> objetivos, double dificultad, int duracion, int[] fechaLim, boolean obligatoria, 
			double calificacionMin, List<PreguntaQuiz> preguntas, Profesor profesor)
	{
		CreadorCaminosActividades.crearQuizCero(camino, nombre, descripcion, objetivos, dificultad, duracion,
				fechaLim, obligatoria, calificacionMin, preguntas, profesor);
	}
	
	public static void crearExamenCero(CaminoAprendizaje camino, String nombre, String descripcion,
			List<String> objetivos, double dificultad, int duracion, int[] fechaLim, boolean obligatoria, 
			double calificacionMin, List<String> preguntas, Profesor profesor)
	{
		CreadorCaminosActividades.crearExamenCero(camino, nombre, descripcion, objetivos, dificultad, 
				duracion, fechaLim, obligatoria, calificacionMin, preguntas, profesor);
	}
	
	public static void crearEncuestaCero(CaminoAprendizaje camino, String nombre, String descripcion,
			List<String> objetivos, double dificultad, int duracion, int[] fechaLim, boolean obligatoria, 
			List<String> preguntas, Profesor profesor)
	{
		CreadorCaminosActividades.crearEncuestaCero(camino, nombre, descripcion, objetivos, dificultad, 
				duracion, fechaLim, obligatoria, preguntas, profesor);
	}
	
	public static void crearARCero(CaminoAprendizaje camino, String nombre, String descripcion,
			List<String> objetivos, double dificultad, int duracion, int[] fechaLim, boolean obligatoria, 
			String recurso, String instrucciones, Profesor profesor)
	{
		CreadorCaminosActividades.crearARCero(camino, nombre, descripcion, objetivos, dificultad, duracion,
				fechaLim, obligatoria, recurso, instrucciones, profesor);
	}
	
	public static void clonarCamino(CaminoAprendizaje caminoOG, String tituloCamino, Profesor profesor, 
			LearningPathSystem LPS) 
	{
		try
		{
			CreadorCaminosActividades.clonarCamino(caminoOG, tituloCamino, profesor, LPS);
		}
		catch (Exception e) 
		{
			System.out.println("No se pudo crear el camino");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void clonarTarea(Tarea tareaOG, Profesor profesor, CaminoAprendizaje camino)
	{
		CreadorCaminosActividades.clonarTarea(tareaOG, profesor, camino);
	}
	
	public static void clonarExamen(Examen examenOG, Profesor profesor, CaminoAprendizaje camino)
	{
		CreadorCaminosActividades.clonarExamen(examenOG, profesor, camino);
	}
	
	public static void clonarQuiz(Quiz quizOG, Profesor profesor, CaminoAprendizaje camino)
	{
		CreadorCaminosActividades.clonarQuiz(quizOG, profesor, camino);
	}
	
	public static void clonarEncuesta(Encuesta encuestaOG, Profesor profesor, CaminoAprendizaje camino)
	{
		CreadorCaminosActividades.clonarEncuesta(encuestaOG, profesor, camino);
	}
	
	public static void clonarActividadRecurso(ActividadRecurso AROG, Profesor profesor, CaminoAprendizaje camino)
	{
		CreadorCaminosActividades.clonarActividadRecurso(AROG, profesor, camino);
	}
	
	public static void verEnviosActividad(CaminoAprendizaje camino, Actividad actividad) {
		VisualizadorEnviosCalificacionesEstados.verEnviosActividad(camino, actividad);
	}
	
	public static void verEnvioIndividual(CaminoAprendizaje camino, Actividad actividad, String loginEst) {
		VisualizadorEnviosCalificacionesEstados.verEnvioIndividual(camino, actividad, loginEst);
	}
	
	public static void calificarExamen(Examen examen, String loginEst, double calificacion, Profesor profesor, String estado) {
		ActualizadorCalificacionesExitoso.addCalificacionExamen(examen, loginEst, calificacion, profesor, estado);
	}
	
	public static void calificarTarea(Tarea tarea, String loginEst, Profesor profesor, String estado) {
		ActualizadorCalificacionesExitoso.calificarTarea(tarea, loginEst, profesor, estado);
	}
	
	
	

}
