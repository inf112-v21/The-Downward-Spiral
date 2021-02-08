package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;

public class HelloWorld extends InputAdapter implements ApplicationListener {
    private SpriteBatch batch;
    private BitmapFont font;

    private TiledMap tm;
    float tileWidth;
    private TiledMapTileLayer board;
    private TiledMapTileLayer playerLayer;
    private TiledMapTileLayer hole;
    private TiledMapTileLayer flag;

    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer render;

    private Texture texture;
    private Cell playerWonCell;
    private Cell playerDiedCell;
    private Cell playerCell;
    private Vector2 playerVector;

    private TextureRegion[][] trSplit;

    @Override
    public void create() {

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);

        TmxMapLoader loader = new TmxMapLoader();
        tm = loader.load("example.tmx");
        tileWidth = tm.getProperties().get("tilewidth", Integer.class);

        board = (TiledMapTileLayer) tm.getLayers().get("Board");
        playerLayer = (TiledMapTileLayer) tm.getLayers().get("Player");
        hole = (TiledMapTileLayer) tm.getLayers().get("Hole");
        flag = (TiledMapTileLayer) tm.getLayers().get("Flag");



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 5, 5);
        camera.translate((float)0, 0);
        camera.update();
        render = new OrthogonalTiledMapRenderer(tm, 1/tileWidth);
        render.setView(camera);

        TextureRegion tr = new TextureRegion(new Texture("player.png"));
        trSplit = tr.split(300, 300);

        playerCell = new Cell();
        playerCell.setTile(new StaticTiledMapTile(trSplit[0][0]));
        playerVector = new Vector2(0,0);

        Gdx.input.setInputProcessor(this);



    }
    @Override
    public boolean keyUp(int keycode) {
        int x = (int) playerVector.x;
        int y = (int) playerVector.y;

        if (keycode == Input.Keys.UP || keycode == Input.Keys.W) {
            playerLayer.setCell(x, y, null);
            if(board.getCell(x, y+1) == null){
                System.out.println("You can't go outside the map");
            }else{
                playerVector.add(0, 1);
            }
        }
        if (keycode == Input.Keys.DOWN || keycode == Input.Keys.S) {
            playerLayer.setCell(x, y, null);
            if(board.getCell(x, y-1) == null){
                System.out.println("You can't go outside the map");
            }else {
                playerVector.add(0, -1);
            }
        }
        if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.D) {
            playerLayer.setCell(x, y, null);
            if(board.getCell(x+1, y) == null){
                System.out.println("You can't go outside the map");
            }else {
                playerVector.add(1, 0);
            }
        }
        if (keycode == Input.Keys.LEFT || keycode == Input.Keys.A) {
            playerLayer.setCell(x, y, null);
            if(board.getCell(x-1, y) == null){
                System.out.println("You can't go outside the map");
            }else {
                playerVector.add(-1, 0);
            }
        }
        if ((flag.getCell((int)playerVector.x, (int)playerVector.y)) != null){
            System.out.println("Won");
            playerCell.setTile(new StaticTiledMapTile(trSplit[0][2]));
            return true;
        }
        if ((hole.getCell((int)playerVector.x, (int)playerVector.y)) != null){
            System.out.println("Lost");
            playerCell.setTile(new StaticTiledMapTile(trSplit[0][1]));
            return true;
        }
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

        playerLayer.setCell((int)playerVector.x,(int)playerVector.y, playerCell);
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
