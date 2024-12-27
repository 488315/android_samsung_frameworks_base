package com.android.server.accessibility.gestures;

import android.view.MotionEvent;

public final class SemMultiFingerMultiTapAndHold extends MultiFingerMultiTapAndHold {
    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold,
              // com.android.server.accessibility.gestures.MultiFingerMultiTap,
              // com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onPointerDown(motionEvent, motionEvent2, i);
        if (this.mIsTargetFingerCountReached
                && this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            this.mDelayedTransition.cancel();
            this.mDelayedTransition.post(2, 5000L, motionEvent, motionEvent2, i);
        }
        if (motionEvent.getPointerCount() > this.mTargetFingerCount) {
            setState(3, motionEvent, motionEvent2, i);
        }
    }
}
