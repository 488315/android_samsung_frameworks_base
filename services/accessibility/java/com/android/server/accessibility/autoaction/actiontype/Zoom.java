package com.android.server.accessibility.autoaction.actiontype;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* loaded from: classes.dex */
public class Zoom extends CornerActionType {
    public static final String TAG = "Zoom";
    public Context mContext;
    public InputManager mInputManager;
    public MotionEvent mLastMotionEvent;
    public String mType;

    public Zoom(Context context, MotionEvent motionEvent, String str) {
        this.mLastMotionEvent = null;
        this.mContext = context;
        this.mInputManager = (InputManager) context.getSystemService("input");
        this.mType = str;
        this.mLastMotionEvent = MotionEvent.obtain(motionEvent);
    }

    public static Zoom createAction(Context context, MotionEvent motionEvent, String str) {
        return new Zoom(context, motionEvent, str);
    }

    public static int getStringResId(String str) {
        str.hashCode();
        if (str.equals("zoom_in")) {
            return R.string.aerr_report;
        }
        if (str.equals("zoom_out")) {
            return R.string.aerr_restart;
        }
        throw new IllegalArgumentException("Wrong Zoom Type");
    }

    @Override // com.android.server.accessibility.autoaction.actiontype.CornerActionType
    public void performCornerAction(final int i) {
        MotionEvent motionEvent;
        if (this.mInputManager == null || (motionEvent = this.mLastMotionEvent) == null) {
            return;
        }
        int actionIndex = motionEvent.getActionIndex();
        MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
        this.mLastMotionEvent.getPointerCoords(actionIndex, pointerCoordsArr[0]);
        MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[0];
        final int i2 = (int) pointerCoords.x;
        final int i3 = (int) pointerCoords.y;
        String topPackageName = getTopPackageName();
        final int i4 = ("com.samsung.android.messaging".equals(topPackageName) || KnoxCustomManagerService.LAUNCHER_PACKAGE.equals(topPackageName)) ? 20 : 50;
        new Thread(new Runnable() { // from class: com.android.server.accessibility.autoaction.actiontype.Zoom.1
            @Override // java.lang.Runnable
            public void run() {
                Zoom.this.zoom(i2, i3, i4, i);
            }
        }).start();
    }

    public void zoom(int i, int i2, float f, int i3) {
        int i4;
        Display display = ((DisplayManager) this.mContext.getSystemService("display")).getDisplay(i3);
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        int i5 = displayInfo.logicalWidth;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(17106329);
        int i6 = i5 - (dimensionPixelSize * 2);
        int i7 = (int) (((i6 * f) / 100.0f) - 2.0f);
        int i8 = i6 - 2;
        if (i7 > i8) {
            i7 = i8;
        }
        int i9 = i - 50;
        int i10 = i + 50;
        int i11 = "com.samsung.android.messaging".equals(getTopPackageName()) ? i9 : i9 - (i7 / 2);
        int i12 = (i7 / 2) + i10;
        if (i11 < dimensionPixelSize) {
            i12 -= (i11 - dimensionPixelSize) + 1;
            i11 = dimensionPixelSize - 1;
        }
        int i13 = i5 - dimensionPixelSize;
        if (i12 > i13) {
            i11 -= (i12 - i13) - 1;
            i4 = i13 + 1;
        } else {
            i4 = i12;
        }
        int i14 = i11;
        String str = this.mType;
        str.hashCode();
        if (str.equals("zoom_in")) {
            generateTouchEvent(i2, i9, i14, i10, i4, i3);
        } else {
            if (str.equals("zoom_out")) {
                generateTouchEvent(i2, i14, i9, i4, i10, i3);
                return;
            }
            throw new IllegalArgumentException("Wrong Zoom Type");
        }
    }

