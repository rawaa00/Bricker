package bricker.main;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.*;
import java.util.ArrayList;


/**
 * A class that Manages the game logic and do the setup for a bricker game.
 *the class manages how the game Objects interacts with each other including the ball, paddle, bricks,
 *  borders, and background.
 *  It also handles game events like updates, rendering, and checking win/loss conditions.
 */
public class BrickerGameManager extends GameManager {

    ///  Game Attributes  ///
    private Vector2 windowDimensions;
    private UserInputListener userInputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private WindowController windowController;

    private Ball mainBall;
    private int heartsNum ;
    public ArrayList<Heart> heartsList;
    private int brickRows;
    private int brickCols;
    private int  bricksNum;


    ///  Constructors  ///

    /**
     * A constructor that Creates a new window for the bricker game, using the number of rows and cols
     * that the user chooses
     * @param windowTitle the game window title
     * @param windowDimensions dimensions of the window
     * @param brickRows the number of brick rows
     * @param brickCols the number of brick columns
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickRows, int brickCols) {
        super(windowTitle, windowDimensions);
        this.brickRows = brickRows;
        this.brickCols = brickCols;
        this.bricksNum = brickRows * brickCols;
        this.heartsNum=0;
    }


    /**
     * A constructor that Creates a new window for the bricker game, using the default number of
     * rows and cols
     * @param windowTitle the game window title
     * @param windowDimensions dimensions of the window
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {

        this(windowTitle, windowDimensions,Constants.DEFAULT_ROWS,Constants.DEFAULT_COLS);
    }



    ///  Public Methods  ///

    /**
     * A method that is called as soon as the program runs to initialize the game objects and state.
     * @param imageReader Tool to read images.
     * @param soundReader Tool to read sounds.
     * @param inputListener Tool to get user input.
     * @param windowController Tool to control the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        //initializing class fields
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.userInputListener = inputListener;
        this.heartsList = new ArrayList<>();

        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        windowController.setTargetFramerate(Constants.TARGET_FRAME_RATE);
        windowDimensions = windowController.getWindowDimensions();
        InitializingGameManager initializingGameManager= new InitializingGameManager(this);

        //adding a Background
        initializingGameManager.addBackground(windowController, imageReader);

        //creat walls
        initializingGameManager.buildWalls(windowDimensions);

        //creating the main ball
        this.mainBall= initializingGameManager.initializeMainBall(imageReader,
                soundReader, windowController);

        //creat user paddle
       initializingGameManager.initializeMainPaddle(imageReader, inputListener, windowDimensions);

        //creat brick
        initializingGameManager.initializeBricks(imageReader, windowDimensions, brickRows, brickCols);

        //Adding the hearts
        initializingGameManager.initializeHearts(imageReader, windowController);
        initializingGameManager.initializeNumeric(windowController);
    }


    /**
     * checks and updates the game state in each frame of the game
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd();
        addExtraHeart();

    }


    ///  getters  ///

    /**
     * Gets a Renderable image from a specified path
     * @param imagePath the path of the picture
     * @param transparent does the picture have sth transparent
     * @return the image of the object
     */
    public Renderable getImage(String imagePath, boolean transparent) {
        return imageReader.readImage(imagePath, transparent);

    }

    /**
     * gets a sound from a specified path
     * @param soundPath the path of the sound
     * @return the sound of the object
     */
    public Sound getSound(String soundPath) {
        return soundReader.readSound(soundPath);
    }

    /**
     * gets an userInputListener
     * @return userInputListener
     */
    public UserInputListener getUserInputListener() {
        return userInputListener;
    }

    /**
     * gets the dimensions of the game window
     * @return window Dimensions
     */
    public Vector2 getWindowDimensions() {
        return windowDimensions;
    }


    /**
     * adds a heart to the game objects and increment the number of lives
     * @param heart the heart object to  add
     */
    public void addHeart(Heart heart){
        heartsList.add(heart);
        this.heartsNum++;
        this.addObject(heart, Layer.FOREGROUND);
    }

    /**
     * gets the number of lives left
     * @return the number of lives left
     */
    public int getHeartsNum() {
        return heartsNum;
    }


    /**
     * a setter to increment the number of lives
     * @return number of lives
     */
    public int incrementHeartsNum() {
        return heartsNum++;
    }

