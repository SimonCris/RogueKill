package rogueTeam_structure;

import java.util.ArrayList;
import java.util.Random;

import rogueTeam_game.GameConfig;
import rougueTeam_actors.LifePack;
import rougueTeam_actors.Munizioni;
import rougueTeam_actors.Proiettile;
import rougueTeam_actors.SimpleSoldier;
import rougueTeam_actors.WallActor;

//Genera la mappa in modo automatico

public class MapCreator {

	Random rand = new Random();

	private int[][] Map;

	private ArrayList<WallActor> wallList;// lista dei muri
	private ArrayList<Proiettile> proiettili;// lista proiettili
	private ArrayList<LifePack> lifePackList;// lista lifepack
	private ArrayList<Munizioni> ammoList;// lista munizioni
	private ArrayList<SimpleSoldier> sSoldierList;// lista soldati
	public int MapWidth;// map width
	public int MapHeight;// map height
	public int PercentAreWalls;// percentuale di muri

	public MapCreator(int pc) {
		this.MapWidth = GameConfig.MAPCREATORWIDTH;
		this.MapHeight = GameConfig.MAPCREATORHEIGHT;

		Map = new int[MapHeight][MapWidth];// inizializzo la mappa
		this.PercentAreWalls = pc;// setto la percentuale di muri
		wallList = new ArrayList<WallActor>();// inizializzo la lista di muri
		proiettili = new ArrayList<Proiettile>(); // inizializzo la lista di muri
		lifePackList = new ArrayList<LifePack>(); // inizializzo la lista di muri
		ammoList = new ArrayList<Munizioni>();// inizializzo la lista di muri
		sSoldierList = new ArrayList<SimpleSoldier>();// inizializzo la lista di muri
		RandomFillMap();
		MakeCaverns(1);
		distribuiteLifepack();
		distribuiteAmmo();
		distribuiteEnemies();

		createActors();

	}

	public void MakeCaverns(int numStep) {// Tramite questo metodo che richiama placeWallLogic apro o chiudo determinate
											// porzioni di mappa
		for (int i = 0; i < numStep; i++) {
			for (int column = 0, row = 0; row <= MapHeight - 1; row++) {
				for (column = 0; column <= MapWidth - 1; column++) {
					Map[column][row] = PlaceWallLogic(column, row);
				}
			}
		}
	}

	public int PlaceWallLogic(int x, int y) {// decide per un determinato punto se settarlo a ground o wall
		int numWalls = GetAdjacentWalls(x, y, 1, 1);

		if (Map[x][y] == 1) {
			if (numWalls >= 4) {
				return 1;
			}
			if (numWalls < 2) {
				return 0;
			}

		} else {
			if (numWalls >= 5) {
				return 1;
			}
		}
		return 0;
	}

	public int GetAdjacentWalls(int x, int y, int scopeX, int scopeY) {// conta e restituisce il numero di muri
																		// nell'intorno al punto passato
		int startX = x - scopeX;
		int startY = y - scopeY;
		int endX = x + scopeX;
		int endY = y + scopeY;

		int iX = startX;
		int iY = startY;

		int wallCounter = 0;

		for (iY = startY; iY <= endY; iY++) {
			for (iX = startX; iX <= endX; iX++) {
				if (!(iX == x && iY == y)) {
					if (IsWall(iX, iY)) {
						wallCounter += 1;
					}
				}
			}
		}
		return wallCounter;
	}

	boolean IsWall(int x, int y) {// restituisco true se è un muro
		// Consider out-of-bound a wall
		if (IsOutOfBounds(x, y)) {
			return true;
		}

		if (Map[x][y] == 1) {
			return true;
		}

		if (Map[x][y] == 0) {
			return false;
		}
		return false;
	}

	boolean IsOutOfBounds(int x, int y) { // resituisco true se le coordinate sono fuori dai bordi
		if (x < 0 || y < 0) {
			return true;
		} else if (x > MapWidth - 1 || y > MapHeight - 1) {
			return true;
		}
		return false;
	}

