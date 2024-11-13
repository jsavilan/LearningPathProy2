package persistencia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class PersistenciaUsuario {

    // Método para guardar una lista de usuarios en un archivo JSON
    public void salvarUsuarios(HashMap<String,Usuario> usuarios, String archivo) throws IOException {
        JSONArray usuariosJson = new JSONArray();

        for (String login : usuarios.keySet()) { // String es el nombre del usuario, Usuario es lo asociado Objeto
            
        	Usuario usuario = usuarios.get(login);
        	
        	JSONObject usuarioObj = new JSONObject();
            usuarioObj.put("login", login);
            usuarioObj.put("password", usuario.getPassword());
            usuarioObj.put("type", usuario.getType());

            if (usuario instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuario;
                usuarioObj.put("actividadActiva", estudiante.isActividadActiva());

                // Guardar historial de caminos
                JSONArray historialArray = new JSONArray(estudiante.getHistorialCaminos());
                usuarioObj.put("historialCaminos", historialArray);

                // Guardar intereses
                JSONArray interesesArray = new JSONArray(estudiante.getIntereses());
                usuarioObj.put("intereses", interesesArray);
            } else if (usuario instanceof Profesor) {
                Profesor profesor = (Profesor) usuario;
                
                // Guardar caminos del profesor
                JSONArray caminosArray = new JSONArray(profesor.getCaminos());
                usuarioObj.put("caminos", caminosArray);
            }

            usuariosJson.put(usuarioObj);
        }

        // Guardar el archivo JSON
        File file = new File(archivo);
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.write(usuariosJson.toString(4)); // Con formato indentado
        }
    }

    // Método para cargar una lista de usuarios desde un archivo JSON
    public HashMap<String, Usuario> cargarUsuarios(String archivo) throws IOException {
        HashMap<String, Usuario> usuarios = new HashMap<>();
        String contenido = leerArchivo(archivo);
        JSONArray usuariosJson = new JSONArray(contenido);

        for (int i = 0; i < usuariosJson.length(); i++) {
            JSONObject usuarioObj = usuariosJson.getJSONObject(i);

            String login = usuarioObj.getString("login");
            String password = usuarioObj.getString("password");
            String type = usuarioObj.getString("type");

            if (type.equals(Usuario.ESTUDIANTE)) {
                // Cargar Estudiante
                JSONArray historialArray = usuarioObj.getJSONArray("historialCaminos");
                List<String> historialCaminos = new ArrayList<>();
                for (int j = 0; j < historialArray.length(); j++) {
                    historialCaminos.add(historialArray.getString(j));
                }

                JSONArray interesesArray = usuarioObj.getJSONArray("intereses");
                List<String> intereses = new ArrayList<>();
                for (int j = 0; j < interesesArray.length(); j++) {
                    intereses.add(interesesArray.getString(j));
                }

                boolean actividadActiva = usuarioObj.getBoolean("actividadActiva");

                Estudiante estudiante = new Estudiante(login, password, type, historialCaminos, intereses, actividadActiva);
                usuarios.put(login, estudiante);

            } else if (type.equals(Usuario.PROFESOR)) {
                // Cargar Profesor
                JSONArray caminosArray = usuarioObj.getJSONArray("caminos");
                List<String> caminos = new ArrayList<>();
                for (int j = 0; j < caminosArray.length(); j++) {
                    caminos.add(caminosArray.getString(j));
                }

                Profesor profesor = new Profesor(login, password, type, caminos);
                usuarios.put(login, profesor);
            }
        }

        return usuarios;
    }

    private String leerArchivo(String archivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(archivo)));
    }
}
