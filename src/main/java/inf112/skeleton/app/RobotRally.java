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
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.network.ClassRegister;
import inf112.skeleton.app.network.NetworkPlayer;
import inf112.skeleton.app.network.PacketRemovePlayer;
import inf112.skeleton.app.network.RRServer;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;

import java.io.IOException;
import java.util.HashMap;


public class RobotRally extends InputAdapter implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;
    private TiledMap tm;

    private TiledMapTileLayer board;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;

    private OrthogonalTiledMapRenderer render;

    private Player localPlayer;
    private HashMap<Integer, Player> networkPlayerQueue;
    private HashMap<Integer, Player> networkPlayers;
    private final int maxPlayers = 10;
    private final String serverIP = "127.0.0.1";
    private final int serverPort = 27960;

    Client client;

    /**
     * Creates all the necessary objects for the game
     * to later be displayed.
     */
    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        tm = loader.load("src/assets/Risky_Exchange.tmx");


        // Initialize the different layers
        board = (TiledMapTileLayer) tm.getLayers().get("Board");
        flag = (TiledMapTileLayer) tm.getLayers().get("Flag");
        hole = (TiledMapTileLayer) tm.getLayers().get("Hole");

        //Creates a bird's eye view of the board/game
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, board.getWidth(), board.getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/board.getTileWidth());
        render.setView(camera);

        Gdx.input.setInputProcessor(this);

        // NETWORKING
        localPlayer = new Player(tm);
        networkPlayerQueue = new HashMap<>();
        networkPlayers = new HashMap<>();

        // Create players and store them in a queue, we do this since Players must be created by same thread which runs the game.
        for (int i = 0; i < maxPlayers; i++) {
            networkPlayerQueue.put(i, new Player(tm));
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

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            if (localPlayer.move(board, 0, 1)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            if (localPlayer.move(board, 0, -1)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            if (localPlayer.move(board, 1, 0)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            if (localPlayer.move(board, -1, 0)) {
                sendPosition(localPlayer.getX(), localPlayer.getY());
            }
        } else {
            localPlayer.checkStatus(flag, hole);
            return false;
        }
        localPlayer.checkStatus(flag, hole);
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
