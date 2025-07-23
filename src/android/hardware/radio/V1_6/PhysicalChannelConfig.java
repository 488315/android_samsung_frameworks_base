package android.hardware.radio.V1_6;

import android.hardware.radio.V1_1.EutranBands;
import android.hardware.radio.V1_1.GeranBands;
import android.hardware.radio.V1_1.UtranBands;
import android.hardware.radio.V1_2.CellConnectionStatus;
import android.hardware.radio.V1_4.RadioTechnology;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class PhysicalChannelConfig {
    public int status = 0;
    public int rat = 0;
    public int downlinkChannelNumber = 0;
    public int uplinkChannelNumber = 0;
    public int cellBandwidthDownlinkKhz = 0;
    public int cellBandwidthUplinkKhz = 0;
    public ArrayList<Integer> contextIds = new ArrayList<>();
    public int physicalCellId = 0;
    public Band band = new Band();

    public static final class Band {
        private byte hidl_d = 0;
        private Object hidl_o;

        public Band() {
            this.hidl_o = null;
            this.hidl_o = 0;
        }

        public static final class hidl_discriminator {
            public static final byte eutranBand = 2;
            public static final byte geranBand = 0;
            public static final byte ngranBand = 3;
            public static final byte utranBand = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "geranBand";
                }
                if (b == 1) {
                    return "utranBand";
                }
                if (b == 2) {
                    return "eutranBand";
                }
                if (b == 3) {
                    return "ngranBand";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void geranBand(int i) {
            this.hidl_d = (byte) 0;
            this.hidl_o = Integer.valueOf(i);
        }

        public int geranBand() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public void utranBand(int i) {
            this.hidl_d = (byte) 1;
            this.hidl_o = Integer.valueOf(i);
        }

        public int utranBand() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public void eutranBand(int i) {
            this.hidl_d = (byte) 2;
            this.hidl_o = Integer.valueOf(i);
        }

        public int eutranBand() {
            if (this.hidl_d != 2) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public void ngranBand(int i) {
            this.hidl_d = (byte) 3;
            this.hidl_o = Integer.valueOf(i);
        }

        public int ngranBand() {
            if (this.hidl_d != 3) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Integer.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return ((Integer) this.hidl_o).intValue();
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != Band.class) {
                return false;
            }
            Band band = (Band) obj;
            return this.hidl_d == band.hidl_d && HidlSupport.deepEquals(this.hidl_o, band.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".geranBand = ");
                sb.append(GeranBands.toString(geranBand()));
            } else if (b == 1) {
                sb.append(".utranBand = ");
                sb.append(UtranBands.toString(utranBand()));
            } else if (b == 2) {
                sb.append(".eutranBand = ");
                sb.append(EutranBands.toString(eutranBand()));
            } else if (b == 3) {
                sb.append(".ngranBand = ");
                sb.append(NgranBands.toString(ngranBand()));
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
        }

        public static final ArrayList<Band> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<Band> arrayList = new ArrayList<>();
            HwBlob readBuffer = hwParcel.readBuffer(16L);
            int int32 = readBuffer.getInt32(8L);
            HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, readBuffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                Band band = new Band();
                band.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 8);
                arrayList.add(band);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
                return;
            }
            if (int8 == 1) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
                return;
            }
            if (int8 == 2) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
            } else if (int8 == 3) {
                this.hidl_o = 0;
                this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(8);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Band> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 8);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                hwBlob.putInt32(j + 4, geranBand());
                return;
            }
            if (b == 1) {
                hwBlob.putInt32(j + 4, utranBand());
                return;
            }
            if (b == 2) {
                hwBlob.putInt32(j + 4, eutranBand());
            } else {
                if (b == 3) {
                    hwBlob.putInt32(j + 4, ngranBand());
                    return;
                }
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != PhysicalChannelConfig.class) {
            return false;
        }
        PhysicalChannelConfig physicalChannelConfig = (PhysicalChannelConfig) obj;
        return this.status == physicalChannelConfig.status && this.rat == physicalChannelConfig.rat && this.downlinkChannelNumber == physicalChannelConfig.downlinkChannelNumber && this.uplinkChannelNumber == physicalChannelConfig.uplinkChannelNumber && this.cellBandwidthDownlinkKhz == physicalChannelConfig.cellBandwidthDownlinkKhz && this.cellBandwidthUplinkKhz == physicalChannelConfig.cellBandwidthUplinkKhz && HidlSupport.deepEquals(this.contextIds, physicalChannelConfig.contextIds) && this.physicalCellId == physicalChannelConfig.physicalCellId && HidlSupport.deepEquals(this.band, physicalChannelConfig.band);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.status))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.rat))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.downlinkChannelNumber))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.uplinkChannelNumber))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cellBandwidthDownlinkKhz))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.cellBandwidthUplinkKhz))), Integer.valueOf(HidlSupport.deepHashCode(this.contextIds)), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.physicalCellId))), Integer.valueOf(HidlSupport.deepHashCode(this.band)));
    }

    public final String toString() {
        return "{.status = " + CellConnectionStatus.toString(this.status) + ", .rat = " + RadioTechnology.toString(this.rat) + ", .downlinkChannelNumber = " + this.downlinkChannelNumber + ", .uplinkChannelNumber = " + this.uplinkChannelNumber + ", .cellBandwidthDownlinkKhz = " + this.cellBandwidthDownlinkKhz + ", .cellBandwidthUplinkKhz = " + this.cellBandwidthUplinkKhz + ", .contextIds = " + this.contextIds + ", .physicalCellId = " + this.physicalCellId + ", .band = " + this.band + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public static final ArrayList<PhysicalChannelConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalChannelConfig> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 56, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            PhysicalChannelConfig physicalChannelConfig = new PhysicalChannelConfig();
            physicalChannelConfig.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 56);
            arrayList.add(physicalChannelConfig);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.status = hwBlob.getInt32(j);
        this.rat = hwBlob.getInt32(4 + j);
        this.downlinkChannelNumber = hwBlob.getInt32(8 + j);
        this.uplinkChannelNumber = hwBlob.getInt32(12 + j);
        this.cellBandwidthDownlinkKhz = hwBlob.getInt32(16 + j);
        this.cellBandwidthUplinkKhz = hwBlob.getInt32(20 + j);
        int int32 = hwBlob.getInt32(32 + j);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 4, hwBlob.handle(), j + 24, true);
        this.contextIds.clear();
        for (int i = 0; i < int32; i++) {
            this.contextIds.add(Integer.valueOf(readEmbeddedBuffer.getInt32(i * 4)));
        }
        this.physicalCellId = hwBlob.getInt32(40 + j);
        this.band.readEmbeddedFromParcel(hwParcel, hwBlob, j + 44);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalChannelConfig> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 56);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt32(j, this.status);
        hwBlob.putInt32(4 + j, this.rat);
        hwBlob.putInt32(8 + j, this.downlinkChannelNumber);
        hwBlob.putInt32(12 + j, this.uplinkChannelNumber);
        hwBlob.putInt32(16 + j, this.cellBandwidthDownlinkKhz);
        hwBlob.putInt32(20 + j, this.cellBandwidthUplinkKhz);
        int size = this.contextIds.size();
        long j2 = 24 + j;
        hwBlob.putInt32(32 + j, size);
        hwBlob.putBool(36 + j, false);
        HwBlob hwBlob2 = new HwBlob(size * 4);
        for (int i = 0; i < size; i++) {
            hwBlob2.putInt32(i * 4, this.contextIds.get(i).intValue());
        }
        hwBlob.putBlob(j2, hwBlob2);
        hwBlob.putInt32(40 + j, this.physicalCellId);
        this.band.writeEmbeddedToBlob(hwBlob, j + 44);
    }
}
