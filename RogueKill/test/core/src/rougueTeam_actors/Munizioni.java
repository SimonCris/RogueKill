package rougueTeam_actors;


import com.badlogic.gdx.graphics.Texture;

import rogueTeam_game.GameConfig;

//classe che gestisce il tipo di munizioni per una relativa arma
public class Munizioni extends BaseActor {

    private Integer ammoAmount = GameConfig.AMMO_AMOUNT;

	public Munizioni(float x, float y) {
		super(x,y);
		this.setTexture(new Texture("Munizioni.png"));
		this.setWidth(GameConfig.SCALE_CONSTANT);
		this.setHeight(GameConfig.SCALE_CONSTANT);
		this.setRectangleBoundary();
		
	}

	

	public void setAmmoAmount(Integer a) {
		this.ammoAmount = a;
	}

	

	public Integer getAmmoAmount() {
		return ammoAmount;
	}


}
