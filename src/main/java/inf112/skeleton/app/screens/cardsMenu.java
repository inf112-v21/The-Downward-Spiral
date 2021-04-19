package inf112.skeleton.app.screens;


import com.badlogic.gdx.graphics.g2d.BitmapFont;
import inf112.skeleton.app.GUI.Button;
import inf112.skeleton.app.ProgramCards.Card;

import java.util.ArrayList;

import static inf112.skeleton.app.screens.GameScreen.hud;
import static inf112.skeleton.app.screens.GameScreen.localPlayer;

public class cardsMenu {
    static RoboRallyGame game;
    static ArrayList<Card> test;
    static ArrayList<Button> buttonTest;
    static ArrayList<Button> buttonTestPicked;
    static ArrayList<Button> buttonTestAdd;
    static ArrayList<Button> buttonTestRemove;
    public static BitmapFont font;
    private int scaleCardInt = 75;

    public cardsMenu(RoboRallyGame game){
        this.game = game;
        test = new ArrayList<>();
        buttonTest = new ArrayList<>();
        buttonTestPicked = new ArrayList<>();
        buttonTestAdd = new ArrayList<>();
        buttonTestRemove = new ArrayList<>();
        font = new BitmapFont();
    }

    public static void setSelectableCards() {
        buttonTest.clear();
        buttonTestPicked.clear();
        if (localPlayer.selectableCards != null) {

            int i = 1;
            int k = 1;
            for (Card card : localPlayer.selectableCards) {
                //System.out.println(card);
                if (i < 6) {
                    buttonTest.add(new Button(card.getActivePath(), card.getInactivePath(), 70, 80, 670, 580+(hud.scaleCardInt * i)));
                    i += 1;
                } else {
                    buttonTest.add(new Button(card.getActivePath(), card.getInactivePath(), 70, 80, 570, 615+(hud.scaleCardInt * k)));
                    k += 1;
                }
            }
        }

    }

    public void touchCardUp(int x, int y) {
        //System.out.println("x: " + x + ", y: " + (800-y));

        for (Button button : buttonTest){
            int buttonX = button.getX();
            int buttonY = button.getY();
            int buttonWidth = button.getWIDTH();
            int buttonHeight = button.getHEIGHT();


            //TODO NEED WORK, SOMETHING IS BUGGY
            if (((x > buttonX-(buttonWidth/2)) && (x < buttonX + buttonWidth/2)) && ((game.getHEIGHT()-y > buttonY) && (game.getHEIGHT()-y < buttonY+buttonHeight))){
                //System.out.println(buttonX + "<-X: :Y-> " +buttonY);
                //System.out.println(button);
                for (Card card : localPlayer.selectableCards) {
                    if (button.getInactive().toString().equals(card.getInactivePath())) {
                        if (buttonTestPicked.size() < 5) {
                                buttonTestPicked.add(new Button(button.getActive().toString(), button.getInactive().toString(),
                                        buttonWidth, buttonHeight, (buttonY < 600 ? buttonY - 200  :  buttonY - 300 ),
                                        580 + scaleCardInt + (hud.scaleCardInt * buttonTestPicked.size())));
                                buttonTestRemove.add(button);
                                localPlayer.chooseCard(card);
                            break;
                        }
                    }
                }
            }
        }
        // This was a simple way to remove item from a list without errors in the for-loop.
        buttonTest.addAll(buttonTestAdd);
        buttonTest.removeAll(buttonTestRemove);
        buttonTestRemove.clear();
    }

    public void renderCard() {
        game.batch.begin();
        font.draw(game.batch, "Pick Cards", 610, 780);
        font.draw(game.batch, "Your picked cards", 610, 520);
        for (Button button: buttonTest){
            button.buttonHover(game);
        }
        for (Button but: buttonTestPicked){
            but.buttonHover(game);
        }
        game.batch.end();
    }
}
