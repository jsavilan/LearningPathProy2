package serviceProviders;

import java.util.List;

import DatosEstudiante.DatosEstudianteAR;
import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteEncuesta;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import DatosEstudiante.DatosEstudianteTarea;
import Envios.EnvioQuiz;
import caminosActividades.ActividadRecurso;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import usuarios.Estudiante;
import usuarios.Profesor;

public class ActualizadorCalificacionesExitoso {
	
	public static boolean addCalificacionExamen(Examen examen, String loginEst, double calificacion, Profesor profesor, String estado) {
		if (profesor.getLogin().equals(examen.getCreadorLogin())) {
			DatosEstudianteActividad datosEst = examen.getDatoEstudianteIndividual(loginEst);
			if (datosEst instanceof DatosEstudianteExamen) {
				DatosEstudianteExamen datosExamen = (DatosEstudianteExamen) datosEst;
				datosExamen.setCalificacion(calificacion);
				try {
					datosExamen.setEstado(estado);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			} else {
				System.out.println("Sólo se pueden calificar exámenes.");
				return false;
			}	
		} else {
			System.out.println("Sólo el profesor creador puede actulizar las calificaciones.");
			return false;
		}
	}
	
	public static boolean marcarCalificarQuiz(Quiz quiz, Estudiante estudiante) throws Exception {
		if (estudiante instanceof Estudiante) {
			String loginEst = estudiante.getLogin();
			DatosEstudianteActividad datosEst = quiz.getDatoEstudianteIndividual(loginEst);
			if (datosEst instanceof DatosEstudianteQuiz) {
				DatosEstudianteQuiz datosQuiz = (DatosEstudianteQuiz) datosEst;
				datosQuiz.finalizarQuiz(quiz);
				
				EnvioQuiz envioQuiz = datosQuiz.getEnvioQuiz();
				
				//Calculo de calificacion
				List<PreguntaQuiz> preguntasQuiz = quiz.getPreguntas();
		        int totalPreguntas = preguntasQuiz.size();
		        int respuestasCorrectas = 0;

		        for (PreguntaQuiz pregunta : preguntasQuiz) {
		            String respuestaCorrecta = pregunta.getRespuesta();
		            String respuestaUsuario = envioQuiz.getRespuestas().get(pregunta);

		            if (respuestaUsuario != null && respuestaUsuario.equals(respuestaCorrecta)) {
		                respuestasCorrectas++;
		            }
		        }
		        
		        double calificacion = (respuestasCorrectas / (double) totalPreguntas) * 10; //Calificacion del 1 al 10
				// Set de calificacion y Estado
				datosQuiz.setCalificacion(calificacion);
				
				if (quiz.getCalificacionMin() > calificacion) {
					datosQuiz.setEstado(DatosEstudianteActividad.EXITOSO);
				} else {
					datosQuiz.setEstado(DatosEstudianteActividad.NOEXITOSO);
				}
				estudiante.setActividadActiva(false);
				return true;
				
			} else {
				System.out.println("Sólo se pueden calificar quizzes.");
				return false;
			}	
		} else {
			System.out.println("No es un estudinte.");
			return false;
		}
	}
	
	public static boolean marcarRecursoExitoso(ActividadRecurso actividad, Estudiante estudiante) throws Exception {
		String loginEst = estudiante.getLogin();
		DatosEstudianteActividad datosEst = actividad.getDatoEstudianteIndividual(loginEst);
		if (datosEst instanceof DatosEstudianteAR) {
			DatosEstudianteAR datosAR = (DatosEstudianteAR) datosEst;
			datosAR.finalizarAR();
			estudiante.setActividadActiva(false);
			return true;
		}
		return false;
	}
	
	public static boolean marcarTareaEnviada(Tarea tarea, Estudiante estudiante) throws Exception {
		String loginEst = estudiante.getLogin();
		DatosEstudianteActividad datosEst = tarea.getDatoEstudianteIndividual(loginEst);
		if (datosEst instanceof DatosEstudianteTarea) {
			DatosEstudianteTarea datosTarea = (DatosEstudianteTarea) datosEst;
			datosTarea.finalizarTarea();
			estudiante.setActividadActiva(false);
			return true;
		}
		return false;
	}
	
	public static boolean marcarEncuestaExitosa(Encuesta encuesta, Estudiante estudiante) throws Exception {
		String loginEst = estudiante.getLogin();
		DatosEstudianteActividad datosEst = encuesta.getDatoEstudianteIndividual(loginEst);
		if (datosEst instanceof DatosEstudianteEncuesta) {
			DatosEstudianteEncuesta datosEnc = (DatosEstudianteEncuesta) datosEst;
			datosEnc.finalizarEncuesta();
			estudiante.setActividadActiva(false);
			return true;
		}
		return false;
	}
	
	public static boolean calificarTarea(Tarea tarea, String loginEst, Profesor profesor, String estado) {
		if (profesor.getLogin().equals(tarea.getCreadorLogin())) {
			DatosEstudianteActividad datosEst = tarea.getDatoEstudianteIndividual(loginEst);
			if (datosEst instanceof DatosEstudianteTarea) {
				DatosEstudianteTarea datosTarea = (DatosEstudianteTarea) datosEst;
				try {
					datosTarea.setEstado(estado);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	
	public static void addMetodoEntrega(Tarea tarea, String loginEst, String metodoEntrega) {
		DatosEstudianteActividad datosAct = tarea.getDatoEstudianteIndividual(loginEst);
		if (datosAct instanceof DatosEstudianteTarea) {
			DatosEstudianteTarea datosTarea = (DatosEstudianteTarea) datosAct;
			datosTarea.setMetodoEntrega(metodoEntrega);
		}
	}
	
	public static boolean marcarExamenEnviado(Examen examen, Estudiante estudiante) throws Exception {
		String loginEst = estudiante.getLogin();
		DatosEstudianteActividad datosEst = examen.getDatoEstudianteIndividual(loginEst);
		if (datosEst instanceof DatosEstudianteExamen) {
			DatosEstudianteExamen datosEx = (DatosEstudianteExamen) datosEst;
			datosEx.finalizarExamen();
			estudiante.setActividadActiva(false);
			return true;
		}
		return false;
	}
	

}
