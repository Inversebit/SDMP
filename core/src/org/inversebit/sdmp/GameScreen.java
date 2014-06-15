package org.inversebit.sdmp;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
	
	private SDMPong sdmp;
	private GameStateEnum GAME_STATE;
	
	private OrthographicCamera camera;
	
	private Sound bouncingSound;
	
	private int P1Points;
	private int P2Points;
	
	private Rectangle P1Paddle;
	private Rectangle P2Paddle;
	private Rectangle ball;
	
	private Vector2 ballDirection;
	
	private float countDownTimer = 4.0f;
	private float goalTimer = 1.0f;
	private float goalScale = 1.0f;
	
	private float delta;
	
	public GameScreen(SDMPong pSdmp) {
		sdmp = pSdmp;
				
		initCamera();
		
		initSounds();
		
		initPoints();
		
		initPaddles();
		initBall();
		
		GAME_STATE = GameStateEnum.COUNTDOWN;
	}

	private void initCamera() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SDMPong.WIDTH, SDMPong.HEIGHT);
		sdmp.font.setScale(3.0f);
	}

	private void initSounds() {
		bouncingSound = Gdx.audio.newSound(Gdx.files.internal("bounce.ogg"));
	}

	private void initPoints() {
		P1Points = 0;
		P2Points = 0;
	}

	private void initPaddles() {
		P1Paddle = new Rectangle(SDMPong.PADDLE_MARGIN, 
								 SDMPong.HEIGHT/2-SDMPong.PADDLE_HEIGHT/2,
								 SDMPong.PADDLE_WIDTH, 
								 SDMPong.PADDLE_HEIGHT);
		
		P2Paddle = new Rectangle(SDMPong.WIDTH-SDMPong.PADDLE_MARGIN-SDMPong.PADDLE_WIDTH, 
								 SDMPong.HEIGHT/2-SDMPong.PADDLE_HEIGHT/2,
								 SDMPong.PADDLE_WIDTH, 
								 SDMPong.PADDLE_HEIGHT);
	}

	private void initBall() {
		ball = new Rectangle(SDMPong.WIDTH/2-SDMPong.BALL_RADIUS/2,
							 SDMPong.HEIGHT/2-SDMPong.BALL_RADIUS/2,
							 SDMPong.BALL_RADIUS,
							 SDMPong.BALL_RADIUS);	
		setBall();
	}

	@Override
	public void render(float delta) 
	{
		this.delta = delta;
		openBatch();
		
		switch(GAME_STATE)
		{
			case RUNNING:
				drawPaddles();
				drawBall();		
				drawScores();
				
				readInputAndMovePaddles();
				
				moveBall();
				
				checkPaddleBallCollisions();
				checkBoundaries();
				checkIfGoal();				
				break;
				
			case GOAL:
				announceGoal();
				break;
				
			case COUNTDOWN:
				countdown();
				break;
				
			case INIT_GAME:
				setPaddles();
				setBall();
				GAME_STATE = GameStateEnum.RUNNING;
				break;
		}
		
		closeBatch();
	}

	private void drawScores() {
		sdmp.font.setColor(Color.RED);
		sdmp.font.draw(sdmp.batch, "" + P1Points, SDMPong.PADDLE_MARGIN, SDMPong.HEIGHT);
		sdmp.font.draw(sdmp.batch, "" + P2Points, SDMPong.WIDTH - SDMPong.PADDLE_MARGIN, SDMPong.HEIGHT);
		sdmp.font.setColor(Color.WHITE);
	}

	private void checkPaddleBallCollisions() {
		if(ball.overlaps(P1Paddle))
		{
			ballDirection = new Vector2(-ballDirection.x, MathUtils.random());
			ballDirection.nor();
			correctBallDirection();
			bouncingSound.play();
		}
		else
		{
			if(ball.overlaps(P2Paddle))
			{
				ballDirection = new Vector2(-ballDirection.x, MathUtils.random());
				ballDirection.nor();
				correctBallDirection();
				bouncingSound.play();
			}
		}	
	}

	private void checkBoundaries() {
		if(P1Paddle.y + P1Paddle.height > SDMPong.HEIGHT)
		{
			P1Paddle.y = SDMPong.HEIGHT - P1Paddle.height;
		}else
		{
			if(P1Paddle.y < 0)
			{
				P1Paddle.y = 0;
			}
		}
		
		if(P2Paddle.y + P2Paddle.height > SDMPong.HEIGHT)
		{
			P2Paddle.y = SDMPong.HEIGHT - P2Paddle.height;
		}else
		{
			if(P2Paddle.y < 0)
			{
				P2Paddle.y = 0;
			}
		}
		
		if(ball.y + ball.height > SDMPong.HEIGHT)
		{
			ballDirection.y = -ballDirection.y;
		}else
		{
			if(ball.y < 0)
			{
				ballDirection.y = -ballDirection.y;
			}
		}
	}

	private void openBatch() {
		camera.update();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		sdmp.batch.setProjectionMatrix(camera.combined);
		sdmp.shapeRenderer.setProjectionMatrix(camera.combined);
		sdmp.batch.begin();
		sdmp.shapeRenderer.begin(ShapeType.Filled);
	}

	private void closeBatch() {
		sdmp.batch.end();	
		sdmp.shapeRenderer.end();
	}

	private void setBall() {
		ball.setPosition(SDMPong.WIDTH/2-SDMPong.BALL_RADIUS/2, SDMPong.HEIGHT/2-SDMPong.BALL_RADIUS/2);
		ballDirection = new Vector2(MathUtils.random(), MathUtils.random());
		ballDirection.nor();
		correctBallDirection();
	}

	private void correctBallDirection() {
		if(ballDirection.y > MathUtils.sinDeg(45.0f) && ballDirection.y < MathUtils.sinDeg(135.0f))
		{
			ballDirection.y = MathUtils.sinDeg(45.0f);
		}
		else
		{
			if(ballDirection.y > MathUtils.sinDeg(245.0f) && ballDirection.y < MathUtils.sinDeg(325.0f))
			{
				ballDirection.y = -MathUtils.sinDeg(45.0f);
			}
		}
		
		ballDirection.nor();
	}

	private void setPaddles() {
		P1Paddle.setPosition(SDMPong.PADDLE_MARGIN, SDMPong.HEIGHT/2-SDMPong.PADDLE_HEIGHT/2);
		P2Paddle.setPosition(SDMPong.WIDTH-SDMPong.PADDLE_MARGIN, SDMPong.HEIGHT/2-SDMPong.PADDLE_HEIGHT/2);
	}

	private void countdown() {
		countDownTimer = countDownTimer - delta;
		
		sdmp.font.setScale(3.0f);
		
		if(countDownTimer > 1.0f)
		{
			sdmp.font.draw(sdmp.batch, "" + (int)countDownTimer, SDMPong.WIDTH/2, SDMPong.HEIGHT/2);
		}
		else
		{
			if(countDownTimer > 0.0f)
			{
				sdmp.font.draw(sdmp.batch, "GO!", SDMPong.WIDTH/2, SDMPong.HEIGHT/2);
			}
			else
			{
				countDownTimer = 4.0f;
				GAME_STATE = GameStateEnum.INIT_GAME;
			}
		}
		
		sdmp.font.setScale(1.0f);
	}

	private void announceGoal() {
		goalTimer -= this.delta;
		
		if(goalTimer > 0.0f)
		{
			goalScale += 0.2;
			sdmp.font.setScale(goalScale);
			sdmp.font.draw(sdmp.batch, "GOAL!", 200, SDMPong.HEIGHT/2);
		}
		else
		{
			goalTimer = 1.0f;
			goalScale = 1.0f;
			GAME_STATE = GameStateEnum.COUNTDOWN;
		}
		
		sdmp.font.setScale(1.0f);
	}

	private void checkIfGoal() {
		if(ball.x < 0)
		{
			P2Points++;
			GAME_STATE = GameStateEnum.GOAL;
		}
		else
		{
			if(ball.x > SDMPong.WIDTH)
			{
				P1Points++;
				GAME_STATE = GameStateEnum.GOAL;
			}
		}
		
	}

	private void moveBall() {
		ball.x = ball.x + ballDirection.x * SDMPong.BALL_MOVEMENT_QUANTITY;
		ball.y = ball.y + ballDirection.y * SDMPong.BALL_MOVEMENT_QUANTITY;
	}

	private void moveGameObject(Rectangle gameObject, DirectionEnum direction, int movementQuantity) {
		switch(direction)
		{
			case UP:
				gameObject.y += movementQuantity * Gdx.graphics.getDeltaTime();
				break;
				
			case DOWN:
				gameObject.y -= movementQuantity * Gdx.graphics.getDeltaTime();
				break;
		}		
	}

	private void readInputAndMovePaddles() {
		readP1Input();
		readP2Input();		
	}

	private void readP2Input() {
		if(Gdx.input.isKeyPressed(Input.Keys.I))
		{
			moveGameObject(P2Paddle, DirectionEnum.UP, SDMPong.PADDLE_MOVEMENT_QUANTITY);
		}
		else
		{
			if(Gdx.input.isKeyPressed(Input.Keys.K))
			{
				moveGameObject(P2Paddle, DirectionEnum.DOWN, SDMPong.PADDLE_MOVEMENT_QUANTITY);
			}
		}		
	}

	private void readP1Input() {
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			moveGameObject(P1Paddle, DirectionEnum.UP, SDMPong.PADDLE_MOVEMENT_QUANTITY);
		}
		else
		{
			if(Gdx.input.isKeyPressed(Input.Keys.S))
			{
				moveGameObject(P1Paddle, DirectionEnum.DOWN, SDMPong.PADDLE_MOVEMENT_QUANTITY);
			}
		}		
	}

	private void drawBall() {
		sdmp.shapeRenderer.circle(ball.x+SDMPong.BALL_RADIUS/2, ball.y+SDMPong.BALL_RADIUS/2, SDMPong.BALL_RADIUS/2);
	}

	private void drawPaddles() {
		sdmp.shapeRenderer.rect(P1Paddle.x, P1Paddle.y, P1Paddle.width, P1Paddle.height);
		sdmp.shapeRenderer.rect(P2Paddle.x, P2Paddle.y, P2Paddle.width, P2Paddle.height);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		bouncingSound.dispose();
	}

}
