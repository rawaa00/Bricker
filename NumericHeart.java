package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import bricker.main.BrickerGameManager;

import java.awt.*;

/**
 * a class that Represents a numeric heart in the game.
 */
public class NumericHeart extends GameObject {
    BrickerGameManager brickGameManager;
    private TextRenderable textRenderable;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public NumericHeart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, BrickerGameManager brickGameManager) {
        super(topLeftCorner, dimensions, renderable);
        this.textRenderable= (TextRenderable) renderable;
        this.brickGameManager = brickGameManager;


    }

    /**
     * Updates the state of the heart. This method is called once per frame.
     * @param deltaTime The time elapsed since the last update call, in seconds.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.textRenderable.setString((String.valueOf(brickGameManager.getHeartsNum())));
        this.textRenderable.setColor(Color.WHITE);

    }
}
