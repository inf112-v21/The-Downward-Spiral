package inf112.skeleton.app;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.ProgramCards.Card;

import java.util.*;

public class CellChecker {
    private Player player;

    public CellChecker(Player player) {
        this.player = player;
    }

    public CellType checkNextMove(Card card) {
        if (card.getMoves() == 0) {
            return CellType.TURN_CARD;
        }

        return checkNextMove(player.direction);
    }

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

                if (idSet.getValue() == 53) { // Gear
                    return CellType.GEAR_LEFT_TURN;
                } else if (idSet.getValue() == 54) { // Gear
                    return CellType.GEAR_RIGHT_TURN;
                }

                return CellType.valueOf(idSet.getKey().toUpperCase());
            }
        }

        return CellType.VALID_MOVE;
    }

    private boolean blockedByWall(Vector2 pos, Direction dir) {
        if (getCellIDsAtPosition(pos).containsKey("Walls")) {
            int wallId = getCellIDsAtPosition(pos).get("Walls");

            switch (dir) {
                case NORTH: {
                    // Should be blocked if wall towards north
                    if (wallId == 16 || wallId == 24 || wallId == 31) return true;

                }
                case SOUTH: {
                    // Should be blocked if wall towards south
                    if (wallId == 8 || wallId == 29 || wallId == 32) return true;
                }
                case WEST: {
                    // Should be blocked if wall towards west
                    if (wallId == 24 || wallId == 30 || wallId == 32) return true;
                }
                case EAST: {
                    // Should be blocked if wall towards east
                    if (wallId == 8 || wallId == 16 || wallId == 23) return true;
                }
            }
            // When/if a player moves towards a wall
        } else if (getCellIDsAtPosition(getNewPosition(pos, dir)).get("Walls") != null){
            int wallId = getCellIDsAtPosition(getNewPosition(pos, dir)).get("Walls");

            switch (dir) {
                case NORTH: {
                    // Should be blocked if wall towards south
                    if (wallId == 8 || wallId == 29 || wallId == 32) return true;
                }
                case SOUTH: {
                    // Should be blocked if wall towards north
                    if (wallId == 16 || wallId == 24 || wallId == 31) return true;
                }
                case WEST: {
                    if (wallId == 8 || wallId == 16 || wallId == 23) return true;
                }
                case EAST: {
                    if (wallId == 24 || wallId == 30 || wallId == 32) return true;
                }
            }
        }
        return false;
    }

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

    public HashMap<String, Integer> getCellIDsAtPosition(Vector2 pos) {
        HashMap<String, Integer> IDs = new HashMap<>();

        for (MapLayer layer : player.getBoard().getLayers().getLayers()) {
            TiledMapTileLayer newLayer = (TiledMapTileLayer) layer;

            if (newLayer.getCell((int)pos.x, (int)pos.y) != null) {
                int id = newLayer.getCell((int) pos.x, (int) pos.y).getTile().getId();
                IDs.put(layer.getName(), id);
            }
        }
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
    GEAR_RIGHT_TURN,
    GEAR_LEFT_TURN,
    TURN_CARD,
    BLOCKED_BY_WALL;
}