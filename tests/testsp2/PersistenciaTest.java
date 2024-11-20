package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import persistencia.CentralPersistencia;
import persistencia.PersistenciaCaminoAprendizaje;
import persistencia.PersistenciaUsuario;
import serviceProviders.Inscriptor;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

class PersistenciaTest {
	
	private static ActividadRecurso ar;
	private static Encuesta encuesta;
	private static Examen examen;
    private static Quiz quiz;
    private static Tarea tarea;
    private static Profesor profesor;
    private static Estudiante estudiante;
    private static CaminoAprendizaje camino;
    private static List<PreguntaQuiz> listaPreg;
    
    private static ActividadRecurso recurso2;
    private static Encuesta encuesta2;
    private static Examen examen2;
    private static Quiz quiz2;
    private static Tarea tarea2;
    private static Estudiante estudiante2;
    private static CaminoAprendizaje camino2;
    
	@BeforeEach
	void setUp() throws Exception {
		estudiante = new Estudiante("estudiante1", "clave1", Usuario.ESTUDIANTE);
    	profesor = new Profesor("profesor", "clave2", Usuario.PROFESOR);

        List<String> objetivos = Arrays.asList("Obj1", "Obj2");
        camino = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, profesor.getLogin());

        int[] fechaLim = {2024, 12, 31};
        
        //Setup para Encuesta y Examen
        List<String> pregAb = Arrays.asList("Preg1", "Preg2");
        
        //Setup para Quiz
        listaPreg = new ArrayList<>();
        String[] opciones = {"a) A", "b) B", "c) C", "d) D"};
        String[] explicacion = {"Respuesta Correcta", "Respuesta Incorrecta", "Respuesta Incorrecta", "Respuesta Incorrecta"};
        PreguntaQuiz pregunta1 = new PreguntaQuiz(opciones, "¿Pregunta?", explicacion, "a");
        listaPreg.add(pregunta1);
        
        ar = new ActividadRecurso("AR", "Descripcion", objetivos, 1.5, 15, fechaLim, false, "recurso.com", "Instrucciones", "profesor");
        encuesta = new Encuesta("Encuesta", "Descripcion", objetivos, 2.0, 15, fechaLim, false, pregAb, "profesor");
        examen = new Examen("Examen", "Descripcion", objetivos, 4.0, 60, fechaLim, false, 6.0, pregAb, "profesor");
        quiz = new Quiz("Quiz", "Descripcion", objetivos, 3.0, 30, fechaLim, true, 6.0, listaPreg, "profesor");
        tarea = new Tarea("Tarea", "Descripcion", objetivos, 2.5, 45, fechaLim, true, "Instrucciones", "profesor");
        
        camino.addActividad(ar);
        camino.addActividad(encuesta);
        camino.addActividad(examen);
        camino.addActividad(quiz);
        camino.addActividad(tarea);
        
        //Inscribir al estudiante en el camino
        Inscriptor.inscribirseCamino(camino, estudiante);
        
        //BUENAS NOCHES 
        
        estudiante2 = new Estudiante("estudiante1", "password", Usuario.ESTUDIANTE);

