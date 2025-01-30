package com.android.wm.shell.bubbles;

import android.R;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.wm.shell.bubbles.BubbleStackView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblesNavBarMotionEventHandler {
    public boolean mInterceptingTouches;
    public final MotionEventListener mMotionEventListener;
    public final Runnable mOnInterceptTouch;
    public final BubblePositioner mPositioner;
    public final PointF mTouchDown = new PointF();
    public final int mTouchSlop;
    public boolean mTrackingTouches;
    public VelocityTracker mVelocityTracker;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface MotionEventListener {
    }

    public BubblesNavBarMotionEventHandler(Context context, BubblePositioner bubblePositioner, Runnable runnable, MotionEventListener motionEventListener) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPositioner = bubblePositioner;
        this.mOnInterceptTouch = runnable;
        this.mMotionEventListener = motionEventListener;
    }

    public final VelocityTracker getVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        return this.mVelocityTracker;
    }

    public final boolean onMotionEvent(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        PointF pointF = this.mTouchDown;
        float f = x - pointF.x;
        float y = motionEvent.getY() - pointF.y;
        int action = motionEvent.getAction();
        MotionEventListener motionEventListener = this.mMotionEventListener;
        if (action == 0) {
            BubblePositioner bubblePositioner = this.mPositioner;
            int dimensionPixelSize = bubblePositioner.mContext.getResources().getDimensionPixelSize(R.dimen.notification_conversation_header_separating_margin);
            Rect rect = bubblePositioner.mScreenRect;
            int i = rect.left;
            int i2 = rect.bottom;
            if (new Rect(i, i2 - dimensionPixelSize, rect.right, i2).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                pointF.set(motionEvent.getX(), motionEvent.getY());
                motionEvent.getX();
                motionEvent.getY();
                motionEventListener.getClass();
                this.mTrackingTouches = true;
                return true;
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3 && this.mTrackingTouches) {
                    BubbleStackView.this.mExpandedViewAnimationController.getClass();
                    pointF.set(0.0f, 0.0f);
                    this.mTrackingTouches = false;
                    this.mInterceptingTouches = false;
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        this.mVelocityTracker = null;
                    }
                    return true;
                }
            } else if (this.mTrackingTouches) {
                if (!this.mInterceptingTouches && Math.hypot(f, y) > this.mTouchSlop) {
                    this.mInterceptingTouches = true;
                    this.mOnInterceptTouch.run();
                }
                if (this.mInterceptingTouches) {
                    getVelocityTracker().addMovement(motionEvent);
                    ((BubbleStackView.C38249) motionEventListener).onMove(y);
                }
                return true;
            }
        } else if (this.mTrackingTouches) {
            if (this.mInterceptingTouches) {
                getVelocityTracker().computeCurrentVelocity(1000);
                getVelocityTracker().getXVelocity();
                ((BubbleStackView.C38249) motionEventListener).onUp(getVelocityTracker().getYVelocity());
            }
            pointF.set(0.0f, 0.0f);
            this.mTrackingTouches = false;
            this.mInterceptingTouches = false;
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
            return true;
        }
        return false;
    }
}
