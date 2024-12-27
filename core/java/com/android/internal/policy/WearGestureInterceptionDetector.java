package com.android.internal.policy;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

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

    WearGestureInterceptionDetector(Context context, DecorView installedDecorView) {
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mInstalledDecorView = installedDecorView;
        this.mSwipingStartThreshold = this.mTouchSlop * 2.0f;
    }

    public static boolean isEnabled(Context context) {
        PackageManager pm = context.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_WATCH)) {
            return false;
        }
        TypedArray windowAttr = context.obtainStyledAttributes(new int[] {16843763});
        boolean windowSwipeToDismiss = true;
        if (windowAttr.getIndexCount() > 0) {
            windowSwipeToDismiss = windowAttr.getBoolean(0, true);
        }
        windowAttr.recycle();
        return windowSwipeToDismiss;
    }

    private int getIndexForValidPointer(MotionEvent ev) {
        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
        if (pointerIndex == -1) {
            this.mDiscardIntercept = true;
        }
        return pointerIndex;
    }

    private void updateSwiping(MotionEvent ev) {
        if (this.mSwiping) {
            return;
        }
        float deltaX = ev.getRawX() - this.mDownX;
        float deltaY = ev.getRawY() - this.mDownY;
        if ((deltaX * deltaX) + (deltaY * deltaY) > this.mTouchSlop * this.mTouchSlop) {
            this.mSwiping =
                    deltaX > this.mSwipingStartThreshold && Math.abs(deltaY) < Math.abs(deltaX);
        }
    }

    private void updateDiscardIntercept(MotionEvent ev, int pointerIndex) {
        if (!this.mSwiping || this.mDiscardIntercept) {
            return;
        }
        boolean checkLeft = this.mDownX < ev.getRawX();
        float x = ev.getX(pointerIndex);
        float y = ev.getY(pointerIndex);
        if (canScroll(this.mInstalledDecorView, false, checkLeft, x, y)) {
            this.mDiscardIntercept = true;
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

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int pointerIndex;
        switch (ev.getActionMasked()) {
            case 0:
                resetMembers();
                this.mDownX = ev.getRawX();
                this.mDownY = ev.getRawY();
                this.mActivePointerId = ev.getPointerId(0);
                break;
            case 1:
            case 3:
                resetMembers();
                break;
            case 2:
                if (!this.mDiscardIntercept && (pointerIndex = getIndexForValidPointer(ev)) != -1) {
                    updateSwiping(ev);
                    updateDiscardIntercept(ev, pointerIndex);
                    break;
                }
                break;
            case 5:
                this.mActivePointerId = ev.getPointerId(ev.getActionIndex());
                break;
            case 6:
                int associatedPointerIndex = ev.getActionIndex();
                if (ev.getPointerId(associatedPointerIndex) == this.mActivePointerId) {
                    int newActionIndex = associatedPointerIndex == 0 ? 1 : 0;
                    this.mActivePointerId = ev.getPointerId(newActionIndex);
                    break;
                }
                break;
        }
        return isIntercepting();
    }

    private boolean canScroll(View v, boolean checkSelf, boolean checkLeft, float x, float y) {
        if (v instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) v;
            int scrollX = v.getScrollX();
            int scrollY = v.getScrollY();
            int count = group.getChildCount();
            for (int i = count - 1; i >= 0; i--) {
                View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft()
                        && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop()
                        && y + scrollY < child.getBottom()
                        && canScroll(
                                child,
                                true,
                                checkLeft,
                                (x + scrollX) - child.getLeft(),
                                (y + scrollY) - child.getTop())) {
                    return true;
                }
            }
        }
        if (checkSelf) {
            if (v.canScrollHorizontally(checkLeft ? -1 : 1)) {
                return true;
            }
        }
        return false;
    }
}
