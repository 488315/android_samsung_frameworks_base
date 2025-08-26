package android.hardware.gnss.V1_0;

import android.hardware.scontext.SContextConstants;
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
public interface IGnssDebug extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IGnssDebug";

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    DebugData getDebugData() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

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

    static IGnssDebug asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface iHwInterfaceQueryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (iHwInterfaceQueryLocalInterface != null && (iHwInterfaceQueryLocalInterface instanceof IGnssDebug)) {
            return (IGnssDebug) iHwInterfaceQueryLocalInterface;
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

    static IGnssDebug castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssDebug getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssDebug getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssDebug getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssDebug getService() throws RemoteException {
        return getService("default");
    }

    public static final class SatelliteEphemerisType {
        public static final byte ALMANAC_ONLY = 1;
        public static final byte EPHEMERIS = 0;
        public static final byte NOT_AVAILABLE = 2;

        public static final String toString(byte b) {
            if (b == 0) {
                return "EPHEMERIS";
            }
            if (b == 1) {
                return "ALMANAC_ONLY";
            }
            if (b == 2) {
                return "NOT_AVAILABLE";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("EPHEMERIS");
            if ((b & 1) == 1) {
                arrayList.add("ALMANAC_ONLY");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("NOT_AVAILABLE");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class SatelliteEphemerisSource {
        public static final byte DEMODULATED = 0;
        public static final byte OTHER = 3;
        public static final byte OTHER_SERVER_PROVIDED = 2;
        public static final byte SUPL_PROVIDED = 1;

        public static final String toString(byte b) {
            if (b == 0) {
                return "DEMODULATED";
            }
            if (b == 1) {
                return "SUPL_PROVIDED";
            }
            if (b == 2) {
                return "OTHER_SERVER_PROVIDED";
            }
            if (b == 3) {
                return "OTHER";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("DEMODULATED");
            if ((b & 1) == 1) {
                arrayList.add("SUPL_PROVIDED");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("OTHER_SERVER_PROVIDED");
                b2 = (byte) (b2 | 2);
            }
            if ((b & 3) == 3) {
                arrayList.add("OTHER");
                b2 = (byte) (b2 | 3);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class SatelliteEphemerisHealth {
        public static final byte BAD = 1;
        public static final byte GOOD = 0;
        public static final byte UNKNOWN = 2;

        public static final String toString(byte b) {
            if (b == 0) {
                return "GOOD";
            }
            if (b == 1) {
                return "BAD";
            }
            if (b == 2) {
                return "UNKNOWN";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("GOOD");
            if ((b & 1) == 1) {
                arrayList.add("BAD");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("UNKNOWN");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class PositionDebug {
        public boolean valid = false;
        public double latitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double longitudeDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public float altitudeMeters = 0.0f;
        public float speedMetersPerSec = 0.0f;
        public float bearingDegrees = 0.0f;
        public double horizontalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double verticalAccuracyMeters = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double speedAccuracyMetersPerSecond = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double bearingAccuracyDegrees = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public float ageSeconds = 0.0f;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != PositionDebug.class) {
                return false;
            }
            PositionDebug positionDebug = (PositionDebug) obj;
            return this.valid == positionDebug.valid && this.latitudeDegrees == positionDebug.latitudeDegrees && this.longitudeDegrees == positionDebug.longitudeDegrees && this.altitudeMeters == positionDebug.altitudeMeters && this.speedMetersPerSec == positionDebug.speedMetersPerSec && this.bearingDegrees == positionDebug.bearingDegrees && this.horizontalAccuracyMeters == positionDebug.horizontalAccuracyMeters && this.verticalAccuracyMeters == positionDebug.verticalAccuracyMeters && this.speedAccuracyMetersPerSecond == positionDebug.speedAccuracyMetersPerSecond && this.bearingAccuracyDegrees == positionDebug.bearingAccuracyDegrees && this.ageSeconds == positionDebug.ageSeconds;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.valid))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.latitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.longitudeDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.altitudeMeters))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.speedMetersPerSec))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.bearingDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.horizontalAccuracyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.verticalAccuracyMeters))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.speedAccuracyMetersPerSecond))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.bearingAccuracyDegrees))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.ageSeconds))));
        }

        public final String toString() {
            return "{.valid = " + this.valid + ", .latitudeDegrees = " + this.latitudeDegrees + ", .longitudeDegrees = " + this.longitudeDegrees + ", .altitudeMeters = " + this.altitudeMeters + ", .speedMetersPerSec = " + this.speedMetersPerSec + ", .bearingDegrees = " + this.bearingDegrees + ", .horizontalAccuracyMeters = " + this.horizontalAccuracyMeters + ", .verticalAccuracyMeters = " + this.verticalAccuracyMeters + ", .speedAccuracyMetersPerSecond = " + this.speedAccuracyMetersPerSecond + ", .bearingAccuracyDegrees = " + this.bearingAccuracyDegrees + ", .ageSeconds = " + this.ageSeconds + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
        }

        public static final ArrayList<PositionDebug> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<PositionDebug> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 80, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                PositionDebug positionDebug = new PositionDebug();
                positionDebug.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 80);
                arrayList.add(positionDebug);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.valid = hwBlob.getBool(j);
            this.latitudeDegrees = hwBlob.getDouble(8 + j);
            this.longitudeDegrees = hwBlob.getDouble(16 + j);
            this.altitudeMeters = hwBlob.getFloat(24 + j);
            this.speedMetersPerSec = hwBlob.getFloat(28 + j);
            this.bearingDegrees = hwBlob.getFloat(32 + j);
            this.horizontalAccuracyMeters = hwBlob.getDouble(40 + j);
            this.verticalAccuracyMeters = hwBlob.getDouble(48 + j);
            this.speedAccuracyMetersPerSecond = hwBlob.getDouble(56 + j);
            this.bearingAccuracyDegrees = hwBlob.getDouble(64 + j);
            this.ageSeconds = hwBlob.getFloat(j + 72);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(80);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PositionDebug> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 80);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putBool(j, this.valid);
            hwBlob.putDouble(8 + j, this.latitudeDegrees);
            hwBlob.putDouble(16 + j, this.longitudeDegrees);
            hwBlob.putFloat(24 + j, this.altitudeMeters);
            hwBlob.putFloat(28 + j, this.speedMetersPerSec);
            hwBlob.putFloat(32 + j, this.bearingDegrees);
            hwBlob.putDouble(40 + j, this.horizontalAccuracyMeters);
            hwBlob.putDouble(48 + j, this.verticalAccuracyMeters);
            hwBlob.putDouble(56 + j, this.speedAccuracyMetersPerSecond);
            hwBlob.putDouble(64 + j, this.bearingAccuracyDegrees);
            hwBlob.putFloat(j + 72, this.ageSeconds);
        }
    }

    public static final class TimeDebug {
        public long timeEstimate = 0;
        public float timeUncertaintyNs = 0.0f;
        public float frequencyUncertaintyNsPerSec = 0.0f;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != TimeDebug.class) {
                return false;
            }
            TimeDebug timeDebug = (TimeDebug) obj;
            return this.timeEstimate == timeDebug.timeEstimate && this.timeUncertaintyNs == timeDebug.timeUncertaintyNs && this.frequencyUncertaintyNsPerSec == timeDebug.frequencyUncertaintyNsPerSec;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.timeEstimate))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.timeUncertaintyNs))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.frequencyUncertaintyNsPerSec))));
        }

        public final String toString() {
            return "{.timeEstimate = " + this.timeEstimate + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + ", .frequencyUncertaintyNsPerSec = " + this.frequencyUncertaintyNsPerSec + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
        }

        public static final ArrayList<TimeDebug> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<TimeDebug> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 16, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                TimeDebug timeDebug = new TimeDebug();
                timeDebug.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 16);
                arrayList.add(timeDebug);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.timeEstimate = hwBlob.getInt64(j);
            this.timeUncertaintyNs = hwBlob.getFloat(8 + j);
            this.frequencyUncertaintyNsPerSec = hwBlob.getFloat(j + 12);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(16);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TimeDebug> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 16);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt64(j, this.timeEstimate);
            hwBlob.putFloat(8 + j, this.timeUncertaintyNs);
            hwBlob.putFloat(j + 12, this.frequencyUncertaintyNsPerSec);
        }
    }

    public static final class SatelliteData {
        public short svid = 0;
        public byte constellation = 0;
        public byte ephemerisType = 0;
        public byte ephemerisSource = 0;
        public byte ephemerisHealth = 0;
        public float ephemerisAgeSeconds = 0.0f;
        public boolean serverPredictionIsAvailable = false;
        public float serverPredictionAgeSeconds = 0.0f;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != SatelliteData.class) {
                return false;
            }
            SatelliteData satelliteData = (SatelliteData) obj;
            return this.svid == satelliteData.svid && this.constellation == satelliteData.constellation && this.ephemerisType == satelliteData.ephemerisType && this.ephemerisSource == satelliteData.ephemerisSource && this.ephemerisHealth == satelliteData.ephemerisHealth && this.ephemerisAgeSeconds == satelliteData.ephemerisAgeSeconds && this.serverPredictionIsAvailable == satelliteData.serverPredictionIsAvailable && this.serverPredictionAgeSeconds == satelliteData.serverPredictionAgeSeconds;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.svid))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.constellation))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.ephemerisType))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.ephemerisSource))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.ephemerisHealth))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.ephemerisAgeSeconds))), Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.serverPredictionIsAvailable))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.serverPredictionAgeSeconds))));
        }

        public final String toString() {
            return "{.svid = " + ((int) this.svid) + ", .constellation = " + GnssConstellationType.toString(this.constellation) + ", .ephemerisType = " + SatelliteEphemerisType.toString(this.ephemerisType) + ", .ephemerisSource = " + SatelliteEphemerisSource.toString(this.ephemerisSource) + ", .ephemerisHealth = " + SatelliteEphemerisHealth.toString(this.ephemerisHealth) + ", .ephemerisAgeSeconds = " + this.ephemerisAgeSeconds + ", .serverPredictionIsAvailable = " + this.serverPredictionIsAvailable + ", .serverPredictionAgeSeconds = " + this.serverPredictionAgeSeconds + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
        }

        public static final ArrayList<SatelliteData> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<SatelliteData> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                SatelliteData satelliteData = new SatelliteData();
                satelliteData.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
                arrayList.add(satelliteData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.svid = hwBlob.getInt16(j);
            this.constellation = hwBlob.getInt8(2 + j);
            this.ephemerisType = hwBlob.getInt8(3 + j);
            this.ephemerisSource = hwBlob.getInt8(4 + j);
            this.ephemerisHealth = hwBlob.getInt8(5 + j);
            this.ephemerisAgeSeconds = hwBlob.getFloat(8 + j);
            this.serverPredictionIsAvailable = hwBlob.getBool(12 + j);
            this.serverPredictionAgeSeconds = hwBlob.getFloat(j + 16);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(20);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SatelliteData> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt16(j, this.svid);
            hwBlob.putInt8(2 + j, this.constellation);
            hwBlob.putInt8(3 + j, this.ephemerisType);
            hwBlob.putInt8(4 + j, this.ephemerisSource);
            hwBlob.putInt8(5 + j, this.ephemerisHealth);
            hwBlob.putFloat(8 + j, this.ephemerisAgeSeconds);
            hwBlob.putBool(12 + j, this.serverPredictionIsAvailable);
            hwBlob.putFloat(j + 16, this.serverPredictionAgeSeconds);
        }
    }

    public static final class DebugData {
        public PositionDebug position = new PositionDebug();
        public TimeDebug time = new TimeDebug();
        public ArrayList<SatelliteData> satelliteDataArray = new ArrayList<>();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != DebugData.class) {
                return false;
            }
            DebugData debugData = (DebugData) obj;
            return HidlSupport.deepEquals(this.position, debugData.position) && HidlSupport.deepEquals(this.time, debugData.time) && HidlSupport.deepEquals(this.satelliteDataArray, debugData.satelliteDataArray);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.position)), Integer.valueOf(HidlSupport.deepHashCode(this.time)), Integer.valueOf(HidlSupport.deepHashCode(this.satelliteDataArray)));
        }

        public final String toString() {
            return "{.position = " + this.position + ", .time = " + this.time + ", .satelliteDataArray = " + this.satelliteDataArray + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
        }

        public static final ArrayList<DebugData> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<DebugData> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                DebugData debugData = new DebugData();
                debugData.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
                arrayList.add(debugData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.position.readEmbeddedFromParcel(hwParcel, hwBlob, j);
            this.time.readEmbeddedFromParcel(hwParcel, hwBlob, 80 + j);
            long j2 = j + 96;
            int int32 = hwBlob.getInt32(j + 104);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 20, hwBlob.handle(), j2, true);
            this.satelliteDataArray.clear();
            for (int i = 0; i < int32; i++) {
                SatelliteData satelliteData = new SatelliteData();
                satelliteData.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 20);
                this.satelliteDataArray.add(satelliteData);
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(112);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DebugData> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 112);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            this.position.writeEmbeddedToBlob(hwBlob, j);
            this.time.writeEmbeddedToBlob(hwBlob, 80 + j);
            int size = this.satelliteDataArray.size();
            long j2 = 96 + j;
            hwBlob.putInt32(104 + j, size);
            hwBlob.putBool(j + 108, false);
            HwBlob hwBlob2 = new HwBlob(size * 20);
            for (int i = 0; i < size; i++) {
                this.satelliteDataArray.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
            }
            hwBlob.putBlob(j2, hwBlob2);
        }
    }

    public static final class Proxy implements IGnssDebug {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssDebug]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug
        public DebugData getDebugData() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssDebug.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                DebugData debugData = new DebugData();
                debugData.readFromParcel(hwParcel2);
                return debugData;
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssDebug {
        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssDebug.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssDebug.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{69, 66, 18, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT33, -106, -5, MidiConstants.STATUS_SONG_POSITION, 113, 1, -53, -126, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEIN, -70, -5, 118, -25, -56, MidiConstants.STATUS_CHANNEL_PRESSURE, 50, -39, 119, -35, 16, 88, -19, -40, -27, -120, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, -91, 117, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT80}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssDebug, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssDebug.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IGnssDebug.kInterfaceName);
                    DebugData debugData = getDebugData();
                    hwParcel2.writeStatus(0);
                    debugData.writeToParcel(hwParcel2);
                    hwParcel2.send();
                    return;
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
