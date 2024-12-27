package com.android.server.accessibility.autoaction.actiontype;

import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class Zoom extends CornerActionType {
    public Context mContext;
    public InputManager mInputManager;
    public MotionEvent mLastMotionEvent;
    public String mType;

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0067, code lost:
    
        if (r6 > 20) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void generateTouchEvent(int r36, int r37, int r38, int r39, int r40, int r41) {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.autoaction.actiontype.Zoom.generateTouchEvent(int, int, int, int, int, int):void");
    }

    public final String getTopPackageName() {
        try {
            return ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public final void performCornerAction(final int i) {
        MotionEvent motionEvent;
        if (this.mInputManager == null || (motionEvent = this.mLastMotionEvent) == null) {
            return;
        }
        int actionIndex = motionEvent.getActionIndex();
        MotionEvent.PointerCoords pointerCoords = new MotionEvent.PointerCoords();
        this.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoords);
        MotionEvent.PointerCoords pointerCoords2 = new MotionEvent.PointerCoords[]{pointerCoords}[0];
        final int i2 = (int) pointerCoords2.x;
        final int i3 = (int) pointerCoords2.y;
        String topPackageName = getTopPackageName();
        final int i4 = ("com.samsung.android.messaging".equals(topPackageName) || KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(topPackageName)) ? 20 : 50;
        new Thread(new Runnable() { // from class: com.android.server.accessibility.autoaction.actiontype.Zoom.1
            @Override // java.lang.Runnable
            public final void run() {
                int i5;
                Zoom zoom = Zoom.this;
                int i6 = i2;
                int i7 = i3;
                float f = i4;
                int i8 = i;
                Display display = ((DisplayManager) zoom.mContext.getSystemService("display")).getDisplay(i8);
                DisplayInfo displayInfo = new DisplayInfo();
                display.getDisplayInfo(displayInfo);
                int i9 = displayInfo.logicalWidth;
                int dimensionPixelSize = zoom.mContext.getResources().getDimensionPixelSize(17106439);
                int i10 = i9 - (dimensionPixelSize * 2);
                int i11 = (int) (((i10 * f) / 100.0f) - 2.0f);
                int i12 = i10 - 2;
                if (i11 > i12) {
                    i11 = i12;
                }
                int i13 = i6 - 50;
                int i14 = i6 + 50;
                int i15 = "com.samsung.android.messaging".equals(zoom.getTopPackageName()) ? i13 : i13 - (i11 / 2);
                int i16 = (i11 / 2) + i14;
                if (i15 < dimensionPixelSize) {
                    i16 -= (i15 - dimensionPixelSize) + 1;
                    i15 = dimensionPixelSize - 1;
                }
                int i17 = i9 - dimensionPixelSize;
                if (i16 > i17) {
                    i15 -= (i16 - i17) - 1;
                    i5 = i17 + 1;
                } else {
                    i5 = i16;
                }
                int i18 = i15;
                String str = zoom.mType;
                str.getClass();
                if (str.equals("zoom_in")) {
                    zoom.generateTouchEvent(i7, i13, i18, i14, i5, i8);
                } else {
                    if (!str.equals("zoom_out")) {
                        throw new IllegalArgumentException("Wrong Zoom Type");
                    }
                    zoom.generateTouchEvent(i7, i18, i13, i5, i14, i8);
                }
            }
        }).start();
    }
}
