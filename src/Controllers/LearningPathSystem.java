package Controllers;

import caminosActividades.CaminoAprendizaje;
import java.util.HashMap;
import usuarios.Usuario;

public class LearningPathSystem {
	
	private HashMap<String, Usuario> Usuarios;
	private HashMap<String, CaminoAprendizaje> Caminos;
	
	public LearningPathSystem()
	{
		this.Usuarios=new HashMap<String, Usuario>();
		this.Caminos=new HashMap<String, CaminoAprendizaje>();
	}
	
	public LearningPathSystem(HashMap<String, Usuario> usuarios, HashMap<String, CaminoAprendizaje> caminos) {
		super();
		Usuarios = usuarios;
		Caminos = caminos;
	}
	
	public HashMap<String, Usuario> getUsuarios() {
		return Usuarios;
	}
	
	public void setUsuarios(HashMap<String, Usuario> usuarios) {
		Usuarios = usuarios;
	}
	
	public HashMap<String, CaminoAprendizaje> getCaminos() {
		return Caminos;
	}
	
	public void setCaminos(HashMap<String, CaminoAprendizaje> caminos) {
		Caminos = caminos;
	}
	
	public Usuario getUsuarioIndividal(String login)
	{
		return this.Usuarios.get(login);
	}
	

	public void addCamino(CaminoAprendizaje camino)
	{
		this.Caminos.put(camino.getTitulo(), camino);
	}
	
	public void addUsuario(Usuario usuario)
	{
		this.Usuarios.put(usuario.getLogin(), usuario);
	}
	
	public CaminoAprendizaje getCaminoIndividual(String titulo)
	{
		return this.Caminos.get(titulo);
	}
	
	
	public void delCamino(CaminoAprendizaje camino)
	{
		this.Caminos.remove(camino.getTitulo());
	}
	

}
