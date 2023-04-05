package com.example.challengeweeksegoed;// Tip voor groepsgenoten: verwijder module-info.jav als je de onderstaande foutmelding krijgt
// 'Caused by: java.lang.module.InvalidModuleDescriptorException: Game.class found in top-level directory (unnamed package not allowed in module)'

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Map;

public class Game extends GameApplication {

    private Entity shieldGuy;
    private Entity rangeGuy;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(800);
        gameSettings.setTitle("Dinsdag 12:25");
        gameSettings.setVersion("1.3");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new GameEntityFactory());

        FXGL.spawn("background");

        shieldGuy = FXGL.spawn("shieldguy", FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);
        rangeGuy = FXGL.spawn("rangeguy", FXGL.getAppWidth() / 2, FXGL.getAppHeight() / 2);

    }

    @Override
    protected void initInput() {
        FXGL.onKey(KeyCode.A, () -> rangeGuy.getComponent(RangeGuyComponent.class).moveLeft());
        FXGL.onKey(KeyCode.D, () -> rangeGuy.getComponent(RangeGuyComponent.class).moveRight());
        FXGL.onKey(KeyCode.W, () -> rangeGuy.getComponent(RangeGuyComponent.class).moveUp());
        FXGL.onKey(KeyCode.S, () -> rangeGuy.getComponent(RangeGuyComponent.class).moveDown());
        FXGL.onKeyDown(KeyCode.C, "shoot", () -> rangeGuy.getComponent(RangeGuyComponent.class).shoot());

        FXGL.onKey(KeyCode.LEFT, () -> shieldGuy.getComponent(ShieldGuyComponent.class).moveLeft());
        FXGL.onKey(KeyCode.RIGHT, () -> shieldGuy.getComponent(ShieldGuyComponent.class).moveRight());
        FXGL.onKey(KeyCode.UP, () -> shieldGuy.getComponent(ShieldGuyComponent.class).moveUp());
        FXGL.onKey(KeyCode.DOWN, () -> shieldGuy.getComponent(ShieldGuyComponent.class).moveDown());
        FXGL.onKeyDown(KeyCode.SPACE, "shield", () -> shieldGuy.getComponent(ShieldGuyComponent.class).shield());
    }

    @Override
    protected void initPhysics(){
        FXGL.onCollision(EntityTypes.SHIELDGUY, EntityTypes.ARROW, (shieldguy, arrow) -> {
            shieldguy.removeFromWorld();
            arrow.removeFromWorld();
        });
    }



    @Override
    protected void initUI(){

    }

    @Override
    protected void initGameVars(Map<String, Object> vars){

    }



    public static void main (String[]args){
        launch(args);
    }
}