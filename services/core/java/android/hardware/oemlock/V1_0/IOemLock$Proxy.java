package android.hardware.oemlock.V1_0;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
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

import com.android.server.oemlock.VendorLockHidl$$ExternalSyntheticLambda0;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IOemLock$Proxy implements IBase {
    public IHwBinder mRemote;

    public static IOemLock$Proxy getService() {
        IHwBinder service =
                HwBinder.getService("android.hardware.oemlock@1.0::IOemLock", "default", true);
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface =
                service.queryLocalInterface("android.hardware.oemlock@1.0::IOemLock");
        if (queryLocalInterface != null && (queryLocalInterface instanceof IOemLock$Proxy)) {
            return (IOemLock$Proxy) queryLocalInterface;
        }
        IOemLock$Proxy iOemLock$Proxy = new IOemLock$Proxy();
        iOemLock$Proxy.mRemote = service;
        try {
            Iterator it = iOemLock$Proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next()).equals("android.hardware.oemlock@1.0::IOemLock")) {
                    return iOemLock$Proxy;
                }
            }
            return null;
        } catch (RemoteException unused) {
            return null;
        }
    }

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

    public final void getName(
            VendorLockHidl$$ExternalSyntheticLambda0 vendorLockHidl$$ExternalSyntheticLambda0) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.oemlock@1.0::IOemLock");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(1, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            String readString = hwParcel.readString();
            vendorLockHidl$$ExternalSyntheticLambda0.f$0[0] = Integer.valueOf(readInt32);
            ((String[]) vendorLockHidl$$ExternalSyntheticLambda0.f$1)[0] = readString;
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

    public final void isOemUnlockAllowedByCarrier(
            VendorLockHidl$$ExternalSyntheticLambda0 vendorLockHidl$$ExternalSyntheticLambda0) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.oemlock@1.0::IOemLock");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(3, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            boolean readBool = hwParcel.readBool();
            vendorLockHidl$$ExternalSyntheticLambda0.f$0[0] = Integer.valueOf(readInt32);
            ((Boolean[]) vendorLockHidl$$ExternalSyntheticLambda0.f$1)[0] =
                    Boolean.valueOf(readBool);
        } finally {
            hwParcel.release();
        }
    }

    public final void isOemUnlockAllowedByDevice(
            VendorLockHidl$$ExternalSyntheticLambda0 vendorLockHidl$$ExternalSyntheticLambda0) {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.oemlock@1.0::IOemLock");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(5, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            int readInt32 = hwParcel.readInt32();
            boolean readBool = hwParcel.readBool();
            vendorLockHidl$$ExternalSyntheticLambda0.f$0[0] = Integer.valueOf(readInt32);
            ((Boolean[]) vendorLockHidl$$ExternalSyntheticLambda0.f$1)[0] =
                    Boolean.valueOf(readBool);
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

    public final int setOemUnlockAllowedByCarrier(ArrayList arrayList, boolean z) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.oemlock@1.0::IOemLock");
        hwParcel.writeBool(z);
        hwParcel.writeInt8Vector(arrayList);
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

    public final int setOemUnlockAllowedByDevice(boolean z) {
        HwParcel hwParcel = new HwParcel();
        hwParcel.writeInterfaceToken("android.hardware.oemlock@1.0::IOemLock");
        hwParcel.writeBool(z);
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

    public final String toString() {
        try {
            return interfaceDescriptor() + "@Proxy";
        } catch (RemoteException unused) {
            return "[class or subclass of android.hardware.oemlock@1.0::IOemLock]@Proxy";
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return this.mRemote.unlinkToDeath(deathRecipient);
    }
}
