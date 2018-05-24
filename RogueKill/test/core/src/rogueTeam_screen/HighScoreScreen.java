package rogueTeam_screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.shephertz.app42.paas.sdk.java.ServiceAPI;
import com.shephertz.app42.paas.sdk.java.game.Game;
import com.shephertz.app42.paas.sdk.java.game.ScoreBoardService;

import rogueTeam_game.Checker;
import rogueTeam_gdx.MyGame;
import rougueTeam_actors.BaseActor;

public class HighScoreScreen extends BaseScreen{
	
	protected OrthographicCamera camera;
	private BaseActor sfondo;
	private MainMenuScreen ms;
	private ArrayList<Label> creditsLabel;
	private LabelStyle creditsStyle = new LabelStyle();
	private BitmapFont creditsFont = new BitmapFont();
	private Game game;
	private MyGame myGame;
	
	private ServiceAPI spApi;
	private ScoreBoardService sbService;
	
	
	
	public HighScoreScreen(MyGame g, MainMenuScreen _ms) {
		
		super(g);
		myGame = g;
		this.ms = _ms;
		
		spApi = new ServiceAPI("a5ef2e51f40fb63d987de101a3bdd8573b8cd786cacb59e55916002f58dd80b6",
				"65f8f2af4a15b03a52f174f634826ca5089dc23cc36d2c7d7eab39d9bb821d4d");
		
		sbService = spApi.buildScoreBoardService();

		game = sbService.getTopNRankers("RougueKill", 10);
		creditsFont = new BitmapFont(Gdx.files.internal("font.fnt"));
		creditsStyle.font = creditsFont;
		
		camera = (OrthographicCamera) mainStage.getCamera();
		sfondo = new BaseActor(0, 0);
		
		sfondo.setTexture(new Texture("SfondoMainMenu.png"));
		mainStage.addActor(sfondo);
		
		float incrX = 675, incrY = 230;
		float titoloX = 575, titoloY = 1000;
		
		game.getScoreList().sort(new Checker());
		
		Label titolo = new Label("TOP 10 SCORE", creditsStyle);
		titolo.setBounds(0, 4.2f, 5, 2);
		titolo.setFontScale(3.0f, 3.0f);
		titolo.setColor(Color.RED);
		titolo.setPosition(titoloX, titoloY);
		
		mainStage.addActor(titolo);
		
		for(int x = 0; x < game.getScoreList().size(); x++){
			
			Label label = new Label(game.getScoreList().get(x).getUserName() + " : " + game.getScoreList().get(x).getValue(), creditsStyle);
			label.setBounds(0, 4.2f, 5, 2);
			label.setFontScale(1.5f, 1.5f);
			label.setColor(Color.GREEN);
			label.setPosition(incrX, incrY - 10);
			
			mainStage.addActor(label);
			incrY += 60;
			
		}
		
	}

	
	
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void render(float delta) {
		
		super.render(delta);

		camera.setToOrtho(false, 1600, 1600);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			
			myGame.setScreen(ms);
			
		}
		
		
		
	}

}
