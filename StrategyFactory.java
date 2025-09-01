package bricker.main;

import bricker.brick_strategies.*;

public class StrategyFactory {

    ///  Class Attributes  ///
    private final BrickerGameManager brickGameManager;

    /**
     * Constructor for the strategy factory
     * @param brickGameManager
     */
    public StrategyFactory(BrickerGameManager brickGameManager){
        this.brickGameManager = brickGameManager;
    }


    /**
     * a factory function that chooses the collision type according to the probability specified
     * @param probability
     * @return
     */
    public CollisionStrategy chooseCollisionStrategy(int probability){

        if(probability < 50){
            return new BasicCollisionStrategy(this.brickGameManager);
        }
        if (probability >= 50 && probability < 60 ){
            return new TurboCollisionStrategy(this.brickGameManager);
        }
        if (probability >= 60 && probability < 70 ){
            return new ExtraPaddleCollisionStrategy(this.brickGameManager);
        }
        if (probability >= 70 && probability < 80 ){
            return new AddBallsCollisionStrategy(this.brickGameManager);
        }
        if (probability >= 80 && probability < 90 ){
            return new ExtraHeartCollisionStrategy(this.brickGameManager);
        }
        if (probability >= 90 && probability < 100 ){
            return new RandomCollisionStrategy(this.brickGameManager);
        }
        return null;
    }
}
