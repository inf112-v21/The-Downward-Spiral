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
    private final TextureRegion[][] trSplit;

    public Player(TiledMap tm) {

        playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        TextureRegion tr = new TextureRegion(new Texture("player.png"));
        trSplit = tr.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trSplit[0][0]));

    }

    public void move(TiledMapTileLayer board, int dx, int dy) {
        int x = (int) position.x;
        int y = (int) position.y;

        playerLayer.setCell(x, y, null);

        if(board.getCell(x + dx, y+ dy) == null){
            System.out.println("You can't go outside the map");
        }else{
            position.add(dx, dy);
        }

    }

    public void checkStatus(TiledMapTileLayer flag, TiledMapTileLayer hole) {
        if ((flag.getCell(getX(), getY())) != null){
            System.out.println("Won");
            playerCell.setTile(new StaticTiledMapTile(trSplit[0][2]));
        }
        if ((hole.getCell(getX(), getY())) != null){
            System.out.println("Lost");
            playerCell.setTile(new StaticTiledMapTile(trSplit[0][1]));
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
