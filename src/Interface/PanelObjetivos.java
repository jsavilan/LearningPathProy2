package Interface;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import caminosActividades.*;

public class PanelObjetivos extends JPanel{
	
	private JTextField cantidadObjetivos;
	private JTextArea objetivos;
	private JLabel labCantObj;
	private JLabel labObjetivos;
	private int contador = 1;
	private int cantidad;
	public List<String> objetivos_;
	
	public PanelObjetivos() {
		
		setLayout(new GridLayout(2,2));
		
		labCantObj = new JLabel("¿Cuantos objetivos desea ingresar para la actividad?");
		add(labCantObj);
		
		cantidadObjetivos = new JTextField();
		add(cantidadObjetivos);
		
		cantidadObjetivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidad= Integer.parseInt(cantidadObjetivos.getText());
			}
		});
		
		while (contador<=cantidad) {
			
			labObjetivos = new JLabel("Ingrese el objetivo: ");
			add(labObjetivos);
			
			objetivos = new JTextArea();
			add(objetivos);
			
			objetivos_.add(objetivos.getText());
			
			contador++;
		}
		
		
	}
	
}
