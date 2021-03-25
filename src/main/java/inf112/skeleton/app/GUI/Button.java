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

    public Button (String activeLoc, String inactiveLoc, int WIDTH, int HEIGHT, int Y){
        this.active = new Texture(activeLoc);
        this.inactive = new Texture(inactiveLoc);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.Y = Y;
    }

    public void buttonHover(RoboRallyGame game){
        int x = 600/2 - getWIDTH() / 2;
        int y = getY();
        if (Gdx.input.getX() < x + getWIDTH() && Gdx.input.getX() > x && 800 - Gdx.input.getY() < y + getHEIGHT() && 800 - Gdx.input.getY() > y) {
            game.getBatch().draw(getActive(), x, y, getWIDTH(), getHEIGHT());
        }else{
            game.getBatch().draw(getInactive(), x, y, getWIDTH(), getHEIGHT());
        }
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
}
