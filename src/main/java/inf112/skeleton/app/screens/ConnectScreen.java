package inf112.skeleton.app.screens;

import com.badlogic.gdx.*;
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
    private final Button backToMenu;
    private final Texture titleScreen;
    private final BitmapFont font;
    private String inputIP;
    private String lastInput;
    private boolean userWriting;

    public ConnectScreen(RoboRallyGame game) {
        this.game = game;
        connectToLocal = new Button(new Texture("Menu/buttonActiveLocal.png"), new Texture("Menu/buttonInactiveLocal.png"), 300, 86, 500, 300);
        inputAddress = new Button(new Texture("Menu/input.png"), new Texture("Menu/input.png"), 456, 65, 350, 300);
        connectToAddress = new Button(new Texture("Menu/buttonConnectActive.png"), new Texture("Menu/buttonConnectInactive.png"), 300, 86, 230, 300);
        backToMenu = new Button(new Texture("Menu/buttonActiveBack.png"), new Texture("Menu/buttonInactiveBack.png"), 300, 86, 70, 300);
        font = new BitmapFont();
        userWriting = false;
        inputIP = "Write IP-Address here";
        lastInput = "";

        titleScreen = new Texture("Menu/connectScreen.png");

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
        }, 0, 1000);
    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {

                if (Button.onClick(game, connectToLocal, x, y)) {
                    Client client = new Client();
                    System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
                    game.setScreen(new GameScreen(game));
                }else if (Button.onClick(game, inputAddress, x, y)) {
                    inputIP = "|";
                    userWriting = true;
                    // Wrote own text-field input for more flexibility
                }else if (Button.onClick(game, connectToAddress, x, y)) {
                    Client client = new Client();
                    client.discoverHost(27960, 5000);
                    game.setScreen(new GameScreen(game, inputIP.replace("|", "")));
                }else {
                    userWriting = false;
                }
                return true;
            }
            @Override
            public boolean keyDown(int keyCode) {
                if (userWriting && (Input.Keys.toString(keyCode).length() <= 1 || keyCode == Input.Keys.BACKSPACE || keyCode == Input.Keys.ENTER)){
                    inputIP = inputIP.replace("|", "");
                    if (keyCode == Input.Keys.ENTER){
                        Client client = new Client();
                        client.discoverHost(27960, 5000);
                        game.setScreen(new GameScreen(game, inputIP.replace("|", "")));
                    }
                    if (keyCode == Input.Keys.BACKSPACE && inputIP.length() >= lastInput.length()) {
                        inputIP = inputIP.substring(0, inputIP.length() - lastInput.length());
                        lastInput = " ";
                    } else {
                        inputIP = inputIP.concat(Input.Keys.toString(keyCode).replace("|", ""));
                        lastInput = Input.Keys.toString(keyCode);
                    }
                }
                return true;
            }

        });
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
        backToMenu.buttonHover(game);
        font.draw(game.batch, inputIP, 90, 395);
        font.getData().setScale(2);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}