package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Starship;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Texture ss;
    private Background background;
    private Starship starship;

    @Override
    public void show() {
        super.show();
        bg = new Texture("background.jpg");
        ss = new Texture("starship.png");
        background = new Background(bg);
        starship = new Starship(ss);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        starship.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        background.draw(batch);
        starship.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        ss.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        starship.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        starship.touchDragged(touch, pointer);
        return super.touchDragged(touch, pointer);
    }
}
