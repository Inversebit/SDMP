package org.inversebit.sdmp;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class SDMPong extends Game {
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 350;
	
	public static final int PADDLE_HEIGHT = 60;
	public static final int PADDLE_WIDTH = 15;
	public static final int PADDLE_MARGIN = 10;
	
	public static final int BALL_RADIUS = 20;
	public static final int PADDLE_MOVEMENT_QUANTITY = 230;
	public static final int BALL_MOVEMENT_QUANTITY = 5;
	
	public SpriteBatch batch;
	public BitmapFont font;
	public ShapeRenderer shapeRenderer;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		shapeRenderer = new ShapeRenderer();
		this.setScreen(new MenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
		shapeRenderer.dispose();
	}
	
}
