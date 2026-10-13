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
		boolean moved = super.onStep(timeDelta);
		if(!moved) {
			setHorizontalSpeed(-1*getHorizontalSpeed());
		    setVerticalSpeed(1.02*getVerticalSpeed());
		}
		return moved;
	}
	
	@Override
	public void onCollisionWith(IAnimated other) {
		// Pour l'instant, une collision n'a aucun effet sur l'alien.
	}
	
}
