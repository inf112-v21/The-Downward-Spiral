package inf112.skeleton.app.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.screens.RoboRallyGame;

public class Button {

    Texture active;
    Texture inactive;
    private int WIDTH;
    private int HEIGHT;
    private int Y;
    private int X;

    public Button (Texture active, Texture inactive, int WIDTH, int HEIGHT, int Y, int X){
        this.active = active;
        this.inactive = inactive;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.Y = Y;
        this.X = X;
    }

    public void buttonHover(RoboRallyGame game){
        int x = getX() - getWIDTH() / 2;
        int y = getY();
        if (Gdx.input.getX() < x + getWIDTH() && Gdx.input.getX() > x && 800 - Gdx.input.getY() < y + getHEIGHT() && 800 - Gdx.input.getY() > y) {
            game.getBatch().draw(getActive(), x, y, getWIDTH(), getHEIGHT());
        }else{
            game.getBatch().draw(getInactive(), x, y, getWIDTH(), getHEIGHT());
        }
    }

    public static boolean onClick(RoboRallyGame game, Button button, int x, int y) {
         return (((x > button.getX() - (button.getWIDTH() / 2)) && (x < button.getX() + button.getWIDTH() / 2)) && ((game.getHEIGHT() - y > button.getY()) && (game.getHEIGHT() - y < button.getY() + button.getHEIGHT())));
    }

    public Texture getInactive() {
        return inactive;
    }

    public Texture getActive() {
        return active;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getY() {
        return Y;
    }

    public int getX() {
        return X;
    }
}
