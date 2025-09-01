package bricker.brick_strategies;

import danogl.GameObject;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

import java.util.Random;

/**
 * This class implements the CollisionStrategy interface. This class only remove the brick from
 * the game when a ball collides with the brick.
 */
public class RandomCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager brickGameManager;
    private static int strategiesNum=1;  //TODO check this and what should it do exactly
    private CollisionStrategy strategy1;
    private CollisionStrategy strategy2;


    /**
     * A constructor
     * @param brickGameManager the manager of the game
     */
    public RandomCollisionStrategy(BrickerGameManager brickGameManager) {
        this.brickGameManager = brickGameManager;

    }


    /**
     * A public void method that overrides the onCollision method of CollisionStrategy interface.
     * This method removes the brick from the game .
     * @param firstObject The object that'll be removed on collision
     * @param secObject The second object in the collision.
     */
    @Override
    public void onCollision(GameObject firstObject, GameObject secObject) {

        System.out.println("Random collision");
        this.strategy1 = getRandomStrategy();
        this.strategy2 = getRandomStrategy();

        this.strategy1.onCollision(firstObject, secObject);
        this.strategy2.onCollision(firstObject, secObject);


    }


    /**
     * chooses a random strategy
     * @return a collision strategy
     */
    private CollisionStrategy getRandomStrategy() {
        Random rand = new Random();
        int probability = rand.nextInt(100);

        if (probability < 20) {
            return new AddBallsCollisionStrategy(this.brickGameManager);
        }
        if (probability < 40) {
            return new ExtraHeartCollisionStrategy(this.brickGameManager);
        }
        if (probability < 60) {
            return new ExtraPaddleCollisionStrategy(this.brickGameManager);
        }
        if (probability < 80) {
            return new TurboCollisionStrategy(this.brickGameManager);
        }
            if (probability < 100) {

                System.out.println("sec Random collision Strategies num: " + this.strategiesNum);

                if (this.strategiesNum <= Constants.MAX_STRATEGIES_NUMBER) {
                   //brickGameManager.incrementBricks();
                    RandomCollisionStrategy.strategiesNum++;
                    return new RandomCollisionStrategy(this.brickGameManager);

                } else {
                    RandomCollisionStrategy.strategiesNum = 0;
                    System.out.println("sec Random collision Basic");
                    return new BasicCollisionStrategy(this.brickGameManager);
                }
            }
            return null;
        }

}

