package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Swipe extends CornerActionType {
    public Context mContext;
    public MotionEvent mLastMotionEvent;
    public String mType;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(final int i) {
        if (this.mLastMotionEvent != null) {
            new Thread(new Runnable() { // from class: com.android.server.accessibility.autoaction.actiontype.Swipe.1
                @Override // java.lang.Runnable
                public final void run() {
                    int i2;
                    float f;
                    float f2;
                    float f3;
                    float f4;
                    char c = 0;
                    Swipe swipe = Swipe.this;
                    int i3 = i;
                    swipe.getClass();
                    long uptimeMillis = SystemClock.uptimeMillis();
                    long uptimeMillis2 = SystemClock.uptimeMillis();
                    int actionIndex = swipe.mLastMotionEvent.getActionIndex();
                    MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
                    MotionEvent.PointerCoords[] pointerCoordsArr = {pointerCoords};
                    swipe.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoords);
                    InputManager inputManager = (InputManager) swipe.mContext.getSystemService("input");
                    if (inputManager == null) {
                        return;
                    }
                    MotionEvent.PointerCoords pointerCoords2 = pointerCoordsArr[0];
                    MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis2, 0, pointerCoords2.x, pointerCoords2.y, 1);
                    int i4 = 4098;
                    obtain.setSource(4098);
                    int i5 = 8388608;
                    obtain.setFlags(8388608);
                    obtain.setDisplayId(i3);
                    inputManager.semInjectInputEvent(obtain, 2);
                    obtain.recycle();
                    Display display = ((DisplayManager) swipe.mContext.getSystemService("display")).getDisplay(i3);
                    DisplayInfo displayInfo = new DisplayInfo();
                    display.getDisplayInfo(displayInfo);
                    i2 = displayInfo.logicalWidth;
                    String str = swipe.mType;
                    str.getClass();
                    switch (str) {
                        case "swipe_up":
                            f = -100.0f;
                            f2 = 0.0f;
                            break;
                        case "swipe_down":
                            f = 100.0f;
                            f2 = 0.0f;
                            break;
                        case "swipe_left":
                            f3 = (-i2) / 9.0f;
                            float f5 = pointerCoordsArr[0].x;
                            if ((f3 * 6.0f) + f5 < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                                f4 = -f5;
                                f3 = f4 / 6.0f;
                            }
                            f2 = f3;
                            f = 0.0f;
                            break;
                        case "swipe_right":
                            float f6 = i2;
                            float f7 = f6 / 9.0f;
                            float f8 = pointerCoordsArr[0].x;
                            if ((f7 * 6.0f) + f8 > f6) {
                                f4 = f6 - f8;
                                f3 = f4 / 6.0f;
                                f2 = f3;
                                f = 0.0f;
                                break;
                            } else {
                                f2 = f7;
                                f = 0.0f;
                            }
                        default:
                            f = 0.0f;
                            f2 = 0.0f;
                            break;
                    }
                    int i6 = 0;
                    while (i6 < 6.0f) {
                        MotionEvent.PointerCoords pointerCoords3 = pointerCoordsArr[c];
                        pointerCoords3.x += f2;
                        pointerCoords3.y += f;
                        long uptimeMillis3 = SystemClock.uptimeMillis();
                        MotionEvent.PointerCoords pointerCoords4 = pointerCoordsArr[c];
                        int i7 = i5;
                        int i8 = i4;
                        MotionEvent obtain2 = MotionEvent.obtain(uptimeMillis, uptimeMillis3, 2, pointerCoords4.x, pointerCoords4.y, 1);
                        obtain2.setSource(i8);
                        obtain2.setFlags(i7);
                        obtain2.setDisplayId(i3);
                        inputManager.semInjectInputEvent(obtain2, 2);
                        obtain2.recycle();
                        i6++;
                        i4 = i8;
                        i5 = i7;
                        c = 0;
                    }
                    long uptimeMillis4 = SystemClock.uptimeMillis();
                    MotionEvent.PointerCoords pointerCoords5 = pointerCoordsArr[0];
                    MotionEvent obtain3 = MotionEvent.obtain(uptimeMillis, uptimeMillis4, 1, pointerCoords5.x, pointerCoords5.y, 1);
                    obtain3.setSource(i4);
                    obtain3.setFlags(i5);
                    obtain3.setDisplayId(i3);
                    inputManager.semInjectInputEvent(obtain3, 2);
                    obtain3.recycle();
                }
            }).start();
        }
    }
}
