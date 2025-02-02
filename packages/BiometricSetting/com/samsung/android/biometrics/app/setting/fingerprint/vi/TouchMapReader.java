package com.samsung.android.biometrics.app.setting.fingerprint.vi;

import android.content.Context;
import android.util.Log;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class TouchMapReader {
    public int displayHCellMax;
    public int displayHCellSize;
    public int displayVCellMax;
    public int displayVCellSize;
    private int mDisplayInfoBufferSize;
    private boolean mIsTouchInfoUpdated;
    private SemInputDeviceManager mSemInputDeviceManager;

    public TouchMapReader(Context context) {
        this.displayHCellSize = 17;
        this.displayVCellSize = 14;
        this.displayHCellMax = 17;
        this.displayVCellMax = 14;
        this.mIsTouchInfoUpdated = false;
        this.mDisplayInfoBufferSize = (((17 + 14) + 7) / 8) * 2;
        SemInputDeviceManager semInputDeviceManager = (SemInputDeviceManager) context.getSystemService("SemInputDeviceManagerService");
        this.mSemInputDeviceManager = semInputDeviceManager;
        if (this.mIsTouchInfoUpdated) {
            return;
        }
        this.mIsTouchInfoUpdated = true;
        if (semInputDeviceManager == null) {
            return;
        }
        String fodInfo = semInputDeviceManager.getFodInfo(1);
        String[] split = fodInfo.split(",");
        if (split.length != 3 && split.length != 5) {
            Log.e("BSS_TouchMapReader", "Wrong information(" + fodInfo + ")");
            return;
        }
        try {
            this.displayHCellSize = Integer.parseInt(split[0]);
            this.displayVCellSize = Integer.parseInt(split[1]);
            this.mDisplayInfoBufferSize = Integer.parseInt(split[2]) * 2;
            this.displayHCellMax = split.length == 5 ? Integer.parseInt(split[3]) : -1;
            this.displayVCellMax = split.length == 5 ? Integer.parseInt(split[4]) : -1;
            if (this.displayHCellSize <= 0 || this.displayVCellSize <= 0) {
                Log.e("BSS_TouchMapReader", "invalid display info (" + fodInfo + ")");
            }
        } catch (NumberFormatException unused) {
            Log.e("BSS_TouchMapReader", "Wrong information(" + fodInfo + ")");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0057  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int[][] readTouchMatrix() {
        String str;
        int i;
        int i2;
        SemInputDeviceManager semInputDeviceManager = this.mSemInputDeviceManager;
        if (semInputDeviceManager != null) {
            str = semInputDeviceManager.getFodPosition(1);
            if (str.length() != this.mDisplayInfoBufferSize) {
                Log.e("BSS_TouchMapReader", "raw data has " + str.length());
            }
            if (this.displayHCellSize > 0 && this.displayVCellSize > 0 && str != null && str.length() > 0) {
                int i3 = this.displayHCellSize;
                int i4 = this.displayVCellSize;
                int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i4, i3);
                i = 0;
                int i5 = 0;
                int i6 = 0;
                while (i < str.length()) {
                    int i7 = i + 2;
                    try {
                        i2 = Integer.parseInt(str.substring(i, i7), 16);
                    } catch (NumberFormatException unused) {
                        i2 = 0;
                    }
                    for (int i8 = 0; i8 < 8; i8++) {
                        iArr[i6][i5] = ((i2 >> i8) & 1) == 1 ? 1 : 0;
                        i5++;
                        if (i5 == i3) {
                            i6++;
                            if (i6 == i4) {
                                return iArr;
                            }
                            i5 = 0;
                        }
                    }
                    i = i7;
                }
                Log.e("BSS_TouchMapReader", "invalid data(" + i5 + ", " + i6 + ")");
            }
            return null;
        }
        str = null;
        if (this.displayHCellSize > 0) {
            int i32 = this.displayHCellSize;
            int i42 = this.displayVCellSize;
            int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i42, i32);
            i = 0;
            int i52 = 0;
            int i62 = 0;
            while (i < str.length()) {
            }
            Log.e("BSS_TouchMapReader", "invalid data(" + i52 + ", " + i62 + ")");
        }
        return null;
    }
}
