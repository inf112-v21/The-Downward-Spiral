package inf112.skeleton.app.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.network.RRServer;

public class MenuScreen extends ScreenAdapter {

    RoboRallyGame game;

    public MenuScreen(RoboRallyGame game) {
        this.game = game;
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

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
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