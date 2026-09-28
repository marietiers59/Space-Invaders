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

import fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.beans.property.IntegerProperty;

/**
 * L'interface {@link ISpaceInvadersController} définit le contrat à remplir par tout
 * contrôleur du jeu Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface ISpaceInvadersController {

    /**
     * Associe à ce contrôleur la partie du jeu Space-Invaders en cours.
     *
     * @param game La partie en cours.
     */
    void setGame(SpaceInvadersGame game);

    /**
     * Modifie l'instance de {@link ISpriteStore} fournissant les {@link Sprite}
     * pour les différents éléments du jeu.
     *
     * @param spriteStore L'instance de {@link ISpriteStore} à utiliser.
     */
    void setSpriteStore(ISpriteStore spriteStore);

    /**
     * Prépare l'affichage du jeu avant que celui-ci ne démarre.
     */
    void prepare();

    /**
     * Lie le score du joueur à son affichage dans la vue.
     *
     * @param scoreProperty La propriété stockant le score du joueur.
     */
    void bindScore(IntegerProperty scoreProperty);

    /**
     * Lie la vie du joueur à son affichage dans la vue.
     *
     * @param lifeProperty La propriété stockant la vie du joueur.
     */
    void bindLife(IntegerProperty lifeProperty);

    /**
     * Ajoute un objet pouvant se déplacer dans le jeu, afin de pouvoir l'afficher.
     *
     * @param movable L'objet à aficher.
     */
    void addMovable(IMovable movable);

    /**
     * Affiche un message lorsque la partie est terminée.
     *
     * @param endMessage Le message à afficher.
     */
    void gameOver(String endMessage);

    /**
     * Réinitialise l'affichage, afin de préparer une nouvelle partie.
     */
    void reset();

}
