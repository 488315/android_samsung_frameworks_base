package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ClickAndHold extends CornerActionType {
    public Context mContext;
    public MotionEvent mLastMotionEvent;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(final int i) {
        if (this.mLastMotionEvent != null) {
            new Thread(new Runnable() { // from class: com.android.server.accessibility.autoaction.actiontype.ClickAndHold.1
                @Override // java.lang.Runnable
                public final void run() {
                    ClickAndHold clickAndHold = ClickAndHold.this;
                    int i2 = i;
                    clickAndHold.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    int actionIndex = clickAndHold.mLastMotionEvent.getActionIndex();
                    MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                    MotionEvent.PointerCoords[] pointerCoordsArr = {pointerCoords};
                    clickAndHold.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoords);
                    InputManager inputManager = (InputManager) clickAndHold.mContext.getSystemService("input");
                    if (inputManager == null) {
                        return;
                    }
                    MotionEvent.PointerCoords pointerCoords2 = pointerCoordsArr[0];
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis2, 0, pointerCoords2.x, pointerCoords2.y, 1);
                    obtain.setSource(4098);
                    obtain.setFlags(8388608);
                    obtain.setDisplayId(i2);
                    inputManager.semInjectInputEvent(obtain, 2);
                    obtain.recycle();
                }
            }).start();
        }
    }
}
