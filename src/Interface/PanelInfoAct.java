package Interface;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import caminosActividades.*;

public class PanelInfoAct extends JPanel{
	
	private JTextArea descripcion;
	private JTextArea objetivos;
	private JLabel rating;
	private JLabel dificultad;
	private JLabel fechaLim;
	private JLabel duracion;
	private JLabel obligatoria;
	private JLabel logInCreador;
	private JTextArea preReq;
	private StringBuilder preReq_ = new StringBuilder();
	private JTextArea rese;
	private StringBuilder rese_ = new StringBuilder();
	private JLabel actSig;
	private StringBuilder objetivos_ = new StringBuilder();
	
	public PanelInfoAct(Actividad act) {
		
		JPanel contenedor = new JPanel();
		JScrollPane scrollP = new JScrollPane(contenedor);
		descripcion = new JTextArea(act.getDescripcion());
		descripcion.setEditable(false);
		
		for (String objetivo:act.getObjetivos()) {
			objetivos_.append(objetivo).append("\n");
		}
		objetivos = new JTextArea(objetivos_.toString().trim());
		descripcion.setEditable(false);
		
		rating = new JLabel("Rating: " + act.getRating());
		
		dificultad = new JLabel("Dificultad: "+ act.getDificultad());
		
		fechaLim = new JLabel("Fecha Limite:" + act.getFechaLim()[0] + "/" + act.getFechaLim()[1] + "/" + act.getFechaLim()[2]);
		
		duracion = new JLabel("Duracion: " + act.getDuracion() + " minutos");
		
		if (act.isObligatoria()) {
			obligatoria = new JLabel("La actividad es obligatoria");
		}
		else {
			obligatoria = new JLabel("La actividad no es obligatoria");
		}
		
		logInCreador = new JLabel("Autor: " + act.getCreadorLogin());

		for (Actividad Req:act.getActividadesPrereqs()) {
			preReq_.append(Req.getNombre()).append("\n");
		}
		preReq = new JTextArea(preReq_.toString().trim());
		preReq.setEditable(false);
		
		for (String res:act.getResenias()) {
			rese_.append(res).append("\n");
		}
		rese = new JTextArea(rese_.toString().trim());
		preReq.setEditable(false);
		
	}
	
	
}
