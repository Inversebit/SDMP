/**
Copyright (C) 2014 Inversebit

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/    
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
