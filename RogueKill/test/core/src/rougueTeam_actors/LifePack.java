package rougueTeam_actors;

import com.badlogic.gdx.graphics.Texture;

import rogueTeam_game.GameConfig;

//classe che gestisce i lifepacks lasciati in giro per la mappa

public class LifePack extends BaseActor {

	private int packAmountLife = 20;// vita ripristinata


	public LifePack(float x,float y) {
		
		super(x,y);
		this.setTexture(new Texture("Medikit.png"));
		this.setWidth(GameConfig.SCALE_CONSTANT);
		this.setHeight(GameConfig.SCALE_CONSTANT);
		this.setRectangleBoundary();
		
	}

	//Get e Set
	public int getPackAmountLife() {
		return packAmountLife;

	}



	
}
