package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.network.RRServer;

public class MenuScreen extends ScreenAdapter {

    RoboRallyGame game;

    Button playButton;
    Button connectButton;
    Button hostButton;

    Texture titleScreen;

    public MenuScreen(RoboRallyGame game) {
        this.game = game;
        Gdx.graphics.setWindowedMode(600, 800);

        playButton = new Button(new Texture("Menu/buttonPlayActive.png"), new Texture("Menu/buttonPlayInactive.png"), 300, 86, 450, 300);
        connectButton = new Button(new Texture("Menu/buttonConnectActive.png"), new Texture("Menu/buttonConnectInactive.png"), 300, 86, 250, 300);
        hostButton = new Button(new Texture("Menu/buttonHostActive.png"), new Texture("Menu/buttonHostInactive.png"), 300, 86, 50, 300);

        titleScreen = new Texture("Menu/Menu.png");
    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly
        Gdx.input.setInputProcessor(new InputAdapter() {
            // This is temporary, only for testing purposes
            // TODO change to buttons
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                if (Button.onClick(game, playButton, x, y)){
                    System.out.println("Host & Play");
                    RRServer server = new RRServer();
                    Client client = new Client();
                    System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                    System.out.println("Passed 1 here");
                    game.setScreen(new GameScreen(game));
                }
                if (Button.onClick(game, connectButton, x, y)){
                    game.setScreen(new ConnectScreen(game));

                }
                if (Button.onClick(game, hostButton, x, y)){
                    RRServer server = new RRServer();
                    Gdx.app.exit();
                    dispose();
                }
                return true;
            }

        });
        }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(titleScreen,0,0);
        playButton.buttonHover(game);
        connectButton.buttonHover(game);
        hostButton.buttonHover(game);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}