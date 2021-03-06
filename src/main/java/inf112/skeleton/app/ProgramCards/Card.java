package inf112.skeleton.app.ProgramCards;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;


public class Card extends Actor {
    private final int priority;
    private final String type;
    private final int moves;

    ArrayList<Card> cards;

    public Card(int priority, String type, int moves) {
        this.priority = priority;
        this.type = type;
        this.moves = moves;
    }

    public String toString() {
        return type;
    }


    public void add(int priority, String type) {
        cards.add(new Card(priority, type, moves));
    }

    public int getMoves() {
        return moves;
    }


}
