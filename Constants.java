package bricker.main;

public class Constants {

    //a static variable to track the turbo state
    public static boolean isTurbo =false;

    //Brick game window display constants:
    public static final int BORDER_WIDTH = 10;
    public static final int MARGIN = 10;
    public static final int SPACING = 5;
    public static final int TARGET_FRAME_RATE = 80;


    //Bricks Constants:
    public static final float BRICK_HEIGHT =15;
    public static final int DEFAULT_ROWS = 5;
    public static final int DEFAULT_COLS = 8;


    //Ball Constants:
    public static final float BALL_SPEED = 250;
    public static final int BALL_SIZE = 50;


    //Paddle Constants:
    public static final int PADDLE_WIDTH =200;
    public static final int PADDLE_HEIGHT = 20;
    public static final float MAIN_PADDLE_SPEED = 300;
    public static final float MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    public static final int EXTRA_PADDLE_COLLISIONS = 4;


    //Heart Constants:
    public final static int HEART_SIZE = 25;
    public static final int MAX_EXTRA_HEART = 4;
    public static final int HEART_SPACE = 50;
    public static int DEFAULT_HEART_NUM = 3;
    public static final float HEART_VELOCITY = 100.f;


    //Messages Constants:



    //Strategies Constants:
    public static final float SPEED_FACTOR = 1.4f;
    public static final int MAX_STRATEGIES_NUMBER =3 ;





}
