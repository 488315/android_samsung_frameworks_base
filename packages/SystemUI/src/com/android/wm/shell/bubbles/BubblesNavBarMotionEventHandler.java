package com.android.wm.shell.bubbles;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* loaded from: classes3.dex */
public class BubblesNavBarMotionEventHandler {
    public boolean mInterceptingTouches;
    public final MotionEventListener mMotionEventListener;
    public final Runnable mOnInterceptTouch;
    public final BubblePositioner mPositioner;
    public final PointF mTouchDown = new PointF();
    public final int mTouchSlop;
    public boolean mTrackingTouches;
    public VelocityTracker mVelocityTracker;

    public interface MotionEventListener {
    }

    public BubblesNavBarMotionEventHandler(Context context, BubblePositioner bubblePositioner, Runnable runnable, MotionEventListener motionEventListener) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mPositioner = bubblePositioner;
        this.mOnInterceptTouch = runnable;
        this.mMotionEventListener = motionEventListener;
    }

    public final boolean onMotionEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        float x = motionEvent.getX() - this.mTouchDown.x;
        float y = motionEvent.getY() - this.mTouchDown.y;
        int action = motionEvent.getAction();
        MotionEventListener motionEventListener = this.mMotionEventListener;
        if (action == 0) {
            BubblePositioner bubblePositioner = this.mPositioner;
            int dimensionPixelSize = bubblePositioner.mContext.getResources().getDimensionPixelSize(R.dimen.seekbar_track_background_height_material);
            Rect rect = bubblePositioner.mScreenRect;
            int i = rect.left;
            int i2 = rect.bottom;
            if (new Rect(i, i2 - dimensionPixelSize, rect.right, i2).contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
                    long x2 = (int) motionEvent.getX();
                    long y2 = (int) motionEvent.getY();
                    int dimensionPixelSize2 = bubblePositioner.mContext.getResources().getDimensionPixelSize(R.dimen.seekbar_track_background_height_material);
                    Rect rect2 = bubblePositioner.mScreenRect;
                    int i3 = rect2.left;
                    int i4 = rect2.bottom;
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -3823162406471254633L, 5, Long.valueOf(x2), Long.valueOf(y2), String.valueOf(new Rect(i3, i4 - dimensionPixelSize2, rect2.right, i4)));
                }
                this.mTouchDown.set(motionEvent.getX(), motionEvent.getY());
                motionEvent.getX();
                motionEvent.getY();
                motionEventListener.getClass();
                this.mTrackingTouches = true;
                return true;
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3 && this.mTrackingTouches) {
                    BubbleStackView.this.mExpandedViewAnimationController.animateBackToExpanded();
                    this.mTouchDown.set(0.0f, 0.0f);
                    this.mTrackingTouches = false;
                    this.mInterceptingTouches = false;
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    if (velocityTracker != null) {
                        velocityTracker.recycle();
                        this.mVelocityTracker = null;
                        return true;
                    }
                    return true;
                }
            } else if (this.mTrackingTouches) {
                if (!this.mInterceptingTouches && Math.hypot(x, y) > this.mTouchSlop) {
                    this.mInterceptingTouches = true;
                    this.mOnInterceptTouch.run();
                }
                if (this.mInterceptingTouches) {
                    if (this.mVelocityTracker == null) {
                        this.mVelocityTracker = VelocityTracker.obtain();
                    }
                    this.mVelocityTracker.addMovement(motionEvent);
                    ((BubbleStackView.AnonymousClass9) motionEventListener).onMove(y);
                    return true;
                }
                return true;
            }
        } else if (this.mTrackingTouches) {
            if (this.mInterceptingTouches) {
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                this.mVelocityTracker.computeCurrentVelocity(1000);
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                this.mVelocityTracker.getXVelocity();
                if (this.mVelocityTracker == null) {
                    this.mVelocityTracker = VelocityTracker.obtain();
                }
                ((BubbleStackView.AnonymousClass9) motionEventListener).onUp(this.mVelocityTracker.getYVelocity());
            }
            this.mTouchDown.set(0.0f, 0.0f);
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
