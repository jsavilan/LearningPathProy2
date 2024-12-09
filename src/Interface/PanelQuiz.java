package Interface;

import java.awt.*;
import javax.swing.*;
import caminosActividades.*;

public class PanelQuiz extends JPanel{

	private ButtonGroup opciones;
	private JLabel pregunta;
	private JRadioButton opcion;
	
	public PanelQuiz(Quiz q) {
		JPanel contenedor = new JPanel();
		JScrollPane scrollP = new JScrollPane(contenedor);

		scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));

		for (PreguntaQuiz pq : q.getPreguntas()) 
		{
			pregunta = new JLabel(pq.getTextoPregunta());
			contenedor.add(pregunta);
			for (String opc : pq.getOpciones()) 
			{
				opcion = new JRadioButton(opc);
				opciones.add(opcion);
				contenedor.add(opcion);
			}
		}
		
	}
}
