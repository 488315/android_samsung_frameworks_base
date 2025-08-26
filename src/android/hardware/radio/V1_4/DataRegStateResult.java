package android.hardware.radio.V1_4;

import android.internal.hidl.safe_union.V1_0.Monostate;
import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class DataRegStateResult {
    public android.hardware.radio.V1_2.DataRegStateResult base = new android.hardware.radio.V1_2.DataRegStateResult();
    public VopsInfo vopsInfo = new VopsInfo();
    public NrIndicators nrIndicators = new NrIndicators();

    public static final class VopsInfo {
        private byte hidl_d = 0;
        private Object hidl_o;

        public VopsInfo() {
            this.hidl_o = null;
            this.hidl_o = new Monostate();
        }

        public static final class hidl_discriminator {
            public static final byte lteVopsInfo = 1;
            public static final byte noinit = 0;

            public static final String getName(byte b) {
                if (b == 0) {
                    return "noinit";
                }
                if (b == 1) {
                    return "lteVopsInfo";
                }
                return LsConstants.TAG_UNKNOWN;
            }

            private hidl_discriminator() {
            }
        }

        public void noinit(Monostate monostate) {
            this.hidl_d = (byte) 0;
            this.hidl_o = monostate;
        }

        public Monostate noinit() {
            if (this.hidl_d != 0) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !Monostate.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (Monostate) this.hidl_o;
        }

        public void lteVopsInfo(LteVopsInfo lteVopsInfo) {
            this.hidl_d = (byte) 1;
            this.hidl_o = lteVopsInfo;
        }

        public LteVopsInfo lteVopsInfo() {
            if (this.hidl_d != 1) {
                Object obj = this.hidl_o;
                throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
            }
            Object obj2 = this.hidl_o;
            if (obj2 != null && !LteVopsInfo.class.isInstance(obj2)) {
                throw new Error("Union is in a corrupted state.");
            }
            return (LteVopsInfo) this.hidl_o;
        }

        public byte getDiscriminator() {
            return this.hidl_d;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || obj.getClass() != VopsInfo.class) {
                return false;
            }
            VopsInfo vopsInfo = (VopsInfo) obj;
            return this.hidl_d == vopsInfo.hidl_d && HidlSupport.deepEquals(this.hidl_o, vopsInfo.hidl_o);
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("{");
            byte b = this.hidl_d;
            if (b == 0) {
                sb.append(".noinit = ");
                sb.append(noinit());
            } else if (b == 1) {
                sb.append(".lteVopsInfo = ");
                sb.append(lteVopsInfo());
            } else {
                throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
            sb.append("}");
            return sb.toString();
        }

        public final void readFromParcel(HwParcel hwParcel) {
            readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
        }

        public static final ArrayList<VopsInfo> readVectorFromParcel(HwParcel hwParcel) {
            ArrayList<VopsInfo> arrayList = new ArrayList<>();
            HwBlob buffer = hwParcel.readBuffer(16L);
            int int32 = buffer.getInt32(8L);
            HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 3, buffer.handle(), 0L, true);
            arrayList.clear();
            for (int i = 0; i < int32; i++) {
                VopsInfo vopsInfo = new VopsInfo();
                vopsInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 3);
                arrayList.add(vopsInfo);
            }
            return arrayList;
        }

        public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
            byte int8 = hwBlob.getInt8(j);
            this.hidl_d = int8;
            if (int8 == 0) {
                Monostate monostate = new Monostate();
                this.hidl_o = monostate;
                monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 1);
            } else if (int8 == 1) {
                LteVopsInfo lteVopsInfo = new LteVopsInfo();
                this.hidl_o = lteVopsInfo;
                lteVopsInfo.readEmbeddedFromParcel(hwParcel, hwBlob, j + 1);
            } else {
                throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
            }
        }

        public final void writeToParcel(HwParcel hwParcel) {
            HwBlob hwBlob = new HwBlob(3);
            writeEmbeddedToBlob(hwBlob, 0L);
            hwParcel.writeBuffer(hwBlob);
        }

        public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<VopsInfo> arrayList) {
            HwBlob hwBlob = new HwBlob(16);
            int size = arrayList.size();
            hwBlob.putInt32(8L, size);
            hwBlob.putBool(12L, false);
            HwBlob hwBlob2 = new HwBlob(size * 3);
            for (int i = 0; i < size; i++) {
                arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
            }
            hwBlob.putBlob(0L, hwBlob2);
            hwParcel.writeBuffer(hwBlob);
        }

        public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
            hwBlob.putInt8(j, this.hidl_d);
            byte b = this.hidl_d;
            if (b == 0) {
                noinit().writeEmbeddedToBlob(hwBlob, j + 1);
            } else {
                if (b == 1) {
                    lteVopsInfo().writeEmbeddedToBlob(hwBlob, j + 1);
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
        if (obj == null || obj.getClass() != DataRegStateResult.class) {
            return false;
        }
        DataRegStateResult dataRegStateResult = (DataRegStateResult) obj;
        return HidlSupport.deepEquals(this.base, dataRegStateResult.base) && HidlSupport.deepEquals(this.vopsInfo, dataRegStateResult.vopsInfo) && HidlSupport.deepEquals(this.nrIndicators, dataRegStateResult.nrIndicators);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.base)), Integer.valueOf(HidlSupport.deepHashCode(this.vopsInfo)), Integer.valueOf(HidlSupport.deepHashCode(this.nrIndicators)));
    }

    public final String toString() {
        return "{.base = " + this.base + ", .vopsInfo = " + this.vopsInfo + ", .nrIndicators = " + this.nrIndicators + "}";
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public static final ArrayList<DataRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataRegStateResult> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 112, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            DataRegStateResult dataRegStateResult = new DataRegStateResult();
            dataRegStateResult.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 112);
            arrayList.add(dataRegStateResult);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, j);
        this.vopsInfo.readEmbeddedFromParcel(hwParcel, hwBlob, 104 + j);
        this.nrIndicators.readEmbeddedFromParcel(hwParcel, hwBlob, j + 107);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataRegStateResult> arrayList) {
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
        this.base.writeEmbeddedToBlob(hwBlob, j);
        this.vopsInfo.writeEmbeddedToBlob(hwBlob, 104 + j);
        this.nrIndicators.writeEmbeddedToBlob(hwBlob, j + 107);
    }
}
