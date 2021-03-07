package inf112.skeleton.app;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import inf112.skeleton.app.ProgramCards.Card;
import inf112.skeleton.app.ProgramCards.Deck;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CardTest {

    public Deck currentDeck;
    public ArrayList<Card> hand;
    public int handSize = 9;

    @Before
    public void setup(){
        currentDeck = new Deck();
        hand = currentDeck.deal(handSize);
    }

    @Test
    public void handSizeTest(){
        assertEquals(9, hand.size());
    }

    @Test
    public void deckSizeTest(){
        assertEquals(84, currentDeck.size());
    }




}
