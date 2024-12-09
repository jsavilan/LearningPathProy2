package Interface;

import java.awt.BorderLayout;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controllers.LearningPathSystem;
import DatosEstudiante.DatosEstudianteExamen;
import DatosEstudiante.DatosEstudianteQuiz;
import caminosActividades.Actividad;
import caminosActividades.CaminoAprendizaje;
import usuarios.Profesor;

public class PanelEstadisticas extends JPanel{
	
	private final LearningPathSystem LPS;
    private final Profesor profesor;
    
    private final JFrame frame = new JFrame("Panel de Estadísticas");
    private final JPanel mainPanel = new JPanel(new BorderLayout());
    private final JComboBox<String> listaCaminosElegir;
    private final JTextArea estadisticasArea = new JTextArea(20, 30);

	
	public PanelEstadisticas(LearningPathSystem LPS, Profesor profesor) {
		this.LPS = LPS;
        this.profesor = profesor;
        
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        
        JButton verEstadistBoton = new JButton("Ver estadsticas");
        verEstadistBoton.addActionListener(e -> obtenerEstadisticas());
        
        listaCaminosElegir = new JComboBox<>();
        obtenerInfoCaminos();
        
        JButton irAActividadBoton = new JButton("Ir a Actividades");
        irAActividadBoton.addActionListener(e -> mostrarActividadesVentana());
        
        
        JPanel panelSelector = new JPanel(new BorderLayout());
        panelSelector.add(new JLabel("Selecciona un camino:"), BorderLayout.WEST);
        panelSelector.add(listaCaminosElegir, BorderLayout.CENTER);
        panelSelector.add(verEstadistBoton, BorderLayout.EAST);
        
        mainPanel.add(panelSelector, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(estadisticasArea), BorderLayout.CENTER);
        mainPanel.add(irAActividadBoton, BorderLayout.SOUTH);
        
        frame.add(mainPanel);
        frame.setVisible(true);
        
	}
	
	private void obtenerInfoCaminos() {
        Collection<CaminoAprendizaje> caminos = LPS.getCaminos().values();

        for (CaminoAprendizaje camino : caminos) {
            if (camino.getCreadorLogin().equals(profesor.getLogin())) {
            	listaCaminosElegir.addItem(camino.getTitulo());
            }
        }
    }
	
	 private void obtenerEstadisticas() {
	        String tituloCamino = (String) listaCaminosElegir.getSelectedItem();

	        if (tituloCamino == null) {
	            return;
	        }

	        CaminoAprendizaje camino = LPS.getCaminos().get(tituloCamino);

	        if (camino == null) {
	            JOptionPane.showMessageDialog(frame, "El camino seleccionado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        estadisticasArea.setText("Estadísticas del Camino: " + tituloCamino + "\n");
	        estadisticasArea.append("Número de estudiantes inscritos: " + camino.getActividades().get(0).getDatosEstudiantes().size() + "\n");
	        estadisticasArea.append("Número de actividades obligatorias: " + camino.getNumActividadesObligatorias() + "\n");
	    }
	 
	 private void mostrarActividadesVentana() {
	        String tituloCamino = (String) listaCaminosElegir.getSelectedItem();

	        if (tituloCamino == null) {
	            JOptionPane.showMessageDialog(frame, "Debe seleccionar un camino primero.", "Error", JOptionPane.WARNING_MESSAGE);
	            return;
	        }

	        CaminoAprendizaje camino = LPS.getCaminos().get(tituloCamino);

	        if (camino == null) {
	            JOptionPane.showMessageDialog(frame, "El camino seleccionado no existe.", "Error", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        //nueva ventana para mostrar las actividades
	        JFrame actividadesFrame = new JFrame("Actividades de " + tituloCamino);
	        actividadesFrame.setSize(600, 400);
	        actividadesFrame.setLayout(new BorderLayout());

	        JTextArea actividadesArea = new JTextArea(15, 30);
	        actividadesArea.setEditable(false);

	        JTextArea estadisticasActividades = new JTextArea(10, 30);
	        estadisticasActividades.setEditable(false);

	        for (Actividad actividad : camino.getActividades()) {
	            actividadesArea.append(actividad.getNombre() + "\n");
	            
	            int estudiantesInscritos = actividad.getDatosEstudiantes().size();
	            double promedio = calcularNotaPromedio(actividad);
	            estadisticasActividades.append("Actividad: " + actividad.getNombre() + "\n");
	            estadisticasActividades.append("Promedio: " + promedio + "\n");
	            estadisticasActividades.append("Estudiantes: " + estudiantesInscritos + "\n\n");
	        }

	        actividadesFrame.add(new JScrollPane(actividadesArea), BorderLayout.CENTER);
	        actividadesFrame.add(new JScrollPane(estadisticasActividades), BorderLayout.SOUTH);

	        actividadesFrame.setVisible(true);

	        actividadesFrame.addWindowListener(new java.awt.event.WindowAdapter() {
	            @Override
	            public void windowClosing(java.awt.event.WindowEvent e) {
	                actividadesFrame.dispose();
	                frame.setVisible(true);
	            }
	        });

	        frame.setVisible(false); //Se oculta la principal
	    }
	 
	 private double calcularNotaPromedio(Actividad actividad) {
		    double suma = 0;
		    int contador = 0;

		    for (var datos : actividad.getDatosEstudiantes().values()) {
		        if (datos instanceof DatosEstudianteExamen) {
		            DatosEstudianteExamen examen = (DatosEstudianteExamen) datos;
		            suma += examen.getCalificacion();
		            contador++;
		        } else if (datos instanceof DatosEstudianteQuiz) {
		            DatosEstudianteQuiz quiz = (DatosEstudianteQuiz) datos;
		            suma += quiz.getCalificacion();
		            contador++;
		        }
		    }

		    if (contador == 0) {
		        return 0;
		    } else {
		        return suma / contador;
		    }
		}

}
