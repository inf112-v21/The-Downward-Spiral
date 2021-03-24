package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private final Vector2 position;
    private final Board board;
    private final TiledMapTileLayer.Cell playerCell;
    private final TextureRegion[][] trRegionsPlayerStatus;
    private final TextureRegion[][] trRegionsPlayerDir;

    private boolean flagOneConfirmed;
    private boolean flagTwoConfirmed;

    public Direction direction;

    /**
     * Constructor
     *
     */
    public Player() {
        this.board = RoboRally.boardTiledMap;
        //playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        TextureRegion trStatus = new TextureRegion(new Texture("player_Status.png"));
        trRegionsPlayerStatus = trStatus.split(300, 300);
        TextureRegion trDir = new TextureRegion(new Texture("player_Directions.png"));
        trRegionsPlayerDir = trDir.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][0]));

        direction = Direction.NORTH; // starting direction

    }

    /**
     * Moves the robot on an initialized board
     * on the x-axis and y-axis. Also makes sure
     * the robot doesn't move outside of the board
     *
     * @param dx
     * @param dy
     *
     * @return true if player is moved
     */
    public boolean move(int dx, int dy) {

        this.board.getPlayerLayer().setCell(getX(), getY(), null);

        if (this.board.getBoardLayer().getCell(getX() + dx, getY() + dy) == null) {
            System.out.println("You can't go outside the map");
            return false;
        } else {
            position.add(dx, dy);
        }
        return true;
    }

    /**
     * Rotated the player based on card type
     * @param type name of card
     */
    public void turn(String type){
        if (type.equals("left_turn"))
            direction = direction.rotateLeft(direction);
        if (type.equals("right_turn"))
            direction = direction.rotateRight(direction);
        if (type.equals("u_turn"))
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
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][0]));
        }
        if (direction == Direction.EAST) {
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerDir[0][0]));
        }
        if (direction == Direction.WEST) {
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerDir[0][1]));
        }
        if (direction == Direction.SOUTH) {
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerDir[0][2]));
        }
    }

    /**
     * Checks the status of the game at the end of a move,
     * whether a player has won, picked up a flag or fallen
     * in a hole
     */
    public boolean checkStatus() {
        updateDirection();

        // Checks if player have won
        if ((this.board.getFlagLayer().getCell(getX(), getY())) != null) {

            int tileId = (this.board.getFlagLayer().getCell(getX(), getY())).getTile().getId();

            if (tileId == 55) {
                flagOneConfirmed = true;
                System.out.println("1st");
            }
            if ((tileId == 63) && (flagOneConfirmed)) {
                flagTwoConfirmed = true;
                System.out.println("2nd");
            }
            if ((tileId == 71) && (flagTwoConfirmed)) {
                playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][2]));
                System.out.println("Won");
                return true;
            }
        }
        if ((this.board.getHoleLayer().getCell(getX(), getY())) != null){
            System.out.println("Lost");
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][1]));
        }
        return false;
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
        this.board.getPlayerLayer().setCell(getX(), getY(), null);
        position.set(x, y);
    }

    public void removePlayer() {
        this.board.getPlayerLayer().setCell(getX(), getY(), null);
    }

    /**
     * Displays a player at their current position.
     */
    public void render() {
        this.board.getPlayerLayer().setCell(getX(), getY(), playerCell);
    }
}
