package Interface;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controllers.LearningPathSystem;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import persistencia.CentralPersistencia;
import usuarios.Estudiante;

//Panel principal para estudiantes
public class StudentPanel {
	private final JFrame frame = new JFrame("Panel de Estudiante");
	private final JPanel mainPanel = new JPanel(new CardLayout());
	private final CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

	private final LearningPathSystem LPS;
	private CaminoAprendizaje CA;

	private Estudiante usuario;

	public StudentPanel(JFrame parentFrame, Estudiante usuario, LearningPathSystem LPS) {

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
						persistencia.salvarDatos(LPS.getUsuarios(), caminos);
					} catch (IOException e1) {
					}
					;

					frame.dispose();
					frame.dispose();
				}
			}
		});

		mainPanel.add(new JLabel("Bienvenido al panel de estudiante", SwingConstants.CENTER), "inicio");
		mainPanel.add(createLearningPathsPanel(), "verLPs");
		mainPanel.add(createActivitiesPanel(), "verAct");
		mainPanel.add(createReviewsPanel(), "verRes");
		mainPanel.add(createAddReviewPanel(), "aggRes");
		mainPanel.add(createEnrollLPPanel(), "inscLP");
		mainPanel.add(createStartActivityPanel(), "iniciarActividad");
		mainPanel.add(createProgressPanel(), "verProgresoLP");
		mainPanel.add(seleccionarLP(), "seleccionarLP");
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
		addButton(panel, "VisualizarActividad","verActividad");

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
			String seleccion = (String) JOptionPane.showInputDialog(frame, "Seleccione un Learning Path:",
					"Learning Paths", JOptionPane.QUESTION_MESSAGE, null, opciones,
					opciones.length > 0 ? opciones[0] : null);

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
				JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.", "Error",
						JOptionPane.WARNING_MESSAGE);
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

	private void createActiviesInfoViewer() {
	    if (CA == null) {
	        JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.", 
	                                      "Error", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    // Obtener la lista de actividades del Learning Path seleccionado
	    List<Actividad> actividades = CA.getActividades();
	    String[] actividadesNombres = new String[actividades.size()];
	    for (int i = 0; i < actividades.size(); i++) {
	        actividadesNombres[i] = actividades.get(i).getNombre();
	    }

	    // Solicitar al usuario seleccionar una actividad
	    String actividadSeleccionada = (String) JOptionPane.showInputDialog(
	            frame,
	            "Seleccione una actividad:",
	            "Actividades",
	            JOptionPane.QUESTION_MESSAGE,
	            null,
	            actividadesNombres,
	            actividadesNombres.length > 0 ? actividadesNombres[0] : null
	    );

	    if (actividadSeleccionada != null) {
	        // Buscar la actividad seleccionada en la lista
	        Actividad actividad = null;
	        for (Actividad act : actividades) {
	            if (act.getNombre().equals(actividadSeleccionada)) {
	                actividad = act;
	                break;
	            }
	        }

	        if (actividad != null) {
	            // Crear el panel de información de la actividad y agregarlo al mainPanel
	            PanelInfoAct panelInfo = new PanelInfoAct(actividad);
	            mainPanel.add(panelInfo, "verActInfo");
	            cardLayout.show(mainPanel, "verActInfo");
	        } else {
	            JOptionPane.showMessageDialog(frame, "Actividad no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);}
	    }
	        
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
				JOptionPane.showMessageDialog(frame, "Debe seleccionar un Learning Path primero.", "Error",
						JOptionPane.WARNING_MESSAGE);
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

}
