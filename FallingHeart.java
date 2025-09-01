
package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

/**
 * a class that Represents a falling heart in the game.
 */
public class FallingHeart extends Heart{

    BrickerGameManager brickGameManager;

    /**
     * Construct a new FallingHeart instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public FallingHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        BrickerGameManager brickGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.brickGameManager = brickGameManager;
        this.setVelocity(new Vector2(0, Constants.HEART_VELOCITY ));
    }

    /**
     * checks the object that should collide with heart
     * @param other     The GameObject that should collide with the heart
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other.getTag().equals("Main Paddle");
    }

    /**
     * Handles the event when the heart collides with another object.
     * @param other     The GameObject that collided with the heart
     * @param collision collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        brickGameManager.removeObject(this);
        if(brickGameManager.getHeartsNum()< Constants.MAX_EXTRA_HEART){
            this.brickGameManager.incrementHeartsNum();
        }
    }



    /**
     * Updates the state of the heart. This method is called once per frame.
     * @param deltaTime The time elapsed since the last update call, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        // Check if the heart has fallen off the screen and remove it if necessary.
        if (this.getTopLeftCorner().y() > brickGameManager.getWindowDimensions().y()) {
            this.brickGameManager.removeObject(this);
        }
    }
}
