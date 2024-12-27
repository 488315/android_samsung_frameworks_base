package com.android.server.tv;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.IBinder;

import dalvik.system.CloseGuard;

import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UinputBridge {
    public final CloseGuard mCloseGuard;
    public long mPtr;
    public IBinder mToken;

    public UinputBridge(IBinder iBinder, long j) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        this.mPtr = j;
        this.mToken = iBinder;
        closeGuard.open("close");
    }

    public UinputBridge(IBinder iBinder, String str, int i, int i2, int i3) {
        CloseGuard closeGuard = CloseGuard.get();
        this.mCloseGuard = closeGuard;
        if (i < 1 || i2 < 1) {
            throw new IllegalArgumentException("Touchpad must be at least 1x1.");
        }
        if (i3 < 1 || i3 > 32) {
            throw new IllegalArgumentException("Touchpad must support between 1 and 32 pointers.");
        }
        if (iBinder == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        long nativeOpen = nativeOpen(str, iBinder.toString(), i, i2, i3);
        this.mPtr = nativeOpen;
        if (nativeOpen == 0) {
            throw new IOException(
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                            "Could not open uinput device ", str));
        }
        this.mToken = iBinder;
        closeGuard.open("close");
    }

    private static native void nativeClear(long j);

    private static native void nativeClose(long j);

    private static native long nativeGamepadOpen(String str, String str2);

    private static native long nativeOpen(String str, String str2, int i, int i2, int i3);

    private static native void nativeSendGamepadAxisValue(long j, int i, float f);

    private static native void nativeSendGamepadKey(long j, int i, boolean z);

    private static native void nativeSendKey(long j, int i, boolean z);

    private static native void nativeSendPointerDown(long j, int i, int i2, int i3);

    private static native void nativeSendPointerSync(long j);

    private static native void nativeSendPointerUp(long j, int i);

    public static UinputBridge openGamepad(IBinder iBinder, String str) {
        if (iBinder == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
        long nativeGamepadOpen = nativeGamepadOpen(str, iBinder.toString());
        if (nativeGamepadOpen != 0) {
            return new UinputBridge(iBinder, nativeGamepadOpen);
        }
        throw new IOException(
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Could not open uinput device ", str));
    }

    public final void clear(IBinder iBinder) {
        if (this.mToken.equals(iBinder)) {
            nativeClear(this.mPtr);
        }
    }

    public final void close(IBinder iBinder) {
        if (!this.mToken.equals(iBinder) || this.mPtr == 0) {
            return;
        }
        clear(iBinder);
        nativeClose(this.mPtr);
        this.mPtr = 0L;
        this.mCloseGuard.close();
    }

    public final void finalize() {
        try {
            this.mCloseGuard.warnIfOpen();
            close(this.mToken);
        } finally {
            this.mToken = null;
            super.finalize();
        }
    }

    public final void sendGamepadAxisValue(IBinder iBinder, int i, float f) {
        if (this.mToken.equals(iBinder)) {
            nativeSendGamepadAxisValue(this.mPtr, i, f);
        }
    }

    public final void sendGamepadKey(int i, boolean z, IBinder iBinder) {
        if (this.mToken.equals(iBinder)) {
            nativeSendGamepadKey(this.mPtr, i, z);
        }
    }

    public final void sendKeyDown(IBinder iBinder, int i) {
        if (this.mToken.equals(iBinder)) {
            nativeSendKey(this.mPtr, i, true);
        }
    }

    public final void sendKeyUp(IBinder iBinder, int i) {
        if (this.mToken.equals(iBinder)) {
            nativeSendKey(this.mPtr, i, false);
        }
    }

    public final void sendPointerDown(IBinder iBinder, int i, int i2, int i3) {
        if (this.mToken.equals(iBinder)) {
            nativeSendPointerDown(this.mPtr, i, i2, i3);
        }
    }

    public final void sendPointerSync(IBinder iBinder) {
        if (this.mToken.equals(iBinder)) {
            nativeSendPointerSync(this.mPtr);
        }
    }

    public final void sendPointerUp(IBinder iBinder, int i) {
        if (this.mToken.equals(iBinder)) {
            nativeSendPointerUp(this.mPtr, i);
        }
    }
}
