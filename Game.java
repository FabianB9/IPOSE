// Tip voor groepsgenoten: verwijder module-info.jav als je de onderstaande foutmelding krijgt
// 'Caused by: java.lang.module.InvalidModuleDescriptorException: Game.class found in top-level directory (unnamed package not allowed in module)'

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.particle.ParticleComponent;
import com.almasb.fxgl.particle.ParticleEmitters;
import com.almasb.fxgl.physics.CollisionHandler;
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

import static com.almasb.fxgl.core.math.FXGLMath.random;

public class Game extends GameApplication {

    private Entity shieldGuy;
    private Entity rangeGuy;
    private Entity arrow;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Dinsdagochtend 10:55 Two Player Version met pijltjes");
        gameSettings.setVersion("1.2");
    }

    @Override
    protected void initGame() {
        var emitter = ParticleEmitters.newFireEmitter();
        emitter.setMaxEmissions(Integer.MAX_VALUE);
        emitter.setNumParticles(1);
        emitter.setEmissionRate(1);
        emitter.setSize(1, 1);
        emitter.setExpireFunction(i -> Duration.seconds(5));
        emitter.setAccelerationFunction(() -> FXGLMath.randomPoint2D().multiply(100));
        emitter.setVelocityFunction(i -> FXGLMath.randomPoint2D().multiply(1));

        shieldGuy = FXGL.entityBuilder()
                .viewWithBBox("pacman.png")
                .with(new CollidableComponent(true))
                .scale(0.1,0.1)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();

        rangeGuy = FXGL.entityBuilder()
                .at(300, 300)
                .viewWithBBox("Slide1.jpg")
                .with(new CollidableComponent(true))
                .scale(0.5,0.5)
                .type(EntityTypes.PLAYER)
                .buildAndAttach();



//        rangeGuy.addComponent(new ParticleComponent(emitter));

//        FXGL.getGameTimer().runAtInterval(() -> {
//            int randomPosX = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
//            int randomPosY = ThreadLocalRandom.current().nextInt(80, FXGL.getGameScene().getAppWidth() -80);
//            FXGL.entityBuilder()
//                    .at(randomPosX, randomPosY)
//                    .with(new CollidableComponent(true))
//                    .viewWithBBox("ghost.png")
//                    .scale(0.1, 0.1)
//                    .type(EntityTypes.STAR)
//                    .buildAndAttach();
//        }, Duration.millis(2000));

    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.D, () -> {
            shieldGuy.translateX(5);
        });

        FXGL.onKey(KeyCode.A, () -> {
            shieldGuy.translateX(-5);
        });

        FXGL.onKey(KeyCode.W, () -> {
            shieldGuy.translateY(-5);
        });

        FXGL.onKey(KeyCode.S, () -> {
            shieldGuy.translateY(5);
        });

        FXGL.onKey(KeyCode.RIGHT, () -> {
            rangeGuy.translateX(5);
        });

        FXGL.onKey(KeyCode.LEFT, () -> {
            rangeGuy.translateX(-5);
        });

        FXGL.onKey(KeyCode.UP, () -> {
            rangeGuy.translateY(-5);
        });

        FXGL.onKey(KeyCode.DOWN, () -> {
            rangeGuy.translateY(5);
        });



        FXGL.onKey(KeyCode.SPACE, () -> {
            arrow = FXGL.entityBuilder()
                    .at(rangeGuy.getX(), rangeGuy.getY())
                    .with(new CollidableComponent(true))
                    .viewWithBBox("ghost.png")
                    .scale(0.1, 0.1)
                    .type(EntityTypes.ARROW)
                    .buildAndAttach();
            FXGL.getGameTimer().runAtInterval(() -> {
                double posX = arrow.getX() + 5;
                arrow.setPosition(posX, arrow.getY());
            }, Duration.millis(100));
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
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.ARROW, EntityTypes.ARROW) {

            protected void onCollision(Entity arrow) {
                arrow.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI(){
        Label myText = new Label("Hello there");
        myText.setStyle("-fx-text-fill: white");
        myText.setTranslateX(50);
        myText.setTranslateY(50);
        myText.textProperty().bind(FXGL.getWorldProperties().intProperty("kills").asString());

        FXGL.getGameScene().setBackgroundColor(Color.MEDIUMPURPLE);
        FXGL.getGameScene().addUINode(myText);
    }

    @Override
    protected void initGameVars(Map<String, Object> vars){
        vars.put("kills", 0);
    }


    public static void main (String[]args){
        launch(args);
    }
}