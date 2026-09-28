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

import javafx.beans.binding.ObjectBinding;
import javafx.scene.image.Image;

/**
 * Un Sprite fournit une représentation graphique pour les éléments du jeu.
 * Il s'agit d'un objet encapsulant une ou plusieurs image(s), de manière à pouvoir placer
 * facilement l'élément dans une scène JavaFX, sans avoir à se soucier de la gestion
 * de sa représentation, et en particulier de son animation.
 *
 * Un Sprite peut être partagé entre plusieurs éléments du jeu, lorsqu'ils partagent la
 * même représentation graphique.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public abstract class Sprite {

    /**
     * La propriété contenant l'image (courante) de ce Sprite.
     */
    private ObjectBinding<Image> imageProperty;

    /**
     * Crée une nouvelle instance de Sprite.
     *
     * @param imageProperty La propriété contenant l'image du Sprite.
     */
    protected Sprite(ObjectBinding<Image> imageProperty) {
        this.imageProperty = imageProperty;
    }

    /**
     * Donne la largeur de l'image (courante) de ce Sprite, mesurée en nombre de pixels.
     *
     * @return La largeur de l'image.
     */
    public final int getWidth() {
        return (int) imageProperty.getValue().getWidth();
    }

    /**
     * Donne la hauteur de l'image (courante) de ce Sprite, mesurée en nombre de pixels.
     *
     * @return La hauteur de l'image.
     */
    public final int getHeight() {
        return (int) imageProperty.getValue().getHeight();
    }

    /**
     * Donne la propriété contenant l'image (ou l'image courante, dans le cas d'une
     * animation) de ce Sprite.
     *
     * @return La propriété contenant l'image de ce Sprite.
     */
    public final ObjectBinding<Image> imageProperty() {
        return imageProperty;
    }

    /**
     * Détruit les ressources associées à ce Sprite.
     */
    public abstract void destroy();

}
