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
	private JComboBox<String> comboTipo;//
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
	private JComboBox<String> obligatoria;
	private JLabel labPreReq;
	private JTextField cantidadPreReq;
	private JTextArea req;
	private JLabel labProxAct;
	private JTextField cantidadProxAct;
	private JTextArea proxAct;
	
	private String tipo;
	private boolean oblig;
	
	private PanelObjetivos POB;
	private PanelPreReq PPR;
	private PanelProxAct PPA;
	
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
		
		labDesc = new JLabel("Descripcion de la actividad:");
		add(labDesc);
		descAct = new JTextArea();
		add(descAct);
		
		labObjetivos = new JLabel("Objetivos de la actividad: ");
		add(labObjetivos);
		POB = new PanelObjetivos();
		add(POB);
		
		labDificultad = new JLabel("Dificultad: ");
		add(labDificultad);
		dificultad = new JTextArea();
		add(dificultad);
		
		labFechaLim = new JLabel("Fecha limite: ");
		add(labFechaLim);
		fechaLim = new JTextField();
		add(fechaLim);
		
		labDuracion = new JLabel("Duracion en minutos: ");
		add(labDuracion);
		duracion = new JTextField();
		add(duracion);
		
		labObligatoria = new JLabel("Es obligatoria: ");
		add(labObligatoria);
		obligatoria = new JComboBox();
		obligatoria.addItem("false");
		obligatoria.addItem("true");
		add(obligatoria);
		
		labPreReq = new JLabel("Pre requistos: ");
		add(labPreReq);
		PPR = new PanelPreReq();
		add(PPR);
		
		labProxAct = new JLabel("Proximas actividades: ");
		add(labProxAct);
		PPA = new PanelProxAct();
		add(PPA);		
		
		obligatoria.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tipo = obligatoria.getSelectedItem().toString();
				
				if(tipo == "false") {
					oblig = false;
				}
				else {
					oblig = true;
				}
			}
		});
		
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
