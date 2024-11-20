package persistencia;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import Controllers.LearningPathSystem;
import caminosActividades.CaminoAprendizaje;
import usuarios.Usuario;

public class CentralPersistencia {

    // Ruta de directorio para almacenar datos
    public static final String direccionArchivo = System.getProperty("user.dir") + "/datos";

    private PersistenciaUsuario persistenciaUsuario;
    private PersistenciaCaminoAprendizaje persistenciaCaminoAprendizaje;

    public CentralPersistencia() {
        this.persistenciaUsuario = new PersistenciaUsuario();
        this.persistenciaCaminoAprendizaje = new PersistenciaCaminoAprendizaje();

        // Crear el directorio si no existe
        crearDirectorioSiNoExiste();
    }

    // Método para crear el directorio donde se almacenarán los archivos
    private void crearDirectorioSiNoExiste() {
        File directorio = new File(direccionArchivo);
        if (!directorio.exists()) {
            directorio.mkdir();
        }
    }

    // Método para salvar los datos de los usuarios, actividades y caminos de aprendizaje
    public void salvarDatos(HashMap<String,Usuario> usuario, HashMap<String, CaminoAprendizaje> camino) throws IOException {
        // Aquí se delega la lógica a las clases especializadas
        persistenciaUsuario.salvarUsuarios(usuario, direccionArchivo + "/usuarios.json");
        persistenciaCaminoAprendizaje.salvarCaminos(camino, direccionArchivo + "/caminos.json");
    }

    // Método para cargar los datos de los usuarios, actividades y caminos de aprendizaje
    public LearningPathSystem cargarDatos() throws IOException {
        // Cargar usuarios
        HashMap<String, Usuario> usuarios = persistenciaUsuario.cargarUsuarios(direccionArchivo + "/usuarios.json");
        	
        // Cargar caminos de aprendizaje
        HashMap<String, CaminoAprendizaje> caminos = persistenciaCaminoAprendizaje.cargarCamino(direccionArchivo + "/caminos.json");
        
        return new LearningPathSystem(usuarios, caminos);
    }
}
