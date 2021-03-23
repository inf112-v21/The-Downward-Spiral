package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.network.RRServer;

public class MenuScreen extends ScreenAdapter {

    RoboRallyGame game;

    Texture activePlayButton;
    Texture inactivePlayButton;
    Texture activeConnectButton;
    Texture inactiveConnectButton;
    Texture activeHostButton;
    Texture inactiveHostButton;

    private static final int BUTTON_WIDTH = 300;
    private static final int BUTTON_HEIGHT = 150;
    private static final int PLAY_Y = 350;
    private static final int CONNECT_Y = 200;
    private static final int HOST_Y = 50;


    public MenuScreen(RoboRallyGame game) {
        this.game = game;
        activePlayButton = new Texture("Menu/buttonPlayActive.png");
        inactivePlayButton = new Texture("Menu/buttonPlayInactive.png");
        activeConnectButton = new Texture("Menu/buttonConnectActive.png");
        inactiveConnectButton = new Texture("Menu/buttonConnectInactive.png");
        activeHostButton = new Texture("Menu/buttonHostActive.png");
        inactiveHostButton = new Texture("Menu/buttonHostInactive.png");
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
                if ((x < 300 && x > 135) && (y < 306 && y > 220)){
                    System.out.println("Host & Play");
                    RRServer server = new RRServer();
                    Client client = new Client();
                    System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                    game.setScreen(new GameScreen(game));
                }
                return true;
            }

        });
        }

    public void buttonHover(Texture activeButton, Texture inactiveButton, int y){
        int x = 600/2 - BUTTON_WIDTH / 2;
        if (Gdx.input.getX() < x + BUTTON_WIDTH && Gdx.input.getX() > x && 800 - Gdx.input.getY() < y + BUTTON_HEIGHT && 800 - Gdx.input.getY() > y) {
            game.batch.draw(activeButton, x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }else{
            game.batch.draw(inactiveButton, x, y, BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        buttonHover(activePlayButton, inactivePlayButton, PLAY_Y);
        buttonHover(activeConnectButton, inactiveConnectButton, CONNECT_Y);
        buttonHover(activeHostButton, inactiveHostButton, HOST_Y);

        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .90f);
        game.font.draw(game.batch, "Click to host and play", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .65f);
        game.font.draw(game.batch, "Click to connect to a server", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .40f);
        game.font.draw(game.batch, "Click to host a server", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .15f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}