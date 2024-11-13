package Controllers;

import java.util.HashMap;

import caminosActividades.Actividad;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import serviceProviders.ActualizadorCalificacionesExitoso;
import serviceProviders.Inscriptor;
import usuarios.Estudiante;

public class ControladorEstudiante {
	
	public void inscribirseCamino(Estudiante estudiante, CaminoAprendizaje camino)
	{
		Inscriptor.inscribirseCamino(camino, estudiante);
	}
	
	public void iniciarActividad(Estudiante estudiante, Actividad actividad)
	{
		try 
		{
			Inscriptor.iniciarActivad(actividad, estudiante);
		}
		catch (Exception e) 
		{
			System.out.println("No se pudo crear el camino");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public HashMap<String, String> getAvancesCaminos(LearningPathSystem LPS, Estudiante estudiante)
	{
		return Inscriptor.getAvancesCaminos(LPS, estudiante);
	}
	
	public void  marcarRecursoExitoso(ActividadRecurso activ, Estudiante estudiante) {
		ActualizadorCalificacionesExitoso.marcarRecursoExitoso(activ, estudiante);
	}
	
	public void marcarEncuestaExitosa(Encuesta encuesta, Estudiante estudiante) {
		ActualizadorCalificacionesExitoso.marcarEncuestaExitosa(encuesta, estudiante);
	}
	
	public void marcarTareaEnviada(Tarea tarea, Estudiante estudiante) {
		ActualizadorCalificacionesExitoso.marcarTareaEnviada(tarea, estudiante);
	} 
	
	public void addMetodoEntrega(Tarea tarea, String loginEst, String metodoEntrega) {
		ActualizadorCalificacionesExitoso.addMetodoEntrega(tarea, loginEst, metodoEntrega);
	}
	
	public void marcarExamenEviado(Examen examen, Estudiante estudiante) {
		ActualizadorCalificacionesExitoso.marcarExamenEnviado(examen, estudiante);
	}
	
	public void marcarCalificarQuiz(Quiz quiz, Estudiante estudiante) throws Exception {
		ActualizadorCalificacionesExitoso.marcarCalificarQuiz(quiz, estudiante);
	}
}
