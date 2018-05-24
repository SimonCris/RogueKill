package rogueTeam_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import rogueTeam_game.GameConfig;
import rogueTeam_gdx.MyGame;

public class SettingsMenuScreen extends BaseScreen{

	protected OrthographicCamera camera;
	public GameScreen gs;
	
	private boolean isGamescr = false;
	
	private Texture background;
	
	public SettingsMenuScreen(MyGame g) {
		
		super(g);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1600);
		
		background = new Texture("SfondoMainMenu.png");//sfondo del menu settings
		
	}


	@Override
	public void create() {
		
		
	}


	@Override
	public void update() {
		
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		
		game.batch.draw(background,0,0);//setto lo sfondo del menu di settings
		
		
		game.batch.end();

	}


	@Override
	public void dispose() {
		
		super.dispose();
		background.dispose();
	}
}
