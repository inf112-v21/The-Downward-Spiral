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

public class EndScreen extends ScreenAdapter {

    public RoboRallyGame game;
    Texture victoryScreen;
    Button restartButton;

    public EndScreen(RoboRallyGame game) {
        this.game = game;
        this.restartButton = new Button("Menu/buttonRestartActive.png", "Menu/buttonRestartInactive.png" , 300, 150, 300);
        this.victoryScreen = new Texture("Menu/endScreen.png");

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {

            @Override
            public boolean keyDown(int keyCode) {

                if (keyCode == Input.Keys.ENTER) {
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                int restartW = restartButton.getWIDTH();
                int restartH = restartButton.getHEIGHT();
                int restartY = restartButton.getY();
                if (x < 600 / 2 - restartW / 2 + restartW && x > 600 / 2 - restartW / 2 && 800 - y < restartY + restartH && 800 - y > restartY) {
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.25f, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.batch.draw(victoryScreen,0,0);
        restartButton.buttonHover(game);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}