package bricker.brick_strategies;

import danogl.GameObject;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

/**
 * This class implements the CollisionStrategy interface. This class only remove the brick from
 * the game when a ball collides with the brick.
 */
public class TurboCollisionStrategy implements CollisionStrategy{

    private BrickerGameManager brickGameManager;


    /**
     * A constructor
     * @param brickGameManager the manager of the game
     */
    public TurboCollisionStrategy(BrickerGameManager brickGameManager) {
        this.brickGameManager = brickGameManager;

    }


    /**
     * A public void method that overrides the onCollision method of CollisionStrategy interface.
     * This method removes the brick from the game .
     * @param firstObject The object that'll be removed on collision
     * @param secondObject The second object in the collision.
     */
    @Override
    public void onCollision(GameObject firstObject, GameObject secondObject) {
        if(brickGameManager.removeObject(firstObject)) {
            brickGameManager.decrementBricks();
        }

        if (!Constants.isTurbo && secondObject.getTag().equals("main ball")) {
            brickGameManager.changeBall("assets/redball.png", Constants.SPEED_FACTOR);
            Constants.isTurbo=true;

        }
    }


}