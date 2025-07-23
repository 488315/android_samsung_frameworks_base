package com.android.internal.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/* loaded from: classes5.dex */
public class WearGestureInterceptionDetector {
    private static final boolean DEBUG = false;
    private static final String TAG = "WearGestureInterceptionDetector";
    private int mActivePointerId;
    private boolean mDiscardIntercept;
    private float mDownX;
    private float mDownY;
    private final DecorView mInstalledDecorView;
    private boolean mSwiping;
    private final float mSwipingStartThreshold;
    private final float mTouchSlop;

    WearGestureInterceptionDetector(Context context, DecorView decorView) {
        float scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTouchSlop = scaledTouchSlop;
        this.mInstalledDecorView = decorView;
        this.mSwipingStartThreshold = scaledTouchSlop * 2.0f;
    }

    public static boolean isEnabled(Context context) {
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            return false;
        }
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(new int[]{16843763});
        boolean z = obtainStyledAttributes.getIndexCount() > 0 ? obtainStyledAttributes.getBoolean(0, true) : true;
        obtainStyledAttributes.recycle();
        return z;
    }

    private int getIndexForValidPointer(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            this.mDiscardIntercept = true;
        }
        return findPointerIndex;
    }

    private void updateSwiping(MotionEvent motionEvent) {
        if (this.mSwiping) {
            return;
        }
        float rawX = motionEvent.getRawX() - this.mDownX;
        float rawY = motionEvent.getRawY() - this.mDownY;
        float f = (rawX * rawX) + (rawY * rawY);
        float f2 = this.mTouchSlop;
        if (f > f2 * f2) {
            this.mSwiping = rawX > this.mSwipingStartThreshold && Math.abs(rawY) < Math.abs(rawX);
        }
    }

    private void updateDiscardIntercept(MotionEvent motionEvent, int i) {
        if (this.mSwiping && !this.mDiscardIntercept) {
            if (canScroll(this.mInstalledDecorView, false, this.mDownX < motionEvent.getRawX(), motionEvent.getX(i), motionEvent.getY(i))) {
                this.mDiscardIntercept = true;
            }
        }
    }

    private void resetMembers() {
        this.mDownX = 0.0f;
        this.mDownY = 0.0f;
        this.mSwiping = false;
        this.mDiscardIntercept = false;
    }

    public boolean isIntercepting() {
        return !this.mDiscardIntercept && this.mSwiping;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int indexForValidPointer;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            resetMembers();
            this.mDownX = motionEvent.getRawX();
            this.mDownY = motionEvent.getRawY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            this.mActivePointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
                        } else if (actionMasked == 6) {
                            int actionIndex = motionEvent.getActionIndex();
                            if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
                                this.mActivePointerId = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                            }
                        }
                    }
                } else if (!this.mDiscardIntercept && (indexForValidPointer = getIndexForValidPointer(motionEvent)) != -1) {
                    updateSwiping(motionEvent);
                    updateDiscardIntercept(motionEvent, indexForValidPointer);
                }
            }
            resetMembers();
        }
        return isIntercepting();
    }

    private boolean canScroll(View view, boolean z, boolean z2, float f, float f2) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                float f3 = scrollX + f;
                if (f3 >= childAt.getLeft() && f3 < childAt.getRight()) {
                    float f4 = f2 + scrollY;
                    if (f4 >= childAt.getTop() && f4 < childAt.getBottom() && canScroll(childAt, true, z2, f3 - childAt.getLeft(), f4 - childAt.getTop())) {
                        return true;
                    }
                }
            }
        }
        if (z) {
            return view.canScrollHorizontally(z2 ? -1 : 1);
        }
        return false;
    }
}
