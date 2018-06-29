package config;


public class ConfigJugglingFromServer {
	int background;
	int velocity;
	String form;

	// PApplet parent;

	public ConfigJugglingFromServer() {
	}

	// public ConfigFromServer(PApplet parent) {
	// this.parent = parent;
	// }

	public int getBackground() {
		return background;
	}

	public void setBackground(int background) {
		this.background = background;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
}
