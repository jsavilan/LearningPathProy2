package testsp2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Controllers.LearningPathSystem;
import serviceProviders.CreadorUsuarios;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

class CreadorUsuariosTest {

	private CreadorUsuarios creadorUsuarios;
    private LearningPathSystem learningPathSystem;
    
    @BeforeEach
    public void setUp() {
        learningPathSystem = new LearningPathSystem();
        creadorUsuarios = new CreadorUsuarios();
    }
	
	@Test
	public void crearEstudiante() {
		creadorUsuarios.crearUsuario("Juan", "12345", Usuario.ESTUDIANTE, learningPathSystem);
        Usuario usuario = CreadorUsuarios.getUsuario("Juan");
        
        assertNotNull(usuario, "No se crea o guarda el usuario.");
        assertTrue(usuario instanceof Estudiante, "El usuario no es un estudiante.");
        assertEquals("12345", usuario.getPassword(), "La contraseña no se guarda apropiadamente.");
	}
	
	@Test
	public void crearProfesor() {
		creadorUsuarios.crearUsuario("P. Jose", "abcd", Usuario.PROFESOR, learningPathSystem);
        Usuario usuario = CreadorUsuarios.getUsuario("P. Jose");
        
        assertNotNull(usuario, "No se crea o guarda el usuario.");
        assertTrue(usuario instanceof Profesor, "El usuario no es un profesor.");
        assertEquals("abcd", usuario.getPassword(), "La contraseña no se guarda apropiadamente.");
	}
	
	@Test
    public void testAutenticarUsuarioExitoso() {
        creadorUsuarios.crearUsuario("Fernando", "123abc", Usuario.ESTUDIANTE, learningPathSystem);
        boolean autenticado = creadorUsuarios.autentificarUsuario("Fernando", "123abc");
        assertTrue(autenticado, "No se autentica correctamente.");
    }
	
	@Test
    public void testAutenticarUsuarioIncorrecto() {
        creadorUsuarios.crearUsuario("Fernando", "123abc", Usuario.ESTUDIANTE, learningPathSystem);
        boolean autenticado = creadorUsuarios.autentificarUsuario("Fernando", "abc123");
        assertFalse(autenticado, "No se debería poder autenticar.");
    }
	
	@Test
	public void testUsuarioDuplicado() {
	    creadorUsuarios.crearUsuario("Juan", "clave1", Usuario.ESTUDIANTE, learningPathSystem);
	    creadorUsuarios.crearUsuario("Juan", "clave2", Usuario.ESTUDIANTE, learningPathSystem);
	    Usuario usuario = CreadorUsuarios.getUsuario("Juan");
	    assertNotNull(usuario, "El usuario debería existir.");
	    assertEquals("clave1", usuario.getPassword(), "La contraseña del usuario no es la del original.");
	    assertNotEquals("clave2", usuario.getPassword(),  "La contraseña del usuario es la del duplicado.");
	}
	

}
