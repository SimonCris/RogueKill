package rougueTeam_actors;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import rougueTeam_actions.FireAction;
import rougueTeam_actions.MoveDownAction;
import rougueTeam_actions.MoveLeftAction;
import rougueTeam_actions.MoveRightAction;
import rougueTeam_actions.MoveUpAction;

//classe che gestisce un nemico.

public class Nemico extends PhysicsActor {

	protected Integer enemyLife;
	protected boolean isAlive;
	private ArrayList<Proiettile> pList;
	private String bulletDirection = "UP";
	private Random dir;

	public Nemico(float x, float y) {

		super(x, y);
		dir = new Random();

		setMovements();

	}

	public void setMovements() {

		MoveUpAction MoveUP = new MoveUpAction(0, 100, this);
		MoveDownAction MoveDOWN = new MoveDownAction(0, -100, this);
		MoveRightAction MoveRIGHT = new MoveRightAction(100, 0, this);
		MoveLeftAction MoveLEFT = new MoveLeftAction(-100, 0, this);
		FireAction Fire = new FireAction(this, 0.1f);

		
		//switch
		switch (dir.nextInt(2)) {
		case 0:
			this.addAction(MoveRIGHT);
			this.addAction(MoveLEFT);
			this.addAction(Fire);
			break;

		case 1:
			this.addAction(MoveDOWN);
			this.addAction(MoveUP);
			this.addAction(Fire);

            break;
		
		}

	}

	public void fire() {

		Proiettile p = null;

		switch (bulletDirection) {
		case "UP":
			p = new Proiettile(this.getX(), this.getY() + 10);
			p.setAccelerationXY(0, 350);
			this.getStage().addActor(p);
			pList.add(p);

			break;
		case "DOWN":
			p = new Proiettile(this.getX(), this.getY() - 10);
			p.setAccelerationXY(0, -350);
			this.getStage().addActor(p);
			pList.add(p);
			break;
		case "RIGHT":
			p = new Proiettile(this.getX() + 10, this.getY());
			p.setAccelerationXY(350, 0);
			this.getStage().addActor(p);
			pList.add(p);
			break;
		case "LEFT":
			p = new Proiettile(this.getX() - 10, this.getY());
			p.setAccelerationXY(-350, 0);
			this.getStage().addActor(p);
			pList.add(p);
			break;
		}

	}

	// metodi propri di Nemico
	public Proiettile fireproiettile() {

		Proiettile p = null;

		switch (bulletDirection) {
		case "UP":
			p = new Proiettile(this.getX(), this.getY() + 10);
			p.setAccelerationXY(0, 350);
			pList.add(p);
			break;
		case "DOWN":
			p = new Proiettile(this.getX(), this.getY() - 10);
			p.setAccelerationXY(0, -350);
			pList.add(p);
			break;
		case "RIGHT":
			p = new Proiettile(this.getX() + 10, this.getY());
			p.setAccelerationXY(350, 0);
			pList.add(p);
			break;
		case "LEFT":
			p = new Proiettile(this.getX() - 10, this.getY());
			p.setAccelerationXY(-350, 0);
			pList.add(p);
			break;
		}

		return p;
	}

	@Override
	public void act(float dt) {

		super.act(dt);

		if (getActions().size == 0) {// se il nemico ha finito le azioni le ricarica
			setMovements();
		}

	}

	protected void move(String dir) {

		switch (dir) {
		case "UP":
			setBulletDirection("UP");
			break;

		case "DOWN":
			setBulletDirection("DOWN");

			break;
		case "RIGHT":
			setBulletDirection("RIGHT");
			break;

		case "LEFT":
			setBulletDirection("LEFT");
			break;
		}

	}

	public Integer getEnemyLife() {
		return enemyLife;
	}

	public void setEnemyLife(Integer enemyLife) {
		this.enemyLife = enemyLife;
	}

	public boolean isAlive() {

		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public ArrayList<Proiettile> getpList() {
		return pList;
	}

	public void setpList(ArrayList<Proiettile> pList) {
		this.pList = pList;

	}

	public void setStage(Stage g) {
		super.setStage(g);
	}

	public String getBulletDirection() {
		return bulletDirection;
	}

	public void setBulletDirection(String bulletDirection) {
		this.bulletDirection = bulletDirection;
	}

}
