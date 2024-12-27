package android.hardware.configstore.V1_1;

import android.hardware.authsecret.V1_0.IAuthSecret$Proxy$$ExternalSyntheticOutline0;
import android.hardware.configstore.V1_0.OptionalBool;
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
/* loaded from: classes.dex */
public final class ISurfaceFlingerConfigs$Proxy implements IBase {
    public IHwBinder mRemote;

    public static ISurfaceFlingerConfigs$Proxy getService() {
        IHwBinder service =
                HwBinder.getService(
                        "android.hardware.configstore@1.1::ISurfaceFlingerConfigs", "default");
        if (service == null) {
            return null;
        }
        IHwInterface queryLocalInterface =
                service.queryLocalInterface(
                        "android.hardware.configstore@1.1::ISurfaceFlingerConfigs");
        if (queryLocalInterface != null
                && (queryLocalInterface instanceof ISurfaceFlingerConfigs$Proxy)) {
            return (ISurfaceFlingerConfigs$Proxy) queryLocalInterface;
        }
        ISurfaceFlingerConfigs$Proxy iSurfaceFlingerConfigs$Proxy =
                new ISurfaceFlingerConfigs$Proxy();
        iSurfaceFlingerConfigs$Proxy.mRemote = service;
        try {
            Iterator it = iSurfaceFlingerConfigs$Proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (((String) it.next())
                        .equals("android.hardware.configstore@1.1::ISurfaceFlingerConfigs")) {
                    return iSurfaceFlingerConfigs$Proxy;
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

    public final OptionalBool hasHDRDisplay() {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(5, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            OptionalBool optionalBool = new OptionalBool();
            HwBlob readBuffer = hwParcel.readBuffer(2L);
            optionalBool.specified = readBuffer.getBool(0L);
            optionalBool.value = readBuffer.getBool(1L);
            return optionalBool;
        } finally {
            hwParcel.release();
        }
    }

    public final OptionalBool hasWideColorDisplay() {
        HwParcel m =
                IAuthSecret$Proxy$$ExternalSyntheticOutline0.m(
                        "android.hardware.configstore@1.0::ISurfaceFlingerConfigs");
        HwParcel hwParcel = new HwParcel();
        try {
            this.mRemote.transact(4, m, hwParcel, 0);
            hwParcel.verifySuccess();
            m.releaseTemporaryStorage();
            OptionalBool optionalBool = new OptionalBool();
            HwBlob readBuffer = hwParcel.readBuffer(2L);
            optionalBool.specified = readBuffer.getBool(0L);
            optionalBool.value = readBuffer.getBool(1L);
            return optionalBool;
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
                       + " android.hardware.configstore@1.1::ISurfaceFlingerConfigs]@Proxy";
        }
    }

    @Override // android.hidl.base.V1_0.IBase
    public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
        return this.mRemote.unlinkToDeath(deathRecipient);
    }
}
