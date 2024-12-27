package com.android.server.accessibility.gestures;

import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class MultiFingerMultiTapAndHold extends MultiFingerMultiTap {
    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public final String getGestureName() {
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
        sb.append(" Tap and hold");
        return sb.toString();
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public void onPointerDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        super.onPointerDown(motionEvent, motionEvent2, i);
        if (this.mIsTargetFingerCountReached && this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            long longPressTimeout = ViewConfiguration.getLongPressTimeout();
            this.mDelayedTransition.cancel();
            this.mDelayedTransition.post(2, longPressTimeout, motionEvent, motionEvent2, i);
        }
    }

    @Override // com.android.server.accessibility.gestures.MultiFingerMultiTap, com.android.server.accessibility.gestures.GestureMatcher
    public final void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
        if (this.mCompletedTapCount + 1 == this.mTargetTapCount) {
            setState(3, motionEvent, motionEvent2, i);
        } else {
            super.onUp(motionEvent, motionEvent2, i);
            cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
        }
    }
}
