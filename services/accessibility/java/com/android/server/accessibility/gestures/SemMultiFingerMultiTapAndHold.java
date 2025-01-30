package com.android.server.accessibility.gestures;

import android.content.Context;
import android.view.MotionEvent;
import com.android.server.accessibility.gestures.GestureMatcher;

/* loaded from: classes.dex */
public class SemMultiFingerMultiTapAndHold extends MultiFingerMultiTapAndHold {
    public SemMultiFingerMultiTapAndHold(Context context, int i, int i2, int i3, GestureMatcher.StateChangeListener stateChangeListener) {
        super(context, i, i2, i3, stateChangeListener);
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTapAndHold, com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onPointerDown(motionEvent, motionEvent2, i);
        if (this.mIsTargetFingerCountReached && this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            completeAfter(5000L, motionEvent, motionEvent2, i);
        }
        if (motionEvent.getPointerCount() > this.mTargetFingerCount) {
            cancelGesture(motionEvent, motionEvent2, i);
        }
    }
}
