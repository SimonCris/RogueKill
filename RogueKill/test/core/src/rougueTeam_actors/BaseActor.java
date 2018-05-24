package rougueTeam_actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.Actor;

import rogueTeam_game.GameConfig;

public class BaseActor extends Actor //
{
	public TextureRegion region; // BaseActor texture
	public Polygon boundingPolygon;// BOX
    public boolean Collide;
	
	public BaseActor(float x, float y) {
		super();
		region = new TextureRegion();
		boundingPolygon = null;
        setPosition(x, y);
        this.setWidth(GameConfig.SCALE_CONSTANT);
        this.setHeight(GameConfig.SCALE_CONSTANT);
	    Collide=true;
	}

	public void destroy() {
		remove(); // self from Stage

	}

	public void setTexture(Texture t) { // set di texture
		int w = t.getWidth();
		int h = t.getHeight();
		setWidth(w);
		setHeight(h);
		region.setRegion(t);
	}

	public void setPosition(float _x, float _y) {
		if (this.getX() != _x || this.getY() !=_y) {
			this.setX(_x);;
			this.setY(_y);
			positionChanged();
		}
	}

	// imposto il boundingPolygon secondo le preferenze
	// Posso scegliere tra un Rettangolo o un ellisse

	public void setRectangleBoundary() {
		float w = getWidth();
		float h = getHeight();
		float[] vertices = { 0, 0, w, 0, w, h, 0, h };
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin(this.getX(),this.getY());
	}

	public void setEllipseBoundary() {
		int n = 12; // numero di vertici
		float w = getWidth();
		float h = getHeight();
		float[] vertices = new float[2 * n];
		for (int i = 0; i < n; i++) {
			float t = i * 6.28f / n;
			// x-coordinate
			vertices[2 * i] = w / 2 * MathUtils.cos(t) + w / 2;
			// y-coordinate
			vertices[2 * i + 1] = h / 2 * MathUtils.sin(t) + h / 2;
		}
		boundingPolygon = new Polygon(vertices);
		boundingPolygon.setOrigin(this.getX(), this.getY());
	}

	public Polygon getBoundingPolygon() {
		boundingPolygon.setPosition(getX(), getY());
		boundingPolygon.setRotation(getRotation());
		return boundingPolygon;
	}

	/**
	 * Determina se due oggetti di tipo Baseactor si sovrappongono Se (resolve
	 * == true), quindi quando c'è sovrapposizione, sposta questo BaseActor
	 ** Lungo il vettore di traslazione minimo fino a quando non c'è
	 * sovrapposizione
	 */

	public boolean overlaps(BaseActor other, boolean resolve) {
	    if(!other.Collide)
	    	return false;
	
		Polygon poly1 = this.getBoundingPolygon();//crea il bounding box del primo attore
		Polygon poly2 = other.getBoundingPolygon();//crea il bounding box del secondo attore

		if (!poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()))//Se non c'è collisione fra gli attori ritorna false
			
			return false;

		MinimumTranslationVector mtv = new MinimumTranslationVector();//Creo il vettore di traslazione minimo
		
		boolean polyOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);//restituisce true se i due poligoni collidono e overlapConvexPolygons sposta 
																				   //poly2 nel punto piu vicino del vettore delle traslazioni in cui non collide
																				   //con poly1 
		
		
		if (polyOverlap && resolve) {//se collidono e resolve è == true, sposta il secondo attore nel punto piu vicino del vettore delle traslazioni in cui non collide con il primo
			this.moveBy(mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth);
		}
		float significant = 0.5f;
		return (polyOverlap && (mtv.depth > significant));
	}

	public void act(float dt) {// aggiorna l'attore in base al tempo
		super.act(dt);
	}

	public void draw(Batch batch, float parentAlpha) {// disegno il BaseActor
		Color c = getColor();
		batch.setColor(c.r, c.g, c.b, c.a);
		if (isVisible())
			batch.draw(region,this.getX(), this.getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(),
					getScaleY(), getRotation());

		super.draw(batch, parentAlpha);
	}

	
	
}