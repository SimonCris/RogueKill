package rogueTeam_editor;

import java.awt.Color;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.badlogic.gdx.maps.Map;

import rogueTeam_gdx.MyGame;
import rogueTeam_screen.EditorScreen;
import rogueTeam_screen.MainMenuScreen;

//Pannello che contiene le texture da inserire nella griglia
public class ToolsPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PreviewPanel pp;
	protected MainMenuScreen mm;
	protected EditorScreen es;
	protected MyGame game;

	// stringa su cui salvo e ricostruisco il mondo di gioco
	private String saveMap = "";

	private boolean alreadyGenerated = false;

	private Color paintColor = Color.yellow;
	public CustomBufferedImage paintImage = null;

	//bottoni del panel
	private JButton wall = new JButton();
	private JButton ground = new JButton();
	private JButton lifePack = new JButton();
	private JButton bullet = new JButton();
	private JButton simpleSoldier = new JButton();
	private JButton random = new JButton();
	private JButton save = new JButton();
	private JButton load = new JButton();
	private JButton exit = new JButton();

	//textaree per inserire il nome mappa e la percentuale di muri per il MapCreator
	private TextArea MapNameArea = new TextArea(3, 12);
	private TextArea PercentOfWalls = new TextArea(1, 7);

	
	//texture dei Bottoni
	private BufferedImageLoader loader = new BufferedImageLoader();
    private CustomBufferedImage wallImage = new CustomBufferedImage();
	private CustomBufferedImage groundImage = new CustomBufferedImage();
	private CustomBufferedImage lifePackImage = new CustomBufferedImage();
	private CustomBufferedImage bulletImage = new CustomBufferedImage();
	private CustomBufferedImage simpleSoldierImage = new CustomBufferedImage();
	private CustomBufferedImage RandomImage = new CustomBufferedImage();
	private CustomBufferedImage saveImage = new CustomBufferedImage();
	private CustomBufferedImage loadImage = new CustomBufferedImage();
	private CustomBufferedImage exitImage = new CustomBufferedImage();

	//
	public ToolsPanel(PreviewPanel pp, MainMenuScreen mm, MyGame g, EditorScreen es) {
		super();

		this.game = g;
		this.pp = pp;
		this.es = es;
		this.mm = mm;

		// Assegno texture e stringhe alle immagini dei bottoni

		// Wall Image
		wallImage.setImage(loader.loadImage("/Wall.png"));
		wallImage.setTipo("Wall");

		// Ground Image
		groundImage.setImage(loader.loadImage("/Ground.png"));
		groundImage.setTipo("Ground");

		// LifePack Image
		lifePackImage.setImage(loader.loadImage("/MedikitToolPanel.png"));
		lifePackImage.setTipo("LifePack");

		// Bullet Image
		bulletImage.setImage(loader.loadImage("/AmmoToolPanel.png"));
		bulletImage.setTipo("Bullet");

		// SimpleSoldier Image
		simpleSoldierImage.setImage(loader.loadImage("/SimpleSoldierToolPanel.png"));
		simpleSoldierImage.setTipo("SimpleSoldier");

		// Random Image
		RandomImage.setImage(loader.loadImage("/Random.png"));
		RandomImage.setTipo("Random");

		// Save Image
		saveImage.setImage(loader.loadImage("/Save.png"));
		saveImage.setTipo("Save");

		// Save Image
		loadImage.setImage(loader.loadImage("/load.png"));
		loadImage.setTipo("Load");

		// Exit Image
		exitImage.setImage(loader.loadImage("/Exit.png"));
		exitImage.setTipo("Exit");

		// pp.paintImage = wallImage;
		MapNameArea.setText("Inserire Nome Mappa");
		add(MapNameArea);

		PercentOfWalls.setText("% Muri");

		// Set Wall Button
		wall.addActionListener(this);
		wall.setIcon(new ImageIcon(wallImage.getImage().getScaledInstance(32, 32, 0)));
		wall.setToolTipText("Wall");
		add(wall);

		// Set Ground Button
		ground.addActionListener(this);
		ground.setIcon(new ImageIcon(groundImage.getImage().getScaledInstance(32, 32, 0)));
		ground.setToolTipText("Ground");
		add(ground);

		// Set LifePack Button
		lifePack.addActionListener(this);
		lifePack.setIcon(new ImageIcon(lifePackImage.getImage().getScaledInstance(32, 32, 0)));
		lifePack.setToolTipText("LifePack");
		add(lifePack);

		// Set Bullet Button
		bullet.addActionListener(this);
		bullet.setIcon(new ImageIcon(bulletImage.getImage().getScaledInstance(32, 32, 0)));
		bullet.setToolTipText("Bullet");
		add(bullet);

		// Set SimpleSoldier Button
		simpleSoldier.addActionListener(this);
		simpleSoldier.setIcon(new ImageIcon(simpleSoldierImage.getImage().getScaledInstance(32, 32, 0)));
		simpleSoldier.setToolTipText("Simple Soldier");
		add(simpleSoldier);

		// Set Random Button
		random.addActionListener(this);
		random.setIcon(new ImageIcon(RandomImage.getImage().getScaledInstance(32, 32, 0)));
		random.setToolTipText("Generate Random Map");
		add(random);
		add(PercentOfWalls);

		// Set Save Button
		save.addActionListener(this);
		save.setIcon(new ImageIcon(saveImage.getImage().getScaledInstance(32, 32, 0)));
		save.setToolTipText("Save");
		add(save);

		// Set Load Button
		load.addActionListener(this);
		load.setIcon(new ImageIcon(loadImage.getImage().getScaledInstance(32, 32, 0)));
		load.setToolTipText("Load");
		add(load);

		// Set Exit Button
		exit.addActionListener(this);
		exit.setIcon(new ImageIcon(exitImage.getImage().getScaledInstance(32, 32, 0)));
		exit.setToolTipText("Torna al Menu Principale");
		add(exit);

	}

	// In base al click sul bottone stampa sulla griglia la determinata texture
	// e/o salva/carica mappa
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == wall) {
			pp.paintImage = wallImage;

		} else if (e.getSource() == ground) {
			pp.paintImage = groundImage;
		} else if (e.getSource() == lifePack) {
			pp.paintImage = lifePackImage;
		} else if (e.getSource() == bullet) {
			pp.paintImage = bulletImage;
		} else if (e.getSource() == simpleSoldier) {
			pp.paintImage = simpleSoldierImage;
		} else if (e.getSource() == random) {
			if (alreadyGenerated) // controllo se ho già generato una mappa in precedenza e in questo caso
			pp.getPoints().removeAllElements();  //elimino la vecchia

			generateRandomMap();
			reposizionatePoints();
			alreadyGenerated = true;
		} else if (e.getSource() == save) {
			saveOnString();
		} else if (e.getSource() == load) {
			loadFromString();
		} else if (e.getSource() == exit) {

			es.exit();

		}

	}

	// salvo i numeri della matrice in ordine sulla Stringa
	public void saveOnString() {

		for (int x = 0; x < 50; x++) {
			for (int y = 0; y < 50; y++) {

				saveMap += pp.getMatrice()[x][y];

			}

		}

		try { // Catch errors in I/O if necessary.
				// Apro un file rinominandolo tramite la texArea e dandogli
				// l'estensione .map
			FileOutputStream saveFile = new FileOutputStream(MapNameArea.getText() + ".map");

			// Creo un ObjectOutputStream per inserire la mappa nel file di
			// salvataggio.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			save.writeObject(saveMap);
			// Chiudo il file.
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // Se c'è stato un errore stampa info.
		}

	}

	public void loadFromString() {
		// creo un "chooser"
		JFileChooser chooser = new JFileChooser();
		// imposto l'estensione consigliata come .map
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Map from RougueKill", "map");
		// setto il filtro
		chooser.setFileFilter(filter);
		// controllo
		int returnVal = chooser.showOpenDialog(null);
		// carico la stringa
		if (returnVal == JFileChooser.APPROVE_OPTION) {

			loader(chooser);
			setMatrixfromLoader();
			reposizionatePoints();

		}

	}

	public void loader(JFileChooser chooser) {

		try {
			// Apro un file per leggere, scelto tramite il chooser.
			FileInputStream saveFile = new FileInputStream(chooser.getSelectedFile());

			// Creo un ObjectInputStream per reperire gli objects dal file di
			// salvataggio.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// leggo da save e memorizzo dentro saveMap
			saveMap = (String) save.readObject();

			// Chiudo il file.
			save.close();
		} catch (Exception exc) {
			exc.printStackTrace(); // Se c'è stato un errore stampa info.
		}

	}

	// converto la stringa nella matrice di int
	public void setMatrixfromLoader() {
		int indice = 0;

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				int x = saveMap.charAt(indice) - '0';
				pp.getMatrice()[i][j] = x;
				indice++;

			}
		}

	}

	public void generateRandomMap() {
		MapCreatorEditor map = new MapCreatorEditor(Integer.parseInt(PercentOfWalls.getText()));

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				pp.getMatrice()[i][j] = map.getMap()[i][j];
			}
		}
	}

	public void reposizionatePoints() {

		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {

				switchImage(pp.getMatrice()[i][j]);

				Point p = new Point(j, i);// OCCHIO ALL'indice al contrario !!!!
				ColoredPoint cp = new ColoredPoint(p, paintColor, paintImage);
				pp.getPoints().add(cp);
			}
		}

		pp.repaint();
	}

	public void switchImage(int num) {
		switch (num) {
		case 0:
			paintImage = groundImage;
			break;
		case 1:
			paintImage = wallImage;
			break;
		case 2:
			paintImage = lifePackImage;
			break;

		case 3:
			paintImage = bulletImage;
			break;

		case 4:
			paintImage = simpleSoldierImage;
			break;

		case 5:
			// paintImage = bossImage;
			break;

	
		}
	}

	public String getSaveMap() {
		return saveMap;
	}

	public void setSaveMap(String saveMap) {
		this.saveMap = saveMap;
	}

}