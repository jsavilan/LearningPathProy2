package Envios;

import java.util.HashMap;
import java.util.List; 
import caminosActividades.PreguntaQuiz;
import caminosActividades.Quiz;

public class EnvioQuiz extends Envio<PreguntaQuiz> {
	private double calificacion;
	
	public EnvioQuiz() {
		super();
	}
	
	public EnvioQuiz(HashMap<PreguntaQuiz, String> respuestas, double calificacion) {
		super(respuestas);
		this.calificacion = calificacion;
	}

	public void agregarRespuesta(PreguntaQuiz pregunta, String respuesta) {
          respuestas.put(pregunta, respuesta);
    }
	
	public double getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}
	public HashMap<PreguntaQuiz, String> getRespuestas() {
		return respuestas;
	}
	
	public double calcularCalificacionQuiz(Quiz quiz) {
        List<PreguntaQuiz> preguntasQuiz = quiz.getPreguntas();
        int totalPreguntas = preguntasQuiz.size();
        int respuestasCorrectas = 0;

        for (PreguntaQuiz pregunta : preguntasQuiz) {
            String respuestaCorrecta = pregunta.getRespuesta();
            String respuestaUsuario = respuestas.get(pregunta);

            if (respuestaUsuario != null && respuestaUsuario.equals(respuestaCorrecta)) {
                respuestasCorrectas++;
            }
        }
        
        return (respuestasCorrectas / (double) totalPreguntas) * 10; //Calificacion del 1 al 10
    }

}
