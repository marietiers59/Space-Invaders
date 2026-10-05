# *Space-Invaders* en JavaFX

## Description

Ce projet fournit une implémentation de base du jeu *Space-Invaders* en
*JavaFX*.
Pour pouvoir développer votre propre implémentation de ce projet, vous devez
en créer une **bifurcation** en cliquant sur le bouton `Fork` en haut à droite
de cette page.

Lorsque ce sera fait, vous pourrez inviter les membres de votre groupe en tant
que *Developer* pour vous permettre de travailler ensemble sur ce projet.

## Consignes

Vous pouvez retrouver ci-dessous les liens vers les sujets de TP vous guidant
dans le développement de votre projet :

- [Lancement du projet *Space-Invaders*](https://gitlab.univ-artois.fr/enseignements-rwa/modules/but-2/r3-04/tp/-/tree/main/tp/TP03)

## Diagramme de classes

```plantuml
hide empty members

' --------------------------------------- '
' Gestion des images du jeu (les sprites) '
' --------------------------------------- '

abstract class Sprite {
    - imageProperty : ObjectBinding<Image>

    # Sprite(imageProperty : ObjectBinding<Image>)
    + getWidth() : int
    + getHeight() : int
    + imageProperty() : ObjectBinding<Image>
    + {abstract} destroy() : void
}

class StaticSprite extends Sprite {
    - StaticSprite(imageProperty : ObjectBinding<Image>)
    + {static} newInstance(image : Image) : Sprite
    + destroy() : void
}

class AnimatedSprite extends Sprite {
    - timeline : Timeline

    - AnimatedSprite(imageProperty : ObjectBinding<Image>, timeline : Timeline)
    + {static} newInstance(images : ObservableList<Image>, frameRate : int) : Sprite
    + destroy() : void
}

interface ISpriteStore {
    + {static} DEFAULT_SPRITE_SIZE : int
    + {static} DEFAULT_FRAME_RATE : int

    + {abstract} getSprite(identifier : String) : Sprite
    + getSprite(identifiers : String[]) : Sprite
    + getSprite(frameRate : int, identifiers : String[]) : Sprite
    + getSprite(identifiers : List<String>) : Sprite
    + {abstract} getSprite(frameRate : int, identifiers : List<String>) : Sprite
    + getSpriteSize() : int
}

class SpriteStore implements ISpriteStore {
    - spriteCache : Map<String, Sprite>

    + getSprite(identifier : String) : Sprite
    + getSprite(frameRate : int, identifiers : List<String>) : Sprite
    - loadImage(name : String) : Image
}

ISpriteStore --> Sprite : << crée >>

' -------------------- '
' Gestion de la partie '
' -------------------- '

class SpaceInvadersGame {
    + {static} SHIP_SPEED : double
    + {static} SHOT_TEMPORIZATION : long
    - width : int
    - height : int
    - spriteStore : ISpriteStore
    - factory : IAnimatedFactory
    - ship : IAnimated
    - lastShot : long
    - nbRemainingAliens : int
    - life : IntegerProperty
    - score : IntegerProperty
    - movingObjects : List<IAnimated>
    - animatedObjects : List<IAnimated>
    - animation : AnimationTimer
    - controller : ISpaceInvadersController

    + SpaceInvadersGame(width : int, height : int, spriteStore : ISpriteStore, factory : IAnimatedFactory)
    + setController(controller : ISpaceInvadersController) : void
    + getWidth() : int
    + getLeftLimit() : int
    + getRightLimit() : int
    + getHeight() : int
    + getTopLimit() : int
    + getBottomLimit() : int
    + prepare() : void
    + start() : void
    - createAnimated() : void
    - initStatistics() : void
    - spawnAnimated(animated : IAnimated): void
    + moveLeft() : void
    + moveRight() : void
    + stopMoving() : void
    + fireShot() : void
    + alienIsDead() : void
    + reducePlayerLife() : void
    + playerIsDead() : void
    + alienReachedPlanet() : void
    + addMoving(object : IAnimated) : void
    + addAnimated(object : IAnimated) : void
    + removeAnimated(object : IAnimated) : void
    - clearAnimated() : void
}

interface IAnimated {
    + {abstract} getWidth() : int
    + {abstract} getHeight() : int
    + {abstract} setX(xPosition : double) : void
    + {abstract} getX() : int
    + {abstract} xProperty() : DoubleProperty
    + {abstract} setY(yPosition : double) : void
    + {abstract} getY() : int
    + {abstract} yProperty() : DoubleProperty
    + {abstract} setHorizontalSpeed(speed : double) : void
    + {abstract} getHorizontalSpeed() : double
    + {abstract} setVerticalSpeed(speed : double) : void
    + {abstract} getVerticalSpeed() : double
    + {abstract} setSprite(sprite : Sprite) : void
    + {abstract} getSprite() : Sprite
    + {abstract} spriteProperty() : ObjectProperty<Sprite>
    + {abstract} imageProperty() : ObjectProperty<Image>
    + {abstract} setDestroyed(destroyed : boolean) : void
    + {abstract} isDestroyed() : boolean
    + {abstract} destroyedProperty() : BooleanProperty
    + {abstract} onCreation() : void
    + {abstract} onSpawn() : void
    + {abstract} onStep(timeDelta : long) : boolean
    + {abstract} isCollidingWith(other : IAnimated) : boolean
    + {abstract} onCollisionWith(other : IAnimated) : void
    + {abstract} onDespawn() : void
    + {abstract} onDestruction() : void
    + {abstract} self() : IAnimated
}

interface IAnimatedFactory {
    + {abstract} setSpriteStore(spriteStore : ISpriteStore) : void
    + {abstract} setGame(game : SpaceInvadersGame) : void
    + {abstract} createAlien(x : int, y : int) : IAnimated
    + {abstract} createShip(x : int, y : int) : IAnimated
    + {abstract} createShot(x : int, y : int) : IAnimated
}

interface ISpaceInvadersController {
    + {abstract} setGame(game : SpaceInvadersGame) : void
    + {abstract} setSpriteStore(spriteStore : ISpriteStore) : void
    + {abstract} prepare(map : GameMap) : void
    + {abstract} bindScore(scoreProperty : IntegerExpression) : void
    + {abstract} bindLife(lifeProperty : IntegerExpression) : void
    + {abstract} addAnimated(movable : IAnimated) : void
    + {abstract} gameOver(endMessage : String) : void
    + {abstract} reset() : void
}

class GameAnimation {
    - movingObjects : List<IAnimated>
    - animatedObjects : List<IAnimated>
    - previousTimestamp : long

    + GameAnimation(movingObjects : List<IAnimated>, animatedObjects : List<IAnimated>)
    + start(): void
    + handle(now : long) : void
    - updateObjects(delta : long) : void
    - checkCollisions() : void
}

SpaceInvadersGame o-- "1" ISpriteStore
SpaceInvadersGame o-- "1" GameMap
SpaceInvadersGame o-- "*" IAnimated
SpaceInvadersGame o-- "1" GameAnimation
SpaceInvadersGame o-- "1" ISpaceInvadersController

IAnimatedFactory o-- "1" ISpriteStore
IAnimatedFactory o-- "1" SpaceInvadersGame

IAnimatedFactory --> IAnimated : << crée >>

GameAnimation o-- "*" IAnimated

' --------------------------------- '
' Gestion de base des objets animés '
' --------------------------------- '

abstract class AbstractAnimated implements IAnimated {
    - {static} MARGIN : int
    # game : SpaceInvadersGame
    # xPosition : DoubleProperty
    # yPosition : DoubleProperty
    # horizontalSpeed : double
    # verticalSpeed : double
    # destroyed : BooleanProperty
    # sprite : ObjectProperty<Sprite>
    # image : ObjectProperty<Image>

    # AbstractAnimated(game : SpaceInvadersGame, xPosition : double, yPosition : double, sprite : Sprite)
    + getWidth() : int
    + getHeight() : int
    + setX(xPosition : double) : void
    + getX() : int
    + xProperty() : DoubleProperty
    + setY(yPosition : double) : void
    + getY() : int
    + yProperty() : DoubleProperty
    + setHorizontalSpeed(speed : double) : void
    + getHorizontalSpeed() : double
    + setVerticalSpeed(speed : double) : void
    + getVerticalSpeed() : double
    + setSprite(sprite : Sprite) : void
    + getSprite() : Sprite
    + spriteProperty() : ObjectProperty<Sprite>
    + imageProperty() : ObjectProperty<Image>
    + setDestroyed(destroyed : boolean) : void
    + isDestroyed() : boolean
    + destroyedProperty() : BooleanProperty
    + onCreation() : void
    + onSpawn() : void
    + onStep(delta : long) : boolean
    + isCollidingWith(other : IAnimated) : boolean
    + onDespawn() : void
    + onDestruction() : void
    + self() : IAnimated
    + hashCode() : int
    + equals(obj : Object) : boolean
}

AbstractAnimated o-- "1" SpaceInvadersGame
AbstractAnimated o-- "1" Sprite

' ----------------- '
' Contrôleur JavaFX '
' ----------------- '

class SpaceInvadersController implements ISpaceInvadersController {
    - game : SpaceInvadersGame
    - stage : Stage
    - backgroundPane : GridPane
    - animatedPane : Pane
    - message : Label
    - score : Label
    - life : Label
    - started : boolean

    + setGame(game : SpaceInvadersGame) : void
    + setStage(stage : Stage) : void
    - addKeyListeners() : void
    + prepare(map : GameMap) : void
    - createBackground(map : GameMap) : void
    + bindScore(scoreProperty : IntegerExpression) : void
    + bindLife(lifeProperty : IntegerExpression) : void
    + addAnimated(animated : IAnimated) : void
    + gameOver(endMessage : String) : void
    + reset() : void
}

SpaceInvadersController o-- "1" SpaceInvadersGame
```

## Tâches réalisées

### Jalon n°1 - TP n°3

| Fonctionnalité                             | Terminée ? | Auteur(s)                                     |
| ------------------------------------------ | ---------- | --------------------------------------------- |
| Représentation d'un tir                    |            |                                               |
| Représentation du vaisseau du joueur       |            |                                               |
| Représentation d'un vaisseau alien         |            |                                               |
| Gestion des collisions spécifiques         |            |                                               |
| Création des différents éléments du jeu    |            |                                               |
| Intégration des vaisseaux dans la partie   |            |                                               |
| Gestion du tir allié                       |            |                                               |
| Gestion de la fin de partie                |            |                                               |
