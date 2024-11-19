package serviceProviders;

import java.util.HashMap;
import java.util.Iterator;

import Controllers.LearningPathSystem;
import DatosEstudiante.DatosEstudianteAR;
import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteEncuesta;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import DatosEstudiante.DatosEstudianteTarea;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import usuarios.Estudiante;

public class Inscriptor {

	public static void inscribirseCamino(CaminoAprendizaje camino, Estudiante estudiante)
	{
		Iterator<Actividad> it1= camino.getActividades().iterator();
		DatosEstudianteActividad datoEst;
		
		while (it1.hasNext())
		{
			Actividad act= it1.next();
			if (act.getType().equals(Actividad.ENCUESTA))
			{
				datoEst= new DatosEstudianteEncuesta(estudiante.getLogin());
			}
			else if (act.getType().equals(Actividad.QUIZ))
			{
				datoEst= new DatosEstudianteQuiz(estudiante.getLogin());
			}
			else if (act.getType().equals(Actividad.EXAMEN))
			{
				datoEst= new DatosEstudianteExamen(estudiante.getLogin());
			}
			else if (act.getType().equals(Actividad.TAREA))
			{
				datoEst= new DatosEstudianteTarea(estudiante.getLogin(), "Sin enviar");
			}
			else 
			{
				datoEst= new DatosEstudianteAR(estudiante.getLogin());
			}
			
			act.putDatoEstudiante(datoEst);
			
		}
		
		estudiante.addCamino(camino.getTitulo());
	}
	
	public static void iniciarActivad(Actividad actividad, Estudiante estudiante) throws
	Exception
	{
		if (!estudiante.isActividadActiva())
		{
			estudiante.setActividadActiva(true);
			DatosEstudianteActividad datoEst = actividad.getDatoEstudianteIndividual(estudiante.getLogin());
			datoEst.setFechaInicio();
		}
		else
		{
			throw new Exception ("No se puede iniciar una nueva actividad porque ya hay una iniciada");
		}
	
	}
	
	/**
	 * retorna un HashMap<String, String> que contiene como llave el nombre del camino y como valor el porcentaje
	 * logrado en ese camino
	 */
	public static HashMap<String, String> getAvancesCaminos(LearningPathSystem LPS, Estudiante estudiante) 
	{
		HashMap<String, String> avances = new HashMap<String, String>();
		Iterator<String> it1= estudiante.getHistorialCaminos().iterator();
		CaminoAprendizaje camino;
		
		while (it1.hasNext())
		{
			camino=LPS.getCaminoIndividual(it1.next());
			
			Iterator<Actividad> it2= camino.getActividades().iterator();
			int actvCompletadas =0;
			
			while (it2.hasNext())
			{
				DatosEstudianteActividad datoEst = it2.next().getDatoEstudianteIndividual(estudiante.getLogin());
				
				if (it2.next().isObligatoria()  && (datoEst.getEstado().equals(DatosEstudianteActividad.EXITOSO)))
				{
					actvCompletadas+=1;
				}
				
			}
			
			int porcentaje = (actvCompletadas/camino.getNumActividadesObligatorias())*100;
			
			avances.put(it1.next(), String.valueOf(porcentaje)+"%");

		}
		
		return avances;
	}
}
