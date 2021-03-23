package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.Screens.RoboRallyGame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Commands for the game is written in README.md");
        RoboRallyGame game = new RoboRallyGame();
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RobotRally");
        cfg.setWindowedMode(600, 800);

        new Lwjgl3Application(game, cfg);

        /*
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
         */
    }
}