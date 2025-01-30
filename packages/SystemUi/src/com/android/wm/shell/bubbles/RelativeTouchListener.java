package com.android.wm.shell.bubbles;

import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class RelativeTouchListener implements View.OnTouchListener {
    public boolean movedEnough;
    public boolean performedLongClick;
    public final PointF touchDown = new PointF();
    public final PointF viewPositionOnTouchDown = new PointF();
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public int touchSlop = -1;

    public abstract void onDown(View view, MotionEvent motionEvent);

    public abstract void onMove(View view, MotionEvent motionEvent, float f, float f2, float f3, float f4);

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0041, code lost:
    
        if (r1 != 3) goto L30;
     */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(final View view, MotionEvent motionEvent) {
        float rawX = motionEvent.getRawX() - motionEvent.getX();
        float rawY = motionEvent.getRawY() - motionEvent.getY();
        motionEvent.offsetLocation(rawX, rawY);
        this.velocityTracker.addMovement(motionEvent);
        motionEvent.offsetLocation(-rawX, -rawY);
        float rawX2 = motionEvent.getRawX() - this.touchDown.x;
        float rawY2 = motionEvent.getRawY() - this.touchDown.y;
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    if (!this.movedEnough && ((float) Math.hypot(rawX2, rawY2)) > this.touchSlop && !this.performedLongClick) {
                        this.movedEnough = true;
                        view.getHandler().removeCallbacksAndMessages(null);
                    }
                    if (this.movedEnough) {
                        PointF pointF = this.viewPositionOnTouchDown;
                        onMove(view, motionEvent, pointF.x, pointF.y, rawX2, rawY2);
                    }
                }
            }
            if (this.movedEnough) {
                this.velocityTracker.computeCurrentVelocity(1000);
                onUp(view, this.viewPositionOnTouchDown.x, rawX2, this.velocityTracker.getXVelocity(), this.velocityTracker.getYVelocity());
            } else if (this.performedLongClick) {
                view.getHandler().removeCallbacksAndMessages(null);
            } else {
                view.performClick();
            }
            this.velocityTracker.clear();
            this.movedEnough = false;
        } else {
            onDown(view, motionEvent);
            this.touchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.touchDown.set(motionEvent.getRawX(), motionEvent.getRawY());
            this.viewPositionOnTouchDown.set(view.getTranslationX(), view.getTranslationY());
            this.performedLongClick = false;
            view.getHandler().postDelayed(new Runnable() { // from class: com.android.wm.shell.bubbles.RelativeTouchListener$onTouch$1
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

    public abstract void onUp(View view, float f, float f2, float f3, float f4);
}
