package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Star;
import ru.geekbrains.sprite.Starship;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Texture ss;
    private Background background;
    private Starship starship;
    private TextureAtlas menuAtlas;
    private TextureAtlas mainAtlas;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        menuAtlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.pack"));
        mainAtlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        background = new Background(bg);
        starship = new Starship(mainAtlas);
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(menuAtlas);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        starship.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        bg.dispose();
        ss.dispose();
        menuAtlas.dispose();
        mainAtlas.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starship.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        starship.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        starship.touchDragged(touch, pointer);
        return super.touchDragged(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        starship.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        return super.keyUp(keycode);
    }

    private void update(float delta){
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw(){
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        starship.draw(batch);
        batch.end();
    }
}
