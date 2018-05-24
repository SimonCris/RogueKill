package rougueTeam_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import rogueTeam_game.GameUtils;

public class SimpleSoldier extends Nemico {
	
	public SimpleSoldier(float x, float y) {
		
		super(x, y);
		this.enemyLife = 30;

		this.setSize(30, 30);
		this.setEllipseBoundary();
		
		Texture playerTex = new Texture("SoldierAnimationSingle.png");
		this.storeAnimation("default", playerTex);

		float t = 0.15f;
		this.storeAnimation("down",
				GameUtils.parseSpriteSheet("SoldierAnimation.png", 3, 4, new int[] { 0, 1, 2 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("left",
				GameUtils.parseSpriteSheet("SoldierAnimation.png", 3, 4, new int[] { 3, 4, 5 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("right",
				GameUtils.parseSpriteSheet("SoldierAnimation.png", 3, 4, new int[] { 6, 7, 8 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("up",
				GameUtils.parseSpriteSheet("SoldierAnimation.png", 3, 4, new int[] { 9, 10, 11 }, t, PlayMode.LOOP_PINGPONG));
		
		
	}
	

	

	

	@Override
	public void act(float dt){
		super.act(dt);
		
	}


	       
	        
}




