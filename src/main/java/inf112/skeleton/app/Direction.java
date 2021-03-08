package inf112.skeleton.app;

/*
All allowed directions
 */

public enum Direction {
    NORTH,
    WEST,
    EAST,
    SOUTH;

    /**
     * Returns direction to the left of given direction
     * @param dir current direction
     * @return new direction
     */
    public Direction rotateLeft(Direction dir) {
        if (dir == NORTH)
            dir = WEST;
        else if (dir == WEST)
            dir = SOUTH;
        else if (dir == SOUTH)
            dir = EAST;
        else if (dir == EAST)
            dir = NORTH;
        return dir;
    }
    /**
     * Returns direction to the right of given direction
     * @param dir current direction
     * @return new direction
     */
    public Direction rotateRight(Direction dir) {
        if (dir == NORTH)
            dir = EAST;
        else if (dir == WEST)
            dir = NORTH;
        else if (dir == SOUTH)
            dir = WEST;
        else if (dir == EAST)
            dir = SOUTH;
        return dir;
    }

    /**
     * Returns direction opposite of given direction
     * @param dir current direction
     * @return new direction
     */
    public Direction uTurn(Direction dir) {
        if (dir == NORTH)
            dir = SOUTH;
        else if (dir == WEST)
            dir = EAST;
        else if (dir == SOUTH)
            dir = NORTH;
        else if (dir == EAST)
            dir = WEST;
        return dir;
    }

    /**
     * direction components used to move the player in the right direction
     * @param dir player direction
     * @return direction components
     */
    public int[] dirComponents(Direction dir) {
        if (dir == NORTH)
            return new int[] {0, 1};
        if (dir == SOUTH)
            return new int[] {0, -1};
        if (dir == WEST)
            return new int[] {-1, 0};
        if (dir == EAST)
            return new int[] {1, 0};
        return null;
    }


}