        List<String> objetivos2 = Arrays.asList("Obj1", "Obj2","Obj3","Ob4");
        camino2 = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, "profesor1");

        int[] fechaLim2 = {2024, 12, 31};
        
        //Setup para Encuesta y Examen
        List<String> pregAb2 = Arrays.asList("Preg1", "Preg2");
        
        //Setup para  Quiz
        List<PreguntaQuiz> listaPreg2 = new ArrayList<>();
        String[] opciones2 = {"a) París", "b) Londres", "c) Berlín", "d) Madrid"};
        String[] explicacion2 = {"La respuesta es París", "Londres es incorrecto", "Berlín no es la capital de Francia", "Madrid es la capital de España"};
        PreguntaQuiz pregunta2 = new PreguntaQuiz(opciones2, "¿Cuál es la capital de Francia?", explicacion2, "a");
        listaPreg2.add(pregunta2);

        recurso2 = new ActividadRecurso("AR2", "Descripcion", objetivos2, 1.5, 15, fechaLim2, false, "recurso.com", "Instrucciones", "profesor1");
        encuesta2 = new Encuesta("Encuesta2", "Descripcion", objetivos2, 2.0, 15, fechaLim2, false, pregAb2, "profesor1");
        examen2 = new Examen("Examen2", "Descripcion", objetivos2, 4.0, 60, fechaLim2, true, 6.0, pregAb2, "profesor1");
        quiz2 = new Quiz("Quiz2", "Descripcion", objetivos2, 3.0, 30, fechaLim2, true, 6.0, listaPreg2, "profesor1");
        tarea2 = new Tarea("Tarea2", "Descripcion", objetivos2, 2.5, 45, fechaLim2, true, "Instrucciones", "profesor1");

        camino2.addActividad(recurso2);
        camino2.addActividad(encuesta2);
        camino2.addActividad(examen2);
        camino2.addActividad(quiz2);
        camino2.addActividad(tarea2);
        
        Inscriptor.inscribirseCamino(camino2, estudiante2);
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testSalvarUsuarios() throws IOException {
		
		HashMap<String, Usuario> usuarios = new HashMap<>();
		usuarios.put(estudiante.getLogin(), estudiante);
		usuarios.put(estudiante2.getLogin(), estudiante2);
		usuarios.put(profesor.getLogin(), profesor);

	    PersistenciaUsuario persistenciaUsuario = new PersistenciaUsuario();
		persistenciaUsuario.salvarUsuarios(usuarios, CentralPersistencia.direccionArchivo + "/usuariosTEST.json");

	    // Validación: Leer el archivo y validar contenido
	    HashMap<String, Usuario> usuariosCargados = null;
		try {
			usuariosCargados = persistenciaUsuario.cargarUsuarios(CentralPersistencia.direccionArchivo + "/usuariosTEST.json");
		} catch (IOException e) {
		}
	    assertNotNull(usuariosCargados, "La lista de usuarios cargados es nula.");
	    assertEquals(usuarios.size(), usuariosCargados.size(), "El número de usuarios no coincide.");

	    // Validar contenido específico
	    for (String usuario : usuarios.keySet()) {
	    	
	        assertEquals(usuarios.get(usuario).getLogin(), usuariosCargados.get(usuario).getLogin(), 
	                     "El login del usuario no coincide.");
	        assertEquals(usuarios.get(usuario).getType(), usuariosCargados.get(usuario).getType(), 
	                     "El tipo de usuario no coincide.");
	    }
		
	}
	
	@Test
	void testSalvarCaminos() throws IOException {
		HashMap<String, CaminoAprendizaje> caminos = new HashMap<>();
		caminos.put(camino.getTitulo(), camino);
		caminos.put(camino2.getTitulo(), camino2);

	    PersistenciaCaminoAprendizaje persistenciaCaminoAprendizaje = new PersistenciaCaminoAprendizaje();
		persistenciaCaminoAprendizaje.salvarCaminos(caminos, CentralPersistencia.direccionArchivo + "/caminosTEST.json");

	    // Validación: Leer el archivo y validar contenido
		HashMap<String, CaminoAprendizaje> caminosCargados = persistenciaCaminoAprendizaje.cargarCamino(CentralPersistencia.direccionArchivo + "/caminosTEST.json");
	    assertNotNull(caminosCargados, "La lista de caminos cargados es nula.");
	    assertEquals(caminos.size(), caminosCargados.size(), "El número de caminos no coincide.");

	    // Validar contenido específico
	    for (String titulo : caminos.keySet()) {
	        assertEquals(caminos.get(titulo).getTitulo(), caminosCargados.get(titulo).getTitulo(), 
	                     "El nombre del camino no coincide.");
	        assertEquals(caminos.get(titulo).getDescripcion(), caminosCargados.get(titulo).getDescripcion(), 
	                     "La descripción del camino no coincide.");
	        assertEquals(caminos.get(titulo).getObjetivos(), caminosCargados.get(titulo).getObjetivos(), 
	                     "Los objetivos del camino no coinciden.");
	    }
	}
}
	
