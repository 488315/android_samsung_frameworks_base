package android.hardware.gnss.V2_1;

import android.hardware.gnss.V1_0.IGnssMeasurementCallback;
import android.hardware.gnss.V1_1.IGnssMeasurementCallback;
import android.hardware.gnss.V2_0.ElapsedRealtime;
import android.hardware.gnss.V2_0.IGnssMeasurementCallback;
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
public interface IGnssMeasurementCallback extends android.hardware.gnss.V2_0.IGnssMeasurementCallback {
    public static final String kInterfaceName = "android.hardware.gnss@2.1::IGnssMeasurementCallback";

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
    IHwBinder asBinder();

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    DebugInfo getDebugInfo() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<byte[]> getHashChain() throws RemoteException;

    void gnssMeasurementCb_2_1(GnssData gnssData) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    ArrayList<String> interfaceChain() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    String interfaceDescriptor() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void notifySyspropsChanged() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void ping() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
    void setHALInstrumentation() throws RemoteException;

    @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

    public static final class GnssMeasurementFlags {
        public static final int HAS_AUTOMATIC_GAIN_CONTROL = 8192;
        public static final int HAS_CARRIER_CYCLES = 1024;
        public static final int HAS_CARRIER_FREQUENCY = 512;
        public static final int HAS_CARRIER_PHASE = 2048;
        public static final int HAS_CARRIER_PHASE_UNCERTAINTY = 4096;
        public static final int HAS_FULL_ISB = 65536;
        public static final int HAS_FULL_ISB_UNCERTAINTY = 131072;
        public static final int HAS_SATELLITE_ISB = 262144;
        public static final int HAS_SATELLITE_ISB_UNCERTAINTY = 524288;
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
            if (i == 65536) {
                return "HAS_FULL_ISB";
            }
            if (i == 131072) {
                return "HAS_FULL_ISB_UNCERTAINTY";
            }
            if (i == 262144) {
                return "HAS_SATELLITE_ISB";
            }
            if (i == 524288) {
                return "HAS_SATELLITE_ISB_UNCERTAINTY";
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
            if ((i & 65536) == 65536) {
                arrayList.add("HAS_FULL_ISB");
                i2 |= 65536;
            }
            if ((i & 131072) == 131072) {
                arrayList.add("HAS_FULL_ISB_UNCERTAINTY");
                i2 |= 131072;
            }
            if ((i & 262144) == 262144) {
                arrayList.add("HAS_SATELLITE_ISB");
                i2 |= 262144;
            }
            if ((i & 524288) == 524288) {
                arrayList.add("HAS_SATELLITE_ISB_UNCERTAINTY");
                i2 |= 524288;
            }
            if (i != i2) {
                arrayList.add("0x" + Integer.toHexString(i & (~i2)));
            }
            return String.join(" | ", arrayList);
        }
    }

