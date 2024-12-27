package com.android.server.display;

import android.os.IBinder;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class DisplayControl {
    public static IBinder createVirtualDisplay(String str, boolean z) {
        Objects.requireNonNull(str, "name must not be null");
        return nativeCreateVirtualDisplay(
                str, z, "", FullScreenMagnificationGestureHandler.MAX_SCALE);
    }

    public static IBinder createVirtualDisplay(String str, boolean z, String str2, float f) {
        Objects.requireNonNull(str, "name must not be null");
        Objects.requireNonNull(str2, "uniqueId must not be null");
        return nativeCreateVirtualDisplay(str, z, str2, f);
    }

    public static void destroyVirtualDisplay(IBinder iBinder) {
        if (iBinder == null) {
            throw new IllegalArgumentException("displayToken must not be null");
        }
        nativeDestroyVirtualDisplay(iBinder);
    }

    public static boolean getHdrOutputConversionSupport() {
        return nativeGetHdrOutputConversionSupport();
    }

    public static int[] getHdrOutputTypesWithLatency() {
        return nativeGetHdrOutputTypesWithLatency();
    }

    public static long[] getPhysicalDisplayIds() {
        return nativeGetPhysicalDisplayIds();
    }

    public static IBinder getPhysicalDisplayToken(long j) {
        return nativeGetPhysicalDisplayToken(j);
    }

    public static int[] getSupportedHdrOutputTypes() {
        return nativeGetSupportedHdrOutputTypes();
    }

    private static native IBinder nativeCreateVirtualDisplay(
            String str, boolean z, String str2, float f);

    private static native void nativeDestroyVirtualDisplay(IBinder iBinder);

    private static native boolean nativeGetHdrOutputConversionSupport();

    private static native int[] nativeGetHdrOutputTypesWithLatency();

    private static native long[] nativeGetPhysicalDisplayIds();

    private static native IBinder nativeGetPhysicalDisplayToken(long j);

    private static native int[] nativeGetSupportedHdrOutputTypes();

    private static native void nativeOverrideHdrTypes(IBinder iBinder, int[] iArr);

    private static native int nativeSetHdrConversionMode(int i, int i2, int[] iArr, int i3);

    public static void overrideHdrTypes(IBinder iBinder, int[] iArr) {
        nativeOverrideHdrTypes(iBinder, iArr);
    }

    public static int setHdrConversionMode(int[] iArr, int i, int i2) {
        return nativeSetHdrConversionMode(i, i2, iArr, iArr != null ? iArr.length : 0);
    }
}
