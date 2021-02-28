package inf112.skeleton.app.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.RobotRally;
import inf112.skeleton.app.network.packets.PacketAddPlayer;
import inf112.skeleton.app.network.packets.PacketNewConnectionResponse;
import inf112.skeleton.app.network.packets.PacketUpdatePosition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RRServer extends Listener {
    static Server server;
    public static final int port = 27960;
    static Map<Integer, NetworkPlayer> players = new HashMap<Integer, NetworkPlayer>();

    static RobotRally game = new RobotRally();




    public RRServer() {
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
                    players.get(packet.playerID).xPos = packet.posX;
                    players.get(packet.playerID).yPos = packet.posY;
                    server.sendToAllExceptTCP(c.getID(), packet);
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



}
