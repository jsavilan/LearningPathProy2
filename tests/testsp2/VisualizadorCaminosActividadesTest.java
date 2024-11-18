package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.ControladorFuncionesGenerales;
import Controllers.LearningPathSystem;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import serviceProviders.VisualizadorCaminosActividades;
import usuarios.Estudiante;
import usuarios.Usuario;

class VisualizadorCaminosActividadesTest {

	private LearningPathSystem LPS;
    private ControladorFuncionesGenerales controladorGen;
    private CaminoAprendizaje camino;
    private List<String> objetivos = Arrays.asList("Obj1", "Obj2");
    private ActividadRecurso ar;
    
	@BeforeEach
	public void setUp() {
        LPS = new LearningPathSystem();
        controladorGen = new ControladorFuncionesGenerales(LPS);
        
        camino = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, "Juan");

        // Agregar actividad al camino
        int[] fechaLim = {2024, 12, 31};
        ar = new ActividadRecurso("AR", "Descripcion", objetivos, 2.5, 30, fechaLim, true, "recurso.com", "Instrucciones", "Juan");
        camino.addActividad(ar);
        ar.addResenia("Reseña1");

        // Añadir el camino al sistema
        LPS.addCamino(camino);

        // Crear un estudiante
        Estudiante estudiante = new Estudiante("Maria", "maria123", Usuario.ESTUDIANTE);
	}
	
	@Test
	public void verCaminoTest() {
		HashMap<String, CaminoAprendizaje> caminosDisponibles = controladorGen.getCaminos();

        // Verificar que el camino se añadió correctamente
        assertNotNull(caminosDisponibles, "El camino no esta disponible.");
        assertTrue(caminosDisponibles.containsKey("Camino"), "El camino no existe.");
        
        CaminoAprendizaje caminoObtenido = caminosDisponibles.get("Camino");
        
        assertNotNull(caminoObtenido);
        assertEquals("Camino", caminoObtenido.getTitulo(), "El titulo del camino no coincide.");
        assertEquals(objetivos, caminoObtenido.getObjetivos(), "Los objetivos no coindiden.");
	}
	
	@Test 
	public void verActividadTest() {
		//Para verificar los prints del sistema
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        
        VisualizadorCaminosActividades.verActividad(ar);
        
        String output = outContent.toString();
        
        assertTrue(output.contains("Nombre: AR"));
        assertTrue(output.contains("Tipo: Actividad de Recurso"));
        assertTrue(output.contains("Descripcion"));
        assertTrue(output.contains("Dificultad: 2.5"));
	}
	
	@Test
	public void addReseniaTest() {
		assertNotNull(ar.getResenias(), "No hay reseñas guardadas.");
		assertEquals("Reseña1", ar.getResenias().get(0), "La reseña no coincide.");
	}
	
	@Test
	public void verReseniasTest() {
		//Para verificar los prints del sistema
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		        
		VisualizadorCaminosActividades.verResenias(ar);
		        
		String output = outContent.toString();
		
		assertTrue(output.contains("Reseña1"), "La reseña no coincide.");
		assertTrue(output.contains("Reseñas de "+ar.getNombre()+":"));
		assertTrue(output.contains("Rating total: "));
	}

}
