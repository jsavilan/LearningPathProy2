package Interface;

import Controllers.ControladorEstudiante;
import Controllers.ControladorFuncionesGenerales;
import Controllers.ControladorProfesor;
import Controllers.LearningPathSystem;
import caminosActividades.CaminoAprendizaje;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import serviceProviders.VisualizadorCaminosActividades;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;
import persistencia.CentralPersistencia;

public class MainProy1 {

	public static void main(String[] args) 
	{
		ControladorFuncionesGenerales ControllerGeneral = new ControladorFuncionesGenerales();
		ControladorProfesor ControllerProf = new ControladorProfesor();
		ControladorEstudiante ControllerEstu= new ControladorEstudiante();
		CentralPersistencia persistencia = new CentralPersistencia();

		LearningPathSystem LPS = ControllerGeneral.getLPS();
		ControllerGeneral.crearUsuario("Alonso Botero", "Alonso123", Usuario.PROFESOR, LPS);
		ControllerGeneral.crearUsuario("Alonso Botero", "Alonso1234", Usuario.PROFESOR, LPS); //Usuario Duplicado
		ControllerGeneral.autentificarUsuario("Alonso Botero", "Alonso123");
		ControllerGeneral.autentificarUsuario("Alonso Botero", "ContraseñaIncorrecta"); //Contraseña Incorrecta
		Profesor AlonsoProf = (Profesor) LPS.getUsuarioIndividal("Alonso Botero");

		List<String> objetivosExample = new ArrayList<String>();
		objetivosExample.add("1. Entender que es una variable.");
		objetivosExample.add("2. Saber como hacer loops.");

		ControllerProf.crearCamino("Introducción a Python", "Este curso está dirigido a estudiantes que quieren empezar a programar en Python. Abarca desde variables hasta bucles.", objetivosExample, 1.5, AlonsoProf, LPS);

		CaminoAprendizaje caminoPython = LPS.getCaminoIndividual("Introducción a Python");

		List<String> objetivosTarea = new ArrayList<String>();
		objetivosTarea.add("1. Entender que es un String.");
		objetivosTarea.add("2. Entender que es un int.");
		ControllerProf.crearTareaCero(caminoPython, "Tarea 1", "Entender los distintos tipos de variables", objetivosTarea, 1.0, 30, new int[] {0, 0, 10}, true, "Declarar 5 variables de diferentes tipos.", AlonsoProf);

		VisualizadorCaminosActividades.verCamino(caminoPython);

		ControllerGeneral.crearUsuario("Mariana Diaz", "Mariana123", Usuario.ESTUDIANTE, ControllerGeneral.getLPS());

		Estudiante MarianaEst= (Estudiante) LPS.getUsuarioIndividal("Mariana Diaz");
		ControllerEstu.inscribirseCamino(MarianaEst, caminoPython);
		
		HashMap<String, Usuario> usuarios = ControllerGeneral.getUsuarios();
		HashMap<String, CaminoAprendizaje> caminos = ControllerGeneral.getCaminos();
		
		try {
			persistencia.salvarDatos(usuarios, caminos);
		} catch (IOException e) {
			e.printStackTrace();
		};


	}

}
