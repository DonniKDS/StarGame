package ru.geekbrains.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.BaseButton;
import ru.geekbrains.math.Rect;
import ru.geekbrains.screen.GameScreen;

public class ButtonPlay extends BaseButton {

    private final Game game;

    private static final float MARGIN = 0.35f;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("play"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setBottom(worldBounds.getTop() - MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
