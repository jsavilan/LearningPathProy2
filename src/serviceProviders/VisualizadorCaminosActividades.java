package serviceProviders;

import java.util.HashMap;
import java.util.Iterator;

import Controllers.LearningPathSystem;
import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import usuarios.Estudiante;

public class VisualizadorCaminosActividades {
	
	public static void verCamino(CaminoAprendizaje camino)
	{
		System.out.println("Titulo: "+camino.getTitulo()+"\n");
		System.out.println("Descripcion: "+camino.getDescripcion()+"\n");
		System.out.println("Dificultad: "+String.valueOf(camino.getDificultad())+"\n");
		
		System.out.println("Objetivos: ");	
		Iterator<String> it1 = camino.getObjetivos().iterator();
		
		while (it1.hasNext())
		{
			System.out.println(it1.next());	
		}

		System.out.println("\n");
		
		System.out.println("Rating: "+ String.valueOf(camino.getRating())+"\n");
		
		System.out.println("Actividades: ");	
		Iterator<Actividad> it2 = camino.getActividades().iterator();
		
		while (it2.hasNext())
		{
			System.out.println(it2.next().getNombre());	
		}

		System.out.println("\n");
	}
	
	public static void verActividad(Actividad actividad)
	{
		System.out.println("Nombre: "+ actividad.getNombre() + "\n");
		System.out.println("Tipo: "+actividad.getType());
		System.out.println("Descripcion: "+actividad.getDescripcion()+"\n");
		System.out.println("Dificultad: "+String.valueOf(actividad.getDificultad())+"\n");

		Iterator<String> it1 = actividad.getObjetivos().iterator();
		
		while (it1.hasNext())
		{
			System.out.println(it1.next());	
		}

		System.out.println("\n");
		
		System.out.println("Rating: "+ String.valueOf(actividad.getRating())+"\n");
		
		System.out.println("Obligatoria: "+ String.valueOf(actividad.isObligatoria())+"\n");
				
	}
	
	public static void verAvanceCamino(CaminoAprendizaje camino, String loginEstudiante)
	{
		System.out.println("Avance de actividades: ");
		Iterator<Actividad> it1 = camino.getActividades().iterator();
		int actvCompletadas = 0;
		int numObligatorias = camino.getNumActividadesObligatorias();
		
		while (it1.hasNext())
		{
			Actividad actividad = it1.next();
			DatosEstudianteActividad datoEst = actividad.getDatoEstudianteIndividual(loginEstudiante);
			
			System.out.println(actividad.getNombre() + ": " + datoEst.getEstado());
			
			if (actividad.isObligatoria() && (datoEst.getEstado().equals(DatosEstudianteActividad.EXITOSO)))
			{
				actvCompletadas++;
			}
			
		}
		
		int porcentaje = (numObligatorias > 0) ? (int) (( (double) actvCompletadas/numObligatorias)*100) : (100);
		System.out.println("Porcentaje completado: "+ String.valueOf(porcentaje) + "%");
		
		System.out.println("Actividades completadas: " + actvCompletadas);
	    System.out.println("Total actividades obligatorias: " + numObligatorias);
	}
	
	public static void verResenias(Actividad actividad)
	{
		System.out.println("Reseñas de "+actividad.getNombre()+":");
		
		Iterator<String> it1= actividad.getResenias().iterator();
		
		while (it1.hasNext())
		{
			System.out.println(it1.next()+"\n");
		
		}
		System.out.println("Rating total: "+String.valueOf(actividad.getRating()));
	}
	
	public static void verAvances(HashMap<String, String> avances)
	{
		System.out.println("Tus avances en tus caminos son:");
		
		for (String caminoTitulo: avances.keySet()) {
		    String avance = avances.get(caminoTitulo);
		    System.out.println(caminoTitulo + " " + avance);
		}
	}
	
	public static void recibirRecomendaciones(Estudiante est) {
		
	}
	
}

