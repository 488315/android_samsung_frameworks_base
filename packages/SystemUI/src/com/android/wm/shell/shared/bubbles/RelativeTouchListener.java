package com.android.wm.shell.shared.bubbles;

import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class RelativeTouchListener implements View.OnTouchListener {
    public boolean movedEnough;
    public boolean performedLongClick;
    public PointF touchDown;
    public final PointF viewPositionOnTouchDown = new PointF();
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public int touchSlop = -1;

    public abstract boolean onDown(View view, MotionEvent motionEvent);

    public abstract void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4);

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(final View view, MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.velocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
        PointF pointF = this.touchDown;
        float rawX2 = pointF != null ? motionEvent.getRawX() - pointF.x : 0.0f;
        PointF pointF2 = this.touchDown;
        float rawY2 = pointF2 != null ? motionEvent.getRawY() - pointF2.y : 0.0f;
        int action = motionEvent.getAction();
        if (action == 0) {
            if (onDown(view, motionEvent)) {
                this.touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
                this.touchDown = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                this.viewPositionOnTouchDown.set(view.getTranslationX(), view.getTranslationY());
                this.performedLongClick = false;
                Handler handler = view.getHandler();
                if (handler != null) {
                    handler.postDelayed(new Runnable() { // from class: com.android.wm.shell.shared.bubbles.RelativeTouchListener$onTouch$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            if (view.isLongClickable()) {
                                this.performedLongClick = view.performLongClick();
                            }
                        }
                    }, ViewConfiguration.getLongPressTimeout());
                }
                return true;
            }
            return false;
        }
        if (action == 1) {
            float f = rawX2;
            if (this.touchDown != null) {
                if (this.movedEnough) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                    onUp(view, this.viewPositionOnTouchDown.x, f, this.velocityTracker.getXVelocity(), this.velocityTracker.getYVelocity());
                } else if (this.performedLongClick) {
                    Handler handler2 = view.getHandler();
                    if (handler2 != null) {
                        handler2.removeCallbacksAndMessages(null);
                    }
                } else {
                    view.performClick();
                }
                this.velocityTracker.clear();
                this.movedEnough = false;
                this.touchDown = null;
                return true;
            }
        } else {
            if (action != 2) {
                if (action == 3) {
                    if (this.touchDown != null) {
                        Handler handler3 = view.getHandler();
                        if (handler3 != null) {
                            handler3.removeCallbacksAndMessages(null);
                        }
                        this.velocityTracker.clear();
                        this.movedEnough = false;
                        this.touchDown = null;
                        float f2 = this.viewPositionOnTouchDown.x;
                        onCancel(view);
                        return true;
                    }
                }
                return true;
            }
            if (this.touchDown != null) {
                if (!this.movedEnough && ((float) Math.hypot(rawX2, rawY2)) > this.touchSlop && !this.performedLongClick) {
                    this.movedEnough = true;
                    Handler handler4 = view.getHandler();
                    if (handler4 != null) {
                        handler4.removeCallbacksAndMessages(null);
                    }
                }
                if (this.movedEnough) {
                    PointF pointF3 = this.viewPositionOnTouchDown;
                    onMove(view, motionEvent, pointF3.x, pointF3.y, rawX2, rawY2);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public abstract void onUp(View view, float f, float f2, float f3, float f4);

    public void onCancel(View view) {
    }
}
