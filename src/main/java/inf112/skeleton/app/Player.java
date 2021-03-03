package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private final Vector2 position;
    private final TiledMapTileLayer playerLayer;
    private final TiledMapTileLayer.Cell playerCell;
    private final TextureRegion[][] trRegions;
    private final TextureRegion[][] trRegions2;

    private boolean flagOneConfirmed;
    private boolean flagTwoConfirmed;

    public Direction direction;

    /**
     * Constructor
     * @param tm
     */
    public Player(TiledMap tm) {

        playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        // temporary texture showing direction
        TextureRegion tr = new TextureRegion(new Texture("player_1.png"));
        trRegions = tr.split(300, 300);
        TextureRegion tr2 = new TextureRegion(new Texture("player_2.png"));
        trRegions2 = tr2.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegions[0][0]));

        direction = Direction.NORTH; // starting direction

    }

    /**
     * Moves the robot on an initialized board
     * on the x-axis and y-axis. Also makes sure
     * the robot doesn't move outside of the board
     *
     * @param board
     * @param dx
     * @param dy
     *
     * @return true if player is moved
     */
    public boolean move(TiledMapTileLayer board, int dx, int dy) {

        playerLayer.setCell(getX(), getY(), null);

        if(board.getCell(getX() + dx, getY()+ dy) == null){
            System.out.println("You can't go outside the map");
            return false;
        }else{
            position.add(dx, dy);
        }
        return true;

    }

    /**
     * Rotated the player based on card type
     * @param type name of card
     */
    public void turn(String type){
        if (type == "left_turn")
            direction = direction.rotateLeft(direction);
        if (type == "right_turn")
            direction = direction.rotateRight(direction);
        if (type == "u_turn")
            direction = direction.uTurn(direction);
        updateDirection();
    }

    /**
     * Change player direction
     * @param dir new direction
     */
    public void rotate(Direction dir) {
        direction = dir; }

    // updates texture for robot based on direction (temp)
    public void updateDirection() {
        if (direction == Direction.NORTH) {
            playerCell.setTile(new StaticTiledMapTile(trRegions[0][0]));
        }
        if (direction == Direction.EAST) {
            playerCell.setTile(new StaticTiledMapTile(trRegions2[0][0]));
        }
        if (direction == Direction.WEST) {
            playerCell.setTile(new StaticTiledMapTile(trRegions2[0][1]));
        }
        if (direction == Direction.SOUTH) {
            playerCell.setTile(new StaticTiledMapTile(trRegions2[0][2]));
        }
    }

    /**
     * Checks the status of the game at the end of a move,
     * whether a player has won, picked up a flag or fallen
     * in a hole
     *
     * @param flag
     * @param hole
     */
    public void checkStatus(TiledMapTileLayer flag, TiledMapTileLayer hole) {
        updateDirection();
        // Checks if player have won
        if ((flag.getCell(getX(), getY())) != null) {
            if ((flag.getCell(getX(), getY())).getTile().getId() == 55) {
                flagOneConfirmed = true;
                System.out.println("1st");
            }
            if (((flag.getCell(getX(), getY())).getTile().getId() == 63) && (flagOneConfirmed)) {
                flagTwoConfirmed = true;
                System.out.println("2nd");
            }
            if (((flag.getCell(getX(), getY())).getTile().getId() == 71) && (flagTwoConfirmed)) {
                playerCell.setTile(new StaticTiledMapTile(trRegions[0][2]));
                System.out.println("Won");

            }
        }
        if ((hole.getCell(getX(), getY())) != null){
            System.out.println("Lost");
            playerCell.setTile(new StaticTiledMapTile(trRegions[0][1]));
        }
    }

    /**
     * Gets the position of a robot on the x-axis
     * @return x coordinate
     */
    public int getX() {
        return (int) position.x;
    }

    /**
     * Gets the position of a robot on the y-axis
     * @return y coordinate
     */
    public int getY() {
        return (int) position.y;
    }

    public void setPosition(int x, int y) {
        playerLayer.setCell(getX(), getY(), null);
        position.set(x, y);
    }

    public void removePlayer() {
        playerLayer.setCell(getX(), getY(), null);
    }

    /**
     * Displays a player at their current position.
     */
    public void render() {
        playerLayer.setCell(getX(), getY(), playerCell);
    }
}
