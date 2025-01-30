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
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FlingRunnable implements Runnable {
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
                HeaderBehavior.this.onFlingFinished(this.layout, this.parent);
                return;
            }
            HeaderBehavior headerBehavior = HeaderBehavior.this;
            headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
            View view = this.layout;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.postOnAnimation(view, this);
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

    public void onFlingFinished(View view, CoordinatorLayout coordinatorLayout) {
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
            if (i == -1 || (findPointerIndex = motionEvent.findPointerIndex(i)) == -1) {
                return false;
            }
            int y = (int) motionEvent.getY(findPointerIndex);
            if (Math.abs(y - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = y;
                return true;
            }
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x00d4  */
    @Override // androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, View view, MotionEvent motionEvent) {
        VelocityTracker velocityTracker;
        VelocityTracker velocityTracker2;
        this.mLastTouchEvent = motionEvent.getAction();
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                int findPointerIndex = motionEvent.findPointerIndex(this.activePointerId);
                if (findPointerIndex == -1) {
                    return false;
                }
                int y = (int) motionEvent.getY(findPointerIndex);
                int i = this.lastMotionY - y;
                this.lastMotionY = y;
                scroll(coordinatorLayout, view, i, getMaxDragOffset(view), 0);
            } else if (actionMasked != 3) {
                if (actionMasked == 6) {
                    int i2 = motionEvent.getActionIndex() != 0 ? 0 : 1;
                    this.activePointerId = motionEvent.getPointerId(i2);
                    this.lastMotionY = (int) (motionEvent.getY(i2) + 0.5f);
                }
            }
            velocityTracker2 = this.velocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.addMovement(motionEvent);
            }
            return this.isBeingDragged;
        }
        if (this.mHasNoSnapFlag && (velocityTracker = this.velocityTracker) != null) {
            velocityTracker.addMovement(motionEvent);
            this.velocityTracker.computeCurrentVelocity(1000);
            float yVelocity = this.velocityTracker.getYVelocity(this.activePointerId);
            int i3 = -getScrollRangeForDragFling(view);
            FlingRunnable flingRunnable = this.flingRunnable;
            if (flingRunnable != null) {
                view.removeCallbacks(flingRunnable);
                this.flingRunnable = null;
            }
            if (this.scroller == null) {
                this.scroller = new OverScroller(view.getContext());
            }
            this.scroller.fling(0, getTopAndBottomOffset(), 0, Math.round(yVelocity), 0, 0, i3, 0);
            if (this.scroller.computeScrollOffset()) {
                FlingRunnable flingRunnable2 = new FlingRunnable(coordinatorLayout, view);
                this.flingRunnable = flingRunnable2;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api16Impl.postOnAnimation(view, flingRunnable2);
            } else {
                onFlingFinished(view, coordinatorLayout);
            }
        }
        this.isBeingDragged = false;
        this.activePointerId = -1;
        VelocityTracker velocityTracker3 = this.velocityTracker;
        if (velocityTracker3 != null) {
            velocityTracker3.recycle();
            this.velocityTracker = null;
        }
        velocityTracker2 = this.velocityTracker;
        if (velocityTracker2 != null) {
        }
        return this.isBeingDragged;
    }

    public final int scroll(CoordinatorLayout coordinatorLayout, View view, int i, int i2, int i3) {
        return setHeaderTopBottomOffset(coordinatorLayout, view, getTopBottomOffsetForScrollingSibling() - i, i2, i3);
    }

    public final void setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, View view, int i) {
        setHeaderTopBottomOffset(coordinatorLayout, view, i, VideoPlayer.MEDIA_ERROR_SYSTEM, Integer.MAX_VALUE);
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
