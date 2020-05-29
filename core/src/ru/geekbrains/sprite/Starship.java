package ru.geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Ship;
import ru.geekbrains.math.Rect;
import ru.geekbrains.pool.BulletPool;
import ru.geekbrains.pool.ExplosionPool;

public class Starship extends Ship {

    private static final float SIZE = 0.15f;
    private static final int HP = 100;

    private Vector2 stopPos;

    private float speed;
    private float key;

    public Starship(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.01f;
        damage = 1;
        reloadInterval = 0.25f;
        reloadTimer = reloadInterval;
        stopPos = new Vector2();
        sound = Gdx.audio.newSound(Gdx.files.internal("music/bulletSound.mp3"));
        startNewGame();
    }

    public void startNewGame() {
        hp = HP;
        speed = 0.006f;
        this.pos.set(0, -0.3f);
        stopPos.set(pos);
        flushDestroy();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        bulletPos.set(pos.x, pos.y + getHalfHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        moveShipToStopPosition();
        super.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(SIZE);
        this.pos.set(0, -0.3f);
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

    @Override
    public void dispose() {
        sound.dispose();
    }

    @Override
    protected void shoot() {
        super.shoot();
    }

    private void moveShipToStopPosition(){
        v0.sub(stopPos);
        if (v0.len() >= v.len()){
            pos.sub(v);
            v0.set(pos);
        } else {
            pos.set(stopPos);
            v.setZero();
        }
    }

    private void setStopPos(Vector2 touch){
        stopPos.set(touch);
        v0.set(pos);
        v.set(pos.cpy().sub(stopPos));
        v.nor().scl(speed);
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }
}
