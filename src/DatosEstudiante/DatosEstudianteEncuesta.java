package DatosEstudiante;

import java.util.Date;

import Envios.EnvioEncuesta;

public class DatosEstudianteEncuesta extends DatosEstudianteActividad {
	
	private EnvioEncuesta envio;
	
	public DatosEstudianteEncuesta(String loginEstudiante) {
		super(loginEstudiante);
	}
	
	
	public DatosEstudianteEncuesta(String loginEstudiante, String estado, Date fechaInicio, Date fechaFinal) {
		super(loginEstudiante, estado, fechaInicio, fechaFinal);
	}



	public void finalizarEncuesta() throws Exception {
		finalizarActividad();
		setEstado(DatosEstudianteActividad.EXITOSO);
	}
	

}
