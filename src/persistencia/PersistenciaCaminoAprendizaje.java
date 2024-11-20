package persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;


public class PersistenciaCaminoAprendizaje {
	
	public final static String FORMATO_DATE = "EEE MMM dd HH:mm:ss zzz yyyy";
	
	private PersistenciaActividad persitenciaActividad = new PersistenciaActividad(); 

	public void salvarCaminos(HashMap<String,CaminoAprendizaje> caminos, String archivo) throws IOException {
        // Crear un JSONArray para almacenar los caminos
        JSONArray caminosJson = new JSONArray();
        
        // Formato de fecha para guardar las fechas
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_DATE);

        // Recorrer la lista de caminos de aprendizaje y convertir cada uno en JSONObject
        for (String titulo : caminos.keySet()) {
            JSONObject caminoObj = new JSONObject();
            
            CaminoAprendizaje camino = caminos.get(titulo);
            
            caminoObj.put("titulo", titulo);
            caminoObj.put("descripcion", camino.getDescripcion());
            caminoObj.put("dificultad", camino.getDificultad());
            caminoObj.put("creadorLogin", camino.getCreadorLogin());
            caminoObj.put("duracion", camino.getDuracion());
            
            Date date = camino.getFechaCreacion();
        
            caminoObj.put("fechaCreacion", dateFormat.format(date));
            caminoObj.put("version", camino.getVersion());
            caminoObj.put("rating", camino.getRating());
            caminoObj.put("ratingTotales", camino.getRatingsTotales());
            
            date = camino.getFechaModificacion();
            caminoObj.put("fechaModificacion", dateFormat.format(date)); 
            caminoObj.put("numActividadesObligatorias", camino.getNumActividadesObligatorias());

            // Convertir la lista de objetivos a JSONArray
            JSONArray objetivosArray = new JSONArray();
            for (String objetivo : camino.getObjetivos()) {
                objetivosArray.put(objetivo);
            }
            caminoObj.put("objectivos", objetivosArray);
            
            List<Actividad> actividades = camino.getActividades();
            JSONArray actividadesArray = persitenciaActividad.salvarActividades(actividades);
            
            caminoObj.put("Actividades", actividadesArray);

            // Añadir el camino al arreglo de caminos
            caminosJson.put(caminoObj);
        }

        // Guardar el archivo JSON en la ruta
        File file = new File(archivo);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(caminosJson.toString(4));
        }
	}

    public HashMap<String,CaminoAprendizaje> cargarCamino(String archivo) throws IOException {
        HashMap<String,CaminoAprendizaje> caminos = new HashMap<>();
        String contenido = leerArchivo(archivo);
        JSONArray caminosJson = new JSONArray(contenido);
        for (int i = 0; i < caminosJson.length(); i++) {
            JSONObject caminoObj = caminosJson.getJSONObject(i);
            
            String titulo = caminoObj.getString("titulo");
            String descripcion = caminoObj.getString("descripcion");
            double dificultad = caminoObj.getDouble("dificultad");
            String creadorLogin = caminoObj.getString("creadorLogin");
            int duracion = caminoObj.getInt("duracion");
            String fechaCreacionstr = caminoObj.getString("fechaCreacion");
            int version = caminoObj.getInt("version");
            double rating = caminoObj.getDouble("rating");
            int ratingsTotales = caminoObj.getInt("ratingTotales");
            String fechaModificacionstr = caminoObj.getString("fechaModificacion");
            int numActividadesObligatorias = caminoObj.getInt("numActividadesObligatorias");
            
            SimpleDateFormat dateFormat = new SimpleDateFormat(FORMATO_DATE);
            SimpleDateFormat dateFormatModificacion = new SimpleDateFormat(FORMATO_DATE);
            
            Date fechaCreacion = null;
            Date fechaModificacion = null;
            
            try {
				fechaCreacion = dateFormat.parse(fechaCreacionstr);
				fechaModificacion = dateFormatModificacion.parse(fechaModificacionstr);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            List<Actividad> actividades = new ArrayList<>();
            
            JSONArray objetivosArray = caminoObj.getJSONArray("objectivos");
            List<String> objetivos = new ArrayList<String>();
            
            for (int j = 0; j < objetivosArray.length(); j++) {
                objetivos.add(objetivosArray.getString(j));
            }
            
            CaminoAprendizaje camino = new CaminoAprendizaje(
            	    titulo, descripcion, objetivos, dificultad, duracion, fechaCreacion, rating, 
            	    ratingsTotales, version, fechaModificacion, numActividadesObligatorias, 
            	    actividades, creadorLogin);
            
            caminos.put(titulo,camino);
            
        }
        return caminos;
    }

    private String leerArchivo(String archivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(archivo)));
    }
}
