package com.android.server.knox.dar.sdp;

import android.annotation.SystemApi;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.util.Log;

public class SdpManagerImpl {
    public static final UserInfo NULL_USER = null;

    static {
        new UserInfo(-10000, (String) null, (String) null, 0);
    }

    public static void checkSystemPermission() {
        if (Binder.getCallingUid() == 1000) {
            return;
        }
        Log.e("SdpManagerImpl", "Require system permission.");
        throw new SecurityException(
                "Security Exception Occurred in pid["
                        + Binder.getCallingPid()
                        + "] with uid["
                        + Binder.getCallingUid()
                        + "]");
    }

    public static void isSupportedDevice() {
        Log.e("SdpManagerImpl", "SDP not supported");
    }

    public static native int nativeOnBoot(int i, int i2);

    public static native int nativeOnChangePassword(int i, byte[] bArr, byte[] bArr2);

    public static native int nativeOnDeviceLocked(int i, int i2);

    public static native int nativeOnDeviceUnlocked(int i, byte[] bArr);

    public static native int nativeOnMigration(int i, int i2, int i3, int i4, byte[] bArr);

    public static native int nativeOnUserAdded(int i, int i2, byte[] bArr);

    public static native int nativeOnUserRemoved(int i, int i2);

    @SystemApi
    public byte[] getResetToken(int i) {
        checkSystemPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            throw null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @SystemApi
    public byte[] getResetTokenSafe(int i) {
        checkSystemPermission();
        throw null;
    }

    @SystemApi
    public long getTokenHandle(int i) {
        checkSystemPermission();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            throw null;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    @SystemApi
    public void initializeMasterKeyIfRequired(byte[] bArr, int i) {
        isSupportedDevice();
    }

    @SystemApi
    public boolean isSdpPackage(int i, String str) {
        throw null;
    }

    @SystemApi
    public void lockSdpIfRequired(int i) {
        isSupportedDevice();
    }

    @SystemApi
    public void onCredentialChanged(int i, int i2) {
        isSupportedDevice();
    }

    @SystemApi
    public void onDeviceLocked(int i) {
        isSupportedDevice();
    }

    @SystemApi
    public void onDeviceUnlocked(int i) {
        isSupportedDevice();
    }

    @SystemApi
    public void saveMasterKeyIfUnsecured(byte[] bArr, int i) {
        isSupportedDevice();
    }

    @SystemApi
    public void saveResetTokenSafe(byte[] bArr, int i) {}

    @SystemApi
    public void unlockSdpIfUnsecuredOrBiometricAuthenticated(int i, int i2) {
        isSupportedDevice();
    }

    @SystemApi
    public void unlockSdpOnCredentialVerified(byte[] bArr, int i) {
        isSupportedDevice();
    }

    @SystemApi
    public void welcomeNewUser(int i) {
        isSupportedDevice();
    }
}
