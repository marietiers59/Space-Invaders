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

import java.util.List;
import java.util.NoSuchElementException;

/**
 * L'interface ISpriteStore définit un contrat visant à permettre la création d'instances
 * de {@link Sprite} à partir de l'identifiant (ou des identifiants) de leur(s) image(s)
 * associée(s).
 * Typiquement, cet identifiant permet d'associer l'image d'un élément du jeu à
 * l'instance de {@link Sprite} qui sera créée, afin de pouvoir la réutiliser si celle-ci
 * le permet.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface ISpriteStore {

    /**
     * La taille par défaut des sprites (en pixels).
     */
    int DEFAULT_SPRITE_SIZE = 30;

    /**
     * La fréquence d'images par défaut pour les sprites animés (en images par seconde).
     */
    int DEFAULT_FRAME_RATE = 5;

    /**
     * Charge une instance de {@link Sprite} donnée par l'identifiant de son image.
     *
     * @param identifier L'identifiant de l'image du {@link Sprite} à charger.
     *
     * @return L'instance de {@link Sprite} correspondant à l'identifiant donné.
     *
     * @throws NoSuchElementException Si aucune instance de {@link Sprite} ne correspond à
     *         l'identifiant donné.
     */
    Sprite getSprite(String identifier);

    /**
     * Charge une instance de {@link Sprite} donnée par les identifiants de ses images.
     *
     * @param identifiers Les identifiants des images du {@link Sprite} à charger.
     *
     * @return L'instance de {@link Sprite} correspondant aux identifiants donnés.
     *
     * @throws NoSuchElementException Si aucune instance de {@link Sprite} ne correspond
     *         aux identifiants donné.
     */
    default Sprite getSprite(String... identifiers) {
        return getSprite(DEFAULT_FRAME_RATE, identifiers);
    }

    /**
     * Charge une instance de {@link Sprite} donnée par les identifiants de ses images.
     *
     * @param frameRate Le nombre d'images par seconde pour l'animation du {@link Sprite}.
     * @param identifiers Les identifiants des images du {@link Sprite} à charger.
     *
     * @return L'instance de {@link Sprite} correspondant aux identifiants donnés.
     *
     * @throws NoSuchElementException Si aucune instance de {@link Sprite} ne correspond
     *         aux identifiants donné.
     */
    default Sprite getSprite(int frameRate, String... identifiers) {
        return getSprite(frameRate, List.of(identifiers));
    }

    /**
     * Charge une instance de {@link Sprite} donnée par les identifiants de ses images.
     *
     * @param identifiers Les identifiants des images du {@link Sprite} à charger.
     *
     * @return L'instance de {@link Sprite} correspondant aux identifiants donnés.
     *
     * @throws NoSuchElementException Si aucune instance de {@link Sprite} ne correspond
     *         aux identifiants donné.
     */
    default Sprite getSprite(List<String> identifiers) {
        return getSprite(DEFAULT_FRAME_RATE, identifiers);
    }

    /**
     * Charge une instance de {@link Sprite} donnée par les identifiants de ses images.
     *
     * @param frameRate Le nombre d'images par seconde pour l'animation du {@link Sprite}.
     * @param identifiers Les identifiants des images du {@link Sprite} à charger.
     *
     * @return L'instance de {@link Sprite} correspondant aux identifiants donnés.
     *
     * @throws NoSuchElementException Si aucune instance de {@link Sprite} ne correspond
     *         aux identifiants donné.
     */
    Sprite getSprite(int frameRate, List<String> identifiers);

    /**
     * Donne la taille des sprites à charger.
     *
     * @return La taille des sprites (en pixels).
     */
    default int getSpriteSize() {
        return DEFAULT_SPRITE_SIZE;
    }

}
