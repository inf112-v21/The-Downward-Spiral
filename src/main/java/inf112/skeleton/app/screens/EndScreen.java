package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.GUI.Button;


public class EndScreen extends ScreenAdapter {

    public RoboRallyGame game;
    Texture victoryScreen;
    Button restartButton;
    Button exitButton;

    public EndScreen(RoboRallyGame game) {
        this.game = game;
        this.restartButton = new Button("Menu/buttonRestartActive.png", "Menu/buttonRestartInactive.png" , 300, 150, 400, 300);
        this.exitButton = new Button("Menu/buttonExitActive.png", "Menu/buttonExitInactive.png" , 300, 150, 200, 300);
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
                int restartW = restartButton.getWIDTH(); int restartH = restartButton.getHEIGHT(); int restartY = restartButton.getY();
                int exitW = exitButton.getWIDTH(); int exitH = exitButton.getHEIGHT(); int exitY = exitButton.getY();
                int w = game.getWIDTH(); int h = game.getHEIGHT();

                if (x < w / 2 - restartW / 2 + restartW && x > w / 2 - restartW / 2 && h - y < restartY + restartH && h - y > restartY) {
                    game.setScreen(new MenuScreen(game));
                }

                if (x < w / 2 - exitW / 2 + exitW && x > w / 2 - exitW / 2 && h - y < exitY + exitH && h - y > exitY) {
                    Gdx.app.exit();
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
        exitButton.buttonHover(game);
        game.batch.end();

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}