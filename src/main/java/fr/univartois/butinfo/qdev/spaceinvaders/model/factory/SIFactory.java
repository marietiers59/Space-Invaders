package fr.univartois.butinfo.qdev.spaceinvaders.model.factory;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.model.animated.Alien;
import fr.univartois.butinfo.qdev.spaceinvaders.model.animated.PlayerShip;
import fr.univartois.butinfo.qdev.spaceinvaders.model.animated.Shot;
import fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore;

public class SIFactory implements IAnimatedFactory {
    private ISpriteStore spriteStore;

    private SpaceInvadersGame game;

    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    @Override
    public void setGame(SpaceInvadersGame game) {
        this.game = game;
    }

    @Override
    public IAnimated createAlien(int x, int y) {
        return new Alien(game, x, y, spriteStore.getSprite("alien"));
    }

    @Override
    public IAnimated createShip(int x, int y) {
        return new PlayerShip(game, x, y, spriteStore.getSprite("ship"));
    }

    @Override
    public IAnimated createShot(int x, int y) {
        return new Shot(game, x, y, spriteStore.getSprite("shot"));
    }
}
