package inf112.skeleton.app.ProgramCards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Deck {
    public ArrayList<Card> currentDeck;


    public Deck() {
        createDeck();
    }

    /**
     * create new deck
     */
    public void createDeck () {
        currentDeck = new ArrayList<>();
        // todo: give cards different priority
        for (int i = 0; i < 18; i++) currentDeck.add(new Card(0, "move_1", 1));
        for (int i = 0; i < 12; i++) currentDeck.add(new Card(0, "move_2", 2));
        for (int i = 0; i < 6; i++) currentDeck.add(new Card(0, "move_3",3));
        for (int i = 0; i < 6; i++) currentDeck.add(new Card(0, "move_-1",-1));
        // rotation cards, movement = 0
        for (int i = 0; i < 18; i++) currentDeck.add(new Card(0, "left_turn", 0));
        for (int i = 0; i < 18; i++) currentDeck.add(new Card(0, "right_turn", 0));
        for (int i = 0; i < 6; i++) currentDeck.add(new Card(0, "u_turn",0));
        Collections.shuffle(currentDeck);
        }

    /**
     * Deal cards to players
     * @param handSize how many cards each player should get
     * @return arraylist of cards
     */
    public ArrayList<Card> deal(int handSize) {
        ArrayList<Card> hand = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<handSize; i++){
            hand.add(currentDeck.remove(random.nextInt(currentDeck.size())));
        }
        return hand;
    }

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void remove(Card card) {
        currentDeck.remove(card);
    }
}