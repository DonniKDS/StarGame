package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Starship extends Sprite {

    private Vector2 stopPos;
    private Vector2 v;
    private Vector2 distance;
    private float speed;

    public Starship(Texture texture) {
        super(new TextureRegion(texture));
        stopPos = new Vector2();
        v = new Vector2();
        distance = new Vector2();
        speed = 0.006f;
    }

    @Override
    public void update(float delta) {
        moveShipToStopPosition();
    }

    @Override
    public void draw(SpriteBatch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        createStopVector(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        createStopVector(touch);
        return super.touchDragged(touch, pointer);
    }

    private void moveShipToStopPosition(){
        distance.sub(stopPos);
        if (distance.len() >= v.len()){
            pos.sub(v);
            distance.set(pos);
        } else {
            pos.set(stopPos);
            v.setZero();
        }
    }

    private void createStopVector(Vector2 touch){
        stopPos.set(touch);
        distance.set(pos);
        v = pos.cpy().sub(stopPos);
        v.nor().scl(speed);
    }
}
