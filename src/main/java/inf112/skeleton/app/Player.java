package inf112.skeleton.app;

import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.screens.EndScreen;
import inf112.skeleton.app.screens.GameScreen;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

public class Player {
    private final Vector2 position;
    private NetworkConnection connection;

    private final Board board;
    private final TiledMapTileLayer.Cell playerCell;
    private final TextureRegion[][] trRegionsPlayerStatus;
    private final TextureRegion[][] trRegionsPlayerDir;

    private boolean flagOneConfirmed;
    private boolean flagTwoConfirmed;

    private int lifeTokens = 3;
    private int damageTokens = 0;

    private Vector2 startingPosition;
    public Direction direction;

    public ArrayList<Card> selectableCards; // hand
    public ArrayList<Card> chosenCards; // program
    public int handSize = 9; // should be 9. 5 for testing
    public int fullHandSize = 5;

    /**
     * Constructor
     */
    public Player() {
        this.board = GameScreen.boardTiledMap;
        //playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        position = new Vector2(0,0);

        TextureRegion trStatus = new TextureRegion(new Texture("player_Status.png"));
        trRegionsPlayerStatus = trStatus.split(300, 300);
        TextureRegion trDir = new TextureRegion(new Texture("player_Directions.png"));
        trRegionsPlayerDir = trDir.split(300, 300);

        playerCell = new TiledMapTileLayer.Cell();
        playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][0]));

        direction = Direction.NORTH; // starting direction
        this.chosenCards = new ArrayList<>();

    }

    public void setConnection(NetworkConnection con) {
        this.connection = con;
        this.setStartingPosition(con.getClientID());
    }

    public void setStartingPosition(int id) {
        System.out.println("Joined as player " + id);

        int x = (int) getStartingPosition(connection.getClientID()).x;
        int y = (int) getStartingPosition(connection.getClientID()).y;

        System.out.println("Setting my location to: " + x + " | " + y);
        setPosition(x, y, Direction.NORTH);
        connection.sendPosition(x, y, direction);
    }

    /**
     * Moves the robot in the direction it's facing, but
     * it can also move backwards without changing the robot's direction.
     * It will only move one one space at a time.
     *
     * @param distance the "direction". Positive for forwards, negative for backwards
     */
    public void move(int distance) {
        move(direction, distance);
    }


    /**
     * Moves the robot in given direction, but it
     * can also move in the opposite direction.
     * Will never change the robot's direction.
     *
     * @param dir the direction you want the robot to move in
     * @param distance the "direction". Positive for dir direction, negative for the opposite direction
     */
    public void move(Direction dir, int distance) {
        // Removes player from the old position
        board.getPlayerLayer().setCell(getX(), getY(), null);

        int[] components = dir.dirComponents(dir);
        // If distance is negative

        if (distance < 0) {
            components[0] = components[0] * (-1);
            components[1] = components[1] * (-1);
        }

        position.add(components[0], components[1]); }


    /**
     * Takes a card and moves/turns the robot according to the card and the game rules.
     *
     * @param card the card/program you want to run
     */
    public void executeCard(Card card) {

        int distance = Math.max(1, card.getMoves());
        CellChecker checker = new CellChecker(this);

        for (int i = 0; i < distance; i++) {
            CellType type = checker.checkNextMove(card);

            switch(type) {
                case VALID_MOVE: {
                    // Perform move
                    move(card.getMoves());
                    break;
                }
                case FLAG: {
                    // Perform move
                    move(card.getMoves());

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
                    // Lose a life token
                    this.loseLifeToken();
                    return;
                }
                case BLOCKED_BY_WALL: {
                    // Do nothing
                    break;
                }
                case WRENCHES: {
                    // Do whatever a wrench does
                    move(card.getMoves());
                    if (this.damageTokens > 0) this.damageTokens -= 1;
                    System.out.println("Damage tokens now: " + damageTokens);

                    break;
                }
                case YELLOW_BELTS: { // Should use execute card instead, doesn't register anything it lands on after moving
                    // Doesn't know what it ends up on
                    // Do whatever a yellow belt does
                    move(card.getMoves());

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

                    int beltId = checker.getCellIDsAtPosition(position).get("Blue_belts");
                    switch(beltId) {

                        case(21): {
                            move(Direction.SOUTH, 1);
                            move(Direction.SOUTH, 1);
                            break;
                        }
                        case(14): {
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
                    this.takeDamage();
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

    public void takeDamage() {
        this.damageTokens += 1;
        System.out.println("You took 1 damage and now have " + damageTokens);

        if (this.damageTokens >= 10) {
            this.damageTokens = 0;
            this.loseLifeToken();
        }
    }

    public void loseLifeToken() {
        this.lifeTokens -= 1;
        System.out.println("You lost a life token and now have " + lifeTokens + " left");
        int x = (int) getStartingPosition(connection.getClientID()).x;
        int y = (int) getStartingPosition(connection.getClientID()).y;

        System.out.println(x + " | " + y);
        setPosition(x, y, Direction.NORTH);
        connection.sendPosition(x, y, direction);

        if (this.lifeTokens <= 0) {
            // The player loses
            playerCell.setTile(new StaticTiledMapTile(trRegionsPlayerStatus[0][1]));
            System.out.println("You lost the game");
        } else {
            this.damageTokens = 0; // (Y/N)
            // Move the player to starting position/checkpoint
        }
    }

    private Vector2 getStartingPosition(int id) {
        switch (id) {
            case (1): { return new Vector2(5, 0); }
            case (2): { return new Vector2(6, 0); }
            case (3): { return new Vector2(3, 1); }
            case (4): { return new Vector2(8, 1); }
            case (5): { return new Vector2(1, 2); }
            case (6): { return new Vector2(10, 2); }
        }
        return new Vector2(0,0);
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
     * @param card for the requested card
     */
    public void chooseCard(Card card) {
        if (chosenCards == null || chosenCards.size() <= fullHandSize -1) {
            assert chosenCards != null;
            chosenCards.add(card);
            System.out.println("move " + (card) + " added to hand");
            System.out.println("Your hand: " + chosenCards);
            showHand();
            if (chosenCards.size() == fullHandSize){
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
