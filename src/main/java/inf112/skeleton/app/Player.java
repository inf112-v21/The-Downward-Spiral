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

    private boolean flagOneConfirmed;
    private boolean flagTwoConfirmed;

    /**
     * Constructor
     * @param tm
     */
    public Player(TiledMap tm) {

        playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        TextureRegion tr = new TextureRegion(new Texture("player.png"));
        trRegions = tr.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegions[0][0]));

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
     * Checks the status of the game at the end of a move,
     * whether a player has won, picked up a flag or fallen
     * in a hole
     *
     * @param flag
     * @param hole
     */
    public void checkStatus(TiledMapTileLayer flag, TiledMapTileLayer hole) {

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

    /**
     * Displays a player at their current position.
     */
    public void render() {
        playerLayer.setCell(getX(), getY(), playerCell);
    }
}
