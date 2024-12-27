package com.android.server.accessibility.gestures;

import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemMultiFingerMultiTapAndHold extends MultiFingerMultiTapAndHold {
    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold, com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public final void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onPointerDown(motionEvent, motionEvent2, i);
        if (this.mIsTargetFingerCountReached && this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            this.mDelayedTransition.cancel();
            this.mDelayedTransition.post(2, 5000L, motionEvent, motionEvent2, i);
        }
        if (motionEvent.getPointerCount() > this.mTargetFingerCount) {
            setState(3, motionEvent, motionEvent2, i);
        }
    }
}
