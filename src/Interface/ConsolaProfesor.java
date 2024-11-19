package Interface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Controllers.ControladorProfesor;
import Controllers.ControladorFuncionesGenerales;
import Controllers.LearningPathSystem;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import usuarios.Profesor;

public class ConsolaProfesor {
	private LearningPathSystem LPS;
	private ControladorFuncionesGenerales CFG;
	private ControladorProfesor CPR;
	private CaminoAprendizaje CA;
	private Actividad ACT;
	private Scanner inp;
	private Profesor logInAct;
	private String rolAct;
	

	public ConsolaProfesor() {
		this.LPS = new LearningPathSystem();
		this.CFG= new ControladorFuncionesGenerales();
		this.inp = new Scanner(System.in);
	}
	public static void main(String[] args) throws IOException{
		ConsolaProfesor consola = new ConsolaProfesor();
		consola.consolaProfesor();
		consola.inp.close();
	}

	private void consolaProfesor() throws IOException{
		int resp;
		cargarDatos();
		do {
			System.out.println("\nIngrese el numero de la opcion que desea usar: \n"
					+ "1) Ingresar como profesor\n"
					+ "2) Registrarse como nuevo profesor");
			resp = inp.nextInt();
			inp.nextLine();
			if (resp != 1 || resp != 2) {
				System.out.println("\nEl numero que ingresó no es valido, porfavor vuelva a intentarlo.");
			}
			switch (resp) {
			case 1:
				ingresarEst();
				break;
				
			case 2:
				resgistrarEst();
				break;
				
			}
		} while (rolAct == null);
		do {
			System.out.println("\nIngrese el numero de la opcion que desea usar: \n"
							+ "1)Ver learning paths \n"
							+ "2)Ver actividades \n"
							+ "3)Ver reseñas \n"
							+ "4)Agregar reseña"
							+ "5)Crear learning path \n"
							+ "6)Crear actividad\n"
							+ "7)Editar actividad\n"
							+ "8)Editar learning path\n"
							+ "9)Salir");
			
			resp = inp.nextInt();
			inp.nextLine();
			if (resp < 1 || resp > 9) {
				System.out.println("\nEl numero que ingresó no es valido, porfavor vuelva a intentarlo.");
			}
			switch(resp) {
			case 1:
				verLPs();
				break;
			case 2:
				verAct();
				break;
			case 3:
				verRes();
				break;
			case 4:
				aggRes();
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			
			
			}
		} while (resp != 9);
		guardarDatos();
		
	}

	
	private void guardarDatos() throws IOException{
		
	}
	private void cargarDatos() throws IOException{
		
	}	
	private void resgistrarEst() {
		
	}
	private void ingresarEst() {
		
	}
	

	private void verLPs() {
		HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();
		Set<String> stlps = lps.keySet();
		
		System.out.println("\nLearning paths disponibles:");
		for (String lp : stlps) {
			System.out.println("\n"+lp);
		}
	}

	private void verAct() {
		List<Actividad> actividades = CA.getActividades();
		System.out.println("\nActividades:");
		for (Actividad act : actividades) {
			System.out.println("\n"+act.getNombre());
		}
	}
	
	private void verRes() {
		List<Actividad> actividades = CA.getActividades();
		System.out.println("\nIngrese el nombre de la actividad:");
		String nombre = inp.nextLine();
		List<String> resenias = new ArrayList<>();
		Boolean existe = false;

		for (Actividad act : actividades) {
			if (act.getNombre().equals(nombre)) {
				resenias.addAll(act.getResenias());
				existe = true;
			}
		}
		
		if (resenias.isEmpty()) {
			if (existe = true) {
				System.out.println("\nActividad no tiene reseñas");
			}
			else {
				System.out.println("\nNo existe la actividad");
			}
		}
		else {
			for (String res: resenias) {

				System.out.println("\n " + res);
			}
		}
	}
	
	private void aggRes() {
		System.out.println("\nIngrese el nombre de la actividad:");
		String actividad = inp.nextLine();
		System.out.println("\nIngrese la reseña:");
		String resenia = inp.nextLine();
		List<Actividad> actividades = CA.getActividades();
		
		for(Actividad act: actividades) {
			if(act.getNombre().equals(actividad)) {
				act.addResenia(resenia);
			}
		}
	}
	
	private void crearLP() {
		System.out.println("\nIngrese el titulo para el nuevo learning path:");
		String titulo = inp.nextLine();
		System.out.println("\nIngrese una descripcion del nuevo learning path:");
		String descripcion = inp.nextLine();
		System.out.println("\n");
		String titulo = inp.nextLine();
		

		
	}

}
