package com.example.smokeeffect;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;


public class DrawingView extends View implements Listener {
    Thread thread;
    Context context;
    public static int screen_height, screen_width;
    public Bitmap dustImage;

    public static ArrayList<DustParticle> dustParticles = new ArrayList<>();


    public DrawingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        screen_height = context.getResources().getDisplayMetrics().heightPixels;
        screen_width = context.getResources().getDisplayMetrics().widthPixels;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        dustImage = BitmapFactory.decodeResource((context).getResources(), R.drawable.dust, options);
        threadInit();
        thread.start();

    }

    public DrawingView(Context context) {
        super(context);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, screen_width, screen_height, paint);

        for (int delIndex = 0; delIndex < dustParticles.size(); delIndex++) {
            if (dustParticles.get(delIndex).getPointF().y < (3*(screen_height))/4 || dustParticles.get(delIndex).getPointF().x < 0 || dustParticles.get(delIndex).getPointF().x > screen_width) {
                dustParticles.remove(delIndex);
                Log.d("REMOVED", "DATA");
            }
        }


        for (int index = 0; index < dustParticles.size(); index++) {
            dustParticles.get(index).moveDust();
            canvas.save();
            canvas.translate(dustParticles.get(index).getPointF().x, dustParticles.get(index).getPointF().y);
            canvas.scale((float) dustParticles.get(index).scaleFactor, (float) dustParticles.get(index).scaleFactor);
            canvas.drawBitmap(dustImage, (float) ((-1) * (dustImage.getWidth()) / 2), (float) ((-1) * (dustImage.getHeight()) / 2), null);
            canvas.restore();
        }


    }

    private Activity getActivity(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void threadInit() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted()) {
                    try {
                        Thread.sleep(1000 / 90);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    (getActivity(context)).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                }

            }
        });
    }


    @Override
    public void clickHandle() {
        Log.d("RECEIVED", "FROM PRESENTER IMP");
        for (int i = 0; i < 3; i++) {
            dustParticles.add(new DustParticle());
        }
    }
}
