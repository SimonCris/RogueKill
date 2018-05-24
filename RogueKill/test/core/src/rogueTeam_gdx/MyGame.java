package rogueTeam_gdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import rogueTeam_game.GameConfig;
import rogueTeam_screen.MainMenuScreen;

public class MyGame extends Game {
	
	public static final float WIDTH = GameConfig.GAME_WIDTH;
	public static final float HEIGHT = GameConfig.GAME_HEIGHT;
	public Skin skin;
	public SpriteBatch batch;

	
	public MyGame(){
		
		skin = new Skin();

		
	}
	
	@Override
	public void create () {
		
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	

	}
	
	@Override
	public void dispose () {
		
		super.dispose();
		skin.dispose();
		
	}

	@Override
	public void render () {
		
		super.render();
	
	}
	
	

	@Override
	public void resize(int width, int height) {
		
		super.resize(width, height);
		
	}

	@Override
	public void pause() {
		
		super.pause();
		
	}

	@Override
	public void resume() {
		
		super.resume();
		
	}
}
