package serviceProviders;

import java.util.HashMap;

import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;

public class VisualizadorEnviosCalificacionesEstados {
	
	public static void verEnviosActividad(CaminoAprendizaje camino, Actividad actividad) {
		System.out.println("Los envios para " + actividad.getNombre() + 
				" en el camino de aprendizaje " + camino.getTitulo() + " son: ");
		HashMap<String, DatosEstudianteActividad> envios = actividad.getDatosEstudiantes();
		
		for (String login : envios.keySet()) {
            DatosEstudianteActividad envio = envios.get(login);
            String estado = envio.getEstado();
            System.out.println("Login: " + login + ", Estado: " + estado);
		}

	}
	
	public static void verEnvioIndividual(CaminoAprendizaje camino, Actividad actividad, String login) {
		DatosEstudianteActividad datosEst = actividad.getDatoEstudianteIndividual(login);
		if (datosEst != null) {
	        System.out.println("Fecha de inicio: " + datosEst.getFechaInicio());

	        try {
	            System.out.println("Fecha final: " + datosEst.getFechaFinal());
	        } catch (Exception e) {
	            System.out.println("La actividad aún no ha sido finalizada.");
	        }
	        System.out.println("Estado: " + datosEst.getEstado());
	        if (datosEst instanceof DatosEstudianteQuiz) {
	        	DatosEstudianteQuiz datosQuiz = (DatosEstudianteQuiz) datosEst;
	            System.out.println("Calificación: " + datosQuiz.getCalificacion());
	        } else if (datosEst instanceof DatosEstudianteExamen) {
	        	DatosEstudianteExamen datosExamen = (DatosEstudianteExamen) datosEst;
	            System.out.println("Calificación: " + datosExamen.getCalificacion());
	        } else {
	    	System.out.println("No se encontró un envío para el estudiante con login: " + login);
	        }
		}
	}

}
