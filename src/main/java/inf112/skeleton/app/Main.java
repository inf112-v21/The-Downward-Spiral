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

        switch (input) {
            case "s": {
                RRServer server = new RRServer();
                break;
            }
            case "c":
                runGame();
                break;
            case "sc": {
                RRServer server = new RRServer();
                runGame();
                break;
            }
            default:
                Client client = new Client();
                System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                break;
        }
    }

    static private void runGame() {
        RoboRally game = new RoboRally();
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RobotRally");
        cfg.setWindowedMode(600, 800);

        new Lwjgl3Application(game, cfg);
    }
}