    public static final class GnssMeasurement {
        public int flags;
        public IGnssMeasurementCallback.GnssMeasurement v2_0 = new IGnssMeasurementCallback.GnssMeasurement();
        public double fullInterSignalBiasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double fullInterSignalBiasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double satelliteInterSignalBiasNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double satelliteInterSignalBiasUncertaintyNs = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        public double basebandCN0DbHz = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssMeasurement.class) {
                return false;
            }
            GnssMeasurement gnssMeasurement = (GnssMeasurement) obj;
            return HidlSupport.deepEquals(this.v2_0, gnssMeasurement.v2_0) && HidlSupport.deepEquals(Integer.valueOf(this.flags), Integer.valueOf(gnssMeasurement.flags)) && this.fullInterSignalBiasNs == gnssMeasurement.fullInterSignalBiasNs && this.fullInterSignalBiasUncertaintyNs == gnssMeasurement.fullInterSignalBiasUncertaintyNs && this.satelliteInterSignalBiasNs == gnssMeasurement.satelliteInterSignalBiasNs && this.satelliteInterSignalBiasUncertaintyNs == gnssMeasurement.satelliteInterSignalBiasUncertaintyNs && this.basebandCN0DbHz == gnssMeasurement.basebandCN0DbHz;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v2_0)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.flags))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.fullInterSignalBiasNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.fullInterSignalBiasUncertaintyNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.satelliteInterSignalBiasNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.satelliteInterSignalBiasUncertaintyNs))), Integer.valueOf(HidlSupport.deepHashCode(Double.valueOf(this.basebandCN0DbHz))));
        }

        public final String toString() {
            return "{.v2_0 = " + this.v2_0 + ", .flags = " + GnssMeasurementFlags.dumpBitfield(this.flags) + ", .fullInterSignalBiasNs = " + this.fullInterSignalBiasNs + ", .fullInterSignalBiasUncertaintyNs = " + this.fullInterSignalBiasUncertaintyNs + ", .satelliteInterSignalBiasNs = " + this.satelliteInterSignalBiasNs + ", .satelliteInterSignalBiasUncertaintyNs = " + this.satelliteInterSignalBiasUncertaintyNs + ", .basebandCN0DbHz = " + this.basebandCN0DbHz + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(224L), 0L);
        }

        public static final ArrayList<GnssMeasurement> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssMeasurement> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 224, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssMeasurement gnssMeasurement = new GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 224);
                arrayList.add(gnssMeasurement);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.v2_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
            this.flags = hwBlob.getInt32(176 + j);
            this.fullInterSignalBiasNs = hwBlob.getDouble(184 + j);
            this.fullInterSignalBiasUncertaintyNs = hwBlob.getDouble(192 + j);
            this.satelliteInterSignalBiasNs = hwBlob.getDouble(200 + j);
            this.satelliteInterSignalBiasUncertaintyNs = hwBlob.getDouble(208 + j);
            this.basebandCN0DbHz = hwBlob.getDouble(j + 216);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(224);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssMeasurement> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 224);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 224);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            this.v2_0.writeEmbeddedToBlob(hwBlob, j);
            hwBlob.putInt32(176 + j, this.flags);
            hwBlob.putDouble(184 + j, this.fullInterSignalBiasNs);
            hwBlob.putDouble(192 + j, this.fullInterSignalBiasUncertaintyNs);
            hwBlob.putDouble(200 + j, this.satelliteInterSignalBiasNs);
            hwBlob.putDouble(208 + j, this.satelliteInterSignalBiasUncertaintyNs);
            hwBlob.putDouble(j + 216, this.basebandCN0DbHz);
        }
    }

    public static final class GnssClock {
        public IGnssMeasurementCallback.GnssClock v1_0 = new IGnssMeasurementCallback.GnssClock();
        public GnssSignalType referenceSignalTypeForIsb = new GnssSignalType();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssClock.class) {
                return false;
            }
            GnssClock gnssClock = (GnssClock) obj;
            return HidlSupport.deepEquals(this.v1_0, gnssClock.v1_0) && HidlSupport.deepEquals(this.referenceSignalTypeForIsb, gnssClock.referenceSignalTypeForIsb);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.v1_0)), Integer.valueOf(HidlSupport.deepHashCode(this.referenceSignalTypeForIsb)));
        }

        public final String toString() {
            return "{.v1_0 = " + this.v1_0 + ", .referenceSignalTypeForIsb = " + this.referenceSignalTypeForIsb + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
        }

        public static final ArrayList<GnssClock> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssClock> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 104, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssClock gnssClock = new GnssClock();
                gnssClock.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 104);
                arrayList.add(gnssClock);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            this.v1_0.readEmbeddedFromParcel(hwParcel, hwBlob, j);
            this.referenceSignalTypeForIsb.readEmbeddedFromParcel(hwParcel, hwBlob, j + 72);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(104);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssClock> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 104);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 104);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            this.v1_0.writeEmbeddedToBlob(hwBlob, j);
            this.referenceSignalTypeForIsb.writeEmbeddedToBlob(hwBlob, j + 72);
        }
    }

    public static final class GnssData {
        public ArrayList<GnssMeasurement> measurements = new ArrayList<>();
        public GnssClock clock = new GnssClock();
        public ElapsedRealtime elapsedRealtime = new ElapsedRealtime();

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != GnssData.class) {
                return false;
            }
            GnssData gnssData = (GnssData) obj;
            return HidlSupport.deepEquals(this.measurements, gnssData.measurements) && HidlSupport.deepEquals(this.clock, gnssData.clock) && HidlSupport.deepEquals(this.elapsedRealtime, gnssData.elapsedRealtime);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.measurements)), Integer.valueOf(HidlSupport.deepHashCode(this.clock)), Integer.valueOf(HidlSupport.deepHashCode(this.elapsedRealtime)));
        }

        public final String toString() {
            return "{.measurements = " + this.measurements + ", .clock = " + this.clock + ", .elapsedRealtime = " + this.elapsedRealtime + "}";
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(144L), 0L);
        }

        public static final ArrayList<GnssData> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<GnssData> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 144, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                GnssData gnssData = new GnssData();
                gnssData.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 144);
                arrayList.add(gnssData);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            int int32 = hwBlob.getInt32(8 + j);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 224, hwBlob.handle(), j, true);
            this.measurements.clear();
            for (int i = 0; i < int32; i++) {
                GnssMeasurement gnssMeasurement = new GnssMeasurement();
                gnssMeasurement.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 224);
                this.measurements.add(gnssMeasurement);
            }
            this.clock.readEmbeddedFromParcel(hwParcel, hwBlob, 16 + j);
            this.elapsedRealtime.readEmbeddedFromParcel(hwParcel, hwBlob, 120 + j);
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(144);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GnssData> arrayList) {
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
            int size = this.measurements.size();
            hwBlob.putInt32(8 + j, size);
            hwBlob.putBool(12 + j, false);
            HwBlob hwBlob2 = new HwBlob(size * 224);
            for (int i = 0; i < size; i++) {
                this.measurements.get(i).writeEmbeddedToBlob(hwBlob2, i * 224);
            }
            hwBlob.putBlob(j, hwBlob2);
            this.clock.writeEmbeddedToBlob(hwBlob, 16 + j);
            this.elapsedRealtime.writeEmbeddedToBlob(hwBlob, j + 120);
        }
    }

    public static final class Proxy implements IGnssMeasurementCallback {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = (IHwBinder) Objects.requireNonNull(iHwBinder);
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        public String toString() {
            try {
                return interfaceDescriptor() + "@Proxy";
            } catch (RemoteException unused) {
                return "[class or subclass of android.hardware.gnss@2.1::IGnssMeasurementCallback]@Proxy";
            }
        }

        public final boolean equals(Object obj) {
            return HidlSupport.interfacesEqual(this, obj);
        }

        public final int hashCode() {
            return asBinder().hashCode();
        }

        @Override // android.hardware.gnss.V1_0.IGnssMeasurementCallback
        public void GnssMeasurementCb(IGnssMeasurementCallback.GnssData gnssData) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
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

        @Override // android.hardware.gnss.V1_1.IGnssMeasurementCallback
        public void gnssMeasurementCb(IGnssMeasurementCallback.GnssData gnssData) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_0.IGnssMeasurementCallback
        public void gnssMeasurementCb_2_0(IGnssMeasurementCallback.GnssData gnssData) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback
        public void gnssMeasurementCb_2_1(GnssData gnssData) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IGnssMeasurementCallback.kInterfaceName);
            gnssData.writeToParcel(hwParcel);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
            } finally {
                hwParcel2.release();
            }
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, j);
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub extends HwBinder implements IGnssMeasurementCallback {
        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase, android.os.IHwInterface
        public IHwBinder asBinder() {
            return this;
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void ping() {
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        @Override // android.os.IHwBinder, android.hardware.cas.V1_0.ICas, android.internal.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<>(Arrays.asList(IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName, android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName, IBase.kInterfaceName));
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return IGnssMeasurementCallback.kInterfaceName;
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final ArrayList<byte[]> getHashChain() {
            return new ArrayList<>(Arrays.asList(new byte[]{17, -23, -31, -95, -3, 12, -101, 61, -106, 72, 117, 13, 75, 16, -36, SprAnimatorBase.INTERPOLATOR_TYPE_SINEIN33, -125, -99, 58, 102, -120, MidiConstants.STATUS_NOTE_ON, 76, 63, -60, -107, 0, -92, -25, -54, 117, MidiConstants.STATUS_CONTROL_CHANGE}, new byte[]{-35, 108, -39, -37, -92, -3, -23, -102, 27, -61, -53, 23, 40, -40, 35, 9, -11, 9, -90, -26, -31, -103, 62, 80, 66, -33, -91, -1, -28, -81, 84, 66}, new byte[]{122, -30, 2, 86, 98, -29, 14, 105, 10, 63, -6, SprAnimatorBase.INTERPOLATOR_TYPE_QUADEASEIN, 101, -52, -105, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT50, 98, -105, -90, -122, 56, 23, 64, 85, -61, 60, -65, 61, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT70, 75, -67, -36}, new byte[]{-67, -92, -110, -20, 64, SprAnimatorBase.INTERPOLATOR_TYPE_QUARTEASEINOUT, -47, 56, 105, -34, 114, -67, 111, -116, 21, -59, -125, 123, 120, -42, 19, 107, -115, 83, -114, -2, -59, 50, 5, 115, -91, -20}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, MidiConstants.STATUS_CHANNEL_PRESSURE, SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT60, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, MidiConstants.STATUS_SONG_SELECT, -51, 105, 87, 19, -109, SprAnimatorBase.INTERPOLATOR_TYPE_QUINTEASEINOUT, -72, 59, 24, -54, 76}));
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hardware.gnss.V2_1.IGnssMeasurementCallback, android.hardware.gnss.V2_0.IGnssMeasurementCallback, android.hardware.gnss.V1_1.IGnssMeasurementCallback, android.hardware.gnss.V1_0.IGnssMeasurementCallback, android.internal.hidl.base.V1_0.IBase
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
            if (i == 1) {
                hwParcel.enforceInterface(android.hardware.gnss.V1_0.IGnssMeasurementCallback.kInterfaceName);
                IGnssMeasurementCallback.GnssData gnssData = new IGnssMeasurementCallback.GnssData();
                gnssData.readFromParcel(hwParcel);
                GnssMeasurementCb(gnssData);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 2) {
                hwParcel.enforceInterface(android.hardware.gnss.V1_1.IGnssMeasurementCallback.kInterfaceName);
                IGnssMeasurementCallback.GnssData gnssData2 = new IGnssMeasurementCallback.GnssData();
                gnssData2.readFromParcel(hwParcel);
                gnssMeasurementCb(gnssData2);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 3) {
                hwParcel.enforceInterface(android.hardware.gnss.V2_0.IGnssMeasurementCallback.kInterfaceName);
                IGnssMeasurementCallback.GnssData gnssData3 = new IGnssMeasurementCallback.GnssData();
                gnssData3.readFromParcel(hwParcel);
                gnssMeasurementCb_2_0(gnssData3);
                hwParcel2.writeStatus(0);
                hwParcel2.send();
                return;
            }
            if (i == 4) {
                hwParcel.enforceInterface(IGnssMeasurementCallback.kInterfaceName);
                GnssData gnssData4 = new GnssData();
                gnssData4.readFromParcel(hwParcel);
                gnssMeasurementCb_2_1(gnssData4);
                hwParcel2.writeStatus(0);
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
