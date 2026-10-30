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
		if (!moved) {
			setHorizontalSpeed(-1 * getHorizontalSpeed());
			setVerticalSpeed(1.02 * getVerticalSpeed());
		}
		if ((getY() + getHeight()) >= game.getBottomLimit()) {
			game.alienReachedPlanet();
		}
		return moved;
	}

	@Override
	public void onCollisionWith(IAnimated other) {
		other.collidedWithAlien(this);
	}

	@Override
	public void collidedWithShot(Shot shot) {
		if (isDestroyed() || shot.isDestroyed()) {
			return;
		}
		game.alienIsDead(this);
		game.removeAnimated(this);
		game.removeAnimated(shot);
	}
}
