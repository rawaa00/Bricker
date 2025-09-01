package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.FallingHeart;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

/**
 * This class implements the CollisionStrategy interface. This class only remove the brick from
 * the game when a ball collides with the brick and adds a falling heart.
 */
public class ExtraHeartCollisionStrategy implements CollisionStrategy {


    //Class Fields
    private final BrickerGameManager brickGameManager;

    //public Methods

    /**
     * A constructor
     * @param brickGameManager the manager of the game
     */
    public ExtraHeartCollisionStrategy(BrickerGameManager brickGameManager) {
        this.brickGameManager = brickGameManager;
    }


    /**
     * A public void method that overrides the onCollision method of CollisionStrategy interface. This method
     * removes the brick from the game .
     * @param firstObj The object that'll be removed on collision
     * @param secObj The second object in the collision.
     */
    @Override
    public void onCollision(GameObject firstObj, GameObject secObj) {
        if(brickGameManager.removeObject(firstObj)) {
            brickGameManager.decrementBricks();
        }

        Renderable heartImage = brickGameManager.getImage("assets/heart.png", true);

        float XCord = (float) (firstObj.getTopLeftCorner().x() + firstObj.getDimensions().x()*0.5);
        float YCord = (float) (firstObj.getTopLeftCorner().y() + firstObj.getDimensions().y()*0.5);

        FallingHeart fallingHeart= new FallingHeart(new Vector2(XCord,YCord),
                new Vector2(Constants.HEART_SIZE,Constants.HEART_SIZE), heartImage, brickGameManager);

        brickGameManager.addObject(fallingHeart, Layer.DEFAULT);

    }
}
