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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javafx.collections.FXCollections;
import javafx.scene.image.Image;

/**
 * La classe SpriteStore est une implémentation de {@link ISpriteStore} visant à
 * créer des instances de {@link Sprite}, tout en réutilisant celles qui peuvent
 * l'être lorsqu'elles ont déjà été chargées (en particulier, les instances ne
 * correspondant pas à des animations).
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class SpriteStore implements ISpriteStore {

    /**
     * La Map permettant de conserver en cache les différentes instances de {@link Sprite}
     * déjà chargées.
     */
    private final Map<String, Sprite> spriteCache = new HashMap<>();

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore#getSprite(
     * java.lang.String)
     */
    @Override
    public Sprite getSprite(String identifier) {
        // On commence par regarder si l'instance a déjà été chargée.
        var cached = spriteCache.get(identifier);
        if (cached != null) {
            return cached;
        }

        // Le sprite n'est pas en cache, on le crée à partir de l'image associée.
        var image = loadImage(identifier);
        var sprite = StaticSprite.newInstance(image);
        spriteCache.put(identifier, sprite);
        return sprite;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore#getSprite(
     * int, java.util.List)
     */
    @Override
    public Sprite getSprite(int frameRate, List<String> identifiers) {
        // Un sprite animé n'est pas mis en cache, il doit nécessairement être créé.
        var images = identifiers.stream().map(this::loadImage).toList();
        return AnimatedSprite.newInstance(FXCollections.observableList(images), frameRate);
    }

    /**
     * Charge une image donnée par son nom.
     *
     * @param name Le nom de l'image à charger.
     *
     * @return L'image ayant le nom donné.
     *
     * @throws NoSuchElementException S'il n'existe pas d'image ayant le nom donné.
     */
    private Image loadImage(String name) {
        try {
            var urlImage = getClass().getResource("sprites/" + name + ".png");
            return new Image(urlImage.toExternalForm(), getSpriteSize(), getSpriteSize(), true, true);

        } catch (NullPointerException | IllegalArgumentException e) {
            throw new NoSuchElementException("Could not load image " + name, e);
        }
    }

}
