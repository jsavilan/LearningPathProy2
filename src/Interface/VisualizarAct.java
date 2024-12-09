package Interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import caminosActividades.*;

@SuppressWarnings("serial")
public class VisualizarAct extends JFrame implements ActionListener {
    
	private JPanel panelAct;
	private JLabel labNombre;
	private JButton botAtras;
	
	
	public VisualizarAct(Actividad act) {
		
		panelAct= new JPanel();
		this.labNombre = new JLabel(act.getNombre());
		String tipo = act.getType();
		
	}
	
}
