package rogueTeam_screen;

import com.badlogic.gdx.Screen;

public class ScreenManager {
	
	private static Screen currentScreen;
	
	public static void setScreen(Screen screen){
		
		if(currentScreen != null)
			currentScreen.dispose();
		
		currentScreen = screen;
		//currentScreen.create();
	}
	
	public static Screen getCurrentScreen(){
		
		return currentScreen;
		
	}

}