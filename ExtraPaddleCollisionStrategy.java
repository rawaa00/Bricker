package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import bricker.gameobjects.AIPaddle;
import bricker.gameobjects.Paddle;
import bricker.main.BrickerGameManager;
import bricker.main.Constants;

public class ExtraPaddleCollisionStrategy implements CollisionStrategy {

    private BrickerGameManager brickGameManager;


    public static boolean extraPaddle= false;


    public ExtraPaddleCollisionStrategy(BrickerGameManager brickGameManager) {
        this.brickGameManager = brickGameManager;

    }



    @Override
    public void onCollision(GameObject firstObject, GameObject secondObject) {

        if(brickGameManager.removeObject(firstObject)) {
            brickGameManager.decrementBricks();
        }

        if (!extraPaddle){
        Renderable paddleImage = brickGameManager.getImage("assets/paddle.png", false);

        Vector2 newPaddleDim = new Vector2(brickGameManager.getWindowDimensions().x() * 0.5f,
                brickGameManager.getWindowDimensions().y() * 0.5f);
        Paddle newPaddle = new AIPaddle(Vector2.ZERO, new Vector2(Constants.PADDLE_WIDTH,
                Constants.PADDLE_HEIGHT), paddleImage,
                brickGameManager.getUserInputListener(), brickGameManager.getWindowDimensions(),
                this.brickGameManager);

        newPaddle.setCenter(newPaddleDim);
        brickGameManager.addObject(newPaddle, Layer.DEFAULT);
        extraPaddle = true;
        }


    }

}
