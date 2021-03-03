package inf112.skeleton.app.ProgramCards;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;


public class Card extends Actor {
    private final int priority;
    private final String type;
    private final int moves;
    private final Texture texture;

    ArrayList<Card> cards;

    public Card(int priority, String type, int moves, Texture texture) {
        this.priority = priority;
        this.type = type;
        this.moves = moves;
        this.texture = texture;
    }

    public void setTexture(Texture texture) {
    }
    public String toString() {
        return type;
    }


    public void add(int priority, String type) {
        cards.add(new Card(priority, type, moves, texture));
    }

    public int getMoves() {
        return moves;
    }

    public Texture getTexture() {
        return texture;
    }

}
