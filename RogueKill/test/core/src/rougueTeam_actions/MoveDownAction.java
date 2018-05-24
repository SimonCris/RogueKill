package rougueTeam_actions;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

import rogueTeam_game.GameConfig;
import rougueTeam_actors.Nemico;

public class MoveDownAction extends MoveByAction {
	
	private Nemico n;
	
	public MoveDownAction(float x, float y, Nemico _n){//passo all'azione x e y per il movimento e il nemico che la compie
		
		super();
		setAmount(x, y);
		setDuration(GameConfig.MOVEDOWNDURATION);
		this.n = _n;
		
	}

	@Override
	public boolean act(float delta) {
		
		n.setBulletDirection("DOWN");
		n.pauseAnimation();
		n.setActiveAnimation("down");
		n.startAnimation();
		return super.act(delta);
		
	}
	
	@Override
	protected void updateRelative(float percentDelta) {
		super.updateRelative(percentDelta);
	}

	@Override
	public void setAmount(float x, float y) {
		super.setAmount(x, y);
	}

	@Override
	public float getAmountX() {
		return super.getAmountX();
	}

	@Override
	public void setAmountX(float x) {
		super.setAmountX(x);
	}

	@Override
	public float getAmountY() {
		return super.getAmountY();
	}

	@Override
	public void setAmountY(float y) {
		super.setAmountY(y);
	}

	public Nemico getN() {
		return n;
	}

	public void setN(Nemico n) {
		this.n = n;
	}



}
