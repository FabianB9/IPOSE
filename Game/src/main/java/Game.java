// Tip voor groepsgenoten: verwijder module-info.jav als je de onderstaande foutmelding krijgt
// 'Caused by: java.lang.module.InvalidModuleDescriptorException: Game.class found in top-level directory (unnamed package not allowed in module)'

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsWorld;
import com.sun.scenario.Settings;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.awt.*;
import java.security.Key;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import com.almasb.fxgl.core.util.Platform;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getPhysicsWorld;

public class Game extends GameApplication {


    private static final int AMOUNT_OF_LEVELS = 4;
    private Entity shieldGuy;
    private Entity swordGuy;

    private Entity brickWall;

    private Entity brickWall2;

    private Entity verticalBrickWall;

    private int currentLevel = 1;


    @Override
    protected void initSettings(GameSettings gameSettings) {
        // gameSettings.setWidth(800);
        // gameSettings.setHeight(600);
        gameSettings.setFullScreenAllowed(true);
        gameSettings.setFullScreenFromStart(true);
        gameSettings.setTitle("Woensdag 10:30");
        gameSettings.setVersion("1.8");

    }

    @Override
    protected void initGame() {
        double startXShieldGuy = 0;
        double startYShieldGuy = 450;
        shieldGuy = FXGL.entityBuilder()
                // linksboven op het scherm is x = 0 en y= 0
                .at(startXShieldGuy, startYShieldGuy)
                /* .view(new Rectangle(30, 30, Color.BLUE)) */
                /* .view("Groep19.jpg") */
                .viewWithBBox("shieldguy.png")
                // true betekent: component = collidable
                .with(new CollidableComponent(true))
                .with(new KeepOnScreenComponent())
                .scale(0.5,0.5)
                /* Dit verwijst naar de klasse EntityTypes waar PLAYER en STAR in staan */
                .type(EntityTypes.SHIELDGUY)
                .buildAndAttach();

        double startXSwordGuy = -13;
        double startYSwordGuy = 500;
        swordGuy = FXGL.entityBuilder()
                // FXGL.getAppWidth()
                .at(startXSwordGuy, startYSwordGuy)
                .viewWithBBox("swordguy.png")
                .with(new CollidableComponent(true))
                .with(new KeepOnScreenComponent())
                .scale(0.5,0.5)
                .type(EntityTypes.SWORDGUY)
                .buildAndAttach();

        brickWall = FXGL.entityBuilder()
                .at(-270, 300)
                .viewWithBBox("brick_wall.png")
                .with(new CollidableComponent(true))
                .scale(0.3,0.3)
                .type(EntityTypes.BRICKWALL)
                .buildAndAttach();
                // Rectangle

        brickWall2 = FXGL.entityBuilder()
                .at(-40, 300)
                .viewWithBBox("brick_wall.png")
                .with(new CollidableComponent(true))
                .scale(0.3,0.3)
                .type(EntityTypes.BRICKWALL2)
                .buildAndAttach();

        verticalBrickWall = FXGL.entityBuilder()
                .at(475, 100)
                .viewWithBBox("vertical_brick_wall.png")
                .with(new CollidableComponent(true))
                .scale(0.3,0.3)
                .type(EntityTypes.VERTICALBRICKWALL)
                .buildAndAttach();


        // Rectangle

        FXGL.getGameTimer().runAtInterval(() -> {
            /* randomPos geneert een willekeurige positie (een beetje van de randen af) voor de ster */
            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
            FXGL.entityBuilder()
                    .at(randomPosX, randomPosY)
                    .with(new CollidableComponent(true))
                    .viewWithBBox(new Circle(5, Color.WHITE))
                    /* Dit verwijst naar de klasse EntityTypes waar PLAYER en STAR in staan */
                    /* Dit is de 'ster' in de linkerbovenhoek */
                    .type(EntityTypes.STAR)
                    .buildAndAttach();
        }, Duration.millis(2000));
        /* Duration is in milliseconden. 2000 milliseconde is dus 2 seconde.
        Dit betekent: elke 2 seconde moet er een ster geplaatst worden op positie 200, 200.
         */
    }

