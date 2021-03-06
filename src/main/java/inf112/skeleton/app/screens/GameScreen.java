package inf112.skeleton.app.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

// Project imports
import inf112.skeleton.app.Board;
import inf112.skeleton.app.Direction;
import inf112.skeleton.app.NetworkConnection;
import inf112.skeleton.app.Player;
import inf112.skeleton.app.ProgramCards.Card;


public class GameScreen extends ScreenAdapter {
    static RoboRallyGame game;
    public static Board boardTiledMap;
    private final Board gameBoard;

    private OrthogonalTiledMapRenderer render;

    public static CardsMenu hud;
    public static Player localPlayer;
    public static NetworkConnection networkConnection;
    private String connectIP;

    public GameScreen(RoboRallyGame game, String IP, Board board) {
        this.game = game;
        this.connectIP = IP;
        this.gameBoard = board;
    }

    /**
     * Creates all the necessary objects for the game
     * to later be displayed.
     */
    @Override
    public void show() {
        boardTiledMap = gameBoard;

        //Creates a bird's eye view of the board/game
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, (float) (boardTiledMap.getBoardLayer().getWidth()/0.6), boardTiledMap.getBoardLayer().getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(boardTiledMap.getLayers(), 1/boardTiledMap.getBoardLayer().getTileWidth());
        render.setView(camera);

        localPlayer = new Player(1);
        networkConnection = new NetworkConnection(connectIP);
        localPlayer.setConnection(networkConnection);



        if (localPlayer.selectableCards == null){
            System.out.println("Hit enter to draw cards, or move around with arrows/WASD");
        }
        hud = new CardsMenu(game);
        Gdx.graphics.setWindowedMode(game.getWIDTH(), game.getHEIGHT());

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keyCode) {
                GameScreen.this.keyMovement(keyCode);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                hud.touchCardUp(screenX, screenY);
                return true;
            }
        });


    }
    /**
     * deals cards to players
     * amount equal to handSize
     */
    public void dealCards(){
        networkConnection.requestHand(localPlayer.handSize);
        hud.setSelectableCards();
    }

    /**
     * Checks a keystroke input from the user and moves the
     * robot in the respective direction. Sends the position
     * to the server.
     *
     * @param keycode keystroke from player
     * @return boolean whether the input was processed
     */

    public boolean keyMovement(int keycode) {
        boolean isDebug = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

        // press enter to deal cards
        if (keycode == Input.Keys.ENTER) {
            dealCards();
        }
        // Use space to execute your program
        if (localPlayer.chosenCards != null) {
            final int cardSize = localPlayer.chosenCards.size();
            if (keycode == Input.Keys.SPACE) {
                // Sends hand to server
                networkConnection.sendHand(localPlayer.chosenCards);
                for (int i = 0; i < cardSize; i++) {
                    localPlayer.chosenCards.remove(0);
                }
            }
        }
        if (!isDebug) return true;

        // You can move with Arrows or WASD
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            localPlayer.setDirection(Direction.NORTH);
            this.moveOneForward();
            localPlayer.getConnection().sendPosition(localPlayer.getX(), localPlayer.getY(), localPlayer.direction);
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            localPlayer.setDirection(Direction.SOUTH);
            this.moveOneForward();
            localPlayer.getConnection().sendPosition(localPlayer.getX(), localPlayer.getY(), localPlayer.direction);
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            localPlayer.setDirection(Direction.EAST);
            this.moveOneForward();
            localPlayer.getConnection().sendPosition(localPlayer.getX(), localPlayer.getY(), localPlayer.direction);
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            localPlayer.setDirection(Direction.WEST);
            this.moveOneForward();
            localPlayer.getConnection().sendPosition(localPlayer.getX(), localPlayer.getY(), localPlayer.direction);
        }

        // Used for debugging
        if (keycode == Input.Keys.O) {
            Card card = new Card(0, "move_1", 1);
                GameScreen.localPlayer.executeCard(card);
                for (Player player: networkConnection.getNetworkPlayers().values()) {
                    player.executeCard(card);
            }
        }
        return true;
    }

    // Moves the local player in the direction it's facing.
    private void moveOneForward() {
        localPlayer.chosenCards.add(new Card(0, "move_1", 1));
        localPlayer.executeCard(localPlayer.chosenCards.get(localPlayer.chosenCards.size()-1));
        localPlayer.chosenCards.remove(localPlayer.chosenCards.size()-1);
        localPlayer.updateDirection();
    }


    /**
     * Displays the objects that were previously created
     * for the user to see.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
            localPlayer.render();

            if (!networkConnection.getNetworkPlayers().isEmpty()) {
                for (Player player : networkConnection.getNetworkPlayers().values()) {
                    player.render();
                }
            }
            try {
                render.render();
            } catch (Exception e) {
                System.out.println(e);

        }
            hud.renderCard();
    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public static RoboRallyGame getGame() {
        return game;
    }

}
