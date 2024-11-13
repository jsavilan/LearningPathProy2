package caminosActividades;

public class PreguntaQuiz {
	private String[] opciones = new String[4];
	private String textoPregunta;
	private String[] explicaciones  = new String[4];
	private String respuesta;
	
	public PreguntaQuiz(String[] opciones, String textoPregunta, String[] explicaciones, String respuesta) {
		this.opciones = opciones;
		this.textoPregunta = textoPregunta;
		this.explicaciones = explicaciones;
		this.respuesta = respuesta;
	}
	
	public PreguntaQuiz(PreguntaQuiz preguntaOG)
	{
		this.opciones = preguntaOG.getOpciones().clone();
		this.textoPregunta = preguntaOG.getTextoPregunta();
		this.explicaciones = preguntaOG.getExplicaciones().clone();
		this.respuesta = preguntaOG.getRespuesta();
	}

	public String getTextoPregunta() {
		return textoPregunta;
	}

	public void setTextoPregunta(String textoPregunta) {
		this.textoPregunta = textoPregunta;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String[] getOpciones() {
		return opciones;
	}

	public String[] getExplicaciones() {
		return explicaciones;
	}
	
	public void setOpcion(int pos, String opcion)
	{
		this.opciones[pos]=opcion;
	}
	
	public void setExpliacion(int pos, String explicacion)
	{
		this.explicaciones[pos]=explicacion;
	}

}
