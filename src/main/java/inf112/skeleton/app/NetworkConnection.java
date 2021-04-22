package inf112.skeleton.app;

// Network imports
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.network.ClassRegister;
import inf112.skeleton.app.network.NetworkPlayer;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketRemovePlayer;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;
import inf112.skeleton.app.network.packets.PacketRespondHand;
import inf112.skeleton.app.network.packets.PacketRequestHand;
import inf112.skeleton.app.network.packets.PacketExecuteCard;
import inf112.skeleton.app.screens.GameScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

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
                    addPlayer(p.playerID, p.xPos, p.yPos, p.direction);

                    // Server initial response to client
                } else if (object instanceof PacketNewConnectionResponse) {
                    PacketNewConnectionResponse packet = (PacketNewConnectionResponse)object;
                    GameScreen.localPlayer.setPosition(packet.xPos, packet.yPos, Direction.NORTH);
                    GameScreen.localPlayer.selectableCards = packet.hand;

                    // A network player moved
                } else if (object instanceof PacketUpdatePosition) {
                    PacketUpdatePosition packet = (PacketUpdatePosition)object;
                    networkPlayers.get(packet.playerID).setPosition(packet.x, packet.y, packet.direction);
                } else if (object instanceof PacketRemovePlayer) {
                    PacketRemovePlayer packet = (PacketRemovePlayer)object;
                    removePlayer(packet.playerID);
                } else if (object instanceof PacketRespondHand) {
                    PacketRespondHand packet = (PacketRespondHand)object;
                    GameScreen.localPlayer.selectableCards = packet.hand;
                    GameScreen.hud.setSelectableCards();
                } else if (object instanceof PacketExecuteCard) {

                    PacketExecuteCard packet = (PacketExecuteCard)object;


                    if (packet.playerID == client.getID()) {
                        GameScreen.localPlayer.executeCard(packet.card);
                    } else {
                        networkPlayers.get(packet.playerID).executeCard(packet.card);
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

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
     * @param direction The direction of the player
     */
    public void addPlayer(int playerID, int xPos, int yPos, Direction direction) {
        // Add new player to networkPlayers
        networkPlayers.put(playerID, networkPlayerQueue.remove(networkPlayerQueue.size()-1));
        // Position new player on board
        networkPlayers.get(playerID).setPosition(xPos, yPos, direction);
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
     * @param direction The new direction
     */
    public void sendPosition(int x, int y, Direction direction) {
        PacketUpdatePosition packet = new PacketUpdatePosition();
        packet.playerID = client.getID();
        packet.x = x;
        packet.y = y;
        packet.direction = direction;
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

    public void requestHand(int handSize) {
        PacketRequestHand packet = new PacketRequestHand();
        packet.handSize = handSize;
        client.sendTCP(packet);
    }

    public void sendHand(ArrayList<Card> hand) {
        PacketRespondHand packet = new PacketRespondHand();
        packet.hand = hand;
        client.sendTCP(packet);
    }

    public int getClientID() {
        return client.getID();
    }
}
