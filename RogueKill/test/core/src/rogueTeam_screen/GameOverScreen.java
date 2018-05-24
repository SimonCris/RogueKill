package rogueTeam_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import rogueTeam_game.Score;
import rogueTeam_gdx.MyGame;
import rougueTeam_actors.BaseActor;

public class GameOverScreen extends BaseScreen {

	protected OrthographicCamera camera;
	private BaseActor sfondo;
	private MainMenuScreen ms;
	private LabelStyle creditsStyle = new LabelStyle();
	private BitmapFont creditsFont = new BitmapFont();
	private MyGame myGame;
	private String PlayerName;
	private Integer PlayerScore;

	private Sound gameOver = Gdx.audio.newSound(Gdx.files.internal("GameOver.mp3"));
	private boolean flag = false;
	
	public GameOverScreen(MyGame g, MainMenuScreen _ms, String PlayerName, Integer PlayerScore) {

		super(g);
		myGame = g;
		this.ms = _ms;
		this.PlayerName = PlayerName;
		this.PlayerScore = PlayerScore;

		creditsFont = new BitmapFont(Gdx.files.internal("font.fnt"));
		creditsStyle.font = creditsFont;

		camera = (OrthographicCamera) mainStage.getCamera();
		sfondo = new BaseActor(0, 0);

		sfondo.setTexture(new Texture("SfondoMainMenu.png"));
		mainStage.addActor(sfondo);

		float incrX = 1000, incrY = 1000;

		float titoloX = 600, titoloY = 1000;

		Label titolo = new Label("GAME OVER", creditsStyle);
		titolo.setBounds(0, 4.2f, 5, 2);
		titolo.setFontScale(3.0f, 3.0f);
		titolo.setColor(Color.RED);
		titolo.setPosition(titoloX, titoloY);

		mainStage.addActor(titolo);

		Label GameOver = new Label("PREMI ESC PER TORNARE AL MENU PRINCIPALE", creditsStyle);
		GameOver.setBounds(0, .2f, 1, 2);
		GameOver.setFontScale(1.5f, 1.5f);
		GameOver.setColor(Color.GOLDENROD);
		GameOver.setPosition(450, 300);
		mainStage.addActor(GameOver);

		Label GameOverPlayer = new Label("PLAYER : " + PlayerName + "         SCORE : " + PlayerScore, creditsStyle);
		GameOverPlayer.setBounds(0, .2f, 1, 2);
		GameOverPlayer.setFontScale(1.5f, 1.5f);
		GameOverPlayer.setColor(Color.LIME);
		GameOverPlayer.setPosition(525, 650);
		mainStage.addActor(GameOverPlayer);

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
		
		if(!flag){
			
			gameOver.play();
			flag = true;
			
		}

		camera.setToOrtho(false, 1600, 1600);

		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			Score s = new Score(PlayerName, PlayerScore);
			s.createUser();

			myGame.setScreen(new MainMenuScreen(game));

		}

	}

}
