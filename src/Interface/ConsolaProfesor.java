package Interface;

import java.io.IOException;
import caminosActividades.*;
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
import usuarios.Usuario;
import persistencia.CentralPersistencia;

public class ConsolaProfesor {
	private LearningPathSystem LPS;
	private ControladorFuncionesGenerales CFG;
	private ControladorProfesor CPR;
	private CaminoAprendizaje CA;
	private Actividad ACT;
	private PreguntaQuiz PQ;
	private Scanner inp;
	private Profesor logInAct;
	private String rolAct;
	private CentralPersistencia CPER;
	

	public ConsolaProfesor() {
		this.LPS = new LearningPathSystem();
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
				ingresarProf();
				break;
				
			case 2:
				registrarProf();
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
							+ "7)Salir");
			
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
				crearLP();
				break;
			case 6:
				crearAct();
			
			
			}
		} while (resp != 7);
		guardarDatos();
		
	}

	
	private void guardarDatos() throws IOException{

		HashMap<String, Usuario> usuarios = CFG.getUsuarios();
		HashMap<String, CaminoAprendizaje> caminos = CFG.getCaminos();
		CPER.salvarDatos(usuarios, caminos);
	}
	private void cargarDatos() throws IOException{
		CPER.cargarDatos();
	}	
	private void registrarProf() {
		System.out.println("\nIngrese su nombre:");
		String logIn = inp.nextLine();
		System.out.println("\nIngrese su contraseña:");
		String contra = inp.nextLine();
		
		CFG.crearUsuario(logIn, contra, "PROFESOR" );
	}
	private void ingresarProf() {

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
	
	private void crearLP() {
		System.out.println("\nIngrese el titulo para el nuevo learning path:");
		String titulo = inp.nextLine();
		System.out.println("\nIngrese una descripcion del nuevo learning path:");
		String descripcion = inp.nextLine();
		System.out.println("\nIngrese el nivel de dificultad, debe ser un valor de 1 a 5");
		Double dificultad = inp.nextDouble();
		if (dificultad < 1 || dificultad>5) {
			System.out.println("\nEl valor que ingresó es invalido, ingrese un valor que sea mayor que 1 y menos que 5 para asignar a la dificultad:");
			dificultad = inp.nextDouble();
		}
		
		List<String>  objetivos = null;
		
		Boolean bandera = false;
		
		Integer contador = 0;
		
		do {

			if (contador == 0) {
				
				System.out.println("\nIngrese un objetivo:");
				String objetivo = inp.nextLine();
				objetivos.add(objetivo);
			}
			else {
				
				System.out.println("\nSi desea seguir agregando objetivos ingrese la palabra 'si', si desea parar ingrese la palabra 'no':");
				String continuar = inp.nextLine();
				if (continuar == "si") {

					System.out.println("\nIngrese un objetivo:");
					String objetivo = inp.nextLine();
					objetivos.add(objetivo);
					
				}
				else {
					System.out.println("\nObjetivos agregados");
					bandera = true;
				}
			}
			
			
		} while(bandera == false);
		
		CPR.crearCamino(titulo, descripcion, objetivos, dificultad, logInAct, LPS);
	}
	
	private void crearAct() {
		
		System.out.println("\nIngrese el nombre del learning path en el que desea añadir una actividad:");
		String caminoS = inp.nextLine();
		if (!LPS.getCaminos().containsKey(caminoS)) {
			System.out.println("\nNo existe el learning path");
		}
		else {
		CaminoAprendizaje camino = LPS.getCaminoIndividual(caminoS);
		System.out.println("\n1)Tarea"//
						 + "\n2)Quiz"//
						 + "\n3)Examen"
						 + "\n4)Encuesta"
						 + "\n5)Recurso");//
		Integer tipo = inp.nextInt();
		
		System.out.println("\nIngrese el nombre de la nueva actividad:");
		String nombre = inp.nextLine();
		System.out.println("\nIngrese la descripcion de la actividad:");
		String descripcion = inp.nextLine();
		System.out.println("\nIngrese el nivel de dificultad, debe ser un valor de 1 a 5");
		Double dificultad = inp.nextDouble();
		if (dificultad < 1 || dificultad>5) {
			System.out.println("\nEl valor que ingresó es invalido, ingrese un valor que sea mayor que 1 y menos que 5 para asignar a la dificultad:");
			dificultad = inp.nextDouble();
		}
		//////////////////////////////////////////////////////////////////////
		System.out.println("\nIngrese la duracion de la actividad en minutos:(debe ser un numero entero)");
		Integer duracion = inp.nextInt();
		int[] fechaLim = new int[3];
		System.out.println("\nIngrese la fecha limite para la actividad:");
		System.out.println("\nIngrese el año en el siguiente formato(20XX):");
		fechaLim[0] = inp.nextInt();
		System.out.println("\nIngrese el mes en el siguiente formato(XX):");
		fechaLim[1] = inp.nextInt();
		System.out.println("\nIngrese el dia en el siguiente formato(XX):");
		fechaLim[3] = inp.nextInt();
		List<String>  objetivos = null;
		System.out.println("\nSi la actividad es obligatoria ingrese la palabra 'si', si es opcional ingrese la palabra 'no':");
		Boolean obligatoria = false;
		if (inp.nextLine() == "si") {
			obligatoria = true;
		}
		
		Boolean bandera = false;
		Integer contador = 0;
		do {
			if (contador == 0) {
				System.out.println("\nIngrese un objetivo:");
				String objetivo = inp.nextLine();
				objetivos.add(objetivo);
			}
			else {
				System.out.println("\nSi desea seguir agregando objetivos ingrese la palabra 'si', si desea parar ingrese la palabra 'no':");
				String continuar = inp.nextLine();
				if (continuar == "si") {
					System.out.println("\nIngrese un objetivo:");
					String objetivo = inp.nextLine();
					objetivos.add(objetivo);
				}
				else {
					System.out.println("\nObjetivos agregados");
					bandera = true;
				}
			}
		} while(bandera == false);
		System.out.println("\nIngrese el nivel de dificultad, debe ser un numero entre 0 y 5:");
		double dificultadAct = inp.nextDouble();
	//////////////////////////////////////////////////////////////////////
		if (tipo == 1) {
			CPR.crearTareaCero(camino, nombre, descripcion, objetivos, dificultadAct, duracion, fechaLim, obligatoria, rolAct, logInAct);
		}
		else if (tipo == 2) {
			System.out.println("\nIngrese la calificación minima:");
			double calMin = inp.nextDouble();
			List<PreguntaQuiz> listaPreguntas = new ArrayList<>();
			contador = 0;
			int contadorPQ = 0;
			do {
				if (contador == 0) {
					System.out.println("\nIngrese una pregunta para el quiz:");
					String pregunta = inp.nextLine();
					PQ.setTextoPregunta(pregunta);
					do {
						if (contadorPQ < 4 ) {
							if (contadorPQ<1) {
								System.out.println("\nEl quiz solo puede tener 4 opciones, y una de ellas debe ser la correcta");
								System.out.println("\nIngrese la pregunta para el quiz:");
								String preguntaQ = inp.nextLine();
								PQ.setOpcion(contadorPQ, preguntaQ);
								contadorPQ = contadorPQ + 1;
							}
							else {
								System.out.println("\nIngrese la pregunta para el quiz:");
								String preguntaQ = inp.nextLine();
								PQ.setOpcion(contadorPQ, preguntaQ);
								contadorPQ = contadorPQ +1;
							}
						}
						if (contador == 3) {
							System.out.println("\nIngrese la pregunta correcta(Debe estar entre las opciones que ingresó anteriormente):");
							String correcta = inp.nextLine();
							PQ.setRespuesta(correcta);
							bandera = true;
						}
					}while (bandera==false);
					bandera = false;
				}
				else {
					System.out.println("\nSi desea seguir agregando objetivos ingrese la palabra 'si', si desea parar ingrese la palabra 'no':");
					String continuar = inp.nextLine();
					if (continuar == "si") {
						System.out.println("\nIngrese un objetivo:");
						String objetivo = inp.nextLine();
						objetivos.add(objetivo);
					}
					else {
						System.out.println("\nObjetivos agregados");
						bandera = true;
					}
				}
			} while(bandera == false);
			
			//CPR.crearQuizCero(camino, nombre, descripcion, objetivos, dificultad, duracion, fechaLim, obligatoria, calMin, rolAct, logInAct);
		}
		else if (tipo == 3) {
			System.out.println("\nIngrese la calificación minima:");
			double calMin = inp.nextDouble();
			int conteoEx = 0;
			boolean banderaEx = false;
			List<String> listaPreguntas = new ArrayList<>();
			do {
				System.out.println("\nIngrese la cantidad de preguntas:");
				int cantidadEx = inp.nextInt();
				if (conteoEx >= 0 && conteoEx <=cantidadEx-1) {
					
					System.out.println("\nIngrese la descripcion de la pregunta:");
					String descPreg = inp.nextLine();
					listaPreguntas.add(descPreg);
					conteoEx = conteoEx+1;
				}
				else {
					banderaEx = true;
				}
			}while(banderaEx==false);
			
			
			CPR.crearExamenCero(camino, nombre, descripcion, objetivos, dificultadAct, duracion, fechaLim, obligatoria, calMin, listaPreguntas, logInAct);
		}
		else if(tipo == 4) {

			int conteoEx = 0;
			boolean banderaEx = false;
			List<String> listaPreguntas = new ArrayList<>();
			do {
				System.out.println("\nIngrese la cantidad de preguntas:");
				int cantidadEx = inp.nextInt();
				if (conteoEx >= 0 && conteoEx <=cantidadEx-1) {
					
					System.out.println("\nIngrese la descripcion de la pregunta:");
					String descPreg = inp.nextLine();
					listaPreguntas.add(descPreg);
					conteoEx = conteoEx+1;
				}
				else {
					banderaEx = true;
				}
			}while(banderaEx==false);
			
			CPR.crearEncuestaCero(camino, nombre, descripcion, objetivos, dificultadAct, duracion, fechaLim, obligatoria, listaPreguntas, logInAct);
		}
		else if (tipo == 5) {
			
			System.out.println("\nIngrese el nombre del recurso:");
			String recurso = inp.nextLine();
			
			CPR.crearARCero(camino, nombre, descripcion, objetivos, dificultadAct, duracion, fechaLim, obligatoria, recurso, descripcion, logInAct);
		}
		}
	}
	
}
