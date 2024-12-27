package com.android.server.accessibility.gestures;

import android.content.Context;
import android.graphics.PointF;
import android.net.INetd;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MultiFingerSwipe extends GestureMatcher {
    public final PointF[] mBase;
    public int mCurrentFingerCount;
    public final int mDirection;
    public final float mMinPixelsBetweenSamplesX;
    public final float mMinPixelsBetweenSamplesY;
    public final int[] mPointerIds;
    public final PointF[] mPreviousGesturePoint;
    public final ArrayList[] mStrokeBuffers;
    public final int mTargetFingerCount;
    public boolean mTargetFingerCountReached;
    public final int mTouchSlop;

    public MultiFingerSwipe(
            Context context,
            int i,
            int i2,
            int i3,
            GestureMatcher.StateChangeListener stateChangeListener) {
        super(i3, new Handler(context.getMainLooper()), stateChangeListener);
        this.mTargetFingerCountReached = false;
        this.mTargetFingerCount = i;
        this.mPointerIds = new int[i];
        this.mBase = new PointF[i];
        this.mPreviousGesturePoint = new PointF[i];
        this.mStrokeBuffers = new ArrayList[i];
        this.mDirection = i2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float f = displayMetrics.xdpi / 2.54f;
        float f2 = displayMetrics.ydpi / 2.54f;
        this.mMinPixelsBetweenSamplesX = f * 0.25f;
        this.mMinPixelsBetweenSamplesY = f2 * 0.25f;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        clear();
    }

    public static String directionToString(int i) {
        return i != 0
                ? i != 1
                        ? i != 2
                                ? i != 3 ? "Unknown Direction" : INetd.IF_STATE_DOWN
                                : INetd.IF_STATE_UP
                        : "right"
                : "left";
    }

    public static int toDirection(float f, float f2) {
        return Math.abs(f) > Math.abs(f2)
                ? f < FullScreenMagnificationGestureHandler.MAX_SCALE ? 0 : 1
                : f2 < FullScreenMagnificationGestureHandler.MAX_SCALE ? 2 : 3;
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        this.mTargetFingerCountReached = false;
        this.mCurrentFingerCount = 0;
        for (int i = 0; i < this.mTargetFingerCount; i++) {
            this.mPointerIds[i] = -1;
            PointF[] pointFArr = this.mBase;
            if (pointFArr[i] == null) {
                pointFArr[i] = new PointF();
            }
            PointF pointF = this.mBase[i];
            pointF.x = Float.NaN;
            pointF.y = Float.NaN;
            PointF[] pointFArr2 = this.mPreviousGesturePoint;
            if (pointFArr2[i] == null) {
                pointFArr2[i] = new PointF();
            }
            PointF pointF2 = this.mPreviousGesturePoint[i];
            pointF2.x = Float.NaN;
            pointF2.y = Float.NaN;
            ArrayList[] arrayListArr = this.mStrokeBuffers;
            if (arrayListArr[i] == null) {
                arrayListArr[i] = new ArrayList(100);
            }
            this.mStrokeBuffers[i].clear();
        }
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        return this.mTargetFingerCount + "-finger Swipe " + directionToString(this.mDirection);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mCurrentFingerCount > 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount = 1;
        int actionIndex = GestureUtils.getActionIndex(motionEvent2);
        int pointerId = motionEvent2.getPointerId(actionIndex);
        int pointerCount = motionEvent2.getPointerCount() - 1;
        if (pointerId < 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int[] iArr = this.mPointerIds;
        if (iArr[pointerCount] != -1) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        iArr[pointerCount] = pointerId;
        if (!Float.isNaN(this.mBase[pointerCount].x) || !Float.isNaN(this.mBase[pointerCount].y)) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < FullScreenMagnificationGestureHandler.MAX_SCALE
                || y < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        PointF pointF = this.mBase[pointerCount];
        pointF.x = x;
        pointF.y = y;
        PointF pointF2 = this.mPreviousGesturePoint[pointerCount];
        pointF2.x = x;
        pointF2.y = y;
    }

    /* JADX WARN: Code restructure failed: missing block: B:58:0x0120, code lost:

       setState(3, r18, r19, r20);
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0123, code lost:

       return;
    */
    @Override // com.android.server.accessibility.gestures.GestureMatcher
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMove(android.view.MotionEvent r18, android.view.MotionEvent r19, int r20) {
        /*
            Method dump skipped, instructions count: 292
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.accessibility.gestures.MultiFingerSwipe.onMove(android.view.MotionEvent,"
                    + " android.view.MotionEvent, int):void");
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int pointerCount = motionEvent.getPointerCount();
        int i2 = this.mTargetFingerCount;
        if (pointerCount > i2) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int i3 = this.mCurrentFingerCount + 1;
        this.mCurrentFingerCount = i3;
        if (i3 != motionEvent2.getPointerCount()) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        if (this.mCurrentFingerCount == i2) {
            this.mTargetFingerCountReached = true;
        }
        int actionIndex = GestureUtils.getActionIndex(motionEvent2);
        int pointerId = motionEvent2.getPointerId(actionIndex);
        if (pointerId < 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int i4 = this.mCurrentFingerCount - 1;
        int[] iArr = this.mPointerIds;
        if (iArr[i4] != -1) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        iArr[i4] = pointerId;
        if (!Float.isNaN(this.mBase[i4].x) || !Float.isNaN(this.mBase[i4].y)) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < FullScreenMagnificationGestureHandler.MAX_SCALE
                || y < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        PointF pointF = this.mBase[i4];
        pointF.x = x;
        pointF.y = y;
        PointF pointF2 = this.mPreviousGesturePoint[i4];
        pointF2.x = x;
        pointF2.y = y;
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (!this.mTargetFingerCountReached) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount--;
        int actionIndex = GestureUtils.getActionIndex(motionEvent);
        int pointerId = motionEvent.getPointerId(actionIndex);
        if (pointerId < 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int binarySearch = Arrays.binarySearch(this.mPointerIds, pointerId);
        if (binarySearch < 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < FullScreenMagnificationGestureHandler.MAX_SCALE
                || y < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float abs = Math.abs(x - this.mPreviousGesturePoint[binarySearch].x);
        float abs2 = Math.abs(y - this.mPreviousGesturePoint[binarySearch].y);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffers[binarySearch].add(new PointF(x, y));
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mState != 1) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        this.mCurrentFingerCount = 0;
        int actionIndex = GestureUtils.getActionIndex(motionEvent);
        int binarySearch =
                Arrays.binarySearch(this.mPointerIds, motionEvent.getPointerId(actionIndex));
        if (binarySearch < 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX(actionIndex);
        float y = motionEvent2.getY(actionIndex);
        if (x < FullScreenMagnificationGestureHandler.MAX_SCALE
                || y < FullScreenMagnificationGestureHandler.MAX_SCALE) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float abs = Math.abs(x - this.mPreviousGesturePoint[binarySearch].x);
        float abs2 = Math.abs(y - this.mPreviousGesturePoint[binarySearch].y);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffers[binarySearch].add(new PointF(x, y));
        }
        for (int i2 = 0; i2 < this.mTargetFingerCount; i2++) {
            boolean z = TouchExplorer.DEBUG;
            if (z) {
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        i2, "Recognizing finger: ", getGestureName());
            }
            if (this.mStrokeBuffers[i2].size() < 2) {
                Slog.d(getGestureName(), "Too few points.");
                setState(3, motionEvent, motionEvent2, i);
                return;
            }
            ArrayList arrayList = this.mStrokeBuffers[i2];
            if (z) {
                Slog.d(getGestureName(), "path=" + arrayList.toString());
            }
            motionEvent.getDisplayId();
            int i3 = 0;
            while (i3 < arrayList.size() - 1) {
                PointF pointF = (PointF) arrayList.get(i3);
                i3++;
                PointF pointF2 = (PointF) arrayList.get(i3);
                int direction = toDirection(pointF2.x - pointF.x, pointF2.y - pointF.y);
                int i4 = this.mDirection;
                if (direction != i4) {
                    if (TouchExplorer.DEBUG) {
                        Slog.d(
                                getGestureName(),
                                "Found direction "
                                        + directionToString(direction)
                                        + " when expecting "
                                        + directionToString(i4));
                    }
                    setState(3, motionEvent, motionEvent2, i);
                    return;
                }
            }
            if (TouchExplorer.DEBUG) {
                Slog.d(getGestureName(), "Completed.");
            }
        }
        setState(2, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.mState != 3) {
            sb.append(", mBase: ");
            sb.append(Arrays.toString(this.mBase));
            sb.append(", mMinPixelsBetweenSamplesX:");
            sb.append(this.mMinPixelsBetweenSamplesX);
            sb.append(", mMinPixelsBetweenSamplesY:");
            sb.append(this.mMinPixelsBetweenSamplesY);
        }
        return sb.toString();
    }
}
