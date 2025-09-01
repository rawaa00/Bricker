package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import bricker.gameobjects.*;

import java.awt.*;
import java.util.Random;


/**
 * A class that initializes all the game elements at the beginning this includes the main ball,
 * main paddle, bricks,graphic &numeric hearts, borders,and background.
 */
public class InitializingGameManager {

    ///  Class Fields  ///
    private BrickerGameManager brickGameManager;


    ///  Public Methods  ///
    /**
     * Constructor for InitializingGameManager
     * @param brickGameManager
     */
    public InitializingGameManager(BrickerGameManager brickGameManager) {
        this.brickGameManager = brickGameManager;

    }


    /**
     * initializes the background.
     * @param windowController to get the window dimensions.
     * @param imageReader to get the picture.
     */
    public void addBackground(WindowController windowController, ImageReader imageReader) {

        GameObject background = new GameObject(Vector2.ZERO,
                windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        this.brickGameManager.addObject(background, Layer.BACKGROUND);

    }


    /**
     * initializes the walls/borders
     * @param dimension dimension of the walls
     */
    public void buildWalls(Vector2 dimension) {

        //left wall
        GameObject leftWall = new GameObject(Vector2.ZERO,
                new Vector2(Constants.BORDER_WIDTH, dimension.y()),
                new RectangleRenderable(Color.DARK_GRAY));

        //right wall
        GameObject rightWall = new GameObject(new Vector2(dimension.x()
                - Constants.BORDER_WIDTH, 0),
                new Vector2(Constants.BORDER_WIDTH, dimension.y()),
                new RectangleRenderable(Color.DARK_GRAY));

        //top wall
        GameObject topWall = new GameObject(Vector2.ZERO, new Vector2(dimension.x(),
                Constants.BORDER_WIDTH),
                new RectangleRenderable(Color.DARK_GRAY));

        //adds the walls to the game objects
        this.brickGameManager.addObject(leftWall, Layer.DEFAULT);
        this.brickGameManager.addObject(rightWall, Layer.DEFAULT);
        this.brickGameManager.addObject(topWall, Layer.DEFAULT);
    }


    /**
     * initializes the main ball.
     * @param imageReader to get the ball image
     * @param soundReader to get the ball sound
     * @param windowController to get the window dimensions
     * @return the main ball
     */
    public Ball initializeMainBall(ImageReader imageReader, SoundReader soundReader,
                                   WindowController windowController) {

        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");

        Ball ball = new Ball(Vector2.ZERO, new Vector2(Constants.BALL_SIZE, Constants.BALL_SIZE),
                ballImage, collisionSound, this.brickGameManager);
        ball.setTag("main ball");

        Vector2 windowDimisions = windowController.getWindowDimensions();
        ball.setCenter(windowDimisions.mult(0.5f));

        //set the speed
        float ballVelx = Constants.BALL_SPEED;
        float ballVely = Constants.BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean())
            ballVelx *= -1;
        if (rand.nextBoolean())
            ballVelx *= -1;
        ball.setVelocity(new Vector2(ballVelx, ballVely));

        this.brickGameManager.addObject(ball, Layer.DEFAULT);
        return ball;
    }


    /**
     * initializes the main paddle
     * @param imageReader to get the paddle image
     * @param inputListener to get the users input
     * @param windowDimensions to get the window dimensions
     */
    public void initializeMainPaddle(ImageReader imageReader,
                                     UserInputListener inputListener, Vector2 windowDimensions) {

        Renderable paddleImage = imageReader.readImage("assets/paddle.png", false);

        Paddle userPaddle = new Paddle(Vector2.ZERO,
                new Vector2(Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT),
                paddleImage,
                inputListener,
                windowDimensions);

        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,
                (int) windowDimensions.y() - 30));
        userPaddle.setTag("Main Paddle");

        this.brickGameManager.addObject(userPaddle, Layer.DEFAULT);
    }


    /**
     * initializes the default lives/hearts
     * @param imageReader to get the paddle image
     * @param windowController to get the window dimensions
     */
    public void initializeHearts(ImageReader imageReader, WindowController windowController) {

            float  heartPlacement  = Constants.BORDER_WIDTH + Constants.HEART_SPACE;
            Renderable heartImage = imageReader.readImage("assets/heart.png", true);

            //creates 3 lives
            for(int i =0; i< Constants.DEFAULT_HEART_NUM; i++){
                Heart heart = new Heart(new Vector2(heartPlacement,
                        windowController.getWindowDimensions().y()-30),
                        new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE), heartImage);


                brickGameManager.addHeart(heart);
                heartPlacement+=Constants.HEART_SIZE+Constants.MARGIN;
            }
    }


    /**
     * initializes the bricks
     * @param imageReader to get the brick image
     * @param windowDimensions to get the window dimensions
     * @param brickRows the number of brick rows
     * @param brickCols the number of brick cols
     */
    public void initializeBricks(ImageReader imageReader, Vector2 windowDimensions,
                                 int brickRows, int brickCols) {

        Random random = new Random();
        StrategyFactory strategyFactory= new StrategyFactory(this.brickGameManager);

        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        float brickWidth = ((windowDimensions.x() - 2 * Constants.BORDER_WIDTH)
                - (brickRows - 1) * Constants.MARGIN) / brickCols;
        float brickHeight = Constants.BRICK_HEIGHT;

        for (int row = 0; row < brickRows; row++) {
            for (int col = 0; col < brickCols; col++) {

                Vector2 brickPosition = new Vector2(
                        Constants.MARGIN + col * (brickWidth + Constants.SPACING),
                        Constants.MARGIN + row * (brickHeight + Constants.SPACING));

                //get the collision strategy randomly using the factory
                int probability = random.nextInt(100);
                CollisionStrategy collisionStrategy =
                        strategyFactory.chooseCollisionStrategy(probability);

                Brick brick = new Brick( brickPosition, new Vector2(brickWidth, brickHeight),
                        brickImage, collisionStrategy);

                this.brickGameManager.addObject(brick, Layer.DEFAULT);
            }
        }
    }


    /**
     * initializes the numeric heart counter
     * @param windowController to get the window dimensions
     */
    public void initializeNumeric(WindowController windowController){
        Vector2 numericPlacement= new Vector2(2*Constants.MARGIN+Constants.BORDER_WIDTH,
                windowController.getWindowDimensions().y()-35) ;
        Vector2 numericSize= new Vector2(Constants.HEART_SIZE, Constants.HEART_SIZE);
        TextRenderable textRenderable= new TextRenderable(String.valueOf(brickGameManager.getHeartsNum()));

        NumericHeart numericHeart= new NumericHeart(numericPlacement, numericSize,
                textRenderable,this.brickGameManager);
        brickGameManager.addObject(numericHeart, Layer.FOREGROUND);
    }
}

