package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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

        playButton = new Button("Menu/buttonPlayActive.png", "Menu/buttonPlayInactive.png", 300, 150, 450);
        connectButton = new Button("Menu/buttonConnectActive.png", "Menu/buttonConnectInactive.png", 300, 150, 250);
        hostButton = new Button("Menu/buttonHostActive.png", "Menu/buttonHostInactive.png", 300, 150, 50);

        titleScreen = new Texture("Menu/Menu.png");
    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keyCode) {
                switch (keyCode) {
                    case Input.Keys.SPACE: {
                        RRServer server = new RRServer();
                        Client client = new Client();
                        System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                        game.setScreen(new GameScreen(game));
                        break;
                    }
                    case Input.Keys.S: {
                        RRServer server = new RRServer();
                        dispose();
                        break;
                    }

                    case Input.Keys.C: {
                        Client client = new Client();
                        System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                        game.setScreen(new GameScreen(game));
                        break;
                    }
                    default: {
                        break;
                    }

                }
                return true;
            }
            // This is temporary, only for testing purposes
            // TODO change to buttons
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                int playW = playButton.getWIDTH(); int playH = playButton.getHEIGHT(); int playY = playButton.getY();
                int connectW = connectButton.getWIDTH(); int connectH = connectButton.getHEIGHT(); int connectY = connectButton.getY();
                int hostW = hostButton.getWIDTH(); int hostH = hostButton.getHEIGHT(); int hostY = hostButton.getY();
                int w = game.getWIDTH(); int h = game.getHEIGHT();

                if (x < w/2 - playW / 2 + playW && x > w/2 - playW / 2 && h - y < playY + playH && h - y > playY){
                    System.out.println("Host & Play");
                    RRServer server = new RRServer();
                    Client client = new Client();
                    System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                    game.setScreen(new GameScreen(game));
                }
                if (x < w/2 - connectW / 2 + connectW && x > w/2 - connectW / 2 && h - y < connectY + connectH && h - y > connectY){
                    Client client = new Client();
                    System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                    game.setScreen(new GameScreen(game));
                }
                if (x < w/2 - hostW / 2 + hostW && x > w/2 - hostW / 2 && h - y < hostY + hostH && h - y > hostY){
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