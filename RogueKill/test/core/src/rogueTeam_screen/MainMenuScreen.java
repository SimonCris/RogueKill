package rogueTeam_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import rogueTeam_game.GameConfig;
import rogueTeam_gdx.MyGame;

public class MainMenuScreen extends BaseScreen {

	protected OrthographicCamera camera;

	public GameScreen gs;
	public SettingsMenuScreen ss;
	public EditorScreen es;
	public LoadedScreen ls;
	private MapFromEditorScreen mp;
	private HighScoreScreen hs;

	private boolean isGamescr = false;
	private boolean isSettingScreen = false;
	private boolean isEditorScreen = false;
	private boolean isMapEditorScreen = false;

	private MyTextInputListener listener;
	private String playerName;

	// Texture per i vari bottoni
	private Texture background;

	private Texture playButtonActive;
	private Texture playButtonInactive;

	private Texture exitButtonActive;
	private Texture exitButtonInactive;

	private Texture editorButtonActive;
	private Texture editorButtonInactive;

	private Texture playEditorMapActive;
	private Texture playEditorMapInactive;

	private Texture loadGameButtonActive;
	private Texture loadGameButtonInactive;

	private Texture highScoreButtonActive;
	private Texture highScoreButtonInactive;
	
	private Music mainMenuSound  = Gdx.audio.newMusic(Gdx.files.internal("RougueKillTheme.mp3"));

	public MainMenuScreen(MyGame g) {

		super(g);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1600);

		background = new Texture("SfondoMainMenu.png");// sfondo del mainMenu
														// iniziale

		playButtonActive = new Texture("SinglePlayerButtonActive.png");// tasto
																		// play
		// attivo
		playButtonInactive = new Texture("SinglePlayerButtonInactive.png");// tasto
																			// play
		// non
		// attivo

		editorButtonActive = new Texture("EditorButtonActive.png");// tasto
																	// settings
																	// attivo
		editorButtonInactive = new Texture("EditorButtonInactive.png");// tasto
																		// settings
																		// non
																		// attivo

		exitButtonActive = new Texture("ExitButtonActive.png");// tasto exit
																// attivo
		exitButtonInactive = new Texture("ExitButtonInactive.png");// tasto exit
																	// non
																	// attivo

		loadGameButtonActive = new Texture("LoadGameButtonActive.png");// tasto
																		// load
																		// game
																		// attivo
		loadGameButtonInactive = new Texture("LoadGameButtonInactive.png");// tasto load game non attivo
		
		playEditorMapActive = new Texture("PlayRandomMapButtonActive.png");// tasto playEditorMap attivo
		playEditorMapInactive = new Texture("PlayRandomMapButtonInactive.png");// tasto
		// playEditorMap
		// non
		// attivo
		
