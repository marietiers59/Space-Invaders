/*
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d'aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d'adéquation
 * à un usage particulier et d'absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d'auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d'un contrat, d'un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d'autres éléments du logiciel.
 *
 * (c) 2022-2026 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.qdev.spaceinvaders.model;

import java.util.List;

import javafx.animation.AnimationTimer;

/**
 * La classe GameAnimation implémente l'animation principale du jeu, permettant
 * de modifier l'apparence et/ou la position des objets animés du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
final class GameAnimation extends AnimationTimer {

    /**
     * La liste des objets mobiles dans le jeu.
     */
    private final List<IAnimated> movingObjects;

    /**
     * La liste des objets animés dans le jeu.
     */
    private final List<IAnimated> animatedObjects;

    /**
     * Le timestamp de la dernière mise à jour des différents objets.
     */
    private long previousTimestamp;

    /**
     * Crée une nouvelle instance de GameAnimation.
     *
     * @param movingObjects La liste des objets mobiles dans le jeu.
     * @param animatedObjects La liste des objets animés dans le jeu.
     */
    public GameAnimation(List<IAnimated> movingObjects, List<IAnimated> animatedObjects) {
        this.movingObjects = movingObjects;
        this.animatedObjects = animatedObjects;
    }

    /*
     * (non-Javadoc)
     *
     * @see javafx.animation.AnimationTimer#start()
     */
    @Override
    public void start() {
        previousTimestamp = -1;
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
            return;
        }

        // On détermine le temps écoulé depuis la dernière mise à jour.
        long delta = (now - previousTimestamp) / 1000000;
        previousTimestamp = now;

        // On vérifie l'état actuel des objets.
        updateObjects(delta);
        checkCollisions();
    }

    /**
     * Met à jour l'état des différents objets du jeu.
     *
     * @param delta Le temps écoulé depuis la dernière mise à jour (en millisecondes).
     */
    private void updateObjects(long delta) {
        for (var movable : animatedObjects) {
            movable.onStep(delta);
        }
    }

    /**
     * Vérifie si, au cours du dernier changement d'état, des objets sont entrés en
     * collision.
     */
    private void checkCollisions() {
        for (var moving : movingObjects) {
            for (var animated : animatedObjects) {
                if ((moving != animated) && moving.isCollidingWith(animated)) {
                    // On informe les deux objets qu'ils sont entrés en collision.
                    moving.onCollisionWith(animated);
                    animated.onCollisionWith(moving);
                }
            }
        }
    }

}
