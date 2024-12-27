package com.android.server.accessibility.gestures;

import android.content.Context;
import android.graphics.PointF;
import android.net.INetd;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Slog;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Swipe extends GestureMatcher {
    public long mBaseTime;
    public float mBaseX;
    public float mBaseY;
    public final int[] mDirections;
    public final float mGestureDetectionThresholdPixels;
    public final float mMinPixelsBetweenSamplesX;
    public final float mMinPixelsBetweenSamplesY;
    public float mPreviousGestureX;
    public float mPreviousGestureY;
    public final ArrayList mStrokeBuffer;
    public final int mTouchSlop;

    public Swipe(
            Context context,
            int i,
            int i2,
            int i3,
            GestureMatcher.StateChangeListener stateChangeListener) {
        this(context, new int[] {i, i2}, i3, stateChangeListener);
    }

    public Swipe(
            Context context,
            int i,
            int i2,
            GestureMatcher.StateChangeListener stateChangeListener) {
        this(context, new int[] {i}, i2, stateChangeListener);
    }

    public Swipe(
            Context context,
            int[] iArr,
            int i,
            GestureMatcher.StateChangeListener stateChangeListener) {
        super(i, new Handler(context.getMainLooper()), stateChangeListener);
        this.mStrokeBuffer = new ArrayList(100);
        this.mDirections = iArr;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        this.mGestureDetectionThresholdPixels =
                TypedValue.applyDimension(5, 10, displayMetrics) * 1.0f;
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
        this.mBaseX = Float.NaN;
        this.mBaseY = Float.NaN;
        this.mBaseTime = 0L;
        this.mPreviousGestureX = Float.NaN;
        this.mPreviousGestureY = Float.NaN;
        this.mStrokeBuffer.clear();
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        StringBuilder sb = new StringBuilder("Swipe ");
        int[] iArr = this.mDirections;
        sb.append(directionToString(iArr[0]));
        for (int i = 1; i < iArr.length; i++) {
            sb.append(" and ");
            sb.append(directionToString(iArr[i]));
        }
        return sb.toString();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (Float.isNaN(this.mBaseX) && Float.isNaN(this.mBaseY)) {
            this.mBaseX = motionEvent2.getX();
            this.mBaseY = motionEvent2.getY();
            this.mBaseTime = motionEvent2.getEventTime();
            this.mPreviousGestureX = this.mBaseX;
            this.mPreviousGestureY = this.mBaseY;
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        float f;
        float f2;
        long j;
        float x = motionEvent2.getX();
        float y = motionEvent2.getY();
        long eventTime = motionEvent2.getEventTime();
        float abs = Math.abs(x - this.mPreviousGestureX);
        float abs2 = Math.abs(y - this.mPreviousGestureY);
        double hypot = Math.hypot(Math.abs(x - this.mBaseX), Math.abs(y - this.mBaseY));
        long j2 = eventTime - this.mBaseTime;
        boolean z = TouchExplorer.DEBUG;
        float f3 = this.mGestureDetectionThresholdPixels;
        if (z) {
            f2 = abs2;
            f = abs;
            Slog.d(
                    getGestureName(),
                    "moveDelta:"
                            + Double.toString(hypot)
                            + " mGestureDetectionThreshold: "
                            + Float.toString(f3));
        } else {
            f = abs;
            f2 = abs2;
        }
        if (this.mState == 0) {
            j = j2;
            if (hypot < this.mTouchSlop) {
                return;
            }
            if (this.mStrokeBuffer.size() == 0) {
                if (toDirection(x - this.mBaseX, y - this.mBaseY) != this.mDirections[0]) {
                    setState(3, motionEvent, motionEvent2, i);
                    return;
                }
                this.mStrokeBuffer.add(new PointF(this.mBaseX, this.mBaseY));
            }
        } else {
            j = j2;
        }
        if (hypot > f3) {
            this.mBaseX = x;
            this.mBaseY = y;
            this.mBaseTime = eventTime;
            setState(1, motionEvent, motionEvent2, i);
        } else {
            int i2 = this.mState;
            if (i2 == 0) {
                if (j > 150) {
                    setState(3, motionEvent, motionEvent2, i);
                    return;
                }
            } else if (i2 == 1 && j > 350) {
                setState(3, motionEvent, motionEvent2, i);
                return;
            }
        }
        if (f >= this.mMinPixelsBetweenSamplesX || f2 >= this.mMinPixelsBetweenSamplesY) {
            this.mPreviousGestureX = x;
            this.mPreviousGestureY = y;
            this.mStrokeBuffer.add(new PointF(x, y));
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        int i2;
        float f;
        int i3;
        float f2;
        if (this.mState != 1) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        float x = motionEvent2.getX();
        float y = motionEvent2.getY();
        float abs = Math.abs(x - this.mPreviousGestureX);
        float abs2 = Math.abs(y - this.mPreviousGestureY);
        if (abs >= this.mMinPixelsBetweenSamplesX || abs2 >= this.mMinPixelsBetweenSamplesY) {
            this.mStrokeBuffer.add(new PointF(x, y));
        }
        if (this.mStrokeBuffer.size() < 2) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        ArrayList arrayList = new ArrayList();
        PointF pointF = (PointF) this.mStrokeBuffer.get(0);
        arrayList.add(pointF);
        PointF pointF2 = null;
        int i4 = 1;
        int i5 = 0;
        float f3 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float f4 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        float f5 = FullScreenMagnificationGestureHandler.MAX_SCALE;
        while (i4 < this.mStrokeBuffer.size()) {
            pointF2 = (PointF) this.mStrokeBuffer.get(i4);
            if (i5 > 0) {
                float f6 = i5;
                float f7 = f3 / f6;
                float f8 = f4 / f6;
                PointF pointF3 = new PointF((f5 * f7) + pointF.x, (f5 * f8) + pointF.y);
                float f9 = pointF2.x - pointF3.x;
                float f10 = pointF2.y - pointF3.y;
                i2 = i5;
                f = f3;
                float sqrt = (float) Math.sqrt((f10 * f10) + (f9 * f9));
                if ((f8 * (f10 / sqrt)) + (f7 * (f9 / sqrt))
                        < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    arrayList.add(pointF3);
                    f2 = 0.0f;
                    f4 = 0.0f;
                    pointF = pointF3;
                    i3 = 0;
                    float f11 = pointF2.x - pointF.x;
                    float f12 = pointF2.y - pointF.y;
                    float sqrt2 = (float) Math.sqrt((f12 * f12) + (f11 * f11));
                    i5 = i3 + 1;
                    f3 = f2 + (f11 / sqrt2);
                    f4 += f12 / sqrt2;
                    i4++;
                    f5 = sqrt2;
                }
            } else {
                i2 = i5;
                f = f3;
            }
            i3 = i2;
            f2 = f;
            float f112 = pointF2.x - pointF.x;
            float f122 = pointF2.y - pointF.y;
            float sqrt22 = (float) Math.sqrt((f122 * f122) + (f112 * f112));
            i5 = i3 + 1;
            f3 = f2 + (f112 / sqrt22);
            f4 += f122 / sqrt22;
            i4++;
            f5 = sqrt22;
        }
        arrayList.add(pointF2);
        if (TouchExplorer.DEBUG) {
            Slog.d(getGestureName(), "path=" + arrayList.toString());
        }
        motionEvent.getDisplayId();
        int size = arrayList.size();
        int[] iArr = this.mDirections;
        if (size != iArr.length + 1) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int i6 = 0;
        while (i6 < arrayList.size() - 1) {
            PointF pointF4 = (PointF) arrayList.get(i6);
            int i7 = i6 + 1;
            PointF pointF5 = (PointF) arrayList.get(i7);
            int direction = toDirection(pointF5.x - pointF4.x, pointF5.y - pointF4.y);
            if (direction != iArr[i6]) {
                if (TouchExplorer.DEBUG) {
                    Slog.d(
                            getGestureName(),
                            "Found direction "
                                    + directionToString(direction)
                                    + " when expecting "
                                    + directionToString(iArr[i6]));
                }
                setState(3, motionEvent, motionEvent2, i);
                return;
            }
            i6 = i7;
        }
        if (TouchExplorer.DEBUG) {
            Slog.d(getGestureName(), "Completed.");
        }
        setState(2, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.mState != 3) {
            sb.append(", mBaseX: ");
            sb.append(this.mBaseX);
            sb.append(", mBaseY: ");
            sb.append(this.mBaseY);
            sb.append(", mGestureDetectionThreshold:");
            sb.append(this.mGestureDetectionThresholdPixels);
            sb.append(", mMinPixelsBetweenSamplesX:");
            sb.append(this.mMinPixelsBetweenSamplesX);
            sb.append(", mMinPixelsBetweenSamplesY:");
            sb.append(this.mMinPixelsBetweenSamplesY);
        }
        return sb.toString();
    }
}
