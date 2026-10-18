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

package fr.univartois.butinfo.qdev.spaceinvaders.model.animated;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

/**
 * La classe AbstractAnimated fournit une implémentation de base pour tous les
 * objets animés dans le jeu.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public abstract class AbstractAnimated implements IAnimated {

    /**
     * Le jeu dans lequel cet objet animé évolue.
     */
    protected final SpaceInvadersGame game;

    /**
     * La position en x de cet objet animé.
     */
    protected final DoubleProperty xPosition;

    /**
     * La position en y de cet objet animé.
     */
    protected final DoubleProperty yPosition;

    /**
     * La rotation appliquée à cet objet animé (en degrés).
     */
    protected final DoubleProperty rotate;

    /**
     * La vitesse horizontale actuelle de cet objet (en pixels/s).
     */
    protected double horizontalSpeed;

    /**
     * La vitesse verticale actuelle de cet objet (en pixels/s).
     */
    protected double verticalSpeed;

    /**
     * Si cet objet animé a été détruit.
     */
    protected final BooleanProperty destroyed;

    /**
     * L'instance de {@link Sprite} représentant cet objet animé.
     */
    protected final ObjectProperty<Sprite> sprite;

    /**
     * L'image du {@link Sprite} permettant d'afficher cet objet animé.
     * Elle est liée à celle du {@link #sprite} courant.
     */
    protected final ObjectProperty<Image> image;

    /**
     * Crée une nouvelle instance de AbstractAnimated.
     *
     * @param game Le jeu dans lequel l'objet animé évolue.
     * @param xPosition La position en x initiale de l'objet animé.
     * @param yPosition La position en y initiale de l'objet animé.
     * @param sprite L'instance de {@link Sprite} représentant l'objet animé.
     */
    protected AbstractAnimated(SpaceInvadersGame game, double xPosition,
            double yPosition, Sprite sprite) {
        this.game = game;
        this.xPosition = new SimpleDoubleProperty(xPosition);
        this.yPosition = new SimpleDoubleProperty(yPosition);
        this.rotate = new SimpleDoubleProperty(0);
        this.destroyed = new SimpleBooleanProperty(false);
        this.sprite = new SimpleObjectProperty<>(sprite);
        this.image = new SimpleObjectProperty<>();
        this.image.bind(this.sprite.get().imageProperty());
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getWidth()
     */
    @Override
    public int getWidth() {
        return sprite.get().getWidth();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getHeight()
     */
    @Override
    public int getHeight() {
        return sprite.get().getHeight();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setX(double)
     */
    @Override
    public void setX(double xPosition) {
        this.xPosition.set(xPosition);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getX()
     */
    @Override
    public int getX() {
        return xPosition.intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#xProperty()
     */
    @Override
    public DoubleProperty xProperty() {
        return xPosition;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setY(double)
     */
    @Override
    public void setY(double yPosition) {
        this.yPosition.set(yPosition);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getY()
     */
    @Override
    public int getY() {
        return yPosition.intValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#yProperty()
     */
    @Override
    public DoubleProperty yProperty() {
        return yPosition;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setRotate(double)
     */
    @Override
    public void setRotate(double rotate) {
        this.rotate.set(rotate);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getRotate()
     */
    @Override
    public double getRotate() {
        return rotate.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#rotateProperty()
     */
    @Override
    public DoubleProperty rotateProperty() {
        return rotate;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setHorizontalSpeed(double)
     */
    @Override
    public void setHorizontalSpeed(double speed) {
        this.horizontalSpeed = speed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getHorizontalSpeed()
     */
    @Override
    public double getHorizontalSpeed() {
        return horizontalSpeed;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setVerticalSpeed(double)
     */
    @Override
    public void setVerticalSpeed(double speed) {
        this.verticalSpeed = speed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getVerticalSpeed()
     */
    @Override
    public double getVerticalSpeed() {
        return verticalSpeed;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setSprite(fr.univartois.
     * butinfo.qdev.spaceinvaders.view.Sprite)
     */
    @Override
    public void setSprite(Sprite sprite) {
        this.sprite.set(sprite);

        this.image.unbind();
        this.image.bind(this.sprite.get().imageProperty());
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#getSprite()
     */
    @Override
    public Sprite getSprite() {
        return sprite.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#spriteProperty()
     */
    @Override
    public ObjectProperty<Sprite> spriteProperty() {
        return sprite;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#imageProperty()
     */
    @Override
    public ObjectProperty<Image> imageProperty() {
        return image;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#setDestroyed(boolean)
     */
    @Override
    public void setDestroyed(boolean destroyed) {
        this.destroyed.set(destroyed);
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#isDestroyed()
     */
    @Override
    public boolean isDestroyed() {
        return destroyed.get();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#destroyedProperty()
     */
    @Override
    public BooleanProperty destroyedProperty() {
        return destroyed;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#onCreation()
     */
    @Override
    public void onCreation() {
        // Par défaut, cette méthode ne fait rien.
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#onSpawn()
     */
    @Override
    public void onSpawn() {
        // Par défaut, cette méthode ne fait rien.
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#onStep(long)
     */
    @Override
    public boolean onStep(long delta) {
        // On met à jour la position de l'objet sur l'axe x.
        int limitMaxX = game.getWidth() - getWidth();
        double newX = xPosition.get() + (getHorizontalSpeed() * delta) / 1000;
        if ((newX < 0) || (newX > limitMaxX)) {
            // L'objet a atteint la limite sur l'axe x.
            return false;
        }

        // On met à jour la position de l'objet sur l'axe y.
        int limitMaxY = game.getHeight() - getHeight();
        double newY = yPosition.get() + (getVerticalSpeed() * delta) / 1000;
        if ((newY < 0) || (newY > limitMaxY)) {
            // L'objet a atteint la limite sur l'axe y.
            return false;
        }

        // L'objet peut être déplacé à sa nouvelle position.
        xPosition.set(newX);
        yPosition.set(newY);
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#isCollidingWith(fr.
     * univartois.butinfo.qdev.spaceinvaders.model.IAnimated)
     */
    @Override
    public boolean isCollidingWith(IAnimated other) {
        if (isDestroyed() || other.isDestroyed()) {
            // L'un des deux objets a déjà été retiré du jeu.
            // Il ne peut donc pas y avoir de collision.
            return false;
        }

        // On regarde l'intersection entre les rectangles formés par les deux objets.
        var rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
        return rectangle.intersects(other.getX(), other.getY(),
                other.getWidth(), other.getHeight());
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#onDespawn()
     */
    @Override
    public void onDespawn() {
        // Par défaut, cette méthode ne fait rien.
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#onDestruction()
     */
    @Override
    public void onDestruction() {
        setDestroyed(true);
        sprite.get().destroy();
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated#self()
     */
    @Override
    public IAnimated self() {
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            // Les deux objets sont forcément différents.
            return false;
        }

        if (obj == this) {
            // Les deux objets sont strictement identiques.
            return true;
        }

        if (obj instanceof IAnimated other) {
            // On compare les "vrais objets".
            return other.self() == self();
        }

        // L'objet donné n'est pas d'une classe compatible.
        return false;
    }

    @Override
    public void collidedWithAlien(Alien alien) {
        // Ne fait rien
    }

    @Override
    public void collidedWithShot(Shot shot) {
        // Ne fait rien
    }
}
