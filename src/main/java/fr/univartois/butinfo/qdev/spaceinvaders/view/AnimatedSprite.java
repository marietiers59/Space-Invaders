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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * Un AnimatedSprite représente un {@link Sprite} comportant plusieurs images, dont
 * l'animation est gérée automatiquement par une {@link Timeline}.
 * Les instances de cette classe ne devraient pas être partagées, car la gestion des
 * ressources de l'animation implique l'arrêt de cette animation au moment de la
 * suppression du premier sprite.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
final class AnimatedSprite extends Sprite {

    /**
     * La {@link Timeline} gérant l'animation du sprite.
     */
    private final Timeline timeline;

    /**
     * Crée une nouvelle instance de AnimatedSprite.
     *
     * @param imageProperty La propriété contenant l'image courante du sprite.
     * @param timeline La timeline gérant l'animation du sprite.
     */
    private AnimatedSprite(ObjectBinding<Image> imageProperty, Timeline timeline) {
        super(imageProperty);
        this.timeline = timeline;
    }

    /**
     * Crée une nouvelle instance de AnimatedSprite.
     *
     * @param images La liste des images constituant l'animation.
     * @param frameRate Le nombre d'images par seconde affichées par l'animation.
     *
     * @return L'instance de AnimatedSprite créée.
     */
    public static Sprite newInstance(ObservableList<Image> images, int frameRate) {
        // On crée les propriétés nécessaires pour gérer l'animation.
        var imageIndex = new SimpleIntegerProperty();
        var imageProperty = Bindings.valueAt(images, imageIndex);

        // On crée la timeline qui va gérer l'animation.
        var timeline = new Timeline(new KeyFrame(Duration.millis(1000 / frameRate),
                _ -> imageIndex.set((imageIndex.get() + 1) % images.size())));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // On crée l'instance d'AnimatedSprite.
        return new AnimatedSprite(imageProperty, timeline);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite#destroy()
     */
    @Override
    public void destroy() {
        timeline.stop();
    }

}
