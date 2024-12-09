package Interface;

import java.awt.*;
import javax.swing.*;
import caminosActividades.*;

public class PanelActCalificable extends JPanel{
	
	private JLabel calMin;
	
	public PanelActCalificable (ActividadCalificable act) {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		calMin = new JLabel("Calificacion minima: " + act.getCalificacionMin());
		panel.add(calMin);
	}
}
