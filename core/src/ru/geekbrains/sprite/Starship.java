package ru.geekbrains.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;

public class Starship extends Sprite {

    private Vector2 stopPos;
    private Vector2 v;
    private Vector2 distance;

    private float speed;
    private float key;

    private Rect worldBounds;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV;
    private Vector2 bulletPos;
    private float bulletTimer;
    private float bulletInterval;

    public Starship(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship")
                .split(atlas.findRegion("main_ship").originalWidth/2, atlas.findRegion("main_ship").originalHeight)[0]);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletPos = new Vector2();
        stopPos = new Vector2();
        v = new Vector2();
        distance = new Vector2();
        speed = 0.006f;
    }

    @Override
    public void update(float delta) {
        bulletTimer += 0.08f;
        if (bulletTimer > bulletInterval) {
            shoot();
            bulletTimer = 0f;
        }
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
        bulletInterval = 1f;
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
        switch (keycode){
            case Input.Keys.LEFT:
            case Input.Keys.A:
                key = keycode;
                stopPos.set(worldBounds.getLeft(), pos.y);
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                key = keycode;
                stopPos.set(worldBounds.getRight(), pos.y);
                break;
            case Input.Keys.DOWN:
            case Input.Keys.S:
                key = keycode;
                stopPos.set(pos.x, worldBounds.getBottom());
                break;
            case Input.Keys.UP:
            case Input.Keys.W:
                key = keycode;
                stopPos.set(pos.x, worldBounds.getTop());
                break;
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

    private void shoot() {
        bulletPos.set(pos.x, getTop());
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, 0.01f, worldBounds, 1);
    }
}
