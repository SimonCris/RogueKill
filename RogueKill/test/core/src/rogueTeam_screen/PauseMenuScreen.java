package rogueTeam_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

import rogueTeam_game.GameConfig;
import rogueTeam_game.Score;
import rogueTeam_gdx.MyGame;

public class PauseMenuScreen extends BaseScreen {

	protected OrthographicCamera camera;

	public GameScreen gs = new GameScreen(game, this);
	public LoadedScreen ls = new LoadedScreen(game, this);
	public MainMenuScreen mm;
	public MapFromEditorScreen mp = new MapFromEditorScreen(game, this);

	private boolean isLoadedscr = false;
	private boolean isMapEditorScreenLoader = false;
	private boolean isGameScreen = false;

	private String playerName;
	private Integer playerScore;

	// Texture per i vari bottoni
	private Texture background;

	private Texture resumeButtonActive;
	private Texture resumeButtonInactive;

	private Texture exitButtonActive;
	private Texture exitButtonInactive;

	private Texture saveButtonActive;
	private Texture saveButtonInactive;

	

	private Texture mainMenuButtonActive;
	private Texture mainMenuButtonInactive;
	
	private Music pauseMenuSound  = Gdx.audio.newMusic(Gdx.files.internal("RougueKillTheme.mp3"));

	public PauseMenuScreen(MyGame g, GameScreen gs, String s) {
		super(g);
		this.playerName = s;
		this.playerScore = gs.getPlayer().getScore();
		this.gs = gs;
		this.isGameScreen = true;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1600);

		background = new Texture("SfondoMainMenu.png");// sfondo del mainMenu
														// iniziale

		resumeButtonActive = new Texture("ResumeButtonActive.png");// tasto
																	// resume
																	// attivo
		resumeButtonInactive = new Texture("ResumeButtonInactive.png");// tasto
																		// resume
																		// non
																		// attivo

		exitButtonActive = new Texture("ExitButtonActive.png");// tasto exit
																// attivo
		exitButtonInactive = new Texture("ExitButtonInactive.png");// tasto exit
																	// non
																	// attivo

		

		saveButtonActive = new Texture("SaveGameButtonActive.png");// tasto save
																	// game
																	// attivo
		saveButtonInactive = new Texture("SaveGameButtonInactive.png");// tasto
																		// save
																		// game
																		// non
																		// attivo