	public void RandomFillMap() {

		int mapMiddle = 0; // Variabile Temporana
		for (int column = 0, row = 0; row < MapHeight; row++) {
			for (column = 0; column < MapWidth; column++) {
				// Se le coordinate fanno parte di un angolo della mappa creo un
				// muro
				if (column == 0) {
					Map[column][row] = 1;
				} else if (row == 0) {
					Map[column][row] = 1;
				} else if (column == MapWidth - 1) {
					Map[column][row] = 1;
				} else if (row == MapHeight - 1) {
					Map[column][row] = 1;
				}
				// altrimenti riempo la mappa tramite la percentuale random
				else {
					mapMiddle = (MapHeight / 2);

					if (row == mapMiddle) {
						Map[column][row] = 0;
					} else {
						Map[column][row] = RandomPercent(PercentAreWalls);
					}
				}
			}
		}
	}

	int RandomPercent(int percent) { // restiuisce 1 , ossia un muro , se percent(PercentOfWalls) è maggiore del
										// random tra 0 e 100. 0 altrimenti.
		if (percent >= rand.nextInt((101) + 1)) {
			return 1;
		}
		return 0;
	}

	public ArrayList<WallActor> getWallList() {
		return wallList;
	}

	public void setWallList(ArrayList<WallActor> wallList) {
		this.wallList = wallList;
	}

	public ArrayList<Proiettile> getProiettili() {
		return proiettili;
	}

	public void setProiettili(ArrayList<Proiettile> proiettili) {
		this.proiettili = proiettili;
	}

	// distribuisco i nemici nella mappa in base a num max e num min
	public void distribuiteEnemies() {

		int numEnemies = rand.nextInt(GameConfig.NUM_SIMPLESOLDIERS_MAX - GameConfig.NUM_SIMPLESOLDIERS_MIN)
				+ GameConfig.NUM_SIMPLESOLDIERS_MIN;

		int X, Y;
		for (int i = 0; i < numEnemies; i++) {

			X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random
															// int between 0 and
															// GAME WIDTH
			Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random
															// int between 0 and
															// GAME HEIGHT

			if (Map[X][Y] == 0) {/// se la cella è libera, ossia c'è del
									/// terreno, setto un nemico
				Map[X][Y] = 4;

			}
		}
	}

	// distribuisco medikit nella mappa in relazione a num max e min
	public void distribuiteLifepack() {

		int numLifepacks = rand.nextInt(GameConfig.NUM_MEDIKIT_MAX - GameConfig.NUM_MEDIKIT_MIN)
				+ GameConfig.NUM_MEDIKIT_MIN;

		int X, Y;
		for (int i = 0; i < numLifepacks; i++) {

			X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random
															// int between 0 and
															// GAME WIDTH
			Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random
															// int between 0 and
															// GAME HEIGHT

			if (Map[X][Y] == 0) {/// se la cella è libera, ossia c'è del
									/// terreno, setto un medikit
				Map[X][Y] = 2;

			}
		}
	}

	// distribuisco munizioni nella mappa in relazione a num max e num min
	public void distribuiteAmmo() {

		int numAmmo = rand.nextInt(GameConfig.NUM_AMMO_MAX - GameConfig.NUM_AMMO_MIN) + GameConfig.NUM_AMMO_MIN;

		int X, Y;
		for (int i = 0; i < numAmmo; i++) {

			X = rand.nextInt(GameConfig.MAPCREATORWIDTH); // generates a random
															// int between 0 and
															// GAME WIDTH
			Y = rand.nextInt(GameConfig.MAPCREATORHEIGHT); // generates a random
															// int between 0 and
															// GAME HEIGHT

			if (Map[X][Y] == 0) {/// se la cella è libera, ossia c'è del
									/// terreno, setto le munizioni
				Map[X][Y] = 3;

			}
		}
	}

	public void createActors() {//scorro la matrice e in base all'intero presente genero un nuovo "Attore" e lo inserisco nell' apposita lista.
		for (int i = 0; i < MapHeight; i++) {
			for (int j = 0; j < MapWidth; j++) {
				switch (Map[i][j]) {
				case 1:
					wallList.add(new WallActor(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;//1, Creo un WallActor
				case 2:
					lifePackList.add(new LifePack(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;//2, Creo un LifePack
				case 3:
					ammoList.add(new Munizioni(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;//3, Creo delle Munizioni
				case 4:
					sSoldierList.add(new SimpleSoldier(i * GameConfig.SCALE_CONSTANT, j * GameConfig.SCALE_CONSTANT));
					break;//4, aggiungo un simpleSoldier
				}
			}
		}

	}

	public int[][] getMap() {
		return Map;
	}

	public void setMap(int[][] map) {
		Map = map;
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

}