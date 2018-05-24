package rougueTeam_actors;

import com.badlogic.gdx.graphics.Texture;

public class Proiettile extends PhysicsActor {

	// elementi propri del proiettile
	private int accelerazione;
	private int danno;
	private float timer; // tempo dopo il quale il proiettile scompare dallo
							// stage e viene distrutto

	public Proiettile(float x, float y) {
		super(x, y);

		Texture bullTex = new Texture("bullet.png");
		this.storeAnimation("default", bullTex);
		this.setSize(15, 15);
		this.setEllipseBoundary();
		this.danno = 20;

		this.timer = 0.0f;

	}

	@Override
	public void act(float dt) {

		super.act(dt);
		timer += dt;

	}

	// get e set danno
	public int getDanno() {
		return danno;
	}

	public void setDanno(int danno) {
		this.danno = danno;
	}

	// get e set accelerazione
	public int getAccelerazione() {
		return accelerazione;
	}

	public void setAccelerazione(int accelerazione) {
		this.accelerazione = accelerazione;
	}

	// get e set timer
	public float getTimer() {
		return timer;
	}

	public void setTimer(float timer) {
		this.timer = timer;
	}

}