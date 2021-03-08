package inf112.skeleton.app;

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
    }

    @Test
    public void handSizeTest(){
        hand = currentDeck.deal(handSize);
        assertEquals(9, hand.size());
        assertEquals(75, currentDeck.size());
    }

    @Test
    public void deckSizeTest(){
        assertEquals(84, currentDeck.size());
    }


    public int numberOfCardMoves(String cardType) {
        int numCards = 0;
        for (Card card : currentDeck.currentDeck){
            if(card.toString().equals(cardType)){
                numCards++;
            }
        }
        return numCards;
    }

    @Test
    public void numberOfMove1Test() {
        int amount = numberOfCardMoves("move_1");
        assertEquals(18, amount);
    }

    @Test
    public void numberOfMove2Test() {
        int amount = numberOfCardMoves("move_2");
        assertEquals(12, amount);
    }

    @Test
    public void numberOfMove3Test() {
        int amount = numberOfCardMoves("move_3");
        assertEquals(6, amount);
    }

    @Test
    public void numberOfMoveNeg1Test() {
        int amount = numberOfCardMoves("move_-1");
        assertEquals(6, amount);
    }

    @Test
    public void numberOfMoveLeftTurnTest() {
        int amount = numberOfCardMoves("left_turn");
        assertEquals(18, amount);
    }

    @Test
    public void numberOfMoveRightTurnTest() {
        int amount = numberOfCardMoves("right_turn");
        assertEquals(18, amount);
    }

    @Test
    public void numberOfMoveUTurnTest() {
        int amount = numberOfCardMoves("u_turn");
        assertEquals(6, amount);
    }


}
