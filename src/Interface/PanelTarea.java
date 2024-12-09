package Interface;

import javax.swing.*;
import caminosActividades.*;

public class PanelTarea extends JPanel {

	private JTextArea instrucciones;
	
	public PanelTarea(Tarea ta) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		instrucciones = new JTextArea(ta.getInstrucciones());
		instrucciones.setEditable(false);
		add(instrucciones);
		
	}
	
}
