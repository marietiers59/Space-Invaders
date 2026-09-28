/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.spaceinvaders.model;

import java.util.List;

import javafx.animation.AnimationTimer;

/**
 * La classe {@link SpaceInvadersAnimation} implante l'animation permettant de déplacer
 * les différents objets animés du jeu Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
final class SpaceInvadersAnimation extends AnimationTimer {
    
    /**
     * Le délai minimum entre deux dépôts de bonus.
     */
    private static final long BONUS_DELAY = 20_000;
    
    /**
     * La partie de Space-Invaders en cours.
     */
    private final SpaceInvadersGame game;

    /**
     * La liste des objets pouvant se déplacer dans le jeu.
     */
    private final List<IMovable> movableObjects;

    /**
     * Le timestamp de la dernière mise à jour des différentes position des objets.
     */
    private long previousTimestamp;

    /**
     * Le timestamp du dernier dépôt de bonus.
     */
    private long previousBonus;

    /**
     * Crée une nouvelle instance de SpaceInvadersAnimation.
     *
     * @param game La partie de Space-Invaders en cours.
     * @param movableObjects La liste des objets pouvant se déplacer dans le jeu.
     */
    public SpaceInvadersAnimation(SpaceInvadersGame game, List<IMovable> movableObjects) {
        this.game = game;
        this.movableObjects = movableObjects;
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.animation.AnimationTimer#start()
     */
    @Override
    public void start() {
        previousTimestamp = -1;
        previousBonus = -1;
        super.start();
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.animation.AnimationTimer#handle(long)
     */
    @Override
    public void handle(long now) {
        // Lors de la première mise à jour, on se contente de conserver le timestamp.
        if (previousTimestamp < 0) {
            previousTimestamp = now;
            previousBonus = now;
            return;
        }

        // On détermine le temps écoulé depuis la dernière mise à jour.
        long delta = (now - previousTimestamp) / 1000000;
        previousTimestamp = now;

        // On met à jour la position des objets.
        moveObjects(delta);
        checkCollisions();
        dropBonus(now);
    }

    /**
     * Met à jour la position des différents objets du jeu.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour.
     */
    private void moveObjects(long delta) {
        for (IMovable movable : movableObjects) {
            movable.move(delta);
        }
    }

    /**
     * Vérifie si, au cours du dernier déplacement, des objets sont entrés en collision.
     */
    private void checkCollisions() {
        for (IMovable movable : movableObjects) {
            for (IMovable other : movableObjects) {
                if ((movable != other) && movable.isCollidingWith(other)) {
                    // On informe les deux objets qu'ils sont entrés en collision.
                    movable.collidedWith(other);
                    other.collidedWith(movable);
                }
            }
        }
    }

    /**
     * Dépose un bonus dans le jeu, si le délai minimum est écoulé.
     *
     * @param now L'heure actuelle.
     */
    private void dropBonus(long now) {
        long delay = (now - previousBonus) / 1000000;
        if (delay >= BONUS_DELAY) {
            game.dropBonus();
            previousBonus = now;
        }
    }

}
