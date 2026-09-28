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

package fr.univartois.butinfo.qdev.spaceinvaders.view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.scene.image.Image;

/**
 * Un StaticSprite représente un {@link Sprite} dont l'image ne change pas au cours
 * du temps.
 * Cette classe permet une gestion plus efficace de l'animation des éléments statiques.
 * En particulier, un StaticSprite correspond typiquement à un sprite pouvant être partagé
 * entre différents éléments du jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
final class StaticSprite extends Sprite {

    /**
     * Crée une nouvelle instance de StaticSprite.
     *
     * @param imageProperty La propriété contenant l'image associée au sprite.
     */
    private StaticSprite(ObjectBinding<Image> imageProperty) {
        super(imageProperty);
    }

    /**
     * Crée une nouvelle instance de StaticSprite.
     *
     * @param image L'image associée au sprite.
     *
     * @return L'instance de StaticSprite créée.
     */
    public static Sprite newInstance(Image image) {
        return new StaticSprite(Bindings.createObjectBinding(() -> image));
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite#destroy()
     */
    @Override
    public void destroy() {
        // Rien à faire dans le cas d'un sprite fixe.
    }

}
