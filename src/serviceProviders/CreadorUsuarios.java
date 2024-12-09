package serviceProviders;

import Controllers.LearningPathSystem;
import java.util.HashMap;
import java.util.Map;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class CreadorUsuarios {

	private static Map<String, Usuario> usuariosRegistrados;
	
	public CreadorUsuarios() {
		usuariosRegistrados = new HashMap<>();
	}
	
	public boolean crearUsuario(String login, String password, String type, LearningPathSystem LPS) {
		if (usuariosRegistrados.containsKey(login)) {
            return false;
        } else {
        	
        	if (type.equals(Usuario.ESTUDIANTE)) {
        		Usuario nuevoUsuario = new Estudiante(login, password, type);  //Se puede poner sin type
            	usuariosRegistrados.put(login, nuevoUsuario);
				LPS.addUsuario(nuevoUsuario);
            	return true;
        	} else {
        		Usuario nuevoUsuario = new Profesor(login, password, type);  //Se puede poner sin type
            	usuariosRegistrados.put(login, nuevoUsuario);
				LPS.addUsuario(nuevoUsuario);
            	return true;
        	}	
        }
	}
	
	public boolean autentificarUsuario(String login, String password) {
		Usuario usuario = usuariosRegistrados.get(login);
		if (usuario == null) {
            System.out.println("El login no está registrado.");
            return false;
		}
		
		if (usuario.getPassword().equals(password)) {
			System.out.println("Autentificación exitosa.");
            return true;
		} else {
			System.out.println("Contraseña invalida.");
            return false;	
		}

	}
	
	public static Usuario getUsuario(String login) {
        return usuariosRegistrados.get(login);
    }
}
