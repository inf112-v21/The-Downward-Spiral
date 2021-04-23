package inf112.skeleton.app.screens;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.ProgramCards.Card;
import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class CardsMenu {
    private static RoboRallyGame game;
    private static ArrayList<Button> cardButtons;
    private static ArrayList<Button> cardButtonsPicked;
    private static ArrayList<Button> cardButtonRemove;
    private static HashMap<String,Texture> cardTextures;
    private static Button resetButton;
    private static Button executeButton;
    private final Texture cardMenuBackground;
    public static BitmapFont font;
    private final int scaleCardInt = 75;
    private final int buttonStart = 575;
    private final Texture damageTray;
    private final Texture lifeTray;
    private final Texture damageToken;
    private final Texture lifeToken;
    private final Texture lockedScreen;
    private final Texture lockedButton;

    private boolean cardsIsSent;

    public CardsMenu(RoboRallyGame game){
        this.game = game;
        cardsIsSent = false;
        cardButtons = new ArrayList<>();
        cardButtonsPicked = new ArrayList<>();
        cardButtonRemove = new ArrayList<>();
        cardTextures = new HashMap<>();

        damageTray = new Texture("CardsMenu/damageTray.png");
        lifeTray = new Texture("CardsMenu/lifeTray.png");

        damageToken = new Texture("CardsMenu/damageToken.png");
        lifeToken = new Texture("CardsMenu/lifeToken.png");

        lockedScreen = new Texture("CardsMenu/locked.png");
        lockedButton = new Texture("CardsMenu/lockedButton.png");


        File dir = new File("./assets/Cards");
        for (String file : Objects.requireNonNull(dir.list())) {
            cardTextures.put(file, new Texture("Cards/" + file));
        }

        cardMenuBackground = new Texture("CardsMenu/cardsMenu.png");
        resetButton = new Button(new Texture("CardsMenu/buttonActiveReset.png"),
                new Texture("CardsMenu/buttonInactiveReset.png"), 150, 63, 20, 900);
        executeButton = new Button(new Texture("CardsMenu/buttonActiveExecute.png"),
                new Texture("CardsMenu/buttonInactiveExecute.png"), 150, 63, 20, 700);
        font = new BitmapFont();
    }

    public static void setSelectableCards() {
        System.out.println("Cards: ?" + GameScreen.localPlayer.selectableCards);
        cardButtons.clear();
        cardButtonsPicked.clear();
        if (GameScreen.localPlayer.selectableCards != null) {

            int i = 1;
            int k = 1;
            for (Card card : GameScreen.localPlayer.selectableCards) {
                if (i < 6) {
                    cardButtons.add(new Button(cardTextures.get(card.getActivePath()), cardTextures.get(card.getInactivePath()), 70, 80, 670, GameScreen.hud.buttonStart+(GameScreen.hud.scaleCardInt * i)));
                    i += 1;
                } else {
                    cardButtons.add(new Button(cardTextures.get(card.getActivePath()), cardTextures.get(card.getInactivePath()), 70, 80, 570, 610+(GameScreen.hud.scaleCardInt * k)));
                    k += 1;
                }
            }
        }

    }

    // Gets input from InputAdapter
    public void touchCardUp(int x, int y) {

        // Reset Button
        if (Button.onClick(game, resetButton, x, y) && !cardsIsSent){
            GameScreen.localPlayer.chosenCards.clear();
            setSelectableCards();
        }

        // Execute button
        if (Button.onClick(game, executeButton, x, y) && GameScreen.localPlayer.chosenCards != null
                && GameScreen.localPlayer.chosenCards.size() == 5 && !cardsIsSent){
                // Sends hand to server if possible
            cardsIsSent = true;
            System.out.println(GameScreen.localPlayer.chosenCards);
                GameScreen.networkConnection.sendHand(GameScreen.localPlayer.chosenCards);
        }

        //
        for (Button button : cardButtons){
            int buttonY = button.getY();
            int buttonWidth = button.getWIDTH();
            int buttonHeight = button.getHEIGHT();


            if (Button.onClick(game, button, x, y)){
                for (Card card : GameScreen.localPlayer.selectableCards) {
                    if (button.getInactive().toString().equals("Cards/" + card.getInactivePath()) && cardButtonsPicked.size() < 5) {
                            cardButtonsPicked.add(new Button(button.getActive(), button.getInactive(),
                                        buttonWidth, buttonHeight, (buttonY < 600 ? buttonY - 150  :  buttonY - 250 ),
                                    GameScreen.hud.buttonStart + GameScreen.hud.scaleCardInt + (GameScreen.hud.scaleCardInt * cardButtonsPicked.size())));
                                cardButtonRemove.add(button);
                                GameScreen.localPlayer.chooseCard(card);
                            break;
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

        // Render damage tokens
        game.batch.draw(damageTray, 625, 200, 350, 50);
        for (int i = 0; i < GameScreen.localPlayer.getDamageTokens(); i++){
            game.batch.draw(damageToken, 643+(32*i), 210, 29, 29);
        }

        // Render life tokens
        game.batch.draw(lifeTray, 735, 290, 130, 50);
        for (int i = 0; i < GameScreen.localPlayer.getLifeTokens(); i++){
            game.batch.draw(lifeToken, 753+(33*i), 300, 29, 29);
        }

        for (Button button: cardButtons){
            button.buttonHover(game);
        }
        for (Button but: cardButtonsPicked){
            but.buttonHover(game);
        }
        if (cardsIsSent){
            game.batch.draw(lockedScreen, 610, 410);
            game.batch.draw(lockedButton, 625, 20, 150, 63);
            game.batch.draw(lockedButton, 825, 20, 150, 63);

        }else{
            resetButton.buttonHover(game);
            executeButton.buttonHover(game);
        }
        game.batch.end();
    }

    public void setCardsIsSent(boolean cardsIsSent) {
        this.cardsIsSent = cardsIsSent;
    }
}
