package inf112.skeleton.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.GUI.Button;

import java.util.Timer;
import java.util.TimerTask;

public class ConnectScreen extends ScreenAdapter {

    RoboRallyGame game;

    private final Button connectToLocal;
    private final Button inputAddress;
    private final Button connectToAddress;
    private final Texture titleScreen;
    private final BitmapFont font;
    private String inputIP;
    private String lastInput;
    private InputAdapter test;

    public ConnectScreen(RoboRallyGame game) {
        this.game = game;
        connectToLocal = new Button(new Texture("Menu/buttonActiveLocal.png"), new Texture("Menu/buttonInactiveLocal.png"), 300, 86, 550, 300);
        inputAddress = new Button(new Texture("Menu/input.png"), new Texture("Menu/input.png"), 456, 65, 250, 300);
        connectToAddress = new Button(new Texture("Menu/buttonConnectActive.png"), new Texture("Menu/buttonConnectInactive.png"), 300, 100, 150, 300);
        font = new BitmapFont();
        inputIP = "";
        lastInput = "";

        titleScreen = new Texture("Menu/Menu.png");

        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if(inputIP.contains("|")){
                    inputIP = inputIP.replace("|", "");
                }else{
                    inputIP = inputIP.concat("|");
                }
            }
        }, 0, 500);
    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly
        Gdx.input.setInputProcessor(        
                test = (new InputAdapter() {
                    // TODO change to buttons
                    @Override
                    public boolean touchUp(int x, int y, int pointer, int button) {

                        if(Button.onClick(game, connectToLocal, x, y)){
                            Client client = new Client();
                            System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                            game.setScreen(new GameScreen(game));
                        }
                        if(Button.onClick(game, inputAddress, x, y)){
                            inputIP = "|";
                            // Wrote own text-field input
                            Gdx.input.setInputProcessor(new InputAdapter() {
                                @Override
                                public boolean keyUp(int keyCode) {
                                    if(keyCode == Input.Keys.ENTER){
                                        Gdx.input.setInputProcessor(
                                                test
                                        );
                                    }else if(keyCode == Input.Keys.BACKSPACE && inputIP.length() > 0){
                                        inputIP = inputIP.substring(0, inputIP.length() - lastInput.length());
                                    }else{
                                        inputIP = inputIP.concat(Input.Keys.toString(keyCode));
                                        lastInput = Input.Keys.toString(keyCode);
                                    }
                                    return true;
                                }
                            });

                        }
                        if(Button.onClick(game, connectToAddress, x, y)){
                            Client client = new Client();
                            client.discoverHost(27960, 5000);
                            game.setScreen(new GameScreen(game, inputIP));
                        }

                        return true;
                    }

                })
        );
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(titleScreen,0,0);
        connectToLocal.buttonHover(game);
        inputAddress.buttonHover(game);
        connectToAddress.buttonHover(game);
        font.draw(game.batch, inputIP, 90, 290);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}