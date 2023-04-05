package com.example.challengeweeksegoed;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

public class RangeGuyComponent extends Component {

    public void moveLeft(){
        entity.translate(-3, 0);
        entity.setScaleX(1);
    }

    public void moveRight(){
        entity.translate(3, 0);
        entity.setScaleX(-1);
    }

    public void moveUp(){
        entity.translate(0, -3);
    }

    public void moveDown(){
        entity.translate(0, 3);
    }

    public void shoot(){
        Point2D center = entity.getCenter();

        Vec2 dir = Vec2.fromAngle(entity.getRotation());

        FXGL.spawn("arrow", new SpawnData(center.getX(), center.getY()).put("dir", dir.toPoint2D()));
    }
}
