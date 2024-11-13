package Envios;

import java.util.HashMap;

public abstract class Envio<K> {

	protected HashMap<K, String> respuestas;
	
	 public Envio() {
		 this.respuestas = new HashMap<>(); 
	 }
	 
	 
	 public Envio(HashMap<K, String> respuestas) {
		super();
		this.respuestas = respuestas;
	}


	public abstract HashMap<K, String> getRespuestas();
	 
	 public abstract void agregarRespuesta(K pregunta, String respuesta);
	   
}
