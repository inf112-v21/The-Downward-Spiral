package inf112.skeleton.app;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.ProgramCards.Card;

import java.util.*;

public class CellChecker {
    private Player player;

    public CellChecker(Player player) {
        this.player = player;
    }


    /**
     * Used to check what the robot would stand on after moving, given a card.
     * Will return something else if the robot should do something else, instead of moving.
     *
     * @param card the card/program you want to check
     * @return a CellType that describes the new cell/action
     */
    public CellType checkNextMove(Card card) {
        if (card.getMoves() == 0) {
            return CellType.TURN_CARD;
        }

        return checkNextMove(player.direction);
    }

    /**
     * Used to check what the robot would stand on after moving, given a direction.
     * Will return something else if the robot should do something else, instead of moving.
     *
     * @param dir the direction you want to check
     * @return a CellType that describes the new cell/action
     */
    public CellType checkNextMove(Direction dir) {
        Vector2 nextPos = getNewPosition(player.getPosition(), dir);

        // Check whether move is blocked or not
        if (blockedByWall(player.getPosition(), dir)) {
            return CellType.BLOCKED_BY_WALL;
        }

        // Check whether move is out of bounds or not
        if (getCellIDsAtPosition(nextPos).get("Board") == null) {
            return CellType.HOLE;
        }

        // Check everything else -- Assuming there can only be one at a time
        HashMap<String, Integer> layerIDs = getCellIDsAtPosition(nextPos);
        for (String layer : new ArrayList<>(Arrays.asList("Player", "Starting_positions", "Board", "Walls"))) {
            if (layerIDs.containsKey(layer)) layerIDs.remove(layer);
        }

        for (Map.Entry<String, Integer> idSet : layerIDs.entrySet()) {

            if (idSet.getValue() != null) { //
                return CellType.valueOf(idSet.getKey().toUpperCase());
            }
        }

        return CellType.VALID_MOVE;
    }


    /**
     * Takes a position and a direction, then determines if there's a wall blocking
     * the move towards that direction.
     *
     * @param pos a position
     * @param dir the direction you want to look for a wall in
     * @return boolean whether blocked or not
     */
    private boolean blockedByWall(Vector2 pos, Direction dir) {
        boolean standingOnWall = getCellIDsAtPosition(pos).containsKey("Walls");
        boolean landingOnWall = getCellIDsAtPosition(getNewPosition(pos, dir)).containsKey("Walls");

        if (standingOnWall) {
            int currentWallID = getCellIDsAtPosition(pos).get("Walls");

            switch (dir) {
                case NORTH: {
                    // Should be blocked if wall towards north
                    if (currentWallID == 16 || currentWallID == 24 || currentWallID == 31) return true;
                    break;
                }
                case SOUTH: {
                    // Should be blocked if wall towards south
                    if (currentWallID == 8 || currentWallID == 29 || currentWallID == 32) return true;
                    break;
                }
                case WEST: {
                    // Should be blocked if wall towards west
                    if (currentWallID == 24 || currentWallID == 30 || currentWallID == 32) return true;
                    break;
                }
                case EAST: {
                    // Should be blocked if wall towards east
                    if (currentWallID == 8 || currentWallID == 16 || currentWallID == 23) return true;
                    break;
                }
            }

        }
        if (landingOnWall) {
            int nextWallID = getCellIDsAtPosition(getNewPosition(pos, dir)).get("Walls");

            switch (dir) {
                case NORTH: {
                    // Should be blocked if wall towards south
                    if (nextWallID == 8 || nextWallID == 29 || nextWallID == 32) return true;
                    break;
                }
                case SOUTH: {
                    // Should be blocked if wall towards north
                    if (nextWallID == 16 || nextWallID == 24 || nextWallID == 31) return true;
                    break;
                }
                case WEST: {
                    if (nextWallID == 8 || nextWallID == 16 || nextWallID == 23) return true;
                    break;
                }
                case EAST: {
                    if (nextWallID == 24 || nextWallID == 30 || nextWallID == 32) return true;
                    break;
                }
            }
        }
        return false;
    }


    /**
     * Finds a new position
     *
     * @param pos a position
     * @param dir a direction
     * @return the new position when moving one towards the direction
     */
    private Vector2 getNewPosition(Vector2 pos, Direction dir) {
        Vector2 newPos = new Vector2(pos.x, pos.y);

        switch (dir) {
            case NORTH: return newPos.add(0, 1);

            case SOUTH: return newPos.add(0, -1);

            case WEST: return newPos.add(-1, 0);

            case EAST: return newPos.add(1, 0);

        }
        return newPos;
    }


    /**
     * Finds the ID(s) of the cell at given position on every layer.
     * The layer won't be included if the cell is null.
     *
     * @param pos a position
     * @return HashMap with layer as key and ID as value
     */
    public HashMap<String, Integer> getCellIDsAtPosition(Vector2 pos) {
        HashMap<String, Integer> IDs = new HashMap<>();

        MapLayers mapLayers = player.getBoard().getLayers().getLayers();


        for (int i = 0; i < mapLayers.getCount(); i++) {
            MapLayer layer = mapLayers.get(i);
            TiledMapTileLayer newLayer = (TiledMapTileLayer) layer;

            if (newLayer.getCell((int) pos.x, (int) pos.y) != null) {
                int id = newLayer.getCell((int) pos.x, (int) pos.y).getTile().getId();
                IDs.put(layer.getName(), id);

            }
        }

            /*
        for (MapLayer layer : mapLayers) {
            TiledMapTileLayer newLayer = (TiledMapTileLayer) layer;

            if (newLayer.getCell((int)pos.x, (int)pos.y) != null) {
                int id = newLayer.getCell((int) pos.x, (int) pos.y).getTile().getId();
                IDs.put(layer.getName(), id);
            }
        }
        */
        return IDs;
    }
}


enum CellType {
    VALID_MOVE,
    FLAG,
    HOLE,
    WRENCHES,
    YELLOW_BELTS,
    BLUE_BELTS,
    LASER,
    GEARS,
    TURN_CARD,
    BLOCKED_BY_WALL
}