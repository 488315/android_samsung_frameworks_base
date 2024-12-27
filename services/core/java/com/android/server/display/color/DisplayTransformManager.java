package com.android.server.display.color;

import android.opengl.Matrix;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Slog;
import android.util.SparseArray;

import java.lang.reflect.Array;
import java.util.Arrays;

public final class DisplayTransformManager {
    static final String PERSISTENT_PROPERTY_COMPOSITION_COLOR_MODE = "persist.sys.sf.color_mode";
    static final String PERSISTENT_PROPERTY_DISPLAY_COLOR = "persist.sys.sf.native_mode";
    static final String PERSISTENT_PROPERTY_SATURATION = "persist.sys.sf.color_saturation";
    public static final IBinder sFlinger = ServiceManager.getService("SurfaceFlinger");
    public final SparseArray mColorMatrix = new SparseArray(6);
    public final float[][] mTempColorMatrix =
            (float[][]) Array.newInstance((Class<?>) Float.TYPE, 2, 16);
    final Object mDaltonizerModeLock = new Object();
    int mDaltonizerMode = -1;
    int mDaltonizerLevel = -1;

    public static void applyColorMatrix(float[] fArr) {
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        if (fArr != null) {
            obtain.writeInt(1);
            for (int i = 0; i < 16; i++) {
                obtain.writeFloat(fArr[i]);
            }
        } else {
            obtain.writeInt(0);
        }
        try {
            try {
                sFlinger.transact(1015, obtain, null, 0);
            } catch (RemoteException e) {
                Slog.e("DisplayTransformManager", "Failed to set color transform", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    public static void applySaturation(float f) {
        SystemProperties.set(PERSISTENT_PROPERTY_SATURATION, Float.toString(f));
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeFloat(f);
        try {
            try {
                sFlinger.transact(1022, obtain, null, 0);
            } catch (RemoteException e) {
                Slog.e("DisplayTransformManager", "Failed to set saturation", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    public static void setDisplayColor(int i, int i2) {
        SystemProperties.set(PERSISTENT_PROPERTY_DISPLAY_COLOR, Integer.toString(i));
        if (i2 != -1) {
            SystemProperties.set(PERSISTENT_PROPERTY_COMPOSITION_COLOR_MODE, Integer.toString(i2));
        }
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeInt(i);
        if (i2 != -1) {
            obtain.writeInt(i2);
        }
        try {
            try {
                sFlinger.transact(1023, obtain, null, 0);
            } catch (RemoteException e) {
                Slog.e("DisplayTransformManager", "Failed to set display color", e);
            }
        } finally {
            obtain.recycle();
        }
    }

    public final float[] computeColorMatrixLocked() {
        int size = this.mColorMatrix.size();
        if (size == 0) {
            return null;
        }
        float[][] fArr = this.mTempColorMatrix;
        int i = 0;
        Matrix.setIdentityM(fArr[0], 0);
        while (i < size) {
            int i2 = i + 1;
            Matrix.multiplyMM(
                    fArr[i2 % 2], 0, fArr[i % 2], 0, (float[]) this.mColorMatrix.valueAt(i), 0);
            i = i2;
        }
        return fArr[size % 2];
    }

    public final void setColorMatrix(int i, float[] fArr) {
        if (fArr != null && fArr.length != 16) {
            throw new IllegalArgumentException(
                    "Expected length: 16 (4x4 matrix), actual length: " + fArr.length);
        }
        synchronized (this.mColorMatrix) {
            try {
                float[] fArr2 = (float[]) this.mColorMatrix.get(i);
                if (!Arrays.equals(fArr2, fArr)) {
                    if (fArr == null) {
                        this.mColorMatrix.remove(i);
                    } else if (fArr2 == null) {
                        this.mColorMatrix.put(i, Arrays.copyOf(fArr, fArr.length));
                    } else {
                        System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                    }
                    applyColorMatrix(computeColorMatrixLocked());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:3|4|(3:6|(1:8)|9)|12|13|14|15|9) */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0030, code lost:

       r4 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0031, code lost:

       android.util.Slog.e("DisplayTransformManager", "Failed to set Daltonizer mode", r4);
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setDaltonizerMode(int r4, int r5) {
        /*
            r3 = this;
            java.lang.Object r0 = r3.mDaltonizerModeLock
            monitor-enter(r0)
            int r1 = r3.mDaltonizerMode     // Catch: java.lang.Throwable -> Lc
            if (r1 != r4) goto Le
            int r1 = r3.mDaltonizerLevel     // Catch: java.lang.Throwable -> Lc
            if (r1 == r5) goto L39
            goto Le
        Lc:
            r3 = move-exception
            goto L3f
        Le:
            r3.mDaltonizerMode = r4     // Catch: java.lang.Throwable -> Lc
            r3.mDaltonizerLevel = r5     // Catch: java.lang.Throwable -> Lc
            android.os.Parcel r3 = android.os.Parcel.obtain()     // Catch: java.lang.Throwable -> Lc
            java.lang.String r1 = "android.ui.ISurfaceComposer"
            r3.writeInterfaceToken(r1)     // Catch: java.lang.Throwable -> Lc
            r3.writeInt(r4)     // Catch: java.lang.Throwable -> Lc
            r3.writeInt(r5)     // Catch: java.lang.Throwable -> Lc
            android.os.IBinder r4 = com.android.server.display.color.DisplayTransformManager.sFlinger     // Catch: java.lang.Throwable -> L2e android.os.RemoteException -> L30
            r5 = 0
            r1 = 1014(0x3f6, float:1.421E-42)
            r2 = 0
            r4.transact(r1, r3, r2, r5)     // Catch: java.lang.Throwable -> L2e android.os.RemoteException -> L30
        L2a:
            r3.recycle()     // Catch: java.lang.Throwable -> Lc
            goto L39
        L2e:
            r4 = move-exception
            goto L3b
        L30:
            r4 = move-exception
            java.lang.String r5 = "DisplayTransformManager"
            java.lang.String r1 = "Failed to set Daltonizer mode"
            android.util.Slog.e(r5, r1, r4)     // Catch: java.lang.Throwable -> L2e
            goto L2a
        L39:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            return
        L3b:
            r3.recycle()     // Catch: java.lang.Throwable -> Lc
            throw r4     // Catch: java.lang.Throwable -> Lc
        L3f:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> Lc
            throw r3
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.color.DisplayTransformManager.setDaltonizerMode(int,"
                    + " int):void");
    }
}
