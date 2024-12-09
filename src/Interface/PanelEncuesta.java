package Interface;

import java.awt.*;
import javax.swing.*;
import caminosActividades.*;

public class PanelEncuesta extends JPanel{
	
	private JLabel pregunta;
	private JTextArea respuesta;
	private int contador = 0;
	
	public PanelEncuesta(Encuesta enc) {
		JPanel contenedor = new JPanel();
		JScrollPane scrollP = new JScrollPane(contenedor);

		scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		
		int tamanio = enc.getPreguntasAbiertas().size();
		
		while (contador < tamanio) {
			
			
			pregunta = new JLabel(contador + ". " + enc.getPreguntasAbiertas().get(contador));
			contenedor.add(pregunta);
			
			respuesta = new JTextArea();
			contenedor.add(respuesta);	
			contador +=1;		
		}
	}
}
