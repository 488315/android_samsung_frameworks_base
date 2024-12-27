package com.android.server.accessibility.autoaction.actiontype;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.DisplayInfo;
import android.view.MotionEvent;

import com.android.server.accessibility.autoaction.CornerActionCircleCue;
import com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda1;
import com.android.server.accessibility.autoaction.CornerActionCircleCue$$ExternalSyntheticLambda2;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DragAction extends CornerActionType {
    public Context mContext;
    public MotionEvent mFirstEvent;
    public CornerActionCircleCue mFirstPoint;
    public MotionEvent.PointerCoords[] mFirstPointerCoords;
    public MotionEvent mSecondEvent;
    public CornerActionCircleCue mSecondPoint;
    public MotionEvent.PointerCoords[] mSecondPointerCoords;
    public String mType;

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(final int i) {
        if (this.mFirstEvent != null) {
            new Thread(
                            new Runnable() { // from class:
                                             // com.android.server.accessibility.autoaction.actiontype.DragAction.1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    DragAction dragAction = DragAction.this;
                                    MotionEvent motionEvent = dragAction.mSecondEvent;
                                    if (motionEvent == null) {
                                        dragAction.mFirstPointerCoords =
                                                new MotionEvent.PointerCoords[] {
                                                    new MotionEvent.PointerCoords()
                                                };
                                        MotionEvent motionEvent2 = DragAction.this.mFirstEvent;
                                        motionEvent2.getPointerCoords(
                                                motionEvent2.getActionIndex(),
                                                DragAction.this.mFirstPointerCoords[0]);
                                        DragAction dragAction2 = DragAction.this;
                                        CornerActionCircleCue cornerActionCircleCue =
                                                dragAction2.mFirstPoint;
                                        MotionEvent.PointerCoords pointerCoords =
                                                dragAction2.mFirstPointerCoords[0];
                                        float f = pointerCoords.x;
                                        float f2 = pointerCoords.y;
                                        cornerActionCircleCue.getClass();
                                        cornerActionCircleCue.runOnUiThread(
                                                new CornerActionCircleCue$$ExternalSyntheticLambda1(
                                                        cornerActionCircleCue, f, f2));
                                        CornerActionCircleCue cornerActionCircleCue2 =
                                                DragAction.this.mFirstPoint;
                                        cornerActionCircleCue2.getClass();
                                        cornerActionCircleCue2.runOnUiThread(
                                                new CornerActionCircleCue$$ExternalSyntheticLambda2(
                                                        cornerActionCircleCue2, true));
                                        return;
                                    }
                                    int actionIndex = motionEvent.getActionIndex();
                                    DragAction.this.mSecondPointerCoords =
                                            new MotionEvent.PointerCoords[] {
                                                new MotionEvent.PointerCoords()
                                            };
                                    DragAction dragAction3 = DragAction.this;
                                    dragAction3.mSecondEvent.getPointerCoords(
                                            actionIndex, dragAction3.mSecondPointerCoords[0]);
                                    DragAction dragAction4 = DragAction.this;
                                    CornerActionCircleCue cornerActionCircleCue3 =
                                            dragAction4.mSecondPoint;
                                    MotionEvent.PointerCoords pointerCoords2 =
                                            dragAction4.mSecondPointerCoords[0];
                                    float f3 = pointerCoords2.x;
                                    float f4 = pointerCoords2.y;
                                    cornerActionCircleCue3.getClass();
                                    cornerActionCircleCue3.runOnUiThread(
                                            new CornerActionCircleCue$$ExternalSyntheticLambda1(
                                                    cornerActionCircleCue3, f3, f4));
                                    CornerActionCircleCue cornerActionCircleCue4 =
                                            DragAction.this.mSecondPoint;
                                    cornerActionCircleCue4.getClass();
                                    cornerActionCircleCue4.runOnUiThread(
                                            new CornerActionCircleCue$$ExternalSyntheticLambda2(
                                                    cornerActionCircleCue4, true));
                                    DragAction dragAction5 = DragAction.this;
                                    int i2 = i;
                                    dragAction5.getClass();
                                    long uptimeMillis = SystemClock.uptimeMillis();
                                    long uptimeMillis2 = SystemClock.uptimeMillis();
                                    MotionEvent.PointerProperties[] pointerPropertiesArr = {
                                        new MotionEvent.PointerProperties()
                                    };
                                    MotionEvent motionEvent3 = dragAction5.mFirstEvent;
                                    motionEvent3.getPointerProperties(
                                            motionEvent3.getActionIndex(), pointerPropertiesArr[0]);
                                    InputManager inputManager =
                                            (InputManager)
                                                    dragAction5.mContext.getSystemService("input");
                                    if (inputManager != null) {
                                        MotionEvent.PointerCoords pointerCoords3 =
                                                dragAction5.mFirstPointerCoords[0];
                                        MotionEvent obtain =
                                                MotionEvent.obtain(
                                                        uptimeMillis,
                                                        uptimeMillis2,
                                                        0,
                                                        pointerCoords3.x,
                                                        pointerCoords3.y,
                                                        1);
                                        int i3 = 4098;
                                        obtain.setSource(4098);
                                        int i4 = 8388608;
                                        obtain.setFlags(8388608);
                                        obtain.setDisplayId(i2);
                                        int i5 = 2;
                                        inputManager.semInjectInputEvent(obtain, 2);
                                        obtain.recycle();
                                        try {
                                            Thread.sleep(
                                                    "drag".equals(dragAction5.mType)
                                                            ? 0
                                                            : Settings.Secure.getInt(
                                                                            dragAction5.mContext
                                                                                    .getContentResolver(),
                                                                            "long_press_timeout",
                                                                            500)
                                                                    + 500);
                                            char c = 0;
                                            MotionEvent.PointerCoords[] pointerCoordsArr = {
                                                new MotionEvent.PointerCoords()
                                            };
                                            ((DisplayManager)
                                                            dragAction5.mContext.getSystemService(
                                                                    "display"))
                                                    .getDisplay(i2)
                                                    .getDisplayInfo(new DisplayInfo());
                                            MotionEvent.PointerCoords pointerCoords4 =
                                                    dragAction5.mSecondPointerCoords[0];
                                            float f5 = pointerCoords4.x;
                                            MotionEvent.PointerCoords pointerCoords5 =
                                                    dragAction5.mFirstPointerCoords[0];
                                            float f6 = pointerCoords5.x;
                                            float f7 = (f5 - f6) / 20.0f;
                                            float f8 = pointerCoords4.y;
                                            float f9 = pointerCoords5.y;
                                            float f10 = (f8 - f9) / 20.0f;
                                            MotionEvent.PointerCoords pointerCoords6 =
                                                    pointerCoordsArr[0];
                                            pointerCoords6.x = f6;
                                            pointerCoords6.y = f9;
                                            int i6 = 0;
                                            while (i6 < 20) {
                                                MotionEvent.PointerCoords pointerCoords7 =
                                                        pointerCoordsArr[c];
                                                float f11 = pointerCoords7.x + f7;
                                                pointerCoords7.x = f11;
                                                float f12 = pointerCoords7.y + f10;
                                                pointerCoords7.y = f12;
                                                uptimeMillis2 += 15;
                                                MotionEvent.PointerCoords[] pointerCoordsArr2 =
                                                        pointerCoordsArr;
                                                int i7 = i3;
                                                MotionEvent obtain2 =
                                                        MotionEvent.obtain(
                                                                uptimeMillis,
                                                                uptimeMillis2,
                                                                2,
                                                                f11,
                                                                f12,
                                                                1);
                                                obtain2.setSource(i7);
                                                obtain2.setFlags(8388608);
                                                obtain2.setDisplayId(i2);
                                                inputManager.semInjectInputEvent(obtain2, 2);
                                                obtain2.recycle();
                                                i6++;
                                                i5 = 2;
                                                i4 = 8388608;
                                                c = 0;
                                                i3 = i7;
                                                pointerCoordsArr = pointerCoordsArr2;
                                            }
                                            MotionEvent.PointerCoords pointerCoords8 =
                                                    dragAction5.mSecondPointerCoords[0];
                                            MotionEvent obtain3 =
                                                    MotionEvent.obtain(
                                                            uptimeMillis,
                                                            uptimeMillis2 + 15,
                                                            1,
                                                            pointerCoords8.x,
                                                            pointerCoords8.y,
                                                            1);
                                            obtain3.setSource(i3);
                                            obtain3.setFlags(i4);
                                            obtain3.setDisplayId(i2);
                                            inputManager.semInjectInputEvent(obtain3, i5);
                                            obtain3.recycle();
                                        } catch (InterruptedException e) {
                                            Log.w("DragAction", "InterruptedException!", e);
                                        }
                                    }
                                    CornerActionCircleCue cornerActionCircleCue5 =
                                            DragAction.this.mFirstPoint;
                                    cornerActionCircleCue5.mContext = null;
                                    cornerActionCircleCue5.mWindowManager.removeView(
                                            cornerActionCircleCue5.mView);
                                    CornerActionCircleCue cornerActionCircleCue6 =
                                            DragAction.this.mSecondPoint;
                                    cornerActionCircleCue6.mContext = null;
                                    cornerActionCircleCue6.mWindowManager.removeView(
                                            cornerActionCircleCue6.mView);
                                }
                            })
                    .start();
        }
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void setMotionEventForDragAction(MotionEvent motionEvent) {
        this.mSecondEvent = MotionEvent.obtain(motionEvent);
    }
}
