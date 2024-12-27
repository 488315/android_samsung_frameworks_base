package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback;
import android.hardware.fingerprint.Fingerprint;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.util.Slog;

import com.android.server.biometrics.HardwareAuthTokenUtils;
import com.android.server.biometrics.sensors.EnumerateConsumer;
import com.android.server.biometrics.sensors.ErrorConsumer;
import com.android.server.biometrics.sensors.RemovalConsumer;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda0;
import com.android.server.biometrics.sensors.fingerprint.aidl.AidlResponseHandler$$ExternalSyntheticLambda10;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler;
import com.android.server.biometrics.sensors.fingerprint.aidl.SemFpAidlResponseHandler$$ExternalSyntheticLambda11;

import com.att.iqi.lib.metrics.hw.HwConstants;

import java.util.ArrayList;
import java.util.Arrays;

public final class HidlToAidlCallbackConverter extends HwBinder
        implements IBiometricsFingerprintClientCallback {
    public final SemFpAidlResponseHandler mAidlResponseHandler;

    public HidlToAidlCallbackConverter(SemFpAidlResponseHandler semFpAidlResponseHandler) {
        this.mAidlResponseHandler = semFpAidlResponseHandler;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {}

    @Override // android.hidl.base.V1_0.IBase
    public final DebugInfo getDebugInfo() {
        DebugInfo debugInfo = new DebugInfo();
        debugInfo.pid = HidlSupport.getPidIfSharable();
        debugInfo.ptr = 0L;
        debugInfo.arch = 0;
        return debugInfo;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList getHashChain() {
        return new ArrayList(
                Arrays.asList(
                        new byte[] {
                            -126, -54, -39, -97, 95, -21, 46, -87, -68, -44, 87, -112, 85, -19, -12,
                            -81, -113, -21, -97, -58, 2, -90, -28, -126, 125, -35, 114, 125, 37, 77,
                            73, -111
                        },
                        new byte[] {
                            -86,
                            -69,
                            92,
                            60,
                            88,
                            85,
                            -110,
                            -41,
                            30,
                            -27,
                            123,
                            119,
                            41,
                            -116,
                            20,
                            -103,
                            61,
                            119,
                            -111,
                            77,
                            -34,
                            -86,
                            100,
                            -78,
                            -59,
                            HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
                            -102,
                            96,
                            43,
                            2,
                            -22,
                            71
                        },
                        new byte[] {
                            -20,
                            Byte.MAX_VALUE,
                            -41,
                            -98,
                            -48,
                            45,
                            -6,
                            -123,
                            -68,
                            73,
                            -108,
                            38,
                            -83,
                            -82,
                            62,
                            -66,
                            35,
                            -17,
                            5,
                            36,
                            -13,
                            -51,
                            105,
                            87,
                            19,
                            -109,
                            36,
                            -72,
                            59,
                            24,
                            -54,
                            76
                        }));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
        return new ArrayList(
                Arrays.asList(
                        "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback",
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback",
                        IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        return "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwBinder.enableInstrumentation();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onAcquired(int i, int i2, long j) {
        this.mAidlResponseHandler.onAcquired(i, i2);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onAuthenticated(long j, int i, int i2, ArrayList arrayList) {
        if (i == 0) {
            this.mAidlResponseHandler.onAuthenticationFailed();
            return;
        }
        byte[] bArr = new byte[arrayList.size()];
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            bArr[i3] = ((Byte) arrayList.get(i3)).byteValue();
        }
        this.mAidlResponseHandler.onAuthenticationSucceeded(
                i, HardwareAuthTokenUtils.toHardwareAuthToken(bArr));
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onEnrollResult(int i, int i2, int i3, long j) {
        this.mAidlResponseHandler.onEnrollmentProgress(i, i3);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onEnumerate(int i, int i2, int i3, long j) {
        SemFpAidlResponseHandler semFpAidlResponseHandler = this.mAidlResponseHandler;
        semFpAidlResponseHandler.getClass();
        semFpAidlResponseHandler.handleResponse(
                EnumerateConsumer.class,
                new AidlResponseHandler$$ExternalSyntheticLambda10(
                        new Fingerprint("", i, semFpAidlResponseHandler.mSensorId), i3, 1),
                null);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onError(int i, int i2, long j) {
        SemFpAidlResponseHandler semFpAidlResponseHandler = this.mAidlResponseHandler;
        semFpAidlResponseHandler.getClass();
        semFpAidlResponseHandler.handleResponse(
                ErrorConsumer.class,
                new SemFpAidlResponseHandler$$ExternalSyntheticLambda11(
                        semFpAidlResponseHandler, i, i2, 1),
                null,
                0L);
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback
    public final void onRemoved(int i, int i2, int i3, long j) {
        SemFpAidlResponseHandler semFpAidlResponseHandler = this.mAidlResponseHandler;
        semFpAidlResponseHandler.getClass();
        semFpAidlResponseHandler.handleResponse(
                RemovalConsumer.class,
                new AidlResponseHandler$$ExternalSyntheticLambda10(
                        new Fingerprint("", i, semFpAidlResponseHandler.mSensorId), i3, 0),
                null);
    }

    public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
        switch (i) {
            case 1:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                onEnrollResult(
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt64());
                return;
            case 2:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                hwParcel.readInt64();
                this.mAidlResponseHandler.onAcquired(hwParcel.readInt32(), hwParcel.readInt32());
                return;
            case 3:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                onAuthenticated(
                        hwParcel.readInt64(),
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt8Vector());
                return;
            case 4:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                onError(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt64());
                return;
            case 5:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                onRemoved(
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt64());
                return;
            case 6:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprintClientCallback");
                onEnumerate(
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt32(),
                        hwParcel.readInt64());
                return;
            case 7:
                hwParcel.enforceInterface(
                        "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback");
                hwParcel.readInt64();
                this.mAidlResponseHandler.onAcquired(hwParcel.readInt32(), hwParcel.readInt32());
                return;
            default:
                switch (i) {
                    case 256067662:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        ArrayList interfaceChain = interfaceChain();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeStringVector(interfaceChain);
                        hwParcel2.send();
                        return;
                    case 256131655:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel.readNativeHandle();
                        hwParcel.readStringVector();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 256136003:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeString(
                                "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback");
                        hwParcel2.send();
                        return;
                    case 256398152:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        ArrayList hashChain = getHashChain();
                        hwParcel2.writeStatus(0);
                        HwBlob hwBlob = new HwBlob(16);
                        int size = hashChain.size();
                        hwBlob.putInt32(8L, size);
                        hwBlob.putBool(12L, false);
                        HwBlob hwBlob2 = new HwBlob(size * 32);
                        for (int i3 = 0; i3 < size; i3++) {
                            long j = i3 * 32;
                            byte[] bArr = (byte[]) hashChain.get(i3);
                            if (bArr == null || bArr.length != 32) {
                                throw new IllegalArgumentException(
                                        "Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j, bArr);
                        }
                        hwBlob.putBlob(0L, hwBlob2);
                        hwParcel2.writeBuffer(hwBlob);
                        hwParcel2.send();
                        return;
                    case 256462420:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        return;
                    case 256921159:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 257049926:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        DebugInfo debugInfo = getDebugInfo();
                        hwParcel2.writeStatus(0);
                        debugInfo.writeToParcel(hwParcel2);
                        hwParcel2.send();
                        return;
                    case 257120595:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        HwBinder.enableInstrumentation();
                        return;
                    default:
                        return;
                }
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void ping() {}

    public final IHwInterface queryLocalInterface(String str) {
        if ("android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback"
                .equals(str)) {
            return this;
        }
        return null;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {}

    public final String toString() {
        return "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprintClientCallback@Stub";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return true;
    }

    public final void unsupportedClientScheduled(Class cls) {
        SemFpAidlResponseHandler semFpAidlResponseHandler = this.mAidlResponseHandler;
        semFpAidlResponseHandler.getClass();
        Slog.e("AidlResponseHandler", cls + " is not supported in the HAL.");
        semFpAidlResponseHandler.handleResponse(
                cls, new AidlResponseHandler$$ExternalSyntheticLambda0(3), null);
    }
}
