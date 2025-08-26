package android.hardware.gnss.V1_0;

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
public interface IGnss extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IGnss";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    void cleanup() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    void deleteAidingData(short s) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    IAGnss getExtensionAGnss() throws RemoteException;

    IAGnssRil getExtensionAGnssRil() throws RemoteException;

    IGnssBatching getExtensionGnssBatching() throws RemoteException;

    IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException;

    IGnssDebug getExtensionGnssDebug() throws RemoteException;

    IGnssGeofencing getExtensionGnssGeofencing() throws RemoteException;

    IGnssMeasurement getExtensionGnssMeasurement() throws RemoteException;

    IGnssNavigationMessage getExtensionGnssNavigationMessage() throws RemoteException;

    IGnssNi getExtensionGnssNi() throws RemoteException;

    IGnssXtra getExtensionXtra() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    boolean injectLocation(double d, double d2, float f) throws RemoteException;

    boolean injectTime(long j, long j2, int i) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    boolean setCallback(IGnssCallback iGnssCallback) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    boolean setPositionMode(byte b, int i, int i2, int i3, int i4) throws RemoteException;

    boolean start() throws RemoteException;

    boolean stop() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
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

    public static final class GnssPositionMode {
        public static final byte MS_ASSISTED = 2;
        public static final byte MS_BASED = 1;
        public static final byte STANDALONE = 0;

        public static final String toString(byte b) {
            if (b == 0) {
                return "STANDALONE";
            }
            if (b == 1) {
                return "MS_BASED";
            }
            if (b == 2) {
                return "MS_ASSISTED";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("STANDALONE");
            if ((b & 1) == 1) {
                arrayList.add("MS_BASED");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("MS_ASSISTED");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssPositionRecurrence {
        public static final int RECURRENCE_PERIODIC = 0;
        public static final int RECURRENCE_SINGLE = 1;

        public static final String toString(int i) {
            if (i == 0) {
                return "RECURRENCE_PERIODIC";
            }
            if (i == 1) {
                return "RECURRENCE_SINGLE";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("RECURRENCE_PERIODIC");
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("RECURRENCE_SINGLE");
            } else {
                i2 = 0;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssAidingData {
        public static final short DELETE_ALL = -1;
        public static final short DELETE_ALMANAC = 2;
        public static final short DELETE_CELLDB_INFO = Short.MIN_VALUE;
        public static final short DELETE_EPHEMERIS = 1;
        public static final short DELETE_HEALTH = 64;
        public static final short DELETE_IONO = 16;
        public static final short DELETE_POSITION = 4;
        public static final short DELETE_RTI = 1024;
        public static final short DELETE_SADATA = 512;
        public static final short DELETE_SVDIR = 128;
        public static final short DELETE_SVSTEER = 256;
        public static final short DELETE_TIME = 8;
        public static final short DELETE_UTC = 32;

        public static final String toString(short s) {
            if (s == 1) {
                return "DELETE_EPHEMERIS";
            }
            if (s == 2) {
                return "DELETE_ALMANAC";
            }
            if (s == 4) {
                return "DELETE_POSITION";
            }
            if (s == 8) {
                return "DELETE_TIME";
            }
            if (s == 16) {
                return "DELETE_IONO";
            }
            if (s == 32) {
                return "DELETE_UTC";
            }
            if (s == 64) {
                return "DELETE_HEALTH";
            }
            if (s == 128) {
                return "DELETE_SVDIR";
            }
            if (s == 256) {
                return "DELETE_SVSTEER";
            }
            if (s == 512) {
                return "DELETE_SADATA";
            }
            if (s == 1024) {
                return "DELETE_RTI";
            }
            if (s == Short.MIN_VALUE) {
                return "DELETE_CELLDB_INFO";
            }
            if (s == -1) {
                return "DELETE_ALL";
            }
            return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
        }

        public static final String dumpBitfield(short s) {
            short s2;
            ArrayList arrayList = new ArrayList();
            if ((s & 1) == 1) {
                arrayList.add("DELETE_EPHEMERIS");
                s2 = (short) 1;
            } else {
                s2 = 0;
            }
            if ((s & 2) == 2) {
                arrayList.add("DELETE_ALMANAC");
                s2 = (short) (s2 | 2);
            }
            if ((s & 4) == 4) {
                arrayList.add("DELETE_POSITION");
                s2 = (short) (s2 | 4);
            }
            if ((s & 8) == 8) {
                arrayList.add("DELETE_TIME");
                s2 = (short) (s2 | 8);
            }
            if ((s & 16) == 16) {
                arrayList.add("DELETE_IONO");
                s2 = (short) (s2 | 16);
            }
            if ((s & 32) == 32) {
                arrayList.add("DELETE_UTC");
                s2 = (short) (s2 | 32);
            }
            if ((s & 64) == 64) {
                arrayList.add("DELETE_HEALTH");
                s2 = (short) (s2 | 64);
            }
            if ((s & 128) == 128) {
                arrayList.add("DELETE_SVDIR");
                s2 = (short) (s2 | 128);
            }
            if ((s & DELETE_SVSTEER) == 256) {
                arrayList.add("DELETE_SVSTEER");
                s2 = (short) (s2 | DELETE_SVSTEER);
            }
            if ((s & DELETE_SADATA) == 512) {
                arrayList.add("DELETE_SADATA");
                s2 = (short) (s2 | DELETE_SADATA);
            }
            if ((s & 1024) == 1024) {
                arrayList.add("DELETE_RTI");
                s2 = (short) (s2 | 1024);
            }
            if ((s & Short.MIN_VALUE) == -32768) {
                arrayList.add("DELETE_CELLDB_INFO");
                s2 = (short) (s2 | Short.MIN_VALUE);
            }
            if (s == -1) {
                arrayList.add("DELETE_ALL");
                s2 = (short) (-1);
            }
            if (s != s2) {
                arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class Proxy implements IGnss {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnss]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public boolean setCallback(IGnssCallback iGnssCallback) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
        public IGnssMeasurement getExtensionGnssMeasurement() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssMeasurement.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssNavigationMessage getExtensionGnssNavigationMessage() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
        public IGnssConfiguration getExtensionGnssConfiguration() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return IGnssConfiguration.asInterface(hwParcel2.readStrongBinder());
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnss
        public IGnssDebug getExtensionGnssDebug() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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
            hwParcel.writeInterfaceToken(IGnss.kInterfaceName);
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnss {
        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnss.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnss.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-19, -26, -105, 16, -61, -87, 92, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, -66, -127, -114, 108, -117, -73, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 120, 22, -126, 63, -84, -27, -4, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -63, 119, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, -78, 111, 65, -39, 77, 101}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnss, android.internal.hidl.base.V1_0.IBase
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
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean callback = setCallback(IGnssCallback.asInterface(hwParcel.readStrongBinder()));
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(callback);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean zStart = start();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zStart);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean zStop = stop();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zStop);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    cleanup();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean zInjectTime = injectTime(hwParcel.readInt64(), hwParcel.readInt64(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zInjectTime);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean zInjectLocation = injectLocation(hwParcel.readDouble(), hwParcel.readDouble(), hwParcel.readFloat());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(zInjectLocation);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    deleteAidingData(hwParcel.readInt16());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    boolean positionMode = setPositionMode(hwParcel.readInt8(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32(), hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeBool(positionMode);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IAGnssRil extensionAGnssRil = getExtensionAGnssRil();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnssRil != null ? extensionAGnssRil.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 10:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssGeofencing extensionGnssGeofencing = getExtensionGnssGeofencing();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssGeofencing != null ? extensionGnssGeofencing.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 11:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IAGnss extensionAGnss = getExtensionAGnss();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionAGnss != null ? extensionAGnss.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 12:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssNi extensionGnssNi = getExtensionGnssNi();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNi != null ? extensionGnssNi.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 13:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssMeasurement extensionGnssMeasurement = getExtensionGnssMeasurement();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssMeasurement != null ? extensionGnssMeasurement.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 14:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssNavigationMessage extensionGnssNavigationMessage = getExtensionGnssNavigationMessage();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssNavigationMessage != null ? extensionGnssNavigationMessage.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 15:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssXtra extensionXtra = getExtensionXtra();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionXtra != null ? extensionXtra.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 16:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssConfiguration extensionGnssConfiguration = getExtensionGnssConfiguration();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssConfiguration != null ? extensionGnssConfiguration.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 17:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssDebug extensionGnssDebug = getExtensionGnssDebug();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssDebug != null ? extensionGnssDebug.asBinder() : null);
                    hwParcel2.send();
                    return;
                case 18:
                    hwParcel.enforceInterface(IGnss.kInterfaceName);
                    IGnssBatching extensionGnssBatching = getExtensionGnssBatching();
                    hwParcel2.writeStatus(0);
                    hwParcel2.writeStrongBinder(extensionGnssBatching != null ? extensionGnssBatching.asBinder() : null);
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
