package fr.univartois.butinfo.qdev.spaceinvaders.model.animated;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;

public class PlayerShip extends AbstractAnimated{

	public PlayerShip(SpaceInvadersGame game, double x, double y, Sprite sprite) {
        super(game, x, y, sprite);
    }

    @Override
    public void onCollisionWith(IAnimated other) {

    }

}
