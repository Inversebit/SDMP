package org.inversebit.sdmp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MenuScreen implements Screen{

	private SDMPong sdmp;
	private OrthographicCamera camera;
	
	private float[][] menuColors = {{0.5f, 0.0f, 0.0f},{0.0f, 0.5f, 0.0f},{0.0f, 0.0f, 0.5f}};
	private int currentColor = 0;
	
	private float textScale = 1.0f;
	private boolean textGrow = true;
	
	private float accDelta = 0.0f;
	
	public MenuScreen(SDMPong pGame)
	{
		sdmp = pGame;
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SDMPong.WIDTH, SDMPong.HEIGHT);
	}
	
	@Override
	public void render(float delta) {
		if(Gdx.input.isKeyPressed(Keys.A))
		{
			sdmp.setScreen(new GameScreen(sdmp));
			dispose();
		};
		
		drawMenuBackground();
		
		drawMenuText();
		
		accDelta += delta;
		if(accDelta > 0.2)setNextBackgroundColour();
		
		setNextTextSize();		
	}

	private void setNextTextSize() {
		if(textGrow)textScale += 0.06f;
		else textScale -= 0.06f;
		if(textScale > 3.0f) textGrow = false;
		else if(textScale < 1.0f) textGrow = true;
	}

	private void setNextBackgroundColour() {
		currentColor++;
		if(currentColor > 2) currentColor = 0;
		accDelta = 0.0f;
	}

	private void drawMenuText() {
		camera.update();
		sdmp.batch.setProjectionMatrix(camera.combined);
		sdmp.batch.begin();
		sdmp.font.setScale(textScale);
		sdmp.font.draw(sdmp.batch, "Welcome to Super Duper Mega Pong", SDMPong.WIDTH/2 - 300, SDMPong.HEIGHT/2);
		sdmp.font.setScale(1.0f);
		sdmp.font.draw(sdmp.batch, "Press A to begin", SDMPong.WIDTH/2 - 50, 100);		
		sdmp.batch.end();
	}

	private void drawMenuBackground() {
		Gdx.gl20.glClearColor(menuColors[currentColor][0],
							  menuColors[currentColor][1],
							  menuColors[currentColor][2],
							  1.0f);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
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
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
