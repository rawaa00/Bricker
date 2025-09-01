package bricker.gameobjects;

import bricker.brick_strategies.AddBallsCollisionStrategy;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;


/**
 * a class that Represents a puck ball in the game.
 */
public class PuckBall extends Ball{

    private AddBallsCollisionStrategy addBallsCollisionStrategy;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     *                         the GameObject will not be rendered.
     * @param collisionSound   The Sound that plays when the ball collied with another object
     * @param
     */
    public PuckBall(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound,
                    AddBallsCollisionStrategy addBallsCollisionStrategy, BrickerGameManager brickGameManager) {
        super(topLeftCorner, dimensions, renderable, collisionSound,brickGameManager);
        this.addBallsCollisionStrategy=addBallsCollisionStrategy;
    }


    /**
     * Updates the state of the puck. This method is called once per frame.
     *
     * @param deltaTime The time elapsed since the last update call, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        addBallsCollisionStrategy.removePuckBall(this);
    }
}
