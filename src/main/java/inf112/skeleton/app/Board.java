package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Board {
    private TiledMap layers;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer boardLayer;
    private TiledMapTileLayer flagLayer;
    private TiledMapTileLayer holeLayer;

    public Board(String fileName) {
        TmxMapLoader loader = new TmxMapLoader();
        this.layers = loader.load(fileName);

        this.playerLayer = (TiledMapTileLayer) layers.getLayers().get("Player");
        this.boardLayer = (TiledMapTileLayer) layers.getLayers().get("Board");
        this.flagLayer = (TiledMapTileLayer) layers.getLayers().get("Flag");
        this.holeLayer = (TiledMapTileLayer) layers.getLayers().get("Hole");
    }

    public TiledMap getLayers() {
        return layers;
    }

    public TiledMapTileLayer getPlayerLayer() {
        return playerLayer;
    }

    public TiledMapTileLayer getBoardLayer() {
        return boardLayer;
    }

    public TiledMapTileLayer getFlagLayer() {
        return flagLayer;
    }

    public TiledMapTileLayer getHoleLayer() {
        return holeLayer;
    }
}
