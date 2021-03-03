package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.ProgramCards.Deck;

import java.util.ArrayList;


public class RobotRally extends InputAdapter implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMapTileLayer board;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;

    private OrthogonalTiledMapRenderer render;

    private Player localPlayer;


    public Deck currentDeck;
    public ArrayList<Card> hand;
    public int handSize = 5; // should be 9. 5 for testing



    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tm = loader.load("Risky_Exchange.tmx");

        board = (TiledMapTileLayer) tm.getLayers().get("Board");
        flag = (TiledMapTileLayer) tm.getLayers().get("Flag");
        hole = (TiledMapTileLayer) tm.getLayers().get("Hole");


        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, board.getWidth(), board.getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/board.getTileWidth());
        render.setView(camera);


        localPlayer = new Player(tm);


        Gdx.input.setInputProcessor(this);

        currentDeck = new Deck();

    }

    public void dealHand(){
        hand = currentDeck.deal(handSize);
        showHand();
    }
    // temporary
    public void showHand() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(i+1 +": " + hand.get(i).toString());
        }
    }


    public void movePlayer(int index){
        try {
            int moves = hand.get(index).getMoves();
            String type = hand.get(index).toString();
            int[] dir = localPlayer.direction.dirComponents(localPlayer.direction);
            for (int i = 0; i < moves; i++) {
                localPlayer.move(board, dir[0], dir[1]);
                localPlayer.checkStatus(flag, hole); }
            // move back (should only be 1 type?)
            if (moves < 0)
                localPlayer.move(board, -1*dir[0], -1*dir[1]);

            if (moves == 0)
                localPlayer.turn(hand.get(index).toString());

            System.out.println("you moved " + moves + " towards " + localPlayer.direction);
            showHand();
        }catch (IndexOutOfBoundsException e){
            System.out.println("You don't have that many cards");
        }
    }


    @Override
    public boolean keyUp(int keycode) {
        // press enter to deal cards
        if (keycode == Input.Keys.ENTER) {
            dealHand();}
        // use 1-9 to pick which card
        for (int i=0; i<9;i++) {
            if (keycode == (i+8))
                movePlayer(i);
        }
        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            localPlayer.move(board, 0, 1);
            localPlayer.rotate(Direction.NORTH);
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            localPlayer.move(board, 0, -1);
            localPlayer.rotate(Direction.SOUTH);
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            localPlayer.move(board, 1, 0);
            localPlayer.rotate(Direction.EAST);
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            localPlayer.move(board, -1, 0);
            localPlayer.rotate(Direction.WEST);
        }
        localPlayer.checkStatus(flag, hole);
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        localPlayer.render();
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

}
