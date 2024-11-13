package usuarios;

public abstract class Usuario {
	public static final String ESTUDIANTE = "Estudiante";
	public static final String PROFESOR = "Profesor";
	
	private String login;
	private String password;
	private String type;
	
	public Usuario(String login, String password, String type) {
		this.login = login;
		this.password = password;
		this.type = type;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	
}
