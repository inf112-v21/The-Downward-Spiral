package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.network.RRServer;

import java.util.Scanner;

public class Main {

    // Når koden kjøres venter den på input. Trykk enter for å starte server, eller skriv hva som helst for å starte client.
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equals("")) {
            RRServer server = new RRServer();
        } else {

            RobotRally game = new RobotRally();
            Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
            cfg.setTitle("RobotRally");
            cfg.setWindowedMode(600, 800);

            new Lwjgl3Application(game, cfg);
        }
    }
}