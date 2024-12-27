package vendor.samsung.hardware.biometrics.face.V2_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.IBiometricsFace;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hardware.broadcastradio.V2_0.ITunerSession$Proxy$$ExternalSyntheticOutline0;
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

public interface ISehBiometricsFace extends IBiometricsFace {

    public final class Proxy implements ISehBiometricsFace {
        public IHwBinder mRemote;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int authenticate(long j) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt64(j);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int cancel() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(9, m, hwParcel, 0);
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32Vector(arrayList2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int enumerate() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(10, m, hwParcel, 0);
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final OptionalUint64 generateChallenge(int i) {
            HwParcel m =
                    ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                            i, "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel);
                return optionalUint64;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final OptionalUint64 getAuthenticatorId() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(8, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel);
                return optionalUint64;
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final OptionalBool getFeature(int i, int i2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                OptionalBool optionalBool = new OptionalBool();
                HwBlob readBuffer = hwParcel2.readBuffer(8L);
                optionalBool.status = readBuffer.getInt32(0L);
                optionalBool.value = readBuffer.getBool(4L);
                return optionalBool;
            } finally {
                hwParcel2.release();
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int remove(int i) {
            HwParcel m =
                    ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                            i, "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(11, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int resetLockout(ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int revokeChallenge() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(5, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final int sehAuthenticate(int i, long j, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final void sehGetSecurityLevel(
                sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(30, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                sehgetsecuritylevelcallback.onValues(hwParcel.readInt32(), hwParcel.readInt32());
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final OptionalUint64 sehSetCallback(
                ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            m.writeStrongBinder(
                    iSehBiometricsFaceClientCallback == null
                            ? null
                            : iSehBiometricsFaceClientCallback.asBinder());
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(15, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel);
                return optionalUint64;
            } finally {
                hwParcel.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int setActiveUser(int i, String str) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt32(i);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public final int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt32(i);
            hwParcel.writeBool(z);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
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

        public final String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of"
                           + " vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public interface sehGetSecurityLevelCallback {
        void onValues(int i, int i2);
    }

    static ISehBiometricsFace getService() {
        IHwBinder service =
                HwBinder.getService(
                        "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace",
                        "default");
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface =
                service.queryLocalInterface(
                        "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISehBiometricsFace)) {
            return (ISehBiometricsFace) queryLocalInterface;
        }
        Proxy proxy = new Proxy();
        proxy.mRemote = service;
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next())
                        .equals(
                                "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace")) {
                    return proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    int sehAuthenticate(int i, long j, ArrayList arrayList);

    int sehCloseTaSession();

    String sehGetEngineVersion();

    void sehGetSecurityLevel(sehGetSecurityLevelCallback sehgetsecuritylevelcallback);

    String sehGetTaInfo();

    boolean sehIsTaSessionClosed();

    int sehOpenTaSession();

    int sehPauseEnrollment();

    int sehResumeEnrollment();

    OptionalUint64 sehSetCallback(
            ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback);
}
