package com.android.server.accessibility.magnification;

import android.R;
import android.content.Context;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.android.server.accessibility.gestures.GestureMatcher;
import com.android.server.accessibility.gestures.GestureUtils;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SimpleSwipe extends GestureMatcher {
    public final int mDetectionDurationMillis;
    public MotionEvent mLastDown;
    public final int mSwipeMinDistance;

    public SimpleSwipe(Context context) {
        super(102, new Handler(context.getMainLooper()), null);
        this.mSwipeMinDistance = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mDetectionDurationMillis =
                context.getResources().getInteger(R.integer.config_zen_repeat_callers_threshold)
                        + ViewConfiguration.getDoubleTapTimeout();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void clear() {
        MotionEvent motionEvent = this.mLastDown;
        if (motionEvent != null) {
            motionEvent.recycle();
        }
        this.mLastDown = null;
        super.clear();
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        return "SimpleSwipe";
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        this.mLastDown = MotionEvent.obtain(motionEvent);
        long j = this.mDetectionDurationMillis;
        this.mDelayedTransition.cancel();
        this.mDelayedTransition.post(3, j, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onMove(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        MotionEvent motionEvent3 = this.mLastDown;
        if (motionEvent3 == null
                || GestureUtils.distance(motionEvent3, motionEvent) <= this.mSwipeMinDistance) {
            return;
        }
        setState(2, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        setState(3, motionEvent, motionEvent2, i);
    }

    @Override // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        MotionEvent motionEvent3 = this.mLastDown;
        if (motionEvent3 == null
                || GestureUtils.distance(motionEvent3, motionEvent) <= this.mSwipeMinDistance) {
            setState(3, motionEvent, motionEvent2, i);
        } else {
            setState(2, motionEvent, motionEvent2, i);
        }
    }
}
