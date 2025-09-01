package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.Constants;

import java.awt.event.KeyEvent;

/**
 * a class that Represents a paddle in the game.
 */
public class Paddle extends GameObject {


    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param inputListener to get the users input
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions,
                  Renderable renderable, UserInputListener inputListener, Vector2 windowDimensions){
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;



    }


    /**
     * Updates the state of the paddle. This method is called once per frame.
     * It checks for user input and moves the paddle accordingly. It also prevents the paddle from
     * moving
     * outside the window. In such a case, the paddle will not move.
     *
     * @param deltaTime The time elapsed since the last update call, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        Vector2 movmentDir = Vector2.ZERO;
        super.update(deltaTime);
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
           movmentDir =movmentDir.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movmentDir=movmentDir.add(Vector2.RIGHT);
        }
        setVelocity(movmentDir.mult(Constants.MAIN_PADDLE_SPEED));

        Vector2 topLeftCorner = getTopLeftCorner();
        float paddleWidth=getDimensions().x();
        if(topLeftCorner.x()<Constants.MIN_DISTANCE_FROM_SCREEN_EDGE) {
            topLeftCorner = new Vector2(Constants.MIN_DISTANCE_FROM_SCREEN_EDGE, topLeftCorner.y());
        }
        else if(topLeftCorner.x()>windowDimensions.x() -
                Constants.MIN_DISTANCE_FROM_SCREEN_EDGE-paddleWidth){
            topLeftCorner = new Vector2(topLeftCorner.x()-Constants.MIN_DISTANCE_FROM_SCREEN_EDGE
                             -paddleWidth, topLeftCorner.y());
        }
        setTopLeftCorner(topLeftCorner);
        }

}