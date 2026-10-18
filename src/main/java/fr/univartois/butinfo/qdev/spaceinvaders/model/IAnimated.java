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

import fr.univartois.butinfo.qdev.spaceinvaders.model.animated.Alien;
import fr.univartois.butinfo.qdev.spaceinvaders.model.animated.Shot;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.scene.image.Image;

/**
 * L'interface IAnimated définit le contrat des éléments du jeu dont la
 * représentation graphique évolue au cours de la partie.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public interface IAnimated {

    /**
     * Donne la largeur de cet objet animé (en pixels).
     *
     * @return La largeur de cet objet animé.
     */
    int getWidth();

    /**
     * Donne la hauteur de cet objet animé (en pixels).
     *
     * @return La hauteur de cet objet animé.
     */
    int getHeight();

    /**
     * Modifie la position en x de cet objet animé.
     *
     * @param xPosition La nouvelle position en x de cet objet animé.
     */
    void setX(double xPosition);

    /**
     * Donne la position en x de cet objet animé.
     *
     * @return La position en x de cet objet animé.
     */
    int getX();

    /**
     * Donne la propriété liée à la position en x de cet objet animé.
     *
     * @return La propriété liée à la position en x de cet objet animé.
     */
    DoubleProperty xProperty();

    /**
     * Modifie la position en y de cet objet animé.
     *
     * @param yPosition La nouvelle position en y de cet objet animé.
     */
    void setY(double yPosition);

    /**
     * Donne la position en y de cet objet animé.
     *
     * @return La position en y de cet objet animé.
     */
    int getY();

    /**
     * Donne la propriété liée à la position en y de cet objet animé.
     *
     * @return La propriété liée à la position en y de cet objet animé.
     */
    DoubleProperty yProperty();

    /**
     * Modifie la rotation de cet objet animé (en degrés).
     *
     * @param rotate La nouvelle rotation de cet objet animé.
     */
    void setRotate(double rotate);

    /**
     * Donne la rotation de cet objet animé (en degrés).
     *
     * @return La rotation de cet objet animé.
     */
    double getRotate();

    /**
     * Donne la propriété liée à la rotation de cet objet animé (en degrés).
     *
     * @return La propriété liée à la rotation de cet objet animé.
     */
    DoubleProperty rotateProperty();

    /**
     * Modifie la vitesse horizontale de cet objet animé (en pixels/s).
     *
     * @param speed La nouvelle vitesse horizontale de cet objet animé.
     */
    void setHorizontalSpeed(double speed);

    /**
     * Donne la vitesse horizontale de cet objet animé (en pixels/s).
     *
     * @return La vitesse horizontale de cet objet animé.
     */
    double getHorizontalSpeed();

    /**
     * Modifie la vitesse verticale de cet objet animé (en pixels/s).
     *
     * @param speed La nouvelle vitesse verticale de cet objet animé.
     */
    void setVerticalSpeed(double speed);

    /**
     * Donne la vitesse verticale de cet objet animé (en pixels/s).
     *
     * @return La vitesse verticale de cet objet animé.
     */
    double getVerticalSpeed();

    /**
     * Modifie le {@link Sprite} représentant cet objet animé.
     *
     * @param sprite Le nouveau {@link Sprite} représentant cet objet animé.
     */
    void setSprite(Sprite sprite);

    /**
     * Donne le {@link Sprite} représentant cet objet animé.
     *
     * @return Le {@link Sprite} représentant cet objet animé.
     */
    Sprite getSprite();

    /**
     * Donne la propriété de cet objet correspondant au {@link Sprite} qui le représente.
     *
     * @return La propriété de cet objet correspondant à son {@link Sprite}.
     */
    ObjectProperty<Sprite> spriteProperty();

    /**
     * Donne la propriété de cet objet correspondant à l'{@link Image} qui le représente.
     * Il s'agit d'un raccourci permettant d'obtenir directement l'image du {@link Sprite}
     * représentant cet objet.
     *
     * @return La propriété de cet objet correspondant à son {@link Image}.
     *
     * @see IAnimated#getSprite()
     * @see IAnimated#spriteProperty()
     */
    ObjectProperty<Image> imageProperty();

    /**
     * Modifie l'état de destruction de cet objet animé.
     * Lorsqu'un élément du jeu est détruit, il est retiré du jeu mais sa représentation
     * graphique peut persister un court instant le temps que la destruction soit prise en
     * compte.
     * Cette méthode contribue à éviter de prendre en compte ces objets "zombies" dans la
     * logique du jeu.
     *
     * @param destroyed Le nouvel état de destruction de cet objet animé.
     */
    void setDestroyed(boolean destroyed);

    /**
     * Donne l'état de destruction de cet objet animé.
     * Lorsqu'un élément du jeu est détruit, il est retiré du jeu mais sa représentation
     * graphique peut persister un court instant le temps que la destruction soit prise en
     * compte.
     * Cette méthode contribue à éviter de prendre en compte ces objets "zombies" dans la
     * logique du jeu.
     *
     * @return L'état de destruction de cet objet animé.
     */
    boolean isDestroyed();

    /**
     * Donne la propriété liée à l'état de destruction de cet objet animé.
     * Lorsqu'un élément du jeu est détruit, il est retiré du jeu mais sa représentation
     * graphique peut persister un court instant le temps que la destruction soit prise en
     * compte.
     * Cette méthode contribue à éviter de prendre en compte ces objets "zombies" dans la
     * logique du jeu.
     *
     * @return La propriété liée à l'état de destruction de cet objet animé.
     */
    BooleanProperty destroyedProperty();

    /**
     * Réalise des initialisations particulières lors de la création de cet objet animé.
     */
    void onCreation();

    /**
     * Réalise des initialisations particulières lors de l'apparition sur la carte de cet
     * objet animé.
     */
    void onSpawn();

    /**
     * Modifie l'état de cet objet animé après un certain délai.
     *
     * @param timeDelta Le temps écoulé depuis la dernière mise à jour de cet objet
     *        (en millisecondes).
     *
     * @return Si l'état de cet objet a évolué (par exemple, s'il s'est déplacé).
     */
    boolean onStep(long timeDelta);

    /**
     * Vérifie si cet objet est entré en collision avec un autre objet animé.
     * Cette méthode ne modifie pas l'état interne de l'objet : seul le calcul
     * de collision est réalisé.
     * Pour changer l'état de l'objet suite à une collision, il faut utiliser
     * la méthode {@link #onCollisionWith(IAnimated)}.
     *
     * @param other L'objet avec lequel la collision doit être vérifiée.
     *
     * @return Si cet objet est entré en collision avec {@code other}.
     *
     * @see IAnimated#onCollisionWith(IAnimated)
     */
    boolean isCollidingWith(IAnimated other);

    /**
     * Modifie l'état de cet objet lorsque celui-ci est entré en collision avec un autre
     * objet.
     * Lors de l'appel à cette méthode, il est garanti que les deux objets sont entrés en
     * collision.
     * Il n'est donc pas nécessaire d'utiliser {@link #isCollidingWith(IAnimated)} pour
     * s'en assurer.
     *
     * @param other L'objet avec lequel cet objet est entré en collision.
     *
     * @see IAnimated#isCollidingWith(IAnimated)
     */
    void onCollisionWith(IAnimated other);

    /**
     * Réinitialise ou libère certaines ressources lorsque cet objet animé est retiré de
     * l'affichage, sans toutefois être retiré du jeu.
     */
    void onDespawn();

    /**
     * Réinitialise ou libère certaines ressources lorsque cet objet animé est retiré du
     * jeu définitivement.
     */
    void onDestruction();

    /**
     * Donne l'objet réel qui implémente cette interface.
     *
     * @return L'objet réel.
     */
    IAnimated self();

    void collidedWithAlien(Alien alien);

    void collidedWithShot(Shot shot);
}