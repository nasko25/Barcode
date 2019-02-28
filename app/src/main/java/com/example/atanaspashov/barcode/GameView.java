package com.example.atanaspashov.barcode;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.concurrent.atomic.AtomicBoolean;

public class GameView extends View {

    private Paint paint;
    private float x = 200, y = 200;
    private MotionEvent event;
    private ScrollView scrollView;

    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // ..
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.BLUE);

    }


    public GameView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        canvas.drawCircle(x, y, 100, paint);
    }

  /*  AtomicBoolean actionDownFlag = new AtomicBoolean(true);

    Thread gameThread = new Thread(new Runnable(){
        public void run(){
            while(actionDownFlag.get()){
                x = (int)event.getX();
                y = (int)event.getY();
                invalidate();
            }

        }
    });
*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        // scrollView.setClickable(false);
        switch (event.getAction()) {

            //case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                break;

            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();

                break;
            default:
                return false;
        }
        invalidate();
        return true;

        /*
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            //gameThread.start();
            Log.w("COW", "down");
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            Log.w("COW", "up");
            actionDownFlag.set(false);

        }

        return false;*/
    }

   /* @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = (int)event.getX();
        y = (int)event.getY();
        invalidate();
        // TODO it should follow the input, when continuously pressed.
        /*switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
        }*/ /*
        return false;
    }*/

}