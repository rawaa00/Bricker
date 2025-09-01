package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;


/**
 * a class that Represents a ball in the game.
 */
public class Ball extends GameObject {

    private final Sound collisionSound;

    private int turboCounter;
   private BrickerGameManager brickGameManager;

    /**
     * Construct a new Ball instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionSound The Sound that plays when the ball collied with another object
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable
            ,Sound collisionSound, BrickerGameManager brickGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;

        this.turboCounter = 0;
       this.brickGameManager= brickGameManager;

    }

    /**
     * gets the number of collisions happened when the ball was in turbo mode
     * @return
     */
    public int getCollisionCounter() {
        return turboCounter;
    }


    /**
     * Handles the event when the ball collides with another object.
     *
     * @param other     The GameObject that collided with the ball
     * @param collision collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel=getVelocity().flipped( collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();


        if(Constants.isTurbo && this.getTag().equals("main ball")){
            turboCounter++;
            if(this.getCollisionCounter() == 6) {
            brickGameManager.changeBall("assets/ball.png",
                    1/ Constants.SPEED_FACTOR);
            this.turboCounter=0;
            Constants.isTurbo=false;
        }

        }
    }

}


