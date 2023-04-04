// Tip voor groepsgenoten: verwijder module-info.jav als je de onderstaande foutmelding krijgt
// 'Caused by: java.lang.module.InvalidModuleDescriptorException: Game.class found in top-level directory (unnamed package not allowed in module)'

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.sun.scenario.Settings;
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

public class Game extends GameApplication {

    private Entity player1;
    private Entity player2;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Onze game");
        gameSettings.setVersion("1.1");
    }

    @Override
    protected void initGame() {
        player1 = FXGL.entityBuilder()
                // linksboven op het scherm is x = 0 en y= 0
                .at(400, 400)
                /* .view(new Rectangle(30, 30, Color.BLUE)) */
                /* .view("Groep19.jpg") */
                .viewWithBBox("Groep19.jpg")
                // true betekent: component = collidable
                .with(new CollidableComponent(true))
                .scale(0.5,0.5)
                /* Dit verwijst naar de klasse EntityTypes waar PLAYER en STAR in staan */
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        player2 = FXGL.entityBuilder()
                .at(300, 300)
                .viewWithBBox("Slide1.jpg")
                .with(new CollidableComponent(true))
                .scale(0.5,0.5)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

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
            player1.translateX(5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            player1.translateX(-5);
        });

        FXGL.onKey(KeyCode.W, () -> {
            player1.translateY(-5);
            /* Y naar -5 betekent omhoog. Onthoud 0,0 is linksboven dus omhoog is negatief! */
        });

        FXGL.onKey(KeyCode.S, () -> {
            player1.translateY(5);
            /* Y naar -5 betekent omlaag */
        });

        FXGL.onKey(KeyCode.L, () -> {
            player2.translateX(5);
        });

        FXGL.onKey(KeyCode.J, () -> {
            player2.translateX(-5);
        });

        FXGL.onKey(KeyCode.I, () -> {
            player2.translateY(-5);
            /* Y naar -5 betekent omhoog. Onthoud 0,0 is linksboven dus omhoog is negatief! */
        });

        FXGL.onKey(KeyCode.K, () -> {
            player2.translateY(5);
            /* Y naar -5 betekent omlaag */
        });
    }

    @Override
    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER, EntityTypes.STAR) {
            @Override
            protected void onCollision(Entity player, Entity star) {
                FXGL.inc("kills", +1);
                star.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI(){
        Label myText = new Label("Hello there");
        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(50);
        myText.setTranslateY(50);
        // Nu binden we de ene variabele ("kills", een integer) aan een text property
        // vandaar de asString(), want ze kunnen alleen aan elkaar verbonden worden als ze van hetzelfde type zijn
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        FXGL.getGameScene().setBackgroundColor(Color.MEDIUMPURPLE);
        FXGL.getGameScene().addUINode(myText);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        // we willen een variabele (in ons geval: kills) definiëren, die we kunnen verbinden aan de UI
        vars.put("kills", 0);
    }


    public static void main (String[]args){
        launch(args);
    }
}