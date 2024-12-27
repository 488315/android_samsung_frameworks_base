package com.android.server.accessibility.gestures;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.internal.util.Preconditions;
import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class MultiFingerMultiTap extends GestureMatcher {
    public final PointF[] mBases;
    public int mCompletedTapCount;
    public final int mDoubleTapSlop;
    public final ArrayList mExcludedPointsForDownSlopChecked;
    public boolean mIsTargetFingerCountReached;
    public final int mTargetFingerCount;
    public final int mTargetTapCount;
    public final int mTouchSlop;

    public MultiFingerMultiTap(Context context, int i, int i2, int i3, GestureMatcher.StateChangeListener stateChangeListener) {
        super(i3, new Handler(context.getMainLooper()), stateChangeListener);
        int i4 = 0;
        this.mIsTargetFingerCountReached = false;
        Preconditions.checkArgument(i >= 2);
        Preconditions.checkArgumentPositive(i2, "Tap count must greater than 0.");
        this.mTargetTapCount = i2;
        this.mTargetFingerCount = i;
        this.mDoubleTapSlop = ViewConfiguration.get(context).getScaledDoubleTapSlop() * i;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() * i;
        this.mBases = new PointF[i];
        while (true) {
            PointF[] pointFArr = this.mBases;
            if (i4 >= pointFArr.length) {
                this.mExcludedPointsForDownSlopChecked = new ArrayList(this.mTargetFingerCount);
                clear();
                return;
            } else {
                pointFArr[i4] = new PointF();
                i4++;
            }
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        int i = 0;
        this.mCompletedTapCount = 0;
        this.mIsTargetFingerCountReached = false;
        while (true) {
            PointF[] pointFArr = this.mBases;
            if (i >= pointFArr.length) {
                this.mExcludedPointsForDownSlopChecked.clear();
                super.clear();
                return;
            } else {
                pointFArr[i].set(Float.NaN, Float.NaN);
                i++;
            }
        }
    }

    public final PointF findNearestPoint(MotionEvent motionEvent, float f, boolean z) {
        float f2 = Float.MAX_VALUE;
        int i = 0;
        PointF pointF = null;
        while (true) {
            PointF[] pointFArr = this.mBases;
            if (i >= pointFArr.length) {
                if (f2 >= f) {
                    return null;
                }
                if (z) {
                    this.mExcludedPointsForDownSlopChecked.add(pointF);
                }
                return pointF;
            }
            PointF pointF2 = pointFArr[i];
            if ((!Float.isNaN(pointF2.x) || !Float.isNaN(pointF2.y)) && (!z || !this.mExcludedPointsForDownSlopChecked.contains(pointF2))) {
                int actionIndex = motionEvent.getActionIndex();
                float x = pointF2.x - motionEvent.getX(actionIndex);
                float y = pointF2.y - motionEvent.getY(actionIndex);
                if (x == FullScreenMagnificationGestureHandler.MAX_SCALE && y == FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    if (z) {
                        this.mExcludedPointsForDownSlopChecked.add(pointF2);
                    }
                    return pointF2;
                }
                float hypot = (float) Math.hypot(x, y);
                if (f2 > hypot) {
                    pointF = pointF2;
                    f2 = hypot;
                }
            }
            i++;
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public String getGestureName() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.mTargetFingerCount);
        sb.append("-Finger ");
        int i = this.mTargetTapCount;
        if (i == 1) {
            sb.append("Single");
        } else if (i == 2) {
            sb.append("Double");
        } else if (i == 3) {
            sb.append("Triple");
        } else if (i > 3) {
            sb.append(i);
        }
        sb.append(" Tap");
        return sb.toString();
    }

    public final PointF initBaseLocation(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        PointF pointF = this.mBases[motionEvent.getPointerCount() - 1];
        if (Float.isNaN(pointF.x) && Float.isNaN(pointF.y)) {
            pointF.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        }
        return pointF;
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mCompletedTapCount == this.mTargetTapCount) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        long tapTimeout = ViewConfiguration.getTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, tapTimeout, motionEvent, motionEvent2, i);
        if (this.mCompletedTapCount == 0) {
            initBaseLocation(motionEvent2);
            return;
        }
        PointF findNearestPoint = findNearestPoint(motionEvent2, this.mDoubleTapSlop, true);
        if (findNearestPoint == null) {
            setState(3, motionEvent, motionEvent2, i);
        } else {
            int actionIndex = motionEvent.getActionIndex();
            findNearestPoint.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (findNearestPoint(motionEvent2, this.mTouchSlop, false) == null) {
            setState(3, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        long tapTimeout = ViewConfiguration.getTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, tapTimeout, motionEvent, motionEvent2, i);
        int pointerCount = motionEvent.getPointerCount();
        int i2 = this.mTargetFingerCount;
        if (pointerCount > i2 || this.mIsTargetFingerCountReached) {
            this.mIsTargetFingerCountReached = false;
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        PointF initBaseLocation = this.mCompletedTapCount == 0 ? initBaseLocation(motionEvent2) : findNearestPoint(motionEvent2, this.mDoubleTapSlop, true);
        int i3 = this.mState;
        if ((i3 != 1 && i3 != 0) || initBaseLocation == null) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        if (pointerCount == i2) {
            this.mIsTargetFingerCountReached = true;
        }
        int actionIndex = motionEvent.getActionIndex();
        initBaseLocation.set(motionEvent.getX(actionIndex), motionEvent.getY(actionIndex));
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (!this.mIsTargetFingerCountReached) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        int i2 = this.mState;
        if (i2 != 1 && i2 != 0) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        long tapTimeout = ViewConfiguration.getTapTimeout();
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, tapTimeout, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        PointF findNearestPoint = findNearestPoint(motionEvent2, this.mTouchSlop, false);
        int i2 = this.mState;
        if ((i2 != 1 && i2 != 0) || findNearestPoint == null) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        if (this.mIsTargetFingerCountReached) {
            this.mCompletedTapCount++;
            this.mIsTargetFingerCountReached = false;
            this.mExcludedPointsForDownSlopChecked.clear();
        }
        if (this.mCompletedTapCount == 1) {
            setState(1, motionEvent, motionEvent2, i);
        }
        if (this.mCompletedTapCount == this.mTargetTapCount) {
            long doubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
            this.mDelayedTransition.cancel();
            this.mDelayedTransition.post(2, doubleTapTimeout, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (this.mState != 3) {
            sb.append(", CompletedTapCount: ");
            sb.append(this.mCompletedTapCount);
            sb.append(", IsTargetFingerCountReached: ");
            sb.append(this.mIsTargetFingerCountReached);
            sb.append(", Bases: ");
            sb.append(Arrays.toString(this.mBases));
            sb.append(", ExcludedPointsForDownSlopChecked: ");
            sb.append(this.mExcludedPointsForDownSlopChecked.toString());
        }
        return sb.toString();
    }
}
