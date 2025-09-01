package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.PuckBall;
import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

import java.util.Random;


/**
 * This class implements a collision strategy that creates another two balls after the main ball collide
 * with a brick that have this strategy.
 */
public class AddBallsCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager brickGameManager;

    /**
     * A constructor
     * @param manager the manager of the game
     */
    public AddBallsCollisionStrategy( BrickerGameManager manager ) {

        this.brickGameManager = manager;
    }


    /**
     * A public void method that overrides the onCollision method of CollisionStrategy interface.
     * This method removes the brick from the game .
     * @param firstObject The object that'll be removed on collision
     * @param secObject The second object in the collision.
     */
    @Override
    public void onCollision(GameObject firstObject, GameObject secObject) {
        if(brickGameManager.removeObject(firstObject)) {
            brickGameManager.decrementBricks();
        }

            Renderable puckImage = brickGameManager.getImage("assets/mockBall.png", true);
            Sound ballSound = brickGameManager.getSound("assets/blop.wav");

            float puckDimension = (float) (Constants.BALL_SIZE * 0.75);

            //pucks initial location
            float xCoordinationOfPuck = (float) (firstObject.getTopLeftCorner().x() +
                    firstObject.getDimensions().x() * 0.5);
            float yCoordinationOfPuck = (float) (firstObject.getTopLeftCorner().y() +
                    firstObject.getDimensions().y() * 0.5);

            Ball puck1 = new PuckBall(new Vector2(xCoordinationOfPuck, yCoordinationOfPuck),
                    new Vector2(puckDimension, puckDimension), puckImage, ballSound, this,
                    this.brickGameManager);
            Ball puck2 = new PuckBall(new Vector2(xCoordinationOfPuck, yCoordinationOfPuck),
                    new Vector2(puckDimension, puckDimension)
                    , puckImage, ballSound, this, this.brickGameManager);

            //setting velocity
            puckVelocity(puck1);
            puckVelocity(puck2);

            //adding the pucks to the game objects
            brickGameManager.addObject(puck1, Layer.DEFAULT);
            brickGameManager.addObject(puck2, Layer.DEFAULT);
    }


    /**
     * Checks and removes a Puck ball if it goes beyond the game window's height.
     * @param puckBall The Puck ball to check and remove if necessary.
     */
    public void removePuckBall(PuckBall puckBall) {
        float ballHeight = puckBall.getCenter().y();
        if (ballHeight > brickGameManager.getWindowDimensions().y()) {
            brickGameManager.removeObject(puckBall);
        }
    }


    /**
     * sets the velocity of the Puck ball
     * @param puck The Puck ball
     */
    private void puckVelocity(Ball puck){
        Random random  = new Random();
        double angle = random.nextDouble() * Math.PI;
        float velocityX = (float) Math.cos(angle) * Constants.BALL_SPEED;
        float velocityY = (float) Math.sin(angle) * Constants.BALL_SPEED;
        puck.setVelocity(new Vector2(velocityX,velocityY));
    }



}
