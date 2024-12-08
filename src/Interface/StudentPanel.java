package Interface;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import usuarios.Estudiante;

//Panel principal para estudiantes
public class StudentPanel {
	
	private Estudiante usuario;
	
	public StudentPanel(JFrame parentFrame, Estudiante usuario) {
		
		this.usuario = usuario;
		
		JFrame studentFrame = new JFrame("Panel de Estudiante");
		studentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		studentFrame.setSize(400, 300);

		JLabel label = new JLabel("Bienvenido al Panel de Estudiantes", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		studentFrame.add(label, BorderLayout.CENTER);

		studentFrame.setLocationRelativeTo(parentFrame);
		studentFrame.setVisible(true);
	}
}
