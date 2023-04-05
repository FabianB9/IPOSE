package com.example.challengeweeksegoed;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import java.awt.*;

public class ShieldGuyComponent extends Component {
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

    public void shield(){

    }
}
