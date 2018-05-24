package rougueTeam_actors;

import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import rogueTeam_game.GameUtils;

public class Boss extends AnimatedActor {

	private Integer enemyLife;
	private MoveByAction MoveToPlayer;
	private Personaggio player;
	private float AmountX;
	private float AmountY;

	public Boss(float x, float y, Personaggio p) {

		super(x, y);
		this.setEnemyLife(2000);

		this.storeAnimation("default", GameUtils.parseSpriteSheet("AnimationBoss.png", 4,1,
				new int[] {0,1,2,3}, 0.15f, PlayMode.LOOP));
		this.setSize(200, 200);
		this.setEllipseBoundary();
		this.player = p;
		addAction();

	}

	@Override
	public void act(float dt) {
		super.act(dt);
		addAction();

	}

	public void addAction() {

		MoveToPlayer = new MoveByAction();
		

		if (player.getX() > this.getX())
			AmountX = 0.55f;

		if (player.getX() < this.getX())
			AmountX = -0.55f;

		if (player.getY() > this.getY())
			AmountY = 0.55f;

		if (player.getY() < this.getY())
			AmountY = -0.55f;

		MoveToPlayer.setAmount(AmountX, AmountY);

		this.addAction(MoveToPlayer);

	}

	public Integer getEnemyLife() {
		return enemyLife;
	}

	public void setEnemyLife(Integer enemyLife) {
		this.enemyLife = enemyLife;
	}

}