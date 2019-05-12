package com.example.smokeeffect;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.util.Log;

import java.util.Random;

import static com.example.smokeeffect.DrawingView.screen_height;
import static com.example.smokeeffect.DrawingView.screen_width;


public class DustParticle {

    private PointF pointF;
    private double distFromOrigin = 0;
    private double directionCosine;
    public double scaleFactor = 0;
    private double directionSine;
    private double angle;

    public DustParticle() {

        this.pointF = new PointF();
        init();

        while (angle < 255 || angle > 285)
            angle = Math.random() * 360;
        double direction = Math.toRadians(angle);

        this.directionCosine = Math.cos(direction);
        this.directionSine = Math.sin(direction);
    }


    private void init() {
        pointF.x = (float)(screen_width / 2);
        pointF.y = screen_height;
    }

    public void moveDust() {
        distFromOrigin += 0.05;
        scaleFactor += 0.010;
        if (scaleFactor > 1) {
            scaleFactor = 1;
        }

        pointF.x = (float) (pointF.x + distFromOrigin * directionCosine);
        pointF.y = (float) (pointF.y + distFromOrigin * directionSine);
    }


    public PointF getPointF() {
        return pointF;
    }

}
