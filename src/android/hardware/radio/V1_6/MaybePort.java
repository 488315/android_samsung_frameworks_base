package android.hardware.radio.V1_6;

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
public final class MaybePort {
    private byte hidl_d = 0;
    private Object hidl_o;

    public MaybePort() {
        this.hidl_o = null;
        this.hidl_o = new Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte noinit = 0;
        public static final byte range = 1;

        public static final String getName(byte b) {
            if (b == 0) {
                return "noinit";
            }
            if (b == 1) {
                return "range";
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

    public void range(PortRange portRange) {
        this.hidl_d = (byte) 1;
        this.hidl_o = portRange;
    }

    public PortRange range() {
        if (this.hidl_d != 1) {
            Object obj = this.hidl_o;
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
        }
        Object obj2 = this.hidl_o;
        if (obj2 != null && !PortRange.class.isInstance(obj2)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (PortRange) this.hidl_o;
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != MaybePort.class) {
            return false;
        }
        MaybePort maybePort = (MaybePort) obj;
        return this.hidl_d == maybePort.hidl_d && HidlSupport.deepEquals(this.hidl_o, maybePort.hidl_o);
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
            sb.append(".range = ");
            sb.append(range());
        } else {
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public static final ArrayList<MaybePort> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<MaybePort> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 12, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            MaybePort maybePort = new MaybePort();
            maybePort.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 12);
            arrayList.add(maybePort);
        }
        return arrayList;
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long j) {
        byte int8 = hwBlob.getInt8(j);
        this.hidl_d = int8;
        if (int8 == 0) {
            Monostate monostate = new Monostate();
            this.hidl_o = monostate;
            monostate.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        } else if (int8 == 1) {
            PortRange portRange = new PortRange();
            this.hidl_o = portRange;
            portRange.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        } else {
            throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<MaybePort> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 12);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.hidl_d);
        byte b = this.hidl_d;
        if (b == 0) {
            noinit().writeEmbeddedToBlob(hwBlob, j + 4);
        } else {
            if (b == 1) {
                range().writeEmbeddedToBlob(hwBlob, j + 4);
                return;
            }
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
