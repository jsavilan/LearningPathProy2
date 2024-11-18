package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;

class CaminoAprendizajeTest {

	private CaminoAprendizaje camino;
    private List<String> objetivos;
    private String creadorLogin = "Juan";
    
    @BeforeEach
    public void setUp() {
        objetivos = Arrays.asList("Obj1", "Obj2");
        camino = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, creadorLogin);
    }
    
    @Test
    public void testCrearCamino() {
        assertNotNull(camino, "El camino de aprendizaje no se crea.");
        assertEquals("Camino", camino.getTitulo(), "Titulo no se guarda correctamente.");
        assertEquals("Descripcion", camino.getDescripcion(), "Descripcion no se guarda correctamente.");
        assertEquals(3.5, camino.getDificultad(), "Dificultad no se guarda correctamente.");
        assertEquals(creadorLogin, camino.getCreadorLogin(), "Creador no se registra correctamente.");
    }
    
    @Test
    public void testAgregarActividad() {
    	int[] fechaLim = {2024, 12, 31};
        ActividadRecurso ar = new ActividadRecurso("AR", "Descripcion", objetivos, 2.5, 30, fechaLim , true, "recurso.com",
        		"Instrucciones", creadorLogin);
        		
        camino.addActividad(ar);
        
        assertEquals(1, camino.getActividades().size(), "No hay solo una actividad.");
        assertTrue(camino.getActividades().get(0) instanceof ActividadRecurso, "Tipo de actividad incorrecto.");
        assertEquals(30, camino.getDuracion(), "Duracion no se guarda correctamente.");
        
    }
    
    @Test
    public void testEliminarActividad() {
    	int[] fechaLim = {2024, 12, 31};
        ActividadRecurso ar = new ActividadRecurso("AR", "Descripcion", objetivos, 2.5, 30, fechaLim , true, "recurso.com",
        		"Instrucciones", creadorLogin);		
        camino.addActividad(ar);
        assertEquals(1, camino.getActividades().size(), "No hay solo una actividad.");
        
        camino.delActividad(0);
        assertEquals(0, camino.getActividades().size(), "Actividad no eliminada.");
    }
    
    @Test
    public void testAgregarRating() {
        camino.addRating(10.0);
        camino.addRating(9.0);
        
        assertEquals(2, camino.getRatingsTotales(), "No hay dos ratings solamente.");
        assertEquals(9.5, camino.getRating(), "El promedio no se actualiza a 9.5");
    }
    
    @Test
    public void testAgregarObjetivo() {
        camino.addObjetivo("Objetivo 1");
        assertEquals(3, camino.getObjetivos().size(), "No se agrega el objetivo correctamente.");
    }
    
    @Test
    public void testEliminarObjetivo() {
    	camino.addObjetivo("Objetivo 1");
        assertEquals(3, camino.getObjetivos().size(), "No se agrega el objetivo correctamente.");
        
        camino.delObjetivo(0);
        assertEquals(2, camino.getObjetivos().size(), "No se elimina un objetivo.");
    }
    
    @Test
    public void testClonarCamino() {
    	int[] fechaLim = {2024, 12, 31};
        ActividadRecurso ar = new ActividadRecurso("AR", "Descripcion", objetivos, 2.5, 30, fechaLim , true, "recurso.com",
        		"Instrucciones", creadorLogin);
        camino.addActividad(ar);
        
        CaminoAprendizaje caminoClonado = new CaminoAprendizaje(camino, "Prof2", "Camino clonado");
        
        assertNotNull(caminoClonado, "El camino no se clona correctamente.");
        assertEquals("Camino clonado", caminoClonado.getTitulo(), "El titulo no es el correcto.");
        assertEquals("Prof2", caminoClonado.getCreadorLogin(), "El profesor clonador no es el dueño del camino clonado.");
        assertEquals(2, caminoClonado.getObjetivos().size(), "Los objetivos no se clonaron correctamente.");
        assertEquals(camino.getActividades().size(), caminoClonado.getActividades().size(),  "El número de actividades no coincide.");
        
        
    }

}
