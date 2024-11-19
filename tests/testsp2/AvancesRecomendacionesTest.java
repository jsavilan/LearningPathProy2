package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.LearningPathSystem;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Tarea;
import serviceProviders.ActualizadorCalificacionesExitoso;
import serviceProviders.Inscriptor;
import serviceProviders.VisualizadorCaminosActividades;
import usuarios.Estudiante;
import usuarios.Usuario;

class AvancesRecomendacionesTest {
	
	private Estudiante estudiante;
	private CaminoAprendizaje caminoJava;
	private CaminoAprendizaje caminoPython;
	private LearningPathSystem lps;
	
	@BeforeEach
	public void setUp() throws Exception {
		lps = new LearningPathSystem();
		//Usuarios a Utilizar
		estudiante = new Estudiante("Estudiante", "clave", Usuario.ESTUDIANTE);
    	
    	lps.addUsuario(estudiante);
    	
    	//Caminos a utilizar
        List<String> objetivos = Arrays.asList("Obj1", "Obj2");
        caminoJava = new CaminoAprendizaje("Camino Java", "Descripcion", objetivos, 5.5, "Profesor Java");
        caminoPython = new CaminoAprendizaje("Camino Python", "Descripcion", objetivos, 3.5, "Profesor Python");
        lps.addCamino(caminoJava);
        lps.addCamino(caminoPython);

        //Actividades a utilizar
        int[] fechaLim = {2024, 12, 31};
        ActividadRecurso arJava= new ActividadRecurso("AR Java", "Descripcion", objetivos, 1.5, 15, fechaLim, true, "Java.com", "Instrucciones", "Profesor Java");
        Tarea tareaJava = new Tarea("Tarea Java", "Descripcion", objetivos, 2.5, 45, fechaLim, true, "Instrucciones", "Profesor Java");
        ActividadRecurso arPython= new ActividadRecurso("AR Python", "Descripcion", objetivos, 1.5, 15, fechaLim, true, "Python.com", "Instrucciones", "Profesor Python");
        
        caminoJava.addActividad(arJava);
        caminoJava.addActividad(tareaJava);
        caminoPython.addActividad(arPython);
        
        Inscriptor.inscribirseCamino(caminoJava, estudiante);
        Inscriptor.iniciarActivad(arJava, estudiante);
        ActualizadorCalificacionesExitoso.marcarRecursoExitoso(arJava, estudiante);
        Inscriptor.iniciarActivad(tareaJava, estudiante);
        
        
	}
	
	@Test
	public void addInteresTest() {
		estudiante.addInteres("Python");
		assertTrue(estudiante.getIntereses().contains("Python"));
	}
	
	@Test
	public void delInteresTest() {
		estudiante.addInteres("Python");
		estudiante.addInteres("Java");
		assertEquals(2, estudiante.getIntereses().size());
		
		estudiante.delInteres("Python");
		assertEquals(1, estudiante.getIntereses().size());
		assertFalse(estudiante.getIntereses().contains("Python"));
	}
	
	@Test
	public void getRecomendacionesTest() {
		estudiante.addInteres("Python");
		estudiante.addInteres("Java");
		estudiante.addInteres("Programacion");
		assertEquals(3, estudiante.getIntereses().size());
		
		caminoJava.addEtiqueta("Java");
		caminoPython.addEtiqueta("Python");
		
		List<String> recomendaciones = VisualizadorCaminosActividades.getRecomendaciones(estudiante, lps);
		
		assertEquals(2, recomendaciones.size(), "No se recomendaron dos caminos.");
		assertTrue(recomendaciones.contains("Camino Java"), "No se recomendo el camino de Java.");
		assertTrue(recomendaciones.contains("Camino Python"), "No se recomendo el camino de Python.");
	}
	
	@Test
	public void verAvanceCaminoTest() {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        VisualizadorCaminosActividades.verAvanceCamino(caminoJava, "Estudiante");
        
        String output = outContent.toString();
        
        assertTrue(output.contains("Avance de actividades:"), "No contiene el header.");
        assertEquals(2, caminoJava.getNumActividadesObligatorias(), "Las actividades obligatorias no son 2.");
        assertTrue(output.contains("Porcentaje completado: 50%"), "El porcentaje completado no es correcto.");
        
	}
	
	@Test
	public void verAvancesTest() {
		HashMap<String, String> avances = new HashMap<>();
        avances.put("Camino Java", "50%");
		
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        VisualizadorCaminosActividades.verAvances(avances);
        
        String output = outContent.toString();
        
        assertTrue(output.contains("Camino Java 50%"), "No esta el avance.");
	}
}
