package persistencia;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import DatosEstudiante.*;
import caminosActividades.Actividad;
import caminosActividades.ActividadRecurso;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.Quiz;
import caminosActividades.Tarea;


public class PersistenciaDatosEstudiante {

	public static HashMap<String, DatosEstudianteActividad> cargarDatosUsuario(JSONObject actividadObj, String tipo) {
		
		HashMap<String, DatosEstudianteActividad> DatosEstudiantes = new HashMap<String, DatosEstudianteActividad>();
    	actividadObj.get("datosEstudiante"); //Me retorna un JSONObject
		
    	//Si es Examen, Encuesta, o Quiz hay que tener en cuenta 
    	
    	if ("EXAMEN".equals(tipo)) {
    		
        } else if ("QUIZ".equals(tipo)) {
           
        } else if ("TAREA".equals(tipo)) {
            
        } else if ("ACTIVIDADRECURSO".equals(tipo)) {
        	
        }
    	
		return DatosEstudiantes;
	}

	public JSONArray salvarDatosUsuario(Actividad actividad) {
		
		JSONArray datosUsuarios = new JSONArray();
		
		HashMap<String, DatosEstudianteActividad> datosMapa = actividad.getDatosEstudiantes();
		String tipo = actividad.getType();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(PersistenciaCaminoAprendizaje.FORMATO_DATE);
		
		for (String login: datosMapa.keySet()) {
			JSONObject datosUsuario = new JSONObject ();
			
			DatosEstudianteActividad usuario = datosMapa.get(login);
			
			Date fechaIncial = usuario.getFechaInicio();
			
			datosUsuario.put("login", login);
			datosUsuario.put("estado", usuario.getEstado());
			datosUsuario.put("fechaInicio", dateFormat.format(fechaIncial));
			
			Date fechaFinal;
			
			try {
				fechaFinal = usuario.getFechaFinal();
				datosUsuario.put("fechaFinal", dateFormat.format(fechaFinal));
			} catch (Exception e) {
				fechaFinal = null;
				datosUsuario.put("fechaFinal", "null");
			}
			
			if ("EXAMEN".equals(tipo)) {
	    		datosUsuario.put("calificacion", ((DatosEstudianteExamen) usuario).getCalificacion());
	    		//TODO: agregar a la clase el metodo de obtener y meter en el JSON la entrega
	        } else if ("QUIZ".equals(tipo)) {
	        	datosUsuario.put("calificacion", ((DatosEstudianteQuiz) usuario).getCalificacion());
	        	datosUsuario.put("envio", ((DatosEstudianteQuiz) usuario).getEnvioQuiz());
	        } else if ("TAREA".equals(tipo)) {
	            datosUsuario.put("metodoEntrega", ((DatosEstudianteTarea) usuario).getMetodoEntrega());
	        } else if ("ACTIVIDADRECURSO".equals(tipo)) {
	        	//datosUsuario.put("envio", ((DatosEstudianteEncuesta) usuario).getEnvioEncuesta()); TODO: Agregar envio a la clase de la encuesta
	        }
			
			datosUsuarios.put(datosUsuario);
		}
		
		return datosUsuarios;
	}

}
