package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.ProgramCards.Deck;

import java.util.ArrayList;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.network.ClassRegister;
import inf112.skeleton.app.network.NetworkPlayer;
import inf112.skeleton.app.network.PacketRemovePlayer;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;

import java.io.IOException;
import java.util.HashMap;


public class RoboRally extends InputAdapter implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap tm;
    public static Board boardTiledMap;

    private OrthogonalTiledMapRenderer render;

    private Player localPlayer;
    private HashMap<Integer, Player> networkPlayerQueue;
    private HashMap<Integer, Player> networkPlayers;
    private final int maxPlayers = 10;
    private final String serverIP = "127.0.0.1";
    private final int serverPort = 27960;

    Client client;

    public Deck currentDeck;
    public ArrayList<Card> hand;
    public ArrayList<Card> program;
    public int handSize = 9; // should be 9. 5 for testing

    /**
     * Creates all the necessary objects for the game
     * to later be displayed.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        this.boardTiledMap = new Board("assets/Risky_Exchange.tmx");

        //Creates a bird's eye view of the board/game
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, boardTiledMap.getBoardLayer().getWidth(), boardTiledMap.getBoardLayer().getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(boardTiledMap.getLayers(), 1/boardTiledMap.getBoardLayer().getTileWidth());
        render.setView(camera);

        Gdx.input.setInputProcessor(this);

        currentDeck = new Deck();
        program = new ArrayList<>();
        // NETWORKING
        localPlayer = new Player();
        networkPlayerQueue = new HashMap<>();
        networkPlayers = new HashMap<>();

        // Create players and store them in a queue, we do this since Players must be created by same thread which runs the game.
        for (int i = 0; i < maxPlayers; i++) {
            networkPlayerQueue.put(i, new Player());
        }

        client = new Client();

        // Register classes being sent over the network
        for (Class aClass: ClassRegister.classes) {
            client.getKryo().register(aClass);
        }

        // Connect to server
        client.start();
        try {
            client.connect(5000, serverIP, serverPort, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listen for packets from server
        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                // Add network player to game
                if (object instanceof PacketAddPlayer) {
                    PacketAddPlayer packet = (PacketAddPlayer) object;
                    NetworkPlayer p = packet.player;
                    addPlayer(p.playerID, p.xPos, p.yPos);

                    // Server initial response to client
                } else if (object instanceof PacketNewConnectionResponse) {
                    PacketNewConnectionResponse packet = (PacketNewConnectionResponse)object;
                    localPlayer.setPosition(packet.xPos, packet.yPos);

                    // A network player moved
                } else if (object instanceof PacketUpdatePosition) {
                    PacketUpdatePosition packet = (PacketUpdatePosition)object;
                    networkPlayers.get(packet.playerID).setPosition(packet.posX, packet.posY);
                } else if (object instanceof PacketRemovePlayer) {
                    PacketRemovePlayer packet = (PacketRemovePlayer)object;
                    removePlayer(packet.playerID);
                }
            }
        });

        if (hand == null){
            System.out.println("Hit enter to draw cards, or move around with arrows/WASD");
        }

    }
    /**
     * deals cards to players
     * amount equal to handSize
     */
    public void dealCards(){
        hand = currentDeck.deal(handSize);
        showHand();
    }

    /**
     * prints content of the hand to the terminal
     * this is temporary until we have gui
     */
    public void showHand() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i + 1 + ": " + hand.get(i).toString());
        }

    }

    /**
     * Adds card to the players program
     * @param index of the requested card
     */
    private void chooseProgram(int index) {

        if (program == null || program.size() <= 4) {
            program.add(hand.remove(index));
            System.out.println("move " + (index +1) + " added to hand");
            System.out.println("Your hand: " + program);
            showHand();
            if (program.size() == 5){
                System.out.println("Hit SPACE to execute your list of moves");
            }
        } else {
            System.out.println("Full hand");
            System.out.println("Hit SPACE to execute your list of moves");
        }
    }

    /**
     * moves the player according to the card at index i of the current player hand
     * @param index the cards index in the hand
     */
    public void movePlayer(int index){
        try {
            int moves = program.get(index).getMoves();
            String type = hand.get(index).toString();
            int[] dir = localPlayer.direction.dirComponents(localPlayer.direction);
            for (int i = 0; i < moves; i++) {
                localPlayer.move(dir[0], dir[1]);
                localPlayer.checkStatus();
                sendPosition(localPlayer.getX(), localPlayer.getY());}

            // back up
            if (moves < 0) {
                localPlayer.move(-1*dir[0], -1*dir[1]);
                sendPosition(localPlayer.getX(), localPlayer.getY());}

            // card is rotation card
            if (moves == 0){
                localPlayer.turn(program.get(index).toString());
                sendPosition(localPlayer.getX(), localPlayer.getY());}

            System.out.println("you moved " + moves + " towards " + localPlayer.direction);
            showHand();

        }catch (IndexOutOfBoundsException e){
            System.out.println("You don't have that many cards");
        }
    }





    /**
     * Checks a keystroke input from the user and moves the
     * robot in the respective direction. Sends the position
     * to the server.
     *
     * @param keycode keystroke from player
     * @return boolean whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        // press enter to deal cards
        if (keycode == Input.Keys.ENTER) {
            dealCards();
            System.out.println("To program your robot hit the number corresponding to the move you want to add to your list of moves");
            System.out.println("When you have selected up to 5 moves you can hit SPACE to execute your list of moves");
        }
        // use 1-9 to pick which card
        if (hand != null) {
            for (int i = 0; i < hand.size(); i++) {
                if (keycode == (i + 8)) {
                    chooseProgram(i);
                }
            }
        }
        // Use space to execute your program
        if (program != null) {
            final int cardSize = program.size();
            if (keycode == Input.Keys.SPACE) {
                for (int i = 0; i < cardSize; i++) {
                    movePlayer(0);
                    program.remove(0);
                }
            }
        }

        // You can move with Arrows or WASD
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            localPlayer.rotate(Direction.NORTH);
            if (localPlayer.move(0, 1)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            localPlayer.rotate(Direction.SOUTH);
            if (localPlayer.move(0, -1)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            localPlayer.rotate(Direction.EAST);
            if (localPlayer.move(1, 0)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            localPlayer.rotate(Direction.WEST);
            if (localPlayer.move(-1, 0)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
        }
        localPlayer.checkStatus();

        } else {
            localPlayer.checkStatus();
            return false;
        }
        localPlayer.checkStatus();
        return true;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /**
     * Displays the objects that were previously created
     * for the user to see.
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        localPlayer.render();

        if (!networkPlayers.isEmpty()) {
            for (Player player : networkPlayers.values()) {
                player.render();
            }
        }
        render.render();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    /**
     * Adds player to the game. Pulls player from the player queue, and adds it to
     * the list of active network players.
     *
     * @param playerID The playerID we want to add.
     * @param xPos X position of the player.
     * @param yPos Y position of the player.
     */
    public void addPlayer(int playerID, int xPos, int yPos) {
        // Add new player to networkPlayers
        networkPlayers.put(playerID, networkPlayerQueue.remove(networkPlayerQueue.size()-1));
        // Position new player on board
        networkPlayers.get(playerID).setPosition(xPos, yPos);
    }

    /**
     * Removes given player from the board, and removes it from list of network players.
     *
     * @param playerID ID of the client that disconnected.
     */
    public void removePlayer(int playerID) {
        networkPlayers.get(playerID).removePlayer();
        networkPlayers.remove(playerID);
    }

    /**
     * Sends a move to the server.
     *
     * @param x The x position we moved to.
     * @param y The y position we moved to.
     */
    public void sendPosition(int x, int y) {
        PacketUpdatePosition packet = new PacketUpdatePosition();
        packet.playerID = client.getID();
        packet.posX = x;
        packet.posY = y;
        client.sendTCP(packet);
    }


}
