package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import rogueTeam_game.GameConfig;
import rogueTeam_gdx.MyGame;
import rogueTeam_screen.MyTextInputListener;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "RougueKill Game";
		config.height = GameConfig.GAME_HEIGHT;
		config.width = GameConfig.GAME_WIDTH;
		config.foregroundFPS = 60;
		//config.fullscreen = true;
		config.useGL30 = true;
		config.resizable = true;
		
		
		new LwjglApplication(new MyGame(), config);
		
		
	}
}
