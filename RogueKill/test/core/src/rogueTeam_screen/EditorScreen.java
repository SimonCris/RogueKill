package rogueTeam_screen;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import rogueTeam_editor.PreviewPanel;
import rogueTeam_editor.ToolsPanel;
import rogueTeam_game.GameConfig;
import rogueTeam_gdx.MyGame;

public class EditorScreen extends BaseScreen{

	public MyGame game;
	public MainMenuScreen mm;
	private boolean ex = false;
	
	JFrame frame;
	PreviewPanel panel;
	ToolsPanel tools;
	Container contentPane;
	JScrollPane scrollPanel;

	
	public EditorScreen(MyGame g, MainMenuScreen mm) {
		super(g);
		
		this.mm = mm;
		this.setGame(g);
	}
	
	@Override
	public void create() {
		
		//inizializzo jframe e pannelli griglia e bottoni
		frame = new JFrame("Map Editor");
		panel = new PreviewPanel();
		tools = new ToolsPanel(panel, mm, game, this);
		contentPane = frame.getContentPane();
		

		//set dimensioni griglia e bottoni
		panel.setPreferredSize(new Dimension(GameConfig.GAME_WIDTH,GameConfig.GAME_HEIGHT));
		tools.setPreferredSize(new Dimension(300,300));
		
		scrollPanel = new JScrollPane(panel);
		scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPanel.setBounds(0, 0, GameConfig.GAME_WIDTH, GameConfig.GAME_HEIGHT);
		
		//set posizione pannello dei bottoni e pannello griglia
		contentPane.add(scrollPanel);
		contentPane.add(tools, BorderLayout.EAST);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		//Schermo intero
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setUndecorated(true);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.addMouseWheelListener(new MouseWheelListener() {

            @Override
            public void mouseWheelMoved(MouseWheelEvent event) {
                if (event.isShiftDown()) {
                    System.err.println("Horizontal " + event.getWheelRotation());
                } else {
                    System.err.println("Vertical " + event.getWheelRotation());                    
                }
            }
        });
		
		
		
		frame.setVisible(true);
		
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		
		//se il pannello dell'editor in toolsPanel viene chiuso
		if(ex){
			frame.dispose();
		
		game.setScreen(new MainMenuScreen(game));
		}
	}

	public void exit() {

		ex = true;
		
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	public MyGame getGame() {
		return game;
	}

	public void setGame(MyGame game) {
		this.game = game;
	}



}
