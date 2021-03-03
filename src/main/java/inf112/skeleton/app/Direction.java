package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;

/*
All allowed directions
 */
public enum Direction{
    NORTH,
    WEST,
    EAST,
    SOUTH;

    public Direction rotateLeft(Direction direction) {
        if (direction == NORTH)
            direction = WEST;
        else if (direction == WEST)
            direction = SOUTH;
        else if (direction == SOUTH)
            direction = EAST;
        else if (direction == EAST)
            direction = NORTH;
        return direction;
    }

    public Direction rotateRight(Direction direction) {
        if (direction == NORTH)
            direction = EAST;
        else if (direction == WEST)
            direction = NORTH;
        else if (direction == SOUTH)
            direction = EAST;
        else if (direction == EAST)
            direction = SOUTH;
        return direction;
    }

    // used for moving player in the right direction
    public int[] dirComponents(Direction direction){
        if (direction == NORTH)
            return new int[] {0, 1};
            //return new Vector2(0, 1);
        if (direction == SOUTH)
            return new int[] {0, -1};
            //return new Vector2(0, -1);
        if (direction == WEST)
            return new int[] {-1, 0};
            //return new Vector2(-1, 0);
        if (direction == EAST)
            return new int[] {1, 0};
            //return new Vector2(1, 0);
        return null;
    }


}


