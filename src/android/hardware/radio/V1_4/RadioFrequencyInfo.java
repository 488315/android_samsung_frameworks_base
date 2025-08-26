package android.hardware.radio.V1_4;

import android.media.MediaMetrics;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.lock.LsConstants;
import java.util.ArrayList;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class RadioFrequencyInfo {
    private byte hidl_d = 0;
    private Object hidl_o;

    public RadioFrequencyInfo() {
        this.hidl_o = null;
        this.hidl_o = 0;
    }

    public static final class hidl_discriminator {
        public static final byte channelNumber = 1;
        public static final byte range = 0;

        public static final String getName(byte b) {
            if (b == 0) {
                return "range";
            }
            if (b == 1) {
                return "channelNumber";
            }
            return LsConstants.TAG_UNKNOWN;
        }

        private hidl_discriminator() {
        }
    }

    public void range(int i) {
        this.hidl_d = (byte) 0;
        this.hidl_o = Integer.valueOf(i);
    }

    public int range() {
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

    public void channelNumber(int i) {
        this.hidl_d = (byte) 1;
        this.hidl_o = Integer.valueOf(i);
    }

    public int channelNumber() {
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

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != RadioFrequencyInfo.class) {
            return false;
        }
        RadioFrequencyInfo radioFrequencyInfo = (RadioFrequencyInfo) obj;
        return this.hidl_d == radioFrequencyInfo.hidl_d && HidlSupport.deepEquals(this.hidl_o, radioFrequencyInfo.hidl_o);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(HidlSupport.deepHashCode(this.hidl_o)), Integer.valueOf(Objects.hashCode(Byte.valueOf(this.hidl_d))));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("{");
        byte b = this.hidl_d;
        if (b == 0) {
            sb.append(".range = ");
            sb.append(FrequencyRange.toString(range()));
        } else if (b == 1) {
            sb.append(".channelNumber = ");
            sb.append(channelNumber());
        } else {
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public static final ArrayList<RadioFrequencyInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioFrequencyInfo> arrayList = new ArrayList<>();
        HwBlob buffer = hwParcel.readBuffer(16L);
        int int32 = buffer.getInt32(8L);
        HwBlob embeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 8, buffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            RadioFrequencyInfo radioFrequencyInfo = new RadioFrequencyInfo();
            radioFrequencyInfo.readEmbeddedFromParcel(hwParcel, embeddedBuffer, i * 8);
            arrayList.add(radioFrequencyInfo);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        byte int8 = hwBlob.getInt8(j);
        this.hidl_d = int8;
        if (int8 == 0) {
            this.hidl_o = 0;
            this.hidl_o = Integer.valueOf(hwBlob.getInt32(j + 4));
        } else if (int8 == 1) {
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

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioFrequencyInfo> arrayList) {
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
            hwBlob.putInt32(j + 4, range());
        } else {
            if (b == 1) {
                hwBlob.putInt32(j + 4, channelNumber());
                return;
            }
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
