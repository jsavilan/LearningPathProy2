package DatosEstudiante;

import java.util.Date;

public class DatosEstudianteAR  extends DatosEstudianteActividad {
	
	public DatosEstudianteAR(String loginEstudiante) {
		super(loginEstudiante);
	}
	
	
	public DatosEstudianteAR(String loginEstudiante, String estado, Date fechaInicio, Date fechaFinal) {
		super(loginEstudiante, estado, fechaInicio, fechaFinal);
	}


	public void finalizarAR() throws Exception {
		finalizarActividad();
		setEstado(DatosEstudianteActividad.EXITOSO);
	}

}