		mainMenuButtonActive = new Texture("MainMenuButtonActive.png");// tasto
																		// main
																		// menu
																		// attivo
		mainMenuButtonInactive = new Texture("MainMenuButtonInactive.png");// tasto
																			// main
																			// menu
																			// non
																			// attivo

	}

	public PauseMenuScreen(MyGame game, LoadedScreen loadedScreen) {

		super(game);
		this.ls = loadedScreen;
		this.isLoadedscr = true;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1600);

		background = new Texture("SfondoMainMenu.png");// sfondo del mainMenu
														// iniziale

		resumeButtonActive = new Texture("ResumeButtonActive.png");// tasto
																	// resume
																	// attivo
		resumeButtonInactive = new Texture("ResumeButtonInactive.png");// tasto
																		// resume
																		// non
																		// attivo

		exitButtonActive = new Texture("ExitButtonActive.png");// tasto exit
																// attivo
		exitButtonInactive = new Texture("ExitButtonInactive.png");// tasto exit
																	// non
																	// attivo


		saveButtonActive = new Texture("SaveGameButtonActive.png");// tasto save
																	// game
																	// attivo
		saveButtonInactive = new Texture("SaveGameButtonInactive.png");// tasto
																		// save
																		// game
																		// non
																		// attivo

		mainMenuButtonActive = new Texture("MainMenuButtonActive.png");// tasto
																		// main
																		// menu
																		// attivo
		mainMenuButtonInactive = new Texture("MainMenuButtonInactive.png");// tasto
																			// main
																			// menu
																			// non
																			// attivo

	}

	public PauseMenuScreen(MyGame game, MapFromEditorScreen mapEditorScreen) {

		super(game);
		this.mp = mapEditorScreen;
		this.isMapEditorScreenLoader = true;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1600);

		background = new Texture("SfondoMainMenu.png");// sfondo del mainMenu
														// iniziale

		resumeButtonActive = new Texture("ResumeButtonActive.png");// tasto
																	// resume
																	// attivo
		resumeButtonInactive = new Texture("ResumeButtonInactive.png");// tasto
																		// resume
																		// non
																		// attivo

		exitButtonActive = new Texture("ExitButtonActive.png");// tasto exit
																// attivo
		exitButtonInactive = new Texture("ExitButtonInactive.png");// tasto exit
																	// non
																	// attivo


		saveButtonActive = new Texture("SaveGameButtonActive.png");// tasto save
																	// game
																	// attivo
		saveButtonInactive = new Texture("SaveGameButtonInactive.png");// tasto
																		// save
																		// game
																		// non
																		// attivo

		mainMenuButtonActive = new Texture("MainMenuButtonActive.png");// tasto
																		// main
																		// menu
																		// attivo
		mainMenuButtonInactive = new Texture("MainMenuButtonInactive.png");// tasto
																			// main
																			// menu
																			// non
																			// attivo

	}

	@Override
	public void create() {

	}

	@Override
	public void update() {

	}

	@Override
	public void render(float delta) {
		
		pauseMenuSound.setLooping(true);
		pauseMenuSound.play();

		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();

		game.batch.draw(background, 0, 0);// setto lo sfondo del menu iniziale

		// setto il bottone resume
		int x = (GameConfig.GAME_WIDTH / 2) - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 155 && Gdx.input.getX() > x - 80
				&& Gdx.input.getY() > GameConfig.RESUMEBUTTON_Y - 650
				&& Gdx.input.getY() < GameConfig.RESUMEBUTTON_Y - 580) {
			game.batch.draw(resumeButtonActive, x, GameConfig.RESUMEBUTTON_Y, GameConfig.RESUMEBUTTON_WIDTH,
					GameConfig.RESUMEBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {
				pauseMenuSound.stop();
				if (this.isLoadedscr)
					game.setScreen(ls);
				else if (this.isGameScreen)
					game.setScreen(gs);
				else if (this.isMapEditorScreenLoader)
					game.setScreen(mp);

				dispose();
			}

		} else
			game.batch.draw(resumeButtonInactive, x, GameConfig.RESUMEBUTTON_Y, GameConfig.RESUMEBUTTON_WIDTH,
					GameConfig.RESUMEBUTTON_HEIGHT);


		// setto il bottone exit
		x = (GameConfig.GAME_WIDTH / 2) + 7 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 90 && Gdx.input.getX() > x - 60 && Gdx.input.getY() > GameConfig.EXITBUTTON_Y + 610
				&& Gdx.input.getY() < GameConfig.EXITBUTTON_Y + 680) {

			game.batch.draw(exitButtonActive, x, GameConfig.EXITBUTTON_Y, GameConfig.EXITBUTTON_WIDTH,
					GameConfig.EXITBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {
				Score s = new Score(playerName, playerScore);
				s.createUser();
				Gdx.app.exit();
			}
		} else
			game.batch.draw(exitButtonInactive, x, GameConfig.EXITBUTTON_Y, GameConfig.EXITBUTTON_WIDTH,
					GameConfig.EXITBUTTON_HEIGHT);

		// setto il bottone load game
		x = (GameConfig.GAME_WIDTH / 2) + 15 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		// setto il bottone saveGame
		x = (GameConfig.GAME_WIDTH / 2) + 30 - (GameConfig.PLAYBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 130 && Gdx.input.getX() > x - 100
				&& Gdx.input.getY() > GameConfig.MAPEDITORBUTTON_Y + 190
				&& Gdx.input.getY() < GameConfig.MAPEDITORBUTTON_Y + 250) {

			game.batch.draw(saveButtonActive, x, GameConfig.MAPEDITORBUTTON_Y, GameConfig.MAPEDITORBUTTON_WIDTH,
					GameConfig.MAPEDITORBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {

				if (ls.isLoaded()) {// controllo se sono sotto loadedScreen
					ls.SaveGame();
					dispose();
					game.setScreen(ls);
				} else {
					gs.SaveGame();
					dispose();
					game.setScreen(gs);
				}
			}

		} else {
			game.batch.draw(saveButtonInactive, x, GameConfig.MAPEDITORBUTTON_Y, GameConfig.MAPEDITORBUTTON_WIDTH,
					GameConfig.MAPEDITORBUTTON_HEIGHT);
		}

		// setto il bottone mainMenu
		x = (GameConfig.GAME_WIDTH / 2) + 12 - (GameConfig.MAINMENUBUTTON_WIDTH / 2);

		if (Gdx.input.getX() < x + 130 && Gdx.input.getX() > x - 100
				&& Gdx.input.getY() > GameConfig.MAINMENUBUTTON_Y + 405
				&& Gdx.input.getY() < GameConfig.MAINMENUBUTTON_Y + 440) {

			game.batch.draw(mainMenuButtonActive, x, GameConfig.MAINMENUBUTTON_Y, GameConfig.MAINMENUBUTTON_WIDTH,
					GameConfig.MAINMENUBUTTON_HEIGHT);

			if (Gdx.input.isTouched()) {

				pauseMenuSound.stop();
				mm = new MainMenuScreen(game);
				dispose();
				game.setScreen(mm);

			}

		} else {
			game.batch.draw(mainMenuButtonInactive, x, GameConfig.MAINMENUBUTTON_Y, GameConfig.MAINMENUBUTTON_WIDTH,
					GameConfig.MAINMENUBUTTON_HEIGHT);
		}

		game.batch.end();

	}

	@Override
	public void dispose() {

		super.dispose();
		background.dispose();
	}
}