package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Starship extends Sprite {

    private Vector2 stopPos;
    private Vector2 v;
    private Vector2 distance;
    private float speed;
    private Rect worldBounds;
    private float key;

    public Starship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship")
                .split(atlas.findRegion("main_ship").originalWidth/2, atlas.findRegion("main_ship").originalHeight)[0]);
        stopPos = new Vector2();
        v = new Vector2();
        distance = new Vector2();
        speed = 0.006f;
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        moveShipToStopPosition();
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        setStopPos(touch);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        setStopPos(touch);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        setStopPos(touch);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        key = keycode;
        if (key == Input.Keys.LEFT || key == Input.Keys.A) {
            stopPos.set(worldBounds.getLeft(), pos.y);
        }
        if (key == Input.Keys.RIGHT || key == Input.Keys.D) {
            stopPos.set(worldBounds.getRight(), pos.y);
        }
        if (key == Input.Keys.DOWN || key == Input.Keys.S) {
            stopPos.set(pos.x, worldBounds.getBottom());
        }
        if (key == Input.Keys.UP || key == Input.Keys.W) {
            stopPos.set(pos.x, worldBounds.getTop());
        }
        setStopPos(stopPos);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (key == keycode){
            setStopPos(pos);
        }
        return super.keyUp(keycode);
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

    private void setStopPos(Vector2 touch){
        stopPos.set(touch);
        distance.set(pos);
        v = pos.cpy().sub(stopPos);
        v.nor().scl(speed);
    }
}
