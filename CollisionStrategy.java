package bricker.brick_strategies;

import danogl.GameObject;


/**
 * a CollisionStrategy interface.
 */
public interface CollisionStrategy {

    /**
     *This method removes the brick from the game on collision.
     * @param one The object that'll be removed on collision
     * @param two The second object in the collision.
     */
    void onCollision(GameObject one, GameObject two);
}