		highScoreButtonActive = new Texture("HighScoreButtonActive.png");// tasto
		// credits
		// attivo
		highScoreButtonInactive = new Texture("HighScoreButtonInactive.png");// tasto
		// credits
		// non
		// attivo
		
	}

	@Override
	public void create() {
		listener = new MyTextInputListener(this);
		Gdx.input.getTextInput(listener, "Welcome To RougueKill", "", "Inserire Nome Giocatore");

	}

	@Override
	public void update() {

	}

	@Override
	public void render(float delta) {
		
		mainMenuSound.setLooping(true);
		mainMenuSound.play();

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		game.batch.draw(background, 0, 0);// setto lo sfondo del menu iniziale

		// setto il bottone play
		int x = (GameConfig.GAME_WIDTH / 2) - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 155 && Gdx.input.getX() > x - 80 && Gdx.input.getY() > GameConfig.PLAYBUTTON_Y - 650
				&& Gdx.input.getY() < GameConfig.PLAYBUTTON_Y - 580) {
			game.batch.draw(playButtonActive, x, GameConfig.PLAYBUTTON_Y, GameConfig.PLAYBUTTON_WIDTH,
					GameConfig.PLAYBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {

				if (isGamescr) {
					mainMenuSound.stop();
					game.setScreen(gs);
					dispose();

				} else {
					mainMenuSound.stop();
					gs = new GameScreen(game, this, playerName);
					game.setScreen(gs);
					isGamescr = true;
					dispose();
				}

			}
		} else
			game.batch.draw(playButtonInactive, x, GameConfig.PLAYBUTTON_Y, GameConfig.PLAYBUTTON_WIDTH,
					GameConfig.PLAYBUTTON_HEIGHT);
		
		
		// setto il bottone high score
				x = (GameConfig.GAME_WIDTH / 2) + 27 - (GameConfig.PLAYBUTTON_WIDTH / 2);

				if(Gdx.input.getX() < x + 130 && Gdx.input.getX() > x  - 100 && Gdx.input.getY() > GameConfig.MAINMENUBUTTON_Y + 395 && Gdx.input.getY() < GameConfig.MAINMENUBUTTON_Y + 415){
					game.batch.draw(highScoreButtonActive, x, GameConfig.MAINMENUBUTTON_Y, GameConfig.MAINMENUBUTTON_WIDTH, GameConfig.MAINMENUBUTTON_HEIGHT);

					if (Gdx.input.isTouched()) {

						game.pause();
						hs = new HighScoreScreen(game, this);
						game.setScreen(hs);

					}
				} else
					game.batch.draw(highScoreButtonInactive, x, GameConfig.MAINMENUBUTTON_Y, GameConfig.MAINMENUBUTTON_WIDTH,
							GameConfig.MAINMENUBUTTON_HEIGHT);
		
		
		

		// setto il bottone editor
		x = (GameConfig.GAME_WIDTH / 2) + 30 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 126 && Gdx.input.getX() > x - 110
				&& Gdx.input.getY() > GameConfig.EDITORBUTTON_Y - 270
				&& Gdx.input.getY() < GameConfig.EDITORBUTTON_Y - 215) {

			game.batch.draw(editorButtonActive, x, GameConfig.EDITORBUTTON_Y, GameConfig.EDITORBUTTON_WIDTH,
					GameConfig.EDITORBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {

				es = new EditorScreen(game, this);
				game.setScreen(es);
				dispose();

			}

		} else
			game.batch.draw(editorButtonInactive, x, GameConfig.EDITORBUTTON_Y, GameConfig.EDITORBUTTON_WIDTH,
					GameConfig.EDITORBUTTON_HEIGHT);

		// setto il bottone exit
		x = (GameConfig.GAME_WIDTH / 2) + 7 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 90 && Gdx.input.getX() > x - 60 && Gdx.input.getY() > GameConfig.EXITBUTTON_Y + 610
				&& Gdx.input.getY() < GameConfig.EXITBUTTON_Y + 680) {

			game.batch.draw(exitButtonActive, x, GameConfig.EXITBUTTON_Y, GameConfig.EXITBUTTON_WIDTH,
					GameConfig.EXITBUTTON_HEIGHT);

			if (Gdx.input.isTouched())
				Gdx.app.exit();

		} else
			game.batch.draw(exitButtonInactive, x, GameConfig.EXITBUTTON_Y, GameConfig.EXITBUTTON_WIDTH,
					GameConfig.EXITBUTTON_HEIGHT);

		// setto il bottone load game
		x = (GameConfig.GAME_WIDTH / 2) + 15 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 170 && Gdx.input.getX() > x - 100
				&& Gdx.input.getY() > GameConfig.LOADGAMEBUTTON_Y - 40
				&& Gdx.input.getY() < GameConfig.LOADGAMEBUTTON_Y + 25) {
			game.batch.draw(loadGameButtonActive, x, GameConfig.LOADGAMEBUTTON_Y, GameConfig.LOADGAMEBUTTON_WIDTH,
					GameConfig.LOADGAMEBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {

				mainMenuSound.stop();
				ls = new LoadedScreen(game, this, playerName);
				ls.loadFromSaveinGame();
				dispose();
				game.setScreen(ls);

				/*
				 * mp = new MapFromEditorScreen(game, this);
				 * mp.loadFromString(); dispose(); game.setScreen(mp);
				 */
			}
		}

		else
			game.batch.draw(loadGameButtonInactive, x, GameConfig.LOADGAMEBUTTON_Y, GameConfig.LOADGAMEBUTTON_WIDTH,
					GameConfig.LOADGAMEBUTTON_HEIGHT);

		// setto il bottone playEditorMap
		x = (GameConfig.GAME_WIDTH / 2) + 30 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 130 && Gdx.input.getX() > x - 100
				&& Gdx.input.getY() > GameConfig.MAPEDITORBUTTON_Y + 190
				&& Gdx.input.getY() < GameConfig.MAPEDITORBUTTON_Y + 250) {
			game.batch.draw(playEditorMapActive, x, GameConfig.MAPEDITORBUTTON_Y, GameConfig.MAPEDITORBUTTON_WIDTH,
					GameConfig.MAPEDITORBUTTON_HEIGHT);
			if (Gdx.input.isTouched()) {
				mainMenuSound.stop();
				game.pause();
				mp = new MapFromEditorScreen(game, this, playerName);
				mp.loadFromString();
				game.setScreen(mp);

			}
		} else
			game.batch.draw(playEditorMapInactive, x, GameConfig.MAPEDITORBUTTON_Y, GameConfig.MAPEDITORBUTTON_WIDTH,
					GameConfig.MAPEDITORBUTTON_HEIGHT);

		game.batch.end();

	}

	@Override
	public void dispose() {

		super.dispose();
		background.dispose();
	}

	public boolean isSettingScreen() {
		return isSettingScreen;
	}

	public void setSettingScreen(boolean isSettingScreen) {
		this.isSettingScreen = isSettingScreen;
	}

	public boolean isEditorScreen() {
		return isEditorScreen;
	}

	public void setEditorScreen(boolean isEditorScreen) {
		this.isEditorScreen = isEditorScreen;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}