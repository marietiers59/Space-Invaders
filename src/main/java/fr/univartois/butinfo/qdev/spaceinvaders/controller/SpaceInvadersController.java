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

package fr.univartois.butinfo.qdev.spaceinvaders.controller;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IMovable;
import fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * La classe {@link SpaceInvadersController} fournit le contrôleur permettant de jouer au
 * jeu Space-Invaders dans une interface JavaFX.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class SpaceInvadersController implements ISpaceInvadersController {

    /**
     * La partie du jeu Space-Invaders en cours.
     */
    private SpaceInvadersGame game;

    /**
     * L'instance de {@link ISpriteStore} permettant de générer les {@link Sprite}
     * à afficher dans l'interface.
     */
    private ISpriteStore spriteStore;

    /**
     * La fenêtre dans laquelle se déroule le jeu.
     */
    private Stage stage;

    /**
     * Le canevas dans lequel l'arrière-plan du jeu est affiché.
     */
    @FXML
    private Canvas background;

    /**
     * Le conteneur dans lequel les éléments mobiles du jeu sont affichés.
     */
    @FXML
    private Pane movingPane;

    /**
     * Le label affichant les messages à destination du joueur.
     */
    @FXML
    private Label message;

    /**
     * Le label affichant le score du joueur.
     */
    @FXML
    private Label score;

    /**
     * Le label affichant la vie restante du joueur.
     */
    @FXML
    private Label life;

    /**
     * Un booléen permettant de savoir si la partie a démarré.
     * Il permet de temporiser le démarrage du jeu, en attendant que l'utilisateur appuie
     * sur une touche de son clavier.
     */
    private boolean started = false;

    /**
     * Associe à ce contrôleur la fenêtre affichant le jeu.
     *
     * @param stage La fenêtre affichant le jeu.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#setGame(fr
     * .univartois.butinfo.qdev2.spaceinvaders.model.SpaceInvadersGame)
     */
    @Override
    public void setGame(SpaceInvadersGame game) {
        this.game = game;
    }

    /*
     * (non-Javadoc)
     *
     * @see fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#
     * setSpriteStore(fr.univartois.butinfo.qdev2.spaceinvaders.view.ISpriteStore)
     */
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#prepare()
     */
    @Override
    public void prepare() {
        createBackground();
        addKeyListeners();
    }

    /**
     * Crée l'arrière plan du jeu.
     * Il est créé une et une seule fois, comme il n'évolue pas au cours de la partie.
     */
    private void createBackground() {
        // On commence par adapter le canevas à la taille du jeu.
        int width = game.getWidth();
        int height = game.getHeight();
        background.setWidth(width);
        background.setHeight(height);

        // On ajoute l'arrière-plan principal (le ciel étoilé).
        Sprite back = spriteStore.getSprite("back");
        for (int x = 0; x < width; x += back.getWidth()) {
            for (int y = 0; y < height; y += back.getHeight()) {
                back.draw(background.getGraphicsContext2D(), x, y);
            }
        }

        // On ajoute le sol de la planète.
        Sprite land = spriteStore.getSprite("land");
        for (int x = 0; x < width; x += land.getWidth()) {
            land.draw(background.getGraphicsContext2D(), x, height - land.getHeight());
        }
    }

    /**
     * Ajoute les écouteurs de saisie clavier à la fenêtre affichant le jeu.
     */
    private void addKeyListeners() {
        // L'appui (bref) sur une touche peut avoir plusieurs effets.
        stage.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!started) {
                // La partie démarre à la première touche appuyée.
                started = true;
                message.setVisible(false);
                game.start();

            } else if (" ".equals(e.getCharacter())) {
                // La partie a commencé : il faut tirer vers les aliens.
                game.fireShot();
            }
        });

        // Lorsque l'utilisateur appuie sur une flèche horizontale, on déplace son vaisseau.
        stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (started) {
                if (e.getCode() == KeyCode.LEFT) {
                    game.moveLeft();

                } else if (e.getCode() == KeyCode.RIGHT) {
                    game.moveRight();
                }
            }
        });

        // Lorsque l'utilisateur relâche l'une des flèches, on arrête le déplacement.
        stage.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (started && ((e.getCode() == KeyCode.LEFT) || (e.getCode() == KeyCode.RIGHT))) {
                game.stopMoving();
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#bindScore(
     * javafx.beans.property.IntegerProperty)
     */
    @Override
    public void bindScore(IntegerProperty scoreProperty) {
        score.textProperty().bind(scoreProperty.asString());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#bindLife(
     * javafx.beans.property.IntegerProperty)
     */
    @Override
    public void bindLife(IntegerProperty lifeProperty) {
        life.textProperty().bind(lifeProperty.asString());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#addMovable
     * (fr.univartois.butinfo.qdev2.spaceinvaders.model.IMovable)
     */
    @Override
    public void addMovable(IMovable movable) {
        // On affiche l'objet au bon endroit.
        ImageView view = new ImageView(movable.getSprite().getImage());
        view.xProperty().bind(movable.getXProperty());
        view.yProperty().bind(movable.getYProperty());
        movingPane.getChildren().add(view);

        // Lorsque le sprite de l'objet change, son image doit changer également.
        movable.getSpriteProperty().addListener((p, o, n) -> view.setImage(n.getImage()));

        // Lorsque l'objet est consommé, il n'est plus affiché.
        movable.isConsumedProperty().addListener((p, o, n) -> {
            if (n == Boolean.TRUE) {
                movingPane.getChildren().remove(view);
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#gameOver(
     * java.lang.String)
     */
    @Override
    public void gameOver(String endMessage) {
        started = false;
        message.setVisible(true);
        message.setText(endMessage + "\nPRESS ANY KEY TO RESTART...");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev2.spaceinvaders.model.ISpaceInvadersController#reset()
     */
    @Override
    public void reset() {
        movingPane.getChildren().clear();
    }

}
