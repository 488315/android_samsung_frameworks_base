package vendor.samsung.hardware.biometrics.face.V3_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.biometrics.face.V1_0.IBiometricsFaceClientCallback;
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

import com.att.iqi.lib.metrics.hw.HwConstants;

import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace;
import vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface ISehBiometricsFace
        extends vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
        public final int sehAuthenticateForIssuance(
                int i, long j, ArrayList arrayList, boolean z, boolean z2) {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(
                    "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
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

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final int sehCloseTaSession() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(18, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final void sehGetSecurityLevel(
                ISehBiometricsFace.sehGetSecurityLevelCallback sehgetsecuritylevelcallback) {
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
        public final String sehGetTaInfo() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(22, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readString();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final boolean sehIsTaSessionClosed() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(19, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readBool();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final int sehOpenTaSession() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(17, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final int sehPauseEnrollment() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(20, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final int sehResumeEnrollment() {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(21, m, hwParcel, 0);
                hwParcel.verifySuccess();
                m.releaseTemporaryStorage();
                return hwParcel.readInt32();
            } finally {
                hwParcel.release();
            }
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public final OptionalUint64 sehSetCallback(
                vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
                        iSehBiometricsFaceClientCallback) {
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

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
        public final OptionalUint64 sehSetCallbackEx(
                ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
            HwParcel m =
                    IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                            "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
            m.writeStrongBinder(
                    iSehBiometricsFaceClientCallback == null
                            ? null
                            : iSehBiometricsFaceClientCallback.asBinder());
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(35, m, hwParcel, 0);
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
                           + " vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace]@Proxy";
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class Stub extends HwBinder implements ISehBiometricsFace {
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
                                -61,
                                46,
                                84,
                                -8,
                                -19,
                                22,
                                109,
                                -87,
                                -4,
                                -19,
                                -1,
                                -41,
                                -82,
                                -42,
                                38,
                                51,
                                -17,
                                HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED,
                                8,
                                5,
                                55,
                                7,
                                -44,
                                93,
                                -110,
                                60,
                                115,
                                -82,
                                109,
                                77,
                                -87,
                                -127
                            },
                            new byte[] {
                                8,
                                28,
                                96,
                                -68,
                                -101,
                                -9,
                                -80,
                                95,
                                53,
                                -43,
                                71,
                                -117,
                                -62,
                                107,
                                -63,
                                109,
                                -95,
                                Byte.MIN_VALUE,
                                -3,
                                -33,
                                119,
                                -121,
                                64,
                                54,
                                118,
                                12,
                                79,
                                108,
                                -14,
                                126,
                                -22,
                                68
                            },
                            new byte[] {
                                -31, -113, -13, 24, -13, -4, 67, -37, 55, -11, 84, 105, 109, -60,
                                -27, 81, -85, -71, -79, 25, -67, -27, 57, 80, -9, 62, 40, -50, 51,
                                -87, 122, 64
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
                            "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace",
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace",
                            "android.hardware.biometrics.face@1.0::IBiometricsFace",
                            IBase.kInterfaceName));
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
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) {
            IBiometricsFaceClientCallback iBiometricsFaceClientCallback = null;
            r2 = null;
            r2 = null;
            ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback = null;
            r2 = null;
            r2 = null;
            vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
                    iSehBiometricsFaceClientCallback2 = null;
            iBiometricsFaceClientCallback = null;
            iBiometricsFaceClientCallback = null;
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    IHwBinder readStrongBinder = hwParcel.readStrongBinder();
                    if (readStrongBinder != null) {
                        IHwInterface queryLocalInterface =
                                readStrongBinder.queryLocalInterface(
                                        "android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback");
                        if (queryLocalInterface == null
                                || !(queryLocalInterface
                                        instanceof IBiometricsFaceClientCallback)) {
                            IBiometricsFaceClientCallback.Proxy proxy =
                                    new IBiometricsFaceClientCallback.Proxy();
                            proxy.mRemote = readStrongBinder;
                            try {
                                Iterator it = proxy.interfaceChain().iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        if (((String) it.next())
                                                .equals(
                                                        "android.hardware.biometrics.face@1.0::IBiometricsFaceClientCallback")) {
                                            iBiometricsFaceClientCallback = proxy;
                                        }
                                    }
                                }
                            } catch (RemoteException unused) {
                            }
                        } else {
                            iBiometricsFaceClientCallback =
                                    (IBiometricsFaceClientCallback) queryLocalInterface;
                        }
                    }
                    OptionalUint64 callback = setCallback(iBiometricsFaceClientCallback);
                    hwParcel2.writeStatus(0);
                    callback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int activeUser = setActiveUser(hwParcel.readInt32(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(activeUser);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalUint64 generateChallenge = generateChallenge(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    generateChallenge.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int enroll =
                            enroll(
                                    hwParcel.readInt8Vector(),
                                    hwParcel.readInt32(),
                                    hwParcel.readInt32Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enroll);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int revokeChallenge = revokeChallenge();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(revokeChallenge);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int feature =
                            setFeature(
                                    hwParcel.readInt32(),
                                    hwParcel.readBool(),
                                    hwParcel.readInt8Vector(),
                                    hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(feature);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalBool feature2 = getFeature(hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    HwBlob hwBlob = new HwBlob(8);
                    hwBlob.putInt32(0L, feature2.status);
                    hwBlob.putBool(4L, feature2.value);
                    hwParcel2.writeBuffer(hwBlob);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    OptionalUint64 authenticatorId = getAuthenticatorId();
                    hwParcel2.writeStatus(0);
                    authenticatorId.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int cancel = cancel();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(cancel);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int enumerate = enumerate();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enumerate);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int remove = remove(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(remove);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int authenticate = authenticate(hwParcel.readInt64());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(authenticate);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    userActivity();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(
                            "android.hardware.biometrics.face@1.0::IBiometricsFace");
                    int resetLockout = resetLockout(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(resetLockout);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    IHwBinder readStrongBinder2 = hwParcel.readStrongBinder();
                    if (readStrongBinder2 != null) {
                        IHwInterface queryLocalInterface2 =
                                readStrongBinder2.queryLocalInterface(
                                        "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback");
                        if (queryLocalInterface2 == null
                                || !(queryLocalInterface2
                                        instanceof
                                        vendor.samsung.hardware.biometrics.face.V2_0
                                                .ISehBiometricsFaceClientCallback)) {
                            ISehBiometricsFaceClientCallback.Proxy proxy2 =
                                    new ISehBiometricsFaceClientCallback.Proxy();
                            proxy2.mRemote = readStrongBinder2;
                            try {
                                Iterator it2 = proxy2.interfaceChain().iterator();
                                while (true) {
                                    if (it2.hasNext()) {
                                        if (((String) it2.next())
                                                .equals(
                                                        "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFaceClientCallback")) {
                                            iSehBiometricsFaceClientCallback2 = proxy2;
                                        }
                                    }
                                }
                            } catch (RemoteException unused2) {
                            }
                        } else {
                            iSehBiometricsFaceClientCallback2 =
                                    (vendor.samsung.hardware.biometrics.face.V2_0
                                                    .ISehBiometricsFaceClientCallback)
                                            queryLocalInterface2;
                        }
                    }
                    OptionalUint64 sehSetCallback =
                            sehSetCallback(iSehBiometricsFaceClientCallback2);
                    hwParcel2.writeStatus(0);
                    sehSetCallback.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehAuthenticate =
                            sehAuthenticate(
                                    hwParcel.readInt32(),
                                    hwParcel.readInt64(),
                                    hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehAuthenticate);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehOpenTaSession = sehOpenTaSession();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehOpenTaSession);
                    hwParcel2.send();
                    return;
                case 18:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehCloseTaSession = sehCloseTaSession();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehCloseTaSession);
                    hwParcel2.send();
                    return;
                case 19:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    boolean sehIsTaSessionClosed = sehIsTaSessionClosed();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(sehIsTaSessionClosed);
                    hwParcel2.send();
                    return;
                case 20:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehPauseEnrollment = sehPauseEnrollment();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehPauseEnrollment);
                    hwParcel2.send();
                    return;
                case 21:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    int sehResumeEnrollment = sehResumeEnrollment();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sehResumeEnrollment);
                    hwParcel2.send();
                    return;
                case 22:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    String sehGetTaInfo = sehGetTaInfo();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(sehGetTaInfo);
                    hwParcel2.send();
                    return;
                case 23:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    String sehGetEngineVersion = sehGetEngineVersion();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeString(sehGetEngineVersion);
                    hwParcel2.send();
                    return;
                case 24:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt32();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 25:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 26:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    try {
                        hwParcel.readHidlMemory().dup();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeInt32(0);
                        hwParcel2.send();
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                case 27:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt8Vector();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 28:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt32();
                    hwParcel.readInt8Vector();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 29:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 30:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    sehGetSecurityLevel(
                            new ISehBiometricsFace
                                    .sehGetSecurityLevelCallback() { // from class:
                                                                     // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace.Stub.1
                                @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace.sehGetSecurityLevelCallback
                                public final void onValues(int i3, int i4) {
                                    hwParcel2.writeStatus(0);
                                    hwParcel2.writeInt32(i3);
                                    hwParcel2.writeInt32(i4);
                                    hwParcel2.send();
                                }
                            });
                    return;
                case 31:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt32();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 32:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    return;
                case 33:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt32();
                    return;
                case 34:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@2.0::ISehBiometricsFace");
                    hwParcel.readInt32();
                    hwParcel.readInt8Vector();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(0);
                    hwParcel2.send();
                    return;
                case 35:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
                    IHwBinder readStrongBinder3 = hwParcel.readStrongBinder();
                    if (readStrongBinder3 != null) {
                        IHwInterface queryLocalInterface3 =
                                readStrongBinder3.queryLocalInterface(
                                        "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback");
                        if (queryLocalInterface3 == null
                                || !(queryLocalInterface3
                                        instanceof ISehBiometricsFaceClientCallback)) {
                            ISehBiometricsFaceClientCallback.Proxy proxy3 =
                                    new ISehBiometricsFaceClientCallback.Proxy();
                            proxy3.mRemote = readStrongBinder3;
                            try {
                                Iterator it3 = proxy3.interfaceChain().iterator();
                                while (true) {
                                    if (it3.hasNext()) {
                                        if (((String) it3.next())
                                                .equals(
                                                        "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFaceClientCallback")) {
                                            iSehBiometricsFaceClientCallback = proxy3;
                                        }
                                    }
                                }
                            } catch (RemoteException unused3) {
                            }
                        } else {
                            iSehBiometricsFaceClientCallback =
                                    (ISehBiometricsFaceClientCallback) queryLocalInterface3;
                        }
                    }
                    OptionalUint64 sehSetCallbackEx =
                            sehSetCallbackEx(iSehBiometricsFaceClientCallback);
                    hwParcel2.writeStatus(0);
                    sehSetCallbackEx.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
                case 36:
                    hwParcel.enforceInterface(
                            "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
                    int sehAuthenticateForIssuance =
                            sehAuthenticateForIssuance(
                                    hwParcel.readInt32(),
                                    hwParcel.readInt64(),
                                    hwParcel.readInt8Vector(),
                                    hwParcel.readBool(),
                                    hwParcel.readBool());
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
                            hwParcel.readNativeHandle();
                            hwParcel.readStringVector();
                            hwParcel2.writeStatus(0);
                            hwParcel2.send();
                            return;
                        case 256136003:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(
                                    "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList hashChain = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob2 = new HwBlob(16);
                            int size = hashChain.size();
                            hwBlob2.putInt32(8L, size);
                            hwBlob2.putBool(12L, false);
                            HwBlob hwBlob3 = new HwBlob(size * 32);
                            for (int i3 = 0; i3 < size; i3++) {
                                long j = i3 * 32;
                                byte[] bArr = (byte[]) hashChain.get(i3);
                                if (bArr == null || bArr.length != 32) {
                                    throw new IllegalArgumentException(
                                            "Array element is not of the expected length");
                                }
                                hwBlob3.putInt8Array(j, bArr);
                            }
                            hwBlob2.putBlob(0L, hwBlob3);
                            hwParcel2.writeBuffer(hwBlob2);
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
            if ("vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace".equals(str)) {
                return this;
            }
            return null;
        }

        @Override // vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFace
        public OptionalUint64 sehSetCallback(
                vendor.samsung.hardware.biometrics.face.V2_0.ISehBiometricsFaceClientCallback
                        iSehBiometricsFaceClientCallback) {
            return setCallback(iSehBiometricsFaceClientCallback);
        }

        @Override // vendor.samsung.hardware.biometrics.face.V3_0.ISehBiometricsFace
        public OptionalUint64 sehSetCallbackEx(
                ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback) {
            return setCallback(iSehBiometricsFaceClientCallback);
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {}

        public final String toString() {
            return "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace@Stub";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }
    }

    static ISehBiometricsFace getService() {
        IHwBinder service =
                HwBinder.getService(
                        "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace",
                        "default");
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface =
                service.queryLocalInterface(
                        "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace");
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
                                "vendor.samsung.hardware.biometrics.face@3.0::ISehBiometricsFace")) {
                    return proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

    int sehAuthenticateForIssuance(int i, long j, ArrayList arrayList, boolean z, boolean z2);

    OptionalUint64 sehSetCallbackEx(
            ISehBiometricsFaceClientCallback iSehBiometricsFaceClientCallback);
}
