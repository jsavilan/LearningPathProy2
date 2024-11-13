package persistencia;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import DatosEstudiante.DatosEstudianteActividad;
import caminosActividades.*;
import persistencia.PersistenciaDatosEstudiante;


public class PersistenciaActividad {
	
	public final static String FORMATO_DATE = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	private PersistenciaDatosEstudiante persisteciaDatos = new PersistenciaDatosEstudiante();
	
	public JSONArray salvarActividades(List<Actividad> actividades) throws IOException {
	    JSONArray actividadesJson = new JSONArray();
	    
	    for (Actividad actividad : actividades) {
	        JSONObject actividadObj = new JSONObject();
	        
	        actividadObj.put("tipo", actividad.getType());
	        actividadObj.put("nombre", actividad.getNombre());
	        actividadObj.put("descripcion", actividad.getDescripcion());
	        
	        JSONArray objetivosArray = new JSONArray(actividad.getObjetivos());
	        actividadObj.put("objectivos", objetivosArray);
	        
	        actividadObj.put("dificultad", actividad.getDificultad());
	        actividadObj.put("duracion", actividad.getDuracion());
	        
	        int[] fechaLim = actividad.getFechaLim();
	        JSONArray fechaLimArray = new JSONArray();
	        for (int fecha : fechaLim) {
	            fechaLimArray.put(fecha);
	        }
	        actividadObj.put("fechaLim", fechaLimArray);
	        
	        actividadObj.put("obligatoria", actividad.isObligatoria());
	        actividadObj.put("creadorLogin", actividad.getCreadorLogin());
	        actividadObj.put("rating", actividad.getRating());
	        actividadObj.put("ratingTotales", actividad.getRatingTotales());
	        
	        JSONArray reseniasArray = new JSONArray(actividad.getResenias());
	        actividadObj.put("resenias", reseniasArray);
	        
	        JSONArray datosEstudianteArray = new JSONArray();
	        salvarDatosEstudianteDesdeJson(datosEstudianteArray, actividad);
	        actividadObj.put("datosEstudiante", datosEstudianteArray);
	        
	        actividadesJson.put(actividadObj);
	    }
	    
	    return actividadesJson;
	}

    public List<Actividad> cargarActividades(String archivo) throws IOException {
        List<Actividad> actividades = new ArrayList<>();
        String contenido = leerArchivo(archivo);
        JSONArray actividadesJson = new JSONArray(contenido);

        for (int i = 0; i < actividadesJson.length(); i++) {
            JSONObject actividadObj = actividadesJson.getJSONObject(i);
            String tipo = actividadObj.getString("tipo");  

            Actividad actividad = crearActividadDesdeJson(actividadObj);
            
            if ("EXAMEN".equals(tipo)) {
                
            } else if ("QUIZ".equals(tipo)) {
               
            } else if ("TAREA".equals(tipo)) {
                
            } else if ("ENCUESTA".equals(tipo)) {
                
            } else if ("ACTIVIDADRECURSO".equals(tipo)) {
            	
            }

            actividades.add(actividad);
        }

        return actividades;
    }
    
    private Actividad crearActividadDesdeJson(JSONObject actividadObj) {
        String tipo = actividadObj.getString("tipo");
        
        String nombre = actividadObj.getString("nombre");
        String descripcion = actividadObj.getString("descripcion");
        
        JSONArray objetivosArray = actividadObj.getJSONArray("objectivos");
        List<String> objetivos = new ArrayList<String>();
        
        for (int j = 0; j < objetivosArray.length(); j++) {
            objetivos.add(objetivosArray.getString(j));
        }
        
        double dificultad = actividadObj.getDouble("dificultad");
        int duracion = actividadObj.getInt("duracion");
        
        JSONArray fechaLimArray = actividadObj.getJSONArray("fechaLim");
        int[] fechaLim = new int[3];
        for (int j = 0; j < fechaLimArray.length(); j++) {
            fechaLim[j] = fechaLimArray.getInt(j);
        }
           
        boolean obligatoria = actividadObj.getBoolean("obligatoria");
        String creadorLogin = actividadObj.getString("creadorLogin");
        double rating = actividadObj.getDouble("rating");
        int ratingsTotales = actividadObj.getInt("ratingTotales");
        
        JSONArray reseniasArray = actividadObj.getJSONArray("resenias");
        List<String> resenias = new ArrayList<String>();
        
        for (int k = 0; k < objetivosArray.length(); k++) {
            resenias.add(reseniasArray.getString(k));
        }
        
        HashMap<String, DatosEstudianteActividad> datosEstudiante = crearDatosEstudianteDesdeJson(actividadObj, tipo);
        
        Actividad actividad = null;
        
        if ("EXAMEN".equals(tipo)) {
            actividad = new Examen(nombre,descripcion,objetivos,dificultad,duracion,fechaLim,obligatoria,rating,ratingsTotales,resenias,creadorLogin,tipo,datosEstudiante, rating, null, resenias);
        } else if ("QUIZ".equals(tipo)) {
            actividad = new Quiz(nombre,descripcion,objetivos,dificultad,duracion,fechaLim,obligatoria,rating,ratingsTotales,resenias,creadorLogin,tipo,datosEstudiante, 0, null, null);
        } else if ("TAREA".equals(tipo)) {
            actividad = new Tarea(nombre,descripcion,objetivos,dificultad,duracion,fechaLim,obligatoria,rating,ratingsTotales,resenias,creadorLogin,tipo,datosEstudiante, null, null);
        } else if ("ENCUESTA".equals(tipo)) {
            actividad = new Encuesta(nombre,descripcion,objetivos,dificultad,duracion,fechaLim,obligatoria,rating,ratingsTotales,resenias,creadorLogin,tipo,datosEstudiante, null);
        } else if ("ACTIVIDADRECURSO".equals(tipo)) {
        	actividad = new ActividadRecurso(nombre,descripcion,objetivos,dificultad,duracion,fechaLim,obligatoria,rating,ratingsTotales,resenias,creadorLogin,tipo,datosEstudiante, null, null);
        }

        return actividad;
    }

    private HashMap<String, DatosEstudianteActividad> crearDatosEstudianteDesdeJson(JSONObject actividadObj,String tipo) {
    	return PersistenciaDatosEstudiante.cargarDatosUsuario(actividadObj,tipo);
    }
    
    private void salvarDatosEstudianteDesdeJson(JSONArray actividadJson, Actividad actividad) {
    	PersistenciaDatosEstudiante.salvarDatosUsuario(actividadJson, actividad);
    }

    private String leerArchivo(String archivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(archivo)));
    }
}