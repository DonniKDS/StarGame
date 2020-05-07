package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;

import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Starship;

public class MenuScreen extends BaseScreen {

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
}