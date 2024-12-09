package Interface;

import java.awt.*;
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
	private JTextArea actSig;
	private StringBuilder actSig_ = new StringBuilder();
	private StringBuilder objetivos_ = new StringBuilder();
	
	public PanelInfoAct(Actividad act) {
		
		JPanel contenedor = new JPanel();
		JScrollPane scrollP = new JScrollPane(contenedor);

		scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
		
		descripcion = new JTextArea(act.getDescripcion());
		descripcion.setEditable(false);
		contenedor.add(descripcion);
		
		for (String objetivo:act.getObjetivos()) {
			objetivos_.append(objetivo).append("\n");
		}
		objetivos = new JTextArea("Objetivos: "+objetivos_.toString().trim());
		objetivos.setEditable(false);
		contenedor.add(objetivos);
		
		rating = new JLabel("Rating: " + act.getRating());
		contenedor.add(rating);
		
		dificultad = new JLabel("Dificultad: "+ act.getDificultad());
		contenedor.add(dificultad);
		
		fechaLim = new JLabel("Fecha Limite:" + act.getFechaLim()[0] + "/" + act.getFechaLim()[1] + "/" + act.getFechaLim()[2]);
		contenedor.add(fechaLim);
		
		duracion = new JLabel("Duracion: " + act.getDuracion() + " minutos");
		contenedor.add(duracion);
		
		if (act.isObligatoria()) {
			obligatoria = new JLabel("La actividad es obligatoria");
		}
		else {
			obligatoria = new JLabel("La actividad no es obligatoria");
		}
		contenedor.add(obligatoria);
		
		logInCreador = new JLabel("Autor: " + act.getCreadorLogin());
		contenedor.add(logInCreador);

		for (Actividad Req:act.getActividadesPrereqs()) {
			preReq_.append(Req.getNombre()).append("\n");
		}
		preReq = new JTextArea("Pre requisitos de la actividad: "+preReq_.toString().trim());
		preReq.setEditable(false);
		contenedor.add(preReq);
		
		for (String res:act.getResenias()) {
			rese_.append(res).append("\n");
		}
		rese = new JTextArea(rese_.toString().trim());
		rese.setEditable(false);
		contenedor.add(rese);
		
		for (Actividad sig:act.getActividadesSigExitoso()) {
			actSig_.append(sig.getNombre()).append("\n");
		}
		actSig = new JTextArea("Siguientes actividades:"+actSig_.toString().trim());
		actSig.setEditable(false);
		contenedor.add(preReq);
		
		
		
		
	}
	
	
}
