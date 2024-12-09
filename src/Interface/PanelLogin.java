package Interface;

import javax.swing.*;

import Controllers.ControladorFuncionesGenerales;
import Controllers.LearningPathSystem;
import caminosActividades.CaminoAprendizaje;
import persistencia.CentralPersistencia;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

import usuarios.*;

public class PanelLogin {
	public static void main(String[] args) {
		new PanelLogin().start();
	}
	private LearningPathSystem LPS;
	private CentralPersistencia persistencia = new CentralPersistencia();
	private ControladorFuncionesGenerales controller_general;

	private final JFrame frame = new JFrame("Sistema de Inicio de Sesión");
	private HashMap<String, Usuario> users = new HashMap<>();
	private final CardLayout cardLayout = new CardLayout();

	// Paneles
	private final JPanel mainPanel = new JPanel(cardLayout);
	private final JPanel loginPanel = new JPanel(new GridBagLayout());
	private final JPanel registerPanel = new JPanel(new GridBagLayout());

	// Componentes compartidos
	private final JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

	public void start() {
		
		try {
			LPS = persistencia.cargarDatos();
		} catch (IOException e) {
			LPS = new LearningPathSystem();
		}
		
		this.controller_general = new ControladorFuncionesGenerales(LPS);
		this.users = LPS.getUsuarios();
		
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setSize(400, 300);

		configureLoginPanel();
		configureRegisterPanel();

		mainPanel.add(loginPanel, "login");
		mainPanel.add(registerPanel, "register");

		frame.add(mainPanel);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Guardar datos antes de cerrar
            	
            	HashMap<String, CaminoAprendizaje> caminos = LPS.getCaminos();
                try {
					persistencia.salvarDatos(users,caminos);
				} catch (IOException e1) {
				};
                frame.dispose();
            }
        });
	}

	private void configureLoginPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);

		// Bienvenido label
		JLabel welcomeLabel = new JLabel("Bienvenido", SwingConstants.CENTER);
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		loginPanel.add(welcomeLabel, gbc);

		// Login label
		JLabel loginLabel = new JLabel("Login:");
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		loginPanel.add(loginLabel, gbc);

		// Login textField
		JTextField loginField = new JTextField(20);
		gbc.gridx = 1;
		loginPanel.add(loginField, gbc);

		// Contraseña label
		JLabel passwordLabel = new JLabel("Contraseña:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		loginPanel.add(passwordLabel, gbc);

		// Contraseña textField
		JTextField passwordField = new JTextField(20);
		gbc.gridx = 1;
		loginPanel.add(passwordField, gbc);

		// Error label
		errorLabel.setForeground(Color.RED);
		errorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 3;
		loginPanel.add(errorLabel, gbc);

		// Botón Ingresar
		JButton loginButton = new JButton("Ingresar");
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		loginPanel.add(loginButton, gbc);

		// Botón Crear Usuario
		JButton registerButton = new JButton("Crear Usuario");
		gbc.gridy = 5;
		loginPanel.add(registerButton, gbc);

		// Acción del botón Ingresar
		loginButton.addActionListener(e -> {
			String login = loginField.getText();
			String password = passwordField.getText();
			
			boolean existe = controller_general.autentificarUsuario(login, password);

			if (existe) {
				Usuario user = users.get(login);
				String type = user.getType();
				frame.setVisible(false); // Ocultar ventana actual
				if (type.equals(Usuario.PROFESOR)) {
					new TeacherPanel(frame,(Profesor)user,LPS);
					
				} else {
					new StudentPanel(frame,(Estudiante) user,LPS);
					
				}
				errorLabel.setText("");
			} else {
				errorLabel.setText("ERROR: usuario o contraseña incorrectos");
			}
		});

		// Acción del botón Crear Usuario
		registerButton.addActionListener(e -> cardLayout.show(mainPanel, "register"));
	}

	private void configureRegisterPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 10, 5, 10);

		// Crear Usuario label
		JLabel registerLabel = new JLabel("Crear Nuevo Usuario", SwingConstants.CENTER);
		registerLabel.setFont(new Font("Arial", Font.BOLD, 18));
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		registerPanel.add(registerLabel, gbc);

		// Usuario label
		JLabel newUserLabel = new JLabel("Nuevo Usuario:");
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		registerPanel.add(newUserLabel, gbc);

		// Usuario textField
		JTextField newUserField = new JTextField(20);
		gbc.gridx = 1;
		registerPanel.add(newUserField, gbc);

		// Contraseña label
		JLabel newPasswordLabel = new JLabel("Nueva Contraseña:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		registerPanel.add(newPasswordLabel, gbc);

		// Contraseña textField
		JTextField newPasswordField = new JTextField(20);
		gbc.gridx = 1;
		registerPanel.add(newPasswordField, gbc);
		
		//Botones de roles label
		JLabel newRolLabel = new JLabel("Selecciona uno:");
	    gbc.gridx = 0;
	    gbc.gridy = 3;
	    registerPanel.add(newRolLabel, gbc);
	    
	    //Botones de roles y panel
	    JRadioButton botonProf = new JRadioButton("Profesor");
	    JRadioButton botonEst = new JRadioButton("Estudiante");
	    ButtonGroup grupoRoles = new ButtonGroup();
	    grupoRoles.add(botonProf);
	    grupoRoles.add(botonEst);
	    
	    JPanel rolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    rolPanel.add(botonProf);
	    rolPanel.add(botonEst);
	    gbc.gridx = 1;
	    registerPanel.add(rolPanel, gbc);

		// Botón Guardar
		JButton saveButton = new JButton("Guardar");
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		registerPanel.add(saveButton, gbc);

		// Botón Volver
		JButton backButton = new JButton("Volver");
		gbc.gridy = 5;
		registerPanel.add(backButton, gbc);

		// Acción del botón Guardar
		saveButton.addActionListener(e -> {
			String newUser = newUserField.getText();
			String newPassword = newPasswordField.getText();
			
			String type;
			
			if (botonProf.isSelected()) {
				type = Usuario.PROFESOR;
			} else if (botonEst.isSelected()) {
				type = Usuario.ESTUDIANTE;
			} else {
				JOptionPane.showMessageDialog(frame, "Hay que seleccionar uno máximo.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			boolean exitoso = controller_general.crearUsuario(newUser, newPassword, type);
			
			if (exitoso) {
				this.users = controller_general.getUsuarios();
				JOptionPane.showMessageDialog(frame, "Usuario creado con éxito", "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				cardLayout.show(mainPanel, "login");
			} else {
				JOptionPane.showMessageDialog(frame, "Error al crear usuario: usuario vacío o ya existe", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		// Acción del botón Volver
		backButton.addActionListener(e -> cardLayout.show(mainPanel, "login"));
	}
}
