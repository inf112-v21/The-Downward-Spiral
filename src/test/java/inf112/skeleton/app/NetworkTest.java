package inf112.skeleton.app;

import com.esotericsoftware.kryonet.Client;
import org.junit.BeforeClass;
import org.junit.Test;
import inf112.skeleton.app.network.RRServer;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */

public class NetworkTest {
    private static RRServer server;
    private static Client client;
    private static int port = 27960;

    private static RobotRally game;
    private static RRServer RRServer;

    @BeforeClass
    public static void init() {
        server = new RRServer();

        client = new Client();

    }


    @Test
    public void clientIsAbleToFindLocalServer() {
        client.start();

        assertNotNull(client.discoverHost(port, 5000));

        client.close();
    }

    @Test
    public void clientConnectsToServer() {
        client.start();
        try {
            client.connect(5000, "127.0.0.1", port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(1, RRServer.server.getConnections().length);
        client.close();
    }

    @Test
    public void serverAddsPlayerToList() {
        client.start();

        try {
            client.connect(5000, "127.0.0.1", port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(!RRServer.players.containsKey(1));

        client.close();
    }

    @Test public void serverRemovesPlayerFromList() {
        client.start();

        try {
            client.connect(5000, "127.0.0.1", port, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        client.stop();

        assertTrue(RRServer.players.isEmpty());
    }

}
