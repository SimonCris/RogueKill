package rougueTeam_actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Polygon;

public class WallActor extends AnimatedActor {

	
	public WallActor(float x,float y){
		super(x,y);
		this.storeAnimation("default", new Texture("Wall.png"));
		this.storeAnimation("explode", new Texture("WallExplode.png"));
		this.setRectangleBoundary();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void setTexture(Texture t) {
		// TODO Auto-generated method stub
		super.setTexture(t);
	}

	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
	}

	@Override
	public void setRectangleBoundary() {
		// TODO Auto-generated method stub
		super.setRectangleBoundary();
	}

	@Override
	public void setEllipseBoundary() {
		// TODO Auto-generated method stub
		super.setEllipseBoundary();
	}

	@Override
	public Polygon getBoundingPolygon() {
		// TODO Auto-generated method stub
		return super.getBoundingPolygon();
	}

	@Override
	public boolean overlaps(BaseActor other, boolean resolve) {
		// TODO Auto-generated method stub
		return super.overlaps(other, resolve);
	}

	@Override
	public void act(float dt) {
		// TODO Auto-generated method stub
		super.act(dt);
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		super.draw(batch, parentAlpha);
	}

}
