package rogueTeam_screen;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import rogueTeam_game.GameConfig;
import rogueTeam_game.Identifier;
import rogueTeam_gdx.MyGame;
import rogueTeam_structure.MapCreator;
import rougueTeam_actors.BaseActor;
import rougueTeam_actors.Boss;
import rougueTeam_actors.LifePack;
import rougueTeam_actors.Munizioni;
import rougueTeam_actors.Personaggio;
import rougueTeam_actors.Proiettile;
import rougueTeam_actors.SimpleSoldier;
import rougueTeam_actors.WallActor;

public class MapFromEditorScreen extends BaseScreen {

	private MainMenuScreen ms;
	private PauseMenuScreen pm;
	private MyGame game;
	private int numMap;
	private int[][] loadedMap = new int[GameConfig.MAPCREATORHEIGHT][GameConfig.MAPCREATORWIDTH];
	private String savedMap;
	private boolean isPauseMenu = false;
	private String playerName;

	private ArrayList<Proiettile> proiettili;
	private ArrayList<WallActor> wallList;
	private ArrayList<LifePack> lifePackList;
	private ArrayList<Munizioni> ammoList;
	private ArrayList<SimpleSoldier> sSoldierList;
	private ArrayList<Proiettile> proiettiliNemici;

	private Personaggio lp;
	private OrthographicCamera cam;
	private InputMultiplexer in;
	private BaseActor ground;
	private boolean isProiettile = false;
	private Proiettile p;
	private ArrayList<BaseActor> removeList = new ArrayList<BaseActor>();
	private boolean alreadyRemoved = false;
	ArrayList<Identifier> SavedElements;

	private Label textScore;
	private Label textLife;
	private Label textAmmo;
	private LabelStyle textStyle;
	private BitmapFont font = new BitmapFont();

	private boolean isBossOnStage = false;
	private Boss Boss;
	private Label textBossLife;
	
	private Sound fire = Gdx.audio.newSound(Gdx.files.internal("fire.wav"));
	private Music gameSoundTrack =  Gdx.audio.newMusic(Gdx.files.internal("MapLoop.mp3"));
	private Music gameBossSoundTrack = Gdx.audio.newMusic(Gdx.files.internal("BossMusic.mp3"));
	private Sound ammoDiscarge = Gdx.audio.newSound(Gdx.files.internal("ArmaScarica.mp3"));

	// Costruttore per mainMenuScreen
	public MapFromEditorScreen(MyGame g, MainMenuScreen _ms, String s) {

		super(g);
		setPlayerName(s);
		this.game = g;
		setMs(_ms);

	}

	// Costruttore per pauseMenuScreen
	public MapFromEditorScreen(MyGame g, PauseMenuScreen pm) {

		super(g);
		this.game = g;
		this.pm = pm;

	}

	// Costruttore per pauseMenuScreen
	public MapFromEditorScreen(MyGame g, PauseMenuScreen pm, String s) {

		super(g);
		this.game = g;
		this.pm = pm;
		setPlayerName(s);

	}

