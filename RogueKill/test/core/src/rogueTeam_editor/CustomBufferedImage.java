package rogueTeam_editor;

import java.awt.image.BufferedImage;

public class CustomBufferedImage {

	private String tipo;
	private BufferedImage image;
	
	public CustomBufferedImage(String t, BufferedImage i) {
		
		this.tipo = t;
		this.image = i;
		
	}
	
	public CustomBufferedImage() {
		
		//this.image = new BufferedImage(0, 0, 0);
		
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	

}
