package android.hardware.cas.V1_2;

import android.hardware.cas.V1_0.HidlCasPluginDescriptor;
import android.hardware.cas.V1_0.IDescramblerBase;
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
public interface IMediaCasService extends android.hardware.cas.V1_1.IMediaCasService {
    public static final String kInterfaceName = "android.hardware.cas@1.2::IMediaCasService";

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IMediaCasService asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IMediaCasService)) {
            return (IMediaCasService) iHwInterfaceQueryLocalInterface;
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

    static IMediaCasService castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IMediaCasService getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IMediaCasService getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IMediaCasService getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IMediaCasService getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IMediaCasService {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.cas@1.2::IMediaCasService]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.cas.V1_0.IMediaCasService
        public ArrayList<HidlCasPluginDescriptor> enumeratePlugins() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return HidlCasPluginDescriptor.readVectorFromParcel(hwParcel2);
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_0.IMediaCasService
        public boolean isSystemIdSupported(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.cas.V1_0.IMediaCasService
        public android.hardware.cas.V1_0.ICas createPlugin(int i, android.hardware.cas.V1_0.ICasListener iCasListener) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStrongBinder(iCasListener == null ? null : iCasListener.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.cas.V1_0.ICas.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_0.IMediaCasService
        public boolean isDescramblerSupported(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readBool();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_0.IMediaCasService
        public IDescramblerBase createDescrambler(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IDescramblerBase.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_1.IMediaCasService
        public android.hardware.cas.V1_1.ICas createPluginExt(int i, android.hardware.cas.V1_1.ICasListener iCasListener) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_1.IMediaCasService.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeStrongBinder(iCasListener == null ? null : iCasListener.asBinder());
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return android.hardware.cas.V1_1.ICas.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IMediaCasService {
        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IMediaCasService.kInterfaceName, android.hardware.cas.V1_1.IMediaCasService.kInterfaceName, android.hardware.cas.V1_0.IMediaCasService.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IMediaCasService.kInterfaceName;
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{MidiConstants.STATUS_MIDI_TIME_CODE, -122, -107, -35, 54, -18, 32, 86, 64, -72, 50, 106, 23, 69, 56, 88, -89, -76, 89, 102, 83, -86, -90, -17, 0, 22, MidiConstants.STATUS_CONTROL_CHANGE, -82, MidiConstants.STATUS_MIDI_TIME_CODE, -67, 77, -84}, new byte[]{-33, -6, -51, -66, 11, -49, -124, 67, 1, 61, -27, -67, -59, 106, -125, 71, -102, -39, 121, -44, -111, -98, -47, 90, 85, -123, 83, -97, 70, 9, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEIN, 7}, new byte[]{-122, -70, -100, 3, -105, -117, 121, -89, 66, -23, MidiConstants.STATUS_NOTE_ON, 66, 11, -59, -50, MidiConstants.STATUS_CHANNEL_PRESSURE, 103, 61, 37, -87, 57, -8, 37, 114, -103, 107, -17, -110, 98, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, 32, 20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.cas.V1_2.IMediaCasService, android.hardware.cas.V1_1.IMediaCasService, android.hardware.cas.V1_0.IMediaCasService, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IMediaCasService.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
                    ArrayList<HidlCasPluginDescriptor> arrayListEnumeratePlugins = enumeratePlugins();
                    hwParcel2.writeStatus(0);
                    HidlCasPluginDescriptor.writeVectorToParcel(hwParcel2, arrayListEnumeratePlugins);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
                    boolean zIsSystemIdSupported = isSystemIdSupported(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zIsSystemIdSupported);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
                    android.hardware.cas.V1_0.ICas iCasCreatePlugin = createPlugin(hwParcel.readInt32(), android.hardware.cas.V1_0.ICasListener.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iCasCreatePlugin != null ? iCasCreatePlugin.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
                    boolean zIsDescramblerSupported = isDescramblerSupported(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zIsDescramblerSupported);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.IMediaCasService.kInterfaceName);
                    IDescramblerBase iDescramblerBaseCreateDescrambler = createDescrambler(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iDescramblerBaseCreateDescrambler != null ? iDescramblerBaseCreateDescrambler.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.cas.V1_1.IMediaCasService.kInterfaceName);
                    android.hardware.cas.V1_1.ICas iCasCreatePluginExt = createPluginExt(hwParcel.readInt32(), android.hardware.cas.V1_1.ICasListener.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(iCasCreatePluginExt != null ? iCasCreatePluginExt.asBinder() : null);
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
