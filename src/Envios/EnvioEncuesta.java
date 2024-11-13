package Envios;

import java.util.HashMap;

public class EnvioEncuesta extends Envio<String>{

	public EnvioEncuesta(HashMap<String, String> respuestas) {
		this.respuestas = respuestas;
	}


	public HashMap<String, String> getRespuestas() {
		return respuestas;
	}

    public void agregarRespuesta(String pregunta, String respuesta) {
        respuestas.put(pregunta, respuesta);
    }
	
}
