package com.android.server.accessibility.gestures;

import android.view.MotionEvent;
import android.view.ViewConfiguration;

public final class MultiTapAndHold extends MultiTap {
    @Override // com.android.server.accessibility.gestures.MultiTap,
              // com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
        int i = this.mTargetTaps;
        if (i == 2) {
            return "Double Tap and Hold";
        }
        if (i == 3) {
            return "Triple Tap and Hold";
        }
        return Integer.toString(i) + " Taps and Hold";
    }

    @Override // com.android.server.accessibility.gestures.MultiTap,
              // com.android.server.accessibility.gestures.GestureMatcher
    public final void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onDown(motionEvent, motionEvent2, i);
        if (this.mCurrentTaps + 1 == this.mTargetTaps) {
            long longPressTimeout = ViewConfiguration.getLongPressTimeout();
            this.mDelayedTransition.cancel();
            this.mDelayedTransition.post(2, longPressTimeout, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.MultiTap,
              // com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onUp(motionEvent, motionEvent2, i);
        cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
    }
}
