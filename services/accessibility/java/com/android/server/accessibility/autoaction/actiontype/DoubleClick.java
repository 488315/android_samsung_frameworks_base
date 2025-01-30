package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class DoubleClick extends CornerActionType {
  public InputManager mInputManager;
  public MotionEvent mLastMotionEvent;

  public static int getStringResId() {
    return R.string.action_bar_home_subtitle_description_format;
  }

  public DoubleClick(Context context, MotionEvent motionEvent) {
    this.mLastMotionEvent = null;
    this.mInputManager = (InputManager) context.getSystemService("input");
    this.mLastMotionEvent = MotionEvent.obtain(motionEvent);
  }

  public static DoubleClick createAction(Context context, MotionEvent motionEvent) {
    return new DoubleClick(context, motionEvent);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(int i) {
    if (this.mInputManager != null) {
      long uptimeMillis = SystemClock.uptimeMillis();
      click(uptimeMillis, uptimeMillis, i);
      long j = uptimeMillis + 50;
      click(j, j, i);
    }
  }

  public final void click(long j, long j2, int i) {
    int actionIndex = this.mLastMotionEvent.getActionIndex();
    MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
    this.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoordsArr[0]);
    MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[0];
    MotionEvent obtain = MotionEvent.obtain(j, j2, 0, pointerCoords.x, pointerCoords.y, 1);
    obtain.setSource(4098);
    obtain.setFlags(8388608);
    obtain.setDisplayId(i);
    this.mInputManager.semInjectInputEvent(obtain, 2);
    obtain.recycle();
    MotionEvent obtain2 = MotionEvent.obtain(obtain);
    obtain2.setAction(1);
    this.mInputManager.semInjectInputEvent(obtain2, 2);
    obtain2.recycle();
  }
}
