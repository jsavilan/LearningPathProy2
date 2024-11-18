package Controllers;

import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import java.util.HashMap;
import serviceProviders.CreadorUsuarios;
import usuarios.Usuario;

public class ControladorFuncionesGenerales {
	
	private LearningPathSystem LPS;
	private CreadorUsuarios controlUsuarios;
	
	
	public ControladorFuncionesGenerales(LearningPathSystem LPS)
	{
		this.LPS = LPS;
		controlUsuarios = new CreadorUsuarios();

	}
	
	public void crearUsuario(String login, String password, String type) {
		controlUsuarios.crearUsuario(login, password, type, this.LPS);
	}
	
	public boolean autentificarUsuario(String login, String password) {
		return controlUsuarios.autentificarUsuario(login, password); 
	}
	
	public LearningPathSystem getLPS()
	{
		return this.LPS;
	}
	
	public HashMap<String, CaminoAprendizaje> getCaminos()
	{
		return this.LPS.getCaminos();
	}
	
	public HashMap<String, Usuario> getUsuarios()
	{
		return LPS.getUsuarios();
	}

	public void dejarResenia(String resenia, Actividad actividad)
	{
		actividad.addResenia(resenia);
	}
	
	public void dejarRating(int rating, Actividad actividad)
	{
		actividad.addRating(rating);
	}
	
	public Usuario getUsuario(String login) {
		return CreadorUsuarios.getUsuario(login);
	}
}

