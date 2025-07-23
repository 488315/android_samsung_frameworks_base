package android.hardware.cas.V1_2;

import android.hardware.cas.V1_0.ICas;
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
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface ICas extends android.hardware.cas.V1_1.ICas {
    public static final String kInterfaceName = "android.hardware.cas@1.2::ICas";

    @FunctionalInterface
    public interface openSession_1_2Callback {
        void onValues(int i, ArrayList<Byte> arrayList);
    }

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void openSession_1_2(int i, int i2, openSession_1_2Callback opensession_1_2callback) throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static ICas asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof ICas)) {
            return (ICas) queryLocalInterface;
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

    static ICas castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static ICas getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static ICas getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static ICas getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static ICas getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements ICas {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.cas@1.2::ICas]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.cas.V1_0.ICas
        public int setPrivateData(ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return hwParcel2.readInt32();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_0.ICas
        public void openSession(ICas.openSessionCallback opensessioncallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                opensessioncallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt8Vector());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_0.ICas
        public int closeSession(ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int setSessionPrivateData(ArrayList<Byte> arrayList, ArrayList<Byte> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt8Vector(arrayList2);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int processEcm(ArrayList<Byte> arrayList, ArrayList<Byte> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt8Vector(arrayList2);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int processEmm(ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int sendEvent(int i, int i2, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt8Vector(arrayList);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int provision(String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeString(str);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int refreshEntitlements(int i, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8Vector(arrayList);
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

        @Override // android.hardware.cas.V1_0.ICas
        public int release() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_0.ICas.kInterfaceName);
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

        @Override // android.hardware.cas.V1_1.ICas
        public int sendSessionEvent(ArrayList<Byte> arrayList, int i, int i2, ArrayList<Byte> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.cas.V1_1.ICas.kInterfaceName);
            hwParcel.writeInt8Vector(arrayList);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            hwParcel.writeInt8Vector(arrayList2);
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

        @Override // android.hardware.cas.V1_2.ICas
        public void openSession_1_2(int i, int i2, openSession_1_2Callback opensession_1_2callback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ICas.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt32(i2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                opensession_1_2callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt8Vector());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements ICas {
        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(ICas.kInterfaceName, android.hardware.cas.V1_1.ICas.kInterfaceName, android.hardware.cas.V1_0.ICas.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return ICas.kInterfaceName;
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-63, -86, 80, -115, 0, -74, 110, -43, -2, -17, -22, 57, -113, -43, -19, MidiConstants.STATUS_SONG_POSITION, -113, -90, 81, -84, -119, 119, 58, -38, -41, -33, -38, 78, 10, 115, -87, 82}, new byte[]{68, -56, -119, 84, -77, -62, 1, -78, 111, 100, -4, -37, 111, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, Byte.MIN_VALUE, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -85, 58, -82, -122, 74, -98, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -57, 14, -118, 116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 74, -23, -42, -86}, new byte[]{14, 101, 107, -95, -70, -63, 20, SprAttributeBase.TYPE_ANIMATOR_SET, -95, SprAttributeBase.TYPE_SHADOW, -106, -17, 117, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, 105, -46, 75, 0, 13, -126, 14, -11, 101, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, 1, 80, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, -7, 115, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, 84, -62}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.cas.V1_2.ICas, android.hardware.cas.V1_1.ICas, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (ICas.kInterfaceName.equals(str)) {
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
        public void onTransact(int i, HwParcel hwParcel, final HwParcel hwParcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int privateData = setPrivateData(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(privateData);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    openSession(new ICas.openSessionCallback(this) { // from class: android.hardware.cas.V1_2.ICas.Stub.1
                        @Override // android.hardware.cas.V1_0.ICas.openSessionCallback
                        public void onValues(int i3, ArrayList<Byte> arrayList) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt8Vector(arrayList);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int closeSession = closeSession(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(closeSession);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int sessionPrivateData = setSessionPrivateData(hwParcel.readInt8Vector(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sessionPrivateData);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int processEcm = processEcm(hwParcel.readInt8Vector(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(processEcm);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int processEmm = processEmm(hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(processEmm);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int sendEvent = sendEvent(hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sendEvent);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int provision = provision(hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(provision);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int refreshEntitlements = refreshEntitlements(hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(refreshEntitlements);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(android.hardware.cas.V1_0.ICas.kInterfaceName);
                    int release = release();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(release);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(android.hardware.cas.V1_1.ICas.kInterfaceName);
                    int sendSessionEvent = sendSessionEvent(hwParcel.readInt8Vector(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt8Vector());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(sendSessionEvent);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(ICas.kInterfaceName);
                    openSession_1_2(hwParcel.readInt32(), hwParcel.readInt32(), new openSession_1_2Callback(this) { // from class: android.hardware.cas.V1_2.ICas.Stub.2
                        @Override // android.hardware.cas.V1_2.ICas.openSession_1_2Callback
                        public void onValues(int i3, ArrayList<Byte> arrayList) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt8Vector(arrayList);
                            hwParcel2.send();
                        }
                    });
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
