package rogueTeam_screen;

import com.badlogic.gdx.Input.TextInputListener;

public class MyTextInputListener implements TextInputListener {
	private MainMenuScreen ms;   
	
	public MyTextInputListener(MainMenuScreen _ms) {
		this.ms=_ms;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	   public void input (String text) {
		   ms.setPlayerName(text);
	   }

	   @Override
	   public void canceled () {
	   }
	}