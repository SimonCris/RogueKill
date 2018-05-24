package rogueTeam_game;

import java.io.Serializable;

public class Identifier implements Serializable {
	
	/** La classe Identifier è composta solo da una Stringa per 
	 * distinguere il tipo di Attore che vado a salvare e due
	 * float per la posizione.
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;
	private float posX;
	private float posY;
	
	public Identifier(String _t, float _x, float _y) {
		this.tipo=_t;
		this.posX=_x;
		this.posY=_y;
		// TODO Auto-generated constructor stub
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}


}