    public final String getTopPackageName() {
        try {
            return ((ActivityManager) this.mContext.getSystemService("activity")).getRunningTasks(1).get(0).topActivity.getPackageName();
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0067, code lost:
    
        if (r6 > 20) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void generateTouchEvent(int i, int i2, int i3, int i4, int i5, int i6) {
        int deviceId = this.mLastMotionEvent.getDeviceId();
        int[] inputDeviceIds = this.mInputManager.getInputDeviceIds();
        int i7 = 0;
        while (true) {
            if (i7 >= inputDeviceIds.length) {
                break;
            }
            if (this.mInputManager.getInputDevice(i7) != null && this.mInputManager.getInputDevice(i7).getName() != null && this.mInputManager.getInputDevice(i7).getName().toLowerCase().contains("touchscreen")) {
                deviceId = i7;
                break;
            }
            i7++;
        }
        int abs = Math.abs(i2 - i3) / this.mContext.getResources().getDimensionPixelSize(17106328);
        int i8 = abs >= 10 ? 20 : 10;
        abs = i8;
        long uptimeMillis = SystemClock.uptimeMillis();
        long uptimeMillis2 = SystemClock.uptimeMillis();
        int[] iArr = {0, 1};
        MotionEvent.PointerCoords[] pointerCoordsArr = {new MotionEvent.PointerCoords()};
        MotionEvent.PointerCoords[] pointerCoordsArr2 = {new MotionEvent.PointerCoords(), new MotionEvent.PointerCoords()};
        MotionEvent.PointerCoords pointerCoords = pointerCoordsArr[0];
        pointerCoords.pressure = 1.0f;
        MotionEvent.PointerCoords pointerCoords2 = pointerCoordsArr2[0];
        pointerCoords2.pressure = 1.0f;
        MotionEvent.PointerCoords pointerCoords3 = pointerCoordsArr2[1];
        pointerCoords3.pressure = 1.0f;
        float f = i;
        pointerCoords.y = f;
        pointerCoords2.y = f;
        pointerCoords3.y = f;
        float f2 = i2;
        pointerCoords.x = f2;
        pointerCoords2.x = f2;
        pointerCoords3.x = i4;
        int i9 = deviceId;
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis2, 0, 1, new int[]{0}, pointerCoordsArr, 0, 1.0f, 1.0f, i9, 0, 4098, 8388608);
        obtain.setDisplayId(i6);
        this.mInputManager.semInjectInputEvent(obtain, 2);
        obtain.recycle();
        long j = 280 / (abs + 4);
        long j2 = uptimeMillis2 + j;
        MotionEvent obtain2 = MotionEvent.obtain(uptimeMillis, j2, 261, 2, iArr, pointerCoordsArr2, 0, 1.0f, 1.0f, i9, 0, 4098, 8388608);
        obtain2.setDisplayId(i6);
        this.mInputManager.semInjectInputEvent(obtain2, 2);
        obtain2.recycle();
        long j3 = j2 + j;
        for (int i10 = 0; i10 < abs; i10++) {
            float f3 = abs;
            pointerCoordsArr2[0].x += (i3 - i2) / f3;
            pointerCoordsArr2[1].x += (i5 - i4) / f3;
            j3 += j;
            MotionEvent obtain3 = MotionEvent.obtain(uptimeMillis, j3, 2, 2, iArr, pointerCoordsArr2, 0, 1.0f, 1.0f, deviceId, 0, 4098, 8388608);
            obtain3.setDisplayId(i6);
            this.mInputManager.semInjectInputEvent(obtain3, 2);
            obtain3.recycle();
        }
        long j4 = j3 + j;
        int i11 = deviceId;
        MotionEvent obtain4 = MotionEvent.obtain(uptimeMillis, j4, 262, 2, iArr, pointerCoordsArr2, 0, 1.0f, 1.0f, i11, 0, 4098, 8388608);
        obtain4.setDisplayId(i6);
        this.mInputManager.semInjectInputEvent(obtain4, 2);
        obtain4.recycle();
        long j5 = j4 + j;
        pointerCoordsArr[0].x = i3;
        MotionEvent obtain5 = MotionEvent.obtain(uptimeMillis, j5, 1, 1, iArr, pointerCoordsArr, 0, 1.0f, 1.0f, i11, 0, 4098, 8388608);
        obtain5.setDisplayId(i6);
        this.mInputManager.semInjectInputEvent(obtain5, 2);
        obtain5.recycle();
        try {
            Thread.sleep(j5 - uptimeMillis);
        } catch (InterruptedException e) {
            Log.e(TAG, "InterruptedException : ", e);
        }
    }
}
