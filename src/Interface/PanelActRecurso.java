package Interface;

import java.awt.*;
import javax.swing.*;
import caminosActividades.*;

public class PanelActRecurso extends JPanel {

	private JTextArea instrucciones;
	private JTextArea recurso;
	
	public PanelActRecurso(ActividadRecurso act) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		instrucciones = new JTextArea("Instrucciones: "+ act.getInstrucciones());
		instrucciones.setEditable(false);
		panel.add(instrucciones);
		
		recurso = new JTextArea("Recurso: "+ act.getRecurso());
		recurso.setEditable(false);
		panel.add(recurso);
		

	}
	
}
