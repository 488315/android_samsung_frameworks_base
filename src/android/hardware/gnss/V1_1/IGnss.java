package android.hardware.gnss.V1_1;

import android.hardware.gnss.V1_0.GnssLocation;
import android.hardware.gnss.V1_0.IAGnss;
import android.hardware.gnss.V1_0.IAGnssRil;
import android.hardware.gnss.V1_0.IGnssBatching;
import android.hardware.gnss.V1_0.IGnssDebug;
import android.hardware.gnss.V1_0.IGnssGeofencing;
import android.hardware.gnss.V1_0.IGnssNavigationMessage;
import android.hardware.gnss.V1_0.IGnssNi;
import android.hardware.gnss.V1_0.IGnssXtra;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IGnss extends android.hardware.gnss.V1_0.IGnss {
    public static final String kInterfaceName = "android.hardware.gnss@1.1::IGnss";

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    IGnssConfiguration getExtensionGnssConfiguration_1_1() throws RemoteException;

    IGnssMeasurement getExtensionGnssMeasurement_1_1() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    boolean injectBestLocation(GnssLocation gnssLocation) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    boolean setCallback_1_1(IGnssCallback iGnssCallback) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    boolean setPositionMode_1_1(byte b, int i, int i2, int i3, int i4, boolean z) throws RemoteException;

    @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnss asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IGnss)) {
            return (IGnss) iHwInterfaceQueryLocalInterface;
        }
        Proxy proxy = new Proxy(iHwBinder);
        try {
            Iterator<String> it = proxy.interfaceChain().iterator();
            while (it.hasNext()) {
                if (it.next().equals(kInterfaceName)) {
                    return proxy;
                }
            }
        } catch (RemoteException unused) {
        }
        return null;
    }

    static IGnss castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnss getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnss getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnss getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnss getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IGnss {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.1::IGnss]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean setCallback(android.hardware.gnss.V1_0.IGnssCallback iGnssCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssCallback == null ? null : iGnssCallback.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean start() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean stop() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public void cleanup() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean injectTime(long j, long j2, int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeInt64(j2);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean injectLocation(double d, double d2, float f) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeDouble(d);
            hwParcel.writeDouble(d2);
            hwParcel.writeFloat(f);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public void deleteAidingData(short s) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt16(s);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean setPositionMode(byte b, int i, int i2, int i3, int i4) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IAGnssRil getExtensionAGnssRil() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IAGnssRil.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssGeofencing getExtensionGnssGeofencing() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssGeofencing.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IAGnss getExtensionAGnss() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IAGnss.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssNi getExtensionGnssNi() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssNi.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssMeasurement getExtensionGnssMeasurement() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssNavigationMessage getExtensionGnssNavigationMessage() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssNavigationMessage.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssXtra getExtensionXtra() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssXtra.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public android.hardware.gnss.V1_0.IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.gnss.V1_0.IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssDebug getExtensionGnssDebug() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssDebug.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssBatching getExtensionGnssBatching() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssBatching.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean setCallback_1_1(IGnssCallback iGnssCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssCallback == null ? null : iGnssCallback.asBinder());
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

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean setPositionMode_1_1(byte b, int i, int i2, int i3, int i4, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt32(i3);
            hwParcel.writeInt32(i4);
            hwParcel.writeBool(z);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public IGnssConfiguration getExtensionGnssConfiguration_1_1() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public IGnssMeasurement getExtensionGnssMeasurement_1_1() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss
        public boolean injectBestLocation(GnssLocation gnssLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public ArrayList<String> interfaceChain() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public String interfaceDescriptor() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
                HwBlob buffer = hwParcel2.readBuffer(16L);
                int int32 = buffer.getInt32(8L);
                HwBlob embeddedBuffer = hwParcel2.readEmbeddedBuffer(int32 * 32, buffer.handle(), 0L, true);
                arrayList.clear();
                for (int i = 0; i < int32; i++) {
                    byte[] bArr = new byte[32];
                    embeddedBuffer.copyToInt8Array(i * 32, bArr, 32);
                    arrayList.add(bArr);
                }
                return arrayList;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void setHALInstrumentation() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void ping() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public DebugInfo getDebugInfo() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void notifySyspropsChanged() throws RemoteException {
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

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnss {
        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnss.kInterfaceName, android.hardware.gnss.V1_0.IGnss.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnss.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-75, MidiConstants.STATUS_MIDI_TIME_CODE, -12, -63, -67, 109, -25, 26, -114, 113, -41, 15, 87, -51, -85, MidiConstants.STATUS_NOTE_ON, 74, MidiConstants.STATUS_PROGRAM_CHANGE, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -95, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, 61, -18, 62, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, 115, 119, 10, 69, -125, -68, -62}, new byte[]{-19, -26, -105, 16, -61, -87, 92, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -66, -127, -114, 108, -117, -73, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 120, 22, -126, 63, -84, -27, -4, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -63, 119, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -78, 111, 65, -39, 77, 101}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_1.IGnss, android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnss.kInterfaceName.equals(str)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String str) throws RemoteException {
            registerService(str);
        }

        public String toString() {
            return interfaceDescriptor() + "@Stub";
        }

        @Override // android.os.HwBinder
        public void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean callback = setCallback(android.hardware.gnss.V1_0.IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean zStart = start();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zStart);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean zStop = stop();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zStop);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    cleanup();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean zInjectTime = injectTime(hwParcel.readInt64(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zInjectTime);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean zInjectLocation = injectLocation(hwParcel.readDouble(), hwParcel.readDouble(), hwParcel.readFloat());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zInjectLocation);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    deleteAidingData(hwParcel.readInt16());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    boolean positionMode = setPositionMode(hwParcel.readInt8(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(positionMode);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IAGnssRil extensionAGnssRil = getExtensionAGnssRil();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnssRil != null ? extensionAGnssRil.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssGeofencing extensionGnssGeofencing = getExtensionGnssGeofencing();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssGeofencing != null ? extensionGnssGeofencing.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IAGnss extensionAGnss = getExtensionAGnss();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnss != null ? extensionAGnss.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssNi extensionGnssNi = getExtensionGnssNi();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNi != null ? extensionGnssNi.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssMeasurement extensionGnssMeasurement = getExtensionGnssMeasurement();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement != null ? extensionGnssMeasurement.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssNavigationMessage extensionGnssNavigationMessage = getExtensionGnssNavigationMessage();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNavigationMessage != null ? extensionGnssNavigationMessage.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssXtra extensionXtra = getExtensionXtra();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionXtra != null ? extensionXtra.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    android.hardware.gnss.V1_0.IGnssConfiguration extensionGnssConfiguration = getExtensionGnssConfiguration();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration != null ? extensionGnssConfiguration.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssDebug extensionGnssDebug = getExtensionGnssDebug();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssDebug != null ? extensionGnssDebug.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 18:
                    hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnss.kInterfaceName);
                    IGnssBatching extensionGnssBatching = getExtensionGnssBatching();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssBatching != null ? extensionGnssBatching.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 19:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean callback_1_1 = setCallback_1_1(IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback_1_1);
                    hwParcel2.send();
                    return;
                case 20:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean positionMode_1_1 = setPositionMode_1_1(hwParcel.readInt8(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readBool());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(positionMode_1_1);
                    hwParcel2.send();
                    return;
                case 21:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssConfiguration extensionGnssConfiguration_1_1 = getExtensionGnssConfiguration_1_1();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration_1_1 != null ? extensionGnssConfiguration_1_1.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 22:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssMeasurement extensionGnssMeasurement_1_1 = getExtensionGnssMeasurement_1_1();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement_1_1 != null ? extensionGnssMeasurement_1_1.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 23:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    GnssLocation gnssLocation = new GnssLocation();
                    gnssLocation.readFromParcel(hwParcel);
                    boolean zInjectBestLocation = injectBestLocation(gnssLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zInjectBestLocation);
                    hwParcel2.send();
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> arrayListInterfaceChain = interfaceChain();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeStringVector(arrayListInterfaceChain);
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
                            String strInterfaceDescriptor = interfaceDescriptor();
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeString(strInterfaceDescriptor);
                            hwParcel2.send();
                            return;
                        case 256398152:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<byte[]> hashChain = getHashChain();
                            hwParcel2.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            int size = hashChain.size();
                            hwBlob.putInt32(8L, size);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(size * 32);
                            for (int i3 = 0; i3 < size; i3++) {
                                long j = i3 * 32;
                                byte[] bArr = hashChain.get(i3);
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
