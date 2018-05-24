package rogueTeam_editor;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class ColoredPoint {
	
	private Color color;
	private Point point;
	private CustomBufferedImage image;
	private int type;

	public ColoredPoint(Point point, Color color, CustomBufferedImage image){
		this.color = color;
		this.point = point;
		this.image = image;
		
		switchType();
		
	}
	
	public void switchType(){
		
		switch(image.getTipo()){
		
		case "Wall":
			type = 1;
			break;
			
		case "Ground":
			type = 0;
			break;
			
		case "LifePack":
			type = 2;
			break;
			
		case "Bullet":
			type = 3;
			break;
		
		case "SimpleSoldier":
			type = 4;
			break;
			
		case "Boss":
			type=5;
			break;
			
		case "Player":
			type=6;
			break;
		
		}
		
	}
	
	public CustomBufferedImage getImage() {
		return image;
	}
	
	public void setImage(CustomBufferedImage image) {
		this.image = image;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