    /**
     * removes an object from the game objects list
     * @param object the object to remove
     * @return true or false
     */
    public boolean removeObject(GameObject object) {
       return gameObjects().removeGameObject(object);
    }

    /**
     * adds an object to the game objects list
     * @param object the object to add
     * @param layer the layer to add the object to
     */
    public void addObject(GameObject object, int layer) {
        gameObjects().addGameObject(object, layer);
    }

    /**
     * decrements the number of bricks
     */
    public void decrementBricks() {
        if(this.bricksNum>0) {
            this.bricksNum--;
        }
    }


    /**
     * changes the main ball color and speed
     * @param newPath new photo (color) to change the ball to
     * @param speedFactor the factor to change th ball speed
     */
    public void changeBall(String newPath, float speedFactor) {
        if (mainBall.getTag().equals("main ball")) {

            Renderable ballImage = imageReader.readImage(newPath, true);
            this.mainBall.renderer().setRenderable(ballImage);
            float newVelX = this.mainBall.getVelocity().x() * speedFactor;
            float newVelY = this.mainBall.getVelocity().y() * speedFactor;

            this.mainBall.setVelocity(new Vector2(newVelX, newVelY));

        }
    }


    ///  private methods  ///


    /**
     * a method to check the win/lose condition of the game.
     */
    private void checkForGameEnd() {
        double ballHeight = mainBall.getCenter().y();
        String prompt = "";

        // Check if the ball is out of bounds
        if (ballHeight > windowDimensions.y()) {
            heartsNum--;
            if (heartsNum >= 0 && heartsNum < heartsList.size()) {
                gameObjects().removeGameObject(heartsList.getLast(), Layer.FOREGROUND);
                this.heartsList.remove(heartsNum);
                mainBall.setCenter(this.windowDimensions.mult(0.5f));
                mainBall.setVelocity(new Vector2(Constants.BALL_SPEED, Constants.BALL_SPEED));
            } else {
                prompt = "You lose! Play again?";
            }
        }

        // Check if the player wins
        if (bricksNum == 0) {
            prompt = "You win! Play again?";
        }
        // Handle game reset or exit based on the prompt
        if (!prompt.isEmpty()) {
            if (windowController.openYesNoDialog(prompt)) {
                resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }


    /**
     * a method to reset the game if the player decided to play again
     */
    private void resetGame() {
        // Reset the game state
        this.heartsNum = 0;
        this.bricksNum = brickCols * brickRows;
        windowController.resetGame();
    }


    /**
     * add an extra heart to the game hearts when a heart objects collides with the main paddle
     */
    private void addExtraHeart(){
        Vector2 heartCenter;
        if(heartsList.size() < heartsNum){

            //check if the hearts list is empty
            if (this.heartsList.size()==0){
                heartCenter= new Vector2(Constants.BORDER_WIDTH + Constants.HEART_SPACE+
                        Constants.MARGIN,
                        this.getWindowDimensions().y() -  17);
            }
            else {
                Heart last = this.heartsList.get(this.heartsList.size() - 1);
                heartCenter= new Vector2(last.getCenter().x() + last.getDimensions().x()
                        + Constants.MARGIN,
                        this.getWindowDimensions().y() -  17);
            }

            //initializes the new heart and add it to the window
            Renderable heartImage = imageReader.readImage("assets/heart.png",
                    true);
            Heart newHeart = new Heart(Vector2.ZERO,
                    new Vector2(Constants.HEART_SIZE,Constants.HEART_SIZE),heartImage);
            newHeart.setCenter(heartCenter);
            this.heartsList.add(newHeart);
            this.addObject(newHeart, Layer.FOREGROUND);
        }
    }


    ///  main Method  ///
    /**
     * the main method that runs the program
     * @param args
     */
    public static void main(String[] args) {
        if (args.length==0){
            new BrickerGameManager("Bouncing Ball",new Vector2(700,500)).run();
        }
        else if (args.length==2){
            int rows = Integer.parseInt(args[0]);
            int cols = Integer.parseInt(args[1]);

            new BrickerGameManager("Bouncing Ball",new Vector2(700,500), rows, cols ).run();
        }

    }
}
