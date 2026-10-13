package fr.univartois.butinfo.qdev.spaceinvaders.model.animated;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;

public class Alien extends AbstractAnimated {

	public Alien(SpaceInvadersGame game, double xPosition, double yPosition, Sprite sprite) {
	    super(game, xPosition, yPosition, sprite);
	    setHorizontalSpeed(75);
	    setVerticalSpeed(1);
	}
	
	@Override
	public boolean onStep(long timeDelta) {
		return super.onStep(timeDelta);
	}
	
	@Override
	public void onCollisionWith(IAnimated other) {
		//TODO
	}
	
}
