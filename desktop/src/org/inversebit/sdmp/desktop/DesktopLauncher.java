package org.inversbit.superdupermegapong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.inversbit.superdupermegapong.SDMPong;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Super duper mega Pong";
		config.height = SDMPong.HEIGHT;
		config.width = SDMPong.WIDTH;
		new LwjglApplication(new SDMPong(), config);
	}
}
