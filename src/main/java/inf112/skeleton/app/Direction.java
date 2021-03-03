package inf112.skeleton.app;

/*
All allowed directions
 */
public enum Direction{
    NORTH,
    WEST,
    EAST,
    SOUTH;

    /**
     * Returns direction to the left of given direction
     * @param direction current direction
     * @return new direction
     */
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
    /**
     * Returns direction to the right of given direction
     * @param direction current direction
     * @return new direction
     */
    public Direction rotateRight(Direction direction) {
        if (direction == NORTH)
            direction = EAST;
        else if (direction == WEST)
            direction = NORTH;
        else if (direction == SOUTH)
            direction = WEST;
        else if (direction == EAST)
            direction = SOUTH;
        return direction;
    }

    /**
     * Returns direction opposite of given direction
     * @param direction current direction
     * @return new direction
     */
    public Direction uTurn(Direction direction){
        if (direction == NORTH)
            direction = SOUTH;
        else if (direction == WEST)
            direction = EAST;
        else if (direction == SOUTH)
            direction = NORTH;
        else if (direction == EAST)
            direction = WEST;
        return direction;
    }

    /**
     * direction components used to move the player in the right direction
     * @param direction player direction
     * @return direction components
     */
    public int[] dirComponents(Direction direction){
        if (direction == NORTH)
            return new int[] {0, 1};
        if (direction == SOUTH)
            return new int[] {0, -1};
        if (direction == WEST)
            return new int[] {-1, 0};
        if (direction == EAST)
            return new int[] {1, 0};
        return null;
    }


}


