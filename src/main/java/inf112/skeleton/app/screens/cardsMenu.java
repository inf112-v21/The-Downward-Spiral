package inf112.skeleton.app.screens;


import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.ProgramCards.Card;

import java.util.ArrayList;

public class cardsMenu extends ScreenAdapter  {
    static RoboRallyGame game;
    static Texture hud;
    static Button button;
    static ArrayList<Card> test;

    public cardsMenu(RoboRallyGame game){
        this.game = game;
        test = new ArrayList<>();
    }

    public static void renderHud(ArrayList<Card> cards) {
        if (test != cards){
            if (cards != null) {
                test.addAll(cards);
                renderCard(cards);
            }
        }
    }
    public static void renderCard(ArrayList<Card> cards) {

        game.batch.begin();
        int i = 1;
        for (Card card : cards) {
            System.out.println(card);
            game.getBatch().draw(new Texture(card.getPath()), 800, 50*i, 100, 100);
            i += 1;
        }

        game.batch.end();
    }
}
