package fr.univartois.butinfo.qdev.spaceinvaders.model.animated;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;

public class Shot extends AbstractAnimated {
    protected double verticalSpeed;

    protected Shot(SpaceInvadersGame game, double xPosition, double yPosition, Sprite sprite) {
        super(game, xPosition, yPosition, sprite);
        this.verticalSpeed = -300;
    }

    @Override
    public boolean onStep(long delta) {
        if(!super.onStep(delta)){
            this.game.removeAnimated(this);
            return false;
        }
        return true;
    }

    @Override
    public void onCollisionWith(IAnimated other) {
        this.game.removeAnimated(this);
    }
}
