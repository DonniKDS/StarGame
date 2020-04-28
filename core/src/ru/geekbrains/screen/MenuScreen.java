package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture starShip;
    private Vector2 startPos;
    private Vector2 stopPos;
    private Vector2 v;
    private Vector2 distance;
    private int speed;

    public MenuScreen() {
        super();
        starShip = new Texture("starship.png");
        startPos = new Vector2(Gdx.graphics.getBackBufferWidth() >> 1, Gdx.graphics.getBackBufferHeight() >> 1);
        stopPos = new Vector2(startPos);
        v = new Vector2();
        distance = new Vector2();
        speed = 5;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        moveShipToStopPosition();
        batch.begin();
        batch.draw(starShip, startPos.x, startPos.y);
        batch.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        stopPos.set(screenX, Gdx.graphics.getBackBufferHeight() - screenY);
        distance.set(startPos);
        v = startPos.cpy().sub(stopPos);
        v.nor().scl(speed);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    private void moveShipToStopPosition(){
        distance.sub(stopPos);
        if (distance.len() >= v.len()){
            startPos.sub(v);
            distance.set(startPos);
        } else {
            startPos.set(stopPos);
            v.set(0,0);
        }
    }
}