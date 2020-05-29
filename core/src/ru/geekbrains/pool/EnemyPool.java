package ru.geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.base.SpritesPool;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Rect worldBounds;
    private Sound sound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        sound = Gdx.audio.newSound(Gdx.files.internal("music/bullet.wav"));
    }

    @Override
    protected Enemy newObjects() {
        return new Enemy(bulletPool, explosionPool, worldBounds, sound);
    }

    @Override
    public void dispose() {
        super.dispose();
        sound.dispose();
    }
}
