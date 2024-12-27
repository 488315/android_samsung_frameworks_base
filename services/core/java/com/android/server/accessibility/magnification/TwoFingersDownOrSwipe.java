package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.android.server.accessibility.gestures.GestureMatcher;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TwoFingersDownOrSwipe extends GestureMatcher {
    public final int mDetectionDurationMillis;
    public final int mDoubleTapTimeout;
    public MotionEvent mFirstPointerDown;
    public MotionEvent mSecondPointerDown;
    public final int mSwipeMinDistance;

    public TwoFingersDownOrSwipe(Context context) {
        super(101, new Handler(context.getMainLooper()), null);
        this.mDetectionDurationMillis = context.getResources().getInteger(R.integer.config_zen_repeat_callers_threshold) + ViewConfiguration.getDoubleTapTimeout();
        this.mDoubleTapTimeout = ViewConfiguration.getDoubleTapTimeout();
        this.mSwipeMinDistance = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public static double distance(MotionEvent motionEvent, MotionEvent motionEvent2) {
        if (motionEvent2.findPointerIndex(motionEvent.getPointerId(motionEvent.getActionIndex())) < 0) {
            return -1.0d;
        }
        return MathUtils.dist(motionEvent.getX(r0), motionEvent.getY(r0), motionEvent2.getX(r1), motionEvent2.getY(r1));
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        MotionEvent motionEvent = this.mFirstPointerDown;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mFirstPointerDown = null;
        }
        MotionEvent motionEvent2 = this.mSecondPointerDown;
        if (motionEvent2 != null) {
            motionEvent2.recycle();
            this.mSecondPointerDown = null;
        }
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        return "TwoFingersDownOrSwipe";
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        this.mFirstPointerDown = MotionEvent.obtain(motionEvent);
        long j = this.mDetectionDurationMillis;
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, j, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        MotionEvent motionEvent3 = this.mFirstPointerDown;
        if (motionEvent3 == null || this.mSecondPointerDown == null) {
            return;
        }
        double distance = distance(motionEvent3, motionEvent);
        int i2 = this.mSwipeMinDistance;
        if (distance > i2) {
            setState(2, motionEvent, motionEvent2, i);
        } else if (distance(this.mSecondPointerDown, motionEvent) > i2) {
            setState(2, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mFirstPointerDown == null) {
            setState(3, motionEvent, motionEvent2, i);
        }
        if (motionEvent.getPointerCount() != 2) {
            setState(3, motionEvent, motionEvent2, i);
            return;
        }
        this.mSecondPointerDown = MotionEvent.obtain(motionEvent);
        long j = this.mDoubleTapTimeout;
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(2, j, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }
}
