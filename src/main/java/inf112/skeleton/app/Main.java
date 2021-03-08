package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.network.RRServer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("s")) {
            RRServer server = new RRServer();
        } else if (input.equals("c")){

            RoboRally game = new RoboRally();
            Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
            cfg.setTitle("RobotRally");
            cfg.setWindowedMode(600, 800);

            new Lwjgl3Application(game, cfg);
        } else {
            Client client = new Client();
            System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
        }
    }
}