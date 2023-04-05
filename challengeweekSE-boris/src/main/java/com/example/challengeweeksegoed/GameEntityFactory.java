package com.example.challengeweeksegoed;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;

public class GameEntityFactory implements EntityFactory {

    @Spawns("background")
    public Entity newBackground(SpawnData data){
        return FXGL.entityBuilder()
                .from(data)
                .view(new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight()))
                .build();
    }

    @Spawns("shieldguy")
    public Entity newShieldGuy(SpawnData data){
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("shieldguy.png")
                .type(EntityTypes.SHIELDGUY)
                .with(new ShieldGuyComponent())
                .build();
    }

    @Spawns("rangeguy")
    public Entity newRangeGuy(SpawnData data){
        return FXGL.entityBuilder()
                .from(data)
                .viewWithBBox("swordguy.png")
                .type(EntityTypes.RANGEGUY)
                .with(new RangeGuyComponent())
                .build();
    }

    @Spawns("arrow")
    public Entity newArrow(SpawnData data){
        Point2D dir = data.get("dir");

        return FXGL.entityBuilder()
                .from(data)
                .type(EntityTypes.ARROW)
                .viewWithBBox("arrow.png")
                .with(new ProjectileComponent(dir, 400))
                .build();
    }
}