	@Override
	public void create() {

		proiettili = new ArrayList<Proiettile>();
		wallList = new ArrayList<WallActor>();
		lifePackList = new ArrayList<LifePack>();
		ammoList = new ArrayList<Munizioni>();
		sSoldierList = new ArrayList<SimpleSoldier>();
		proiettiliNemici = new ArrayList<Proiettile>();

		cam = ((OrthographicCamera) mainStage.getCamera());

		// inizializza il baseActor per lo sfondo della mappa
		// con un random, se è 1 setta la mappa di erba, 2 la mappa di terra, 3
		// la mappa di ghiaccio
		ground = new BaseActor(0, 0);

		Random r = new Random();
		numMap = r.nextInt(5) + 1;

		switchMaptype(numMap);

		mainStage.addActor(ground);

		addAllToStage();

		lp = new Personaggio(600, 600);
		Gdx.input.setInputProcessor(in);

		setProiettiliNemici(new ArrayList<Proiettile>());
		for (SimpleSoldier s : sSoldierList) {
			s.setpList(proiettiliNemici);
		}

		// INIZIALIZZO I LABEL
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		textStyle = new LabelStyle();
		textStyle.font = font;

		// TextScore LABEL
		textScore = new Label("Score : " + lp.getScore(), textStyle);
		textScore.setBounds(0, .2f, 1, 2);
		textScore.setFontScale(1f, 1f);
		textScore.setColor(Color.FIREBRICK);

		// PlayerLife LABEL
		textLife = new Label("Health : " + lp.getPlayerLife(), textStyle);
		textLife.setBounds(0, .2f, 1, 2);
		textLife.setFontScale(1f, 1f);
		textLife.setColor(Color.OLIVE);

		// PlayerAmmo LABEL
		textAmmo = new Label("Ammo : " + lp.getAmmoAmount(), textStyle);
		textAmmo.setBounds(0, .2f, 1, 2);
		textAmmo.setFontScale(1f, 1f);
		textAmmo.setColor(Color.DARK_GRAY);

	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		
		gameSoundTrack.setLooping(true);
		gameSoundTrack.play();
		
		gameBossSoundTrack.setLooping(true);

		if (lp.getPlayerLife() <= 0) {
			gameSoundTrack.stop();
			gameBossSoundTrack.stop();
			this.dispose();
			game.pause();
			game.setScreen(new GameOverScreen(game, ms, playerName, lp.getScore()));
		}

		if (isBossOnStage) {
			textBossLife.setText("BOSS HEALTH : " + Boss.getEnemyLife());
			textBossLife.setPosition(MathUtils.clamp(cam.position.x, 800 / 2, 1600 - 800 / 2) - 400,
					MathUtils.clamp(cam.position.y, 800 / 2, 1600 - 800 / 2) + 375);

			for (Proiettile p : proiettili) {
				if (Boss.overlaps(p, false)) {
					Boss.setEnemyLife(Boss.getEnemyLife() - p.getDanno());
					removeList.add(p);
				}

				if (Boss.getEnemyLife() <= 0) {

					gameSoundTrack.stop();
					gameBossSoundTrack.stop();
					game.pause();
					game.setScreen(new YouWinScreen(game, ms, playerName, lp.getScore()));

				}

			}

		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Aggiorno i label
		textScore.setText("Score : " + lp.getScore());
		textLife.setText("Health : " + lp.getPlayerLife());
		textAmmo.setText("Ammo : " + lp.getAmmoAmount());
		switchLabelColors();

		// Input movimenti
		lp.setVelocityXY(0, 0);
		int playerSpeed = 100;
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
			lp.setVelocityXY(-playerSpeed, 0);
			lp.setActiveAnimation("left");
			lp.setBulletDirection("LEFT");
		}
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
			lp.setVelocityXY(playerSpeed, 0);
			lp.setActiveAnimation("right");
			lp.setBulletDirection("RIGHT");
		}
		if (Gdx.input.isKeyPressed(Keys.UP)) {
			lp.setVelocityXY(0, playerSpeed);
			lp.setActiveAnimation("up");
			lp.setBulletDirection("UP");
		}
		if (Gdx.input.isKeyPressed(Keys.DOWN)) {
			lp.setVelocityXY(0, -playerSpeed);
			lp.setActiveAnimation("down");
			lp.setBulletDirection("DOWN");
		}
		if (Gdx.input.isKeyJustPressed(Keys.B)) {
			if (lp.getAmmoAmount() > 0) {
				
				fire.play();
				
				p = lp.fire();
				mainStage.addActor(p);
				proiettili.add(p);
				isProiettile = true;
				lp.setAmmoAmount(lp.getAmmoAmount() - 1);
			}
			else
				ammoDiscarge.play();
		}

		if (lp.getSpeed() < 1) {
			lp.pauseAnimation();
			lp.setAnimationFrame(1);
		} else
			lp.startAnimation();

