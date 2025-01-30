package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class ClickAndHold extends CornerActionType {
  public Context mContext;
  public MotionEvent mLastMotionEvent;

  public static int getStringResId() {
    return R.string.action_bar_home_description_format;
  }

  public ClickAndHold(Context context, MotionEvent motionEvent) {
    this.mLastMotionEvent = null;
    this.mContext = context;
    this.mLastMotionEvent = MotionEvent.obtain(motionEvent);
  }

  public static ClickAndHold createAction(Context context, MotionEvent motionEvent) {
    return new ClickAndHold(context, motionEvent);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(final int i) {
    if (this.mLastMotionEvent != null) {
      new Thread(
              new Runnable() { // from class:
                // com.android.server.accessibility.autoaction.actiontype.ClickAndHold.1
                @Override // java.lang.Runnable
                public void run() {
                  ClickAndHold.this.clickAndHold(i);
                }
              })
          .start();
    }
  }

  public final void clickAndHold(int i) {
    long uptimeMillis = SystemClock.uptimeMillis();
    long uptimeMillis2 = SystemClock.uptimeMillis();
    int actionIndex = this.mLastMotionEvent.getActionIndex();
    MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
    this.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoordsArr[0]);
    InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
    if (inputManager == null) {
      return;
    }
    MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[0];
    MotionEvent obtain =
        MotionEvent.obtain(uptimeMillis, uptimeMillis2, 0, pointerCoords.x, pointerCoords.y, 1);
    obtain.setSource(4098);
    obtain.setFlags(8388608);
    obtain.setDisplayId(i);
    inputManager.semInjectInputEvent(obtain, 2);
    obtain.recycle();
  }
}
