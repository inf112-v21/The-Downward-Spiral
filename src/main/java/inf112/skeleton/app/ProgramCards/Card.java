package inf112.skeleton.app.ProgramCards;
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

    public String getInactivePath(){
        return "Cards/" + type + ".png";
    }

    public String getActivePath(){
        return "Cards/" + type + "_active.png";
    }

    public int getMoves() {
        return moves;
    }


}