		// Metto in pausa il gioco
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {

			gameSoundTrack.stop();
			gameBossSoundTrack.stop();
			
			game.pause();
			pm = new PauseMenuScreen(game, this);
			game.setScreen(pm);
			dispose();
			setPauseMenu(true);

		}

		// Aggiorno la grafica
		float dt = Gdx.graphics.getDeltaTime();// imposta un dt random della
												// grafica
		mainStage.act(dt);

		cam.setToOrtho(false, 800, 800);

		// Setta la cam col giocatore. segue il giocatore
		cam.position.x = lp.getX() + lp.getOriginX();
		cam.position.y = lp.getY() + lp.getOriginY();
		
		if (sSoldierList.size() < 10 && !isBossOnStage) {
			Boss = new Boss(0, 0, lp);
			gameSoundTrack.stop();
			gameBossSoundTrack.play();
			mainStage.addActor(Boss);
			isBossOnStage = true;

			// PlayerAmmo LABEL
			textBossLife = new Label("BOSS LIFE : " + Boss.getEnemyLife(), textStyle);
			textBossLife.setBounds(0, .2f, 1, 2);
			textBossLife.setFontScale(1f, 1f);
			textBossLife.setColor(Color.RED);

			mainStage.addActor(textBossLife);

		}

		// elimino dal mainStage gli elementi che non mi servono piu
		Iterator<BaseActor> iter = removeList.iterator();

		while (iter.hasNext()) {
			alreadyRemoved = false;
			BaseActor bs = iter.next();

			if (wallList.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}

			if (proiettili.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}
			if (sSoldierList.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}
			if (ammoList.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}

			if (lifePackList.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}

			if (proiettiliNemici.remove(bs) && !alreadyRemoved) {
				alreadyRemoved = true;
			}

			mainStage.getActors().removeValue(bs, true);

			iter.remove();

		}

		// GESTIONE COLLISIONE PROIETTILI - SOLDATI
		for (Proiettile p : proiettili) {
			for (SimpleSoldier sp : sSoldierList) {

				if (sp.overlaps(p, false)) {

					sp.setEnemyLife(sp.getEnemyLife() - p.getDanno());
					if (sp.getEnemyLife() < 1) {
						removeList.add(sp);
						lp.setScore(lp.getScore() + 50);

					}

					removeList.add(p);

				}
			}
		}

		// gestione collisioni MURO & SOLDATI
		for (WallActor wall : wallList) {
			
			lp.overlaps(wall, true);
			
			for (SimpleSoldier sp : sSoldierList) {
				
				sp.overlaps(wall, true);

				if (sp.overlaps(lp, true)) {

					lp.setPlayerLife(lp.getPlayerLife() - 1);

				}

			}
		}

		// collisioni personaggio LIFEPACK
		for (LifePack lpack : lifePackList) {

			if ((lp.overlaps(lpack, false))) {

				if (lp.getPlayerLife() < 100) {

					lp.heal(lpack);
					removeList.add(lpack);

				}

			}

		}

		// collisioni personaggio MUNIZIONI
		for (Munizioni ammo : ammoList) {
			if ((lp.overlaps(ammo, false) && lp.getAmmoAmount() < 50)) {
				lp.recharge(ammo);

				removeList.add(ammo);

			}

		}

		// GESTIONE COLLISIONI MURO PROIETTILI E MOTO PROIETTILE
		for (WallActor wall : wallList) {
			if (isProiettile) {
				for (Proiettile p : proiettili) {

					if (p.getTimer() > 1f) {// dopo un tot di tempo il
											// proiettile che non colpisce nulla
											// viene eliminato
						removeList.add(p);
						break;
					}

					if (p.overlaps(wall, false)) {

						wall.setActiveAnimation("explode");
						removeList.add(wall);
						removeList.add(p);

					}
				}
			}
		}

