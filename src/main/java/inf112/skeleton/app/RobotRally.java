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
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;


public class RobotRally extends InputAdapter implements ApplicationListener {

    private SpriteBatch batch;
    private BitmapFont font;

    float tileWidth;
    private TiledMapTileLayer board;

    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;

    private OrthogonalTiledMapRenderer render;

    private Texture texture;
    private Cell playerWonCell;
    private Cell playerDiedCell;

    private Player localPlayer;



    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        TiledMap tm = loader.load("example.tmx");
        tileWidth = tm.getProperties().get("tilewidth", Integer.class);

        board = (TiledMapTileLayer) tm.getLayers().get("Board");


        hole = (TiledMapTileLayer) tm.getLayers().get("Hole");
        flag = (TiledMapTileLayer) tm.getLayers().get("Flag");


        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false, 5, 5);
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/tileWidth);
        render.setView(camera);


        localPlayer = new Player(tm);


        Gdx.input.setInputProcessor(this);



    }
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
