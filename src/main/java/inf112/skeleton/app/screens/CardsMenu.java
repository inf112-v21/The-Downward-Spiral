package inf112.skeleton.app.screens;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.ProgramCards.Card;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static inf112.skeleton.app.screens.GameScreen.*;

public class CardsMenu {
    static RoboRallyGame game;
    static ArrayList<Card> test;
    static ArrayList<Button> cardButtons;
    static ArrayList<Button> cardButtonsPicked;
    static ArrayList<Button> cardButtonsAdd;
    static ArrayList<Button> cardButtonRemove;
    static HashMap<String,Texture> cardTextures;
    static Button resetButton;
    static Button executeButton;
    Texture cardMenuBackground;
    public static BitmapFont font;
    private int scaleCardInt = 75;

    public CardsMenu(RoboRallyGame game){
        this.game = game;
        test = new ArrayList<>();
        cardButtons = new ArrayList<>();
        cardButtonsPicked = new ArrayList<>();
        cardButtonsAdd = new ArrayList<>();
        cardButtonRemove = new ArrayList<>();
        cardTextures = new HashMap<>();


        File dir = new File("./assets/Cards");
        for (String file : Objects.requireNonNull(dir.list())) {
            cardTextures.put(file, new Texture("Cards/" + file));
        }

        cardMenuBackground = new Texture("CardsMenu/cardsMenu.png");
        resetButton = new Button(new Texture("CardsMenu/buttonActiveReset.png"),
                new Texture("CardsMenu/buttonInactiveReset.png"), 250, 103, 0, 900);
        executeButton = new Button(new Texture("CardsMenu/buttonActiveExecute.png"),
                new Texture("CardsMenu/buttonInactiveExecute.png"), 250, 103, 0, 700);
        font = new BitmapFont();
    }

    public static void setSelectableCards() {
        System.out.println("Cards: ?" + localPlayer.selectableCards);
        cardButtons.clear();
        cardButtonsPicked.clear();
        if (localPlayer.selectableCards != null) {

            int i = 1;
            int k = 1;
            for (Card card : localPlayer.selectableCards) {
                //System.out.println(card);
                if (i < 6) {
                    cardButtons.add(new Button(cardTextures.get(card.getActivePath()), cardTextures.get(card.getInactivePath()), 70, 80, 670, 580+(hud.scaleCardInt * i)));
                    i += 1;
                } else {
                    cardButtons.add(new Button(cardTextures.get(card.getActivePath()), cardTextures.get(card.getInactivePath()), 70, 80, 570, 615+(hud.scaleCardInt * k)));
                    k += 1;
                }
            }
        }

    }

    public void touchCardUp(int x, int y) {

        if (((x > resetButton.getX()-(resetButton.getWIDTH()/2)) && (x < resetButton.getX() + resetButton.getWIDTH()/2))
                && ((game.getHEIGHT()-y > resetButton.getY()) && (game.getHEIGHT()-y < resetButton.getY()+ resetButton.getHEIGHT()))){
            localPlayer.chosenCards.clear();
            setSelectableCards();
        }
        if (((x > executeButton.getX()-(executeButton.getWIDTH()/2)) && (x < executeButton.getX() + executeButton.getWIDTH()/2))
                && ((game.getHEIGHT()-y > executeButton.getY()) && (game.getHEIGHT()-y < executeButton.getY()+ executeButton.getHEIGHT()))){
            if (localPlayer.chosenCards != null) {
                final int cardSize = localPlayer.chosenCards.size();
                    // Sends hand to server
                System.out.println(localPlayer.chosenCards);
                    GameScreen.networkConnection.sendHand(localPlayer.chosenCards);
            }
        }

        for (Button button : cardButtons){
            int buttonX = button.getX();
            int buttonY = button.getY();
            int buttonWidth = button.getWIDTH();
            int buttonHeight = button.getHEIGHT();


            if (((x > buttonX-(buttonWidth/2)) && (x < buttonX + buttonWidth/2))
                    && ((game.getHEIGHT()-y > buttonY) && (game.getHEIGHT()-y < buttonY+buttonHeight))){
                for (Card card : localPlayer.selectableCards) {
                    if (button.getInactive().toString().equals("Cards/" + card.getInactivePath())) {
                        if (cardButtonsPicked.size() < 5) {
                                cardButtonsPicked.add(new Button(button.getActive(), button.getInactive(),
                                        buttonWidth, buttonHeight, (buttonY < 600 ? buttonY - 200  :  buttonY - 300 ),
                                        580 + scaleCardInt + (hud.scaleCardInt * cardButtonsPicked.size())));
                                cardButtonRemove.add(button);
                                localPlayer.chooseCard(card);
                            break;
                        }
                    }
                }
            }
        }
        // This was a simple way to remove item from a list without errors in the for-loop.
        cardButtons.removeAll(cardButtonRemove);
        cardButtonRemove.clear();
    }

    public void renderCard() {
        game.batch.begin();
        game.batch.draw(cardMenuBackground,600,0);
        resetButton.buttonHover(game);
        executeButton.buttonHover(game);
        for (Button button: cardButtons){
            button.buttonHover(game);
        }
        for (Button but: cardButtonsPicked){
            but.buttonHover(game);
        }
        game.batch.end();
    }
}
