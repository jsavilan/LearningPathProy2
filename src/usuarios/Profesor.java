package usuarios;

import java.util.ArrayList;
import java.util.List;

public class Profesor extends Usuario{
	private List<String> caminos;
	
	public Profesor(String login, String password, String type) {
		super(login, password, type);
		this.caminos=new ArrayList<String>();
	}
	
	public Profesor(String login, String password, String type, List<String> caminos) {
		super(login, password, type);
		this.caminos = caminos;
	}

	public List<String> getCaminos() {
		return caminos;
	}
	
	public void addCamino(String camino)
	{
		caminos.add(camino);
	}
	
	
}
