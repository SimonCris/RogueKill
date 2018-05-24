package rogueTeam_screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import rogueTeam_game.GameConfig;
import rogueTeam_gdx.MyGame;

//Classe astratta che crea uno schermo base
public abstract class BaseScreen implements Screen, InputProcessor{
	
	protected MyGame game;
	
	protected Stage mainStage; //stage da aggiornare quando il gioco è in pausa

	protected static int WIDTH = GameConfig.MAPCREATORWIDTH*GameConfig.SCALE_CONSTANT;
	protected static int HEIGHT = GameConfig.MAPCREATORHEIGHT*GameConfig.SCALE_CONSTANT;
	
	protected boolean paused; //gestisce lo stato di pausa
	
	public BaseScreen(MyGame g){
		
		game = g;
		this.paused = false;
		
		//inizializzo i due stage con una nuova viewport di coordinate WIDTH e HEIGHT
		mainStage = new Stage();

		
		//gestore eventi
		InputMultiplexer im = new InputMultiplexer(this, mainStage);//creo il gestore di eventi a cui passo inputProcessor(this)
																			//e i due stage
		
		Gdx.input.setInputProcessor(im);//passo il gestore degli eventi all'input
		
		create();//richiama create di esempioScreen
		
	}
	

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
	
	//Ogni schermo verrà creato in base al suo contenuto
	public abstract void create();
	
	//Ogni schermo si aggiornerà in modo diverso
	public abstract void update();

	//FUNZIONI PER SCREEN
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	//Prima di stampare devo aggiornare tutto
	@Override
	public void render(float delta) {
		
		//update
		
		//se il gioco non è in pausa aggiorno lo stage principale
		if(! isPaused()){
			
			mainStage.act();
			update();//aggiorno lo schermo
		}
		
		//render
		
		Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mainStage.draw();

	}

	//Aggiorno le viewPort quando la finestra viene ridimensionata
	@Override
	public void resize(int width, int height) {
		
		mainStage.getViewport().update(width, height, true);//aggiorno le viewPort con le nuove coordinate e la camera al centro
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	//FUNZIONI PER INPUTPROCESSOR
	
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	
}