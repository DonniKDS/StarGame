package ru.geekbrains.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ru.geekbrains.StarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.x = 200;
		config.y = 50;
		config.height = 640;
		config.width = 360;
		new LwjglApplication(new StarGame(), config);
	}
}
