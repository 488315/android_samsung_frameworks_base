package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import com.android.server.accessibility.autoaction.CornerActionCircleCue;

/* loaded from: classes.dex */
public class DragAction extends CornerActionType {
  public static final String TAG = "DragAction";
  public Context mContext;
  public MotionEvent mFirstEvent;
  public CornerActionCircleCue mFirstPoint;
  public MotionEvent.PointerCoords[] mFirstPointerCoords;
  public MotionEvent mSecondEvent = null;
  public CornerActionCircleCue mSecondPoint;
  public MotionEvent.PointerCoords[] mSecondPointerCoords;
  public String mType;

  public DragAction(Context context, MotionEvent motionEvent, String str) {
    this.mFirstEvent = null;
    this.mContext = context;
    this.mType = str;
    this.mFirstEvent = MotionEvent.obtain(motionEvent);
    this.mFirstPoint = new CornerActionCircleCue(this.mContext, 1);
    this.mSecondPoint = new CornerActionCircleCue(this.mContext, 1);
  }

  public static DragAction createAction(Context context, MotionEvent motionEvent, String str) {
    return new DragAction(context, motionEvent, str);
  }

  public static int getStringResId(String str) {
    str.hashCode();
    if (str.equals("drag")) {
      return R.string.action_bar_up_description;
    }
    if (str.equals("drag_and_drop")) {
      return R.string.action_menu_overflow_description;
    }
    throw new IllegalArgumentException("Wrong Swipe Type");
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void setMotionEventForDragAction(MotionEvent motionEvent) {
    this.mSecondEvent = MotionEvent.obtain(motionEvent);
  }

  @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
  public void performCornerAction(final int i) {
    if (this.mFirstEvent != null) {
      new Thread(
              new Runnable() { // from class:
                // com.android.server.accessibility.autoaction.actiontype.DragAction.1
                @Override // java.lang.Runnable
                public void run() {
                  if (DragAction.this.mSecondEvent != null) {
                    int actionIndex = DragAction.this.mSecondEvent.getActionIndex();
                    DragAction.this.mSecondPointerCoords = new MotionEvent.PointerCoords[1];
                    DragAction.this.mSecondPointerCoords[0] = new MotionEvent.PointerCoords();
                    DragAction.this.mSecondEvent.getPointerCoords(
                        actionIndex, DragAction.this.mSecondPointerCoords[0]);
                    DragAction.this.mSecondPoint.updateView(
                        DragAction.this.mSecondPointerCoords[0].x,
                        DragAction.this.mSecondPointerCoords[0].y);
                    DragAction.this.mSecondPoint.setViewOnOff(true);
                    DragAction.this.dragAndDrop(i);
                    DragAction.this.mFirstPoint.destroy();
                    DragAction.this.mSecondPoint.destroy();
                    return;
                  }
                  DragAction.this.mFirstPointerCoords = new MotionEvent.PointerCoords[1];
                  DragAction.this.mFirstPointerCoords[0] = new MotionEvent.PointerCoords();
                  DragAction.this.mFirstEvent.getPointerCoords(
                      DragAction.this.mFirstEvent.getActionIndex(),
                      DragAction.this.mFirstPointerCoords[0]);
                  DragAction.this.mFirstPoint.updateView(
                      DragAction.this.mFirstPointerCoords[0].x,
                      DragAction.this.mFirstPointerCoords[0].y);
                  DragAction.this.mFirstPoint.setViewOnOff(true);
                }
              })
          .start();
    }
  }

  public final void dragAndDrop(int i) {
    long uptimeMillis = SystemClock.uptimeMillis();
    long uptimeMillis2 = SystemClock.uptimeMillis();
    MotionEvent.PointerProperties[] pointerPropertiesArr = {new MotionEvent.PointerProperties()};
    MotionEvent motionEvent = this.mFirstEvent;
    char c = 0;
    motionEvent.getPointerProperties(motionEvent.getActionIndex(), pointerPropertiesArr[0]);
    InputManager inputManager = (InputManager) this.mContext.getSystemService("input");
    if (inputManager == null) {
      return;
    }
    MotionEvent.PointerCoords pointerCoords = this.mFirstPointerCoords[0];
    MotionEvent obtain =
        MotionEvent.obtain(uptimeMillis, uptimeMillis2, 0, pointerCoords.x, pointerCoords.y, 1);
    int i2 = 4098;
    obtain.setSource(4098);
    char c2 = 0;
    obtain.setFlags(8388608);
    obtain.setDisplayId(i);
    char c3 = 2;
    inputManager.semInjectInputEvent(obtain, 2);
    obtain.recycle();
    try {
      Thread.sleep(
          "drag".equals(this.mType)
              ? 0
              : Settings.Secure.getInt(
                      this.mContext.getContentResolver(), "long_press_timeout", 500)
                  + 500);
      MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
      ((DisplayManager) this.mContext.getSystemService("display"))
          .getDisplay(i)
          .getDisplayInfo(new DisplayInfo());
      MotionEvent.PointerCoords pointerCoords2 = this.mSecondPointerCoords[0];
      float f = pointerCoords2.x;
      MotionEvent.PointerCoords pointerCoords3 = this.mFirstPointerCoords[0];
      float f2 = pointerCoords3.x;
      float f3 = (f - f2) / 20.0f;
      float f4 = pointerCoords2.y;
      float f5 = pointerCoords3.y;
      float f6 = (f4 - f5) / 20.0f;
      MotionEvent.PointerCoords pointerCoords4 = pointerCoordsArr[0];
      pointerCoords4.x = f2;
      pointerCoords4.y = f5;
      int i3 = 0;
      while (i3 < 20) {
        MotionEvent.PointerCoords pointerCoords5 = pointerCoordsArr[c];
        float f7 = pointerCoords5.x + f3;
        pointerCoords5.x = f7;
        float f8 = pointerCoords5.y + f6;
        pointerCoords5.y = f8;
        uptimeMillis2 += 15;
        int i4 = i2;
        MotionEvent obtain2 = MotionEvent.obtain(uptimeMillis, uptimeMillis2, 2, f7, f8, 1);
        obtain2.setSource(i4);
        obtain2.setFlags(8388608);
        obtain2.setDisplayId(i);
        inputManager.semInjectInputEvent(obtain2, 2);
        obtain2.recycle();
        i3++;
        i2 = i4;
        c3 = 2;
        c2 = 0;
        c = 0;
      }
      MotionEvent.PointerCoords pointerCoords6 = this.mSecondPointerCoords[0];
      MotionEvent obtain3 =
          MotionEvent.obtain(
              uptimeMillis, uptimeMillis2 + 15, 1, pointerCoords6.x, pointerCoords6.y, 1);
      obtain3.setSource(i2);
      obtain3.setFlags(8388608);
      obtain3.setDisplayId(i);
      inputManager.semInjectInputEvent(obtain3, 2);
      obtain3.recycle();
    } catch (InterruptedException e) {
      Log.w(TAG, "InterruptedException!", e);
    }
  }
}