    @Override
    protected void initInput() {
        /* 'D' betekent naar rechts
        () -> is een Lambda-expressie: een naamloze functie, die in dit geval gaat werken als er op de D-knop
        gedrukt wordt. (Dus als we willen dat de user naar rechts gaat
        translateX = speler verplaatsen*/
        FXGL.onKey(KeyCode.D, () -> {
                shieldGuy.translateX(5);
        });

        FXGL.onKey(KeyCode.A, () -> {
                shieldGuy.translateX(-5);
        });

        FXGL.onKey(KeyCode.W, () -> {
                shieldGuy.translateY(-5);
            /* Y naar -5 betekent omhoog. Onthoud 0,0 is linksboven dus omhoog is negatief! */
        });

        FXGL.onKey(KeyCode.S, () -> {
                shieldGuy.translateY(5);
        });
        /* Y naar -5 betekent omlaag */

            /* System.out.println(FXGL.getAppHeight());
            System.out.println(FXGL.getAppWidth());
            System.out.println(shieldGuy.getY());
            System.out.println(shieldGuy.getX()); */
        // in coördinatensystemen is de positie van een blok de positie linksboven! (Geldt ook voor HTML.)

        FXGL.onKey(KeyCode.RIGHT, () -> {
                swordGuy.translateX(5);
        });

        FXGL.onKey(KeyCode.LEFT, () -> {
                swordGuy.translateX(-5);
        });

        FXGL.onKey(KeyCode.UP, () -> {
                swordGuy.translateY(-5);
            /* Y naar -5 betekent omhoog. Onthoud 0,0 is linksboven dus omhoog is negatief! */
        });

        FXGL.onKey(KeyCode.DOWN, () -> {
                swordGuy.translateY(5);
            /* Y naar -5 betekent omlaag */
        });

        FXGL.onKey(KeyCode.SPACE, () -> {
            shieldGuy.translateY(-10);
            // To-do: ga door naar het volgende level
            // FXGL.getGam.reset();
            // FXGL.getGameWorld().
        });
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0); // set the gravity to 1000 pixels per second squared downwards

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SHIELDGUY, EntityTypes.STAR) {
            @Override
            protected void onCollision(Entity player, Entity star) {
                FXGL.inc("amount of kills player 1", +1);
                star.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SWORDGUY, EntityTypes.STAR) {
            @Override
            protected void onCollision(Entity player, Entity star) {
                FXGL.inc("amount of kills player 2", +1);
                star.removeFromWorld();
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SHIELDGUY, EntityTypes.BRICKWALL) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(0, 450);
            }
        });


        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SWORDGUY, EntityTypes.BRICKWALL) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SHIELDGUY, EntityTypes.BRICKWALL) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SWORDGUY, EntityTypes.BRICKWALL2) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SWORDGUY, EntityTypes.BRICKWALL2) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SHIELDGUY, EntityTypes.VERTICALBRICKWALL) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.SWORDGUY, EntityTypes.VERTICALBRICKWALL) {
            @Override
            protected void onCollision(Entity player, Entity brickwall) {
                player.setPosition(-13, 500);
            }
        });
    }



    @Override
    protected void initUI(){
        Label myText = new Label("Score player 1: ");
        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(10);
        myText.setTranslateY(30);
        Label myScore = new Label("0");
        myScore.setStyle("-fx-text-fill: white");
        myScore.setTranslateX(90);
        myScore.setTranslateY(30);
        Label myText2 = new Label("Score player 2: ");
        myText2.setTranslateX(10);
        myText2.setTranslateY(50);
        Label myScore2 = new Label("0");
        myText2.setStyle("-fx-text-fill: white");
        myScore2.setStyle("-fx-text-fill: white");
        myScore2.setTranslateX(90);
        myScore2.setTranslateY(50);
        // Nu binden we de ene variabele ("kills", een integer) aan een text property
        // vandaar de asString(), want ze kunnen alleen aan elkaar verbonden worden als ze van hetzelfde type zijn
        myScore.textProperty().bind(FXGL.getWorldProperties().intProperty("amount of kills player 1").asString());
        myScore2.textProperty().bind(FXGL.getWorldProperties().intProperty("amount of kills player 2").asString());

        FXGL.getGameScene().setBackgroundColor(Color.MEDIUMPURPLE);
        FXGL.getGameScene().addUINode(myText);
        FXGL.getGameScene().addUINode(myText2);
        FXGL.getGameScene().addUINode(myScore);
        FXGL.getGameScene().addUINode(myScore2);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        // we willen een variabele (in ons geval: kills) definiëren, die we kunnen verbinden aan de UI
        vars.put("amount of kills player 1", 0);
        vars.put("amount of kills player 2", 0);
    }



    public static void main (String[]args){
        launch(args);
    }
}