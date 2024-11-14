package persistencia;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import DatosEstudiante.*;
import Envios.*;
import caminosActividades.Actividad;


public class PersistenciaDatosEstudiante {

	public static HashMap<String, DatosEstudianteActividad> cargarDatosUsuario(JSONObject actividadObj, String tipo) {
		
		HashMap<String, DatosEstudianteActividad> DatosEstudiantes = new HashMap<>();
	    JSONArray datosEstudianteArray = actividadObj.getJSONArray("datosEstudiante");
	    SimpleDateFormat dateFormat = new SimpleDateFormat(PersistenciaCaminoAprendizaje.FORMATO_DATE);

	    for (int i = 0; i < datosEstudianteArray.length(); i++) {
	        JSONObject datosUsuario = datosEstudianteArray.getJSONObject(i);

	        String login = datosUsuario.getString("login");
	        String estado = datosUsuario.getString("estado");
	        
	        Date fechaInicio, fechaFinal;

	        try {
				fechaInicio = dateFormat.parse(datosUsuario.getString("fechaInicio"));
			} catch 	(JSONException | ParseException e) {
				fechaInicio = null;
			}
	        try {
				fechaFinal = dateFormat.parse(datosUsuario.getString("fechaFinal"));
			} catch (JSONException | ParseException e) {
				fechaFinal = null;
			}
	        
	        DatosEstudianteActividad usuario;

	        // Cargar datos adicionales según el tipo de actividad
	        if ("EXAMEN".equals(tipo)) {
	            double calificacion = datosUsuario.getDouble("calificacion");
	            usuario = new DatosEstudianteExamen(login, estado, fechaInicio, fechaFinal, calificacion);
	        } else if ("QUIZ".equals(tipo)) {
	            double calificacion = datosUsuario.getDouble("calificacion");
	            String stringEnvio = datosUsuario.getString("envio"); //Esto tiene que ser del tipo que es envioQuiz, cuando lo saca es str
	            HashMap<String, String> respuestas = new HashMap(); //TODO: agregar respuestas string al HashMap
	            EnvioQuiz envio = new EnvioQuiz();
	            usuario = new DatosEstudianteQuiz(login, estado, fechaInicio, fechaFinal, calificacion, envio);
	        } else if ("TAREA".equals(tipo)) {
	            String metodoEntrega = datosUsuario.getString("metodoEntrega");
	            usuario = new DatosEstudianteTarea(login, estado, fechaInicio, fechaFinal, metodoEntrega);
	        } else if ("ENCUESTA".equals(tipo)) {
	            String stringEnvio = datosUsuario.getString("envio");
	            HashMap<String, String> respuestas = new HashMap(); //TODO: agregar respuestas string al HashMap
	            EnvioEncuesta envio = new EnvioEncuesta(respuestas);
	            usuario = new DatosEstudianteEncuesta(login, estado, fechaInicio, fechaFinal);
	        } else {
	            usuario = new DatosEstudianteAR(login, estado, fechaFinal, fechaFinal);
	        }

	        DatosEstudiantes.put(login, usuario);
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
			// Salvar datos adicionales según el tipo de actividad
			if ("EXAMEN".equals(tipo)) {
	    		datosUsuario.put("calificacion", ((DatosEstudianteExamen) usuario).getCalificacion());
	    		//TODO: agregar a la clase el metodo de obtener y meter en el JSON la entrega
	        } else if ("QUIZ".equals(tipo)) {
	        	datosUsuario.put("calificacion", ((DatosEstudianteQuiz) usuario).getCalificacion());
	        	datosUsuario.put("envio", ((DatosEstudianteQuiz) usuario).getEnvioQuiz());
	        } else if ("TAREA".equals(tipo)) {
	            datosUsuario.put("metodoEntrega", ((DatosEstudianteTarea) usuario).getMetodoEntrega());
	        } else if ("ENCUESTA".equals(tipo))
	        	//datosUsuario.put("envio", ((DatosEstudianteEncuesta) usuario).getEnvioEncuesta()); TODO: Agregar envio a la clase de la encuesta
			
			datosUsuarios.put(datosUsuario);
		}
		
		return datosUsuarios;
	}

}
