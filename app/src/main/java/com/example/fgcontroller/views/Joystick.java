package com.example.fgcontroller.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Joystick extends View {

    Paint paint;
    float x, y ,r ,maxX, maxY, minX, minY;
    OnJoystickChangeListener listener;

    public void setListener(OnJoystickChangeListener listener) {
        this.listener = listener;
    }

    public Joystick(Context context) {
        super(context);
        x = (float) getWidth() / 2;
        y = (float) getHeight() / 2;
        paint = new Paint();
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        x = (float) getWidth() / 2;
        y = (float) getHeight() / 2;
        paint = new Paint();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        x = (float) getWidth() / 2;
        y = (float) getHeight() / 2;
        r = (float)Math.sqrt(x * x + y * y)/5;
        maxX = getWidth() - r;
        maxY = getHeight() - r;
        minY = r;
        minX = r;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(x, y, r, paint);
        paint.setColor(Color.GRAY);
        canvas.drawLine((float) getWidth() / 2, (float) getHeight() / 2, x, y, paint);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Get touch event
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                x = (float) getWidth() / 2;
                y = (float) getHeight() / 2;
                break;
            //ACTION_MOVE do not set break, otherwise the circle will not follow the movement of the finger. Only when the finger is released, the circle will directly reach the position where the screen stops.
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > minX && event.getY() > minY && maxX > event.getX() && maxY > event.getY()) {
                    x = event.getX();
                    y = event.getY();
                }
                //start up
                break;

        }
        if (listener != null) {
            // normal to (-1...1)
            double a = (x - (float) getWidth() / 2) / ((float) getWidth() / 2 - r);
            double e = (y - (float) getHeight() / 2) / ((float) getHeight() / 2 - r);
            listener.onChange(a, e);
        }
            return true;
    }

    public interface OnJoystickChangeListener {
        void onChange(double a, double e);
    }

}