		if (isBossOnStage) {

			if (lp.overlaps(Boss, true)) {

				lp.setPlayerLife(lp.getPlayerLife() - 10);

			}

			for (WallActor wall : wallList) {
				if (Boss.overlaps(wall, false)) {

					wall.setActiveAnimation("explode");
					removeList.add(wall);
				}

			}
		}

		for (Proiettile p : proiettiliNemici) {

			for (WallActor wall : wallList) {
				if (p.overlaps(wall, false)) {
					removeList.add(p);
					break;
				}
			}

			if (p.getTimer() > 1f) {// dopo un tot di tempo il
									// proiettile che non colpisce nulla
									// viene eliminato
				removeList.add(p);
				break;
			}

			if (p.overlaps(lp, false)) {

				removeList.add(p);
				lp.setPlayerLife(lp.getPlayerLife() - p.getDanno());

			}

		}

		/// confino l'attore entro i limiti della mappa
		lp.setX(MathUtils.clamp(lp.getX(), 0, 1600 - lp.getWidth()));
		lp.setY(MathUtils.clamp(lp.getY(), 0, 1600 - lp.getHeight()));

		// setto la camera entro i limiti della mappa e dell'attore
		cam.position.x = MathUtils.clamp(cam.position.x, 800 / 2, 1600 - 800 / 2);
		cam.position.y = MathUtils.clamp(cam.position.y, 800 / 2, 1600 - 800 / 2);
		cam.update();

		// AGGIORNO LE POSIZIONI DEI LABEL CON LA CAM

		// TEXT
		textScore.setPosition(MathUtils.clamp(cam.position.x, 800 / 2, 1600 - 800 / 2) + 215,
				MathUtils.clamp(cam.position.y, 800 / 2, 1600 - 800 / 2) + 375);

		// LIFE
		textLife.setPosition(MathUtils.clamp(cam.position.x, 800 / 2, 1600 - 800 / 2) + 215,
				MathUtils.clamp(cam.position.y, 800 / 2, 1600 - 800 / 2) + 325);

		// AMMO
		textAmmo.setPosition(MathUtils.clamp(cam.position.x, 800 / 2, 1600 - 800 / 2) + 215,
				MathUtils.clamp(cam.position.y, 800 / 2, 1600 - 800 / 2) + 275);

		mainStage.draw();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {

	}

	// se ridimensiono la finestra, aggiorno width e height della camera
	// ortografica
	@Override
	public void resize(int width, int height) {

		cam.viewportHeight = height;
		cam.viewportWidth = width;
		cam.update();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		super.pause();
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	public void SaveGame() {

		SavedElements = new ArrayList<Identifier>();

		// riempio le liste di wall e ground
		for (WallActor wall : wallList) {
			SavedElements.add(new Identifier("Wall", wall.getX(), wall.getY()));
		}

		for (Munizioni mun : ammoList) {
			SavedElements.add(new Identifier("Ammo", mun.getX(), mun.getY()));

		}

		for (LifePack lp : lifePackList) {
			SavedElements.add(new Identifier("LifePack", lp.getX(), lp.getY()));

		}

		for (SimpleSoldier ss : sSoldierList) {

			SavedElements.add(new Identifier("SimpleSoldier", ss.getX(), ss.getY()));

		}

		// aggiungo il personaggio

		SavedElements.add(new Identifier("Player", lp.getX(), lp.getY()));

		JFileChooser chooser = new JFileChooser();
		// imposto l'estensione consigliata come .map
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SaveGame", "save");
		// setto il filtro
		chooser.setFileFilter(filter);
		// controllo
		int returnVal = chooser.showOpenDialog(null);
		// carico la stringa
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			try { // Catch errors in I/O if necessary.
					// Apro un file rinominandolo tramite la texArea e dandogli
					// l'estensione .map
				FileOutputStream saveFile = new FileOutputStream(chooser.getSelectedFile() + ".save");

				// Creo un ObjectOutputStream per inserire la mappa nel file di
				// salvataggio.
				ObjectOutputStream save = new ObjectOutputStream(saveFile);

				save.writeObject(SavedElements);
				save.writeObject(numMap);
				// Chiudo il file.
				save.close();
				SavedElements.clear();// elimino la lista
			} catch (Exception exc) {
				exc.printStackTrace(); // Se c'è stato un errore stampa info.
			}

		}

	}

