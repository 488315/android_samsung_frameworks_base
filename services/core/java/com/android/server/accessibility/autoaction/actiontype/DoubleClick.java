package com.android.server.accessibility.autoaction.actiontype;

import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.MotionEvent;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DoubleClick extends CornerActionType {
    public InputManager mInputManager;
    public MotionEvent mLastMotionEvent;

    public final void click(int i, long j, long j2) {
        int actionIndex = this.mLastMotionEvent.getActionIndex();
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        this.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoords);
        MotionEvent.PointerCoords pointerCoords2 = new MotionEvent.PointerCoords[]{pointerCoords}[0];
        MotionEvent obtain = MotionEvent.obtain(j, j2, 0, pointerCoords2.x, pointerCoords2.y, 1);
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

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(int i) {
        if (this.mInputManager != null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            click(i, uptimeMillis, uptimeMillis);
            long j = uptimeMillis + 50;
            click(i, j, j);
        }
    }
}
