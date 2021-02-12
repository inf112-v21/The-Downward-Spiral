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


public class RobotRally extends InputAdapter implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMapTileLayer board;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;

    private OrthogonalTiledMapRenderer render;

    private Player localPlayer;

    /**
     * Creates all the necessary objects for the game
     * to later be displayed.
     */
    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tm = loader.load("Risky_Exchange.tmx");

        // Initialize the different layers
        board = (TiledMapTileLayer) tm.getLayers().get("Board");
        flag = (TiledMapTileLayer) tm.getLayers().get("Flag");
        hole = (TiledMapTileLayer) tm.getLayers().get("Hole");

        //Creates a bird's eye view of the board/game
        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, board.getWidth(), board.getHeight());
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/board.getTileWidth());
        render.setView(camera);


        localPlayer = new Player(tm);


        Gdx.input.setInputProcessor(this);
    }

    /**
     * Checks a keystroke input from the user and moves the
     * robot in the respective direction.
     *
     * @param keycode keystroke from player
     * @return boolean whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            localPlayer.move(board, 0, 1);
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            localPlayer.move(board, 0, -1);
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            localPlayer.move(board, 1, 0);
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            localPlayer.move(board, -1, 0);
        } else {
            localPlayer.checkStatus(flag, hole);
            return false;
        }
        localPlayer.checkStatus(flag, hole);
        return true;
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    /**
     * Displays the objects that were previously created
     * for the user to see.
     */
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