	public void loadFromSaveinGame() {
		// creo un "chooser"
		JFileChooser chooser = new JFileChooser();
		// imposto l'estensione consigliata come .map
		FileNameExtensionFilter filter = new FileNameExtensionFilter("SaveGame from RougueKill", "save");
		// setto il filtro
		chooser.setFileFilter(filter);
		// controllo
		int returnVal = chooser.showOpenDialog(null);
		// carico la stringa
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			loader(chooser);

		}

		clearAllLists();
		refreshLists();
		switchMaptype(numMap);
		mainStage.addActor(ground);

		addAllToStage();
		mainStage.addActor(lp);
		mainStage.addActor(textScore);
		mainStage.addActor(textLife);
		mainStage.addActor(textAmmo);
	}

	@SuppressWarnings("unchecked")
	public void loader(JFileChooser chooser) {

		try {
			// Apro un file per leggere, scelto tramite il chooser.
			FileInputStream saveFile = new FileInputStream(chooser.getSelectedFile());

			// Creo un ObjectInputStream per reperire gli objects dal file di
			// salvataggio.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// leggo da save e memorizzo dentro saveMap
			SavedElements = (ArrayList<Identifier>) save.readObject();

			// leggo da il numtipo di mappa
			numMap = (Integer) save.readObject();

			// Chiudo il file.
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // Se c'è stato un errore stampa info.
		}

	}

	// cancello tutte le liste di GameScreen
	public void clearAllLists() {
		wallList.clear();
		proiettili.clear();
		sSoldierList.clear();
		ammoList.clear();
		lifePackList.clear();
		mainStage.clear();

	}

	public void refreshLists() {

		for (Identifier id : SavedElements) {
			switch (id.getTipo()) {
			case "Wall":
				wallList.add(new WallActor(id.getPosX(), id.getPosY()));
				break;
			case "SimpleSoldier":
				sSoldierList.add(new SimpleSoldier(id.getPosX(), id.getPosY()));
				break;
			case "Ammo":
				ammoList.add(new Munizioni(id.getPosX(), id.getPosY()));
				break;
			case "LifePack":
				lifePackList.add(new LifePack(id.getPosX(), id.getPosY()));
				break;

			case "Player":
				lp.setX(id.getPosX());
				lp.setY(id.getPosY());
				break;

			}
		}

	}

	public void addAllToStage() {
		for (WallActor wall : wallList) {
			mainStage.addActor(wall);

		}

		for (Munizioni mun : ammoList) {
			mainStage.addActor(mun);

		}

		for (LifePack lp : lifePackList) {
			mainStage.addActor(lp);

		}

		for (SimpleSoldier sp : sSoldierList) {

			sp.setpList(proiettiliNemici);
			sp.setStage(mainStage);
			mainStage.addActor(sp);

		}

	}

	public void switchMaptype(int num) {

		switch (num) {

		case 1:
			ground.setTexture(new Texture("BackGround GiocoErba.png"));
			break;
		case 2:
			ground.setTexture(new Texture("BackGroundGiocoTerra.png"));
			break;
		case 3:
			ground.setTexture(new Texture("BackGroundSabbia.png"));
			break;
		case 4:
			ground.setTexture(new Texture("BackGroundPietra.png"));
			break;
		case 5:
			ground.setTexture(new Texture("BackGroundGhiaccio.png"));
			break;

		}

	}

	public void loaderFromStringMap(JFileChooser chooser) {

		try {
			// Apro un file per leggere, scelto tramite il chooser.
			FileInputStream saveFile = new FileInputStream(chooser.getSelectedFile());

			// Creo un ObjectInputStream per reperire gli objects dal file di
			// salvataggio.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// leggo da save e memorizzo dentro saveMap
			savedMap = (String) save.readObject();

			// Chiudo il file.
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // Se c'è stato un errore stampa info.
		}

	}

	public void loadFromString() {
		// creo un "chooser"
		JFileChooser chooser = new JFileChooser();
		// imposto l'estensione consigliata come .map
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map from RougueKill", "map");
		// setto il filtro
		chooser.setFileFilter(filter);
		// controllo
		int returnVal = chooser.showOpenDialog(null);
		// carico la stringa
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			loaderFromStringMap(chooser);
			setMatrixfromLoader();
			PopulateFromEditorMap();

		}

	}

	// converto la stringa nella matrice di int
	public void setMatrixfromLoader() {
		int indice = 0;

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				int x = savedMap.charAt(indice) - '0';
				loadedMap[i][j] = x;
				indice++;

			}
		}

	}

	private void PopulateFromEditorMap() {

		for (int i = 0; i < GameConfig.MAPCREATORWIDTH; i++) {
			for (int j = 0; j < GameConfig.MAPCREATORHEIGHT; j++) {

				switch (loadedMap[i][j]) {
				case 1:
					wallList.add(new WallActor(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;

				case 2:
					lifePackList.add(new LifePack(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;

				case 3:
					ammoList.add(new Munizioni(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;

				case 4:
					sSoldierList.add(new SimpleSoldier(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;

				}

			}

		}
		addAllToStage();
		mainStage.addActor(lp);
		mainStage.addActor(textScore);
		mainStage.addActor(textLife);
		mainStage.addActor(textAmmo);
	}

	public ArrayList<WallActor> getWallList() {
		return wallList;
	}

	public void setWallList(ArrayList<WallActor> wallList) {
		this.wallList = wallList;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public ArrayList<Proiettile> getProiettili() {
		return proiettili;
	}

	public void setProiettili(ArrayList<Proiettile> proiettili) {
		this.proiettili = proiettili;
	}

	public ArrayList<LifePack> getLifePackList() {
		return lifePackList;
	}

	public void setLifePackList(ArrayList<LifePack> lifePackList) {
		this.lifePackList = lifePackList;
	}

	public ArrayList<Munizioni> getAmmoList() {
		return ammoList;
	}

	public void setAmmoList(ArrayList<Munizioni> ammoList) {
		this.ammoList = ammoList;
	}

	public ArrayList<SimpleSoldier> getsSoldierList() {
		return sSoldierList;
	}

	public void setsSoldierList(ArrayList<SimpleSoldier> sSoldierList) {
		this.sSoldierList = sSoldierList;
	}

	public boolean isPauseMenu() {
		return isPauseMenu;
	}

	public void setPauseMenu(boolean isPauseMenu) {
		this.isPauseMenu = isPauseMenu;
	}

	public MainMenuScreen getMs() {
		return ms;
	}

	public void setMs(MainMenuScreen ms) {
		this.ms = ms;
	}

	public ArrayList<Proiettile> getProiettiliNemici() {
		return proiettiliNemici;
	}

	public void setProiettiliNemici(ArrayList<Proiettile> proiettiliNemici) {
		this.proiettiliNemici = proiettiliNemici;
	}

	public void switchLabelColors() {

		if (lp.getPlayerLife() < 60 && lp.getPlayerLife() > 40)
			textLife.setColor(Color.ORANGE);
		else if (lp.getPlayerLife() >= 60)
			textLife.setColor(Color.OLIVE);
		else if (lp.getPlayerLife() <= 40 && lp.getPlayerLife() > 0)
			textLife.setColor(Color.RED);

		if (lp.getAmmoAmount() <= 15 && lp.getAmmoAmount() > 0)
			textAmmo.setColor(Color.ORANGE);
		else if (lp.getAmmoAmount() > 15)
			textAmmo.setColor(Color.DARK_GRAY);
		else if (lp.getAmmoAmount() == 0)
			textAmmo.setColor(Color.RED);

	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}