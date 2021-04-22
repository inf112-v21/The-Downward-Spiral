package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.network.RRServer;

public class ConnectScreen extends ScreenAdapter {

    RoboRallyGame game;

    Button connectToLocal;
    Button connectToAddress;

    Texture titleScreen;

    public ConnectScreen(RoboRallyGame game) {
        this.game = game;
        connectToLocal = new Button(new Texture("Menu/buttonPlayActive.png"), new Texture("Menu/buttonPlayInactive.png"), 300, 150, 450, 300);
        connectToAddress = new Button(new Texture("Menu/buttonConnectActive.png"), new Texture("Menu/buttonConnectInactive.png"), 300, 150, 250, 300);

        titleScreen = new Texture("Menu/Menu.png");
    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly
        Gdx.input.setInputProcessor(new InputAdapter() {
            // TODO change to buttons
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {

                if()

                return true;
            }

        });
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.batch.draw(titleScreen,0,0);
        connectToLocal.buttonHover(game);
        connectToAddress.buttonHover(game);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}