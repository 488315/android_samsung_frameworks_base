package android.hardware.radio.V1_6;

import android.hardware.radio.V1_2.CellConnectionStatus;
import android.hardware.radio.V1_2.CellInfoCdma;
import android.hardware.radio.V1_5.CellInfoGsm;
import android.hardware.radio.V1_5.CellInfoTdscdma;
import android.hardware.radio.V1_5.CellInfoWcdma;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class CellInfo {
    public boolean registered = false;
    public int connectionStatus = 0;
    public CellInfoRatSpecificInfo ratSpecificInfo = new CellInfoRatSpecificInfo();

    public static final class CellInfoRatSpecificInfo {
        private byte hidl_d = 0;
        private Object hidl_o;

        public CellInfoRatSpecificInfo() {
            this.hidl_o = null;
            this.hidl_o = new CellInfoGsm();
        }

        public static final class hidl_discriminator {
            public static final byte cdma = 5;
            public static final byte gsm = 0;
            public static final byte lte = 3;
            public static final byte nr = 4;
            public static final byte tdscdma = 2;
            public static final byte wcdma = 1;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "gsm";
                }
                if (b == 1) {
                    return "wcdma";
                }
                if (b == 2) {
                    return "tdscdma";
                }
                if (b == 3) {
                    return "lte";
                }
                if (b == 4) {
                    return "nr";
                }
                if (b == 5) {
                    return "cdma";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void gsm(CellInfoGsm cellInfoGsm) {
            this.hidl_d = (byte) 0;
            this.hidl_o = cellInfoGsm;
        }

        public CellInfoGsm gsm() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoGsm.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoGsm) this.hidl_o;
        }

        public void wcdma(CellInfoWcdma cellInfoWcdma) {
            this.hidl_d = (byte) 1;
            this.hidl_o = cellInfoWcdma;
        }

        public CellInfoWcdma wcdma() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoWcdma.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoWcdma) this.hidl_o;
        }

        public void tdscdma(CellInfoTdscdma cellInfoTdscdma) {
            this.hidl_d = (byte) 2;
            this.hidl_o = cellInfoTdscdma;
        }

        public CellInfoTdscdma tdscdma() {
            if (this.hidl_d != 2) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoTdscdma.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoTdscdma) this.hidl_o;
        }

        public void lte(CellInfoLte cellInfoLte) {
            this.hidl_d = (byte) 3;
            this.hidl_o = cellInfoLte;
        }

        public CellInfoLte lte() {
            if (this.hidl_d != 3) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoLte.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoLte) this.hidl_o;
        }

        public void nr(CellInfoNr cellInfoNr) {
            this.hidl_d = (byte) 4;
            this.hidl_o = cellInfoNr;
        }

        public CellInfoNr nr() {
            if (this.hidl_d != 4) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoNr.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoNr) this.hidl_o;
        }

        public void cdma(CellInfoCdma cellInfoCdma) {
            this.hidl_d = (byte) 5;
            this.hidl_o = cellInfoCdma;
        }

        public CellInfoCdma cdma() {
            if (this.hidl_d != 5) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !CellInfoCdma.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (CellInfoCdma) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != CellInfoRatSpecificInfo.class) {
                return false;
            }
            CellInfoRatSpecificInfo cellInfoRatSpecificInfo = (CellInfoRatSpecificInfo) obj;
            return this.hidl_d == cellInfoRatSpecificInfo.hidl_d && HidlSupport.deepEquals(this.hidl_o, cellInfoRatSpecificInfo.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".gsm = ");
                sb.append(gsm());
            } else if (b == 1) {
                sb.append(".wcdma = ");
                sb.append(wcdma());
            } else if (b == 2) {
                sb.append(".tdscdma = ");
                sb.append(tdscdma());
            } else if (b == 3) {
                sb.append(".lte = ");
                sb.append(lte());
            } else if (b == 4) {
                sb.append(".nr = ");
                sb.append(nr());
            } else if (b == 5) {
                sb.append(".cdma = ");
                sb.append(cdma());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(200L), 0L);
        }

        public static final ArrayList<CellInfoRatSpecificInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<CellInfoRatSpecificInfo> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 200, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                CellInfoRatSpecificInfo cellInfoRatSpecificInfo = new CellInfoRatSpecificInfo();
                cellInfoRatSpecificInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 200);
                arrayList.add(cellInfoRatSpecificInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                CellInfoGsm cellInfoGsm = new CellInfoGsm();
                this.hidl_o = cellInfoGsm;
                cellInfoGsm.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            }
            if (int8 == 1) {
                CellInfoWcdma cellInfoWcdma = new CellInfoWcdma();
                this.hidl_o = cellInfoWcdma;
                cellInfoWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            }
            if (int8 == 2) {
                CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma();
                this.hidl_o = cellInfoTdscdma;
                cellInfoTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
                return;
            }
            if (int8 == 3) {
                CellInfoLte cellInfoLte = new CellInfoLte();
                this.hidl_o = cellInfoLte;
                cellInfoLte.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
            } else if (int8 == 4) {
                CellInfoNr cellInfoNr = new CellInfoNr();
                this.hidl_o = cellInfoNr;
                cellInfoNr.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
            } else if (int8 == 5) {
                CellInfoCdma cellInfoCdma = new CellInfoCdma();
                this.hidl_o = cellInfoCdma;
                cellInfoCdma.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(200);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoRatSpecificInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 200);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 200);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                gsm().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            }
            if (b == 1) {
                wcdma().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            }
            if (b == 2) {
                tdscdma().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            }
            if (b == 3) {
                lte().writeEmbeddedToBlob(hwBlob, j + 8);
                return;
            }
            if (b == 4) {
                nr().writeEmbeddedToBlob(hwBlob, j + 8);
            } else {
                if (b == 5) {
                    cdma().writeEmbeddedToBlob(hwBlob, j + 8);
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
        if (obj == null || obj.getClass() != CellInfo.class) {
            return false;
        }
        CellInfo cellInfo = (CellInfo) obj;
        return this.registered == cellInfo.registered && this.connectionStatus == cellInfo.connectionStatus && HidlSupport.deepEquals(this.ratSpecificInfo, cellInfo.ratSpecificInfo);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(Boolean.valueOf(this.registered))), Integer.valueOf(HidlSupport.deepHashCode(Integer.valueOf(this.connectionStatus))), Integer.valueOf(HidlSupport.deepHashCode(this.ratSpecificInfo)));
    }

    public final String toString() {
        return "{.registered = " + this.registered + ", .connectionStatus = " + CellConnectionStatus.toString(this.connectionStatus) + ", .ratSpecificInfo = " + this.ratSpecificInfo + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(208L), 0L);
    }

    public static final ArrayList<CellInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 208, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            CellInfo cellInfo = new CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 208);
            arrayList.add(cellInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.registered = hwBlob.getBool(j);
        this.connectionStatus = hwBlob.getInt32(4 + j);
        this.ratSpecificInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 8);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(208);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 208);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 208);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putBool(j, this.registered);
        hwBlob.putInt32(4 + j, this.connectionStatus);
        this.ratSpecificInfo.writeEmbeddedToBlob(hwBlob, j + 8);
    }
}
