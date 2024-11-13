package persistencia;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import DatosEstudiante.DatosEstudianteActividad;
import caminosActividades.Actividad;
import caminosActividades.ActividadRecurso;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.Quiz;
import caminosActividades.Tarea;

public class PersisteciaDatosEstudiante {

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

	public static void salvarDatosUsuario(JSONArray actividadJson, Actividad actividad) {
		
		JSONObject datosMapaObj = new JSONObject(actividad.getDatosEstudiantes());
		
		actividadJson.put(datosMapaObj);
		
		/*
		String tipo = actividad.getType();
		
		if ("EXAMEN".equals(tipo)) {
    		
        } else if ("QUIZ".equals(tipo)) {
           
        } else if ("TAREA".equals(tipo)) {
            
        } else if ("ACTIVIDADRECURSO".equals(tipo)) {
        	
        }
		*/
	}

}
