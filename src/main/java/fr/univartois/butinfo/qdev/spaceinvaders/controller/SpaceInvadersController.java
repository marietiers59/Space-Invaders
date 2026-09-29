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

package fr.univartois.butinfo.qdev.spaceinvaders.controller;

import fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated;
import fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController;
import fr.univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame;
import fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore;
import fr.univartois.butinfo.qdev.spaceinvaders.view.Sprite;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
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
     * Le conteneur affichant l'arrière-plan du jeu.
     */
    @FXML
    private GridPane backgroundPane;

    /**
     * Le conteneur affichant les objets animés du jeu.
     */
    @FXML
    private Pane animatedPane;

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

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#setGame(fr.
     * univartois.butinfo.qdev.spaceinvaders.model.SpaceInvadersGame)
     */
    @Override
    public void setGame(SpaceInvadersGame game) {
        this.game = game;
    }

    /**
     * Associe à ce contrôleur la fenêtre affichant le jeu.
     *
     * @param stage La fenêtre affichant le jeu.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        addKeyListeners();
    }

    /**
     * Ajoute les écouteurs de touches pour le jeu.
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

        // Lorsque l'utilisateur appuie sur une flèche, on déplace son vaisseau.
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
     * @see fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#
     * setSpriteStore(fr.univartois.butinfo.qdev.spaceinvaders.view.ISpriteStore)
     */
    @Override
    public void setSpriteStore(ISpriteStore spriteStore) {
        this.spriteStore = spriteStore;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#prepare()
     */
    @Override
    public void prepare() {
        createBackground();
    }

    /**
     * Crée l'arrière plan du jeu.
     * Il est créé une et une seule fois, comme il n'évolue pas au cours de la partie.
     */
    private void createBackground() {
        // On retire les précédents composants pour éviter de les dupliquer.
        backgroundPane.getChildren().clear();

        // On crée les sprites de l'arrière-plan pour les partager dans toutes les vues.
        var back = spriteStore.getSprite("back");
        var land = spriteStore.getSprite("land");

        // On détermine les dimensions de la grille à partir des sprites et du jeu.
        int height = game.getHeight();
        int nRows = height / back.getHeight();
        int width = game.getWidth();
        int nCols = width / back.getWidth();

        // On commence par créer le ciel nocturne.
        for (int row = 0; row < nRows; row++) {
            for (int column = 0; column < nCols; column++) {
                var view = new ImageView();
                view.imageProperty().bind(back.imageProperty());
                backgroundPane.add(view, column, row);
            }
        }

        // On ajoute le sol de la planète.
        for (int column = 0; column < nCols; column++) {
            var view = new ImageView();
            view.imageProperty().bind(land.imageProperty());
            backgroundPane.add(view, column, nRows - 1);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#bindScore(
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
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#bindLife(
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
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#addAnimated
     * (fr.univartois.butinfo.qdev.spaceinvaders.model.IAnimated)
     */
    @Override
    public void addAnimated(IAnimated animated) {
        // On affiche l'objet au bon endroit.
        var view = new ImageView();
        view.xProperty().bind(animated.xProperty());
        view.yProperty().bind(animated.yProperty());
        view.rotateProperty().bind(animated.rotateProperty());
        animatedPane.getChildren().add(view);

        // Lorsque le sprite de l'objet change, son image doit changer également.
        view.imageProperty().bind(animated.imageProperty());

        // Lorsque l'objet est consommé, il n'est plus affiché.
        animated.destroyedProperty().addListener((_, _, n) -> {
            if (n == Boolean.TRUE) {
                animatedPane.getChildren().remove(view);
            }
        });
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#gameOver(
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
     * fr.univartois.butinfo.qdev.spaceinvaders.model.ISpaceInvadersController#reset()
     */
    @Override
    public void reset() {
        animatedPane.getChildren().clear();
    }

}
