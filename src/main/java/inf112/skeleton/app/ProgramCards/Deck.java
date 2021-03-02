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
        // todo: give cards different priority and add right amount of cards
        for (int i = 0; i < 10; i++) currentDeck.add(new Card(100, "move_1", 1, new Texture(Gdx.files.internal("Cards/move_1.png"))));
        for (int i = 0; i < 10; i++) currentDeck.add(new Card(100, "move_2", 2, new Texture(Gdx.files.internal("Cards/move_2.png"))));
        for (int i = 0; i < 10; i++) currentDeck.add(new Card(100, "move_3",3, new Texture(Gdx.files.internal("Cards/move_3.png"))));
        for (int i = 0; i < 10; i++) currentDeck.add(new Card(100, "move_-1",-1, new Texture(Gdx.files.internal("Cards/move_-1.png"))));
        // todo: add rotation cards
        Collections.shuffle(currentDeck);
        }

    /**
     * Deal cards to players
     * @param handSize
     * @return
     */
    public ArrayList<Card> deal(int handSize) {
        ArrayList<Card> hand = new ArrayList<>();
        Random random = new Random();
        for(int i=0; i<handSize; i++){
            hand.add(currentDeck.remove(random.nextInt(currentDeck.size())));
        }
        return hand;
    }

    public void remove(Card card) {
        currentDeck.remove(card);
    }
}