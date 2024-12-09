package Interface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controllers.LearningPathSystem;
import DatosEstudiante.DatosEstudianteActividad;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import caminosActividades.Examen;
import caminosActividades.Tarea;
import persistencia.CentralPersistencia;
import serviceProviders.ActualizadorCalificacionesExitoso;
import usuarios.Profesor;

//Panel principal para profesores
public class TeacherPanel {
	
	private final JFrame frame = new JFrame("Panel de Estudiante");
	private final JPanel mainPanel = new JPanel(new CardLayout());
	private final CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
	
	private Profesor usuario;
	private final LearningPathSystem LPS;
	private CaminoAprendizaje CA;
	
	public TeacherPanel(JFrame parentFrame, Profesor usuario, LearningPathSystem LPS) {
		
		this.usuario = usuario;
		this.LPS = LPS;

		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLayout(new BorderLayout());

		JPanel buttonPanel = createButtonPanel();
		frame.add(buttonPanel, BorderLayout.WEST);
		frame.add(mainPanel, BorderLayout.CENTER);

		// Agregar listener para manejar el evento de cierre de la ventana
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showConfirmDialog(frame, "¿Estás seguro de que deseas salir?",
						"Confirmar cierre", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					CentralPersistencia persistencia = new CentralPersistencia();
					HashMap<String, CaminoAprendizaje> caminos = LPS.getCaminos();
	                try {
						persistencia.salvarDatos(LPS.getUsuarios(),caminos);
					} catch (IOException e1) {
					};
					
					frame.dispose();
					frame.dispose();
				}
			}
		});

		mainPanel.add(new JLabel("Bienvenido al panel de profesor", SwingConstants.CENTER), "inicio");
		mainPanel.add(createLearningPathsPanel(), "verLPs");
		mainPanel.add(createActivitiesPanel(), "verAct");
		mainPanel.add(createReviewsPanel(), "verRes");
		mainPanel.add(createAddReviewPanel(), "aggRes");
		mainPanel.add(createEnrollLPPanel(), "inscLP");
		mainPanel.add(createStartActivityPanel(), "iniciarActividad");
		mainPanel.add(createProgressPanel(), "verProgresoLP");
		mainPanel.add(seleccionarLP(), "seleccionarLP");
		
		mainPanel.add(showCloneTaskPanel(), "clonarTarea");
		mainPanel.add(showCloneExamPanel(), "clonarExamen");

		mainPanel.add(showGradeTaskPanel(), "calificarTarea");
		mainPanel.add(showGradeExamPanel(), "calificarExamen");

		mainPanel.add(createViewActivitySubmissionsPanel(), "enviosActividad");
		mainPanel.add(createViewIndividualSubmissionPanel(), "enviosInvididuales");
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 1));

		addButton(panel, "Inicio", "inicio");
		addButton(panel, "Ver Learning Paths", "verLPs");
		addButton(panel, "Ver Actividades", "verAct");
		addButton(panel, "Ver Reseñas", "verRes");
		addButton(panel, "Agregar Reseña", "aggRes");
		addButton(panel, "Inscribirse a LP", "inscLP");
		addButton(panel, "Iniciar Actividad", "iniciarActividad");
		addButton(panel, "Ver Progreso LP", "verProgresoLP");
		addButton(panel, "Seleccionar LP", "seleccionarLP");
		
		addButton(panel, "Clonar Tarea", "clonarTarea");
	    addButton(panel, "Clonar Examen", "clonarExamen");
	    addButton(panel, "Calificar Tarea", "calificarTarea");
	    addButton(panel, "Calificar Examen", "calificarExamen");
	    addButton(panel, "Ver Envíos de Actividad", "enviosActividad");
	    addButton(panel, "Ver Envíos Invididuales", "enviosIndividuales");


		return panel;
	}

	private void addButton(JPanel panel, String text, String actionCommand) {
		JButton button = new JButton(text);
		button.addActionListener(e -> cardLayout.show(mainPanel, actionCommand));
		panel.add(button);
	}

	private JPanel createLearningPathsPanel() {
		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea(15, 40);
		textArea.setEditable(false);

		JButton showButton = new JButton("Mostrar Learning Paths");
		showButton.addActionListener(e -> {
			HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();
			textArea.setText("Learning Paths disponibles:\n");
			for (String lp : lps.keySet()) {
				textArea.append(lp + "\n");
			}
		});

		panel.add(showButton);
		panel.add(new JScrollPane(textArea));
		return panel;
	}
	
	private JPanel seleccionarLP() {
	    JPanel panel = new JPanel(new BorderLayout());
	    JButton seleccionarButton = new JButton("Seleccionar Learning Path");
	    JTextArea infoArea = new JTextArea("Seleccione un Learning Path para continuar.", 5, 30);
	    infoArea.setFont(new Font("SansSerif", Font.BOLD, 12));
	    infoArea.setEditable(false);

	    seleccionarButton.addActionListener(e -> {
	        String[] opciones = LPS.getCaminos().keySet().toArray(new String[0]);
	        String seleccion = (String) JOptionPane.showInputDialog(
	                frame,
	                "Seleccione un Learning Path:",
	                "Learning Paths",
	                JOptionPane.QUESTION_MESSAGE,
	                null,
	                opciones,
	                opciones.length > 0 ? opciones[0] : null
	        );

	        if (seleccion != null) {
	            this.CA = LPS.getCaminos().get(seleccion);
	            JOptionPane.showMessageDialog(frame, "Learning Path seleccionado: " + seleccion);
	            infoArea.setText("Learning Path actual: " + seleccion);
	        } else {
	            JOptionPane.showMessageDialog(frame, "No se seleccionó ningún Learning Path.");
	        }
	    });

	    panel.add(infoArea, BorderLayout.CENTER);
	    panel.add(seleccionarButton, BorderLayout.SOUTH);
	    return panel;
	}

	private JPanel createActivitiesPanel() {
		JPanel panel = new JPanel();
		JTextArea textArea = new JTextArea(15, 40);
		textArea.setEditable(false);

		JButton showButton = new JButton("Mostrar Actividades");
		showButton.addActionListener(e -> {
			if (CA == null) {
	            JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.", 
	                                          "Error", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
			List<Actividad> actividades = CA.getActividades();
			textArea.setText("Actividades disponibles:\n");
			for (Actividad act : actividades) {
				textArea.append(act.getNombre() + "\n");
			}
		});

		panel.add(showButton);
		panel.add(new JScrollPane(textArea));
		return panel;
	}

	private JPanel createReviewsPanel() {
		JPanel panel = new JPanel();
		JTextField activityField = new JTextField(20);
		JTextArea textArea = new JTextArea(10, 40);
		textArea.setEditable(false);

		JButton searchButton = new JButton("Buscar Reseñas");
		searchButton.addActionListener(e -> {
			
			String nombre = activityField.getText();
			List<Actividad> actividades = CA.getActividades();
			List<String> resenias = new ArrayList<>();
			boolean existe = false;

			for (Actividad act : actividades) {
				if (act.getNombre().equals(nombre)) {
					resenias.addAll(act.getResenias());
					existe = true;
				}
			}

			textArea.setText("");
			if (!existe) {
				textArea.setText("No existe la actividad");
			} else if (resenias.isEmpty()) {
				textArea.setText("Actividad no tiene reseñas");
			} else {
				for (String res : resenias) {
					textArea.append(res + "\n");
				}
			}
		});

		panel.add(new JLabel("Nombre de la actividad:"));
		panel.add(activityField);
		panel.add(searchButton);
		panel.add(new JScrollPane(textArea));
		return panel;
	}

	private JPanel createAddReviewPanel() {
		JPanel panel = new JPanel();
		JTextField activityField = new JTextField(20);
		JTextField reviewField = new JTextField(20);

		JButton addButton = new JButton("Agregar Reseña");
		addButton.addActionListener(e -> {
			String actividad = activityField.getText();
			String resenia = reviewField.getText();
			List<Actividad> actividades = CA.getActividades();

			for (Actividad act : actividades) {
				if (act.getNombre().equals(actividad)) {
					act.addResenia(resenia);
					JOptionPane.showMessageDialog(frame, "Reseña agregada con éxito");
					return;
				}
			}
			JOptionPane.showMessageDialog(frame, "Actividad no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
		});

		panel.add(new JLabel("Nombre de la actividad:"));
		panel.add(activityField);
		panel.add(new JLabel("Reseña:"));
		panel.add(reviewField);
		panel.add(addButton);
		return panel;
	}

	private JPanel createEnrollLPPanel() {
		JPanel panel = new JPanel();
		JTextField lpField = new JTextField(20);

		JButton enrollButton = new JButton("Inscribirse");
		enrollButton.addActionListener(e -> {
			String Lp = lpField.getText();
			HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();

			if (lps.containsKey(Lp)) {
				CaminoAprendizaje camino = lps.get(Lp);
				usuario.addCamino(Lp);
				JOptionPane.showMessageDialog(frame, "Learning Path inscrito");
			} else {
				JOptionPane.showMessageDialog(frame, "Learning Path no existe", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		panel.add(new JLabel("Nombre del Learning Path:"));
		panel.add(lpField);
		panel.add(enrollButton);
		return panel;
	}

	private JPanel createStartActivityPanel() {
		JPanel panel = new JPanel();
		JTextField activityField = new JTextField(20);

		JButton startButton = new JButton("Iniciar/Continuar");
		startButton.addActionListener(e -> {
			if (CA == null) {
	            JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.", 
	                                          "Error", JOptionPane.WARNING_MESSAGE);
	            return;
	        }
			String actividad = activityField.getText();
			List<Actividad> actividades = CA.getActividades();

			for (Actividad act : actividades) {
				if (act.getNombre().equals(actividad)) {
					JOptionPane.showMessageDialog(frame, "Actividad iniciada: " + actividad);
					return;
				}
			}
			JOptionPane.showMessageDialog(frame, "Actividad no encontrada", "Error", JOptionPane.ERROR_MESSAGE);
		});

		panel.add(new JLabel("Nombre de la actividad:"));
		panel.add(activityField);
		panel.add(startButton);
		return panel;
	}

	private JPanel createProgressPanel() {
		JPanel panel = new JPanel();
		JTextField lpField = new JTextField(20);

		JButton progressButton = new JButton("Ver Progreso");
		progressButton.addActionListener(e -> {
			
			String Lp = lpField.getText();
			HashMap<String, CaminoAprendizaje> lps = LPS.getCaminos();

			if (lps.containsKey(Lp)) {
				JOptionPane.showMessageDialog(frame, "Progreso mostrado para " + Lp);
			} else {
				JOptionPane.showMessageDialog(frame, "Learning Path no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		panel.add(new JLabel("Nombre del Learning Path:"));
		panel.add(lpField);
		panel.add(progressButton);
		return panel;
	}
	
	private JPanel createViewActivitySubmissionsPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    JTextArea submissionsArea = new JTextArea(15, 40);
	    submissionsArea.setEditable(false);

	    JPanel inputPanel = new JPanel(new GridLayout(2, 2));
	    JLabel caminoLabel = new JLabel("Título del Camino:");
	    JTextField caminoField = new JTextField();
	    JLabel actividadLabel = new JLabel("Nombre de la Actividad:");
	    JTextField actividadField = new JTextField();
	    inputPanel.add(caminoLabel);
	    inputPanel.add(caminoField);
	    inputPanel.add(actividadLabel);
	    inputPanel.add(actividadField);

	    JButton viewButton = new JButton("Ver Envíos");
	    viewButton.addActionListener(e -> {
	        String caminoTitulo = caminoField.getText();
	        String actividadNombre = actividadField.getText();

	        CaminoAprendizaje camino = LPS.getCaminos().get(caminoTitulo);
	        if (camino == null) {
	            JOptionPane.showMessageDialog(panel, "Camino no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        Actividad actividad = null;//camino.getActividadPorNombre(actividadNombre);
	        if (actividad == null) {
	            JOptionPane.showMessageDialog(panel, "Actividad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        submissionsArea.setText("Envíos para la actividad " + actividadNombre + ":\n");
	        HashMap<String, DatosEstudianteActividad> envios = actividad.getDatosEstudiantes();
	        for (String login : envios.keySet()) {
	            DatosEstudianteActividad envio = envios.get(login);
	            submissionsArea.append("Login: " + login + ", Estado: " + envio.getEstado() + "\n");
	        }
	    });

	    panel.add(inputPanel, BorderLayout.NORTH);
	    panel.add(new JScrollPane(submissionsArea), BorderLayout.CENTER);
	    panel.add(viewButton, BorderLayout.SOUTH);
	    return panel;
	}

	private JPanel createViewIndividualSubmissionPanel() {
	    JPanel panel = new JPanel(new BorderLayout());
	    JTextArea detailsArea = new JTextArea(15, 40);
	    detailsArea.setEditable(false);

	    JPanel inputPanel = new JPanel(new GridLayout(3, 2));
	    JLabel caminoLabel = new JLabel("Título del Camino:");
	    JTextField caminoField = new JTextField();
	    JLabel actividadLabel = new JLabel("Nombre de la Actividad:");
	    JTextField actividadField = new JTextField();
	    JLabel estudianteLabel = new JLabel("Login del Estudiante:");
	    JTextField estudianteField = new JTextField();
	    inputPanel.add(caminoLabel);
	    inputPanel.add(caminoField);
	    inputPanel.add(actividadLabel);
	    inputPanel.add(actividadField);
	    inputPanel.add(estudianteLabel);
	    inputPanel.add(estudianteField);

	    JButton viewButton = new JButton("Ver Envío Individual");
	    viewButton.addActionListener(e -> {
	        String caminoTitulo = caminoField.getText();
	        String actividadNombre = actividadField.getText();
	        String loginEst = estudianteField.getText();

	        CaminoAprendizaje camino = LPS.getCaminos().get(caminoTitulo);
	        if (camino == null) {
	            JOptionPane.showMessageDialog(panel, "Camino no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        List<Actividad> actividades = camino.getActividades();
	        Actividad actividad_encontrada = null;
	        
	        for (Actividad actividad: actividades) {
	        	if (actividad.getNombre()==actividadNombre) {
	        		actividad_encontrada = actividad;
	        	}
	        }
	        
	        if (actividad_encontrada == null) {
	            JOptionPane.showMessageDialog(panel, "Actividad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        DatosEstudianteActividad datosEst = actividad_encontrada.getDatoEstudianteIndividual(loginEst);
	        if (datosEst == null) {
	            JOptionPane.showMessageDialog(panel, "Envío no encontrado para el estudiante.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        detailsArea.setText("Detalles del envío:\n");
	        detailsArea.append("Fecha de inicio: " + datosEst.getFechaInicio() + "\n");
	        try {
	            detailsArea.append("Fecha final: " + datosEst.getFechaFinal() + "\n");
	        } catch (Exception ex) {
	            detailsArea.append("La actividad aún no ha sido finalizada.\n");
	        }
	        detailsArea.append("Estado: " + datosEst.getEstado() + "\n");

	        if (datosEst instanceof DatosEstudianteQuiz) {
	            DatosEstudianteQuiz datosQuiz = (DatosEstudianteQuiz) datosEst;
	            detailsArea.append("Calificación: " + datosQuiz.getCalificacion() + "\n");
	        } else if (datosEst instanceof DatosEstudianteExamen) {
	            DatosEstudianteExamen datosExamen = (DatosEstudianteExamen) datosEst;
	            detailsArea.append("Calificación: " + datosExamen.getCalificacion() + "\n");
	        }
	    });

	    panel.add(inputPanel, BorderLayout.NORTH);
	    panel.add(new JScrollPane(detailsArea), BorderLayout.CENTER);
	    panel.add(viewButton, BorderLayout.SOUTH);
	    return panel;
	}
	
	private JPanel showGradeTaskPanel() {
	    JPanel panel = new JPanel(new GridLayout(0, 1));
	    JLabel label = new JLabel("Calificar Tarea:");
	    panel.add(label);
	    
	    if (CA == null) { // Verifica si se ha seleccionado un Learning Path
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.",
	                                      "Error", JOptionPane.WARNING_MESSAGE);
	        return panel;
	    }
	    
	    List<Tarea> tareas = new ArrayList<>();
	    List<String> estudiantes = new ArrayList<>();
	    
	    for (Actividad actividad : CA.getActividades()) {
	    	String type = actividad.getType();
	    	if (type == Actividad.TAREA) {
	    		tareas.add((Tarea) actividad);
	    		Iterator<String> iterador = actividad.getDatosEstudiantes().keySet().iterator();
	            while (iterador.hasNext()) {
	                String login = iterador.next();
	                estudiantes.add(login);  // Añade cada login de estudiante
	            }
	    	}
	    }

	    JComboBox<Tarea> tareaComboBox = new JComboBox<>(tareas.toArray(new Tarea[0]));
	    JComboBox<String> estudianteComboBox = new JComboBox<>(estudiantes.toArray(new String[0]));
	    JTextField stateField = new JTextField("Estado (Ejemplo: 'Completado')");
	    panel.add(new JLabel("Selecciona la Tarea:"));
	    panel.add(tareaComboBox);
	    panel.add(new JLabel("Selecciona el Estudiante:"));
	    panel.add(estudianteComboBox);
	    panel.add(stateField);

	    JButton gradeButton = new JButton("Calificar");
	    gradeButton.addActionListener(e -> {
	        Tarea tareaSeleccionada = (Tarea) tareaComboBox.getSelectedItem();
	        String estudianteSeleccionado = (String) estudianteComboBox.getSelectedItem();
	        String estado = stateField.getText();

	        try {
	            boolean success = ActualizadorCalificacionesExitoso.calificarTarea(tareaSeleccionada, estudianteSeleccionado, usuario, estado);

	            if (success) {
	                JOptionPane.showMessageDialog(frame, "Tarea calificada correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(frame, "No se pudo calificar la tarea.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
	        }
	    });

	    panel.add(gradeButton);

	    JFrame gradeFrame = new JFrame("Calificar Tarea");
	    gradeFrame.add(panel);
	    gradeFrame.setSize(400, 300);
	    gradeFrame.setVisible(true);
	    
	    return panel;
	}

	private JPanel showGradeExamPanel() {
		
	    JPanel panel = new JPanel(new GridLayout(0, 1));
	    JLabel label = new JLabel("Calificar Examen:");
	    panel.add(label);
	    
	    if (CA == null) { // Verifica si se ha seleccionado un Learning Path
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.",
	                                      "Error", JOptionPane.WARNING_MESSAGE);
	        return panel;
	    }
	    
	    List<Examen> examenes = new ArrayList<>();
	    List<String> estudiantes = new ArrayList<>();
	    
	    for (Actividad actividad : CA.getActividades()) {
	    	String type = actividad.getType();
	    	if (type == Actividad.EXAMEN) {
	    		examenes.add((Examen) actividad);
	    		Iterator<String> iterador = actividad.getDatosEstudiantes().keySet().iterator();
	            while (iterador.hasNext()) {
	                String login = iterador.next();
	                estudiantes.add(login);  // Añade cada login de estudiante
	            }
	    	}
	    }
	    

	    JComboBox<Examen> examenComboBox = new JComboBox<>(examenes.toArray(new Examen[0]));
	    JComboBox<String> estudianteComboBox = new JComboBox<>(estudiantes.toArray(new String[0]));
	    JTextField gradeField = new JTextField("Calificación");
	    JTextField stateField = new JTextField("Estado (Ejemplo: 'Aprobado')");
	    panel.add(new JLabel("Selecciona el Examen:"));
	    panel.add(examenComboBox);
	    panel.add(new JLabel("Selecciona el Estudiante:"));
	    panel.add(estudianteComboBox);
	    panel.add(gradeField);
	    panel.add(stateField);

	    JButton gradeButton = new JButton("Calificar");
	    gradeButton.addActionListener(e -> {
	        Examen examenSeleccionado = (Examen) examenComboBox.getSelectedItem();
	        String estudianteSeleccionado = (String) estudianteComboBox.getSelectedItem();
	        double grade = Double.parseDouble(gradeField.getText());
	        String estado = stateField.getText();

	        try {
	            boolean success = ActualizadorCalificacionesExitoso.addCalificacionExamen(examenSeleccionado, estudianteSeleccionado, grade, usuario, estado);

	            if (success) {
	                JOptionPane.showMessageDialog(frame, "Examen calificado correctamente.");
	            } else {
	                JOptionPane.showMessageDialog(frame, "No se pudo calificar el examen.");
	            }
	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
	        }
	    });

	    panel.add(gradeButton);

	    JFrame gradeFrame = new JFrame("Calificar Examen");
	    gradeFrame.add(panel);
	    gradeFrame.setSize(400, 300);
	    gradeFrame.setVisible(true);
	    
	    return panel;
	}

	private JPanel showCloneTaskPanel() {
	    JPanel panel = new JPanel(new GridLayout(0, 1));
	    JLabel label = new JLabel("Clonar Tarea:");
	    panel.add(label);
	    
	    if (CA == null) { // Verifica si se ha seleccionado un Learning Path
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.",
	                                      "Error", JOptionPane.WARNING_MESSAGE);
	        return panel;
	    }
	    
	    List<Tarea> tareas = new ArrayList<>();
	    
	    for (Actividad actividad : CA.getActividades()) {
	    	String type = actividad.getType();
	    	if (type == Actividad.TAREA) {
	    		tareas.add((Tarea) actividad);
	    	}
	    }

	    JComboBox<Tarea> tareaComboBox = new JComboBox<>(tareas.toArray(new Tarea[0]));
	    JTextField nombreClonField = new JTextField("Nombre para la tarea clonada");
	    JTextField descripcionClonField = new JTextField("Descripción para la tarea clonada");

	    panel.add(new JLabel("Selecciona la Tarea a Clonar:"));
	    panel.add(tareaComboBox);
	    panel.add(new JLabel("Nombre del clon:"));
	    panel.add(nombreClonField);
	    panel.add(new JLabel("Descripción del clon:"));
	    panel.add(descripcionClonField);

	    JButton cloneButton = new JButton("Clonar Tarea");
	    cloneButton.addActionListener(e -> {
	        Tarea tareaSeleccionada = (Tarea) tareaComboBox.getSelectedItem();
	        String nombreClon = nombreClonField.getText();
	        String descripcionClon = descripcionClonField.getText();

	        if (tareaSeleccionada != null && !nombreClon.isEmpty() && !descripcionClon.isEmpty()) {
	            Tarea tareaClonada = new Tarea(nombreClon, tareaSeleccionada);

	            // Agregar la nueva tarea al sistema
	            tareas.add(tareaClonada);

	            JOptionPane.showMessageDialog(frame, "Tarea clonada correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(frame, "Por favor, completa todos los campos.");
	        }
	    });

	    panel.add(cloneButton);

	    JFrame cloneFrame = new JFrame("Clonar Tarea");
	    cloneFrame.add(panel);
	    cloneFrame.setSize(400, 300);
	    cloneFrame.setVisible(true);
	    
	    return panel;
	}

	private JPanel showCloneExamPanel() {
	    JPanel panel = new JPanel(new GridLayout(0, 1));
	    JLabel label = new JLabel("Clonar Examen:");
	    panel.add(label);
	    
	    if (CA == null) { // Verifica si se ha seleccionado un Learning Path
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.",
	                                      "Error", JOptionPane.WARNING_MESSAGE);
	        return panel;
	    }
	    
	    List<Examen> examenes = new ArrayList<>();
	    
	    for (Actividad actividad : CA.getActividades()) {
	    	String type = actividad.getType();
	    	if (type == Actividad.EXAMEN) {
	    		examenes.add((Examen) actividad);
	    	}
	    }

	    JComboBox<Examen> examenComboBox = new JComboBox<>(examenes.toArray(new Examen[0]));
	    JTextField nombreClonField = new JTextField("Nombre para el examen clonado");
	    JTextField descripcionClonField = new JTextField("Descripción para el examen clonado");

	    panel.add(new JLabel("Selecciona el Examen a Clonar:"));
	    panel.add(examenComboBox);
	    panel.add(new JLabel("Nombre del clon:"));
	    panel.add(nombreClonField);
	    panel.add(new JLabel("Descripción del clon:"));
	    panel.add(descripcionClonField);

	    JButton cloneButton = new JButton("Clonar Examen");
	    cloneButton.addActionListener(e -> {
	        Examen examenSeleccionado = (Examen) examenComboBox.getSelectedItem();
	        String nombreClon = nombreClonField.getText();
	        String descripcionClon = descripcionClonField.getText();

	        if (examenSeleccionado != null && !nombreClon.isEmpty() && !descripcionClon.isEmpty()) {
	            Examen examenClonado = new Examen(nombreClon, examenSeleccionado);

	            // Agregar el nuevo examen al sistema
	            examenes.add(examenClonado);

	            JOptionPane.showMessageDialog(frame, "Examen clonado correctamente.");
	        } else {
	            JOptionPane.showMessageDialog(frame, "Por favor, completa todos los campos.");
	        }
	    });

	    panel.add(cloneButton);

	    JFrame cloneFrame = new JFrame("Clonar Examen");
	    cloneFrame.add(panel);
	    cloneFrame.setSize(400, 300);
	    cloneFrame.setVisible(true);
	    return panel;
	}

}
