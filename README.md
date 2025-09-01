1.
    to break the bricks on collision we added a field of BrickerGameManager then called an API function
    of BrickerGameManager (called removeObject()) inside onCollision() of each Strategy, that removes
    the brick from the game objects then we decrement the number of brick using
    BrickerGameManager.decrementBricks

2.
    we made 3 classes for managing the lives,
    the first one is Heart, this class makes static hearts that appear at the corner of the screen.
    the second class is the FallingHeart inherit from Heart and represent the heart that falls from the
    brick that has the ExtraHeartCollisionStrategy,
    the third class is the NumericHeart that renders the number of the left lives.

3.
    AddBallsCollisionStrategy: if the main ball collied with a brick that has this strategy,
        two puck balls appear the brick's place.
    ExtraHeartCollision: if the main ball collied with a brick that has this strategy,
        an extra heart (life) falls in the place of the brick and if it collied with the main paddle,
        an extra heart will be added on the screen in condition that the number of lives doesnt exceed
         the maximum lives number (4).
    ExtraPaddleCollisionStrategy: if the main ball collied with a brick that has this strategy,
        adds an extra paddle (inherits from the main paddle) at the center of the window, the new paddle
        disappear after colliding with four objects.
    TurboCollisionStrategy: if the main ball collied with a brick that has this strategy,
        it will change to turbo state (it will turned to red and it's speed will be multiplied
        by a factor 1.4)
        and after 6 collisions it turns back to the original state.

4.
    to implement the random double behaviour we first initialized a Random and using it chose a random
    number from 0-99 and used it as a probability for choosing two random behaviour out of the 5
    special behaviours we have.
    to limit the number of behaviours to 3 we used a static int counter and increment it by 1 every time
    a new behaviour is created, then added a condition using the counter.

5. API functions that we added:
    -public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickRows,
        int brickCols) : a Constructor that uses the number of rows and columns specified in the args.
    -public void changeBall(String newPath, float speedFactor): changes the ball color and speed when
        a turbo state is on and when the turbo state is done.

    the next functions are getters/setters that we initialized to help us initializing objects outside
    of the brickerGameManager, or to check the different counters of the game
        -public Renderable getImage(String imagePath, boolean transparent): gets an image
        -public Sound getSound(String soundPath) : gets a sound
        -public UserInputListener getUserInputListener(): helps to get users input
        -public Vector2 getWindowDimensions(): gets the dimensions od the game window to help
            with objects placement.
        -public void addHeart(Heart heart): helps to add a live to the game objects.
        -public int getHeartsNum(): gets the number of lives left.
        -public int incrementHeartsNum(): a setter to increment the number of lives.
        -public boolean removeObject(GameObject object): removes an object from the game objects list.
        -public void addObject(GameObject object, int layer): adds an object to the game objects list.
        -public void decrementBricks(): decrements the number of bricks

we also added a class for all the constants of the game to make it easier to use them in different
classes and also to make it easier to find and change them when needed
