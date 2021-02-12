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

    public Player(TiledMap tm) {

        playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        TextureRegion tr = new TextureRegion(new Texture("player.png"));
        trRegions = tr.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegions[0][0]));

    }

    public void move(TiledMapTileLayer board, int dx, int dy) {

        playerLayer.setCell(getX(), getY(), null);

        if(board.getCell(getX() + dx, getY()+ dy) == null){
            System.out.println("You can't go outside the map");
        }else{
            position.add(dx, dy);
        }

    }

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

    public int getX() {
        return (int) position.x;
    }
    public int getY() {
        return (int) position.y;
    }

    public void render() {
        playerLayer.setCell(getX(), getY(), playerCell);
    }
}
