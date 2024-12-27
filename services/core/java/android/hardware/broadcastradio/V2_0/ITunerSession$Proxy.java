package android.hardware.broadcastradio.V2_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.util.MutableBoolean;
import android.util.MutableInt;

import com.android.server.broadcastradio.hal2.TunerSession$$ExternalSyntheticLambda0;

import java.util.ArrayList;

public final class ITunerSession$Proxy implements IBase {
    public IHwBinder mRemote;

    @Override // android.hidl.base.V1_0.IBase
    public final IHwBinder asBinder() {
        return this.mRemote;
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

    public final boolean equals(Object obj) {
        return HidlSupport.interfacesEqual(this, obj);
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

    public final void isConfigFlagSet(
            int i, TunerSession$$ExternalSyntheticLambda0 tunerSession$$ExternalSyntheticLambda0) {
        HwParcel m =
                ITunerSession$Proxy$$ExternalSyntheticOutline0.m(
                        i, "android.hardware.broadcastradio@2.0::ITunerSession");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(7, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            boolean readBool = hwParcel.readBool();
            MutableInt mutableInt = (MutableInt) tunerSession$$ExternalSyntheticLambda0.f$0;
            MutableBoolean mutableBoolean =
                    (MutableBoolean) tunerSession$$ExternalSyntheticLambda0.f$1;
            mutableInt.value = readInt32;
            mutableBoolean.value = readBool;
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

    public final int scan(boolean z, boolean z2) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
        hwParcel.writeBool(z);
        hwParcel.writeBool(z2);
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

    public final int setConfigFlag(int i, boolean z) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
        hwParcel.writeInt32(i);
        hwParcel.writeBool(z);
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

    public final int startProgramListUpdates(ProgramFilter programFilter) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.broadcastradio@2.0::ITunerSession");
        HwBlob hwBlob = new HwBlob(40);
        int size = programFilter.identifierTypes.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, ((Integer) programFilter.identifierTypes.get(i)).intValue());
        }
        hwBlob.putBlob(0L, hwBlob2);
        int size2 = programFilter.identifiers.size();
        hwBlob.putInt32(24L, size2);
        hwBlob.putBool(28L, false);
        HwBlob hwBlob3 = new HwBlob(size2 * 16);
        for (int i2 = 0; i2 < size2; i2++) {
            ProgramIdentifier programIdentifier =
                    (ProgramIdentifier) programFilter.identifiers.get(i2);
            long j = i2 * 16;
            hwBlob3.putInt32(j, programIdentifier.type);
            hwBlob3.putInt64(j + 8, programIdentifier.value);
        }
        hwBlob.putBlob(16L, hwBlob3);
        hwBlob.putBool(32L, programFilter.includeCategories);
        hwBlob.putBool(33L, programFilter.excludeModifications);
        m.writeBuffer(hwBlob);
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

    public final int step(boolean z) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.broadcastradio@2.0::ITunerSession");
        hwParcel.writeBool(z);
        HwParcel hwParcel2 = new HwParcel();
        try {
            this.mRemote.transact(3, hwParcel, hwParcel2, 0);
            hwParcel2.verifySuccess();
            hwParcel.releaseTemporaryStorage();
            return hwParcel2.readInt32();
        } finally {
            hwParcel2.release();
        }
    }

    public final String toString() {
        try {
            return interfaceDescriptor() + "@Proxy";
        } catch (RemoteException unused) {
            return "[class or subclass of"
                       + " android.hardware.broadcastradio@2.0::ITunerSession]@Proxy";
        }
    }

    public final int tune(ProgramSelector programSelector) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.broadcastradio@2.0::ITunerSession");
        HwBlob hwBlob = new HwBlob(32);
        ProgramIdentifier programIdentifier = programSelector.primaryId;
        hwBlob.putInt32(0L, programIdentifier.type);
        hwBlob.putInt64(8L, programIdentifier.value);
        int size = programSelector.secondaryIds.size();
        hwBlob.putInt32(24L, size);
        hwBlob.putBool(28L, false);
        HwBlob hwBlob2 = new HwBlob(size * 16);
        for (int i = 0; i < size; i++) {
            ProgramIdentifier programIdentifier2 =
                    (ProgramIdentifier) programSelector.secondaryIds.get(i);
            long j = i * 16;
            hwBlob2.putInt32(j, programIdentifier2.type);
            hwBlob2.putInt64(j + 8, programIdentifier2.value);
        }
        hwBlob.putBlob(16L, hwBlob2);
        m.writeBuffer(hwBlob);
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(1, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            return hwParcel.readInt32();
        } finally {
            hwParcel.release();
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return this.mRemote.unlinkToDeath(deathRecipient);
    }
}
