package Interface;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class PanelPreReq extends JPanel{
	private JTextField cantidadPreReq;
	private JTextArea preReqs;
	private JLabel labCantPreReqs;
	private JLabel labPreReqs;
	private int contador = 1;
	private int cantidad;
	public List<String> preReqs_;
	
	public PanelPreReq() {

		setLayout(new GridLayout(2,2));
		
		labCantPreReqs = new JLabel("¿Cuantos objetivos desea ingresar para la actividad?");
		add(labCantPreReqs);
		
		cantidadPreReq = new JTextField();
		add(cantidadPreReq);
		
		cantidadPreReq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cantidad= Integer.parseInt(cantidadPreReq.getText());
			}
		});
		
		while (contador<=cantidad) {
			
			labPreReqs = new JLabel("Ingrese el objetivo: ");
			add(labPreReqs);
			
			preReqs = new JTextArea();
			add(preReqs);
			
			preReqs_.add(preReqs.getText());
			
			contador++;
		}
	}
	
}
