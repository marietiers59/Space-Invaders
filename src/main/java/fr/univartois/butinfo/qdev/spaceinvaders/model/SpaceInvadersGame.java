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
import java.util.concurrent.CopyOnWriteArrayList;

import fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.animation.AnimationTimer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * La classe SpaceInvadersGame permet de gérer une partie du jeu Space-Invaders.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class SpaceInvadersGame {

    /**
     * La vitesse du vaisseau du joueur lorsqu'il se déplace (en pixels/s).
     */
    private static final double SHIP_SPEED = 150;

    /**
     * La temporisation contraignant le temps entre deux tirs successifs (en
     * millisecondes).
     */
    private static final long SHOT_TEMPORIZATION = 500;

    /**
     * La largeur sur laquelle les objets du jeu peuvent se déplacer (en pixels).
     */
    private final int width;

    /**
     * La hauteur sur laquelle les objets du jeu peuvent se déplacer (en pixels).
     */
    private final int height;

    /**
     * L'instance de {@link ISpriteStore} permettant de créer les {@link Sprite} du jeu.
     */
    private final ISpriteStore spriteStore;

    /**
     * L'instance de {@link IAnimatedFactory} permettant de créer les objets du jeu.
     */
    private IAnimatedFactory factory;

    /**
     * Le vaisseau du joueur.
     */
    private IAnimated ship;

    /**
     * Le timestamp du dernier tir.
     * Il permet de s'assurer que le joueur ne tire pas trop souvent.
     */
    private long lastShot = 0;

    /**
     * Le nombre d'aliens encore vivants.
     */
    private int nbRemainingAliens;

    /**
     * Le nombre de vies restantes pour le joueur.
     */
    private final IntegerProperty life = new SimpleIntegerProperty();

    /**
     * Le score du joueur (en nombre d'aliens éliminés).
     */
    private final IntegerProperty score = new SimpleIntegerProperty();

    /**
     * La liste des objets mobiles du jeu.
     */
    private final List<IAnimated> movingObjects = new CopyOnWriteArrayList<>();

    /**
     * La liste des objets animés du jeu.
     */
    private final List<IAnimated> animatedObjects = new CopyOnWriteArrayList<>();

    /**
     * L'animation du jeu, qui permet aux différents objets de se déplacer.
     */
    private final AnimationTimer animation = new GameAnimation(movingObjects, animatedObjects);

    /**
     * Le contrôleur du jeu.
     */
    private ISpaceInvadersController controller;

    /**
     * Crée une nouvelle instance de SpaceInvadersGame.
     *
     * @param width La largeur sur laquelle les objets du jeu peuvent se déplacer
     *        (en pixels).
     * @param height La hauteur sur laquelle les objets du jeu peuvent se déplacer
     *        (en pixels).
     * @param spriteStore L'instance de {@link ISpriteStore} permettant de créer les
     *        {@link Sprite} du jeu.
     * @param factory L'instance de {@link IAnimatedFactory} permettant de créer les
     *        objets du jeu.
     */
    public SpaceInvadersGame(int width, int height, ISpriteStore spriteStore,
            IAnimatedFactory factory) {
        this.width = width;
        this.height = height;
        this.spriteStore = spriteStore;
        this.factory = factory;
    }

    /**
     * Associe à cette partie de Space-Invaders le contrôleur gérant l'affichage du jeu.
     *
     * @param controller Le contrôleur de l'application.
     */
    public void setController(ISpaceInvadersController controller) {
        this.controller = controller;
    }

    /**
     * Donne la largeur sur laquelle les objets du jeu peuvent se déplacer (en pixels).
     *
     * @return La largeur du jeu.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Donne la limite à gauche pour la position des objets mobiles du jeu (mesurée en
     * pixels).
     *
     * @return La limite à gauche pour la position des objets mobiles du jeu.
     */
    public int getLeftLimit() {
        return 10;
    }

    /**
     * Donne la limite à droite pour la position des objets mobiles du jeu (mesurée en
     * pixels).
     *
     * @return La limite à droite pour la position des objets mobiles du jeu.
     */
    public int getRightLimit() {
        return width - 10;
    }

    /**
     * Donne la hauteur sur laquelle les objets du jeu peuvent se déplacer (en pixels).
     *
     * @return La hauteur du jeu.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Donne la limite en haut pour la position des objets mobiles du jeu (mesurée en
     * pixels).
     *
     * @return La limite en haut pour la position des objets mobiles du jeu.
     */
    public int getTopLimit() {
        return 10;
    }

    /**
     * Donne la limite en bas pour la position des objets mobiles du jeu (mesurée en
     * pixels).
     *
     * @return La limite en bas pour la position des objets mobiles du jeu.
     */
    public int getBottomLimit() {
        return height - 100;
    }

    /**
     * Prépare une partie de Space-Invaders sur le contrôleur de l'application.
     */
    public void prepare() {
        factory.setSpriteStore(spriteStore);
        factory.setGame(this);

        controller.setSpriteStore(spriteStore);
        controller.prepare();
        controller.bindScore(score);
        controller.bindLife(life);
    }

    /**
     * Démarre cette partie de Space-Invaders.
     * Cette méthode permet également de redémarrer une nouvelle partie.
     */
    public void start() {
        initStatistics();
        createAnimated();
        animation.start();
    }

    /**
     * Crée les différents objets animés présents au début de la partie.
     */
    private void createAnimated() {
        // On commence par enlever tous les objets animés encore présents.
        clearAnimated();

        int x = getWidth() / 2;
        int y = getBottomLimit();

        ship = factory.createShip(x, y);

        // Ajouter le vaisseau au jeu
        addMoving(ship);

        // 2. Créer plusieurs aliens en haut de l'écran
        for (int i = 0; i < 5; i++) {

            // Tous les aliens ont la même position horizontale
            int alienX = getWidth() / 2;

            // Chaque alien est placé plus bas que le précédent
            int alienY = getTopLimit() + i * 60;

            // Créer un alien
            IAnimated alien = factory.createAlien(alienX, alienY);

            // Ajouter l'alien au jeu
            addMoving(alien);

            // 3. Compter les aliens créés
            nbRemainingAliens++;
        }
    }

    /**
     * Initialise les statistiques de cette partie.
     */
    private void initStatistics() {
        life.set(3);
        score.set(0);
        nbRemainingAliens = 0;
    }

    /**
     * Déplace le vaisseau du joueur vers la gauche.
     */
    public void moveLeft() {
        ship.setHorizontalSpeed(-SHIP_SPEED);
    }

    /**
     * Déplace le vaisseau du joueur vers la droite.
     */
    public void moveRight() {
        ship.setHorizontalSpeed(SHIP_SPEED);
    }

    /**
     * Arrête le déplacement du vaisseau du joueur.
     */
    public void stopMoving() {
        ship.setHorizontalSpeed(0);
    }

    /**
     * Déclenche un tir depuis le vaisseau du joueur.
     * Cette méthode est sans effet si le délai entre deux tirs n'est pas atteint.
     */
    public void fireShot() {
        long actuel = System.currentTimeMillis();
        if(actuel - lastShot > SHOT_TEMPORIZATION){
            addMoving(factory.createShot(ship.getX()+15, ship.getY()+15));
            lastShot = actuel;
        }
    }

    /**
     * Met à jour le score du joueur lorsqu'un alien est tué.
     * Si c'était le dernier, le joueur gagne la partie.
     *
     * @param alien L'alien qui a été tué.
     */
    public void alienIsDead(IAnimated alien) {
        nbRemainingAliens -= 1;
        if(nbRemainingAliens == 0){
            controller.gameOver("Vous avez gagné !");
        }
    }

    /**
     * Réduit la vie du joueur, et interrompt la partie si elle atteint 0.
     */
    public void reducePlayerLife() {
        life.setValue(life.intValue()-1);
        if(life.intValue() == 0){
            playerIsDead();
        }
    }

    /**
     * Termine la partie lorsque le joueur est tué.
     */
    public void playerIsDead() {
        animation.stop();
        controller.gameOver("Vous avez perdu : votre vaisseau a été détruit !");
    }

    /**
     * Termine la partie lorsque les aliens atteignent la planète.
     */
    public void alienReachedPlanet() {
        animation.stop();
        controller.gameOver("Vous avez perdu : les aliens ont envahi la planète !");
    }

    /**
     * Ajoute un objet mobile dans le jeu.
     *
     * @param object L'objet à ajouter.
     */
    public void addMoving(IAnimated object) {
        movingObjects.add(object);
        addAnimated(object);
    }

    /**
     * Ajoute un objet animé dans le jeu.
     *
     * @param object L'objet à ajouter.
     */
    public void addAnimated(IAnimated object) {
        animatedObjects.add(object);
        controller.addAnimated(object);
        object.onSpawn();
    }

    /**
     * Supprime un objet animé dans le jeu.
     *
     * @param object L'objet à supprimer.
     */
    public void removeAnimated(IAnimated object) {
        animatedObjects.remove(object);
        movingObjects.remove(object);
        object.onDespawn();
        object.onDestruction();
    }

    /**
     * Supprime tous les objets animés dans le jeu.
     */
    private void clearAnimated() {
        for (var animated : animatedObjects) {
            animated.onDespawn();
            animated.onDestruction();
        }
        animatedObjects.clear();
        movingObjects.clear();
    }

}
