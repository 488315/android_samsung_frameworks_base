package android.hardware.gnss.V2_0;

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
public interface IGnssMeasurement extends android.hardware.gnss.V1_1.IGnssMeasurement {
    public static final String kInterfaceName = "android.hardware.gnss@2.0::IGnssMeasurement";

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    int setCallback_2_0(IGnssMeasurementCallback iGnssMeasurementCallback, boolean z) throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssMeasurement asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssMeasurement)) {
            return (IGnssMeasurement) queryLocalInterface;
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

    static IGnssMeasurement castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssMeasurement getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssMeasurement getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssMeasurement getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssMeasurement getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IGnssMeasurement {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@2.0::IGnssMeasurement]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurement
        public int setCallback(android.hardware.gnss.V1_0.IGnssMeasurementCallback iGnssMeasurementCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssMeasurement.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssMeasurementCallback == null ? null : iGnssMeasurementCallback.asBinder());
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurement
        public void close() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssMeasurement.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_1.IGnssMeasurement
        public int setCallback_1_1(android.hardware.gnss.V1_1.IGnssMeasurementCallback iGnssMeasurementCallback, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssMeasurement.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssMeasurementCallback == null ? null : iGnssMeasurementCallback.asBinder());
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement
        public int setCallback_2_0(IGnssMeasurementCallback iGnssMeasurementCallback, boolean z) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssMeasurement.kInterfaceName);
            hwParcel.writeStrongBinder(iGnssMeasurementCallback == null ? null : iGnssMeasurementCallback.asBinder());
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssMeasurement {
        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssMeasurement.kInterfaceName, android.hardware.gnss.V1_1.IGnssMeasurement.kInterfaceName, android.hardware.gnss.V1_0.IGnssMeasurement.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssMeasurement.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-58, 119, 89, -11, -42, 56, 125, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, 59, 102, 114, -111, Byte.MIN_VALUE, MidiConstants.STATUS_CHANNEL_PRESSURE, 54, MidiConstants.STATUS_NOTE_ON, -24, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -16, -74, -72, -44, -31, 60, -30, -1, 66, -45, 27, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 64, 101}, new byte[]{26, 7, -47, 56, 62, -124, 124, 61, -21, 105, 110, -57, -94, -55, -29, 59, -106, -125, 119, 41, 69, 102, 4, 72, MidiConstants.STATUS_POLYPHONIC_AFTERTOUCH, 16, -79, Byte.MIN_VALUE, 99, -38, 103, -92}, new byte[]{-98, -88, -104, 123, -79, 8, -100, -116, 93, 123, 103, -122, 101, 117, -72, 102, -17, 81, SprAttributeBase.TYPE_DURATION, 69, 2, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEOUT, -98, -4, -61, 124, 99, 82, -68, MidiConstants.STATUS_PITCH_BEND, 114, -93}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurement, android.hardware.gnss.V1_1.IGnssMeasurement, android.hardware.gnss.V1_0.IGnssMeasurement, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssMeasurement.kInterfaceName.equals(str)) {
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
            if (i == 1) {
                hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssMeasurement.kInterfaceName);
                int callback = setCallback(android.hardware.gnss.V1_0.IGnssMeasurementCallback.asInterface(hwParcel.readStrongBinder()));
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(callback);
                hwParcel2.send();
                return;
            }
            if (i == 2) {
                hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssMeasurement.kInterfaceName);
                close();
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 3) {
                hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssMeasurement.kInterfaceName);
                int callback_1_1 = setCallback_1_1(android.hardware.gnss.V1_1.IGnssMeasurementCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readBool());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(callback_1_1);
                hwParcel2.send();
                return;
            }
            if (i == 4) {
                hwParcel.enforceInterface(IGnssMeasurement.kInterfaceName);
                int callback_2_0 = setCallback_2_0(IGnssMeasurementCallback.asInterface(hwParcel.readStrongBinder()), hwParcel.readBool());
                hwParcel2.writeStatus(0);
                hwParcel2.writeInt32(callback_2_0);
                hwParcel2.send();
                return;
            }
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
