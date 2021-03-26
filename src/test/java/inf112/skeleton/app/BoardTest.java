package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import static org.junit.Assert.*;


public class BoardTest {

    private static TiledMapTileLayer board;


    @Before
    public void setUp(){
        board = new TiledMapTileLayer(5, 5, 300, 300);

    }
    @Test
    public void boardHeightTest(){
        assertEquals(5, board.getHeight());
    }

    @Test
    public void boardWidthTest(){
        assertEquals(5, board.getWidth());
    }
    @Test
    public void visibilityTest(){
        assertTrue(board.isVisible());
    }

    @Test
    public void testMapObject() {
        assertNotNull(board);
    }

}
