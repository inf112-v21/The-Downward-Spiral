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
        Gdx.graphics.setWindowedMode(600, 800);
        this.game = game;
        this.restartButton = new Button(new Texture("Menu/buttonRestartActive.png"), new Texture("Menu/buttonRestartInactive.png") , 300, 150, 400, 300);
        this.exitButton = new Button(new Texture("Menu/buttonExitActive.png"), new Texture("Menu/buttonExitInactive.png") , 300, 150, 200, 300);
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

                if (Button.onClick(game, restartButton, x, y)) {
                    game.setScreen(new MenuScreen(game));
                }

                if (Button.onClick(game, exitButton, x, y)) {
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