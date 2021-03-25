package inf112.skeleton.app;

// Network imports
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.network.ClassRegister;
import inf112.skeleton.app.network.NetworkPlayer;
import inf112.skeleton.app.network.PacketRemovePlayer;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;
import inf112.skeleton.app.screens.GameScreen;

import java.io.IOException;
import java.util.HashMap;

public class NetworkConnection {
    private final int maxPlayers = 10;
    private final String serverIP;
    private final int serverPort = 27960;
    private static Client client = new Client();
    //public Player localPlayer = new Player();

    private HashMap<Integer, Player> networkPlayerQueue = new HashMap<>();
    private HashMap<Integer, Player> networkPlayers = new HashMap<>();

    public NetworkConnection() {
        this.serverIP = "127.0.0.1";
        connectionSetup();
    }

    public NetworkConnection(String IP) {
        this.serverIP = IP;
        connectionSetup();
    }

    private void connectionSetup() {
        // Create players and store them in a queue, we do this since Players must be created by same thread which runs the game.
        for (int i = 0; i < this.maxPlayers; i++) {
            networkPlayerQueue.put(i, new Player());
        }


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
                    GameScreen.localPlayer.setPosition(packet.xPos, packet.yPos);

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



    public int getMaxPlayers() {
        return maxPlayers;
    }

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    public HashMap<Integer, Player> getNetworkPlayerQueue() {
        return networkPlayerQueue;
    }

    public HashMap<Integer, Player> getNetworkPlayers() {
        return networkPlayers;
    }
}
