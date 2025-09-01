package bricker.brick_strategies;

import danogl.GameObject;
import bricker.main.BrickerGameManager;


/**
 * This class implements the CollisionStrategy interface. This class only remove the brick from
 * the game when a ball collides with the brick.
 */
public class BasicCollisionStrategy implements CollisionStrategy {

    //class attributes
    private final BrickerGameManager gameManager;


    /**
     * A constructor
     * @param gameManager the manager of the game
     */
    public BasicCollisionStrategy(BrickerGameManager gameManager) {

        this.gameManager=gameManager;
    }


    /**
     * A public void method that overrides the onCollision method of CollisionStrategy interface. This method
     * removes the brick from the game .
     * @param firstObject The object that'll be removed on collision
     * @param secObject The second object in the collision.
     */
    @Override
    public void onCollision(GameObject firstObject , GameObject secObject) {
        System.out.println("collision with brick detected");
        if(gameManager.removeObject(firstObject)) {
            gameManager.decrementBricks();
        }
    }

}
