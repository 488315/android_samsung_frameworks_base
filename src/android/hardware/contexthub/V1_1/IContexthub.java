package android.hardware.contexthub.V1_1;

import android.hardware.contexthub.V1_0.ContextHub;
import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.contexthub.V1_0.IContexthubCallback;
import android.hardware.contexthub.V1_0.NanoAppBinary;
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
public interface IContexthub extends android.hardware.contexthub.V1_0.IContexthub {
    public static final String kInterfaceName = "android.hardware.contexthub@1.1::IContexthub";

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void onSettingChanged(byte b, byte b2) throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IContexthub asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IContexthub)) {
            return (IContexthub) queryLocalInterface;
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

    static IContexthub castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IContexthub getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IContexthub getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IContexthub getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IContexthub getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IContexthub {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.contexthub@1.1::IContexthub]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public ArrayList<ContextHub> getHubs() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return ContextHub.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int registerCallback(int i, IContexthubCallback iContexthubCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStrongBinder(iContexthubCallback == null ? null : iContexthubCallback.asBinder());
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

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int sendMessageToHub(int i, ContextHubMsg contextHubMsg) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            contextHubMsg.writeToParcel(hwParcel);
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

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int loadNanoApp(int i, NanoAppBinary nanoAppBinary, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            nanoAppBinary.writeToParcel(hwParcel);
            hwParcel.writeInt32(i2);
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

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int unloadNanoApp(int i, long j, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i2);
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

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int enableNanoApp(int i, long j, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
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

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int disableNanoApp(int i, long j, int i2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt64(j);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_0.IContexthub
        public int queryApps(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.contexthub.V1_1.IContexthub
        public void onSettingChanged(byte b, byte b2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt8(b);
            hwParcel.writeInt8(b2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256398152, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                ArrayList<byte[]> arrayList = new ArrayList<>();
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IContexthub {
        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IContexthub.kInterfaceName, android.hardware.contexthub.V1_0.IContexthub.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IContexthub.kInterfaceName;
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-125, 81, -52, 1, -18, -44, MidiConstants.STATUS_PROGRAM_CHANGE, -76, 72, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -107, 114, -75, -57, -35, -3, 23, -121, 77, -114, -37, 81, -42, 118, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, 52, -127, 22, -4, -111, -35, 24}, new byte[]{MidiConstants.STATUS_PITCH_BEND, 66, 82, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -86, 75, 95, Byte.MAX_VALUE, -44, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -95, -101, -51, -83, -71, 60, 121, -95, MidiConstants.STATUS_CONTROL_CHANGE, 76, 9, -17, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -104, 19, -93, -88, -108, 16, 50, MidiConstants.STATUS_SONG_SELECT, -11}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.contexthub.V1_1.IContexthub, android.hardware.contexthub.V1_0.IContexthub, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IContexthub.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    ArrayList<ContextHub> hubs = getHubs();
                    hwParcel2.writeStatus(0);
                    ContextHub.writeVectorToParcel(hwParcel2, hubs);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int registerCallback = registerCallback(hwParcel.readInt32(), IContexthubCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(registerCallback);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int readInt32 = hwParcel.readInt32();
                    ContextHubMsg contextHubMsg = new ContextHubMsg();
                    contextHubMsg.readFromParcel(hwParcel);
                    int sendMessageToHub = sendMessageToHub(readInt32, contextHubMsg);
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sendMessageToHub);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int readInt322 = hwParcel.readInt32();
                    NanoAppBinary nanoAppBinary = new NanoAppBinary();
                    nanoAppBinary.readFromParcel(hwParcel);
                    int loadNanoApp = loadNanoApp(readInt322, nanoAppBinary, hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(loadNanoApp);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int unloadNanoApp = unloadNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(unloadNanoApp);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int enableNanoApp = enableNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(enableNanoApp);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int disableNanoApp = disableNanoApp(hwParcel.readInt32(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(disableNanoApp);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.contexthub.V1_0.IContexthub.kInterfaceName);
                    int queryApps = queryApps(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(queryApps);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(IContexthub.kInterfaceName);
                    onSettingChanged(hwParcel.readInt8(), hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                default:
                    switch (i) {
                        case 256067662:
                            hwParcel.enforceInterface(IBase.kInterfaceName);
                            ArrayList<String> interfaceChain = interfaceChain();
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
