package com.android.server.wm;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Handler;
import android.os.PowerManagerInternal;
import android.os.SystemClock;
import android.view.DisplayCutout;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import android.widget.OverScroller;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.multiwindow.MultiWindowEdgeDetector;
import com.samsung.android.rune.CoreRune;

public final class SystemGesturesPointerEventListener
        implements WindowManagerPolicyConstants.PointerEventListener {
    public final DisplayPolicy.AnonymousClass1 mCallbacks;
    public final Context mContext;
    public boolean mDebugFireable;
    public DisplayContent mDisplayContent;
    public int mDisplayCutoutTouchableRegionSize;
    public int mDownPointers;
    public AnonymousClass1 mGestureDetector;
    public final Handler mHandler;
    public long mLastFlingTime;
    public boolean mMouseHoveringAtBottom;
    public boolean mMouseHoveringAtLeft;
    public boolean mMouseHoveringAtRight;
    public boolean mMouseHoveringAtTop;
    public MultiWindowEdgeDetector mMultiWindowEdgeDetector;
    public int mSwipeDistanceThreshold;
    public boolean mSwipeFireable;
    public int screenHeight;
    public int screenWidth;
    public final Rect mSwipeStartThreshold = new Rect();
    public final int[] mDownPointerId = new int[32];
    public final float[] mDownX = new float[32];
    public final float[] mDownY = new float[32];
    public final long[] mDownTime = new long[32];

    /* renamed from: com.android.server.wm.SystemGesturesPointerEventListener$1, reason: invalid class name */
    public final class AnonymousClass1 extends GestureDetector {}

    public final class FlingGestureDetector extends GestureDetector.SimpleOnGestureListener {
        public final OverScroller mOverscroller;

        public FlingGestureDetector() {
            this.mOverscroller = new OverScroller(SystemGesturesPointerEventListener.this.mContext);
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnGestureListener
        public final boolean onFling(
                MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            PowerManagerInternal powerManagerInternal;
            this.mOverscroller.computeScrollOffset();
            long uptimeMillis = SystemClock.uptimeMillis();
            long j = SystemGesturesPointerEventListener.this.mLastFlingTime;
            if (j != 0 && uptimeMillis > j + 5000) {
                this.mOverscroller.forceFinished(true);
            }
            this.mOverscroller.fling(
                    0,
                    0,
                    (int) f,
                    (int) f2,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE,
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE);
            int duration = this.mOverscroller.getDuration();
            if (duration > 5000) {
                duration = 5000;
            }
            SystemGesturesPointerEventListener systemGesturesPointerEventListener =
                    SystemGesturesPointerEventListener.this;
            systemGesturesPointerEventListener.mLastFlingTime = uptimeMillis;
            DisplayPolicy.AnonymousClass1 anonymousClass1 =
                    systemGesturesPointerEventListener.mCallbacks;
            anonymousClass1.getClass();
            if (!CoreRune.FW_VRR_PERFORMANCE
                    && (powerManagerInternal = DisplayPolicy.this.mService.mPowerManagerInternal)
                            != null) {
                powerManagerInternal.setPowerBoost(0, duration);
            }
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener,
                  // android.view.GestureDetector.OnGestureListener
        public final boolean onSingleTapUp(MotionEvent motionEvent) {
            if (!this.mOverscroller.isFinished()) {
                this.mOverscroller.forceFinished(true);
            }
            return true;
        }
    }

    public SystemGesturesPointerEventListener(
            Context context,
            DisplayPolicy.PolicyHandler policyHandler,
            DisplayPolicy.AnonymousClass1 anonymousClass1) {
        if (context == null) {
            throw new IllegalArgumentException("context".concat(" must not be null"));
        }
        this.mContext = context;
        this.mHandler = policyHandler;
        this.mCallbacks = anonymousClass1;
        onConfigurationChanged();
    }

    public final void captureDown(int i, MotionEvent motionEvent) {
        int findIndex = findIndex(motionEvent.getPointerId(i));
        if (findIndex != -1) {
            this.mDownX[findIndex] = motionEvent.getX(i);
            this.mDownY[findIndex] = motionEvent.getY(i);
            this.mDownTime[findIndex] = motionEvent.getEventTime();
        }
    }

    public final int detectSwipe(int i, long j, float f, float f2) {
        float f3 = this.mDownX[i];
        float f4 = this.mDownY[i];
        long j2 = j - this.mDownTime[i];
        Rect rect = this.mSwipeStartThreshold;
        if (f4 <= rect.top && f2 > this.mSwipeDistanceThreshold + f4 && j2 < 500) {
            return 1;
        }
        if (f4 >= this.screenHeight - rect.bottom
                && f2 < f4 - this.mSwipeDistanceThreshold
                && j2 < 500) {
            return 2;
        }
        if (f3 < this.screenWidth - rect.right
                || f >= f3 - this.mSwipeDistanceThreshold
                || j2 >= 500) {
            return (f3 > ((float) rect.left)
                            || f <= f3 + ((float) this.mSwipeDistanceThreshold)
                            || j2 >= 500)
                    ? 0
                    : 4;
        }
        return 3;
    }

    public final void detectSystemGestureForDex(MotionEvent motionEvent) {
        if (!this.mMouseHoveringAtTop
                && motionEvent.getY() == FullScreenMagnificationGestureHandler.MAX_SCALE) {
            DisplayPolicy.AnonymousClass1 anonymousClass1 = this.mCallbacks;
            DisplayPolicy displayPolicy = DisplayPolicy.this;
            displayPolicy.mHandler.removeCallbacks(anonymousClass1.mOnSwipeFromTop);
            displayPolicy.mHandler.postDelayed(anonymousClass1.mOnSwipeFromTop, 500L);
            this.mMouseHoveringAtTop = true;
            return;
        }
        if (this.mMouseHoveringAtTop
                && motionEvent.getY() > FullScreenMagnificationGestureHandler.MAX_SCALE
                && motionEvent.getY() < this.screenHeight - 22) {
            DisplayPolicy.AnonymousClass1 anonymousClass12 = this.mCallbacks;
            DisplayPolicy.this.mHandler.removeCallbacks(anonymousClass12.mOnSwipeFromTop);
            this.mMouseHoveringAtTop = false;
        } else if (!this.mMouseHoveringAtBottom && motionEvent.getY() >= this.screenHeight - 22) {
            this.mCallbacks.onMouseHoverAtBottom();
            this.mMouseHoveringAtBottom = true;
        } else {
            if (!this.mMouseHoveringAtBottom || motionEvent.getY() >= this.screenHeight - 22) {
                return;
            }
            DisplayPolicy.AnonymousClass1 anonymousClass13 = this.mCallbacks;
            DisplayPolicy.this.mHandler.removeCallbacks(anonymousClass13.mOnSwipeFromBottom);
            this.mMouseHoveringAtBottom = false;
        }
    }

    public final int findIndex(int i) {
        int i2 = 0;
        while (true) {
            int i3 = this.mDownPointers;
            if (i2 >= i3) {
                if (i3 == 32 || i == -1) {
                    return -1;
                }
                int[] iArr = this.mDownPointerId;
                this.mDownPointers = i3 + 1;
                iArr[i3] = i;
                return i3;
            }
            if (this.mDownPointerId[i2] == i) {
                return i2;
            }
            i2++;
        }
    }

    public final void onConfigurationChanged() {
        MultiWindowEdgeDetector multiWindowEdgeDetector = this.mMultiWindowEdgeDetector;
        if (multiWindowEdgeDetector != null) {
            multiWindowEdgeDetector.onConfigurationChanged();
        }
        Resources resources = this.mContext.getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(17106300);
        this.mSwipeStartThreshold.set(
                dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mSwipeDistanceThreshold = resources.getDimensionPixelSize(17106299);
        DisplayCutout cutout = DisplayManagerGlobal.getInstance().getRealDisplay(0).getCutout();
        if (cutout != null) {
            this.mDisplayCutoutTouchableRegionSize =
                    resources.getDimensionPixelSize(R.dimen.harmful_app_name_padding_right);
            Rect[] boundingRectsAll = cutout.getBoundingRectsAll();
            Rect rect = boundingRectsAll[0];
            if (rect != null) {
                Rect rect2 = this.mSwipeStartThreshold;
                rect2.left =
                        Math.max(rect2.left, rect.width() + this.mDisplayCutoutTouchableRegionSize);
            }
            Rect rect3 = boundingRectsAll[1];
            if (rect3 != null) {
                Rect rect4 = this.mSwipeStartThreshold;
                rect4.top =
                        Math.max(
                                rect4.top, rect3.height() + this.mDisplayCutoutTouchableRegionSize);
            }
            Rect rect5 = boundingRectsAll[2];
            if (rect5 != null) {
                Rect rect6 = this.mSwipeStartThreshold;
                rect6.right =
                        Math.max(
                                rect6.right,
                                rect5.width() + this.mDisplayCutoutTouchableRegionSize);
            }
            Rect rect7 = boundingRectsAll[3];
            if (rect7 != null) {
                Rect rect8 = this.mSwipeStartThreshold;
                rect8.bottom =
                        Math.max(
                                rect8.bottom,
                                rect7.height() + this.mDisplayCutoutTouchableRegionSize);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x0196  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x019b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPointerEvent(android.view.MotionEvent r22) {
        /*
            Method dump skipped, instructions count: 761
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.SystemGesturesPointerEventListener.onPointerEvent(android.view.MotionEvent):void");
    }
}
