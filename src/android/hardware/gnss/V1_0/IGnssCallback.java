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
import android.security.keystore.KeyProperties;
import com.android.internal.logging.nano.MetricsProto;
import com.android.internal.midi.MidiConstants;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes2.dex */
public interface IGnssCallback extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IGnssCallback";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void gnssAcquireWakelockCb() throws RemoteException;

    void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException;

    void gnssNmeaCb(long j, String str) throws RemoteException;

    void gnssReleaseWakelockCb() throws RemoteException;

    void gnssRequestTimeCb() throws RemoteException;

    void gnssSetCapabilitesCb(int i) throws RemoteException;

    void gnssSetSystemInfoCb(GnssSystemInfo gnssSystemInfo) throws RemoteException;

    void gnssStatusCb(byte b) throws RemoteException;

    void gnssSvStatusCb(GnssSvStatus gnssSvStatus) throws RemoteException;

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

    @Override // android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException;

    static IGnssCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssCallback)) {
            return (IGnssCallback) queryLocalInterface;
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

    static IGnssCallback castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssCallback getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssCallback getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssCallback getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class Capabilities {
        public static final int GEOFENCING = 32;
        public static final int MEASUREMENTS = 64;
        public static final int MSA = 4;
        public static final int MSB = 2;
        public static final int NAV_MESSAGES = 128;
        public static final int ON_DEMAND_TIME = 16;
        public static final int SCHEDULING = 1;
        public static final int SINGLE_SHOT = 8;

        public static final String toString(int i) {
            if (i == 1) {
                return "SCHEDULING";
            }
            if (i == 2) {
                return "MSB";
            }
            if (i == 4) {
                return "MSA";
            }
            if (i == 8) {
                return "SINGLE_SHOT";
            }
            if (i == 16) {
                return "ON_DEMAND_TIME";
            }
            if (i == 32) {
                return "GEOFENCING";
            }
            if (i == 64) {
                return "MEASUREMENTS";
            }
            if (i == 128) {
                return "NAV_MESSAGES";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("SCHEDULING");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("MSB");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("MSA");
                i2 |= 4;
            }
            if ((i & 8) == 8) {
                arrayList.add("SINGLE_SHOT");
                i2 |= 8;
            }
            if ((i & 16) == 16) {
                arrayList.add("ON_DEMAND_TIME");
                i2 |= 16;
            }
            if ((i & 32) == 32) {
                arrayList.add("GEOFENCING");
                i2 |= 32;
            }
            if ((i & 64) == 64) {
                arrayList.add("MEASUREMENTS");
                i2 |= 64;
            }
            if ((i & 128) == 128) {
                arrayList.add("NAV_MESSAGES");
                i2 |= 128;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssStatusValue {
        public static final byte ENGINE_OFF = 4;
        public static final byte ENGINE_ON = 3;
        public static final byte NONE = 0;
        public static final byte SESSION_BEGIN = 1;
        public static final byte SESSION_END = 2;

        public static final String toString(byte b) {
            if (b == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "SESSION_BEGIN";
            }
            if (b == 2) {
                return "SESSION_END";
            }
            if (b == 3) {
                return "ENGINE_ON";
            }
            if (b == 4) {
                return "ENGINE_OFF";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add(KeyProperties.DIGEST_NONE);
            if ((b & 1) == 1) {
                arrayList.add("SESSION_BEGIN");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("SESSION_END");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("ENGINE_ON");
                b2 = (byte) (b2 | 3);
            }
            if ((b & 4) == 4) {
                arrayList.add("ENGINE_OFF");
                b2 = (byte) (b2 | 4);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssSvFlags {
        public static final byte HAS_ALMANAC_DATA = 2;
        public static final byte HAS_CARRIER_FREQUENCY = 8;
        public static final byte HAS_EPHEMERIS_DATA = 1;
        public static final byte NONE = 0;
        public static final byte USED_IN_FIX = 4;

        public static final String toString(byte b) {
            if (b == 0) {
                return KeyProperties.DIGEST_NONE;
            }
            if (b == 1) {
                return "HAS_EPHEMERIS_DATA";
            }
            if (b == 2) {
                return "HAS_ALMANAC_DATA";
            }
            if (b == 4) {
                return "USED_IN_FIX";
            }
            if (b == 8) {
                return "HAS_CARRIER_FREQUENCY";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add(KeyProperties.DIGEST_NONE);
            if ((b & 1) == 1) {
                arrayList.add("HAS_EPHEMERIS_DATA");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("HAS_ALMANAC_DATA");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 4) == 4) {
                arrayList.add("USED_IN_FIX");
                b2 = (byte) (b2 | 4);
            }
            if ((b & 8) == 8) {
                arrayList.add("HAS_CARRIER_FREQUENCY");
                b2 = (byte) (b2 | 8);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssSvInfo {
        public byte svFlag;
        public short svid = 0;
        public byte constellation = 0;
        public float cN0Dbhz = 0.0f;
        public float elevationDegrees = 0.0f;
        public float azimuthDegrees = 0.0f;
        public float carrierFrequencyHz = 0.0f;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssSvInfo.class) {
                return false;
            }
            GnssSvInfo gnssSvInfo = (GnssSvInfo) obj;
            return this.svid == gnssSvInfo.svid && this.constellation == gnssSvInfo.constellation && this.cN0Dbhz == gnssSvInfo.cN0Dbhz && this.elevationDegrees == gnssSvInfo.elevationDegrees && this.azimuthDegrees == gnssSvInfo.azimuthDegrees && this.carrierFrequencyHz == gnssSvInfo.carrierFrequencyHz && HidlSupport.deepEquals(Byte.valueOf(this.svFlag), Byte.valueOf(gnssSvInfo.svFlag));
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.svid))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.constellation))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.cN0Dbhz))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.elevationDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.azimuthDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.carrierFrequencyHz))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.svFlag))));
        }

        public final String toString() {
            return "{.svid = " + ((int) this.svid) + ", .constellation = " + GnssConstellationType.toString(this.constellation) + ", .cN0Dbhz = " + this.cN0Dbhz + ", .elevationDegrees = " + this.elevationDegrees + ", .azimuthDegrees = " + this.azimuthDegrees + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .svFlag = " + GnssSvFlags.dumpBitfield(this.svFlag) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
        }

        public static final ArrayList<GnssSvInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssSvInfo> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 24, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssSvInfo gnssSvInfo = new GnssSvInfo();
                gnssSvInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 24);
                arrayList.add(gnssSvInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.svid = hwBlob.getInt16(j);
            this.constellation = hwBlob.getInt8(2 + j);
            this.cN0Dbhz = hwBlob.getFloat(4 + j);
            this.elevationDegrees = hwBlob.getFloat(8 + j);
            this.azimuthDegrees = hwBlob.getFloat(12 + j);
            this.carrierFrequencyHz = hwBlob.getFloat(16 + j);
            this.svFlag = hwBlob.getInt8(j + 20);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(24);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssSvInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 24);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt16(j, this.svid);
            hwBlob.putInt8(2 + j, this.constellation);
            hwBlob.putFloat(4 + j, this.cN0Dbhz);
            hwBlob.putFloat(8 + j, this.elevationDegrees);
            hwBlob.putFloat(12 + j, this.azimuthDegrees);
            hwBlob.putFloat(16 + j, this.carrierFrequencyHz);
            hwBlob.putInt8(j + 20, this.svFlag);
        }
    }

    public static final class GnssSvStatus {
        public int numSvs = 0;
        public GnssSvInfo[] gnssSvList = new GnssSvInfo[64];

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssSvStatus.class) {
                return false;
            }
            GnssSvStatus gnssSvStatus = (GnssSvStatus) obj;
            return this.numSvs == gnssSvStatus.numSvs && HidlSupport.deepEquals(this.gnssSvList, gnssSvStatus.gnssSvList);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.numSvs))), Integer.valueOf(HidlSupport.deepHashCode(this.gnssSvList)));
        }

        public final String toString() {
            return "{.numSvs = " + this.numSvs + ", .gnssSvList = " + Arrays.toString(this.gnssSvList) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(1540L), 0L);
        }

        public static final ArrayList<GnssSvStatus> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssSvStatus> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssSvStatus gnssSvStatus = new GnssSvStatus();
                gnssSvStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
                arrayList.add(gnssSvStatus);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.numSvs = hwBlob.getInt32(j);
            long j2 = j + 4;
            for (int i = 0; i < 64; i++) {
                this.gnssSvList[i] = new GnssSvInfo();
                this.gnssSvList[i].readEmbeddedFromParcel(hwParcel, hwBlob, j2);
                j2 += 24;
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssSvStatus> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * MetricsProto.MetricsEvent.FIELD_ACTIVITY_RECORD_LAUNCH_MODE);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt32(j, this.numSvs);
            long j2 = j + 4;
            for (int i = 0; i < 64; i++) {
                this.gnssSvList[i].writeEmbeddedToBlob(hwBlob, j2);
                j2 += 24;
            }
        }
    }

    public static final class GnssSystemInfo {
        public short yearOfHw = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && obj.getClass() == GnssSystemInfo.class && this.yearOfHw == ((GnssSystemInfo) obj).yearOfHw;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.yearOfHw))));
        }

        public final String toString() {
            return "{.yearOfHw = " + ((int) this.yearOfHw) + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
        }

        public static final ArrayList<GnssSystemInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssSystemInfo> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 2, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssSystemInfo gnssSystemInfo = new GnssSystemInfo();
                gnssSystemInfo.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 2);
                arrayList.add(gnssSystemInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.yearOfHw = hwBlob.getInt16(j);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(2);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssSystemInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 2);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt16(j, this.yearOfHw);
        }
    }

    public static final class Proxy implements IGnssCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssLocationCb(GnssLocation gnssLocation) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            gnssLocation.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssStatusCb(byte b) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            hwParcel.writeInt8(b);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSvStatusCb(GnssSvStatus gnssSvStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            gnssSvStatus.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssNmeaCb(long j, String str) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            hwParcel.writeInt64(j);
            hwParcel.writeString(str);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetCapabilitesCb(int i) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            hwParcel.writeInt32(i);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssAcquireWakelockCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssReleaseWakelockCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssRequestTimeCb() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback
        public void gnssSetSystemInfoCb(GnssSystemInfo gnssSystemInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssCallback.kInterfaceName);
            gnssSystemInfo.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssCallback {
        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-94, -5, -39, 116, Byte.MAX_VALUE, -69, -100, -21, -116, 16, MidiConstants.STATUS_NOTE_ON, -75, -94, 65, 56, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, 70, 80, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, 90, -16, 101, 74, -116, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, SprAttributeBase.TYPE_DURATION, 58, -101, -11, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -4}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    GnssLocation gnssLocation = new GnssLocation();
                    gnssLocation.readFromParcel(hwParcel);
                    gnssLocationCb(gnssLocation);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 2:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssStatusCb(hwParcel.readInt8());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 3:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    GnssSvStatus gnssSvStatus = new GnssSvStatus();
                    gnssSvStatus.readFromParcel(hwParcel);
                    gnssSvStatusCb(gnssSvStatus);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 4:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssNmeaCb(hwParcel.readInt64(), hwParcel.readString());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 5:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssSetCapabilitesCb(hwParcel.readInt32());
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 6:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssAcquireWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 7:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssReleaseWakelockCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 8:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    gnssRequestTimeCb();
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
                case 9:
                    hwParcel.enforceInterface(IGnssCallback.kInterfaceName);
                    GnssSystemInfo gnssSystemInfo = new GnssSystemInfo();
                    gnssSystemInfo.readFromParcel(hwParcel);
                    gnssSetSystemInfoCb(gnssSystemInfo);
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
