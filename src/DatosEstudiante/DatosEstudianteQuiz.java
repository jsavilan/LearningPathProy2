package DatosEstudiante;

import java.util.Date;

import Envios.EnvioQuiz;
import caminosActividades.Quiz;

public class DatosEstudianteQuiz extends DatosEstudianteActividad {
	private double calificacion;
	private EnvioQuiz envioQuiz;
	
	public DatosEstudianteQuiz(String loginEstudiante) {
		super(loginEstudiante);
		this.envioQuiz = new EnvioQuiz();
		this.calificacion = 0.0;

	}
	
	
	
	public DatosEstudianteQuiz(String loginEstudiante, String estado, Date fechaInicio, Date fechaFinal,
			double calificacion, EnvioQuiz envioQuiz) {
		super(loginEstudiante, estado, fechaInicio, fechaFinal);
		this.calificacion = calificacion;
		this.envioQuiz = envioQuiz;
	}



	public double getCalificacion() {
		return calificacion;
	}
	
	public void finalizarQuiz(Quiz quiz) {
		finalizarActividad();
	}
	
	public EnvioQuiz getEnvioQuiz() {
        return envioQuiz;
    }
	
	public void setCalificacion(double calificacion) {
		this.calificacion = calificacion;
	}
	
}
