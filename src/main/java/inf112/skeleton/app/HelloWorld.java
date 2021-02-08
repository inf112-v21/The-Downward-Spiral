package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class HelloWorld implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap tm;
    float tileWidth;
    private TiledMapTileLayer board;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer render;

    @Override
    public void create() {

     batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        tm = loader.load("example.tmx");
        tileWidth = tm.getProperties().get("tilewidth", Integer.class);

        board = (TiledMapTileLayer) tm.getLayers().get("Board");



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 5, 5);
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/tileWidth);
        render.setView(camera);




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
