package inf112.skeleton.app.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;
import com.esotericsoftware.kryonet.Client;
import inf112.skeleton.app.Board;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.network.RRServer;

public class selectMapScreen extends ScreenAdapter {

    private final RoboRallyGame game;
    private final Texture mapScreen;
    private final Button RiskyExchange;
    private final Button FactoryReject;
    private final Button backToMenu;
    private Boolean playAndHost;
    private String connectType;


    public selectMapScreen(RoboRallyGame game, Boolean play, String connectType, String IP) {
        this.game = game;
        this.playAndHost = play;
        this.connectType = connectType;
        RiskyExchange = new Button(new Texture("Menu/riskyExchangeActive.png"),
                new Texture("Menu/riskyExchangeInactive.png"), 300, 86, 500, 300);
        FactoryReject = new Button(new Texture("Menu/factoryRejectActive.png"),
                new Texture("Menu/factoryRejectInactive.png"), 300, 86, 350, 300);
        backToMenu = new Button(new Texture("Menu/buttonActiveBack.png"),
                new Texture("Menu/buttonInactiveBack.png"), 300, 86, 70, 300);
        mapScreen = new Texture("Menu/mapScreen.png");

    }

    @Override
    public void show(){

        // Checks for input and create server and client accordingly

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                if (Button.onClick(game, RiskyExchange, x, y)){
                    runGame("Risky_Exchange");
                }else if (Button.onClick(game, FactoryReject, x, y)) {
                    runGame("Factory_Rejects");
                }else if (Button.onClick(game, backToMenu, x ,y)) {
                    game.setScreen(new MenuScreen(game));
                }

                return true;
            }

        });
    }

    private void runGame(String map) {
        if (playAndHost) {
            System.out.println("Host & Play");
            RRServer server = new RRServer();
            Client client = new Client();
            System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
            game.setScreen(new GameScreen(game, "127.0.0.1", new Board("assets/" + map +".tmx")));
        } else if (connectType == "localhost") {
            Client client = new Client();
            System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
            game.setScreen(new GameScreen(game, "127.0.0.1", new Board("assets/" + map +".tmx")));

        }else if (connectType == "customIP") {
            Client client = new Client();
            System.out.println("Found server with IP: " + client.discoverHost(27960, 5000));
            game.setScreen(new GameScreen(game, connectType, new Board("assets/" + map +".tmx")));

        } else {
            RRServer server = new RRServer();
            Gdx.app.exit();
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(mapScreen,0,0);
        RiskyExchange.buttonHover(game);
        FactoryReject.buttonHover(game);
        backToMenu.buttonHover(game);
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }
}