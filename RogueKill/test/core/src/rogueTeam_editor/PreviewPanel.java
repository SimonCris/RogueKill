package rogueTeam_editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Vector;

import javax.swing.JPanel;

import com.badlogic.gdx.graphics.OrthographicCamera;

import rogueTeam_game.GameConfig;


//Pannello che mostra la griglia dell'editor
//serializzata
public class PreviewPanel extends JPanel implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	
	private int width = GameConfig.GAME_WIDTH;
	private Vector<ColoredPoint> points = new Vector<ColoredPoint>();//vettore di punti della griglia di visualizzazione
	private int [][] matrice = new int[50][50];//inizializzo la matrice di interi che descrive la mappa
	private Color paintColor = Color.yellow;
	public CustomBufferedImage paintImage = null;

	protected OrthographicCamera camera = new OrthographicCamera();
	
	public PreviewPanel(){
		super();
		
		//abilita il click del mouse su questo pannello
		addMouseListener(this);
		addMouseMotionListener(this);
		
		for(int x = 0; x <50; x++)
			for(int y = 0; y <50; y++)
				matrice[x][y] = 0;
		
	}

	//disegna la grafica del pannello
	public void paintComponent(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0, 0, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
		
		//genera la griglia
		for(int i = 0; i < width; i+=32){
			g.setColor(Color.white);
			g.fillRect(i, 0, 1, GameConfig.GAME_WIDTH);
			g.fillRect(0, i, GameConfig.GAME_WIDTH, 1);
		}
		
		//stampa per ogni cella delal griglia il suo contenuto
		for(int i = 0; i < points.size();i++){
			
			ColoredPoint tmp = points.get(i);
			g.drawImage(tmp.getImage().getImage(), tmp.getPoint().x*32, tmp.getPoint().y*32, 32, 32, null);
			
		}
	}

	//se il bottone sx del mouse è sempre premuto 
	public void mouseDragged(MouseEvent e) {
		mousePressed(e);
		
	}
	
	//se il bottone sx del mouse è premuto una sola svolta
	public void mousePressed(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		Point p = clickToGrid(x,y);//traduce le coordinate del mouse in coordinate di interi per la matrice
		ColoredPoint cp = new ColoredPoint(p,paintColor, paintImage);//genera il nuovo punto
		removeDuplicate(p);//rimuove eventuali punti duplicati
		points.add(cp);//lo aggiunge alla lista
		
		matrice[cp.getPoint().y][cp.getPoint().x] = cp.getType();//aggiunge il tipo di punto alla matrice di interi per poi decodificare
		repaint();//ristampa il PreviewPanel

	}
	
	//Converte le coordinate del mouse nelle coordinate della griglia
	private Point clickToGrid(int x, int y){
		
		int px = x ;
		int py = y ;
		
		px = px / 32;
		py = py / 32;
		
		return new Point(px,py);
	}
	
	//Rimuove i punti duplicati
	private void removeDuplicate(Point p){
		for(int i = 0; i < points.size();i++){
			ColoredPoint tmp = points.get(i);
			if (tmp.getPoint().equals(p)){
				points.remove(i);
				return;
			}
		}
	}
	
	public void setPaintColor(Color color){
		this.paintColor = color;
	}
	
	public void mouseMoved(MouseEvent e) {
	}
	public void mouseClicked(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {		
	}

	public void mouseEntered(MouseEvent e) {		
	}

	public void mouseExited(MouseEvent e) {
	}

	public Vector<ColoredPoint> getPoints() {
		return points;
	}

	public void setPoints(Vector<ColoredPoint> points) {
		this.points = points;
	}

	public int[][] getMatrice() {
		return matrice;
	}

	public void setMatrice(int[][] matrice) {
		this.matrice = matrice;
	}


}
