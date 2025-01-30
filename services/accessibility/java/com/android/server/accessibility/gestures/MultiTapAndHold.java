package com.android.server.accessibility.gestures;

import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class MultiTapAndHold extends MultiTap {
  public MultiTapAndHold(
      Context context, int i, int i2, GestureMatcher.StateChangeListener stateChangeListener) {
    super(context, i, i2, stateChangeListener);
  }

  @Override // com.android.server.accessibility.gestures.MultiTap,
  // com.android.server.accessibility.gestures.GestureMatcher
  public void onDown(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    super.onDown(motionEvent, motionEvent2, i);
    if (this.mCurrentTaps + 1 == this.mTargetTaps) {
      completeAfterLongPressTimeout(motionEvent, motionEvent2, i);
    }
  }

  @Override // com.android.server.accessibility.gestures.MultiTap,
  // com.android.server.accessibility.gestures.GestureMatcher
  public void onUp(MotionEvent motionEvent, MotionEvent motionEvent2, int i) {
    super.onUp(motionEvent, motionEvent2, i);
    cancelAfterDoubleTapTimeout(motionEvent, motionEvent2, i);
  }

  @Override // com.android.server.accessibility.gestures.MultiTap,
  // com.android.server.accessibility.gestures.GestureMatcher
  public String getGestureName() {
    int i = this.mTargetTaps;
    if (i == 2) {
      return "Double Tap and Hold";
    }
    if (i == 3) {
      return "Triple Tap and Hold";
    }
    return Integer.toString(this.mTargetTaps) + " Taps and Hold";
  }
}
