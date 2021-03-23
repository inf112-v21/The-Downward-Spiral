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
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
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
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.font.draw(game.batch, "Title Screen!", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .75f);
        game.font.draw(game.batch, "Click the circle to win.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .5f);
        game.font.draw(game.batch, "Press space to play.", Gdx.graphics.getWidth() * .25f, Gdx.graphics.getHeight() * .25f);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}