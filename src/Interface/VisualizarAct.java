package Interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import caminosActividades.*;

@SuppressWarnings("serial")
public class VisualizarAct extends JFrame{
    
	private JPanel panelAct;
	private JLabel labNombre;
	private JButton botAtras;
	private PanelInfoAct PIA;
	private PanelActCalificable PAC;
	private PanelActRecurso PAR;
	private PanelEncuesta PENC;
	private PanelExamen PEX;
	private PanelQuiz PQZ;
	private PanelTarea PTA;
	private JFrame frame;
	
	public VisualizarAct(Actividad act) {
		
		JPanel contenedor = new JPanel();
		JScrollPane scrollP = new JScrollPane(contenedor);

		scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		
		this.labNombre = new JLabel(act.getNombre());
		contenedor.add(labNombre);
		String tipo = act.getType();
		
		PIA = new PanelInfoAct(act);
		contenedor.add(PIA);
		if (tipo=="Actividad de Recurso") {
			PAR = new PanelActRecurso((ActividadRecurso) act);
			contenedor.add(PAR);
		}
		else if (tipo =="Encuesta") {
			PENC = new PanelEncuesta((Encuesta) act);
			contenedor.add(PENC);
		}
		else if (tipo=="Examen") {
			PEX = new PanelExamen((Examen)act);
			contenedor.add(PEX);
		}
		else if (tipo=="Quiz") {
			PQZ = new PanelQuiz((Quiz)act);
			contenedor.add(PQZ);
		}
		else if (tipo=="Tarea") {
			PTA = new PanelTarea((Tarea) act);
			contenedor.add(PTA);
		}
		setTitle( "Actividad" );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setSize( 600, 900 );
        setLocationRelativeTo( null );
        setVisible( true );
	}
	
}
