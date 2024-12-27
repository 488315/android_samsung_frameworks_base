package com.android.server.biometrics.sensors.fingerprint.hidl;

import android.content.Context;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback;
import android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint;
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
import com.android.server.biometrics.sensors.fingerprint.FingerprintUtils;
import com.att.iqi.lib.metrics.hw.HwConstants;
import com.att.iqi.lib.metrics.mm.MM05;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TestHal extends HwBinder implements IBiometricsFingerprint {
    public IBiometricsFingerprintClientCallback mCallback;
    public final Context mContext;
    public final int mSensorId;

    public TestHal(Context context, int i) {
        this.mContext = context;
        this.mSensorId = i;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int authenticate(int i, long j) {
        Slog.w("fingerprint.hidl.TestHal", "Authenticate");
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int cancel() {
        IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback = this.mCallback;
        if (iBiometricsFingerprintClientCallback != null) {
            iBiometricsFingerprintClientCallback.onError(5, 0, 0L);
        }
        return 0;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int enroll(int i, int i2, byte[] bArr) {
        Slog.w("fingerprint.hidl.TestHal", "enroll");
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int enumerate() {
        Slog.w("fingerprint.hidl.TestHal", "Enumerate");
        IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback = this.mCallback;
        if (iBiometricsFingerprintClientCallback == null) {
            return 0;
        }
        iBiometricsFingerprintClientCallback.onEnumerate(0, 0, 0, 0L);
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long getAuthenticatorId() {
        return 0L;
    }

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
        return new ArrayList(Arrays.asList(new byte[]{122, 120, -23, -106, 59, -20, MM05.IQ_SIP_CALL_STATE_DISCONNECTING, 7, 30, 125, 70, -110, -116, 97, 0, -30, 23, 66, 112, -119, 45, 63, 21, -95, -22, -83, 7, 73, -105, -83, -14, 121}, new byte[]{20, 15, -113, 98, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 12, -49, -100, -46, -126, -82, 54, -123, -96, -12, -17, 10, -97, -105, 29, 119, -33, -68, 115, 80, -52, -76, -32, 76, -14, -107, -20}, new byte[]{31, -67, -63, -8, 82, -8, -67, 46, 74, 108, 92, -77, 10, -62, -73, -122, 104, -55, -115, -50, 17, -118, 97, 118, 45, 64, 52, -82, -123, -97, 67, -40}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final ArrayList interfaceChain() {
        return new ArrayList(Arrays.asList("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint", "android.hardware.biometrics.fingerprint@2.2::IBiometricsFingerprint", "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint", IBase.kInterfaceName));
    }

    @Override // android.hidl.base.V1_0.IBase
    public final String interfaceDescriptor() {
        return "android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
        return true;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void notifySyspropsChanged() {
        HwBinder.enableInstrumentation();
    }

    @Override // android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint
    public final void onFingerDown(int i, float f, float f2, int i2) {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_3.IBiometricsFingerprint
    public final void onFingerUp() {
    }

    public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
        switch (i) {
            case 1:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                this.mCallback = IBiometricsFingerprintClientCallback.asInterface(hwParcel.readStrongBinder());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(1L);
                hwParcel2.send();
                return;
            case 2:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(0L);
                hwParcel2.send();
                return;
            case 3:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                byte[] bArr = new byte[69];
                hwParcel.readBuffer(69L).copyToInt8Array(0L, bArr, 69);
                enroll(hwParcel.readInt32(), hwParcel.readInt32(), bArr);
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 4:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 5:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt64(0L);
                hwParcel2.send();
                return;
            case 6:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                cancel();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 7:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                enumerate();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 8:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                remove(hwParcel.readInt32(), hwParcel.readInt32());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 9:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                hwParcel.readInt32();
                hwParcel.readString();
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 10:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
                authenticate(hwParcel.readInt32(), hwParcel.readInt64());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(0);
                hwParcel2.send();
                return;
            case 11:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint");
                hwParcel.readInt32();
                hwParcel2.writeStatus(0);
                hwParcel2.writeBool(false);
                hwParcel2.send();
                return;
            case 12:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint");
                hwParcel.readInt32();
                hwParcel.readInt32();
                hwParcel.readFloat();
                hwParcel.readFloat();
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            case 13:
                hwParcel.enforceInterface("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint");
                hwParcel2.writeStatus(0);
                hwParcel2.send();
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
                        hwParcel2.writeString("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint");
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
                            byte[] bArr2 = (byte[]) hashChain.get(i3);
                            if (bArr2 == null || bArr2.length != 32) {
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j, bArr2);
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
    public final void ping() {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int postEnroll() {
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long preEnroll() {
        return 0L;
    }

    public final IHwInterface queryLocalInterface(String str) {
        if ("android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint".equals(str)) {
            return this;
        }
        return null;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int remove(int i, int i2) {
        Slog.w("fingerprint.hidl.TestHal", "Remove");
        IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback = this.mCallback;
        if (iBiometricsFingerprintClientCallback != null) {
            if (i2 == 0) {
                List biometricsForUser = FingerprintUtils.getInstance(this.mSensorId).getBiometricsForUser(this.mContext, i);
                for (int i3 = 0; i3 < biometricsForUser.size(); i3++) {
                    this.mCallback.onRemoved(((Fingerprint) biometricsForUser.get(i3)).getBiometricId(), i, (biometricsForUser.size() - i3) - 1, 0L);
                }
            } else {
                iBiometricsFingerprintClientCallback.onRemoved(i2, i, 0, 0L);
            }
        }
        return 0;
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final int setActiveGroup(int i, String str) {
        return 0;
    }

    @Override // android.hidl.base.V1_0.IBase
    public final void setHALInstrumentation() {
    }

    @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
    public final long setNotify(IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback) {
        this.mCallback = iBiometricsFingerprintClientCallback;
        return 1L;
    }

    public final String toString() {
        return "android.hardware.biometrics.fingerprint@2.3::IBiometricsFingerprint@Stub";
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return true;
    }
}
