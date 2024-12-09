package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import caminosActividades.*;

public class CrearAct extends JFrame{
	
	private JLabel labNombreAct;
	private JTextField nombreAct;
	private JLabel labDesc;	
	private JTextArea descAct;	
	private JLabel labTipo;		//
	private JComboBox comboTipo;//
	private JLabel labObjetivos;
	private JTextField cantidadObjetivos;
	private JTextArea objetivos;
	private JLabel labDificultad;
	private JTextArea dificultad;
	private JLabel labFechaLim;
	private JTextField fechaLim;
	private JLabel labDuracion;
	private JTextField duracion;
	private JLabel labObligatoria;
	private JComboBox obligatoria;
	private JLabel labPreReq;
	private JTextField cantidadPreReq;
	private JTextArea req;
	private JLabel labproxAct;
	private JTextField cantidadProxAct;
	private JTextArea proxAct;
	
	private String tipo;
	
	private PanelObjetivos POB;
	
	public CrearAct(){
		setLayout(new GridLayout(10,2));
		labTipo = new JLabel("Tipo de actividad.");
		add(labTipo);
		comboTipo = new JComboBox();
		comboTipo.addItem("Actividad de Recurso");
		comboTipo.addItem("Encuesta");
		comboTipo.addItem("Examen");
		comboTipo.addItem("Quiz");
		comboTipo.addItem("Tarea");
		add(comboTipo);
		
		labNombreAct = new JLabel("Nombre de la actividad: ");
		add(labNombreAct);
		nombreAct = new JTextField();
		add(nombreAct);
		
		labDesc = new JLabel("Descripcion de la actividad");
		add(labDesc);
		descAct = new JTextArea();
		add(descAct);
		
		labObjetivos = new JLabel("Objetivos de la actividad");
		add(labObjetivos);
		POB = new PanelObjetivos();
		add(POB);
		
		labDificultad = new JLabel("Dificultad: ");
		add(labDificultad);
		dificultad = new JTextArea();
		add(dificultad);
		
		labFechaLim = new JLabel("Fecha limite:");
		add(labFechaLim);
		fechaLim = new JTextField();
		add(fechaLim);
		
		labDuracion = new JLabel("Duracion en minutos:");
		add(labDuracion);
		duracion = new JTextField();
		add(duracion);
		
		comboTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = comboTipo.getSelectedItem().toString();
			}
		});
		
		if (tipo=="Actividad de Recurso") {
			
		}
		else if (tipo =="Encuesta") {
		}
		else if (tipo=="Examen") {
		}
		else if (tipo=="Quiz") {
		}
		else if (tipo=="Tarea") {
		}
		
	}
	
}
