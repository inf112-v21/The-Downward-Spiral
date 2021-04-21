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
import inf112.skeleton.app.ProgramCards.Deck;



public class GameScreen extends ScreenAdapter {

    static RoboRallyGame game;
    public static Board boardTiledMap;

    private OrthogonalTiledMapRenderer render;

    public static Player localPlayer;
    public static NetworkConnection networkConnection;

    //public Deck currentDeck;

    public GameScreen(RoboRallyGame game) {
        this.game = game;
    }

    /**
     * Creates all the necessary objects for the game
     * to later be displayed.
     */
    @Override
    public void show() {
        boardTiledMap = new Board("assets/Risky_Exchange.tmx");

        //Creates a bird's eye view of the board/game
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, boardTiledMap.getBoardLayer().getWidth(), boardTiledMap.getBoardLayer().getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(boardTiledMap.getLayers(), 1/boardTiledMap.getBoardLayer().getTileWidth());
        render.setView(camera);

        //currentDeck = new Deck();

        localPlayer = new Player();
        this.networkConnection = new NetworkConnection();

        if (localPlayer.selectableCards == null){
            System.out.println("Hit enter to draw cards, or move around with arrows/WASD");
        }

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keyCode) {
                GameScreen.this.keyMovement(keyCode);
                return true;
            }
        });


    }
    /**
     * deals cards to players
     * amount equal to handSize
     */
    public void dealCards(){
        //localPlayer.selectableCards = currentDeck.deal(localPlayer.handSize);
        networkConnection.requestHand(localPlayer.handSize);
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
        // press enter to deal cards
        if (keycode == Input.Keys.ENTER) {
            dealCards();
            System.out.println("To program your robot hit the number corresponding to the move you want to add to your list of moves");
            System.out.println("When you have selected up to 5 moves you can hit SPACE to execute your list of moves");
        }
        // use 1-9 to pick which card
        if (localPlayer.selectableCards != null) {
            for (int i = 0; i < localPlayer.selectableCards.size(); i++) {
                if (keycode == (i + 8)) {
                    localPlayer.chooseCard(i);
                }
            }
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

        // You can move with Arrows or WASD
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            localPlayer.setDirection(Direction.NORTH);
            this.moveOneForward();

        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            localPlayer.setDirection(Direction.SOUTH);
            this.moveOneForward();
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            localPlayer.setDirection(Direction.EAST);
            this.moveOneForward();
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            localPlayer.setDirection(Direction.WEST);
            this.moveOneForward();
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
            Gdx.gl.glClearColor(1, 1, 1, 1);
            Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

            localPlayer.render();

            if (!networkConnection.getNetworkPlayers().isEmpty()) {
                for (Player player : networkConnection.getNetworkPlayers().values()) {
                    player.render();
                }
            }
            render.render();
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
