package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.screens.EndScreen;
import inf112.skeleton.app.screens.GameScreen;

import java.util.ArrayList;

public class Player {
    private final Vector2 position;

    private final Board board;
    private final TiledMapTileLayer.Cell playerCell;
    private final TextureRegion[][] trRegionsPlayerStatus;
    private final TextureRegion[][] trRegionsPlayerDir;

    private boolean flagOneConfirmed;
    private boolean flagTwoConfirmed;

    public Direction direction;

    public ArrayList<Card> selectableCards; // hand
    public ArrayList<Card> chosenCards; // program
    public int handSize = 9; // should be 9. 5 for testing

    /**
     * Constructor
     */
    public Player() {
        this.board = GameScreen.boardTiledMap;
        //playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0, 0);

        TextureRegion trStatus = new TextureRegion(new Texture("player_Status.png"));
        trRegionsPlayerStatus = trStatus.split(300, 300);
        TextureRegion trDir = new TextureRegion(new Texture("player_Directions.png"));
        trRegionsPlayerDir = trDir.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][0]));

        direction = Direction.NORTH; // starting direction
        this.chosenCards = new ArrayList<>();

    }

    /**
     * Moves the robot in the direction it's facing
     *
     * @param distance how far you want to move
     */
    public void move(int distance) {
        move(direction, distance);
    }

    public void move(Direction dir, int distance) { // Will only move one at a time, the distance is to check if it's moving forwards or backwards
        // Removes player from the old position
        board.getPlayerLayer().setCell(getX(), getY(), null);

        int[] components = dir.dirComponents(dir);
        // If distance is negative

        if (distance < 0) {
            components[0] = components[0] * (-1);
            components[1] = components[1] * (-1);
        }

        position.add(components[0], components[1]);
        GameScreen.networkConnection.sendPosition(this.getX(), this.getY(), this.direction);
    }

    public void executeCard(Card card) {
        if (!chosenCards.contains(card)) {
            System.out.println("You don't have that many cards");
            return;
        }

        int distance = Math.max(1, card.getMoves());

        CellChecker checker = new CellChecker(this);
        CellType type = checker.checkNextMove(card);

        for (int i = 0; i < distance; i++) {
            //System.out.println(type);
            switch(type) {
                case VALID_MOVE: {
                    // Perform move
                    move(card.getMoves());
                    type = checker.checkNextMove(card);
                    break;
                }
                case FLAG: {
                    // Perform move
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    int flagId = checker.getCellIDsAtPosition(position).get("Flag");

                    if (flagId == 55) {
                        flagOneConfirmed = true;
                        System.out.println("Flag1");
                    }
                    if ((flagId == 63) && (flagOneConfirmed)){
                        flagTwoConfirmed = true;
                        System.out.println("Flag2");
                    }
                    if ((flagId == 71) && (flagTwoConfirmed)) {
                        playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][2]));
                        System.out.println("Won");
                        GameScreen.getGame().setScreen(new EndScreen(GameScreen.getGame()));
                    }
                    break;
                }
                case HOLE: {
                    // Take damage
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    System.out.println("Lost");
                    playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][1]));
                    break;
                }
                case BLOCKED_BY_WALL: { // DOES NOT WORK PROPERLY, HAS TO BE FIXED
                    // Do nothing
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    break;
                }
                case WRENCHES: {
                    // Do whatever a wrench does
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    break;
                }
                case YELLOW_BELTS: { // Should use execute card instead, doesn't register anything it lands on after moving
                    // Doesn't know what it ends up on
                    // Do whatever a yellow belt does
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    int beltId = checker.getCellIDsAtPosition(position).get("Yellow_belts");
                    switch(beltId) {
                        case(49): {
                            move(Direction.NORTH, 1);
                            break;
                        }
                        case(50):
                        case(36):
                        case(33): {
                            move(Direction.SOUTH, 1);
                            break;
                        }
                        case(51):
                        case(44): {
                            move(Direction.WEST, 1);
                            break;
                        }
                        case(52):
                        case(41): {
                            move(Direction.EAST, 1);
                            break;
                        }
                        default: {
                            System.out.println("Error with belt: " + beltId);
                            break;
                        }
                    }
                    break;
                }
                case BLUE_BELTS: { // Doesn't know what it ends up on
                    // Do whatever a blue belt does
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    int beltId = checker.getCellIDsAtPosition(position).get("Blue_belts");
                    switch(beltId) {

                        case(21): {
                            System.out.println("Should move 2 down");
                            move(Direction.SOUTH, 1);
                            move(Direction.SOUTH, 1);
                            break;
                        }
                        case(14): {
                            System.out.println("Should move 2 right");
                            move(Direction.EAST, 1);
                            move(Direction.EAST, 1);
                            break;
                        }
                        default: {
                            System.out.println("Error with belt: " + beltId);
                            break;
                        }
                    }
                    break;
                }
                case LASER: {
                    // Take damage
                    move(card.getMoves());
                    type = checker.checkNextMove(card);

                    break;
                }
                case GEARS: {
                    // Turn right
                    move(card.getMoves());

                    int gearId = checker.getCellIDsAtPosition(position).get("Gears");
                    if (gearId == 53) { // turn left
                        turn("left_turn");
                    } else  {
                        turn("right_turn"); // 54, turn right
                    }

                    type = checker.checkNextMove(card);
                    break;
                }
                case TURN_CARD: {
                    turn(card.toString());
                    break;
                }
                default: {
                    System.out.println("Error with type: " + type);
                    break;
                }
            }
        }
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
    public void setDirection(Direction dir) {
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

    /**
     *
     * @param x The new x position
     * @param y The new y position
     * @param direction The new direction
     */
    public void setPosition(int x, int y, Direction direction) {
        this.board.getPlayerLayer().setCell(getX(), getY(), null);
        position.set(x, y);
        setDirection(direction);
        updateDirection();
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

    /**
     * prints content of the hand to the terminal
     * this is temporary until we have gui
     */
    public void showHand() {
        for (int i = 0; i < this.selectableCards.size(); i++) {
            System.out.println(i + 1 + ": " + this.selectableCards.get(i).toString());
        }

    }

    /**
     * Adds card to the players program
     * @param index of the requested card
     */
    public void chooseCard(int index) {
        if (chosenCards == null || chosenCards.size() <= 4) {
            chosenCards.add(selectableCards.remove(index));
            System.out.println("move " + (index +1) + " added to hand");
            System.out.println("Your hand: " + chosenCards);
            showHand();
            if (chosenCards.size() == 5){
                System.out.println("Hit SPACE to execute your list of moves");
            }
        } else {
            System.out.println("Full hand");
            System.out.println("Hit SPACE to execute your list of moves");
        }
    }

    public Board getBoard() {
        return board;
    }

    public Vector2 getPosition() {
        return position;
    }
}
