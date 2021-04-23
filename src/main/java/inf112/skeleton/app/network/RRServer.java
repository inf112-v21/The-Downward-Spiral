package inf112.skeleton.app.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.ProgramCards.Deck;
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
import java.util.LinkedHashMap;
import java.util.Map;

public class RRServer extends Listener {
    public static Server server;
    public static final int port = 27960;
    public static Map<Integer, NetworkPlayer> players = new HashMap<Integer, NetworkPlayer>();
    public static Deck deck = new Deck();
    public static LinkedHashMap<Card, Integer> selectedCardsThisRound = new LinkedHashMap<Card, Integer>();
    public static ArrayList<LinkedHashMap<Card, Integer>> selectedCardsPerPhase = new ArrayList<>();
    public int roundNumber;

    public RRServer() {
        roundNumber = 0;
        server = new Server();

        // Register classes being sent over the network
        for (Class aClass: ClassRegister.classes) {
            server.getKryo().register(aClass);
        }


        // Server is hosted on 127.0.0.1 (localhost)
        server.start();
        try {
            server.bind(port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Listen for packets being sent to the server
        server.addListener(new Listener() {
            public void received (Connection c, Object object) {

                // If a client sends a move
                if (object instanceof PacketUpdatePosition) {
                    PacketUpdatePosition packet = (PacketUpdatePosition) object;
                    players.get(packet.playerID).xPos = packet.x;
                    players.get(packet.playerID).yPos = packet.y;
                    players.get(packet.playerID).direction = packet.direction;
                    server.sendToAllExceptTCP(c.getID(), packet);

                    // If a client requests a hand
                } else if (object instanceof PacketRequestHand) {
                    PacketRequestHand packet = (PacketRequestHand) object;
                    ArrayList<Card> hand = deck.deal(packet.handSize);

                    PacketRespondHand response = new PacketRespondHand();
                    response.hand = hand;
                    server.sendToTCP(c.getID(), response);

                    // If a client sends chosen cards for the round
                } else if (object instanceof PacketRespondHand) {
                    PacketRespondHand packet = (PacketRespondHand) object;
                    for (Card card: packet.hand) {
                        selectedCardsThisRound.put(card, c.getID());
                    }

                    if (selectedCardsThisRound.keySet().size() == players.size()* GameScreen.localPlayer.fullHandSize) {
                        executeRound();
                        selectedCardsThisRound.clear();
                        selectedCardsPerPhase.clear();
                        roundNumber++;
                    }

                }
            }

            // When a client connects to the server
            public void connected(Connection c) {

                // Send player info to everyone else
                NetworkPlayer player = new NetworkPlayer();

                // Set player info, CHANGE THIS LATER WHEN MORE STUFF IS ADDED
                System.out.println("Set player ID to: " + c.getID());
                player.playerID = c.getID();
                player.xPos = players.size();
                player.yPos = 0;
                player.direction = Direction.NORTH;

                PacketAddPlayer packet = new PacketAddPlayer();
                packet.player = player;
                server.sendToAllExceptTCP(c.getID(), packet);

                // Send all existing players back to client
                for (NetworkPlayer p: players.values()) {
                    PacketAddPlayer packet2 = new PacketAddPlayer();
                    packet2.player = p;
                    c.sendTCP(packet2);
                }

                // Send the new connection the position its in.
                PacketNewConnectionResponse packet3 = new PacketNewConnectionResponse();
                packet3.xPos = player.xPos;
                packet3.yPos = player.yPos;
                c.sendTCP(packet3);


                // Add new player to players list
                players.put(c.getID(), player);



            }

            // When a client disconnects
            public void disconnected(Connection c) {
                players.remove(c.getID());
                PacketRemovePlayer packet = new PacketRemovePlayer();
                packet.playerID = c.getID();
                server.sendToAllExceptTCP(c.getID(), packet);
            }
        });
    }

    /**
     * Executes all cards stored in selectedCardsThisRound one by one.
     */
    private void executeRound() {
        sortCards();

        for (LinkedHashMap<Card, Integer> phase: selectedCardsPerPhase) {
            for (Card card: phase.keySet()) {
                PacketExecuteCard packet = new PacketExecuteCard();
                packet.card = card;
                packet.playerID = selectedCardsThisRound.get(card);
                server.sendToAllTCP(packet);
            }
        }

    }

    // Sort cards into phases
    private void sortCards() {
        for (int i = 1; i < 6; i++) {
            selectedCardsPerPhase.add(new LinkedHashMap<Card, Integer>());
        }
        int i = 0;
        for (Card card: selectedCardsThisRound.keySet()) {
            selectedCardsPerPhase.get(i%5).put(card, selectedCardsThisRound.get(card));
            i++;
        }
    }


}
