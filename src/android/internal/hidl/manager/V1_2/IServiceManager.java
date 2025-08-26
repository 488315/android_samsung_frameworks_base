package android.internal.hidl.manager.V1_2;

import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
import android.internal.hidl.manager.V1_0.IServiceManager;
import android.internal.hidl.manager.V1_0.IServiceNotification;
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
public interface IServiceManager extends android.internal.hidl.manager.V1_1.IServiceManager {
    public static final String kInterfaceName = "android.hidl.manager@1.2::IServiceManager";

    boolean addWithChain(String str, IBase iBase, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    ArrayList<String> listManifestByInterface(String str) throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    boolean registerClientCallback(String str, String str2, IBase iBase, IClientCallback iClientCallback) throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    boolean tryUnregister(String str, String str2, IBase iBase) throws RemoteException;

    @Override // android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    boolean unregisterClientCallback(IBase iBase, IClientCallback iClientCallback) throws RemoteException;

    static IServiceManager asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IServiceManager)) {
            return (IServiceManager) iHwInterfaceQueryLocalInterface;
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

    static IServiceManager castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IServiceManager getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IServiceManager getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IServiceManager getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IServiceManager getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IServiceManager {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hidl.manager@1.2::IServiceManager]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public IBase get(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IBase.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean add(String str, IBase iBase) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public byte getTransport(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt8();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<String> list() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<String> listByInterface(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public boolean registerForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iServiceNotification == null ? null : iServiceNotification.asBinder());
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

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public ArrayList<IServiceManager.InstanceDebugInfo> debugDump() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IServiceManager.InstanceDebugInfo.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_0.IServiceManager
        public void registerPassthroughClient(String str, String str2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_1.IServiceManager
        public boolean unregisterForNotifications(String str, String str2, IServiceNotification iServiceNotification) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iServiceNotification == null ? null : iServiceNotification.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean registerClientCallback(String str, String str2, IBase iBase, IClientCallback iClientCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStrongBinder(iClientCallback != null ? iClientCallback.asBinder() : null);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean unregisterClientCallback(IBase iBase, IClientCallback iClientCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStrongBinder(iClientCallback != null ? iClientCallback.asBinder() : null);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean addWithChain(String str, IBase iBase, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            hwParcel.writeStringVector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public ArrayList<String> listManifestByInterface(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readStringVector();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager
        public boolean tryUnregister(String str, String str2, IBase iBase) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IServiceManager.kInterfaceName);
            hwParcel.writeString(str);
            hwParcel.writeString(str2);
            hwParcel.writeStrongBinder(iBase == null ? null : iBase.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IServiceManager {
        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IServiceManager.kInterfaceName, android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName, android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IServiceManager.kInterfaceName;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{111, 58, -118, 63, -44, -65, -67, 2, -28, -26, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 115, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -10, 22, -1, 105, 67, 74, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -40, 60, -38, 51, 115, 3, -83, -58, -41, 20, -33}, new byte[]{11, -108, -36, -121, 111, 116, -98, -46, 74, -104, -10, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 65, -44, 106, -41, 90, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 81, 17, 99, MidiConstants.STATUS_MIDI_TIME_CODE, -106, -118, 8, 66, 19, -93, 60, 104, 78, -10}, new byte[]{-123, 57, 79, -118, 13, 21, -25, -5, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, -28, 92, 82, -47, -5, -117, -113, -45, -63, 60, 51, 62, 99, -57, -116, 74, -95, -1, -122, -124, 12, -10, -36}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.internal.hidl.manager.V1_2.IServiceManager, android.internal.hidl.manager.V1_1.IServiceManager, android.internal.hidl.manager.V1_0.IServiceManager, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IServiceManager.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    IBase iBase = get(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iBase == null ? null : iBase.asBinder());
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    boolean zAdd = add(hwParcel.readString(), IBase.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zAdd);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    byte transport = getTransport(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt8(transport);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    ArrayList<String> list = list();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(list);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    ArrayList<String> arrayListListByInterface = listByInterface(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(arrayListListByInterface);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    boolean zRegisterForNotifications = registerForNotifications(hwParcel.readString(), hwParcel.readString(), IServiceNotification.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zRegisterForNotifications);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    ArrayList<IServiceManager.InstanceDebugInfo> arrayListDebugDump = debugDump();
                    hwParcel2.writeStatus(0);
                    IServiceManager.InstanceDebugInfo.writeVectorToParcel(hwParcel2, arrayListDebugDump);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_0.IServiceManager.kInterfaceName);
                    registerPassthroughClient(hwParcel.readString(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.internal.hidl.manager.V1_1.IServiceManager.kInterfaceName);
                    boolean zUnregisterForNotifications = unregisterForNotifications(hwParcel.readString(), hwParcel.readString(), IServiceNotification.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zUnregisterForNotifications);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean zRegisterClientCallback = registerClientCallback(hwParcel.readString(), hwParcel.readString(), IBase.asInterface(hwParcel.readStrongBinder()), IClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zRegisterClientCallback);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean zUnregisterClientCallback = unregisterClientCallback(IBase.asInterface(hwParcel.readStrongBinder()), IClientCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zUnregisterClientCallback);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean zAddWithChain = addWithChain(hwParcel.readString(), IBase.asInterface(hwParcel.readStrongBinder()), hwParcel.readStringVector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zAddWithChain);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    ArrayList<String> arrayListListManifestByInterface = listManifestByInterface(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStringVector(arrayListListManifestByInterface);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(IServiceManager.kInterfaceName);
                    boolean zTryUnregister = tryUnregister(hwParcel.readString(), hwParcel.readString(), IBase.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zTryUnregister);
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
