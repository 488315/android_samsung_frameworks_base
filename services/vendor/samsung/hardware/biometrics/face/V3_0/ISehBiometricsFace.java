package vendor.samsung.hardware.biometrics.face.V3_0;

import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
import android.hardware.biometrics.face.V1_0.OptionalBool;
import android.hardware.biometrics.face.V1_0.OptionalUint64;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlMemory;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace;

/* loaded from: classes2.dex */
public interface ISehBiometricsFace extends vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace {
    @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace, android.hidl.base.V1_0.IBase
    ArrayList interfaceChain();

    int sehAuthenticateForIssuance(long j, int i, ArrayList arrayList, boolean z, boolean z2);

    OptionalUint64 sehSetCallbackEx(ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback);

    static ISehBiometricsFace asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
        if (queryLocalInterface != null && (queryLocalInterface instanceof ISehBiometricsFace)) {
            return (ISehBiometricsFace) queryLocalInterface;
        }
        Proxy proxy = new Proxy(iHwBinder);
        try {
            Iterator it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace")) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static ISehBiometricsFace getService(String str) {
        return asInterface(HwBinder.getService("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace", str));
    }

    static ISehBiometricsFace getService() {
        return getService("default");
    }

    public final class Proxy implements ISehBiometricsFace {
        public IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            Objects.requireNonNull(iHwBinder);
            this.mRemote = iHwBinder;
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int setActiveUser(int i, String str) {
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
        public OptionalUint64 generateChallenge(int i) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel2);
                return optionalUint64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int enroll(ArrayList arrayList, int i, ArrayList arrayList2) {
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
        public int revokeChallenge() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int setFeature(int i, boolean z, ArrayList arrayList, int i2) {
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public OptionalBool getFeature(int i, int i2) {
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
                optionalBool.readFromParcel(hwParcel2);
                return optionalBool;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public OptionalUint64 getAuthenticatorId() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel2);
                return optionalUint64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int cancel() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int enumerate() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
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

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int remove(int i) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.biometrics.face@1.0::IBiometricsFace");
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace
        public int authenticate(long j) {
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

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public OptionalUint64 sehSetCallback(vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            hwParcel.writeStrongBinder(iSehBiometricsFaceClientCallback == null ? null : iSehBiometricsFaceClientCallback.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel2);
                return optionalUint64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehAuthenticate(long j, int i, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
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
        public int sehOpenTaSession() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehCloseTaSession() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public boolean sehIsTaSessionClosed() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehPauseEnrollment() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehResumeEnrollment() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public String sehGetTaInfo() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehSetRotation(int i) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public void sehGetSecurityLevel(ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                sehgetsecuritylevelcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public int sehSetFaceTag(int i, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
        public OptionalUint64 sehSetCallbackEx(ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
            hwParcel.writeStrongBinder(iSehBiometricsFaceClientCallback == null ? null : iSehBiometricsFaceClientCallback.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                OptionalUint64 optionalUint64 = new OptionalUint64();
                optionalUint64.readFromParcel(hwParcel2);
                return optionalUint64;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
        public int sehAuthenticateForIssuance(long j, int i, ArrayList arrayList, boolean z, boolean z2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeBool(z);
            hwParcel.writeBool(z2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace, vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace, android.hidl.base.V1_0.IBase
        public ArrayList interfaceChain() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256067662, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace, android.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList arrayList) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public String interfaceDescriptor() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256136003, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readString();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public ArrayList getHashChain() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel2.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    readEmbeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public void setHALInstrumentation() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hidl.base.V1_0.IBase
        public void ping() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                DebugInfo debugInfo = new DebugInfo();
                debugInfo.readFromParcel(hwParcel2);
                return debugInfo;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public abstract class Stub extends HwBinder implements ISehBiometricsFace {
        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace, android.hidl.base.V1_0.IBase
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.biometrics.face.V1_0.IBiometricsFace, android.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList arrayList) {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace, vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace, android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            return new ArrayList(Arrays.asList("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace", "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace", "android.hardware.biometrics.face@1.0::IBiometricsFace", IBase.kInterfaceName));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            return new ArrayList(Arrays.asList(new byte[]{-61, 46, 84, -8, -19, 22, 109, -87, -4, -19, -1, -41, -82, -42, 38, 51, -17, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 8, 5, 55, 7, -44, 93, -110, 60, 115, -82, 109, 77, -87, -127}, new byte[]{8, 28, 96, -68, -101, -9, -80, 95, 53, -43, 71, -117, -62, 107, -63, 109, -95, Byte.MIN_VALUE, -3, -33, 119, -121, 64, 54, 118, 12, 79, 108, -14, 126, -22, 68}, new byte[]{-31, -113, -13, 24, -13, -4, 67, -37, 55, -11, 84, 105, 109, -60, -27, 81, -85, -71, -79, 25, -67, -27, 57, 80, -9, 62, 40, -50, 51, -87, 122, 64}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
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
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public IHwInterface queryLocalInterface(String str) {
            if ("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace".equals(str)) {
                return this;
            }
            return null;
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        public static /* synthetic */ HidlMemory lambda$onTransact$0(HwParcel hwParcel) {
            try {
                return hwParcel.readHidlMemory().dup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalUint64 callback = setCallback(IBiometricsFaceClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    callback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int activeUser = setActiveUser(hwParcel.readInt32(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(activeUser);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalUint64 generateChallenge = generateChallenge(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    generateChallenge.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int enroll = enroll(hwParcel.readInt8Vector(), hwParcel.readInt32(), hwParcel.readInt32Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enroll);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int revokeChallenge = revokeChallenge();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(revokeChallenge);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int feature = setFeature(hwParcel.readInt32(), hwParcel.readBool(), hwParcel.readInt8Vector(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(feature);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalBool feature2 = getFeature(hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    feature2.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalUint64 authenticatorId = getAuthenticatorId();
                    hwParcel2.writeStatus(0);
                    authenticatorId.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int cancel = cancel();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(cancel);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int enumerate = enumerate();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enumerate);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int remove = remove(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(remove);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int authenticate = authenticate(hwParcel.readInt64());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(authenticate);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int userActivity = userActivity();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(userActivity);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface("android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int resetLockout = resetLockout(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(resetLockout);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    OptionalUint64 sehSetCallback = sehSetCallback(vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    sehSetCallback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehAuthenticate = sehAuthenticate(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehAuthenticate);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehOpenTaSession = sehOpenTaSession();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehOpenTaSession);
                    hwParcel2.send();
                    return;
                case 18:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehCloseTaSession = sehCloseTaSession();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehCloseTaSession);
                    hwParcel2.send();
                    return;
                case 19:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    boolean sehIsTaSessionClosed = sehIsTaSessionClosed();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(sehIsTaSessionClosed);
                    hwParcel2.send();
                    return;
                case 20:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehPauseEnrollment = sehPauseEnrollment();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehPauseEnrollment);
                    hwParcel2.send();
                    return;
                case 21:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehResumeEnrollment = sehResumeEnrollment();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehResumeEnrollment);
                    hwParcel2.send();
                    return;
                case 22:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    String sehGetTaInfo = sehGetTaInfo();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(sehGetTaInfo);
                    hwParcel2.send();
                    return;
                case 23:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    String sehGetEngineVersion = sehGetEngineVersion();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(sehGetEngineVersion);
                    hwParcel2.send();
                    return;
                case 24:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehSetRotation = sehSetRotation(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehSetRotation);
                    hwParcel2.send();
                    return;
                case 25:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehPrepareTaInstallation = sehPrepareTaInstallation();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehPrepareTaInstallation);
                    hwParcel2.send();
                    return;
                case 26:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehInstallTaDataChunk = sehInstallTaDataChunk((HidlMemory) new Function() { // from class: vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace$Stub$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            HidlMemory lambda$onTransact$0;
                            lambda$onTransact$0 = ISehBiometricsFace.Stub.lambda$onTransact$0((HwParcel) obj);
                            return lambda$onTransact$0;
                        }
                    }.apply(hwParcel));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehInstallTaDataChunk);
                    hwParcel2.send();
                    return;
                case 27:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehFinishTaInstallation = sehFinishTaInstallation(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehFinishTaInstallation);
                    hwParcel2.send();
                    return;
                case 28:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehConfigurePreview = sehConfigurePreview(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehConfigurePreview);
                    hwParcel2.send();
                    return;
                case 29:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehGetServicePid = sehGetServicePid();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehGetServicePid);
                    hwParcel2.send();
                    return;
                case 30:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    sehGetSecurityLevel(new ISehBiometricsFace.sehGetSecurityLevelCallback() { // from class: vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace.Stub.1
                        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace.sehGetSecurityLevelCallback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 31:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehSetSecurityLevel = sehSetSecurityLevel(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehSetSecurityLevel);
                    hwParcel2.send();
                    return;
                case 32:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    sehGetFaceTagList(new ISehBiometricsFace.sehGetFaceTagListCallback() { // from class: vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace.Stub.2
                    });
                    return;
                case 33:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    sehGetFaceTag(hwParcel.readInt32(), new ISehBiometricsFace.sehGetFaceTagCallback() { // from class: vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace.Stub.3
                    });
                    return;
                case 34:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehSetFaceTag = sehSetFaceTag(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehSetFaceTag);
                    hwParcel2.send();
                    return;
                case 35:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
                    OptionalUint64 sehSetCallbackEx = sehSetCallbackEx(ISehBiometricsFaceClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    sehSetCallbackEx.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 36:
                    hwParcel.enforceInterface("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
                    int sehAuthenticateForIssuance = sehAuthenticateForIssuance(hwParcel.readInt64(), hwParcel.readInt32(), hwParcel.readInt8Vector(), hwParcel.readBool(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehAuthenticateForIssuance);
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
                            debug(hwParcel.readNativeHandle(), hwParcel.readStringVector());
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            String interfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(interfaceDescriptor);
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
                                    throw new IllegalArgumentException("Array element is not of the expected length");
                                }
                                hwBlob2.putInt8Array(j, bArr);
                            }
                            hwBlob.putBlob(0L, hwBlob2);
                            hwParcel2.writeBuffer(hwBlob);
                            hwParcel2.send();
                            return;
                        case 256462420:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            setHALInstrumentation();
                            return;
                        case 256921159:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ping();
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
                            notifySyspropsChanged();
                            return;
                        default:
                            return;
                    }
            }
        }
    }
}
