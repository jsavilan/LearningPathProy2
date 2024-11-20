package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DatosEstudiante.DatosEstudianteAR;
import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteEncuesta;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import DatosEstudiante.DatosEstudianteTarea;
import Envios.EnvioQuiz;
import caminosActividades.ActividadRecurso;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Encuesta;
import caminosActividades.Examen;
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;
import caminosActividades.Tarea;
import serviceProviders.ActualizadorCalificacionesExitoso;
import serviceProviders.Inscriptor;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

class CalificacionesTest {

	private ActividadRecurso ar;
	private Encuesta encuesta;
	private Examen examen;
    private Quiz quiz;
    private Tarea tarea;
    private Profesor profesor;
    private Estudiante estudiante;
    private CaminoAprendizaje camino;
    private List<PreguntaQuiz> listaPreg;
    
    @BeforeEach
    public void setUp() {
    	estudiante = new Estudiante("estudiante1", "clave1", Usuario.ESTUDIANTE);
    	profesor = new Profesor("profesor", "clave2", Usuario.PROFESOR);

        List<String> objetivos = Arrays.asList("Obj1", "Obj2");
        camino = new CaminoAprendizaje("Camino", "Descripcion", objetivos, 3.5, "profesor");

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
    }
    
    //ACTIVIDAD DE RECURSO
    @Test
    public void marcarRecursoExitosoTest() throws Exception {
    	Inscriptor.iniciarActivad(ar, estudiante);
    	
    	DatosEstudianteAR datosAR = (DatosEstudianteAR) ar.getDatoEstudianteIndividual("estudiante1");
    	
    	boolean resultado = ActualizadorCalificacionesExitoso.marcarRecursoExitoso(ar, estudiante);
        assertTrue(resultado, "AR: No se pudo marcar el recurso como exitoso.");
        assertNotNull(datosAR.getFechaFinal(), "AR: No se marca la fecha final.");
        assertEquals(DatosEstudianteActividad.EXITOSO, datosAR.getEstado(), "AR: El estado no se marca como exitoso.");
        assertFalse(estudiante.isActividadActiva(), "AR: Hay una actividad activa cuando no deberia ser asi.");
    }
    
    //ENCUESTA
    @Test
    public void marcarEncuestaExitosaTest() throws Exception {
    	Inscriptor.iniciarActivad(encuesta, estudiante);
    	
    	DatosEstudianteEncuesta datosEnc = (DatosEstudianteEncuesta) encuesta.getDatoEstudianteIndividual("estudiante1");
    	
    	boolean resultado = ActualizadorCalificacionesExitoso.marcarEncuestaExitosa(encuesta, estudiante);
        assertTrue(resultado, "Enc: No se pudo marcar la encuesta como exitoso.");
        assertNotNull(datosEnc.getFechaFinal(), "Enc: No se marca la fecha final.");
        assertEquals(DatosEstudianteActividad.EXITOSO, datosEnc.getEstado(), "Enc: El estado no se marca como exitoso.");
        assertFalse(estudiante.isActividadActiva(), "Enc: Hay una actividad activa cuando no deberia ser asi.");
    }
    
    //EXAMEN
    @Test
    public void marcarExamenEnviadoTest() throws Exception {
    	Inscriptor.iniciarActivad(examen, estudiante);
    	
    	DatosEstudianteExamen datosEx = (DatosEstudianteExamen) examen.getDatoEstudianteIndividual("estudiante1");
    	
    	boolean resultado = ActualizadorCalificacionesExitoso.marcarExamenEnviado(examen, estudiante);
    	assertTrue(resultado, "Ex: No se pudo marcar el examen como enviado.");
        assertNotNull(datosEx.getFechaFinal(), "Ex: No se marca la fecha final.");
        assertEquals(DatosEstudianteActividad.ENVIADO, datosEx.getEstado(), "Ex: El estado no se marca como enviado.");
        assertFalse(estudiante.isActividadActiva(), "Ex: Hay una actividad activa cuando no deberia ser asi.");
    }
    
    @Test
    public void addCalificacionExamenTest() throws Exception {
    	
    	boolean resultadoEnviado = ActualizadorCalificacionesExitoso.marcarExamenEnviado(examen, estudiante);
    	assertTrue(resultadoEnviado, "Ex: No se pudo marcar el examen como enviado.");
    	
    	boolean resultadoCalif = ActualizadorCalificacionesExitoso.addCalificacionExamen(examen, "estudiante1", 7.0, profesor, DatosEstudianteActividad.EXITOSO);
    	assertTrue(resultadoCalif, "Ex: No se calificar el examen.");
    	
    	DatosEstudianteExamen datosEx = (DatosEstudianteExamen) examen.getDatoEstudianteIndividual("estudiante1");
    	assertEquals(7.0, datosEx.getCalificacion(), "Ex: La calificacion no coincide.");
    	assertEquals(DatosEstudianteActividad.EXITOSO, datosEx.getEstado(), "Ex: El estado no es el correcto.");
    	
    }
    
    //QUIZ
    @Test
    public void marcarCalificarQuizTest() throws Exception {
    	
    	DatosEstudianteQuiz datosQuiz = (DatosEstudianteQuiz) quiz.getDatoEstudianteIndividual("estudiante1");
        EnvioQuiz envioQuiz = datosQuiz.getEnvioQuiz();
        
        envioQuiz.agregarRespuesta(listaPreg.get(0), "a");
        
    	boolean resultado = ActualizadorCalificacionesExitoso.marcarCalificarQuiz(quiz, estudiante);
    	assertTrue(resultado, "Quiz: No se califica el quiz.");
    	
    	assertNotNull(datosQuiz.getFechaFinal(), "Ex: No se marca la fecha final.");
    	assertEquals(10.0, datosQuiz.getCalificacion(), "Ex: La calificacion no coincide.");
    }
    
    //TAREA
    @Test
    public void marcarTareaEnviadaTest() throws Exception {   	
    	boolean resultado = ActualizadorCalificacionesExitoso.marcarTareaEnviada(tarea, estudiante);
    	assertTrue(resultado, "Tarea: No se califica la tarea.");
    	
    	DatosEstudianteTarea datosTar = (DatosEstudianteTarea) tarea.getDatoEstudianteIndividual("estudiante1");
    	assertNotNull(datosTar.getFechaFinal(), "Ex: No se marca la fecha final.");
    	assertEquals(DatosEstudianteActividad.ENVIADO, datosTar.getEstado(), "Tarea: El estado no es el correcto.");
    }
    
    @Test
    public void addMetodoEntregaTest() {
    	ActualizadorCalificacionesExitoso.addMetodoEntrega(tarea, "estudiante1", "E-mail");
    	
    	DatosEstudianteTarea datosTar = (DatosEstudianteTarea) tarea.getDatoEstudianteIndividual("estudiante1");
    	assertEquals("E-mail", datosTar.getMetodoEntrega(), "El metodo de entrega no coincide.");
    }
    
    @Test
    public void calificarTareaTest() {
    	boolean resultado = ActualizadorCalificacionesExitoso.calificarTarea(tarea, "estudiante1", profesor, DatosEstudianteActividad.EXITOSO);
    	assertTrue(resultado, "Tarea: No se califica la tarea.");
    	
    	DatosEstudianteTarea datosTar = (DatosEstudianteTarea) tarea.getDatoEstudianteIndividual("estudiante1");
    	assertEquals(DatosEstudianteActividad.EXITOSO, datosTar.getEstado(), "Tarea: El estado no es el correcto.");
    	
    }
    
    
}
