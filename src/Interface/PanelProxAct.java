package Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelProxAct extends JPanel{
	private JTextField cantidadProxAct;
	private JTextArea proxAct;
	private JLabel labCantProxAct;
	private JLabel labProxAct;
	private int contador = 1;
	private int cantidad;
	public List<String> proxAct_;
	
	public PanelProxAct() {

		setLayout(new GridLayout(2,2));
		
		labCantProxAct = new JLabel("¿Cuantos objetivos desea ingresar para la actividad?");
		add(labCantProxAct);
		
		cantidadProxAct = new JTextField();
		add(cantidadProxAct);
		
		cantidadProxAct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidad= Integer.parseInt(cantidadProxAct.getText());
			}
		});
		
		while (contador<=cantidad) {
			
			labProxAct = new JLabel("Ingrese el objetivo: ");
			add(labProxAct);
			
			proxAct = new JTextArea();
			add(proxAct);
			
			proxAct_.add(proxAct.getText());
			
			contador++;
		}
		
		
	}
	

}
