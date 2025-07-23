package android.hardware.vibrator.V1_1;

import android.hardware.vibrator.V1_0.IVibrator;
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
public interface IVibrator extends android.hardware.vibrator.V1_0.IVibrator {
    public static final String kInterfaceName = "android.hardware.vibrator@1.1::IVibrator";

    @FunctionalInterface
    public interface perform_1_1Callback {
        void onValues(int i, int i2);
    }

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    void perform_1_1(int i, byte b, perform_1_1Callback perform_1_1callback) throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IVibrator asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IVibrator)) {
            return (IVibrator) queryLocalInterface;
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

    static IVibrator castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IVibrator getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IVibrator getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IVibrator getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IVibrator getService() throws RemoteException {
        return getService("default");
    }

    public static final class Proxy implements IVibrator {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.vibrator@1.1::IVibrator]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.vibrator.V1_0.IVibrator
        public int on(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
            hwParcel.writeInt32(i);
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

        @Override // android.hardware.vibrator.V1_0.IVibrator
        public int off() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
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

        @Override // android.hardware.vibrator.V1_0.IVibrator
        public boolean supportsAmplitudeControl() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
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

        @Override // android.hardware.vibrator.V1_0.IVibrator
        public int setAmplitude(byte b) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
            hwParcel.writeInt8(b);
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

        @Override // android.hardware.vibrator.V1_0.IVibrator
        public void perform(int i, byte b, IVibrator.performCallback performcallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                performcallback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator
        public void perform_1_1(int i, byte b, perform_1_1Callback perform_1_1callback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IVibrator.kInterfaceName);
            hwParcel.writeInt32(i);
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                perform_1_1callback.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IVibrator {
        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IVibrator.kInterfaceName, android.hardware.vibrator.V1_0.IVibrator.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IVibrator.kInterfaceName;
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-7, 90, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEINOUT, -123, SprAttributeBase.TYPE_ANIMATOR_SET, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 13, SprAttributeBase.TYPE_ANIMATOR_SET, 110, -84, -46, -21, 99, -59, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 16, -33, -88, -119, MidiConstants.STATUS_MIDI_TIME_CODE, 101, -33, 87, 105, 124, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90, -31, -12, 123, 71, -123}, new byte[]{6, -22, 100, -52, 53, 101, 119, Byte.MAX_VALUE, 59, 37, -98, 64, 15, -6, 113, 0, MidiConstants.STATUS_CHANNEL_PRESSURE, Byte.MAX_VALUE, 56, SprAnimatorBase.INTERPOLATOR_TYPE_SINEEASEINOUT, -83, -109, 87, MidiConstants.STATUS_CONTROL_CHANGE, -59, -45, -58, 81, 56, 78, 85, 83}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.vibrator.V1_1.IVibrator, android.hardware.vibrator.V1_0.IVibrator, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IVibrator.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
                    int on = on(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(on);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
                    int off = off();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(off);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
                    boolean supportsAmplitudeControl = supportsAmplitudeControl();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(supportsAmplitudeControl);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
                    int amplitude = setAmplitude(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeInt32(amplitude);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(android.hardware.vibrator.V1_0.IVibrator.kInterfaceName);
                    perform(hwParcel.readInt32(), hwParcel.readInt8(), new IVibrator.performCallback(this) { // from class: android.hardware.vibrator.V1_1.IVibrator.Stub.1
                        @Override // android.hardware.vibrator.V1_0.IVibrator.performCallback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
                            hwParcel2.send();
                        }
                    });
                    return;
                case 6:
                    hwParcel.enforceInterface(IVibrator.kInterfaceName);
                    perform_1_1(hwParcel.readInt32(), hwParcel.readInt8(), new perform_1_1Callback(this) { // from class: android.hardware.vibrator.V1_1.IVibrator.Stub.2
                        @Override // android.hardware.vibrator.V1_1.IVibrator.perform_1_1Callback
                        public void onValues(int i3, int i4) {
                            hwParcel2.writeStatus(0);
                            hwParcel2.writeInt32(i3);
                            hwParcel2.writeInt32(i4);
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
