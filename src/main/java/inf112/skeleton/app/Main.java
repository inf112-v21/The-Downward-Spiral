package inf112.skeleton.app;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import inf112.skeleton.app.screens.RoboRallyGame;

public class Main {
    public static void main(String[] args) {
        System.out.println("Commands for the game is written in README.md");
        RoboRallyGame game = new RoboRallyGame();
        Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
        cfg.setTitle("RobotRally");
        cfg.setWindowedMode(game.getWIDTH(), game.getHEIGHT());
        cfg.setResizable(false);
        new Lwjgl3Application(game, cfg);
    }
}