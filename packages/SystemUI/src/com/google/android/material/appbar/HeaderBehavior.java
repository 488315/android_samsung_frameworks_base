package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class HeaderBehavior extends ViewOffsetBehavior {
    public int activePointerId;
    public FlingRunnable flingRunnable;
    public boolean isBeingDragged;
    public int lastMotionY;
    public boolean mHasNoSnapFlag;
    public int mLastInterceptTouchEvent;
    public int mLastTouchEvent;
    public OverScroller scroller;
    public int touchSlop;
    public VelocityTracker velocityTracker;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FlingRunnable implements Runnable {
        public final View layout;
        public final CoordinatorLayout parent;

        public FlingRunnable(CoordinatorLayout coordinatorLayout, View view) {
            this.parent = coordinatorLayout;
            this.layout = view;
        }

        @Override // java.lang.Runnable
        public final void run() {
            OverScroller overScroller;
            if (this.layout == null || (overScroller = HeaderBehavior.this.scroller) == null) {
                return;
            }
            if (!overScroller.computeScrollOffset()) {
                HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
                return;
            }
            HeaderBehavior headerBehavior = HeaderBehavior.this;
            headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
            View view = this.layout;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            view.postOnAnimation(this);
        }
    }

    public HeaderBehavior() {
        this.activePointerId = -1;
        this.touchSlop = -1;
    }

    public boolean canDragView(View view) {
        return false;
    }

    public int getMaxDragOffset(View view) {
        return -view.getHeight();
    }

    public int getScrollRangeForDragFling(View view) {
        return view.getHeight();
    }

    public void onFlingFinished(CoordinatorLayout coordinatorLayout, View view) {
        FlingRunnable flingRunnable = this.flingRunnable;
        if (flingRunnable != null) {
            view.removeCallbacks(flingRunnable);
            this.flingRunnable = null;
        }
    }

    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        int findPointerIndex;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get(coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        this.mLastInterceptTouchEvent = motionEvent.getAction();
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            int i = this.activePointerId;
            if (i != -1 && (findPointerIndex = motionEvent.findPointerIndex(i)) != -1) {
                int y = (int) motionEvent.getY(findPointerIndex);
                if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                    this.lastMotionY = y;
                    return true;
                }
            }
            return false;
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            int x = (int) motionEvent.getX();
            int y2 = (int) motionEvent.getY();
            boolean z = canDragView(view) && coordinatorLayout.isPointInChildBounds(view, x, y2);
            this.isBeingDragged = z;
            if (z) {
                this.lastMotionY = y2;
                this.activePointerId = motionEvent.getPointerId(0);
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }
                OverScroller overScroller = this.scroller;
                if (overScroller != null && !overScroller.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        VelocityTracker velocityTracker = this.velocityTracker;
        if (velocityTracker != null) {
            velocityTracker.addMovement(motionEvent);
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00e2 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00e3 A[RETURN] */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout r19, android.view.View r20, android.view.MotionEvent r21) {
        /*
            Method dump skipped, instructions count: 228
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.appbar.HeaderBehavior.onTouchEvent(androidx.coordinatorlayout.widget.CoordinatorLayout, android.view.View, android.view.MotionEvent):boolean");
    }

    public final void setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i) {
        setHeaderTopBottomOffset(coordinatorLayout, view, i, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        int clamp;
        int topAndBottomOffset = getTopAndBottomOffset();
        if (i2 == 0 || topAndBottomOffset < i2 || topAndBottomOffset > i3 || topAndBottomOffset == (clamp = MathUtils.clamp(i, i2, i3))) {
            return 0;
        }
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper != null) {
            viewOffsetHelper.setTopAndBottomOffset(clamp);
        } else {
            this.tempTopBottomOffset = clamp;
        }
        return topAndBottomOffset - clamp;
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.activePointerId = -1;
        this.touchSlop = -1;
    }
}
