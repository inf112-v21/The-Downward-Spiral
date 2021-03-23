package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.screens.RoboRallyGame;


//TODO Create new readme after rework.
public class Main {
    public static void main(String[] args) {
        System.out.println("Commands for the game is written in README.md");
        RoboRallyGame game = new RoboRallyGame();
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RobotRally");
        cfg.setWindowedMode(600, 800);

        new Lwjgl3Application(game, cfg);

    }
}