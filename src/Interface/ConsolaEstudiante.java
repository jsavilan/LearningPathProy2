package Interface;

import Controllers.*;
import caminosActividades.CaminoAprendizaje;
import usuarios.Estudiante;
import usuarios.Usuario;
import caminosActividades.*;
import persistencia.CentralPersistencia;
import serviceProviders.*;

import java.util.*;
import java.io.*;
import java.time.*;

public class ConsolaEstudiante {
	private LearningPathSystem LPS;
	private ControladorFuncionesGenerales CFG;
	private ControladorEstudiante CEs = new ControladorEstudiante();;
	private CaminoAprendizaje CA;
	private Actividad ACT;
	private Scanner inp;
	private Estudiante logInAct;
	private String rolAct;
	private CentralPersistencia CPER = new CentralPersistencia();
	
	public ConsolaEstudiante() {
		this.LPS = new LearningPathSystem();
		this.CFG = new ControladorFuncionesGenerales(LPS);
		this.inp = new Scanner(System.in);
	}
	
	public static void main(String[] args) throws IOException{
		ConsolaEstudiante consola = new ConsolaEstudiante();
		consola.consolaEst();
		consola.inp.close();
	}
	private void consolaEst() throws IOException{
		int resp;
		cargarDatos();
		do {
			System.out.println("\nIngrese el numero de la opcion que desea usar: \n"
					+ "1) Ingresar como estudiante\n"
					+ "2) Registrarse como nuevo estudiante");
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
							+ "4)Agregar reseña \n"
							+ "5)Inscribir learning path \n"
							+ "6)Iniciar/Continuar actividad \n"
							+ "7)Ver progreso learning path \n"
							+ "8)Salir");
			
			resp = inp.nextInt();
			inp.nextLine();
			if (resp < 1 || resp > 8) {
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
				inscLP();
				break;
			case 6:
				iniciarContinuarActividad();
				break;
			case 7:
				verProgresoLP();
				break;
			
			
			}
		} while (resp != 8);
		guardarDatos();
		
	}
	
	private void guardarDatos() throws IOException{

		HashMap<String, Usuario> usuarios = CFG.getUsuarios();
		HashMap<String, CaminoAprendizaje> caminos = CFG.getCaminos();
		CPER.salvarDatos(usuarios, caminos);
	}
	private void cargarDatos() throws IOException{

		this.LPS = CPER.cargarDatos();
	}	
	private void resgistrarEst() {

		System.out.println("\nIngrese su nombre:");
		String logIn = inp.nextLine();
		System.out.println("\nIngrese su contraseña:");
		String contra = inp.nextLine();
		
		CFG.crearUsuario(logIn, contra, "ESTUDIANTE" );
	}
	private void ingresarEst() {

		System.out.println("\nIngrese su nombre:");
		String logIn = inp.nextLine();
		System.out.println("\nIngrese su contraseña:");
		String contra = inp.nextLine();
		CFG.autentificarUsuario(logIn, contra);
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
	private void inscLP() {
		HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();
		System.out.println("\nIngrese el nombre del Learning Path:");
		String Lp = inp.nextLine();
		
		
		
		if (lps.containsKey(Lp)) {

			CaminoAprendizaje camino = lps.get(Lp);
			logInAct.addCamino(Lp);
			Inscriptor.inscribirseCamino(camino, logInAct);
			System.out.println("\n Learning Path inscrito.");
		}
		else {
			System.out.println("\n El learning path no existe.");
		}
	}
	private void iniciarContinuarActividad() {
		
		System.out.println("\nIngrese el nombre de la actividad");
		String actividad = inp.nextLine();
		List<Actividad> actividades = CA.getActividades();
		for (Actividad act : actividades) {
			if (act.getNombre().equals(actividad)) {
				CEs.iniciarActividad(logInAct, act);
			}
		}
	}
	private void verProgresoLP() {
		
		HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();
		System.out.println("\nIngrese el nombre del Learning Path:");
		String Lp = inp.nextLine();
		
		CaminoAprendizaje camino = lps.get(Lp);
		
		VisualizadorCaminosActividades.verAvanceCamino(camino, logInAct.getLogin());
		
	}
}
