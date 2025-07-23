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
public interface IGnssMeasurementCallback extends IBase {
    public static final String kInterfaceName = "android.hardware.gnss@1.0::IGnssMeasurementCallback";

    void GnssMeasurementCb(GnssData gnssData) throws RemoteException;

    @Override // android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

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

    static IGnssMeasurementCallback asInterface(IHwBinder iHwBinder) {
        if (iHwBinder == null) {
            return null;
        }
        IHwInterface queryLocalInterface = iHwBinder.queryLocalInterface(kInterfaceName);
        if (queryLocalInterface != null && (queryLocalInterface instanceof IGnssMeasurementCallback)) {
            return (IGnssMeasurementCallback) queryLocalInterface;
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

    static IGnssMeasurementCallback castFrom(IHwInterface iHwInterface) {
        if (iHwInterface == null) {
            return null;
        }
        return asInterface(iHwInterface.asBinder());
    }

    static IGnssMeasurementCallback getService(String str, boolean z) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str, z));
    }

    static IGnssMeasurementCallback getService(boolean z) throws RemoteException {
        return getService("default", z);
    }

    @Deprecated
    static IGnssMeasurementCallback getService(String str) throws RemoteException {
        return asInterface(HwBinder.getService(kInterfaceName, str));
    }

    @Deprecated
    static IGnssMeasurementCallback getService() throws RemoteException {
        return getService("default");
    }

    public static final class GnssClockFlags {
        public static final short HAS_BIAS = 8;
        public static final short HAS_BIAS_UNCERTAINTY = 16;
        public static final short HAS_DRIFT = 32;
        public static final short HAS_DRIFT_UNCERTAINTY = 64;
        public static final short HAS_FULL_BIAS = 4;
        public static final short HAS_LEAP_SECOND = 1;
        public static final short HAS_TIME_UNCERTAINTY = 2;

        public static final String toString(short s) {
            if (s == 1) {
                return "HAS_LEAP_SECOND";
            }
            if (s == 2) {
                return "HAS_TIME_UNCERTAINTY";
            }
            if (s == 4) {
                return "HAS_FULL_BIAS";
            }
            if (s == 8) {
                return "HAS_BIAS";
            }
            if (s == 16) {
                return "HAS_BIAS_UNCERTAINTY";
            }
            if (s == 32) {
                return "HAS_DRIFT";
            }
            if (s == 64) {
                return "HAS_DRIFT_UNCERTAINTY";
            }
            return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
        }

        public static final String dumpBitfield(short s) {
            short s2;
            ArrayList arrayList = new ArrayList();
            if ((s & 1) == 1) {
                arrayList.add("HAS_LEAP_SECOND");
                s2 = (short) 1;
            } else {
                s2 = 0;
            }
            if ((s & 2) == 2) {
                arrayList.add("HAS_TIME_UNCERTAINTY");
                s2 = (short) (s2 | 2);
            }
            if ((s & 4) == 4) {
                arrayList.add("HAS_FULL_BIAS");
                s2 = (short) (s2 | 4);
            }
            if ((s & 8) == 8) {
                arrayList.add("HAS_BIAS");
                s2 = (short) (s2 | 8);
            }
            if ((s & 16) == 16) {
                arrayList.add("HAS_BIAS_UNCERTAINTY");
                s2 = (short) (s2 | 16);
            }
            if ((s & 32) == 32) {
                arrayList.add("HAS_DRIFT");
                s2 = (short) (s2 | 32);
            }
            if ((s & 64) == 64) {
                arrayList.add("HAS_DRIFT_UNCERTAINTY");
                s2 = (short) (s2 | 64);
            }
            if (s != s2) {
                arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurementFlags {
        public static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
        public static final int HAS_CARRIER_CYCLES = 1024;
        public static final int HAS_CARRIER_FREQUENCY = 512;
        public static final int HAS_CARRIER_PHASE = 2048;
        public static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
        public static final int HAS_SNR = 1;

        public static final String toString(int i) {
            if (i == 1) {
                return "HAS_SNR";
            }
            if (i == 512) {
                return "HAS_CARRIER_FREQUENCY";
            }
            if (i == 1024) {
                return "HAS_CARRIER_CYCLES";
            }
            if (i == 2048) {
                return "HAS_CARRIER_PHASE";
            }
            if (i == 4096) {
                return "HAS_CARRIER_PHASE_UNCERTAINTY";
            }
            if (i == 8192) {
                return "HAS_AUTOMATIC_GAIN_CONTROL";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("HAS_SNR");
            } else {
                i2 = 0;
            }
            if ((i & 512) == 512) {
                arrayList.add("HAS_CARRIER_FREQUENCY");
                i2 |= 512;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("HAS_CARRIER_CYCLES");
                i2 |= 1024;
            }
            if ((i & 2048) == 2048) {
                arrayList.add("HAS_CARRIER_PHASE");
                i2 |= 2048;
            }
            if ((i & 4096) == 4096) {
                arrayList.add("HAS_CARRIER_PHASE_UNCERTAINTY");
                i2 |= 4096;
            }
            if ((i & 8192) == 8192) {
                arrayList.add("HAS_AUTOMATIC_GAIN_CONTROL");
                i2 |= 8192;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssMultipathIndicator {
        public static final byte INDICATIOR_NOT_PRESENT = 2;
        public static final byte INDICATOR_PRESENT = 1;
        public static final byte INDICATOR_UNKNOWN = 0;

        public static final String toString(byte b) {
            if (b == 0) {
                return "INDICATOR_UNKNOWN";
            }
            if (b == 1) {
                return "INDICATOR_PRESENT";
            }
            if (b == 2) {
                return "INDICATIOR_NOT_PRESENT";
            }
            return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
        }

        public static final String dumpBitfield(byte b) {
            byte b2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("INDICATOR_UNKNOWN");
            if ((b & 1) == 1) {
                arrayList.add("INDICATOR_PRESENT");
                b2 = (byte) 1;
            } else {
                b2 = 0;
            }
            if ((b & 2) == 2) {
                arrayList.add("INDICATIOR_NOT_PRESENT");
                b2 = (byte) (b2 | 2);
            }
            if (b != b2) {
                arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurementState {
        public static final int STATE_BDS_D2_BIT_SYNC = 256;
        public static final int STATE_BDS_D2_SUBFRAME_SYNC = 512;
        public static final int STATE_BIT_SYNC = 2;
        public static final int STATE_CODE_LOCK = 1;
        public static final int STATE_GAL_E1BC_CODE_LOCK = 1024;
        public static final int STATE_GAL_E1B_PAGE_SYNC = 4096;
        public static final int STATE_GAL_E1C_2ND_CODE_LOCK = 2048;
        public static final int STATE_GLO_STRING_SYNC = 64;
        public static final int STATE_GLO_TOD_DECODED = 128;
        public static final int STATE_GLO_TOD_KNOWN = 32768;
        public static final int STATE_MSEC_AMBIGUOUS = 16;
        public static final int STATE_SBAS_SYNC = 8192;
        public static final int STATE_SUBFRAME_SYNC = 4;
        public static final int STATE_SYMBOL_SYNC = 32;
        public static final int STATE_TOW_DECODED = 8;
        public static final int STATE_TOW_KNOWN = 16384;
        public static final int STATE_UNKNOWN = 0;

        public static final String toString(int i) {
            if (i == 0) {
                return "STATE_UNKNOWN";
            }
            if (i == 1) {
                return "STATE_CODE_LOCK";
            }
            if (i == 2) {
                return "STATE_BIT_SYNC";
            }
            if (i == 4) {
                return "STATE_SUBFRAME_SYNC";
            }
            if (i == 8) {
                return "STATE_TOW_DECODED";
            }
            if (i == 16) {
                return "STATE_MSEC_AMBIGUOUS";
            }
            if (i == 32) {
                return "STATE_SYMBOL_SYNC";
            }
            if (i == 64) {
                return "STATE_GLO_STRING_SYNC";
            }
            if (i == 128) {
                return "STATE_GLO_TOD_DECODED";
            }
            if (i == 256) {
                return "STATE_BDS_D2_BIT_SYNC";
            }
            if (i == 512) {
                return "STATE_BDS_D2_SUBFRAME_SYNC";
            }
            if (i == 1024) {
                return "STATE_GAL_E1BC_CODE_LOCK";
            }
            if (i == 2048) {
                return "STATE_GAL_E1C_2ND_CODE_LOCK";
            }
            if (i == 4096) {
                return "STATE_GAL_E1B_PAGE_SYNC";
            }
            if (i == 8192) {
                return "STATE_SBAS_SYNC";
            }
            if (i == 16384) {
                return "STATE_TOW_KNOWN";
            }
            if (i == 32768) {
                return "STATE_GLO_TOD_KNOWN";
            }
            return "0x" + Integer.toHexString(i);
        }

        public static final String dumpBitfield(int i) {
            ArrayList arrayList = new ArrayList();
            arrayList.add("STATE_UNKNOWN");
            int i2 = 1;
            if ((i & 1) == 1) {
                arrayList.add("STATE_CODE_LOCK");
            } else {
                i2 = 0;
            }
            if ((i & 2) == 2) {
                arrayList.add("STATE_BIT_SYNC");
                i2 |= 2;
            }
            if ((i & 4) == 4) {
                arrayList.add("STATE_SUBFRAME_SYNC");
                i2 |= 4;
            }
            if ((i & 8) == 8) {
                arrayList.add("STATE_TOW_DECODED");
                i2 |= 8;
            }
            if ((i & 16) == 16) {
                arrayList.add("STATE_MSEC_AMBIGUOUS");
                i2 |= 16;
            }
            if ((i & 32) == 32) {
                arrayList.add("STATE_SYMBOL_SYNC");
                i2 |= 32;
            }
            if ((i & 64) == 64) {
                arrayList.add("STATE_GLO_STRING_SYNC");
                i2 |= 64;
            }
            if ((i & 128) == 128) {
                arrayList.add("STATE_GLO_TOD_DECODED");
                i2 |= 128;
            }
            if ((i & 256) == 256) {
                arrayList.add("STATE_BDS_D2_BIT_SYNC");
                i2 |= 256;
            }
            if ((i & 512) == 512) {
                arrayList.add("STATE_BDS_D2_SUBFRAME_SYNC");
                i2 |= 512;
            }
            if ((i & 1024) == 1024) {
                arrayList.add("STATE_GAL_E1BC_CODE_LOCK");
                i2 |= 1024;
            }
            if ((i & 2048) == 2048) {
                arrayList.add("STATE_GAL_E1C_2ND_CODE_LOCK");
                i2 |= 2048;
            }
            if ((i & 4096) == 4096) {
                arrayList.add("STATE_GAL_E1B_PAGE_SYNC");
                i2 |= 4096;
            }
            if ((i & 8192) == 8192) {
                arrayList.add("STATE_SBAS_SYNC");
                i2 |= 8192;
            }
            if ((i & 16384) == 16384) {
                arrayList.add("STATE_TOW_KNOWN");
                i2 |= 16384;
            }
            if ((i & 32768) == 32768) {
                arrayList.add("STATE_GLO_TOD_KNOWN");
                i2 |= 32768;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssAccumulatedDeltaRangeState {
        public static final short ADR_STATE_CYCLE_SLIP = 4;
        public static final short ADR_STATE_RESET = 2;
        public static final short ADR_STATE_UNKNOWN = 0;
        public static final short ADR_STATE_VALID = 1;

        public static final String toString(short s) {
            if (s == 0) {
                return "ADR_STATE_UNKNOWN";
            }
            if (s == 1) {
                return "ADR_STATE_VALID";
            }
            if (s == 2) {
                return "ADR_STATE_RESET";
            }
            if (s == 4) {
                return "ADR_STATE_CYCLE_SLIP";
            }
            return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
        }

        public static final String dumpBitfield(short s) {
            short s2;
            ArrayList arrayList = new ArrayList();
            arrayList.add("ADR_STATE_UNKNOWN");
            if ((s & 1) == 1) {
                arrayList.add("ADR_STATE_VALID");
                s2 = (short) 1;
            } else {
                s2 = 0;
            }
            if ((s & 2) == 2) {
                arrayList.add("ADR_STATE_RESET");
                s2 = (short) (s2 | 2);
            }
            if ((s & 4) == 4) {
                arrayList.add("ADR_STATE_CYCLE_SLIP");
                s2 = (short) (s2 | 4);
            }
            if (s != s2) {
                arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssClock {
        public short gnssClockFlags;
        public short leapSecond = 0;
        public long timeNs = 0;
        public double timeUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public long fullBiasNs = 0;
        public double biasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double biasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double driftNsps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double driftUncertaintyNsps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public int hwClockDiscontinuityCount = 0;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssClock.class) {
                return false;
            }
            GnssClock gnssClock = (GnssClock) obj;
            return HidlSupport.deepEquals(Short.valueOf(this.gnssClockFlags), Short.valueOf(gnssClock.gnssClockFlags)) && this.leapSecond == gnssClock.leapSecond && this.timeNs == gnssClock.timeNs && this.timeUncertaintyNs == gnssClock.timeUncertaintyNs && this.fullBiasNs == gnssClock.fullBiasNs && this.biasNs == gnssClock.biasNs && this.biasUncertaintyNs == gnssClock.biasUncertaintyNs && this.driftNsps == gnssClock.driftNsps && this.driftUncertaintyNsps == gnssClock.driftUncertaintyNsps && this.hwClockDiscontinuityCount == gnssClock.hwClockDiscontinuityCount;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.gnssClockFlags))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.leapSecond))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.timeNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.timeUncertaintyNs))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.fullBiasNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.biasNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.biasUncertaintyNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.driftNsps))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.driftUncertaintyNsps))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.hwClockDiscontinuityCount))));
        }

        public final String toString() {
            return "{.gnssClockFlags = " + GnssClockFlags.dumpBitfield(this.gnssClockFlags) + ", .leapSecond = " + ((int) this.leapSecond) + ", .timeNs = " + this.timeNs + ", .timeUncertaintyNs = " + this.timeUncertaintyNs + ", .fullBiasNs = " + this.fullBiasNs + ", .biasNs = " + this.biasNs + ", .biasUncertaintyNs = " + this.biasUncertaintyNs + ", .driftNsps = " + this.driftNsps + ", .driftUncertaintyNsps = " + this.driftUncertaintyNsps + ", .hwClockDiscontinuityCount = " + this.hwClockDiscontinuityCount + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
        }

        public static final ArrayList<GnssClock> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssClock> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 72, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssClock gnssClock = new GnssClock();
                gnssClock.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 72);
                arrayList.add(gnssClock);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.gnssClockFlags = hwBlob.getInt16(j);
            this.leapSecond = hwBlob.getInt16(2 + j);
            this.timeNs = hwBlob.getInt64(8 + j);
            this.timeUncertaintyNs = hwBlob.getDouble(16 + j);
            this.fullBiasNs = hwBlob.getInt64(24 + j);
            this.biasNs = hwBlob.getDouble(32 + j);
            this.biasUncertaintyNs = hwBlob.getDouble(40 + j);
            this.driftNsps = hwBlob.getDouble(48 + j);
            this.driftUncertaintyNsps = hwBlob.getDouble(56 + j);
            this.hwClockDiscontinuityCount = hwBlob.getInt32(j + 64);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(72);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssClock> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 72);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt16(j, this.gnssClockFlags);
            hwBlob.putInt16(2 + j, this.leapSecond);
            hwBlob.putInt64(8 + j, this.timeNs);
            hwBlob.putDouble(16 + j, this.timeUncertaintyNs);
            hwBlob.putInt64(24 + j, this.fullBiasNs);
            hwBlob.putDouble(32 + j, this.biasNs);
            hwBlob.putDouble(40 + j, this.biasUncertaintyNs);
            hwBlob.putDouble(48 + j, this.driftNsps);
            hwBlob.putDouble(56 + j, this.driftUncertaintyNsps);
            hwBlob.putInt32(j + 64, this.hwClockDiscontinuityCount);
        }
    }

    public static final class GnssMeasurement {
        public short accumulatedDeltaRangeState;
        public int flags;
        public int state;
        public short svid = 0;
        public byte constellation = 0;
        public double timeOffsetNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public long receivedSvTimeInNs = 0;
        public long receivedSvTimeUncertaintyInNs = 0;
        public double cN0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double pseudorangeRateMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double pseudorangeRateUncertaintyMps = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double accumulatedDeltaRangeM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double accumulatedDeltaRangeUncertaintyM = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public float carrierFrequencyHz = 0.0f;
        public long carrierCycles = 0;
        public double carrierPhase = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double carrierPhaseUncertainty = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public byte multipathIndicator = 0;
        public double snrDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double agcLevelDb = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssMeasurement.class) {
                return false;
            }
            GnssMeasurement gnssMeasurement = (GnssMeasurement) obj;
            return HidlSupport.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(gnssMeasurement.flags)) && this.svid == gnssMeasurement.svid && this.constellation == gnssMeasurement.constellation && this.timeOffsetNs == gnssMeasurement.timeOffsetNs && HidlSupport.deepEquals(Integer.valueOf(this.state), Integer.valueOf(gnssMeasurement.state)) && this.receivedSvTimeInNs == gnssMeasurement.receivedSvTimeInNs && this.receivedSvTimeUncertaintyInNs == gnssMeasurement.receivedSvTimeUncertaintyInNs && this.cN0DbHz == gnssMeasurement.cN0DbHz && this.pseudorangeRateMps == gnssMeasurement.pseudorangeRateMps && this.pseudorangeRateUncertaintyMps == gnssMeasurement.pseudorangeRateUncertaintyMps && HidlSupport.deepEquals(Short.valueOf(this.accumulatedDeltaRangeState), Short.valueOf(gnssMeasurement.accumulatedDeltaRangeState)) && this.accumulatedDeltaRangeM == gnssMeasurement.accumulatedDeltaRangeM && this.accumulatedDeltaRangeUncertaintyM == gnssMeasurement.accumulatedDeltaRangeUncertaintyM && this.carrierFrequencyHz == gnssMeasurement.carrierFrequencyHz && this.carrierCycles == gnssMeasurement.carrierCycles && this.carrierPhase == gnssMeasurement.carrierPhase && this.carrierPhaseUncertainty == gnssMeasurement.carrierPhaseUncertainty && this.multipathIndicator == gnssMeasurement.multipathIndicator && this.snrDb == gnssMeasurement.snrDb && this.agcLevelDb == gnssMeasurement.agcLevelDb;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.flags))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.svid))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.constellation))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.timeOffsetNs))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.state))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.receivedSvTimeInNs))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.receivedSvTimeUncertaintyInNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.cN0DbHz))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.pseudorangeRateMps))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.pseudorangeRateUncertaintyMps))), Integer.valueOf(HidlSupport.deepHashCode(Short.valueOf(this.accumulatedDeltaRangeState))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.accumulatedDeltaRangeM))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.accumulatedDeltaRangeUncertaintyM))), Integer.valueOf(HidlSupport.deepHashCode(Float.valueOf(this.carrierFrequencyHz))), Integer.valueOf(HidlSupport.deepHashCode(Long.valueOf(this.carrierCycles))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.carrierPhase))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.carrierPhaseUncertainty))), Integer.valueOf(HidlSupport.deepHashCode(Byte.valueOf(this.multipathIndicator))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.snrDb))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.agcLevelDb))));
        }

        public final String toString() {
            return "{.flags = " + GnssMeasurementFlags.dumpBitfield(this.flags) + ", .svid = " + ((int) this.svid) + ", .constellation = " + GnssConstellationType.toString(this.constellation) + ", .timeOffsetNs = " + this.timeOffsetNs + ", .state = " + GnssMeasurementState.dumpBitfield(this.state) + ", .receivedSvTimeInNs = " + this.receivedSvTimeInNs + ", .receivedSvTimeUncertaintyInNs = " + this.receivedSvTimeUncertaintyInNs + ", .cN0DbHz = " + this.cN0DbHz + ", .pseudorangeRateMps = " + this.pseudorangeRateMps + ", .pseudorangeRateUncertaintyMps = " + this.pseudorangeRateUncertaintyMps + ", .accumulatedDeltaRangeState = " + GnssAccumulatedDeltaRangeState.dumpBitfield(this.accumulatedDeltaRangeState) + ", .accumulatedDeltaRangeM = " + this.accumulatedDeltaRangeM + ", .accumulatedDeltaRangeUncertaintyM = " + this.accumulatedDeltaRangeUncertaintyM + ", .carrierFrequencyHz = " + this.carrierFrequencyHz + ", .carrierCycles = " + this.carrierCycles + ", .carrierPhase = " + this.carrierPhase + ", .carrierPhaseUncertainty = " + this.carrierPhaseUncertainty + ", .multipathIndicator = " + GnssMultipathIndicator.toString(this.multipathIndicator) + ", .snrDb = " + this.snrDb + ", .agcLevelDb = " + this.agcLevelDb + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(144L), 0L);
        }

        public static final ArrayList<GnssMeasurement> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssMeasurement> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 144, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssMeasurement gnssMeasurement = new GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 144);
                arrayList.add(gnssMeasurement);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.flags = hwBlob.getInt32(j);
            this.svid = hwBlob.getInt16(4 + j);
            this.constellation = hwBlob.getInt8(6 + j);
            this.timeOffsetNs = hwBlob.getDouble(8 + j);
            this.state = hwBlob.getInt32(16 + j);
            this.receivedSvTimeInNs = hwBlob.getInt64(24 + j);
            this.receivedSvTimeUncertaintyInNs = hwBlob.getInt64(32 + j);
            this.cN0DbHz = hwBlob.getDouble(40 + j);
            this.pseudorangeRateMps = hwBlob.getDouble(48 + j);
            this.pseudorangeRateUncertaintyMps = hwBlob.getDouble(56 + j);
            this.accumulatedDeltaRangeState = hwBlob.getInt16(64 + j);
            this.accumulatedDeltaRangeM = hwBlob.getDouble(72 + j);
            this.accumulatedDeltaRangeUncertaintyM = hwBlob.getDouble(80 + j);
            this.carrierFrequencyHz = hwBlob.getFloat(88 + j);
            this.carrierCycles = hwBlob.getInt64(96 + j);
            this.carrierPhase = hwBlob.getDouble(104 + j);
            this.carrierPhaseUncertainty = hwBlob.getDouble(112 + j);
            this.multipathIndicator = hwBlob.getInt8(120 + j);
            this.snrDb = hwBlob.getDouble(128 + j);
            this.agcLevelDb = hwBlob.getDouble(j + 136);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(144);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssMeasurement> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 144);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 144);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt32(j, this.flags);
            hwBlob.putInt16(4 + j, this.svid);
            hwBlob.putInt8(6 + j, this.constellation);
            hwBlob.putDouble(8 + j, this.timeOffsetNs);
            hwBlob.putInt32(16 + j, this.state);
            hwBlob.putInt64(24 + j, this.receivedSvTimeInNs);
            hwBlob.putInt64(32 + j, this.receivedSvTimeUncertaintyInNs);
            hwBlob.putDouble(40 + j, this.cN0DbHz);
            hwBlob.putDouble(48 + j, this.pseudorangeRateMps);
            hwBlob.putDouble(56 + j, this.pseudorangeRateUncertaintyMps);
            hwBlob.putInt16(64 + j, this.accumulatedDeltaRangeState);
            hwBlob.putDouble(72 + j, this.accumulatedDeltaRangeM);
            hwBlob.putDouble(80 + j, this.accumulatedDeltaRangeUncertaintyM);
            hwBlob.putFloat(88 + j, this.carrierFrequencyHz);
            hwBlob.putInt64(96 + j, this.carrierCycles);
            hwBlob.putDouble(104 + j, this.carrierPhase);
            hwBlob.putDouble(112 + j, this.carrierPhaseUncertainty);
            hwBlob.putInt8(120 + j, this.multipathIndicator);
            hwBlob.putDouble(128 + j, this.snrDb);
            hwBlob.putDouble(j + 136, this.agcLevelDb);
        }
    }

    public static final class GnssData {
        public int measurementCount = 0;
        public GnssMeasurement[] measurements = new GnssMeasurement[64];
        public GnssClock clock = new GnssClock();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssData.class) {
                return false;
            }
            GnssData gnssData = (GnssData) obj;
            return this.measurementCount == gnssData.measurementCount && HidlSupport.deepEquals(this.measurements, gnssData.measurements) && HidlSupport.deepEquals(this.clock, gnssData.clock);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.measurementCount))), Integer.valueOf(HidlSupport.deepHashCode(this.measurements)), Integer.valueOf(HidlSupport.deepHashCode(this.clock)));
        }

        public final String toString() {
            return "{.measurementCount = " + this.measurementCount + ", .measurements = " + Arrays.toString(this.measurements) + ", .clock = " + this.clock + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(9296L), 0L);
        }

        public static final ArrayList<GnssData> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssData> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 9296, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssData gnssData = new GnssData();
                gnssData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 9296);
                arrayList.add(gnssData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.measurementCount = hwBlob.getInt32(j);
            long j2 = 8 + j;
            for (int i = 0; i < 64; i++) {
                this.measurements[i] = new GnssMeasurement();
                this.measurements[i].readEmbeddedFromParcel(hwParcel, hwBlob, j2);
                j2 += 144;
            }
            this.clock.readEmbeddedFromParcel(hwParcel, hwBlob, j + 9224);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(9296);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssData> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 9296);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 9296);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt32(j, this.measurementCount);
            long j2 = 8 + j;
            for (int i = 0; i < 64; i++) {
                this.measurements[i].writeEmbeddedToBlob(hwBlob, j2);
                j2 += 144;
            }
            this.clock.writeEmbeddedToBlob(hwBlob, j + 9224);
        }
    }

    public static final class Proxy implements IGnssMeasurementCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@1.0::IGnssMeasurementCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback
        public void GnssMeasurementCb(GnssData gnssData) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssMeasurementCallback {
        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssMeasurementCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssMeasurementCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{-67, -92, -110, -20, 64, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -47, 56, 105, -34, 114, -67, 111, -116, 21, -59, -125, 123, 120, -42, 19, 107, -115, 83, -114, -2, -59, 50, 5, 115, -91, -20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override // android.os.IHwBinder
        public IHwInterface queryLocalInterface(String str) {
            if (IGnssMeasurementCallback.kInterfaceName.equals(str)) {
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
                    hwParcel.enforceInterface(IGnssMeasurementCallback.kInterfaceName);
                    GnssData gnssData = new GnssData();
                    gnssData.readFromParcel(hwParcel);
                    GnssMeasurementCb(gnssData);
                    hwParcel2.writeStatus(0);
                    hwParcel2.send();
                    return;
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
