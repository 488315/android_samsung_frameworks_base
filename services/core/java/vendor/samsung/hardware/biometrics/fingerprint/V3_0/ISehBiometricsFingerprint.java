package vendor.samsung.hardware.biometrics.fingerprint.V3_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint;
import android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprintClientCallback;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ISehBiometricsFingerprint extends IBiometricsFingerprint {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Proxy implements ISehBiometricsFingerprint {
        public IHwBinder mRemote;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int authenticate(int i, long j) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int cancel() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(6, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            IBase.kInterfaceName, nativeHandle, arrayList);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256131655, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int enroll(int i, int i2, byte[] bArr) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwBlob hwBlob = new HwBlob(69);
            if (bArr.length != 69) {
                throw new IllegalArgumentException("Array element is not of the expected length");
            }
            hwBlob.putInt8Array(0L, bArr);
            m.writeBuffer(hwBlob);
            m.writeInt32(i);
            m.writeInt32(i2);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int enumerate() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(7, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final long getAuthenticatorId() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(5, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt64();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257049926, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel);
                return debugInfo;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer =
                        hwParcel.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel.release();
            }
        }

        public final int hashCode() {
            return this.mRemote.hashCode();
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256067662, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readStringVector();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256136003, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readString();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257120595, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256921159, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int postEnroll() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(4, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final long preEnroll() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(2, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt64();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int remove(int i, int i2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.fingerprint.V3_0.ISehBiometricsFingerprint
        public final void sehRequest(
                int i, int i2, ArrayList arrayList, sehRequestCallback sehrequestcallback) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint");
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                sehrequestcallback.onValues(hwParcel2.readInt8Vector(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final int setActiveGroup(int i, String str) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
            HwParcel m = IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(IBase.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256462420, m, hwParcel, 1);
                m.releaseTemporaryStorage();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.fingerprint.V2_1.IBiometricsFingerprint
        public final long setNotify(
                IBiometricsFingerprintClientCallback iBiometricsFingerprintClientCallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.fingerprint@2.1::IBiometricsFingerprint");
            m.writeStrongBinder(
                    iBiometricsFingerprintClientCallback == null
                            ? null
                            : iBiometricsFingerprintClientCallback.asBinder());
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt64();
            } finally {
                hwParcel.release();
            }
        }

        public final String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of"
                           + " vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface sehRequestCallback {
        void onValues(ArrayList arrayList, int i);
    }

    static ISehBiometricsFingerprint getService() {
        IHwBinder service =
                HwBinder.getService(
                        "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint",
                        "default");
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface =
                service.queryLocalInterface(
                        "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint");
        if (queryLocalInterface != null
                && (queryLocalInterface instanceof ISehBiometricsFingerprint)) {
            return (ISehBiometricsFingerprint) queryLocalInterface;
        }
        Proxy proxy = new Proxy();
        proxy.mRemote = service;
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next())
                        .equals(
                                "vendor.samsung.hardware.biometrics.fingerprint@3.0::ISehBiometricsFingerprint")) {
                    return proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    void sehRequest(int i, int i2, ArrayList arrayList, sehRequestCallback sehrequestcallback);
}
