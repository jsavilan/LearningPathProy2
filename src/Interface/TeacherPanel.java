package Interface;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import usuarios.Profesor;

//Panel principal para profesores
public class TeacherPanel {
	
	private Profesor usuario;
	
	public TeacherPanel(JFrame parentFrame, Profesor usuario) {
		
		this.usuario = usuario;
		
		JFrame teacherFrame = new JFrame("Panel de Profesor");
		teacherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		teacherFrame.setSize(400, 300);

		JLabel label = new JLabel("Bienvenido al Panel de Profesores", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 18));
		teacherFrame.add(label, BorderLayout.CENTER);

		teacherFrame.setLocationRelativeTo(parentFrame);
		teacherFrame.setVisible(true);
	}
}