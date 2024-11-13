package Envios;

import java.util.HashMap;

public class EnvioExamen extends Envio<String>{

	public EnvioExamen(HashMap<String, String> respuestas) {
		this.respuestas = respuestas;
	}
	
	public HashMap<String, String> getRespuestas() {
		return respuestas;
	}
	
    public void agregarRespuesta(String pregunta, String respuesta) {
        respuestas.put(pregunta, respuesta);
    }

}
