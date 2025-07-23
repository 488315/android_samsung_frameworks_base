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
public final class Qos {
    private byte hidl_d = 0;
    private Object hidl_o;

    public Qos() {
        this.hidl_o = null;
        this.hidl_o = new Monostate();
    }

    public static final class hidl_discriminator {
        public static final byte eps = 1;
        public static final byte noinit = 0;
        public static final byte nr = 2;

        public static final String getName(byte b) {
            if (b == 0) {
                return "noinit";
            }
            if (b == 1) {
                return "eps";
            }
            if (b == 2) {
                return "nr";
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

    public void eps(EpsQos epsQos) {
        this.hidl_d = (byte) 1;
        this.hidl_o = epsQos;
    }

    public EpsQos eps() {
        if (this.hidl_d != 1) {
            Object obj = this.hidl_o;
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
        }
        Object obj2 = this.hidl_o;
        if (obj2 != null && !EpsQos.class.isInstance(obj2)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (EpsQos) this.hidl_o;
    }

    public void nr(NrQos nrQos) {
        this.hidl_d = (byte) 2;
        this.hidl_o = nrQos;
    }

    public NrQos nr() {
        if (this.hidl_d != 2) {
            Object obj = this.hidl_o;
            throw new IllegalStateException("Read access to inactive union components is disallowed. Discriminator value is " + ((int) this.hidl_d) + " (corresponding to " + hidl_discriminator.getName(this.hidl_d) + "), and hidl_o is of type " + (obj != null ? obj.getClass().getName() : PerfettoProtoLogImpl.NULL_STRING) + MediaMetrics.SEPARATOR);
        }
        Object obj2 = this.hidl_o;
        if (obj2 != null && !NrQos.class.isInstance(obj2)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (NrQos) this.hidl_o;
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Qos.class) {
            return false;
        }
        Qos qos = (Qos) obj;
        return this.hidl_d == qos.hidl_d && HidlSupport.deepEquals(this.hidl_o, qos.hidl_o);
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
            sb.append(".eps = ");
            sb.append(eps());
        } else if (b == 2) {
            sb.append(".nr = ");
            sb.append(nr());
        } else {
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
        sb.append("}");
        return sb.toString();
    }

    public final void readFromParcel(HwParcel hwParcel) {
        readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(28L), 0L);
    }

    public static final ArrayList<Qos> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Qos> arrayList = new ArrayList<>();
        HwBlob readBuffer = hwParcel.readBuffer(16L);
        int int32 = readBuffer.getInt32(8L);
        HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 28, readBuffer.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < int32; i++) {
            Qos qos = new Qos();
            qos.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i * 28);
            arrayList.add(qos);
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
            EpsQos epsQos = new EpsQos();
            this.hidl_o = epsQos;
            epsQos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        } else if (int8 == 2) {
            NrQos nrQos = new NrQos();
            this.hidl_o = nrQos;
            nrQos.readEmbeddedFromParcel(hwParcel, hwBlob, j + 4);
        } else {
            throw new IllegalStateException("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(28);
        writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Qos> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int size = arrayList.size();
        hwBlob.putInt32(8L, size);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(size * 28);
        for (int i = 0; i < size; i++) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 28);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long j) {
        hwBlob.putInt8(j, this.hidl_d);
        byte b = this.hidl_d;
        if (b == 0) {
            noinit().writeEmbeddedToBlob(hwBlob, j + 4);
            return;
        }
        if (b == 1) {
            eps().writeEmbeddedToBlob(hwBlob, j + 4);
        } else {
            if (b == 2) {
                nr().writeEmbeddedToBlob(hwBlob, j + 4);
                return;
            }
            throw new Error("Unknown union discriminator (value: " + ((int) this.hidl_d) + ").");
        }
    }
}
