package inf112.skeleton.app.ProgramCards;

import java.util.ArrayList;


public class Card {
    private final int priority;
    private final String type;
    private final int moves;

    ArrayList<Card> cards;

    public Card() {
        this.priority = 0;
        this.type = "0";
        this.moves = 0;
    }

    public Card(int priority, String type, int moves) {
        this.priority = priority;
        this.type = type;
        this.moves = moves;
    }

    public String toString() {
        return type;
    }

    public int getMoves() {
        return moves;
    }


}
