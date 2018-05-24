package rougueTeam_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;

import rogueTeam_game.GameUtils;

//classe che gestisce il personaggio. Estende la classe astratta AbstractIGameObjectNonStatic

public class Personaggio extends PhysicsActor {

	private Integer playerLife = 100;
	private boolean isAlive;
	private String bulletDirection;
	private int AmmoAmount = 50;
	private Integer Score = 0;

	public Personaggio(float x, float y) {
		super(x, y);
		Texture playerTex = new Texture("general-single.png");
		this.storeAnimation("default", playerTex);

		float t = 0.15f;
		this.storeAnimation("down",
				GameUtils.parseSpriteSheet("general-48.png", 3, 4, new int[] { 0, 1, 2 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("left",
				GameUtils.parseSpriteSheet("general-48.png", 3, 4, new int[] { 3, 4, 5 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("right",
				GameUtils.parseSpriteSheet("general-48.png", 3, 4, new int[] { 6, 7, 8 }, t, PlayMode.LOOP_PINGPONG));

		this.storeAnimation("up",
				GameUtils.parseSpriteSheet("general-48.png", 3, 4, new int[] { 9, 10, 11 }, t, PlayMode.LOOP_PINGPONG));

		this.setSize(30, 30);

		this.setEllipseBoundary();

		this.bulletDirection = "DOWN";

	}

	// metodi propri di Personaggio

	// aggiunge una nuova arma al personaggio

	public String getBulletDirection() {
		return bulletDirection;
	}

	public void setBulletDirection(String bulletDirection) {
		this.bulletDirection = bulletDirection;
	}

	// set e get la vita del Personaggio

	public Integer getPlayerLife() {
		return playerLife;
	}

	public void setPlayerLife(Integer playerLife) {
		this.playerLife = playerLife;
	}

	public Proiettile fire() {
		Proiettile p = null;

		switch (bulletDirection) {
		case "UP":
			p = new Proiettile(this.getX(), this.getY() + 20);
			p.setAccelerationXY(0, 350);
			break;
		case "DOWN":
			p = new Proiettile(this.getX(), this.getY() - 20);
			p.setAccelerationXY(0, -350);
			break;
		case "RIGHT":
			p = new Proiettile(this.getX() + 20, this.getY());
			p.setAccelerationXY(350, 0);
			break;
		case "LEFT":
			p = new Proiettile(this.getX() - 20, this.getY());
			p.setAccelerationXY(-350, 0);
			break;
		}

		return p;
	}

	// curo il personaggio
	public void heal(LifePack lp) {

		this.playerLife += lp.getPackAmountLife();

		if (this.playerLife > 100)
			this.playerLife = 100;

	};

	// raccolto Munizioni

	public void recharge(Munizioni m) {
		this.AmmoAmount += m.getAmmoAmount();

		if (this.AmmoAmount > 50)
			this.AmmoAmount = 50;

	}

	// riceve in input la direzione dello spostamento
	public void moveObject(String direction) {

	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public int getAmmoAmount() {
		return AmmoAmount;
	}

	public void setAmmoAmount(int ammoAmount) {
		AmmoAmount = ammoAmount;
	}

	public Integer getScore() {
		return Score;
	}

	public void setScore(Integer score) {
		Score = score;
	}
	
	public void enemyhit() {
		Score+=50;
	}
	
	

}