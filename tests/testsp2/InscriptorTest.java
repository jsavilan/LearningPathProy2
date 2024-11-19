package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatosEstudiante.DatosEstudianteExamen;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import serviceProviders.Inscriptor;
import usuarios.Estudiante;

class InscriptorTest {

	private CaminoAprendizaje camino;
	private ActividadRecurso recurso;
	private Encuesta encuesta;
    private Examen examen;
    private Quiz quiz;
    private Tarea tarea;
    private Estudiante estudiante;

    @BeforeEach
    public void setUp() {
        estudiante = new Estudiante("estudiante1", "password", "Estudiante");

        List<String> objetivos = Arrays.asList("Obj1", "Obj2");
        camino = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, "profesor1");

        int[] fechaLim = {2024, 12, 31};
        
        //Setup para Encuesta y Examen
        List<String> pregAb = Arrays.asList("Preg1", "Preg2");
        
        //Setup paraara Quiz
        List<PreguntaQuiz> listaPreg = new ArrayList<>();
        String[] opciones = {"a) París", "b) Londres", "c) Berlín", "d) Madrid"};
        String[] explicacion = {"La respuesta es París", "Londres es incorrecto", "Berlín no es la capital de Francia", "Madrid es la capital de España"};
        PreguntaQuiz pregunta1 = new PreguntaQuiz(opciones, "¿Cuál es la capital de Francia?", explicacion, "a");
        listaPreg.add(pregunta1);

        recurso = new ActividadRecurso("AR", "Descripcion", objetivos, 1.5, 15, fechaLim, false, "recurso.com", "Instrucciones", "profesor1");
        encuesta = new Encuesta("Encuesta", "Descripcion", objetivos, 2.0, 15, fechaLim, false, pregAb, "profesor1");
        examen = new Examen("Examen", "Descripcion", objetivos, 4.0, 60, fechaLim, true, 6.0, pregAb, "profesor1");
        quiz = new Quiz("Quiz", "Descripcion", objetivos, 3.0, 30, fechaLim, true, 6.0, listaPreg, "profesor1");
        tarea = new Tarea("Tarea", "Descripcion", objetivos, 2.5, 45, fechaLim, true, "Instrucciones", "profesor1");

        camino.addActividad(recurso);
        camino.addActividad(encuesta);
        camino.addActividad(examen);
        camino.addActividad(quiz);
        camino.addActividad(tarea);
    }

    @Test
    public void testInscribirseCamino() {
        //Verificar la inscripcion al camino
        Inscriptor.inscribirseCamino(camino, estudiante);
        assertTrue(estudiante.getHistorialCaminos().contains("Camino"), "El estudiante no esta inscrito en el camino.");

        // Verificar que todas las actividades tengan datos de estudiante asociados
        assertNotNull(recurso.getDatoEstudianteIndividual("estudiante1"), "El recurso no tiene datos del estudiante.");
        assertNotNull(encuesta.getDatoEstudianteIndividual("estudiante1"), "La encuesta no tiene datos del estudiante.");
        assertNotNull(examen.getDatoEstudianteIndividual("estudiante1"), "El examen no tiene datos del estudiante.");
        assertNotNull(quiz.getDatoEstudianteIndividual("estudiante1"), "El quiz no tiene datos del estudiante.");
        assertNotNull(tarea.getDatoEstudianteIndividual("estudiante1"), "La tarea no tiene datos del estudiante.");
    }

    @Test
    public void testIniciarActividad() throws Exception {
        //Inscripcion al camino y empezar actividad
        Inscriptor.inscribirseCamino(camino, estudiante);
        Inscriptor.iniciarActivad(examen, estudiante);

        //Verificacion que la actividad este empezada
        DatosEstudianteExamen datosExamen = (DatosEstudianteExamen) examen.getDatoEstudianteIndividual("estudiante1");
        assertNotNull(datosExamen.getFechaInicio(), "La actividad no se ha iniciado correctamente.");
        assertTrue(estudiante.isActividadActiva(), "El estado del estudiante no está activo.");
    }

    @Test
    public void testIniciarActividadError() {
        estudiante.setActividadActiva(true);
        Exception exception = null;

        try {
            Inscriptor.iniciarActivad(quiz, estudiante);
        } catch (Exception e) {
            exception = e;
        }
        
        assertNotNull(exception, "Se esperaba una excepción al iniciar una actividad ya activa.");
        assertEquals("No se puede iniciar una nueva actividad porque ya hay una iniciada", exception.getMessage());
    }

}
