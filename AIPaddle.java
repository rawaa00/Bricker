package bricker.gameobjects;

import bricker.brick_strategies.ExtraPaddleCollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

/**
 * a class that Represents an extra paddle in the game. This class extends Paddle.
 */

public class AIPaddle extends Paddle {

    ///   Class Fields   ///
    private int paddleCollisionCounter;
    private BrickerGameManager brickGameManager;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param inputListener     to get the users input
     * @param windowDimensions   window Dimensions
     */
    public AIPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                    UserInputListener inputListener, Vector2 windowDimensions, BrickerGameManager brickGameManager) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.paddleCollisionCounter = 0;
        this.brickGameManager = brickGameManager;

    }

    /**
     * Handles the event when the paddle collides with another object.
     *
     * @param other     The GameObject that collided with the paddle
     * @param collision collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);

        paddleCollisionCounter++;
        if (this.paddleCollisionCounter == Constants.EXTRA_PADDLE_COLLISIONS){
            brickGameManager.removeObject(this);
            ExtraPaddleCollisionStrategy.extraPaddle=false;
        }
    }
}
